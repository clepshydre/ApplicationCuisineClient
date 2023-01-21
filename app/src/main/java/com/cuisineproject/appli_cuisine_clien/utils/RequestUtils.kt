package com.cuisineproject.appli_cuisine_clien.utils



import com.cuisineproject.appli_cuisine_clien.constant.ERROR_GENERAL_MESSAGE
import com.cuisineproject.appli_cuisine_clien.model.ErrorBean
import com.cuisineproject.appli_cuisine_clien.model.MyException
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import java.net.CookieManager
import java.net.CookiePolicy


object RequestUtils {

    private val gson = Gson()
    private val client = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(CookieManager().apply { setCookiePolicy(
        CookiePolicy.ACCEPT_ALL) })).build()

    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête

        return client.newCall(request).execute().use {

            //Analyse du code retour
            if (!it.isSuccessful) {
                println("unsucessful")
                val json = it.body?.string() ?: ""
                if(json.isNotBlank()) {
                    println("json not blank")
                    val errorBean = gson.fromJson(json, ErrorBean::class.java)
                    if(!errorBean.errorMessage.isNullOrBlank()){
                        println("error message")
                        throw MyException(errorBean.errorMessage, errorBean.errorCode)
                    }
                }
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            println("Successful")
            //Résultat de la requête
            it.body?.string() ?: ""
        }
    }

    @Throws(java.lang.Exception::class)
    fun sendPost(url: String, jsonAEnvoyer: String): String? {
        println("url : $url")

        //Corps de la requête
        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        val body = jsonAEnvoyer.toRequestBody(mediaTypeJson)

        //Création de la requête
        val request: Request = Request.Builder().url(url).post(body).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("not successful")
                val json = response.body?.string() ?: ""
                if(json.isNotBlank()) {
                    println("json not blank")
                    val errorBean = gson.fromJson(json, ErrorBean::class.java)
                    if(!errorBean.errorMessage.isNullOrBlank()){
                        println("errorMessage not blank or null")
                        throw MyException(errorBean.errorMessage, errorBean.errorCode)
                    }else{
                        throw Exception(ERROR_GENERAL_MESSAGE)
                    }
                }
                throw IOException("Unexpected code $response")
            }
            println("successfull")
            return response.body!!.string()
        }
    }

    @Throws(java.lang.Exception::class)
    fun sendPost(url: String): String? {
        println("url : $url")

        //Création de la requête
        val request: Request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("not successful")
                val json = response.body?.string() ?: ""
                if(json.isNotBlank()) {
                    println("json not blank")
                    val errorBean = gson.fromJson(json, ErrorBean::class.java)
                    if(!errorBean.errorMessage.isNullOrBlank()){
                        println("errorMessage not blank or null")
                        throw MyException(errorBean.errorMessage, errorBean.errorCode)
                    }
                }
                throw IOException("Unexpected code $response")
            }
            println("successfull")
            return response.body!!.string()
        }
    }
}