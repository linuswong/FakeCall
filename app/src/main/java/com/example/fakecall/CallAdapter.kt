package com.example.fakecall

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault

class CallAdapter (var dataSet: List<Call>):
    RecyclerView.Adapter<CallAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCaller: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textViewCaller = view.findViewById(R.id.textView_callitem_caller)
            layout = view.findViewById(R.id.layout_item_call)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_call, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var context = viewHolder.textViewCaller.context
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val callData = dataSet[position]
        viewHolder.textViewCaller.text = callData.caller
        viewHolder.layout.setOnLongClickListener {
            val popMenu = PopupMenu(context, viewHolder.textViewCaller)
            popMenu.inflate(R.menu.menu_loan_list_context)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_loanList_delete -> {
                        deleteFromBackendless(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }

        viewHolder.layout.setOnClickListener {
            val detailIntent = Intent(it.context, CallDetailActivity::class.java)
            detailIntent.putExtra(CallDetailActivity.EXTRA_CALL, callData)
            it.context.startActivity(detailIntent)
        }

    }

    //pasted from loan project
    private fun deleteFromBackendless(position: Int) {
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete ${dataSet[position]}")
        // put in the code to delete the item using the callback from Backendless
        Backendless.Data.of(Call::class.java).remove(dataSet[position],
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


    override fun getItemCount() = dataSet.size
}
