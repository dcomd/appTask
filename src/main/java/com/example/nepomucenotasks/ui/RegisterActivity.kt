package com.example.nepomucenotasks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.comom.EnumEventos
import com.example.nepomucenotasks.data.entities.UserEntity
import com.example.nepomucenotasks.ui.viewModel.ViewModelUser
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: ViewModelUser by viewModel()
    private var nomeEdt: EditText? = null
    private var emailEdt: EditText? = null
    private var passwordEdt: EditText? = null
    private var userEntity: UserEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        createObjects()
        setListeners()
        observables()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handlerSave()
            }
        }
    }

    private fun createObjects() {
        nomeEdt = findViewById(R.id.editName)
        emailEdt = findViewById(R.id.editEmail)
        passwordEdt = findViewById(R.id.editPassword)
    }

    private fun handlerSave() {

        userEntity = UserEntity(
                null,
                email = emailEdt?.text.toString(),
                name = editName?.text.toString(),
                password = passwordEdt?.text.toString()
        )

        viewModel.saveUser(userEntity!!, this)
    }

    private fun observables() {
        viewModel.viewLiveDataTypeEnumEventos.observe(this, Observer {
            when (it) {
                EnumEventos.SUCESS.value -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                EnumEventos.VERYEMAI.value -> {
                    Toast.makeText(this, getString(R.string.emailExistente), Toast.LENGTH_LONG).show()
                }
                EnumEventos.EMPTYCAMPOS.value -> {
                    Toast.makeText(this, getString(R.string.informardados), Toast.LENGTH_LONG).show()

                }
                EnumEventos.ERROR.value -> {
                    Toast.makeText(this, getString(R.string.ErroAoInserir), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }
}




