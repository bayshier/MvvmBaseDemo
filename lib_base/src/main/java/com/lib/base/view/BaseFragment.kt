package com.lib.base.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.R
import com.lib.base.event.common.BaseFragmentEvent
import com.lib.base.mvvm.view.BaseView
import com.lib.base.utils.NetUtil
import com.lib.base.widget.LoadingInitView
import com.lib.base.widget.LoadingTransView
import com.lib.base.widget.NetErrorView
import com.lib.base.widget.NoDataView
import com.lib.log.KLog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseFragment : Fragment(), BaseView {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    protected lateinit var mContext: Context

    protected lateinit var mActivity: RxAppCompatActivity
    protected lateinit var mView: View

    protected var mTxtTitle: TextView? = null
    protected var tvToolbarRight: TextView? = null
    protected var ivToolbarRight: ImageView? = null
    protected var mToolbar: Toolbar? = null

    protected var mNetErrorView: NetErrorView? = null
    protected var mNoDataView: NoDataView? = null
    protected var mLoadingInitView: LoadingInitView? = null
    protected var mLoadingTransView: LoadingTransView? = null

    protected lateinit var mViewStubToolbar: ViewStub
    protected lateinit var mViewStubContent: ViewStub
    protected lateinit var mViewStubInitLoading: ViewStub
    protected lateinit var mViewStubTransLoading: ViewStub
    protected lateinit var mViewStubNoData: ViewStub
    protected lateinit var mViewStubError: ViewStub
    private var isViewCreated = false
    private var isViewVisable = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentTimeMillis = System.currentTimeMillis()

        mActivity = (activity as RxAppCompatActivity?)!!
        ARouter.getInstance().inject(this)
        EventBus.getDefault().register(this)
        initBundle()

        val totalTime = System.currentTimeMillis() - currentTimeMillis
        KLog.e(TAG, "onCreate: ???????????????Fragment: $javaClass ???????????????:$totalTime ms")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_root, container, false)
        initCommonView(mView)
        return mView
    }

    open fun initCommonView(view: View) {
        mViewStubToolbar = view.findViewById(R.id.view_stub_toolbar)
        mViewStubContent = view.findViewById(R.id.view_stub_content)
        mViewStubInitLoading = view.findViewById(R.id.view_stub_init_loading)
        mViewStubTransLoading = view.findViewById(R.id.view_stub_trans_loading)
        mViewStubNoData = view.findViewById(R.id.view_stub_nodata)
        mViewStubError = view.findViewById(R.id.view_stub_error)

        if (enableToolbar()) {
            mViewStubToolbar.layoutResource = onBindToolbarLayout()
            val viewToolBar = mViewStubToolbar.inflate()
            initTooBar(viewToolBar)
        }
        initContentView(mViewStubContent)
    }

    open fun initContentView(mViewStubContent: ViewStub) {
        mViewStubContent.layoutResource = onBindLayout()
        mViewStubContent.inflate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(mView)
        initListener()

        isViewCreated = true
    }

    /**
     * isVisibleToUser =true????????????????????????????????????false ???????????????
     * setUserVisibleHint(boolean isVisibleToUser) ?????? Fragment OnCreateView()?????????????????????
     * ??????FragmentTransaction.setMaxLifecycle ?????? Lifecycle.State.RESUMED ????????????????????????????????????
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisable = isVisibleToUser
        //?????????????????????????????????????????????
        if (enableLazyData() && isViewVisable) {
            lazyLoad()
        }
    }

    /**
     * ??????????????? ????????????????????????????????????
     * ???????????? FragmentTransaction.setMaxLifecycle ?????? Lifecycle.State.RESUMED ??? ???????????????
     * ?????? FragmentTransaction.setMaxLifecycle ??????BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ???
     * ??????????????????Fragment??????Lifecycle.State.RESUMED????????? ??????????????????????????????Lifecycle.State.STARTED ???
     * ???????????????BEHAVIOR_SET_USER_VISIBLE_HINT ???????????????????????????Lifecycle.State.RESUMED?????????
     * ???????????????Fragment.setUserVisibleHint(boolean)??????
     */
    private fun lazyLoad() {
        //??????????????????????????????,????????????onCreateView???????????????????????????,???????????????
        KLog.v("MYTAG", "lazyLoad start...")
        KLog.v("MYTAG", "isViewCreated:$isViewCreated")
        KLog.v("MYTAG", "isViewVisable:$isViewVisable")
        if (isViewCreated && isViewVisable) {
            Log.d(TAG, "lazyLoad: Successful")
            initData()
            //??????????????????,????????????,??????????????????
            isViewCreated = false
            isViewVisable = false
        } else {
            Log.d(TAG, "lazyLoad: Fail")
        }
    }

    //????????????????????????
    open fun enableLazyData(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        //?????????????????????????????????????????????????????????????????????
        if (enableLazyData()) {
            lazyLoad()
        } else {
            initData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    protected fun initTooBar(view: View) {
        mToolbar = view.findViewById(R.id.toolbar_root)
        mTxtTitle = view.findViewById(R.id.toolbar_title)
        tvToolbarRight = view.findViewById(R.id.tv_toolbar_right)
        mToolbar?.apply {
            mActivity.setSupportActionBar(this)
            mActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
            setNavigationOnClickListener { mActivity.onBackPressed() }
            if (enableToolBarLeft()) {
                //????????????????????????NavigationIcon.????????????
                mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                //??????NavigationIcon???icon.?????????Drawable ,????????????ResId
                setNavigationIcon(getToolBarLeftIcon())
            }
            //???????????????????????????????????????????????????????????????
            if (getToolBarRightTxt().isNotBlank()) {
                tvToolbarRight?.apply {
                    text = getToolBarRightTxt()
                    visibility = View.VISIBLE
                    setOnClickListener(getToolBarRightTxtClick())
                }
            }
            //?????????????????????????????? ??????0?????????????????????
            if (getToolBarRightImg() != 0) {
                ivToolbarRight?.apply {
                    setImageResource(getToolBarRightImg())
                    visibility = View.VISIBLE
                    setOnClickListener(getToolBarRightImgClick())
                }
            }
        }
        mTxtTitle?.text = getTootBarTitle()
    }

    open fun getTootBarTitle(): String {
        return ""
    }

    /**
     * ???????????????????????????????????????Drawable ,????????????ResId
     * ???????????? enableToolBarLeft ????????? true ????????????
     *
     * @return
     */
    open fun getToolBarLeftIcon(): Int {
        return R.drawable.navbar_icon_return
    }

    /**
     * ??????????????????
     *
     * @return
     */
    open fun enableToolBarLeft(): Boolean {
        return false
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    open fun getToolBarRightTxt(): String {
        return ""
    }

    /**
     * ???????????????????????? Icon
     *
     * @return int resId ??????
     */
    open fun getToolBarRightImg(): Int {
        return 0
    }

    /**
     * ????????????????????????
     * @return
     */
    open fun getToolBarRightTxtClick(): View.OnClickListener? {
        return null
    }

    /**
     * ????????????????????????
     * @return
     */
    open fun getToolBarRightImgClick(): View.OnClickListener? {
        return null
    }

    open fun initBundle() {

    }

    abstract fun onBindLayout(): Int

    abstract fun initView(mView: View)
    abstract override fun initData()

    override fun initListener() {}

    override fun finishActivity() {
        mActivity.finish()
    }

    open fun enableToolbar(): Boolean {
        return false
    }

    open fun onBindToolbarLayout(): Int {
        return R.layout.common_toolbar
    }

    override fun showInitLoadView() {
        showInitLoadView(true)
    }

    override fun hideInitLoadView() {
        showInitLoadView(false)
    }

    override fun showTransLoadingView() {
        showTransLoadingView(true)
    }

    override fun hideTransLoadingView() {
        showTransLoadingView(false)
    }

    override fun showNoDataView() {
        showNoDataView(true)
    }

    override fun showNoDataView(resid: Int) {
        showNoDataView(true, resid)
    }

    override fun hideNoDataView() {
        showNoDataView(false)
    }

    override fun showNetWorkErrView() {
        showNetWorkErrView(true)
    }

    override fun hideNetWorkErrView() {
        showNetWorkErrView(false)
    }

    open fun showInitLoadView(show: Boolean) {
        if (mLoadingInitView == null) {
            val view = mViewStubInitLoading.inflate()
            mLoadingInitView = view.findViewById(R.id.view_init_loading)
        }
        mLoadingInitView?.visibility = if (show) View.VISIBLE else View.GONE
        mLoadingInitView?.loading(show)
    }

    open fun showNetWorkErrView(show: Boolean) {
        if (mNetErrorView == null) {
            val view = mViewStubError.inflate()
            mNetErrorView = view.findViewById(R.id.view_net_error)
            mNetErrorView?.setOnClickListener(View.OnClickListener {
                if (!NetUtil.checkNetToast()) {
                    return@OnClickListener
                }
                hideNetWorkErrView()
                initData()
            })
        }
        mNetErrorView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    open fun showNoDataView(show: Boolean) {
        if (mNoDataView == null) {
            val view = mViewStubNoData.inflate()
            mNoDataView = view.findViewById(R.id.view_no_data)
        }
        mNoDataView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    open fun showNoDataView(show: Boolean, resid: Int) {
        showNoDataView(show)
        if (show) {
            mNoDataView?.setNoDataView(resid)
        }
    }

    open fun showTransLoadingView(show: Boolean) {
        if (mLoadingTransView == null) {
            val view = mViewStubTransLoading.inflate()
            mLoadingTransView = view.findViewById(R.id.view_trans_loading)
        }
        mLoadingTransView?.visibility = if (show) View.VISIBLE else View.GONE
        mLoadingTransView?.loading(show)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> onEvent(event: BaseFragmentEvent<T>) {
    }

    open fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(activity, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * ????????????
     * ???????????? startActivity<TargetActivity> { putExtra("param1", "data1") }
     */
    inline fun <reified T> startActivity(block: Intent.() -> Unit) {
        val intent = Intent(context, T::class.java)
        intent.block()
        startActivity(intent)
    }

    /**
     * ??????????????????
     * open("?????????????????????ARouter??????") {
     * withString("????????????extra???key", "????????????extra???value")
     * }
     */
    open fun open(path: String, block: Postcard.() -> Unit = {}) {
        val postcard = ARouter.getInstance().build(path)
        postcard.block()
        postcard.navigation()
    }

    /**
     * ???????????????????????????????????????
     * ?????????????????????Activity????????????Frament??????????????????
     */
    open fun openWithFinish(path: String, block: Postcard.() -> Unit = {}) {
        open(path, block)
        mActivity.finish()
    }

    private var mLastButterKnifeClickTime: Long = 0

    /**
     * ??????????????????
     *
     * @return true ???
     */
    open fun beFastClick(): Boolean {
        val currentClickTime = System.currentTimeMillis()
        val flag = currentClickTime - mLastButterKnifeClickTime < 400L
        mLastButterKnifeClickTime = currentClickTime
        return flag
    }

    open fun onClick(v: View?) {

    }

    open fun <T : View?> findViewById(@IdRes id: Int): T {
        return mView.findViewById<T>(id)
    }
}
