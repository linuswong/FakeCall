package com.example.fakecall
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fakecall.databinding.FakeCallScreenBinding
import kotlinx.coroutines.Dispatchers.Main

class FakeCallScreen:AppCompatActivity() {

    private lateinit var binding: FakeCallScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FakeCallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.floatingActionButtonMuteFakeCallScreen.setOnClickListener{
            var mute=binding.floatingActionButtonMuteFakeCallScreen

            mute.setBackgroundColor(0)
        }
        binding.floatingActionButtonHangUp.setOnClickListener{
            val detailIntent= Intent(it.context,CallActivity::class.java)
            startActivity(detailIntent)


        }
    }


}