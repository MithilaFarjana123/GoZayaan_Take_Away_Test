package com.takeawaytest.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takeawaytest.common.Resource
import com.takeawaytest.data.Model.ContactListResponce
import com.takeawaytest.data.Repository.contactRepository

class ContactViewModel : ViewModel(){
    private var contactMutableLiveData: MutableLiveData<Resource<ContactListResponce>>? = null

    fun getContactList(

    ): MutableLiveData<Resource<ContactListResponce>> {
        contactMutableLiveData = contactRepository.getContactInfo()
        return contactMutableLiveData!!
    }

}