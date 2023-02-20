package com.example.test001.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.test001.bean.ExcelBean
import com.example.test001.R

/**
 * 标题行
 */
class ExcelRowTitleAdapter :
    BaseQuickAdapter<ExcelBean, BaseViewHolder>(R.layout.item_excel_row_title, null) {
    override fun convert(holder: BaseViewHolder, item: ExcelBean) {
        holder.setText(R.id.tv_excel_row_title, item.title)
    }
}