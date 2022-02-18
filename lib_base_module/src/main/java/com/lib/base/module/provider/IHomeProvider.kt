package com.lib.base.module.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IHomeProvider : IProvider {
    val mainHomeFragment: Fragment
}