package com.example.fakecall

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fakecall.databinding.FakeCallScreenBinding


class FakeCallScreen:AppCompatActivity() {

    private lateinit var binding: FakeCallScreenBinding
    private var thingy: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FakeCallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            binding.floatingActionButtonHangUp.setOnClickListener {
                val detailIntent = Intent(it.context, CallActivity::class.java)
                startActivity(detailIntent)
            }
        }


    }
}