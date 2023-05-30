package com.example.fakecall

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.MotionLayoutBinding


class Scheduling : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: MotionLayoutBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CallAdapter
    lateinit var scheduledCalls: List<Call>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)

    }
//    override fun onStart(){
//        super.onStart()
//        retrieveAllData()
//    }
//    override fun onResume(){
//        super.onResume()
//        retrieveAllData()
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MotionLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Why are you doing this here???????? - Me
//        binding.fabFragmentSchedulingCallPerson.setOnClickListener{
//            binding.guidelineFragmentSchedulingGuideline.setGuidelinePercent(0.6F)
//            binding.groupFragmentSchedulingNumbers.visibility
//        }
        retrieveAllData()
        binding.fabSchedulingAddCall.setOnClickListener {
            val loanDetailIntent = Intent(this.context, CallDetailActivity::class.java).apply{
            }
            startActivity(loanDetailIntent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getScheduledCalls(){
        adapter = CallAdapter(scheduledCalls)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(null)
    }

    private fun retrieveAllData(){
        val whereClause = "defaultSettings = 'False'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.setWhereClause( whereClause );
        Backendless.Data.of(Call::class.java).find(queryBuilder,object :
            AsyncCallback<List<Call?>?> {
            override fun handleResponse(foundLoan: List<Call?>?) {
                // all Contact instances have been found
                Log.d("Scheduling", "foundScheduleThing: $foundLoan")
                var wong = ArrayList<Call>()
                if (foundLoan != null) {
                    var loanList = foundLoan as List<Call>
                    loanList.forEach { Call -> wong.add(Call) }
                    scheduledCalls = wong.toList()
                    println(scheduledCalls)
                    getScheduledCalls()
                    if(!foundLoan.isEmpty())
                    {
                        //does not work because of transitions
                        //Why do you do this to me
                        binding.textViewFragmentSchedulingNoSchedule.isVisible = false
                    }
                }
            }


            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("Scheduling","handleFault: ${fault.message}")
            }
        })
    }

}