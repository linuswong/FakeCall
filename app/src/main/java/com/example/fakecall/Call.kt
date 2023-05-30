package com.example.fakecall

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Call(
    var caller: String = "Caller name not set",
    var callTimeText: String = "Call-time not set",
    var ringtone: String= "",
    var ringtoneText: String = "Ringtone not set",
    var callerAudioText: String = "Caller Audio not set",
    var callTime: Date = Date(0),
    //made string because backendless is weird
    var defaultSettings: String = "False",
    var objectId: String? = null,
    var ownerId: String?= null,
) : Parcelable
