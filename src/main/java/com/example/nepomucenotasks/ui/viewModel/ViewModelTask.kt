package com.example.nepomucenotasks.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nepomucenotasks.data.entities.TaskEntity
import com.example.nepomucenotasks.data.repository.TaskRepository
import kotlinx.coroutines.*

class ViewModelTask(private val mTaskRepository: TaskRepository) : ViewModel() {

    private val mutablelivedataInsert: MutableLiveData<Long> = MutableLiveData()
    val returnLiveDataInser: LiveData<Long> = mutablelivedataInsert

    private val mutablelivedataUpdate: MutableLiveData<Long> = MutableLiveData()
    val returnLiveDataUodate: LiveData<Long> = mutablelivedataUpdate


    fun insertTask(taskEntity: TaskEntity) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                mutablelivedataInsert.postValue(mTaskRepository.insert(taskEntity))
            } catch (e: Exception) {
                mutablelivedataInsert.postValue(0)
            }

        }
    }

    fun updateTask(taskEntity: TaskEntity) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                mTaskRepository.update(taskEntity)
                mutablelivedataUpdate.postValue(1)
            } catch (e: Exception) {
                mutablelivedataUpdate.postValue(0)
            }

        }
    }

    fun getListTask(id: Int, mFilter: Int): MutableList<TaskEntity> = mTaskRepository.getList(id, mFilter)

    fun get(id: Int): TaskEntity? = mTaskRepository.get(id)

    fun delete(id : TaskEntity) = mTaskRepository.delete(id)
}