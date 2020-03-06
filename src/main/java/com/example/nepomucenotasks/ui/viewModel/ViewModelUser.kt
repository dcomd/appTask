package com.example.nepomucenotasks.ui.viewModel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nepomucenotasks.data.comom.SecurityPreferences
import com.example.nepomucenotasks.data.comom.EnumEventos
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import com.example.nepomucenotasks.data.entities.UserEntity
import com.example.nepomucenotasks.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewModelUser(private var mRepository: UserRepository) : ViewModel() {

    private var mSecurityPreferences: SecurityPreferences? = null

    private val returnTypeEnum: MutableLiveData<Int> = MutableLiveData()
    val viewLiveDataTypeEnumEventos: LiveData<Int> = returnTypeEnum
    private val returnGetLogin: MutableLiveData<Boolean> = MutableLiveData()
    val viewLiveDataGetLogin: LiveData<Boolean> = returnGetLogin


    fun saveUser(userEntity: UserEntity, context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            mSecurityPreferences = SecurityPreferences(context)

            if (validateUser(userEntity)) {
                returnTypeEnum.postValue(EnumEventos.EMPTYCAMPOS.value)


            } else {

                if (mRepository.isEmailExistent(userEntity.email)) {
                    returnTypeEnum.postValue(EnumEventos.VERYEMAI.value)

                } else {

                    val getReturnInsert = mRepository.insert(userEntity)

                    if (getReturnInsert > 0) {
                        returnTypeEnum.postValue(EnumEventos.SUCESS.value)

                        mSecurityPreferences?.storedString(
                                SharedPreferencesConstantes.Key.USER_ID,
                                getReturnInsert.toString()
                        )
                        mSecurityPreferences?.storedString(
                                SharedPreferencesConstantes.Key.USER_NAME,
                                userEntity.name
                        )
                        mSecurityPreferences?.storedString(
                                SharedPreferencesConstantes.Key.USER_EMAIL,
                                userEntity.email
                        )

                    } else {
                        returnTypeEnum.postValue(EnumEventos.ERROR.value)
                    }
                }

            }
        }
    }

    fun getLogin(email: String, password: String, context: Context) {
        mSecurityPreferences = SecurityPreferences(context)

        GlobalScope.launch(Dispatchers.IO) {
            val userEntity: UserEntity? = mRepository.get(email, password)

            if (userEntity != null) {
                mSecurityPreferences?.storedString(
                        SharedPreferencesConstantes.Key.USER_ID,
                        userEntity.id.toString()
                )
                mSecurityPreferences?.storedString(
                        SharedPreferencesConstantes.Key.USER_NAME,
                        userEntity.name
                )
                mSecurityPreferences?.storedString(
                        SharedPreferencesConstantes.Key.USER_EMAIL,
                        userEntity.email
                )
                returnGetLogin.postValue(true)
            } else {
                returnGetLogin.postValue(false)
            }
        }

    }


    private fun validateUser(userEntity: UserEntity): Boolean {

        var validateBoolean = false

        if (userEntity.email == "" || userEntity.name == "" || userEntity.password == "") {
            validateBoolean = true
        }

        return validateBoolean
    }

}