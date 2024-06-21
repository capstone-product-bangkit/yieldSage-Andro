package com.dicoding.yieldsage.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.data.local.model.UserModel
import com.dicoding.yieldsage.data.local.preference.UserPreference
import com.dicoding.yieldsage.data.local.preference.dataStore
import com.dicoding.yieldsage.databinding.ActivityLoginBinding
import com.dicoding.yieldsage.viewModel.LoginViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_WEB_APPLICATION_CLIENT_ID")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        checkSession()
        setRegisterText()
        setButton()
    }

    private fun checkSession(){
        showLoading(true)
        val userPreference = UserPreference.getInstance(dataStore)
        CoroutineScope(Dispatchers.Main).launch {
            if (userPreference.isSessionActive()) {
                showLoading(false)
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                showLoading(false)
            }
        }
    }

    private fun setButton(){
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().length
            val password = binding.passwordEditText.text.toString().length
            if (email != 0 && password != 0){
                setLogin()
            }else{
                showToast("Isi data dengan benar!")
            }
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }
        binding.loginGoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully
            val idToken = account?.idToken
            val email = account?.email

            showToast("Signed in as: $email")
            // Send ID token to your backend for verification and authentication
        } catch (e: ApiException) {
            Log.w("MainActivity", "signInResult:failed code=" + e.statusCode)
            showToast("Sign in failed")
        }
    }

    private fun setRegisterText(){
        val registerText = binding.registerText
        val spannableString = SpannableString(getString(R.string.register_text))
        val registerSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        val startIndex = spannableString.indexOf("Register")
        val endIndex = startIndex + "Register".length
        spannableString.setSpan(registerSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        registerText.text = spannableString
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setLogin(){
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels{viewModelFactory}
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        showLoading(true)
        viewModel.login(email, password)
        viewModel.loginResponse.observe(this){response->
            if (response.errors == false){
                val userId = response.data?.user?.userId.toString()
                val name = response.data?.user?.name.toString()
                val emailRes = response.data?.user?.email.toString()
                val token = response.data?.token.toString()
                viewModel.saveSession(
                    UserModel(
                    userId, name, emailRes, token
                ))
                showLoading(false)
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else{
                showLoading(false)
                showToast("${response.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.loginButton.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.loginButton.isEnabled = true
        }
    }
}