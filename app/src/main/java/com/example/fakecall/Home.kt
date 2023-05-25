package com.example.fakecall

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakecall.databinding.FragmentHomeBinding
import com.example.fakecall.databinding.FragmentSettingsBinding

private lateinit var binding:FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.buttonInstantCallHome.setOnClickListener {
            val detailIntent= Intent(it.context,CallAcceptScreen::class.java)
            startActivity(detailIntent)
        }

        return binding.root
    }


}