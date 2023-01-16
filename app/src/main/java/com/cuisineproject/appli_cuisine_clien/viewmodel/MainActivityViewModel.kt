package com.cuisineproject.appli_cuisine_clien.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.constant.ERROR_MESSAGE_INVALID_MAIL
import com.cuisineproject.appli_cuisine_clien.constant.ERROR_MESSAGE_INVALID_PASSWORD
import com.cuisineproject.appli_cuisine_clien.constant.SUCCESS_MESSAGE_CREATION_ACCOUNT
import com.cuisineproject.appli_cuisine_clien.model.MyException
import com.cuisineproject.appli_cuisine_clien.utils.UserUtils
import com.cuisineproject.appli_cuisine_clien.utils.isMailValid
import com.cuisineproject.appli_cuisine_clien.utils.isPasswordValid
import kotlin.concurrent.thread

class MainActivityViewModel: ViewModel() {

    var errorEmailMessage = MutableLiveData("")
    var errorPasswordMessage = MutableLiveData("")
    var errorGeneralMessage = MutableLiveData("")
    var successMessage = MutableLiveData("")
    var runInProgress = MutableLiveData(false)
    var firstConnexion = MutableLiveData<Boolean?>(null)

    fun connexion(email:String, password: String){
        errorEmailMessage.postValue(null)
        errorPasswordMessage.postValue(null)
        errorGeneralMessage.postValue(null)
        runInProgress.postValue(true)

        thread {
            try {
                firstConnexion.postValue(UserUtils.connexion(email, password))
            }
            catch (e: Exception) {
                if(e is MyException) {
                    when (e.errorCode) {
                        MyException.ERROR_MAIL -> {
                            println(e.errorMessage)
                            errorEmailMessage.postValue(e.errorMessage)
                        }
                        MyException.ERROR_PASSWORD -> {
                            errorPasswordMessage.postValue(e.errorMessage)
                        }
                        MyException.ERROR_GENERAL_WELCOME -> {
                            errorGeneralMessage.postValue(e.errorMessage)
                        }
                    }
                }else{
                    errorGeneralMessage.postValue(e.message)
                }
                runInProgress.postValue(false)
            }
        }
    }

    fun inscription(email:String, password:String){
        errorEmailMessage.postValue(null)
        errorPasswordMessage.postValue(null)
        errorGeneralMessage.postValue(null)
        runInProgress.postValue(true)

        if(email.isMailValid() && password.isPasswordValid()){

            thread {
                try {
                    UserUtils.createUser(email, password)
                    successMessage.postValue(SUCCESS_MESSAGE_CREATION_ACCOUNT)
                }
                catch (e: Exception) {
                    if(e is MyException) {
                        when (e.errorCode) {
                            MyException.ERROR_MAIL -> {
                                println(e.errorMessage)
                                errorEmailMessage.postValue(e.errorMessage)
                            }
                            MyException.ERROR_PASSWORD -> {
                                errorPasswordMessage.postValue(e.errorMessage)
                            }
                            MyException.ERROR_GENERAL_WELCOME -> {
                                errorGeneralMessage.postValue(e.errorMessage)
                            }
                        }
                    }else{//TODO
                        errorGeneralMessage.postValue(e.message)
                    }
                    runInProgress.postValue(false)
                }
            }
        }else{
            if(!email.isMailValid() && !password.isPasswordValid()){
                errorEmailMessage.postValue(ERROR_MESSAGE_INVALID_MAIL)
                errorPasswordMessage.postValue(ERROR_MESSAGE_INVALID_PASSWORD)
            }
            else if (!email.isMailValid()){
                errorEmailMessage.postValue(ERROR_MESSAGE_INVALID_MAIL)
            }
            else {
                errorPasswordMessage.postValue(ERROR_MESSAGE_INVALID_PASSWORD)
            }
        }
        runInProgress.postValue(false)
    }

    fun getUser(){
        UserUtils.getActualUser()
    }
}