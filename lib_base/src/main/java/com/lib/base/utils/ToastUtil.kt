package com.lib.base.utils

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.lib.base.BuildConfig
import com.lib.base.R



object ToastUtil {

    private var mHandler: Handler? = null

    private var mContext: Application? = null
        get() {
            checkNotNull(field) {
                "还未初始化土司工具类！！！"
            }
            return field
        }

    @JvmStatic
    fun init(context: Application) {
        mContext = context
    }

    @JvmStatic
    fun showCenterOnTestAndDev(res: String) {
        val message = "该提示仅会出现在测试和开发环境：\n$res"
        if (BuildConfig.DEBUG) {
            showToastCenter(message)
        }
    }

    /**
     * 普通Toast
     * @param resId 资源
     */
    @JvmStatic
    fun showToast(@StringRes resId: Int) {
        showToast(resId = resId, duration = Toast.LENGTH_SHORT)
    }

    /**
     * 普通Toast
     * @param resId 资源
     * @param duration 时长
     */
    @JvmStatic
    fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(message = mContext!!.getString(resId), duration = duration)
    }

    /**
     * 普通Toast
     * @param message 文本
     */
    @JvmStatic
    fun showToast(message: CharSequence) {
        showToast(message = message, duration = Toast.LENGTH_SHORT)
    }

    /**
     * 普通Toast
     * @param message 文本
     * @param duration 时长
     */
    @JvmStatic
    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        runOnMainLooper {
            buildToastView(
                message = message,
                duration = duration,
                isNormal = true
            ).show()
        }
    }

    /**
     * 中央toast
     * @param resId 资源文件
     */
    @JvmStatic
    fun showToastCenter(@StringRes resId: Int) {
        showToastCenter(message = mContext!!.getString(resId))
    }

    /**
     * 中央toast
     * @param resId 资源文件
     */
    @JvmStatic
    fun showToastCenter(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToastCenter(message = mContext!!.getString(resId), duration = duration)
    }

    /**
     * 中央toast
     * @param message 文本
     */
    @JvmStatic
    fun showToastCenter(message: CharSequence) {
        showToastCenter(message = message, duration = Toast.LENGTH_SHORT)
    }

    /**
     * 中央toast
     * @param message 文本
     * @param duration 时长
     */
    @JvmStatic
    fun showToastCenter(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        runOnMainLooper {
            buildToastView(
                message = message,
                duration = duration
            ).show()
        }
    }

    /**
     * 构建 ToastView
     * 当且仅当 isNormal 优先级最高 次之 isCustom
     * @param message 文本
     * @param duration 时长
     * @param isCustom 是否开启自定义 默认true 开启
     * @param isNormal 是否普通 默认false 关闭
     */
    @JvmStatic
    fun buildToastView(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        isCustom: Boolean = true,
        isNormal: Boolean = false
    ): Toast {
        var toast = Toast(mContext)
        if (isNormal) {
            toast = Toast.makeText(mContext, message, duration)
        } else {
            if (isCustom /*&& Build.VERSION.SDK_INT < 30*/) {
                val inflate = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_toast, LinearLayout(mContext), true)
                inflate.findViewById<TextView>(R.id.toast_text).text = message
                toast.view = inflate
                toast.duration = duration
                toast.setGravity(Gravity.CENTER, 0, 0)
            } else {
                toast = Toast.makeText(mContext, message, duration)
            }
        }
        return toast
    }

    private fun runOnMainLooper(runnable: Runnable) {
        if (mHandler == null) {
            mHandler = Handler(Looper.getMainLooper())
        }
        mHandler!!.post(runnable)
    }

    /**
     * 情况所有的弹窗任务
     */
    @JvmStatic
    fun clearAllToastTask() {
        mHandler?.removeCallbacksAndMessages(null)
    }

    @JvmStatic
    fun showSnackbar(
        view: View,
        text: String,
        actionText: String? = null,
        duration: Int = Snackbar.LENGTH_SHORT,
        block: (() -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(view, text, duration)
        if (actionText != null && block != null) {
            snackbar.setAction(actionText) {
                block()
            }
        }
        snackbar.show()
    }

    @JvmStatic
    fun showSnackbar(
        view: View,
        resid: Int,
        actionText: String? = null,
        duration: Int = Snackbar.LENGTH_SHORT,
        block: (() -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(view, resid, duration)
        if (actionText != null && block != null) {
            snackbar.setAction(actionText) {
                block()
            }
        }
        snackbar.show()
    }
}
