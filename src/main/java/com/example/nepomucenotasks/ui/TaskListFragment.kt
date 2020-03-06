package com.example.nepomucenotasks.ui

import SwipeToDeleteCallback
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.adapter.TaskListAdapter
import com.example.nepomucenotasks.data.comom.SecurityPreferences
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import com.example.nepomucenotasks.ui.viewModel.ViewModelPriority
import com.example.nepomucenotasks.ui.viewModel.ViewModelTask
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel


class TaskListFragment : Fragment(), View.OnClickListener {

    private var mContext: Context? = null
    private lateinit var mRecyclerView: RecyclerView
    private val viewModel: ViewModelTask by viewModel()
    private val viewModelPriority: ViewModelPriority by viewModel()
    private var mSecurityPreferences: SecurityPreferences? = null
    private var mTaskFilter: Int = 0
    // private lateinit var mIOnTaskListFragmentInteractionListener : IOnTaskListFragmentInteractionListener
    private lateinit var mApdapter: TaskListAdapter
    private lateinit var rootView: View

    companion object {
        fun newInstance(taskfilter: Int): TaskListFragment {

            val bundle = Bundle()
            bundle.putInt(SharedPreferencesConstantes.Taskfilter.KEY, taskfilter)
            val fragment = TaskListFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTaskFilter = arguments!!.getInt(SharedPreferencesConstantes.Taskfilter.KEY)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        mContext = rootView.context
        rootView.findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener(this)
        mRecyclerView = rootView.findViewById(R.id.taskList)
        mSecurityPreferences = SecurityPreferences(mContext!!)
        val idTask = mSecurityPreferences!!.getStoredString(SharedPreferencesConstantes.Key.USER_ID)
        mApdapter = TaskListAdapter(viewModel.getListTask(mTaskFilter, idTask!!.toInt()), viewModelPriority, rootView.context)
        enableSwipeToDeleteAndUndo(rootView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)


        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTask()
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.fabAddTask -> {
                val intent = Intent(mContext, TaskFormActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadTask() {
        val idTask = mSecurityPreferences!!.getStoredString(SharedPreferencesConstantes.Key.USER_ID)
        mRecyclerView.adapter = TaskListAdapter(
                viewModel.getListTask(mTaskFilter, idTask!!.toInt()),
                viewModelPriority, rootView.context
        )
    }

    private fun enableSwipeToDeleteAndUndo(view: View) {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {

                val position = viewHolder.adapterPosition
                val idTask = mSecurityPreferences!!.getStoredString(SharedPreferencesConstantes.Key.USER_ID)
                val list =  viewModel.getListTask(mTaskFilter, idTask!!.toInt())
                val taskEntity = list[position]
                viewModel.delete(taskEntity)

                val snack = Snackbar.make(view, "Item removido com sucesso.", Snackbar.LENGTH_LONG)
                snack.setAction("UNDO") {
                    // adapter!!.restoreItem(item, position)
                    mRecyclerView.scrollToPosition(position)
                }

                snack.setActionTextColor(Color.YELLOW)
                snack.show()
                loadTask()

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(mRecyclerView)

    }


}
