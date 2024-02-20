package com.takeawaytest.data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.takeawaytest.R
import com.takeawaytest.data.Model.ContactListResponce
import com.takeawaytest.data.Model.Result
import de.hdodenhof.circleimageview.CircleImageView

class ItemAdapter (var contactList: ArrayList<Result>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var onClickListener: OnAdapterItemClickListener
    lateinit var mContext: Context

    interface OnAdapterItemClickListener {
        fun OnSelectSubMenu(v: View?, position: Int)
    }


    fun setOnItemClickListener(listener: OnAdapterItemClickListener) {
        onClickListener = listener

    }
    fun clearList(newDataList: ArrayList<Result>) {
        newDataList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemview, parent, false)

        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        mContext = holder.itemView.context


        if(contactList.size>0){

            val Data = contactList[position]
            val ConName = holder.itemView.findViewById<TextView>(R.id.ConName)
            ConName.text = Data.fullName
            val ConNum = holder.itemView.findViewById<TextView>(R.id.ConNum)
            ConNum.text = main(Data.phoneNumber.toString())

            val img =  holder.itemView.findViewById<CircleImageView>(R.id.ConImg)
            var imglink = Data.image.toString()
            if(imglink.isNotEmpty()){
                //ToDo Image
                Glide
                    .with(mContext)
                    .load(Data.image.toString())
                    .error(R.drawable.ic_no_img)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_user_profile)
                    //  .transform(RoundedCorners(30,30.0))
                    .into(img)

        }

        }


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

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ViewHolder(ItemView: View, listener: OnAdapterItemClickListener) :
        RecyclerView.ViewHolder(ItemView) {

        init {

            itemView.setOnClickListener {
                listener.OnSelectSubMenu(itemView, adapterPosition)

            }
        }
    }


}