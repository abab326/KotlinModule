package com.liushx.corelibrary.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.liushx.corelibrary.base.BaseApplication
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesUtils {

    companion object {

        private val sharedName = "shared_name_2019"
        fun put(key: String, value: Any, sharedName: String = this.sharedName) {
            val sharedPreferences =
                BaseApplication.getContext().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
            when (value) {
                is Int -> sharedPreferences.edit().putInt(key, value).apply()
                is String -> sharedPreferences.edit().putString(key, value).apply()
                is Long -> sharedPreferences.edit().putLong(key, value).apply()
                is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            }

        }

        fun <T> get(key: String, default: T, sharedName: String = this.sharedName): T {
            val sharedPreferences =
                BaseApplication.getContext().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
            val returnValue: Any? = when (default) {
                is Int -> sharedPreferences.getInt(key, default)
                is String -> sharedPreferences.getString(key, default)
                is Long -> sharedPreferences.getLong(key, default)
                is Boolean -> sharedPreferences.getBoolean(key, default)
                else -> deSerialization<T>(sharedPreferences.getString(key, serialize(default))!!)
            }
            if (returnValue != null) {
                return returnValue as T
            }
            return default
        }


        /**
         * 序列化对象

         * @param person
         * *
         * @return
         * *
         * @throws IOException
         */
        @Throws(IOException::class)
        private fun <A> serialize(obj: A): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream
            )
            objectOutputStream.writeObject(obj)
            var serStr = byteArrayOutputStream.toString("ISO-8859-1")
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
            objectOutputStream.close()
            byteArrayOutputStream.close()
            return serStr
        }

        /**
         * 反序列化对象

         * @param str
         * *
         * @return
         * *
         * @throws IOException
         * *
         * @throws ClassNotFoundException
         */
        @Throws(IOException::class, ClassNotFoundException::class)
        private fun <A> deSerialization(str: String): A {
            val redStr = java.net.URLDecoder.decode(str, "UTF-8")
            val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1"))
            )
            val objectInputStream = ObjectInputStream(
                byteArrayInputStream
            )
            val obj = objectInputStream.readObject() as A
            objectInputStream.close()
            byteArrayInputStream.close()
            return obj
        }

    }
}