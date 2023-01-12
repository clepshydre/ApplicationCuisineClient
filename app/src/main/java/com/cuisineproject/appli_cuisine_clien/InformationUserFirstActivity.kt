package com.cuisineproject.appli_cuisine_clien

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityInformationUserFirstBinding
import com.cuisineproject.appli_cuisine_clien.model.User
import com.cuisineproject.appli_cuisine_clien.viewmodel.InformationUserFirstViewModel
import java.text.DateFormat
import java.time.LocalDate
import java.util.*


class InformationUserFirstActivity : AppCompatActivity() {

    val model by lazy { ViewModelProvider(this)[InformationUserFirstViewModel::class.java] }
    private val myCalendar: Calendar = Calendar.getInstance()
    private val binding by lazy { ActivityInformationUserFirstBinding.inflate(layoutInflater)}
    private var budgetLevel:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        binding.etdBirthdate.setOnClickListener {
            DatePickerDialog(
                this@InformationUserFirstActivity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(
                    Calendar.MONTH
                ),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btSave.setOnClickListener{

            val userToUpdate = User()

            if(binding.cbDebutant.isChecked){
                userToUpdate.cuisineLevel = 1
            }else if(binding.cbIntermediate.isChecked){
                userToUpdate.cuisineLevel = 2
            }else if(binding.cbExpert.isChecked){
                userToUpdate.cuisineLevel = 3
            }

            if(binding.cbMan.isChecked){
                userToUpdate.sex = 1
            }else if(binding.cbWoman.isChecked){
                userToUpdate.sex = 2
            }

            if(binding.etdBirthdate.text.isNotBlank()){
                println("birthdate not blank")
                println(binding.etdBirthdate.text.toString())
                userToUpdate.dateOfBirth = binding.etdBirthdate.text.toString()
                println(userToUpdate.dateOfBirth.toString())
            }

            userToUpdate.budget = budgetLevel

            model.updateUserInformation(userToUpdate)
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }

        binding.btSkip.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }

        binding.ibBudget1.setOnClickListener{
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibBudget2.setBackgroundResource(R.drawable.white_round_button_background)
            binding.ibBudget3.setBackgroundResource(R.drawable.white_round_button_background)
            budgetLevel = 1
        }

        binding.ibBudget2.setOnClickListener{
            binding.ibBudget1.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibBudget3.setBackgroundResource(R.drawable.white_round_button_background)
            budgetLevel = 2
        }

        binding.ibBudget3.setOnClickListener{
            binding.ibBudget1.setBackgroundResource(R.drawable.red_round_button_background)
            binding.ibBudget2.setBackgroundResource(R.drawable.red_round_button_background)
            it.setBackgroundResource(R.drawable.red_round_button_background)
            budgetLevel = 3
        }

        binding.cbMan.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                binding.cbWoman.isChecked = false
            }
        }

        binding.cbWoman.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                binding.cbMan.isChecked = false
            }
        }

        binding.cbDebutant.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                binding.cbIntermediate.isChecked = false
                binding.cbExpert.isChecked = false
            }
        }

        binding.cbIntermediate.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                binding.cbDebutant.isChecked = false
                binding.cbExpert.isChecked = false
            }
        }

        binding.cbExpert.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                binding.cbDebutant.isChecked = false
                binding.cbIntermediate.isChecked = false
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.FRANCE)
        binding.etdBirthdate.setText(dateFormat.format(myCalendar.time))
    }
}