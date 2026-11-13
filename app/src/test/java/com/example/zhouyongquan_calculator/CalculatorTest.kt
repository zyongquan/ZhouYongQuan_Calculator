package com.example.zhouyongquan_calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * [Calculator] 的本地单元测试。
 */
class CalculatorTest {

    private val calculator = Calculator()

    @Test
    fun add_returnsSum() {
        assertEquals(5.0, calculator.add(2.0, 3.0), DELTA)
    }

    @Test
    fun subtract_returnsDifference() {
        assertEquals(-1.0, calculator.subtract(2.0, 3.0), DELTA)
    }

    @Test
    fun multiply_returnsProduct() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0), DELTA)
    }

    @Test
    fun divide_returnsQuotient() {
        assertEquals(2.5, calculator.divide(5.0, 2.0), DELTA)
    }

    @Test
    fun calculate_dispatchesByOperator() {
        assertEquals(7.0, calculator.calculate(3.0, 4.0, '+'), DELTA)
        assertEquals(-1.0, calculator.calculate(3.0, 4.0, '-'), DELTA)
        assertEquals(12.0, calculator.calculate(3.0, 4.0, '*'), DELTA)
        assertEquals(0.75, calculator.calculate(3.0, 4.0, '/'), DELTA)
    }

    @Test
    fun divide_byZero_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.divide(1.0, 0.0)
        }
    }

    @Test
    fun calculate_withUnknownOperator_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.calculate(1.0, 2.0, '%')
        }
    }

    private companion object {
        const val DELTA = 1e-9
    }
}
