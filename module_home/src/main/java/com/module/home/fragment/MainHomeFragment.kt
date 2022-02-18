package com.module.home.fragment

import android.view.View
import com.module.home.adapter.MainHomeAdapter
import com.lib.base.utils.ThreadUtils
import com.lib.base.view.BaseMvvmRefreshDataBindingFragment
import com.lib.common.utils.EnvironmentUtil
import com.lib.domain.entity.Demo
import com.lib.log.KLog
import com.lib.net.dto.Resource
import com.lib.net.utils.ext.launch
import com.lib.net.utils.ext.observe
import com.module.home.BR
import com.module.home.R
import com.module.home.databinding.FragmentHomeMainBinding
import com.module.home.viewmodel.MainHomeViewModel

/**
 * Describe:
 */
class MainHomeFragment :
    BaseMvvmRefreshDataBindingFragment<Demo, FragmentHomeMainBinding, MainHomeViewModel>() {

    companion object {
        fun newsInstance(): MainHomeFragment {
            return MainHomeFragment()
        }
    }

    private lateinit var mAdapter: MainHomeAdapter

    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewModel to mViewModel)
    }

    override fun onBindViewModel(): Class<MainHomeViewModel> = MainHomeViewModel::class.java

    override fun initViewObservable() {
        observe(mViewModel.recipesLiveData, ::handleRecipesList)
    }

    override fun onBindLayout(): Int = R.layout.fragment_home_main

    override fun initView(mView: View) {
        mAdapter = MainHomeAdapter()
        mAdapter.bindSkeletonScreen(
            requireBinding().mRecyclerView,
            com.lib.base.R.layout.skeleton_default_service_item,
            8
        )
    }


    override fun initData() {
        onRefreshEvent()
        KLog.d(TAG, EnvironmentUtil.Storage.getCachePath(mContext))
    }

    var firstLoad = true

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


    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = "首页"

    private fun handleRecipesList(resource: Resource<List<Demo>>) {
        resource.launch {
            it?.apply {
                bindListData(recipes = ArrayList(this))
            }
        }
    }

    private fun bindListData(recipes: ArrayList<Demo>) {
        mAdapter.setNewInstance(recipes)
    }

    override fun onBindRefreshLayout(): Int {
        TODO("Not yet implemented")
    }
}
