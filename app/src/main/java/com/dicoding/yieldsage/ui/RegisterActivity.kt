package com.dicoding.yieldsage.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.databinding.ActivityRegisterBinding
import com.dicoding.yieldsage.viewModel.RegisterViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLoginText()
        setRegisterAction()
    }

    private fun setRegisterAction(){
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().length
            val email = binding.emailEditText.text.toString().length
            val pNumber = binding.phoneNumberEditText.text.toString().length
            val passwordLength = binding.passwordEditText.text.toString().length
            if (name != 0 && email != 0 && pNumber != 0 && passwordLength != 0){
               setRegister()
            }else if(passwordLength < 8){
                showToast("Minimal Password 8 Karakter")
            } else{
                showToast("Isi data dengan benar!")
            }
        }
    }

    private fun setRegister(){

        val viewModelFactory = ViewModelFactory.getInstance(this@RegisterActivity)
        val viewModel: RegisterViewModel by viewModels{viewModelFactory}

        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val pNumber = binding.phoneNumberEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confPassword = binding.confirmPasswordEditText.text.toString()
        if (password == confPassword){
            showLoading(true)
            viewModel.register(name, email, pNumber, password)
            viewModel.registerResponse.observe(this) { response->
                if (response.errors == false){
                    showLoading(false)
                    showToast(response.message.toString())
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }else {
                    showLoading(false)
                    showToast("${response.message}")
                }
            }
        }else{
            showToast("Password do not match")
        }
    }

    private fun setLoginText(){
        val loginText = binding.alreadyHaveText
        val spannableString = SpannableString(getString(R.string.alreadyHave))
        val loginSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
//                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                startActivity(intent)
                finish()
            }
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        val startIndex = spannableString.indexOf("Log In")
        val endIndex = startIndex + "Log In".length
        spannableString.setSpan(loginSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        loginText.text = spannableString
        loginText.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.registerButton.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.registerButton.isEnabled = true
        }
    }
}