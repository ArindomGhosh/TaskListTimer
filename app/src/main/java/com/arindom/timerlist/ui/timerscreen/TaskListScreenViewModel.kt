package com.arindom.timerlist.ui.timerscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arindom.timerlist.models.Task
import com.arindom.timerlist.models.TaskStatus
import com.arindom.timerlist.startTimer
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TaskListScreenViewModel : ViewModel() {
    private val mSupervisorJob = SupervisorJob()

    private val listOfTask = listOf<Task>(
        Task(
            jobId = "011", taskName = "Unloading",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "011", taskName = "Racking",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "011", taskName = "BackStore",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "011", taskName = "Cleaning aisle",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "011", taskName = "Remove Overstocking",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "011", taskName = "Cleaning Bay",
            taskStatus = TaskStatus.INITIAL, targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
    )

    fun getTaskAt(position: Int): Task {
        return listOfTask[position]
    }

    private val taskListLiveData = MutableLiveData<List<Task>>()

    fun getTaskListLiveData(): LiveData<List<Task>> = taskListLiveData

    fun fetchTaskList() {
        viewModelScope.launch {
            delay(1000)
            taskListLiveData.postValue(listOfTask)
        }
    }

    fun startTask(mTask: Task) {
        if (mTask.taskStatus == TaskStatus.INITIAL) {
            mTask.startTask()
            startTaskForTask(mTask)
        }
    }

    fun stopTask(mTask: Task) {
        if (mTask.taskStatus != TaskStatus.COMPLETED)
            mTask.finishTask()
    }

    fun pauseTask(mTask: Task) {
        if (mTask.taskStatus == TaskStatus.STARTED || mTask.taskStatus == TaskStatus.RESUMED)
            mTask.pauseTask()
    }

    fun resumeTask(mTask: Task) {
        if (mTask.taskStatus == TaskStatus.PAUSED) {
            mTask.resumeTask()
            startTaskForTask(mTask)
        }
    }

    fun resetTask(mTask: Task) {
        if (mTask.taskStatus != TaskStatus.COMPLETED)
            mTask.resetTask()
    }

    private fun startTaskForTask(mTask: Task) {
        mTask.coroutineJob = viewModelScope.launch(mSupervisorJob) {
            startTimer(
                startTimerInMilliSecond = if (mTask.taskStatus == TaskStatus.STARTED) 0L
                else mTask.pauseTimeInMilliseconds[mTask.pauseTimeInMilliseconds.lastIndex] - mTask.startTimeInMilliSeconds,
                endTimeInMillisecond = mTask.targetTimeInMilliSeconds
            ).collect {
                mTask.mCounterInMillisecondLiveData.postValue(it)
            }
        }
    }

    // shutdown all running task
    fun closeAllTask() {
        mSupervisorJob.cancel()
    }

    //pause all running task
    fun pauseAllRunningTask() {
    }

    //Resume all paused task
    fun resumeAllPausedTasks() {

    }

}