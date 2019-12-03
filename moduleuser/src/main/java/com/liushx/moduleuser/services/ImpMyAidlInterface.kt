package com.liushx.moduleuser.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.liushx.corelibrary.IMyAidlInterface
import java.util.logging.Logger

class ImpMyAidlInterface : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return sub
    }

    val sub = object : IMyAidlInterface.Stub() {
        override fun getName(): String {
            return "这是AIDL返回的名称:LoginModule"
        }
    }
}