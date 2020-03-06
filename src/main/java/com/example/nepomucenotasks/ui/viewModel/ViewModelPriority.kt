package com.example.nepomucenotasks.ui.viewModel


import androidx.lifecycle.ViewModel
import com.example.nepomucenotasks.data.entities.PriorityEntity
import com.example.nepomucenotasks.data.repository.PriorityRespository


class ViewModelPriority(private val mPriorityRespository: PriorityRespository) : ViewModel() {

    fun getPriotiry(): List<PriorityEntity> = mPriorityRespository.getList()

    fun getPriorityInitial(id: Int): String =
            mPriorityRespository.getList().filter { it.id.toInt() == id }.map { it.description }.first()

}