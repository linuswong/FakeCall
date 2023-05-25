package com.example.fakecall

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import xyz.aprildown.ultimateringtonepicker.RingtonePickerActivity.Companion.getPickerResult

class RingtonePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringtone_picker)


    }
    private val ringtoneLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                //val ringtones = RingtonePickerActivity.getPickerResult(data)
                //https://github.com/DeweyReed/UltimateRingtonePicker
            }
        }
}