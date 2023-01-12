package com.cuisineproject.appli_cuisine_clien.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.model.User
import com.cuisineproject.appli_cuisine_clien.utils.UserUtils
import kotlin.concurrent.thread

class InformationUserFirstViewModel: ViewModel() {


    var dataShow = MutableLiveData<User>()

    fun getUser(){
        thread {
            try {
                UserUtils.getActualUser()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserInformation(userToUpdate: User){
        thread {
            try {
                UserUtils.updateUser(userToUpdate)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}