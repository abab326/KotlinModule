package com.liushx.corelibrary.utils

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class AESUtils {


    companion object {
        private const val AES = "AES/CBC/PKCS5Padding"
        private const val password = "asdfghjklqwertyu"
        private const val ivParameter = "1234567887654321"
        /**
         * AES加密,返回BASE64编码后的加密字符串
         * @param sSrc 待加密字符串
         * @return 返回Aes 加密后 base64位加密字符串
         * @throws Exception
         *
         */
        @Throws(Exception::class)
        fun aesEncrypt(sSrc: String): String {
            val cipher = Cipher.getInstance(AES)
            val raw = password.toByteArray(charset("utf-8"))
            val skeySpec = SecretKeySpec(raw, "AES")
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            val iv = IvParameterSpec(ivParameter.toByteArray(charset("utf-8")))
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(sSrc.toByteArray(charset("utf-8")))
            //此处使用BASE64做转码。
            return Base64Utils.encodeToString(encrypted)
        }

        /**
         * AES解密
         * @param sSrc -- 待解密Base64字符串
         * @return 解密后的字符串
         * @throws Exception
         */
        @Throws(Exception::class)
        fun aesDecrypt(sSrc: String): String {
            val raw = password.toByteArray(charset("utf-8"))
            val skeySpec = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance(AES)
            val iv = IvParameterSpec(ivParameter.toByteArray(charset("utf-8")))
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val encrypted1: ByteArray = Base64Utils.decodeToArray(sSrc)
            val original = cipher.doFinal(encrypted1)
            return String(original, charset("utf-8"))
        }

    }
}