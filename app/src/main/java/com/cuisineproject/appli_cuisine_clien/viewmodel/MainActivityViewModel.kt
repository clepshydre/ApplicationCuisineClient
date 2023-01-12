package com.cuisineproject.appli_cuisine_clien.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.model.MyException
import com.cuisineproject.appli_cuisine_clien.utils.UserUtils
import java.util.regex.Pattern
import kotlin.concurrent.thread

class MainActivityViewModel: ViewModel() {

    var errorEmailMessage = MutableLiveData("")
    var errorPasswordMessage = MutableLiveData("")
    var errorGeneralMessage = MutableLiveData("")
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

        if(isMailValid(email) && isPasswordValid(password)){

            thread {
                try {
                    UserUtils.createUser(email, password)
                    errorGeneralMessage.postValue("Votre compte a bien été créé")
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
        }else{
            if (!isMailValid(email)){
                errorEmailMessage.postValue("Votre adresse mail n'est pas valide")
            }
            if (!isPasswordValid(password)) {
                println(password)
                errorPasswordMessage.postValue("Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre")
            }
        }
        runInProgress.postValue(false)
    }

    fun getUser(){
        UserUtils.getActualUser()
    }

    private fun isMailValid(email :String):Boolean{
        val regexMail = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        return regexMail.matcher(email).matches()
    }

    private fun isPasswordValid(password :String):Boolean{
        val regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        return regexPassword.matcher(password).matches()
    }
}