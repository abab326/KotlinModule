package com.liushx.corelibrary.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtils {
    /**
     * 日期格式化类型
     *
     */
    enum class DatePattern {
        ALL {
            override fun getValue(): String {
                return "yyyy-MM-dd HH:mm:ss"
            }
        },
        YMD {
            override fun getValue(): String {
                return "yyyy-MM-dd"
            }
        },
        MD {
            override fun getValue(): String {
                return "MM-dd"
            }
        },
        HMS {
            override fun getValue(): String {
                return "HH:mm:ss"
            }
        },
        HM {
            override fun getValue(): String {
                return "HH:mm"
            }
        };

        abstract fun getValue(): String
    }

    companion object {

        /**
         * 格式化 string 为 date
         */
        fun parse(date: String, pattern: DatePattern = DatePattern.ALL): Date? {
            var dateString = date
            if (date.isEmpty())
                return null
            if (date.contains("T"))
                dateString = date.replace("T", " ")
            return try {
                val dateFormat = SimpleDateFormat(pattern.getValue(), Locale.getDefault())
                dateFormat.parse(dateString)
            } catch (e: IllegalArgumentException) {
                null
            } catch (e: ParseException) {

                null
            }
        }

        /**
         * 将Date格式化为指定格式的String
         *
         */
        fun format(date: Date, pattern: DatePattern = DatePattern.ALL): String? {

            return try {
                val dateFormat = SimpleDateFormat(pattern.getValue(), Locale.getDefault())
                dateFormat.format(date)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

    }

}