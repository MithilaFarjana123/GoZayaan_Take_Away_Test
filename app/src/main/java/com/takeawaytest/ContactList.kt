package com.takeawaytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeawaytest.UI.ViewModel.ContactViewModel
import com.takeawaytest.UI.ViewModelFactory.ContactViewModelFactory
import com.takeawaytest.common.Status
import com.takeawaytest.data.Adapter.ItemAdapter
import com.takeawaytest.data.Model.ContactListResponce
import com.takeawaytest.data.Model.Result
import java.util.ArrayList

class ContactList : AppCompatActivity() {

    lateinit var conViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
        ContactViewModelFactory()
        setupViewModel()
        getUsersContactList()

    }


    private fun setupViewModel() {
        conViewModel = ViewModelProviders.of(this, ContactViewModelFactory()
        ).get(ContactViewModel::class.java)
    }


    private fun getUsersContactList() {
        conViewModel.getContactList()
            .observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {

                        it.responseData?.let { res ->
                            successAttList(res)
                        }

                    }
                    Status.LOADING -> {


                    }
                    Status.ERROR -> {

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun successAttList(res: ContactListResponce) {

        if (res.status == true) {
            if (res.result.isNotEmpty()) {


                prepareAttendanceRV(res.result)
            } else {
                val recyclerView_contact = this.findViewById<RecyclerView>(R.id.recyclerView_contact)
                recyclerView_contact.visibility = View.GONE
            }
        }


    }


    private fun prepareAttendanceRV(items: ArrayList<Result>) {
        val itemAdapter = ItemAdapter(items)
        val rLayoutmanager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val recyclerView_contact = this.findViewById<RecyclerView>(R.id.recyclerView_contact)

        recyclerView_contact.layoutManager = rLayoutmanager
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_contact.layoutManager = manager
        recyclerView_contact.adapter = itemAdapter


        itemAdapter.setOnItemClickListener(object :
            ItemAdapter.OnAdapterItemClickListener {
            override fun OnSelectSubMenu(v: View?, position: Int) {



            }
        })
    }

}