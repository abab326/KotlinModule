package com.liushx.corelibrary.utils

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*


class NumberFormatUtils {

    companion object {

        /**
         * 格式化数值为指定小数
         * @param value 待格式化数值
         * @param decimal 小数位位数 默认为2位
         * @return 返回格式化后的结果 string 类型
         */
        fun format(value: Double, decimal: Int = 2): String? {
            return try {
                val numberFormat = NumberFormat.getInstance()
                numberFormat.isParseIntegerOnly = true
                numberFormat.minimumFractionDigits = decimal
                numberFormat.maximumFractionDigits = decimal
                numberFormat.roundingMode = RoundingMode.HALF_UP
                numberFormat.format(value)
            } catch (e: ArithmeticException) {
                null
            }

        }

        /**
         * 格式化数值为指定小数
         * @param value 待格式化数值
         * @param decimal 小数位位数 默认为2位
         * @return 返回格式化后的结果 string 类型
         */
        fun format(value: String, decimal: Int = 2): String? {
            return try {
                val bigDecimal = BigDecimal(value, MathContext(decimal, RoundingMode.HALF_UP))

                bigDecimal.toString()
            } catch (e: NumberFormatException) {
                null
            } catch (e: ArithmeticException) {
                null
            }

        }

        /**
         * 精确加法计算
         */
        fun accurateAdd(value: String, value2: String): String? {
            return try {
                val bigDecimal = BigDecimal(value)
                val bigDecimal2 = BigDecimal(value2)
                val result = bigDecimal.plus(bigDecimal2)
                result.toString()
            } catch (e: NumberFormatException) {
                null
            }

        }

        /**
         *
         * 精确减法计算
         *
         */
        fun accurateSubtract(value: String, value2: String): String? {
            return try {
                val bigDecimal = BigDecimal(value)
                val bigDecimal2 = BigDecimal(value2)
                val result = bigDecimal.subtract(bigDecimal2)
                result.toString()
            } catch (e: NumberFormatException) {
                null
            }

        }

    }
}