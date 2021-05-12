package com.arindom.timerlist.ui.timerscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arindom.timerlist.databinding.ItemTaskBinding
import com.arindom.timerlist.models.Task

class TaskListAdapter(
    private val context: AppCompatActivity,
    private val mTaskListScreenViewModel: TaskListScreenViewModel
) :
    RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    private var taskList: List<Task> = emptyList()


    inner class TaskListViewHolder(private val itemViewBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(mTask: Task) {
            itemViewBinding.tvTaskName.text = mTask.taskName
            itemViewBinding.timerWidget.ibStart.setOnClickListener {
                mTaskListScreenViewModel.startTask(mTask)
            }
            itemViewBinding.timerWidget.ibPause.setOnClickListener {
                mTaskListScreenViewModel.pauseTask(mTask)
            }
            itemViewBinding.timerWidget.ibStop.setOnClickListener {
                mTaskListScreenViewModel.stopTask(mTask)
            }
            itemViewBinding.timerWidget.ibResume.setOnClickListener {
                mTaskListScreenViewModel.resumeTask(mTask)
            }

            mTask.mCounterInMillisecondLiveData.observe(context){
                itemViewBinding.timerWidget.tvCounter.text = "$it s"
            }
        }
    }

    fun updateTaskList(taskList: List<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        return TaskListViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}