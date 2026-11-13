package com.example.zhouyongquan_calculator

import java.util.Locale

/**
 * 计算结果格式化工具：把 double 转成便于阅读的字符串。
 *
 * 例如 0.30000000000000004 会显示为 0.3，5.0 会显示为 5。
 */
object NumberFormatter {

    /** 最多保留 10 位小数，并去掉多余的 0。 */
    fun format(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "错误"
        var text = String.format(Locale.US, "%.10f", value)
        if (text.contains('.')) {
            text = text.trimEnd('0').trimEnd('.')
        }
        return when (text) {
            "", "-0", "-" -> "0"
            else -> text
        }
    }
}
