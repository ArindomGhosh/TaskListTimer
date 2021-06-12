package com.arindom.timerlist.ui.timerscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arindom.timerlist.databinding.ItemTaskBinding
import com.arindom.timerlist.models.Task
import com.arindom.timerlist.models.TaskStatus

class TaskListAdapter(
    private val context: AppCompatActivity,
    private val mTaskListScreenViewModel: TaskListScreenViewModel
) :
    RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    private var taskList: List<Task> = emptyList()
    private var taskListAdapterEventListener: TaskListAdapterEventListener? = null

    fun setTaskListAdapterEventListener(taskListAdapterEventListener: TaskListAdapterEventListener) {
        this.taskListAdapterEventListener = taskListAdapterEventListener
    }

    inner class TaskListViewHolder(private val itemViewBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(mTask: Task) {
            itemViewBinding.tvTaskName.text = mTask.taskName
            itemViewBinding.timerWidget.ibStart.setOnClickListener {
                mTaskListScreenViewModel.startTask(mTask)
                taskListAdapterEventListener?.reorderTaskList(taskList)
            }
            itemViewBinding.timerWidget.ibPause.setOnClickListener {
                mTaskListScreenViewModel.pauseTask(mTask)
                if (mTask.taskStatus == TaskStatus.PAUSED)
                    taskListAdapterEventListener?.reorderTaskList(taskList)
            }
            itemViewBinding.timerWidget.ibStop.setOnClickListener {
                mTaskListScreenViewModel.stopTask(mTask)
                if (mTask.taskStatus == TaskStatus.COMPLETED)
                    taskListAdapterEventListener?.reorderTaskList(taskList)
            }
            itemViewBinding.timerWidget.ibResume.setOnClickListener {
                mTaskListScreenViewModel.resumeTask(mTask)
                if (mTask.taskStatus == TaskStatus.RESUMED)
                    taskListAdapterEventListener?.reorderTaskList(taskList)
            }
            mTask.mCounterInMillisecondLiveData.observe(context) {
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    interface TaskListAdapterEventListener {
        fun reorderTaskList(taskList: List<Task>)
    }
}