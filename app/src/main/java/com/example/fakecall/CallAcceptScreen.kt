package com.example.fakecall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakecall.databinding.ActivityCallAcceptScreenBinding

class CallAcceptScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCallAcceptScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCallAcceptScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.floatingActionButtonCallScreenAccept.setOnClickListener{

            val detailIntent= Intent(it.context,FakeCallScreen::class.java)
            startActivity(detailIntent)
        }
        binding.floatingActionButtonCallScreenDecline.setOnClickListener{
            val detailIntent= Intent(it.context,CallActivity::class.java)
            startActivity(detailIntent)
        }
    }
}