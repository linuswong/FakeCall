package com.example.fakecall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.fakecall.databinding.ActivityMainBinding

class CallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    companion object{
        val TAG = "CallActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        Backendless.initApp(this, AppConstants.APP_ID,AppConstants.API_KEY)
        Backendless.UserService.login(
            "testEmail@gmail.com",
            "123",
            object : AsyncCallback<BackendlessUser?> {
                override fun handleResponse(user: BackendlessUser?) {
                    // user has been logged in
                    Log.d(CallActivity.TAG, "handleResponse: ${user?.getProperty("ownerId")}: has logged in")
                }

                override fun handleFault(fault: BackendlessFault) {
                    // login failed, to get the error code call fault.getCode()
                    Log.d(CallActivity.TAG,"handleFault: ${fault.message}")
                }
            }

        )


        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.notification -> replaceFragment(Scheduling())
                R.id.settings -> replaceFragment(Settings())
                else -> super.onOptionsItemSelected(it)
            }
            true
        }
//        supportFragmentManager.setFragmentResultListener(AppConstants.REQUEST_KEY, this) { requestKey, bundle ->
//            val result = bundle.getParcelable<Call>(AppConstants.BUNDLE_KEY)
//            System.out.println("This is the result $result")
//            // Do something with the result.
//        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out)
        fragmentTransaction.replace(R.id.frame_layout,fragment)
    }

}