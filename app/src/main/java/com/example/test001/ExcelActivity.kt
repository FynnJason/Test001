package com.example.test001

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.example.test001.adapter.ExcelColumnTitleAdapter
import com.example.test001.adapter.ExcelContentParentAdapter
import com.example.test001.adapter.ExcelRowTitleAdapter
import com.example.test001.bean.ExcelBean
import com.example.test001.databinding.ActivityExcelBinding
import com.example.test001.event.CommonCode
import com.example.test001.event.CommonEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.BufferedReader
import java.io.InputStreamReader

class ExcelActivity : AppCompatActivity() {

    private val mExcelRowTitleAdapter = ExcelRowTitleAdapter()
    private val mExcelColumnTitleAdapter = ExcelColumnTitleAdapter()
    private val mExcelContentParentAdapter = ExcelContentParentAdapter()
    private var mIsContentScroll = true
    private var mIsColumnTitleScroll = false
    private var mIsRowTitleScroll = false

    private lateinit var mBinding: ActivityExcelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExcelBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = "航海日志"
        EventBus.getDefault().register(this)
        val bean = JSON.parseArray(getData(), ExcelBean::class.java)

        mBinding.tvExcelRowTitle.text = bean[0].title
        mBinding.rvExcelRowTitle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvExcelRowTitle.adapter = mExcelRowTitleAdapter
        mExcelRowTitleAdapter.setNewInstance(bean)

        mBinding.rvExcelColumnTitle.layoutManager = LinearLayoutManager(this)
        mBinding.rvExcelColumnTitle.adapter = mExcelColumnTitleAdapter
        mExcelColumnTitleAdapter.setNewInstance(bean[0].content)

        mBinding.rvExcelContent.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvExcelContent.adapter = mExcelContentParentAdapter
        mExcelContentParentAdapter.setNewInstance(bean)

        initScrollListener()
        initRvClickListener()
    }

    private fun getData(): String {
        val am = assets
        val isr = InputStreamReader(am.open("excel.json"))
        val br = BufferedReader(isr)
        val sb = StringBuilder()
        try {
            var line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
        } catch (e: Exception) {
            Log.d("ExcelActivity", e.message ?: "unknown")
        }
        br.close()
        isr.close()
        return sb.toString()
    }

    private fun initScrollListener() {
        mBinding.rvExcelContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsContentScroll) {
                    mBinding.rvExcelRowTitle.scrollBy(dx, dy)
                }
            }
        })

        mBinding.rvExcelColumnTitle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsColumnTitleScroll) {
                    mBinding.scrollExcelContent.scrollBy(dx, dy)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 0) {
                    resetScroll()
                }
            }
        })

        mBinding.rvExcelRowTitle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsRowTitleScroll) {
                    mBinding.rvExcelContent.scrollBy(dx, dy)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 0) {
                    resetScroll()
                }
            }
        })

        mBinding.scrollExcelContent.setScrollListener { l, t, oldl, oldt ->
            if (mIsContentScroll) {
                mBinding.rvExcelColumnTitle.scrollBy(0, t - oldt);
            }
        }

        mBinding.rvExcelContent.setOnTouchListener { v, event ->
            mIsContentScroll = true
            mIsColumnTitleScroll = false
            mIsRowTitleScroll = false
            false
        }

        mBinding.rvExcelColumnTitle.setOnTouchListener { v, event ->
            mIsContentScroll = false
            mIsColumnTitleScroll = true
            mIsRowTitleScroll = false
            false
        }

        mBinding.rvExcelRowTitle.setOnTouchListener { v, event ->
            mIsContentScroll = false
            mIsColumnTitleScroll = false
            mIsRowTitleScroll = true
            false
        }
    }

    private fun initRvClickListener() {
        mExcelColumnTitleAdapter.setOnItemClickListener { adapter, view, position ->
            mExcelColumnTitleAdapter.setExcelBg(position)
            mExcelContentParentAdapter.setLineExcelBg(position)
        }
    }

    private fun resetScroll() {
        mIsContentScroll = true
        mIsColumnTitleScroll = false
        mIsRowTitleScroll = false
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventMessage(event:CommonEvent) {
        if (event.code == CommonCode.SET_EXCEL_BG){
            mExcelColumnTitleAdapter.setExcelBg(event.msg.toString().toInt())
        }
    }
}