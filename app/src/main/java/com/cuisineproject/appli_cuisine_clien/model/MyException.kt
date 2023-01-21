package com.cuisineproject.appli_cuisine_clien.model

import com.cuisineproject.appli_cuisine_clien.R

class MyException(val errorMessage: String, val errorCode: Int):Exception(errorMessage) {

    companion object{
        const val ERROR_MAIL = 1
        const val ERROR_PASSWORD = 2
        const val ERROR_GENERAL_WELCOME = 3
        const val ERROR_OLD_PASSWORD = 4
        const val ERROR_NEW_PASSWORD = 5
        const val ERROR_TITLE_CREATE_RECIPE = R.id.tv_error_title_create_recipe
        const val ERROR_PREPARATION_TIME_CREATE_RECIPE = R.id.tv_error_preparation_time_create_recipe
        const val ERROR_COOKING_TIME_CREATE_RECIPE = R.id.tv_error_cooking_time
        const val ERROR_WAITING_TIME_CREATE_RECIPE = R.id.tv_error_waiting_time
        const val ERROR_DIFFICULTY_CREATE_RECIPE = R.id.tv_error_difficulty
        const val ERROR_COST_CREATE_RECIPE = R.id.tv_error_cost
        const val ERROR_INGREDIENT_CREATE_RECIPE = R.id.tv_error_ingredient
        const val ERROR_INSTRUCTION_CREATE_RECIPE = R.id.tv_error_instruction
        const val ERROR_GENERAL_CREATE_RECIPE = R.id.tv_error_general
    }
}