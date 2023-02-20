package com.example.test001.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.test001.R
import com.example.test001.bean.ExcelBean
import com.example.test001.event.CommonCode
import com.example.test001.event.EventBusUtils
import com.example.test001.widget.EditValueDialog

/**
 * 内容容器
 */
class ExcelContentParentAdapter :
    BaseQuickAdapter<ExcelBean, BaseViewHolder>(R.layout.item_excel_content_parent, null) {
    private val childAdapterList = mutableListOf<ExcelContentChildAdapter>()
    private var position = -1
    override fun convert(holder: BaseViewHolder, item: ExcelBean) {
        val rvContentParent = holder.getView<RecyclerView>(R.id.rv_excel_content_parent)
        val contentChildAdapter = ExcelContentChildAdapter()
        rvContentParent.layoutManager = LinearLayoutManager(context)
        rvContentParent.adapter = contentChildAdapter
        contentChildAdapter.addChildLongClickViewIds(
            R.id.tv_excel_content_top,
            R.id.tv_excel_content_bottom
        )
        contentChildAdapter.addChildClickViewIds(
            R.id.tv_excel_content_top,
            R.id.tv_excel_content_bottom
        )
        contentChildAdapter.setOnItemChildClickListener { adapter, view, position ->
            for (cal in childAdapterList) {
                this.position = position
                cal.setExcelBg(position)
            }
            EventBusUtils.post(CommonCode.SET_EXCEL_BG, "$position")
        }
        contentChildAdapter.setOnItemChildLongClickListener { adapter, view, position ->
            if (view.id == R.id.tv_excel_content_top) {
                val dialog = EditValueDialog(context, contentChildAdapter.data[position][0])
                dialog.show()
                dialog.setOnValueListener(object : EditValueDialog.OnValueCallback {
                    override fun getNewValue(newValue: String) {
                        contentChildAdapter.data[position][0] = newValue
                        notifyDataSetChanged()
                    }
                })
            } else if (view.id == R.id.tv_excel_content_bottom) {
                val dialog = EditValueDialog(context, contentChildAdapter.data[position][1])
                dialog.show()
                dialog.setOnValueListener(object : EditValueDialog.OnValueCallback {
                    override fun getNewValue(newValue: String) {
                        contentChildAdapter.data[position][1] = newValue
                        notifyDataSetChanged()
                    }
                })
            }
            true
        }
        contentChildAdapter.setNewInstance(item.content)
        contentChildAdapter.setExcelBg(position)
        childAdapterList.add(contentChildAdapter)
    }

    fun setLineExcelBg(position: Int) {
        for (cal in childAdapterList) {
            cal.setExcelBg(position)
        }
    }
}