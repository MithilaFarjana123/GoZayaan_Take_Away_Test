package com.takeawaytest.data.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.takeawaytest.common.Resource
import com.takeawaytest.data.API.GetDataService
import com.takeawaytest.data.API.RetrofitClientInstance
import com.takeawaytest.data.Model.ContactListResponce
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


object contactRepository {

    fun getContactInfo(
    ): MutableLiveData<Resource<ContactListResponce>> {

        val conInfo: MutableLiveData<Resource<ContactListResponce>> = MutableLiveData<Resource<ContactListResponce>>()

        try {
            val service: GetDataService = RetrofitClientInstance.retrofitInstance.create(
                GetDataService::class.java
            )
            service.getContractList()
                ?.toObservable()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { response ->
                        conInfo.postValue(Resource.success(response))
                    },
                    { error ->
                        conInfo.postValue(Resource.error(error.message.toString(), null))
                    },
                )

        } catch (e: Exception) {
            Log.e("ContactRepository", "ERROR : " + e.message)
        }
        return conInfo
    }

}