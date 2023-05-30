package com.example.fakecall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakecall.databinding.ActivityCallAcceptScreenBinding

class CallAcceptScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCallAcceptScreenBinding
    private var callData = Call()
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
        }

        //JOPHNNSAGIJ RIGHTR  HERE DO THE THUING WITH THE RINGTONE
        binding.textViewCallScreenPerson.text="balls"


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