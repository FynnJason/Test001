package com.example.test001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test001.databinding.ActivitySortBinding
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import java.util.regex.Pattern

/**
 * 排序题
 */
class SortActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySortBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSortSure.setOnClickListener {
            val str = binding.etSort.text.toString()
            val arr = str.split(",").toMutableList()
            val map = mutableMapOf<String, String>()
            for (i in arr.indices) {
                if (isChineseString(arr[i])) {
                    val ctl = chineseToLetter(arr[i])
                    map[ctl] = arr[i]
                    arr[i] = chineseToLetter(arr[i])
                }
            }
            arr.sort()
            for (i in arr.indices) {
                map[arr[i]]?.let {
                    arr[i] = it
                }
            }
            binding.tvOutput.text = arr.toList().toString()
        }
    }

    private fun isChineseString(string: String): Boolean {
        val p = Pattern.compile("[\u4e00-\u9fa5]")
        val m = p.matcher(string)
        if (m.find()) {
            return true
        }
        return false
    }

    private fun chineseToLetter(string: String): String {
        var py = ""
        val ca = string.toCharArray()
        val df = HanyuPinyinOutputFormat()
        df.caseType = HanyuPinyinCaseType.LOWERCASE
        df.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (c in ca) {
            py += PinyinHelper.toHanyuPinyinStringArray(c, df)[0]
        }
        return py
    }
}