package com.takeawaytest


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.takeawaytest.UI.ViewModel.ContactViewModel
import com.takeawaytest.UI.ViewModelFactory.ContactViewModelFactory
import com.takeawaytest.common.Status
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
    lateinit var shimmer_con_container : ShimmerFrameLayout
    val List = ArrayList<Result>()
    private var noDataFoundToastShown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
      //  ContactViewModelFactory()
        setupViewModel()


        shimmer_con_container = findViewById(R.id.shimmer_con_container)
        shimmer_con_container.visibility = View.VISIBLE
        shimmer_con_container.startShimmer()

        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchview)
        searchView.setQueryHint("search ")
        //adapter = ItemAdapter(List)
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
                 //   if(newText.length >= 3){

                        filter(newText)

                 //   }

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
                        shimmer_con_container.visibility = View.GONE
                        shimmer_con_container.stopShimmer()
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
        shimmer_con_container.visibility = View.GONE
        shimmer_con_container.stopShimmer()

    }


    private fun prepareAttendanceRV(items: ArrayList<Result>) {
        val itemAdapter = ItemAdapter(items)
        val rLayoutmanager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val recyclerView_contact = this.findViewById<RecyclerView>(R.id.recyclerView_contact)

        recyclerView_contact.layoutManager = rLayoutmanager
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_contact.layoutManager = manager
        recyclerView_contact.adapter = itemAdapter
        var intent = Intent (this, ContactDetails::class.java)


        itemAdapter.setOnItemClickListener(object :
            ItemAdapter.OnAdapterItemClickListener {
            override fun OnSelectSubMenu(v: View?, position: Int) {
                val contactInfo : ArrayList<Result> = items
                saveUserInfo(contactInfo[position],this@ContactList)
                startActivity(intent)



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

                    if (!noDataFoundToastShown) {
                        Toast.makeText(this, "Sorry! No data found", Toast.LENGTH_LONG).show()
                        noDataFoundToastShown = true
                    }

                    //   Toast.makeText(this, "Sorry!No data found", Toast.LENGTH_LONG).show()

                    prepareOtherList(
                        recyclerView_contact,
                        filteredlist
                    )
                }

                else {
                        noDataFoundToastShown = false
                        prepareOtherList(
                            recyclerView_contact,
                            filteredlist
                        )

                }

            }



        } else {
          //  Toast.makeText(this, "Sorry!No data found", Toast.LENGTH_LONG).show()


        }
    }



    private fun prepareOtherList(
        rv: RecyclerView,
        items: ArrayList<Result>
    ) {

        if (items.size > 0) {
           // rv.visibility = View.VISIBLE
            val itemAdapter = ItemAdapter(items)
            val rLayoutmanager: RecyclerView.LayoutManager = LinearLayoutManager(this)

            rv.layoutManager = rLayoutmanager
            val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rv.layoutManager = manager
            rv.adapter = itemAdapter
            var intent = Intent (this, ContactDetails::class.java)

            itemAdapter.setOnItemClickListener(object :
                ItemAdapter.OnAdapterItemClickListener {
                override fun OnSelectSubMenu(v: View?, position: Int) {

                    val contactInfo : ArrayList<Result> = items
                    saveUserInfo(contactInfo[position],this@ContactList)
                    startActivity(intent)


                }
            })

        }

        else{
            //  rv.visibility = View.GONE
            val itemAdapter = ItemAdapter(items)
            itemAdapter.clearList(items)
            val rLayoutmanager: RecyclerView.LayoutManager = LinearLayoutManager(this)

            rv.layoutManager = rLayoutmanager
            val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rv.layoutManager = manager
            rv.adapter = itemAdapter
            var intent = Intent (this, ContactDetails::class.java)

            itemAdapter.setOnItemClickListener(object :
                ItemAdapter.OnAdapterItemClickListener {
                override fun OnSelectSubMenu(v: View?, position: Int) {

                    val contactInfo : ArrayList<Result> = items
                    saveUserInfo(contactInfo[position],this@ContactList)
                    startActivity(intent)


                }
            })
        }


    }






}