package com.example.test001.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.test001.R

/**
 * 内容子元素
 */
class ExcelContentChildAdapter :
    BaseQuickAdapter<ArrayList<String>, BaseViewHolder>(R.layout.item_excel_content_child, null) {
    private var position = -1

    override fun convert(holder: BaseViewHolder, item: ArrayList<String>) {
        val tvContentTop = holder.getView<TextView>(R.id.tv_excel_content_top)
        val viewContentMid = holder.getView<View>(R.id.view_excel_content_mid)
        val tvContentBottom = holder.getView<TextView>(R.id.tv_excel_content_bottom)
        val layoutContentChild = holder.getView<LinearLayout>(R.id.layout_excel_content_child)
        layoutContentChild.isSelected = holder.adapterPosition == position
        if (item.size == 1) {
            tvContentTop.text = item[0]
            viewContentMid.visibility = View.GONE
            tvContentBottom.visibility = View.GONE
        } else {
            tvContentTop.text = item[0]
            viewContentMid.visibility = View.VISIBLE
            tvContentBottom.visibility = View.VISIBLE
            tvContentBottom.text = item[1]
        }
    }

    fun setExcelBg(position: Int) {
        this.position = position
        notifyDataSetChanged()
    }
}