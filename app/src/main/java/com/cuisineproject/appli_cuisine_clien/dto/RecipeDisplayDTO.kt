package com.cuisineproject.appli_cuisine_clien.dto

class RecipeDisplayDTO (

    var id: Int? = null,

    var name: String? = null,

    var preparationTime: Int? = null,

    var cookingTime: Int? = null,

    var waitingTime: Int? = null,

    var totalTime :Int? = null,

    var difficulty: Int? = null,

    var cost: Int? = null,

    var image: String? = null,

    var like:Boolean? = null,

    var listComposeDTO: List<ComposeDTO> = emptyList(),

    var listInstructionDTO: List<InstructionDTO> = emptyList()
)