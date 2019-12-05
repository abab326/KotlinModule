package com.liushx.corelibrary.utils

import android.util.Base64

/**
 * BASE64 加密
 */
class Base64Utils {

    companion object {

        /**
         * 将加密字符串解密为字符串格式
         *
         * @param encryptString 加密字符串
         * @return 返回解密后的字符串
         */
        @Throws(IllegalArgumentException::class)
        fun decodeToString(encryptString: String): String {
            return String(decodeToArray(encryptString), charset("utf-8"))
        }

        /**
         * B将加密字节数组解密为字符串
         *
         * @param encryptArray 加密字节数组
         * @return 返回解密后的字符串
         */
        @Throws(IllegalArgumentException::class)
        fun decodeToString(encryptArray: ByteArray): String {
            return String(decodeToArray(encryptArray), charset("utf-8"))
        }

        /**
         * 将加密的字符串解密为字节数组
         *
         * @param encryptString 加密字符串
         * @return 解密后的字节数组
         */
        @Throws(IllegalArgumentException::class)
        fun decodeToArray(encryptString: String): ByteArray {
            val byteArray = encryptString.toByteArray(charset("utf-8"))
            return decodeToArray(byteArray)
        }

        /**
         * 将加密的字节数组解密为字节数组
         *
         * @param encryptArray 加密字节数组
         *
         * @return 解密后的字符数组
         */
        @Throws(IllegalArgumentException::class)
        fun decodeToArray(encryptArray: ByteArray): ByteArray {

            return Base64.decode(encryptArray, Base64.DEFAULT)
        }

        /**
         * 将字符串进行base64加密
         *
         * @param string 待加密内容
         *
         * @return 加密后的字符串
         *
         */
        fun encodeToString(string: String): String {

            return String(encodeToArray(string), charset("utf-8"))
        }

        /**
         * 将字节数组进行base64加密
         *
         * @param array 待加密内容
         *
         * @return 加密后的字符串
         *
         */
        fun encodeToString(array: ByteArray): String {
            return String(encodeToArray(array), charset("utf-8"))
        }

        /**
         * 将字符串进行base64加密
         *
         * @param string 待加密内容
         *
         * @return 加密后的字节数组
         *
         */
        fun encodeToArray(string: String): ByteArray {
            val byteArray = string.toByteArray(charset("utf-8"))
            return Base64.encode(byteArray, Base64.DEFAULT)

        }

        /**
         * 将字节数组进行base64加密
         *
         * @param array 待加密内容
         *
         * @return 加密后的字节数组
         *
         */
        fun encodeToArray(array: ByteArray): ByteArray {

            return Base64.encode(array, Base64.DEFAULT)

        }
    }
}