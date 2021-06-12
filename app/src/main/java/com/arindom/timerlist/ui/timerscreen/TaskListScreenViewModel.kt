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
            jobId = "011",
            taskName = "Unloading1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "012",
            taskName = "Racking1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "013",
            taskName = "BackStore1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "014",
            taskName = "Cleaning aisle1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "015",
            taskName = "Remove Overstocking1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "016",
            taskName = "Cleaning Bay1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "017",
            taskName = "Unloading1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "018",
            taskName = "Racking1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "019",
            taskName = "BackStore1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "020",
            taskName = "Cleaning aisle1",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "021",
            taskName = "Remove Overstocking2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "022",
            taskName = "Cleaning Bay2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "023",
            taskName = "Unloading2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "024",
            taskName = "Racking2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "025",
            taskName = "BackStore2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "026",
            taskName = "Cleaning aisle2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "027",
            taskName = "Remove Overstocking2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "028",
            taskName = "Cleaning Bay2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "029",
            taskName = "Unloading2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "030",
            taskName = "Racking2",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "031",
            taskName = "BackStore3",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "032",
            taskName = "Cleaning aisle3",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "033",
            taskName = "Remove Overstocking3",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
        ),
        Task(
            jobId = "034",
            taskName = "Cleaning Bay3",
            taskStatus = TaskStatus.INITIAL,
            targetTimeInMilliSeconds = 20 * 60 * 1000
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
        if (mTask.taskStatus == TaskStatus.STARTED || mTask.taskStatus == TaskStatus.RESUMED)
            mTask.finishTask()
    }

    fun pauseTask(mTask: Task) {
        if (mTask.taskStatus == TaskStatus.STARTED || mTask.taskStatus == TaskStatus.RESUMED)
            mTask.pauseTask(mTask.mCounterInMillisecondLiveData.value ?: 0L)
        println("pause: $mTask")
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
        println("start: $mTask")
        mTask.coroutineJob = viewModelScope.launch(mSupervisorJob) {
            startTimer(
                startTimerInMilliSecond = if (mTask.taskStatus == TaskStatus.STARTED) 0L
                else mTask.pauseTimeInMilliseconds[mTask.pauseTimeInMilliseconds.lastIndex],
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