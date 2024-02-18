package com.takeawaytest.data.API

import com.takeawaytest.data.Model.ContactListResponce
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface GetDataService {

    @GET("user_journey/contact_list/")
    fun getContractList(): Flowable<ContactListResponce>?

}