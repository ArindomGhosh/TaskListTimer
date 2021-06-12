package com.arindom.timerlist

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


fun startTimer(startTimerInMilliSecond: Long, endTimeInMillisecond: Long): Flow<Long> {
    var nextTimeInMillisecond = startTimerInMilliSecond
    return flow {
        while (nextTimeInMillisecond <= endTimeInMillisecond) {
            nextTimeInMillisecond += 1000
            emit(nextTimeInMillisecond)
            delay(1000)
        }
    }
}