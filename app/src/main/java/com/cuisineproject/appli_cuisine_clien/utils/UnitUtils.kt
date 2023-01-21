package com.cuisineproject.appli_cuisine_clien.utils

import com.cuisineproject.appli_cuisine_clien.constant.URL_ALL_INGREDIENTS
import com.cuisineproject.appli_cuisine_clien.constant.URL_ALL_UNITS
import com.cuisineproject.appli_cuisine_clien.model.UnitEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object UnitUtils {


    private val gson = Gson()

    fun getAllUnits(): List<UnitEntity> {

        val json = RequestUtils.sendGet(URL_ALL_UNITS)

        //Parser le JSON avec le bon bean et GSON
        val listOfUnit: Type = object : TypeToken<List<UnitEntity?>?>() {}.type
        //Retourner la donnée
        return gson.fromJson(json, listOfUnit)
    }

}