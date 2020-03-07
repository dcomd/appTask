package com.example.nepomucenotasks.data.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import com.example.nepomucenotasks.data.entities.TaskEntity
import com.example.nepomucenotasks.ui.TaskFormActivity
import com.example.nepomucenotasks.ui.viewModel.ViewModelPriority
import kotlinx.android.synthetic.main.row_task_linhas.view.*

class TaskListAdapter(
        private var list: MutableList<TaskEntity>,
        private var viewModel: ViewModelPriority,
        private val context: Context

) :
        RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.row_task_linhas, parent, false)
        return TaskViewHolder(inflate)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskList = list[position]
        holder.bindData(taskList, viewModel,context)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtDescricao = itemView.txtDescricao
        var txtPriority = itemView.txtPriority
        var txtDate = itemView.txtDuoDate
        var imageTask = itemView.imageTask


        fun bindData(taskEntity: TaskEntity, viewModel: ViewModelPriority, context: Context) {
            txtDescricao.text = taskEntity.description

            txtPriority.text = viewModel.getPriorityInitial(taskEntity.priorityId.toInt())
            txtDate.text = taskEntity.dueDate

            if (taskEntity.complete) {
                imageTask.setImageResource(R.drawable.ic_done)
            }

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(SharedPreferencesConstantes.BUNDLE.TASKID, taskEntity.id!!.toInt())

                val intent = Intent(context, TaskFormActivity::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }
}