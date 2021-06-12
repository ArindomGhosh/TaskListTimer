package com.arindom.timerlist.models

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job

enum class TaskStatus() {
    STARTED,
    RESUMED,
    PAUSED,
    INITIAL,
    COMPLETED
}

data class Task(
    val jobId: String,
    val taskName: String,
    var taskStatus: TaskStatus,
    var startTimeInMilliSeconds: Long = 0L,
    var pauseTimeInMilliseconds: MutableList<Long> = mutableListOf(),
    var completeTimeInMilliSeconds: Long = 0L,
    val targetTimeInMilliSeconds: Long = 0L,
    var coroutineJob: Job? = null,
    val mCounterInMillisecondLiveData: MutableLiveData<Long> = MutableLiveData()
) {
    fun startTask() {
        this.taskStatus = TaskStatus.STARTED
        this.startTimeInMilliSeconds = System.currentTimeMillis()
    }

    fun pauseTask(pauseTime :Long) {
        this.taskStatus = TaskStatus.PAUSED
        this.pauseTimeInMilliseconds.add(pauseTime)
        this.coroutineJob?.cancel()?.also {
            this.coroutineJob = null
        }
    }

    fun resumeTask() {
        this.taskStatus = TaskStatus.RESUMED
    }

    fun finishTask() {
        this.taskStatus = TaskStatus.COMPLETED
        this.completeTimeInMilliSeconds = System.currentTimeMillis()
        this.coroutineJob?.cancel().also { this.coroutineJob = null }
    }

    fun resetTask() {
        this.taskStatus = TaskStatus.INITIAL
        this.startTimeInMilliSeconds = 0L
        this.pauseTimeInMilliseconds = mutableListOf()
        this.coroutineJob?.cancel().also { this.coroutineJob = null }
    }
}
