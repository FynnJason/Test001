package com.example.test001.widget

import android.content.Context
import android.view.Gravity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.test001.databinding.DialogEditBinding

class EditValueDialog(context: Context, var hv: String) : BaseDialog(context) {
    private lateinit var mBinding: DialogEditBinding

    override fun initBinding() {
        mBinding = DialogEditBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun remain() {
        mBinding.etValue.hint = hv
        mBinding.tvSure.setOnClickListener {
            val nv = mBinding.etValue.text.toString()
            if (nv.isNotEmpty()) {
                onValueCallback.getNewValue(nv)
            }
            dismiss()
        }
    }

    override fun gravity(): Int {
        return Gravity.BOTTOM
    }

    interface OnValueCallback {
        fun getNewValue(newValue: String)
    }

    private lateinit var onValueCallback: OnValueCallback

    fun setOnValueListener(onValueCallback: OnValueCallback) {
        this.onValueCallback = onValueCallback
    }
}