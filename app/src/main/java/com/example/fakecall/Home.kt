package com.example.fakecall

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.FragmentHomeBinding
import java.util.*
import kotlin.collections.ArrayList

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

        //Big Green Button
        binding.floatingActionButtonHomeStartCall.setOnClickListener {
            retrieveAllData()
            val detailIntent= Intent(it.context,CallAcceptScreen::class.java)
            startActivity(detailIntent)
        }
        retrieveAllDataScheduled()
        return binding.root
    }

    private fun retrieveAllDataScheduled(){
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.setSortBy( "callTime DESC", "isDefault = 'False'" )
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
                val detailIntent= Intent(this@Home.context,CallAcceptScreen::class.java).apply{
                putExtra(CallAcceptScreen.EXTRA_SCHEDULEDCALL, callData)
                }
                startActivity(detailIntent)
                deleteFromBackendless(callData)
            }
        }.start()
    }

    private fun deleteFromBackendless(callData: Call) {
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete ${callData}")
        // put in the code to delete the item using the callback from Backendless
        Backendless.Data.of(Call::class.java).remove(callData,
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Contact has been deleted. The response is the
                    Log.d("LoginActivity","handleResponse: $response")
                    // time in milliseconds when the object was deleted
                }

                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be
                    Log.d("LoginActivity","handleFault: ${fault.message}")
                    // retrieved with fault.getCode()
                }
            })
        // in the handleResponse, we'll need to also delete the item from the loanList
        // and make sure that the recyclerview is updated
    }
    private fun retrieveAllData(){
        // val whereClause = "ownerId = '${AppConstants.ownerId}'"
        val whereClause = "defaultSettings = 'True'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Call::class.java).find(queryBuilder,object :
            AsyncCallback<List<Call?>?> {
            override fun handleResponse(foundLoan: List<Call?>?) {
                // all Contact instances have been found
                Log.d(CallActivity.TAG,"foundDefaultCall: $foundLoan")
                val wong = ArrayList<Call>()
                if (!foundLoan.isNullOrEmpty()) {
                    val callList = foundLoan as List<*>
                    callList.forEach { Call -> wong.add(Call as Call) }
                    callData = callList.first() as Call
                    val detailIntent= Intent(this@Home.context,CallAcceptScreen::class.java).apply{
                        putExtra(CallAcceptScreen.EXTRA_SCHEDULEDCALL, callData)
                    }
                    startActivity(detailIntent)
                }
            }


            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(CallActivity.TAG,"handleFault: ${fault.message}")
            }
        })
    }
}