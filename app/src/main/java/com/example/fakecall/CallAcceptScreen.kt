package com.example.fakecall

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.fakecall.databinding.ActivityCallAcceptScreenBinding

class CallAcceptScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCallAcceptScreenBinding
    private var callData = Call()
    private var player = MediaPlayer()
    companion object{
        val EXTRA_SCHEDULEDCALL = "I hope you trip on a rock Alan"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCallAcceptScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getParcelableExtra<Call>(EXTRA_SCHEDULEDCALL) == null) {
            callData = Call()
        } else {
            callData = intent.getParcelableExtra<Call>(EXTRA_SCHEDULEDCALL) ?: Call()
            if(callData.ringtone.isNotEmpty())
            {
                player = MediaPlayer.create(this@CallAcceptScreen,callData.ringtone.toUri())
                player.start()

            }
        }

        //JOPHNNSAGIJ RIGHTR  HERE DO THE THUING WITH THE RINGTONE
        binding.textViewCallScreenPerson.text= callData.caller

        //Accept
        binding.floatingActionButtonCallScreenAccept.setOnClickListener{
            if(player.isPlaying){player.stop()}
            val detailIntent= Intent(it.context,FakeCallScreen::class.java).apply{
                putExtra(EXTRA_SCHEDULEDCALL, callData)
            }
            startActivity(detailIntent)
        }
        //Decline
        binding.floatingActionButtonCallScreenDecline.setOnClickListener{
            if(player.isPlaying){player.stop()}
            val detailIntent= Intent(it.context,CallActivity::class.java)
            startActivity(detailIntent)
        }
    }
}