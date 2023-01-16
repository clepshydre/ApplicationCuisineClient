package com.cuisineproject.appli_cuisine_clien.ui.createrecipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.forEach
import com.cuisineproject.appli_cuisine_clien.R
import com.cuisineproject.appli_cuisine_clien.databinding.FragmentAccountBinding
import com.cuisineproject.appli_cuisine_clien.databinding.FragmentCreateRecipeBinding
import com.cuisineproject.appli_cuisine_clien.dto.IngredientDTO
import com.cuisineproject.appli_cuisine_clien.dto.InstructionDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO

class CreateRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = CreateRecipeFragment()
    }

    private lateinit var model: CreateRecipeViewModel
    private var _binding: FragmentCreateRecipeBinding? = null
    private val difficulty:Int? = null
    private val cost:Int? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)

        binding.btSave.setOnClickListener{
            val recipe = RecipeDisplayDTO()
            recipe.name = binding.ettTitle.text.toString()
            recipe.preparationTime = Integer.parseInt(binding.etPreparationTime.text.toString())
            recipe.cookingTime = Integer.parseInt(binding.etCookingTime.text.toString())
            recipe.waitingTime = Integer.parseInt(binding.etWaitingTime.text.toString())
            recipe.difficulty = difficulty
            recipe.cost = cost
//            val ingredientDTO:IngredientDTO
//            binding.llIngredient.forEach { view ->
//                if(it is EditText){
//                    ingredientDTO.
//                }
//            }

            var instructionsDTO:MutableList<InstructionDTO> = emptyList<InstructionDTO>() as MutableList<InstructionDTO>
            binding.llIngredient.forEach { view ->

                if(view is EditText){
                    val instructionDTO = InstructionDTO()
                    instructionDTO.instruction = view.text.toString()
                    instructionsDTO += instructionDTO
                }
            }

            recipe.listInstructionDTO = instructionsDTO
            model.createRecipe(recipe)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProvider(this)[CreateRecipeViewModel::class.java]
        // TODO: Use the ViewModel
    }

    private fun addIngredientView(){

    }
}