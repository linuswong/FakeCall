package com.example.fakecall

import android.media.Ringtone
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Call(
    var caller: String = "Caller name not set",
    var callTime: Int = 0,
    var isActive: Boolean = false,
    var isDefault: Boolean = true,
    var objectId: String? = null,
    var ownerId: String?= null,
//    val notifications: NotificationSettings,
    val area: String= ""
) : Parcelable {

    @Parcelize
    data class NotificationSettings(
    val scheduledCall: Date,
    val ringTone: Int,
):Parcelable

}
