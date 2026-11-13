package com.example.zhouyongquan_calculator

/**
 * 一条计算历史记录：参与运算的两个数、运算符、结果以及发生时间。
 */
data class HistoryRecord(
    val first: Double,
    val operator: Char,
    val second: Double,
    val result: Double,
    val time: Long
) {

    /** 运算符在界面上显示的符号。 */
    val operatorSymbol: String
        get() = when (operator) {
            '*' -> "×"
            '/' -> "÷"
            '-' -> "−"
            else -> "+"
        }

    /** 生成历史列表中的展示文本，例如“3 + 5 = 8”。 */
    fun toExpression(): String {
        val firstText = NumberFormatter.format(first)
        val secondText = NumberFormatter.format(second)
        val resultText = NumberFormatter.format(result)
        return "$firstText $operatorSymbol $secondText = $resultText"
    }
}
