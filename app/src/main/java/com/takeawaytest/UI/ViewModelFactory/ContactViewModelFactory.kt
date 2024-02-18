package com.takeawaytest.UI.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takeawaytest.UI.ViewModel.ContactViewModel

class ContactViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }

}