package com.example.fakecall

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.ActivityCallDetailBinding
import xyz.aprildown.ultimateringtonepicker.RingtonePickerDialog
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker
import java.util.*

//duplicate of settings
class CallDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallDetailBinding
    private var m_Text = ""
    private var callData = Call()


    companion object {
        val EXTRA_CALL = "WHY DID U NOT DO THIS AHHHHHH"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getParcelableExtra<Call>(EXTRA_CALL) == null) {
            callData = Call()
        } else {
            callData = intent.getParcelableExtra<Call>(EXTRA_CALL) ?: Call()
        }
        initializeButtonsAndStuff()
        saveSettings()
        showCallerNameOptions()
        showCallTimeOptions()
        showRingToneOptions()
        showCallerAudioOptions()
    }

    private fun showCallerNameOptions(){
        binding.buttonSettingsSetCallerName.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Set Caller Name")
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton(
                "OK"
            ) { dialog, which -> m_Text = input.text.toString()
                binding.textViewSettingsCallerName.text = m_Text
                callData.caller = m_Text

            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }

            builder.show()
        }
    }

    //popup for setting call time
    private fun showCallTimeOptions(){
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                binding.textViewSettingsCallTime.setText(String.format("%d : %d", hourOfDay, minute))
                mcurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                mcurrentTime.set(Calendar.MINUTE, minute)
                callData.callTime = mcurrentTime.time
                callData.callTimeText = binding.textViewSettingsCallTime.text.toString()
            }
        }, hour, minute, false)
        binding.buttonSettingsSetCallTime.setOnClickListener {
            mTimePicker.show()
        }
    }
    //crashes if ringtone is not picked
    //pop up for ringtone thing
    private fun showRingToneOptions(){
        val settings = UltimateRingtonePicker.Settings(
            systemRingtonePicker = UltimateRingtonePicker.SystemRingtonePicker(
                customSection = UltimateRingtonePicker.SystemRingtonePicker.CustomSection(),
                defaultSection = UltimateRingtonePicker.SystemRingtonePicker.DefaultSection(),
                ringtoneTypes = listOf(
                    RingtoneManager.TYPE_RINGTONE,
                    RingtoneManager.TYPE_NOTIFICATION,
                    RingtoneManager.TYPE_ALARM
                )
            ),
            deviceRingtonePicker = UltimateRingtonePicker.DeviceRingtonePicker(
                deviceRingtoneTypes = listOf(
                    UltimateRingtonePicker.RingtoneCategoryType.All,
                    UltimateRingtonePicker.RingtoneCategoryType.Artist,
                    UltimateRingtonePicker.RingtoneCategoryType.Album,
                    UltimateRingtonePicker.RingtoneCategoryType.Folder
                )
            )
        )
        binding.buttonSettingsSetRingtone.setOnClickListener {
            RingtonePickerDialog.createEphemeralInstance(
                settings = settings,
                dialogTitle = "Select Ringtone",
                listener = object : UltimateRingtonePicker.RingtonePickerListener {
                    override fun onRingtonePicked(ringtones: List<UltimateRingtonePicker.RingtoneEntry>) {
                        println(ringtones)
                        callData.ringtone = ringtones.get(0).uri.toString()
                        callData.ringtoneText = ringtones.get(0).name
                        binding.textViewSettingsRingtone.text = callData.ringtoneText
                        //                        val player = MediaPlayer.create(this@Settings.context, ringtones.get(0).uri )
//                        player.start()
                        //code to play the sounds

                    }
                }
            ).show(this.supportFragmentManager, null)


        }
    }
    //Linus do this
    private fun showCallerAudioOptions(){
        binding.buttonSettingsCallerVoice.setOnClickListener {
            val colors = arrayOf("red", "green", "blue", "black")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pick a color")
            builder.setItems(colors) { dialog, which ->
                // the user clicked on colors[which]
            }
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton(
                "OK"
            ) { dialog, which ->

            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }
            builder.show()
        }
    }

    private fun initializeButtonsAndStuff(){
        binding.textViewSettingsCallerName.text = callData.caller
        binding.textViewSettingsCallTime.text = callData.callTimeText
        binding.textViewSettingsRingtone.text = callData.ringtoneText
        binding.textViewSettingsCallerAudioText.text = callData.callerAudioText
    }

    //saves the current settings as the default to backendless
    private fun saveSettings(){
        callData.defaultSettings = "False"
        binding.buttonSettingsSaveDefault.setOnClickListener {
            callData.caller = binding.textViewSettingsCallerName.text.toString()
            //pasted code from login and registration
            fun updateCall() {
                Backendless.Data.of(Call::class.java).save(callData, object : AsyncCallback<Call> {
                    override fun handleResponse(response: Call?) {
                        // Contact instance has been updated
                        Log.d("SAVE SETTINGS","handle response: $response")
                        Toast.makeText(this@CallDetailActivity, "Settings successfully saved", Toast.LENGTH_SHORT).show()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d("SAVE SETTINGS","HandleFault : $fault")
                    }
                })
            }
            updateCall()
        }
    }
}