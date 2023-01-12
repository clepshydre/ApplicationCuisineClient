package com.cuisineproject.appli_cuisine_clien.model

import java.sql.Date

class User(

    var id: Int? = null,

    var mail: String? = null,

    var password: String? = null,

    var sessionId: String? = null,

    var dateOfBirth: String? = null,

    var sex: Int? = null,

    var cuisineLevel: Int? = null,

    var budget: Int? = null
)