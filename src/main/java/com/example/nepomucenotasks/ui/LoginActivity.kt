package com.example.nepomucenotasks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.comom.SecurityPreferences
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import com.example.nepomucenotasks.ui.viewModel.ViewModelUser
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: ViewModelUser by viewModel()
    private var emailEdt: EditText? = null
    private var passwordEdt: EditText? = null
    lateinit var mSecurytiPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mSecurytiPreferences = SecurityPreferences(this)
        emailEdt = findViewById(R.id.editEmail)
        passwordEdt = findViewById(R.id.editPassword)
        setListeners()
        verifyLogedUer()
        observables()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                handleLogin()
            }
            R.id.textRegister ->{
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun handleLogin() {
        viewModel.getLogin(emailEdt?.text.toString(), passwordEdt?.text.toString(),this.applicationContext)

    }

    private fun observables(){
        viewModel.viewLiveDataGetLogin.observe(this, Observer {

            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {

                Toast.makeText(this, getString(R.string.msgSenhaincorretas), Toast.LENGTH_LONG)
                    .show()

            }
        })
    }

    private fun setListeners() {
        buttonLogin.setOnClickListener(this)
        textRegister.setOnClickListener(this)
    }

    private fun verifyLogedUer() {
        val userid = mSecurytiPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_ID)
        val userName =
            mSecurytiPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_NAME)

        if (userid != "" && userName != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }
}
