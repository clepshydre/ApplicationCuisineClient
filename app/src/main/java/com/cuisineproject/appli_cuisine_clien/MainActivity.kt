package com.cuisineproject.appli_cuisine_clien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityMainBinding
import com.cuisineproject.appli_cuisine_clien.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    val model by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        model.errorEmailMessage.observe(this){ message ->
            if(message != null) {
                binding.tvErrorMail.isVisible = true
                binding.tvErrorMail.text = message
            }else{
                binding.tvErrorMail.isVisible = false
            }
        }

        model.errorPasswordMessage.observe(this){message ->
            if(message != null) {
                binding.tvErrorPassword.isVisible = true
                binding.tvErrorPassword.text = message
            }else{
                binding.tvErrorPassword.isVisible = false
            }
        }

        model.errorGeneralMessage.observe(this){message ->
            if(message != null) {
                binding.tvGeneralMessage.isVisible = true
                binding.tvGeneralMessage.setTextColor(R.color.thirdColor)
                binding.tvGeneralMessage.text = message
            }else{
                binding.tvGeneralMessage.isVisible = false
            }
        }
        model.successMessage.observe(this){ message ->
            if(message != null ){
                binding.tvGeneralMessage.isVisible = true
                binding.tvGeneralMessage.setTextColor(R.color.black)
                binding.tvGeneralMessage.text = message
            }else{
                binding.tvGeneralMessage.isVisible = false
            }
        }

        model.runInProgress.observe(this) {
            binding.progressBar.isVisible = it
        }

        model.firstConnexion.observe(this){
            if (it != null) {
                var intent = Intent()
                intent = if(it){
                    Intent(this, InformationUserFirstActivity::class.java)
                }else {
                    Intent(this, HomePageActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
        }

        binding.btConnexion.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            model.connexion(email,password)
        }

        binding.btInscription.setOnClickListener{
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            model.inscription(email, password)
        }
    }
}