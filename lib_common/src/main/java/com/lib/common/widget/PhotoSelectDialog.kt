package com.lib.common.widget


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lib.common.R
import com.lib.common.utils.DisplayUtil
import com.lib.common.utils.MultiMediaUtil
import me.nereo.multi_image_selector.MultiImageSelectorActivity

class PhotoSelectDialog : BottomSheetDialogFragment(), View.OnClickListener {
    private var mOnClickLisener: OnPhotoClickLisener? = null
    private var mPhotoPath: String? = null

    fun setOnClickLisener(onPhotoClickLisener: OnPhotoClickLisener) {
        mOnClickLisener = onPhotoClickLisener
    }

    interface OnPhotoClickLisener {
        fun onTakePhototClick(path: String?)

        fun onSelectPhotoClick(list: List<String>)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(resources.displayMetrics.widthPixels - DisplayUtil.dip2px(16f) * 2, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.findViewById<View>(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_select, container, false)
        val btnSelectPhoto = view.findViewById<View>(R.id.btn_select_photo) as Button
        val btnTakephoto = view.findViewById<View>(R.id.btn_take_photo) as Button
        val btnCancel = view.findViewById<View>(R.id.btn_cancel) as Button

        btnSelectPhoto.setOnClickListener(this)
        btnTakephoto.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.btn_take_photo) {
            mPhotoPath = MultiMediaUtil.getPhotoPath(requireActivity())
            MultiMediaUtil.takePhoto(this, mPhotoPath!!, MultiMediaUtil.TAKE_PHONE)

        } else if (i == R.id.btn_select_photo) {
            MultiMediaUtil.pohotoSelect(this, 1, MultiMediaUtil.SELECT_IMAGE)

        } else if (i == R.id.btn_cancel) {
            dismiss()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            MultiMediaUtil.SELECT_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                val path = data!!.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)
                if (mOnClickLisener != null) {
                    if (path != null) {
                        mOnClickLisener!!.onSelectPhotoClick(path)
                    }
                }
                dismiss()
            }
            MultiMediaUtil.TAKE_PHONE -> {
                Log.v(TAG, "img path:" + mPhotoPath!!)
                if (mOnClickLisener != null) {
                    mOnClickLisener!!.onTakePhototClick(mPhotoPath)
                }
                dismiss()
            }
        }
    }

    companion object {

        val TAG = PhotoSelectDialog::class.java.simpleName

        fun newInstance(): PhotoSelectDialog {
            return PhotoSelectDialog()
        }
    }
}
