package com.takeawaytest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.takeawaytest.common.Utility
import com.takeawaytest.data.Model.Result
import de.hdodenhof.circleimageview.CircleImageView

class ContactDetails : AppCompatActivity() {

    lateinit var phoneNumber : String
    lateinit var emailAddress : String
    private var messageText = " "
    private var subject = " "

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
            .into(conDImg)

        onclick(contactInfo.phoneNumber.toString(),contactInfo.email.toString())

    }



    fun formatPhoneNumber(phoneNumber: String): String {
        val cleanNumber = phoneNumber.replace("\\D".toRegex(), "")

        return "(${cleanNumber.substring(0, 3)}) ${cleanNumber.substring(3, 6)}-${cleanNumber.substring(6)}"
    }

    fun main(phoneNumber: String): String {
        val formattedPhoneNumber = formatPhoneNumber(phoneNumber)
        return formattedPhoneNumber
    }


    fun onclick(number: String,email : String){
        phoneNumber = number
        emailAddress = email

        var give_Call = findViewById<CardView>(R.id.Give_Call)
        var send_Message = findViewById<CardView>(R.id.Send_Message)
        var send_Email = findViewById<CardView>(R.id.Send_Email)


        give_Call.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            }
        }

        send_Message.setOnClickListener {
            val uri = Uri.parse("smsto:$phoneNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", messageText)

            startActivity(intent)
        }


        send_Email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$emailAddress")
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, messageText)
            startActivity(Intent.createChooser(emailIntent, "Send email"))
        }

    }










}