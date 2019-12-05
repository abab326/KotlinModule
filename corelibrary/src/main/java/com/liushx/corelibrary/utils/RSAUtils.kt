package com.liushx.corelibrary.utils

import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException

/**
 * RSA 加密算法
 *   RSA 为一种非对称加密算法
 *   密钥分公钥、私钥,在使用过程中 一般是用公钥进行加密，私钥进行解密
 *
 */
class RSAUtils {

    companion object {
        private val RSA = "RSA/ECB/PKCS1Padding"

        /**
         *
         * 生成公钥和私钥
         *
         */
        fun generateKeys(): HashMap<String, String> {

            val map = HashMap<String, String>()
            val keyPairGen: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGen.initialize(1024)
            val keyPair: KeyPair = keyPairGen.generateKeyPair()
            val publicKey = keyPair.public as RSAPublicKey
            val privateKey = keyPair.private as RSAPrivateKey
            map["public"] = Base64Utils.encodeToString(publicKey.encoded)
            map["private"] = Base64Utils.encodeToString(privateKey.encoded)
            return map

        }

        /**
         * 公钥加密
         */


        fun encryptPublic(content: String, publicKeyString: String): String {
            val decoded: ByteArray = Base64Utils.decodeToArray(publicKeyString)
            val publicKey =
                KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey

            val cipher = Cipher.getInstance(RSA)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return Base64Utils.encodeToString(cipher.doFinal(content.toByteArray(charset("utf-8"))))

        }

        fun decryptPrivate(encryptString: String, privateKeyString: String): String {
            val decoded: ByteArray = Base64Utils.decodeToArray(privateKeyString)
            val inputByte: ByteArray = Base64Utils.decodeToArray(encryptString)
            val privateKey =
                KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(decoded)) as RSAPrivateKey

            val cipher = Cipher.getInstance(RSA)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)

            return String(cipher.doFinal(inputByte), charset("utf-8"))
        }
    }
}