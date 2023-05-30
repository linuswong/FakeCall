package com.example.fakecall

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.FragmentHomeBinding
import com.example.fakecall.databinding.FragmentSettingsBinding
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.DurationUnit

private lateinit var binding:FragmentHomeBinding

class Home : Fragment() {
    private var callData = Call()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.floatingActionButtonHomeStartCall.setOnClickListener {
            val detailIntent= Intent(it.context,CallAcceptScreen::class.java)
            startActivity(detailIntent)
        }
        retrieveAllData()
        return binding.root
    }

    private fun retrieveAllData(){
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.setSortBy( "callTime DESC" )
        Backendless.Data.of(Call::class.java).find(queryBuilder,object :
            AsyncCallback<List<Call?>?> {
            override fun handleResponse(foundLoan: List<Call?>?) {
                // all Contact instances have been found
                Log.d("Scheduling", "foundScheduleThing: $foundLoan")
                var wong = ArrayList<Call>()
                if (foundLoan != null) {
                    if(foundLoan.isNotEmpty()){
                        val callList = foundLoan as List<*>
                        callList.forEach { Call -> wong.add(Call as Call) }
                        callData = callList.first() as Call
                        println(callData.callTime.time.toInt())
                        println("Current Date: " + Calendar.getInstance().time)
                        println(callData.callTime.time.toInt() - Calendar.getInstance().timeInMillis.toInt())
                        countDown((callData.callTime.time.toInt() - Calendar.getInstance().timeInMillis.toInt()).toLong())
                    }
                }
            }


            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("Scheduling","handleFault: ${fault.message}")
            }
        })
    }

    private fun countDown(i: Long){
        object : CountDownTimer(i, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                binding.textViewHomeTimeUntilCall.setText("Time until next scheduled call:\nseconds remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                binding.textViewHomeTimeUntilCall.setText("done!")
                //no
            }
        }.start()
    }
}