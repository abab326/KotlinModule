package com.liushx.corelibrary.utils

/**
 * 排序方法
 */
object SortUtil {

    /**
     * 冒泡排序算法
     */
    fun bubbleSort(data: Array<Int>) {
        if (data.isNullOrEmpty() || data.size < 2)
            return
        val size = data.size
        var temp: Int

        for (i in 0 until size) {

            for (j in 1 until size - i) {
                if (data[j - 1] > data[j]) {
                    temp = data[j - 1]
                    data[j - 1] = data[j]
                    data[j] = temp
                }
            }

        }
    }

    /**
     * 快速排序
     */
    fun quickSort(data: Array<Int>, start: Int, end: Int) {

        if (start < end) {
            val middle = getMiddleIndex(data, start, end)
            quickSort(data, start, middle - 1)
            quickSort(data, middle + 1, end)
        }
    }

    /**
     * 返回中轴位置
     */
    fun getMiddleIndex(data: Array<Int>, start: Int, end: Int): Int {
        val temp = data[start]
        var startIndex = start
        var endIndex = end
        while (startIndex < endIndex) {
            while (startIndex < endIndex && temp <= data[endIndex]) {
                endIndex--
            }
            data[startIndex] = data[endIndex]
            while (startIndex < endIndex && temp > data[startIndex]) {
                startIndex++
            }
            data[endIndex] = data[startIndex]
        }
        data[startIndex] = temp
        return startIndex
    }

    /**
     * 选择排序
     * 每次选出最小的数的位置与未排序的第一个位置交换
     */
    fun selectSort(data: Array<Int>) {
        if (data.isNullOrEmpty() || data.size < 2)
            return
        val size = data.size
        var temp: Int
        for (i in 0 until size) {
            var minIndex = i
            for (j in i + 1 until size) {
                if (data[j] < data[minIndex]) {
                    minIndex = j
                }
            }
            temp = data[i]
            data[i] = data[minIndex]
            data[minIndex] = temp
        }
    }

    /**
     * 插入排序
     */
    fun insertSort(numbers: IntArray) {
        val size = numbers.size
        for (i in 0 until size) {
            val temp = numbers[i]
            var index = i
            while (index > 0 && temp < numbers[index - 1]) {
                numbers[index] = numbers[index - 1]
                index--
            }
            numbers[index] = temp
        }
    }


}