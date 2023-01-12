package com.cuisineproject.appli_cuisine_clien.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.dto.PasswordDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.cuisineproject.appli_cuisine_clien.model.MyException
import com.cuisineproject.appli_cuisine_clien.model.User
import com.cuisineproject.appli_cuisine_clien.utils.UserUtils
import java.util.regex.Pattern
import kotlin.concurrent.thread

class MyInformationViewModel: ViewModel() {
    var errorOldPasswordMessage = MutableLiveData("")
    var errorNewPasswordMessage = MutableLiveData("")
    var dataShow = MutableLiveData<User>(null)
    var modifyPasswordSuccess = MutableLiveData<Boolean?>(null)

    fun getUser(){
        thread {
            try {
                dataShow.postValue(UserUtils.getActualUser())
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

    fun modifyPassword(passwordDTO: PasswordDTO){
        val oldPassword = passwordDTO.oldPassword
        val newPassword = passwordDTO.newPassword
        if(isPasswordValid(oldPassword)){
            if(isPasswordValid(newPassword)){
                thread {
                    try {
                        modifyPasswordSuccess.postValue(UserUtils.modifyPassword(passwordDTO))
                        var errorOldPasswordMessage = MutableLiveData("")
                        var errorNewPasswordMessage = MutableLiveData("")
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        if(e is MyException) {
                            when (e.errorCode) {
                                MyException.ERROR_OLD_PASSWORD -> {
                                    println(e.errorMessage)
                                    errorOldPasswordMessage.postValue(e.errorMessage)
                                }
                                MyException.ERROR_NEW_PASSWORD -> {
                                    errorNewPasswordMessage.postValue(e.errorMessage)
                                }
                            }
                        }
                    }
                }
            }else{
                errorNewPasswordMessage.postValue("Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre")
            }
        }else{
            errorOldPasswordMessage.postValue("Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre")
        }
    }

    private fun isPasswordValid(password :String):Boolean{
        val regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        return regexPassword.matcher(password).matches()
    }
}