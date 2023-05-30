package com.example.fakecall

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fakecall.databinding.FakeCallScreenBinding


class FakeCallScreen:AppCompatActivity() {

    private lateinit var player : MediaPlayer
    private lateinit var binding: FakeCallScreenBinding
    private var thingy: Boolean = false
    private var thangy:Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FakeCallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //player = MediaPlayer.create(this, R.raw.animal_crossing)
        binding.textView7.text="balls"

        binding.floatingActionButtonHangUp.setOnClickListener {
            val detailIntent = Intent(it.context, CallActivity::class.java)
            startActivity(detailIntent)
        }
        binding.floatingActionButtonMuteFakeCallScreen.setOnClickListener {
            var mute = binding.floatingActionButtonMuteFakeCallScreen
            if (!thingy) {
                mute.getBackground().mutate()
                    .setTint(ContextCompat.getColor(it.context, R.color.white))
                thingy = true
            } else if (thingy) {
                mute.getBackground().mutate()
                    .setTint(ContextCompat.getColor(it.context, R.color.balls))
                thingy = false
            }
        }
        binding.floatingActionButtonSpeakerFakeCallScreen.setOnClickListener {
            var mute = binding.floatingActionButtonSpeakerFakeCallScreen
            if (!thangy) {
                mute.getBackground().mutate()
                    .setTint(ContextCompat.getColor(it.context, R.color.white))
                thangy = true
            } else if (thangy) {
                mute.getBackground().mutate()
                    .setTint(ContextCompat.getColor(it.context, R.color.balls))
                thangy = false

            }

        }


    }
}