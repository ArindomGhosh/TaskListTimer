package com.arindom.timerlist.ui.timerscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arindom.timerlist.databinding.ActivityTaskListBinding

class TaskListScreenActivity : AppCompatActivity() {
    private lateinit var mActivityTaskListBinding: ActivityTaskListBinding
    private lateinit var mTaskListAdapter: TaskListAdapter
    private val mTaskListScreenViewModel: TaskListScreenViewModel by lazy { ViewModelProvider(this)[TaskListScreenViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityTaskListBinding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(mActivityTaskListBinding.root)

        mTaskListAdapter = TaskListAdapter(this@TaskListScreenActivity, mTaskListScreenViewModel)
        val layoutManager = LinearLayoutManager(this)
        mActivityTaskListBinding.rvTask.layoutManager = layoutManager
        mActivityTaskListBinding.rvTask.adapter = mTaskListAdapter
        mTaskListScreenViewModel.getTaskListLiveData().observe(this) {
            mTaskListAdapter.updateTaskList(it)
        }
        mTaskListScreenViewModel.fetchTaskList()
        /*val subjectTask = mTaskListScreenViewModel.getTaskAt(1)
        subjectTask.mCounterInMillisecondLiveData.observe(this) {
            mActivityTaskListBinding.timerWidget.tvCounter.text = "$it s"
        }

        mActivityTaskListBinding.timerWidget.ibStart.setOnClickListener {
            mTaskListScreenViewModel.startTask(subjectTask)
        }
        mActivityTaskListBinding.timerWidget.ibStop.setOnClickListener {
            mTaskListScreenViewModel.stopTask(subjectTask)
        }
        mActivityTaskListBinding.timerWidget.ibPause.setOnClickListener {
            mTaskListScreenViewModel.pauseTask(subjectTask)
        }

        mActivityTaskListBinding.timerWidget.ibResume.setOnClickListener {
            mTaskListScreenViewModel.resumeTask(subjectTask)
        }*/
    }
}