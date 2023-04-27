package com.example.fakecall

import android.media.Ringtone
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Call(
    val caller: String = "Linus",
    val callTime: Int = 0,
    val isActive: Boolean = false,
    val notifications: NotificationSettings,
) : Parcelable {

    @Parcelize
    data class NotificationSettings(
    val scheduledCall: Date,
    val ringTone: Int,
):Parcelable

}
