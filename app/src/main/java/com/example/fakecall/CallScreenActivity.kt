package com.example.fakecall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakecall.databinding.ActivityCallScreenBinding


class CallScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)






    }
}