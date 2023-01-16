package com.cuisineproject.appli_cuisine_clien.utils

import com.cuisineproject.appli_cuisine_clien.constant.*
import com.cuisineproject.appli_cuisine_clien.dto.PasswordDTO
import com.cuisineproject.appli_cuisine_clien.model.User
import com.google.gson.Gson

object UserUtils {

    private val gson = Gson()

    fun connexion(email:String, password: String):Boolean{
        println("connexion")
        val userToVerify = User(null,email,password, null, null, null,null)
        val userToVerifyJson = gson.toJson(userToVerify)
        return gson.fromJson(RequestUtils.sendPost(URL_CONNEXION, userToVerifyJson), Boolean::class.java)
    }

    fun createUser(email:String, password: String){
        println("Create user")
        val userToCreate = User(null, email, password, null, null, null, null)
        val userToCreateJson = gson.toJson(userToCreate)
        RequestUtils.sendPost(URL_CREATE_USER, userToCreateJson)
    }

    fun updateUser(userToUpdate:User){
        println("update user")
        val userToUpdateJson = gson.toJson(userToUpdate)
        RequestUtils.sendPost(URL_UPDATE_USER, userToUpdateJson)
    }

    fun getActualUser():User{
        println("get actual user")
        return gson.fromJson(RequestUtils.sendGet(URL_GET_ACTUAL_USER), User::class.java)
    }

    fun modifyPassword(passwordDTO: PasswordDTO):Boolean {
        val passwordDTOJson = gson.toJson(passwordDTO)
        return gson.fromJson(RequestUtils.sendPost(URL_MODIFY_PASSWORD, passwordDTOJson), Boolean::class.java)
    }
}