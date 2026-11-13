package com.example.zhouyongquan_calculator

/**
 * 计算类：完成两个数的加、减、乘、除运算。
 *
 * 该类只负责数学运算，不处理界面与结果格式化，便于单独进行单元测试。
 */
class Calculator {

    /**
     * 根据运算符 [operator] 计算 [first] 与 [second] 的结果。
     *
     * @throws IllegalArgumentException 运算符不受支持，或者除数为 0。
     */
    fun calculate(first: Double, second: Double, operator: Char): Double = when (operator) {
        '+' -> add(first, second)
        '-' -> subtract(first, second)
        '*' -> multiply(first, second)
        '/' -> divide(first, second)
        else -> throw IllegalArgumentException("不支持的运算符：$operator")
    }

    /** 加法。 */
    fun add(first: Double, second: Double): Double = first + second

    /** 减法。 */
    fun subtract(first: Double, second: Double): Double = first - second

    /** 乘法。 */
    fun multiply(first: Double, second: Double): Double = first * second

    /**
     * 除法。
     *
     * @throws IllegalArgumentException 除数为 0。
     */
    fun divide(first: Double, second: Double): Double {
        require(second != 0.0) { "除数不能为 0" }
        return first / second
    }
}
