package com.example.zhouyongquan_calculator

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * 历史记录仓库：内部使用内存缓存，并通过 SharedPreferences 持久化。
 *
 * 这样即使 Activity 被销毁、应用进程重启，历史记录也不会丢失。
 */
object HistoryStore {

    private const val PREFS_NAME = "calculator_history"
    private const val KEY_RECORDS = "records"

    private val records = mutableListOf<HistoryRecord>()
    private var preferences: SharedPreferences? = null

    /** 在 Activity 启动时调用，只加载一次。 */
    fun init(context: Context) {
        if (preferences != null) return
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        preferences = prefs
        records.clear()
        records.addAll(decode(prefs.getString(KEY_RECORDS, null)))
    }

    /** 新增一条记录，最新的记录排在最前面。 */
    fun add(record: HistoryRecord) {
        records.add(0, record)
        encode(records)
    }

    /** 返回按时间倒序排列的全部记录。 */
    fun all(): List<HistoryRecord> = records.toList()

    private fun encode(list: List<HistoryRecord>) {
        val array = JSONArray()
        list.forEach { record ->
            val item = JSONObject()
            item.put("first", record.first)
            item.put("operator", record.operator.code)
            item.put("second", record.second)
            item.put("result", record.result)
            item.put("time", record.time)
            array.put(item)
        }
        preferences?.edit()?.putString(KEY_RECORDS, array.toString())?.apply()
    }

    private fun decode(json: String?): List<HistoryRecord> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<HistoryRecord>()
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                result.add(
                    HistoryRecord(
                        first = item.getDouble("first"),
                        operator = item.getString("operator").firstOrNull() ?: '+',
                        second = item.getDouble("second"),
                        result = item.getDouble("result"),
                        time = item.getLong("time")
                    )
                )
            }
            result
        } catch (exception: JSONException) {
            emptyList()
        }
    }
}
