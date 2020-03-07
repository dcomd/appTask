package com.example.nepomucenotasks.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.comom.SecurityPreferences
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import com.example.nepomucenotasks.data.entities.PriorityEntity
import com.example.nepomucenotasks.data.entities.TaskEntity
import com.example.nepomucenotasks.ui.viewModel.ViewModelPriority
import com.example.nepomucenotasks.ui.viewModel.ViewModelTask
import kotlinx.android.synthetic.main.activity_task_form.*
import kotlinx.android.synthetic.main.row_task_linhas.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private val viewModel: ViewModelPriority by viewModel()
    private val viewModelTask: ViewModelTask by viewModel()
    private var spinner: Spinner? = null
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var mSecurityPreferences: SecurityPreferences

    private var mPriorityEntityList: MutableList<PriorityEntity> = mutableListOf()
    private var mPriorityEntityListId: MutableList<Long> = mutableListOf()
    private var mTaskId: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)
        spinner = findViewById(R.id.spinnerPriority)
        observables()
        loadPrioritys()
        setListeners()
        loadDataFromActivity()
        mSecurityPreferences = SecurityPreferences(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonDate -> {
                openDialogDate()
            }
            R.id.buttonSave -> {
                handlerSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }


    private fun observables() {
        viewModelTask.returnLiveDataInser.observe(this, Observer {
            if (it > 0) {
                Toast.makeText(this, getString(R.string.tarefaSalva), Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.erro_cad_tarefa), Toast.LENGTH_LONG).show()
            }
        })

        viewModelTask.returnLiveDataUodate.observe(this, Observer {
            if (it > 0) {
                Toast.makeText(this, getString(R.string.terefa_alterada), Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.erro_alt_tarefa), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadPrioritys() {

        mPriorityEntityList = viewModel.getPriotiry().toMutableList()
        val listPriorities = mPriorityEntityList.map { it.description }
        mPriorityEntityListId = mPriorityEntityList.map { it.id }.toMutableList()

        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listPriorities
        )
        spinnerPriority.adapter = adapter

    }


    private fun handlerSave() {
        val userId = mSecurityPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_ID)
        var idUser = 0L
        if (userId != "") {
            idUser = userId!!.toLong()

        }

        val description: String = editDescription.text.toString()
        val priorityId = mPriorityEntityListId[spinnerPriority.selectedItemPosition]
        val dueDate: String = buttonDate.text.toString()
        val complete: Boolean = checkComplete.isChecked

        if (mTaskId.toInt() == 0) {
            val task = TaskEntity(null, idUser, priorityId, description, dueDate, complete)
            viewModelTask.insertTask(task)
        } else {
            val task = TaskEntity(mTaskId, idUser, priorityId, description, dueDate, complete)
            viewModelTask.updateTask(task)
        }
        finish()

    }

    private fun openDialogDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()

    }

    private fun setListeners() {

        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun getIndex(priorityId: Int): Int {
        var index = 0
        for (i in 0..mPriorityEntityList.size) {
            if (mPriorityEntityList[i].id.toInt() == priorityId) {
                index = i
                break
            }
        }
        return index
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            this.mTaskId = bundle.getInt(SharedPreferencesConstantes.BUNDLE.TASKID, 0).toLong()

            if (mTaskId.toInt() != 0) {
                textToolbar.setText(R.string.atualizar_tarefa)
                buttonSave.setText(R.string.atualizar_tarefa_button)
                val task = viewModelTask.get(mTaskId.toInt())
                editDescription.setText(task?.description)
                buttonDate.text = task?.dueDate
                if (task != null) {
                    checkComplete.isChecked = task.complete
                    spinnerPriority.setSelection(getIndex(task.priorityId.toInt()))
                }
            }

        }
    }

}
