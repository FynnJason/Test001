package com.example.test001.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.test001.R

/**
 * 标题列
 */
class ExcelColumnTitleAdapter :
    BaseQuickAdapter<ArrayList<String>, BaseViewHolder>(R.layout.item_excel_column_title, null) {
    var position = -1

    override fun convert(holder: BaseViewHolder, item: ArrayList<String>) {
        val tvExcelColumnTitle = holder.getView<TextView>(R.id.tv_excel_column_title)
        tvExcelColumnTitle.isSelected = holder.adapterPosition == position
        tvExcelColumnTitle.text = item[0]
    }

    fun setExcelBg(position: Int) {
        this.position = position
        notifyDataSetChanged()
    }
}