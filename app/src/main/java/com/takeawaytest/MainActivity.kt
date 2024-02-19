package com.takeawaytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeawaytest.UI.ViewModel.ContactViewModel
import com.takeawaytest.UI.ViewModelFactory.ContactViewModelFactory
import com.takeawaytest.common.Status
import com.takeawaytest.data.Adapter.ItemAdapter
import com.takeawaytest.data.Model.ContactListResponce
import com.takeawaytest.data.Model.Result
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    private val SPLASH_DISPLAY_LENGTH = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textSplash: TextView = findViewById(R.id.textSplash)

        // Optionally, you can customize the text or other properties here

        Handler().postDelayed({
            val mainIntent = Intent(this@MainActivity, ContactList::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())

    }






}


