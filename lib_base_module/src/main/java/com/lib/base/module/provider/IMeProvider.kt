package com.lib.base.module.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IMeProvider : IProvider {
    val mainMeFragment: Fragment
}