package com.cuisineproject.appli_cuisine_clien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.constant.URL_TEST
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityMainBinding
import com.cuisineproject.appli_cuisine_clien.utils.RequestUtils
import com.cuisineproject.appli_cuisine_clien.viewmodel.MainActivityViewModel
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    val model by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        model.errorEmailMessage.observe(this){
            if(it != null) {
                binding.tvErrorMail.isVisible = true
                binding.tvErrorMail.text = it
            }else{
                binding.tvErrorMail.isVisible = false
            }
        }
        model.errorPasswordMessage.observe(this){
            if(it != null) {
                binding.tvErrorPassword.isVisible = true
                binding.tvErrorPassword.text = it
            }else{
                binding.tvErrorPassword.isVisible = false
            }
        }

        model.errorGeneralMessage.observe(this){
            if(it != null) {
                binding.tvErrorGeneral.isVisible = true
                binding.tvErrorGeneral.text = it
            }else{
                binding.tvErrorGeneral.isVisible = false
            }
        }

        model.runInProgress.observe(this) {
            binding.progressBar.isVisible = it
        }

        model.firstConnexion.observe(this){
            if (it != null) {
                var intent = Intent()
                if(it){
                    intent = Intent(this, InformationUserFirstActivity::class.java)
                }else {
                    intent = Intent(this, HomePageActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
        }

        binding.btConnexion.setOnClickListener {
            println("btConnexion clicked")
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            model.connexion(email,password)
        }

        binding.btInscription.setOnClickListener{
            println("btInscription clicked")
            val email = binding.etMail.text.toString()
            println(email)
            val password = binding.etPassword.text.toString()
            println(password)
            model.inscription(email, password)
        }
    }
}