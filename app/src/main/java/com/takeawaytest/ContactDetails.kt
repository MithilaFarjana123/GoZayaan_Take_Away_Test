package com.takeawaytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.takeawaytest.common.Utility
import com.takeawaytest.data.Model.Result
import de.hdodenhof.circleimageview.CircleImageView

class ContactDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        val contactInfo: Result = Utility.getUserInfo(this)!!
        val ConDName = findViewById<TextView>(R.id.ConDName)
        val ConDEmail = findViewById<TextView>(R.id.ConDEmail)
        val ConDNum = findViewById<TextView>(R.id.ConDNum)
        val conDImg = findViewById<CircleImageView>(R.id.ConDImg)
        ConDName.setText(contactInfo.fullName)
        ConDEmail.setText(contactInfo.email)
        ConDNum.setText(main(contactInfo.phoneNumber.toString()))
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {

            var intent = Intent (this, ContactList::class.java)
            startActivity(intent)
        }

        Glide
            .with(this)
            .load(contactInfo.image)
            .error(R.drawable.ic_no_img)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.ic_user_profile)
            //  .transform(RoundedCorners(30,30.0))
            .into(conDImg)

    }



    fun formatPhoneNumber(phoneNumber: String): String {
        val cleanNumber = phoneNumber.replace("\\D".toRegex(), "")

        // Apply the desired format
        return "(${cleanNumber.substring(0, 3)}) ${cleanNumber.substring(3, 6)}-${cleanNumber.substring(6)}"
    }

    fun main(phoneNumber: String): String {
        val formattedPhoneNumber = formatPhoneNumber(phoneNumber)

        return formattedPhoneNumber
    }


}