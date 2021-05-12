package com.arindom.timerlist.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.arindom.timerlist.R
import com.arindom.timerlist.databinding.CustomTimerBinding

class TimerWidget : ConstraintLayout {
    private lateinit var mBinding: CustomTimerBinding

    constructor(context: Context, attributes: AttributeSet?, defStyleAttributes: Int) : super(
        context,
        attributes,
        defStyleAttributes
    )

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0)
    constructor(context: Context) : this(context, null)

    init {
        initialize()
    }

    private fun initialize() {
        mBinding = CustomTimerBinding.inflate(LayoutInflater.from(context), this, true)
        inflate(context, R.layout.custom_timer, this)
    }

}