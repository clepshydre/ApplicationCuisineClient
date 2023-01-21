package com.cuisineproject.appli_cuisine_clien.model

class UnitEntity(
    var id: Int? = null,

    var name: String? = null,
){
    override fun toString(): String {
        return if(this.name !=null) {
            this.name!!
        }else{
            ""
        }
    }
}