package com.example.zhouyongquan_calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 历史记录列表适配器，配合 RecyclerView 使用。
 */
class HistoryAdapter : ListAdapter<HistoryRecord, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(itemView, timeFormat)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(
        itemView: View,
        private val timeFormat: SimpleDateFormat
    ) : RecyclerView.ViewHolder(itemView) {

        private val expressionView: TextView = itemView.findViewById(R.id.itemExpression)
        private val timeView: TextView = itemView.findViewById(R.id.itemTime)

        fun bind(record: HistoryRecord) {
            expressionView.text = record.toExpression()
            timeView.text = timeFormat.format(Date(record.time))
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryRecord>() {
            override fun areItemsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord): Boolean =
                oldItem.time == newItem.time

            override fun areContentsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord): Boolean =
                oldItem == newItem
        }
    }
}
