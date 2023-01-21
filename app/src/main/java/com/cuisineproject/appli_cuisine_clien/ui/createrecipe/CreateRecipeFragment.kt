package com.cuisineproject.appli_cuisine_clien.ui.createrecipe

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.cuisineproject.appli_cuisine_clien.R
import com.cuisineproject.appli_cuisine_clien.databinding.FragmentCreateRecipeBinding
import com.cuisineproject.appli_cuisine_clien.dto.ComposeDTO
import com.cuisineproject.appli_cuisine_clien.dto.InstructionDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO

class CreateRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = CreateRecipeFragment()
    }

    private lateinit var model: CreateRecipeViewModel
    private var _binding: FragmentCreateRecipeBinding? = null
    private var difficulty:Int? = null
    private var cost:Int? = null
    private var unitAdapter: ArrayAdapter<String>? = null
    private var ingredientAdapter : ArrayAdapter<String>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)

        binding.ibCostLevelOne.setOnClickListener{
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibCostLevelTwo.setBackgroundResource(R.drawable.white_round_button_background)
            binding.ibCostLevelThree.setBackgroundResource(R.drawable.white_round_button_background)
            cost = 1
        }

        binding.ibCostLevelTwo.setOnClickListener{
            binding.ibCostLevelOne.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibCostLevelThree.setBackgroundResource(R.drawable.white_round_button_background)
            cost = 2
        }

        binding.ibCostLevelThree.setOnClickListener{
            binding.ibCostLevelOne.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibCostLevelTwo.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            cost = 3
        }
        binding.ibDifficultyLevelEasy.setOnClickListener{
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibDifficultyLevelIntermediate.setBackgroundResource(R.drawable.white_round_button_background)
            binding.ibDifficultyLevelExpert.setBackgroundResource(R.drawable.white_round_button_background)
            difficulty = 1
        }

        binding.ibDifficultyLevelIntermediate.setOnClickListener{
            binding.ibDifficultyLevelEasy.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibDifficultyLevelExpert.setBackgroundResource(R.drawable.white_round_button_background)
            difficulty = 2
        }

        binding.ibDifficultyLevelExpert.setOnClickListener{
            binding.ibDifficultyLevelEasy.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibDifficultyLevelIntermediate.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            difficulty = 3
        }

        binding.ibAddInstruction.setOnClickListener{
            val editTextView = EditText(context)
            editTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editTextView.id = R.id.et_instruction
//            val addButton = ImageButton(context)
//            addButton.setBackgroundResource(R.drawable.ic_baseline_add_24)
//            val layout = LayoutInflater.from(binding.llInstructions.context)
            binding.llInstructions.addView(editTextView, binding.llInstructions.childCount-1)
        }

        binding.btAddPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        binding.btSave.setOnClickListener{
            val recipe = RecipeDisplayDTO()
            fillRecipe(recipe)
            model.createRecipe(recipe)
        }
        return binding.root
    }

    private fun fillRecipe(recipe: RecipeDisplayDTO) {
        setTitle(recipe)
        setPreparationTime(recipe)
        setCookingTime(recipe)
        setWaitingTime(recipe)
        setDifficulty(recipe)
        setCost(recipe)
        setInstructions(recipe)
        setComposes(recipe)
    }

    private fun setComposes(recipe: RecipeDisplayDTO) {
        val listComposeDTO = ArrayList<ComposeDTO>()
        binding.llIngredients.forEach { linearLayout ->
            linearLayout as LinearLayout
            val composeDTO = ComposeDTO()
            val etQuantity = linearLayout.findViewById<EditText>(R.id.et_quantity)
            if(etQuantity.text.isNotBlank()){
                val quantity = etQuantity.text.toString()
                composeDTO.quantity = quantity.toDouble()
            }
            val aetUnit = linearLayout.findViewById<AutoCompleteTextView>(R.id.aet_unit)

            if(aetUnit.text.isNotBlank()) {
                composeDTO.unitName = aetUnit.text.toString()
            }

            val aetIngredient = linearLayout.findViewById<AutoCompleteTextView>(R.id.aet_ingredient)
            if(aetIngredient.text.isNotBlank()) {
                composeDTO.ingredientName = aetIngredient.text.toString()
            }

            listComposeDTO.add(composeDTO)
        }
        recipe.listComposeDTO = listComposeDTO
    }

    private fun setInstructions(recipe: RecipeDisplayDTO) {
        val listInstruction = ArrayList<InstructionDTO>()
        binding.llInstructions.forEach { view ->
            if(view is EditText){
                val instructionDTO = InstructionDTO()
                instructionDTO.instruction = view.text.toString()
                listInstruction.add(instructionDTO)
            }
        }
        recipe.listInstructionDTO = listInstruction
    }

    private fun setCost(recipe: RecipeDisplayDTO) {
        recipe.cost = cost
    }

    private fun setDifficulty(recipe: RecipeDisplayDTO) {
        recipe.difficulty = difficulty
    }

    private fun setWaitingTime(recipe: RecipeDisplayDTO) {
        if(!binding.etWaitingTime.text.isNullOrBlank()) {
            recipe.waitingTime = Integer.parseInt(binding.etWaitingTime.text.toString())
        }else{
            recipe.waitingTime = null
        }
    }

    private fun setCookingTime(recipe: RecipeDisplayDTO) {
        if(!binding.etCookingTime.text.isNullOrBlank()) {
            recipe.cookingTime = Integer.parseInt(binding.etCookingTime.text.toString())
        }else{
            recipe.cookingTime = null
        }
    }

    private fun setPreparationTime(recipe: RecipeDisplayDTO) {
        if(!binding.etPreparationTime.text.isNullOrBlank()) {
            recipe.preparationTime = Integer.parseInt(binding.etPreparationTime.text.toString())
        }else{
            recipe.preparationTime = null
        }
    }

    private fun setTitle(recipe: RecipeDisplayDTO) {
        recipe.name = binding.ettTitle.text.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProvider(this)[CreateRecipeViewModel::class.java]

        model.errorElement.observe(viewLifecycleOwner){ list ->
            list?.forEach { exception ->
                val tvError =
                    binding.root.findViewById<TextView>(exception.errorCode)
                println(exception.errorCode)
                if (!exception.errorMessage.isNullOrBlank()) {
                    println("Set visible")
                    tvError.isVisible = true
                    tvError.text = exception.errorMessage
                } else {
                    println("set invisible")
                    tvError.isVisible = false
                }
            }
        }
        model.errorReset.observe(viewLifecycleOwner){ list ->
            list?.forEach{ id ->
                val tvError =
                    binding.root.findViewById<TextView>(id)
                if(tvError != null){
                    tvError.isVisible = false
                }
            }
        }

        model.errorGeneral.observe(viewLifecycleOwner){ message ->
            if(message != null) {
                binding.tvErrorGeneral.isVisible = true
                binding.tvErrorGeneral.text = message
            }else{
                binding.tvErrorGeneral.isVisible = false
            }
        }

        model.sendIngredient.observe(viewLifecycleOwner){ listIngredients ->
            var arrayNameIngredients = getArrayName(listIngredients)
            val adapter = createAdapter(arrayNameIngredients)
            ingredientAdapter = adapter
            binding.aetIngredient.setAdapter(adapter)
        }

        model.sendUnit.observe(viewLifecycleOwner){ listUnits ->
            var arrayNameUnit = getArrayName(listUnits)
            val adapter = createAdapter(arrayNameUnit)
            unitAdapter = adapter
            binding.aetUnit.setAdapter(adapter)
        }

        model.getIngredients()
        model.getUnits()

        binding.ibAddIngredient.setOnClickListener{

            val inflater = LayoutInflater.from(context).inflate(R.layout.row_add_ingredient, null)
            inflater.findViewById<AutoCompleteTextView>(R.id.aet_ingredient).setAdapter(ingredientAdapter)
            inflater.findViewById<AutoCompleteTextView>(R.id.aet_unit).setAdapter(unitAdapter)
            binding.llIngredients.addView(inflater)
        }
    }

    private fun createAdapter(arrayName: Array<String>): ArrayAdapter<String> {
        return ArrayAdapter<String>(context!!, androidx.appcompat.R.layout.select_dialog_item_material,arrayName)
    }

    private fun getArrayName(list : List<Any>):Array<String>{
        var listName: ArrayList<String> = arrayListOf()

        list.forEach{ element ->
            listName.add(element.toString())
        }
        return listName.toTypedArray()
    }

    private fun addIngredientView(){

    }
}