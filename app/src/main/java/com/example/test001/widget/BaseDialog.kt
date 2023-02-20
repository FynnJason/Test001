package com.example.test001.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.example.test001.R

abstract class BaseDialog(context: Context) : Dialog(context, R.style.Theme_EditValue) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setFull()
        remain()
    }

    abstract fun initBinding()
    abstract fun remain()
    abstract fun gravity(): Int

    private fun setFull() {
        val window = this.window
        if (window != null) {
            if (gravity() != 0) window.setGravity(gravity())
            window.decorView.setPadding(0, 0, 0, 0)
            val layoutParams = window.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
            window.decorView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun show() {
        super.show()
        setFull()
    }
}