package com.example.nepomucenotasks.data.interfaces


import com.example.nepomucenotasks.data.entities.PriorityEntity

interface IPriority {

    fun getList(): MutableList<PriorityEntity>
}