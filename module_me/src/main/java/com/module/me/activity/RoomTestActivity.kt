package com.module.me.activity

import android.content.Context
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.module.constons.ARouteConstants
import com.lib.base.utils.ThreadUtils
import com.lib.base.view.BaseFragment
import com.lib.base.view.BaseMvvmRefreshDataBindingActivity
import com.lib.common.utils.VibrateTool
import com.lib.common.widget.CommonDialogFragment
import com.lib.log.KLog
import com.lib.net.dto.Resource
import com.lib.net.local.entity.UserTestRoom
import com.lib.net.utils.ext.observe
import com.module.me.BR
import com.module.me.R
import com.module.me.adapter.RoomTestAdapter
import com.module.me.databinding.ActivityRoomTestBinding
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * 测试 Room，使用了DataBinding示例
 */
@Route(path = ARouteConstants.Me.ME_ROOT_TEST, name = "Room测试")
class RoomTestActivity :
    BaseMvvmRefreshDataBindingActivity<UserTestRoom, ActivityRoomTestBinding, RoomTestViewModel>() {

    companion object {
        fun start(context: Context) {
            ARouter.getInstance().build(ARouteConstants.Me.ME_ROOT_TEST)
                .navigation(context)
        }
    }

    private val image = listOf(
        "https://img2.baidu.com/it/u=2237039644,3735368368&fm=26&fmt=auto",
        "https://img1.baidu.com/it/u=3303981320,1355171730&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=1429175118,2649084526&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=964231911,1680605089&fm=26&fmt=auto",
        "https://img1.baidu.com/it/u=765403172,907191121&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=1960058469,2576593478&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=2906052251,1830965798&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=304846655,1860504905&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=246253048,2961273437&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=2285567582,1185119578&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=1915681330,636879278&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=1406994547,3978776803&fm=26&fmt=auto",
        "https://img2.baidu.com/it/u=395719964,2145680590&fm=26&fmt=auto",
        "https://img1.baidu.com/it/u=138114168,3056449395&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=3829295089,224674855&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=225238437,3015521060&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=659615361,2427857441&fm=26&fmt=auto",
        "https://img0.baidu.com/it/u=3465878675,3814859588&fm=26&fmt=auto",
    )

    private val firstName = listOf(
        "赵",
        "钱",
        "孙",
        "李",
        "周",
        "吴",
        "郑",
        "王",
        "冯",
        "陈",
        "褚",
        "卫",
        "蒋",
        "沈",
        "韩",
        "杨",
        "朱",
        "秦",
        "尤",
        "许",
        "何",
        "吕",
        "施",
        "张",
        "孔",
        "曹",
        "严",
        "华",
        "金",
        "魏",
        "陶",
        "姜"
    )
    private val lastName = listOf("先生", "小姐", "夫人", "大夫", "同志", "同学", "老师", "老板", "经理")

    private lateinit var mAdapter: RoomTestAdapter

    override fun getTootBarTitle(): String = "Room"

    override fun enableToolBarLeft(): Boolean = true

    override fun onBindLayout(): Int = R.layout.activity_room_test

    override fun initView() {
        mAdapter = RoomTestAdapter()
        mAdapter.bindSkeletonScreen(
            requireBinding().mRecyclerView,
            com.lib.base.R.layout.skeleton_default_service_item,
            8
        )
        mAdapter.setOnItemLongClickListener { _, _, position ->
            VibrateTool.vibrateOnce(100)
            CommonDialogFragment.Builder()
                .setTitle("温馨提示")
                .setDescribe(
                    "您确认要删除%s%s吗？".format(
                        mAdapter.getItem(position).firstName,
                        mAdapter.getItem(position).lastName
                    )
                )
                .setLeftBtn("取消")
                .setRightBtn("确认")
                .setOnDialogClickListener(object : CommonDialogFragment.OnDialogClickListener {
                    override fun onLeftBtnClick(view: View) {

                    }

                    override fun onRightBtnClick(view: View) {
                        mViewModel.deleteUserTestRoom(mAdapter.getItem(position))
                    }
                })
                .build()
                .show(supportFragmentManager, "deleteDialog")

            false
        }
    }

    override fun initListener() {
        super.initListener()
        requireBinding().addUser.setOnClickListener(this::onClick)
        requireBinding().selectUser.setOnClickListener(this::onClick)
    }

    override fun initData() {
        onRefreshEvent()
    }

    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewModel to mViewModel)
    }

    override fun onBindViewModel(): Class<RoomTestViewModel> =
        RoomTestViewModel::class.java

    override fun initViewObservable() {
        observe(mViewModel.userTestRoomLiveData, ::handleUserTestRoomList)
    }

    override fun onClick(v: View?) {
        if (beFastClick()) {
            return
        }
        when (v?.id) {
            R.id.addUser -> {
                val userTestRoom = UserTestRoom(
                    image = image.random(),
                    firstName = firstName.random(),
                    lastName = lastName.random(),
                    age = Random.nextInt(IntRange(18, 50))
                )
                mViewModel.insertUserTestRoom(userTestRoom)
            }
            R.id.selectUser -> {
                onRefreshEvent()
            }
        }
    }

    private var firstLoad = true

    override fun onRefreshEvent() {
        // 为了展示骨架屏
        if (firstLoad) {
            firstLoad = false
            ThreadUtils.runOnUiThread({ mViewModel.refreshData() }, 1000)
        } else {
            mViewModel.refreshData()
        }
    }

    override fun onLoadMoreEvent() {
        mViewModel.loadMore()
    }

    override fun onBindRefreshLayout(): Int = R.id.mRefreshLayout

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    private fun handleUserTestRoomList(status: Resource<List<UserTestRoom>>) {
        when (status) {
            is Resource.Success -> status.data?.let { bindListData2(ArrayList(it)) }
            is Resource.DataError -> {
                status.errorCode.let { KLog.e(BaseFragment.TAG, "--------->$it") }
            }
            else -> {}
        }
    }

    private fun bindListData2(userTestRoom: ArrayList<UserTestRoom>) {
        mAdapter.setNewInstance(userTestRoom)
        mViewModel.showEmpty.set(userTestRoom.isEmpty())
    }
}