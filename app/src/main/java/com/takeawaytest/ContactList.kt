package com.takeawaytest


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeawaytest.UI.ViewModel.ContactViewModel
import com.takeawaytest.UI.ViewModelFactory.ContactViewModelFactory
import com.takeawaytest.common.Status
import com.takeawaytest.common.Utility
import com.takeawaytest.common.Utility.saveUserInfo
import com.takeawaytest.data.Adapter.ItemAdapter
import com.takeawaytest.data.Model.ContactListResponce
import com.takeawaytest.data.Model.Result
import java.util.*
import kotlin.collections.ArrayList

class ContactList : AppCompatActivity() {

    lateinit var conViewModel: ContactViewModel
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var adapter: ItemAdapter
    private var conList = ArrayList<Result>()
    private lateinit var recyclerView_contact : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
      //  var searchView = findViewById<SearchView>(R.id.searchView)
        ContactViewModelFactory()
        setupViewModel()

        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchview)

        getUsersContactList()

        recyclerView_contact = this.findViewById<RecyclerView>(R.id.recyclerView_contact)


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isNotEmpty()) {
                    filter(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    filter(newText)
                }
                return false
            }
        })




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
                conList= ArrayList()
                conList=res.result

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



    private fun filter(text: String) {

        if (text.isNotEmpty()) {

            if (conList.size > 0)
            {
                val filteredlist = ArrayList<Result>()
                for (item in conList) {
                    if (item.fullName!!.lowercase(Locale.getDefault())
                            .contains(text.lowercase(Locale.getDefault())) ||
                        item.phoneNumber!!.lowercase(Locale.getDefault())
                            .contains(text.lowercase(Locale.getDefault()))
                    ) {
                        filteredlist.add(item)
                    }
                }
                if (filteredlist.isEmpty()) {

                    Utility.getBaseMessage(
                        this,
                        "Sorry",
                        "No data found",
                        R.drawable.error_white,
                        0
                    )



                }

                else {
                    prepareOtherList(
                        recyclerView_contact,
                        filteredlist
                    )
                }
            }

            else {
                Utility.getBaseMessage(
                    this,
                    "Sorry",
                    "No data found",
                    R.drawable.error_white,
                    2
                )
            }

        } else {
            Utility.getBaseMessage(
                this,
                "Sorry",
                "No data found",
                R.drawable.error_white,
                0
            )
        }
    }



    private fun prepareOtherList(
        rv: RecyclerView,
        items: ArrayList<Result>
    ) {

        if (items.size > 0) {
            val itemAdapter = ItemAdapter(items)
            val rLayoutmanager: RecyclerView.LayoutManager = LinearLayoutManager(this)

            rv.layoutManager = rLayoutmanager
            val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rv.layoutManager = manager
            rv.adapter = itemAdapter
           // var intent = Intent (this, ContactDetails::class.java)

            itemAdapter.setOnItemClickListener(object :
                ItemAdapter.OnAdapterItemClickListener {
                override fun OnSelectSubMenu(v: View?, position: Int) {



                }
            })

        }


    }


}