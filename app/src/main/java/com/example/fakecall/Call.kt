package com.example.fakecall

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Call(
    var caller: String = "Caller name not set",
    var callTimeText: String = "Call-time not set",
    var ringtoneText: String = "Ringtone not set",
    var callerAudioText: String = "Caller Audio not set",
    var callTime: Date = Date(0),
    var isActive: Boolean = false,
    var isDefault: Boolean = true,
    var objectId: String? = null,
    var ownerId: String?= null,
    var ringtone: String?= null,
    val area: String= ""
) : Parcelable
