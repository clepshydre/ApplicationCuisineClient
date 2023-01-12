package com.cuisineproject.appli_cuisine_clien.model

class MyException(val errorMessage: String, val errorCode: Int):Exception(errorMessage) {

    companion object{
        const val ERROR_MAIL = 1
        const val ERROR_PASSWORD = 2
        const val ERROR_GENERAL_WELCOME = 3
        const val ERROR_OLD_PASSWORD = 4
        const val ERROR_NEW_PASSWORD = 5
    }
}