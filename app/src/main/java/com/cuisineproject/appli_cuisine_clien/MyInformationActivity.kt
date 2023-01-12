package com.cuisineproject.appli_cuisine_clien

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityHomePageBinding
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityMainBinding
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityMyInformationBinding
import com.cuisineproject.appli_cuisine_clien.dto.PasswordDTO
import com.cuisineproject.appli_cuisine_clien.model.User
import com.cuisineproject.appli_cuisine_clien.viewmodel.MainActivityViewModel
import com.cuisineproject.appli_cuisine_clien.viewmodel.MyInformationViewModel
import java.util.*

class MyInformationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMyInformationBinding.inflate(layoutInflater)}
    val model by lazy { ViewModelProvider(this)[MyInformationViewModel::class.java] }
    private val myCalendar: Calendar = Calendar.getInstance()
    private var budgetLevel:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        binding.etdBirthdate.setOnClickListener {
            DatePickerDialog(
                this@MyInformationActivity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(
                    Calendar.MONTH
                ),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        model.dataShow.observe(this){ user ->
            if(user != null) {
                if(user.cuisineLevel != null){
                    println("cuisineLevel: "+user.cuisineLevel)
                    if (user.cuisineLevel == 1) {
                        binding.cbDebutant.isChecked = true
                    } else if (user.cuisineLevel == 2) {
                        binding.cbIntermediate.isChecked = true
                    } else {
                        binding.cbExpert.isChecked = true
                    }
                }
                if(user.sex !=null){
                    println("sex: "+user.sex)
                    if (user.sex == 1) {
                        binding.cbMan.isChecked = true
                    } else {
                        binding.cbWoman.isChecked = true
                    }
                }

                if(user.budget != null){
                    println("budget: "+user.budget)
                    if(user.budget == 1){
                        binding.ibBudget1.setBackgroundResource(R.drawable.red_round_button_background)
                        budgetLevel = 1
                    }else if(user.budget == 2){
                        binding.ibBudget1.setBackgroundResource(R.drawable.red_round_button_background)
                        binding.ibBudget2.setBackgroundResource(R.drawable.red_round_button_background)
                        budgetLevel = 2
                    }else{
                        binding.ibBudget1.setBackgroundResource(R.drawable.red_round_button_background)
                        binding.ibBudget2.setBackgroundResource(R.drawable.red_round_button_background)
                        binding.ibBudget3.setBackgroundResource(R.drawable.red_round_button_background)
                        budgetLevel = 3
                    }
                }

                if (!user.dateOfBirth.isNullOrBlank()) {
                    binding.etdBirthdate.setText(user.dateOfBirth)
                }
            }
        }

        model.errorNewPasswordMessage.observe(this){ errorMessage ->
            if(errorMessage.isBlank()){
                binding.tvErrorNewPassword.visibility = View.GONE
            }else {
                binding.tvErrorNewPassword.text = errorMessage
            }
        }

        model.errorOldPasswordMessage.observe(this){ errorMessage ->
            if(errorMessage.isBlank()){
                binding.tvErrorNewPassword.visibility = View.GONE
            }else {
                binding.tvErrorOldPassword.text = errorMessage
            }
        }

        model.modifyPasswordSuccess.observe(this){success ->
            if(success != null){
                if(success) {
                    val userToUpdate = createUserToUpdate()
                    model.updateUserInformation(userToUpdate)
                    redirectToHomePage()
                }
            }
        }

        binding.btSaveMyInfo.setOnClickListener{
            if(binding.etNewPassword.text.isNotBlank() && binding.etOldPassword.text.isNotBlank()){
                val passwordDTO = PasswordDTO(binding.etOldPassword.text.toString(), binding.etNewPassword.text.toString())
                model.modifyPassword(passwordDTO)
            }else{
                val userToUpdate = createUserToUpdate()
                model.updateUserInformation(userToUpdate)
                redirectToHomePage()
            }
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

        model.getUser()
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.FRANCE)
        binding.etdBirthdate.setText(dateFormat.format(myCalendar.time))
    }

    private fun createUserToUpdate():User{
        val userToUpdate = User()
        setCuisineLevel(userToUpdate)
        setSex(userToUpdate)
        setBirthdate(userToUpdate)
        setBudgetLevel(userToUpdate)
        return userToUpdate
    }

    private fun setCuisineLevel(userToUpdate:User){
        if(binding.cbDebutant.isChecked){
            userToUpdate.cuisineLevel = 1
        }else if(binding.cbIntermediate.isChecked){
            userToUpdate.cuisineLevel = 2
        }else if(binding.cbExpert.isChecked){
            userToUpdate.cuisineLevel = 3
        }
    }
    private fun setSex(userToUpdate: User){
        if(binding.cbMan.isChecked){
            userToUpdate.sex = 1
        }else if(binding.cbWoman.isChecked){
            userToUpdate.sex = 2
        }
    }

    private fun setBirthdate(userToUpdate: User){
        if(binding.etdBirthdate.text.isNotBlank()){
            println("birthdate not blank")
            println(binding.etdBirthdate.text.toString())
            userToUpdate.dateOfBirth = binding.etdBirthdate.text.toString()
            println(userToUpdate.dateOfBirth.toString())
        }
    }

    private fun setBudgetLevel(userToUpdate: User){
        userToUpdate.budget = budgetLevel
    }

    private fun redirectToHomePage(){
        intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }
}