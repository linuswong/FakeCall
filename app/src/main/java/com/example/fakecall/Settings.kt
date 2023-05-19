package com.example.fakecall

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CALLERNAME = "LinusNotHere"
private const val ARG_PARAM2 = "param2"
private var m_Text = ""
private var callData = Call()

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var callerName: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_right)

    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
//        viewModel.bundleFromFragmentBToFragmentA.observe(viewLifecycleOwner, Observer {
//            // This will execute when fragment B set data for `bundleFromFragmentBToFragmentA`
//            // TODO: Write your logic here to handle data sent from FragmentB
//            val message = it.getString("ARGUMENT_MESSAGE", "")
//            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
//        })
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater,container,false)
//        setFragmentListener()
        saveSettings()
        showEditTextDialog()
        return binding.root
    }

//    override fun onResume() {
//        setFragmentListener()
//        super.onResume()
//    }
//
//    override fun onStop() {
//       newInstance(callerName?:"Caller Name Not Set", param2?:"e")
//        Log.d("Frogs","STOPPED + $callerName")
//        super.onStop()
//    }

    private fun showEditTextDialog(){
        binding.buttonSettingsSetCallerName.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("Set Caller Name")
            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton(
                "OK"
            ) { dialog, which -> m_Text = input.text.toString()
                binding.textViewSettingsCallerName.text = m_Text
                callerName = m_Text // testing thing

            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }

            builder.show()
        }
    }
//    private fun setFragmentListener(){
//        setFragmentResultListener(AppConstants.REQUEST_KEY) { requestKey, bundle ->
//            // We use a String here, but any type that can be put in a Bundle is supported.
//            System.out.println("LINUS FROGS WORKED 1/2")
//            if(bundle.getParcelable<Call>(AppConstants.BUNDLE_KEY) != null)
//            {
//                callData = bundle.getParcelable<Call>(AppConstants.BUNDLE_KEY)!!
//                System.out.println("LINUS FROGS 2/2")
//                initializeButtonsAndStuff()
//            }
//        }
//    }
    private fun initializeButtonsAndStuff(){
        binding.textViewSettingsCallerName.text = callerName
    }

    private fun saveSettings(){
        binding.buttonSettingsSaveSettings.setOnClickListener {
            callData.caller = binding.textViewSettingsCallerName.text.toString()
//            setFragmentResult( AppConstants.REQUEST_KEY, bundleOf(AppConstants.BUNDLE_KEY to callData))
            System.out.println("linus+$callData")
        }
    }
    private fun retrieveAllData(userId: String){
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.setWhereClause( whereClause );
        Backendless.Data.of(Call::class.java).find(queryBuilder,object :
            AsyncCallback<List<Call?>?> {
            override fun handleResponse(foundLoan: List<Call?>?) {
                // all Contact instances have been found
                Log.d(CallActivity.TAG,"foundLoan: $foundLoan")
                var wong = ArrayList<Call>()
                if (foundLoan != null) {
                    var callList = foundLoan as List<Call>
                    callList.forEach { Call -> wong.add(Call) }
                }
            }


            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(CallActivity.TAG,"handleFault: ${fault.message}")
            }
        })
    }
    fun updateCall() {
        Backendless.Data.of(Call::class.java).save(callData, object : AsyncCallback<Call> {
            override fun handleResponse(response: Call?) {
                // Contact instance has been updated
                Log.d("SAVE LOAN","handle response: $response")
                Toast.makeText(this@Settings.context, "Loan successfully saved", Toast.LENGTH_SHORT).show()
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("SAVE LOAN","HandleFault : $fault")
            }
        })
    }
}