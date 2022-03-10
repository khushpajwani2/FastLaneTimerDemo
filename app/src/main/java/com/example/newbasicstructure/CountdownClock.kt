package com.example.newbasicstructure

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.newbasicstructure.util.getDateData
import com.example.newbasicstructure.util.mServerDateFormat
import kotlinx.android.synthetic.main.view_countdown_clock_digit.view.*
import kotlinx.android.synthetic.main.view_simple_clock.view.*

class CountDownClock : LinearLayout {
    private var countDownTimer: CountDownTimer? = null
    private var countdownListener: CountdownCallBack? = null
    private var countdownTickInterval = 1000

    private var almostFinishedCallbackTimeInSeconds: Int = 5

    private var resetSymbol: String = "8"

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        View.inflate(context, R.layout.view_simple_clock, this)

        attrs?.let {
            val typedArray =
                context?.obtainStyledAttributes(attrs, R.styleable.CountDownClock, defStyleAttr, 0)

            val resetSymbol = typedArray?.getString(R.styleable.CountDownClock_resetSymbol)
            if (resetSymbol != null) {
                setResetSymbol(resetSymbol)
            }

            val digitTopDrawable =
                typedArray?.getDrawable(R.styleable.CountDownClock_digitTopDrawable)
            val digitBottomDrawable =
                typedArray?.getDrawable(R.styleable.CountDownClock_digitBottomDrawable)
            setDigitDrawable(digitTopDrawable, digitBottomDrawable)
            /*setDigitBottomDrawable(digitBottomDrawable)*/
            val digitDividerColor =
                typedArray?.getColor(R.styleable.CountDownClock_digitDividerColor, 0)
            setDigitDividerColor(digitDividerColor ?: 0)
            val digitSplitterColor =
                typedArray?.getColor(R.styleable.CountDownClock_digitSplitterColor, 0)
            setDigitSplitterColor(digitSplitterColor ?: 0)

            val digitTextColor = typedArray?.getColor(R.styleable.CountDownClock_digitTextColor, 0)
            setDigitTextColor(digitTextColor ?: 0)

            val digitTextSize =
                typedArray?.getDimension(R.styleable.CountDownClock_digitTextSize, 0f)

            setDigitTextSize(firstDigitDay, secondDigitDay, digitTextSize ?: 0f)
            setDigitTextSize(firstDigitHour, secondDigitHour, digitTextSize ?: 0f)
            setDigitTextSize(firstDigitMinute, secondDigitMinute, digitTextSize ?: 0f)
            setDigitTextSize(firstDigitSecond, secondDigitSecond, digitTextSize ?: 0f)

            setSplitterDigitTextSize(digitTextSize ?: 0f)

            val digitPadding = typedArray?.getDimension(R.styleable.CountDownClock_digitPadding, 0f)
            setDigitPadding(digitPadding?.toInt() ?: 0)

            val splitterPadding =
                typedArray?.getDimension(R.styleable.CountDownClock_splitterPadding, 0f)
            setSplitterPadding(splitterPadding?.toInt() ?: 0)

            val halfDigitHeight =
                typedArray?.getDimensionPixelSize(R.styleable.CountDownClock_halfDigitHeight, 0)
            val digitWidth =
                typedArray?.getDimensionPixelSize(R.styleable.CountDownClock_digitWidth, 0)
            setHalfDigitHeightAndDigitWidth(halfDigitHeight ?: 0, digitWidth ?: 0)

            val animationDuration =
                typedArray?.getInt(R.styleable.CountDownClock_animationDuration, 0)
            setAnimationDuration(animationDuration ?: 600)

            val almostFinishedCallbackTimeInSeconds =
                typedArray?.getInt(R.styleable.CountDownClock_almostFinishedCallbackTimeInSeconds,
                    5)
            setAlmostFinishedCallbackTimeInSeconds(almostFinishedCallbackTimeInSeconds ?: 5)

            val countdownTickInterval =
                typedArray?.getInt(R.styleable.CountDownClock_countdownTickInterval, 1000)
            this.countdownTickInterval = countdownTickInterval ?: 1000

            invalidate()
            typedArray?.recycle()
        }
    }

    ////////////////
    // Public methods
    ////////////////

    fun startCountDown(timeToNextEvent: Long) {
        countDownTimer?.cancel()
        var hasCalledAlmostFinished = false
        countDownTimer = object : CountDownTimer(timeToNextEvent, countdownTickInterval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished / 1000 <= almostFinishedCallbackTimeInSeconds && !hasCalledAlmostFinished) {
                    hasCalledAlmostFinished = true
                    countdownListener?.countdownAboutToFinish()
                }
                setCountDownTime("2022-03-10T11:58:25.208Z")
            }

            override fun onFinish() {
                hasCalledAlmostFinished = false
                countdownListener?.countdownFinished()
            }
        }
        countDownTimer?.start()
    }

    fun resetCountdownTimer() {
        countDownTimer?.cancel()

        firstDigitDay.setNewText(resetSymbol)
        secondDigitDay.setNewText(resetSymbol)

        firstDigitHour.setNewText(resetSymbol)
        secondDigitHour.setNewText(resetSymbol)

        firstDigitMinute.setNewText(resetSymbol)
        secondDigitMinute.setNewText(resetSymbol)
        firstDigitSecond.setNewText(resetSymbol)
        secondDigitSecond.setNewText(resetSymbol)
    }

    ////////////////
    // Private methods
    ////////////////

    fun setCountDownTime(endDate: String) {
        val date = getDateData(endDate, mServerDateFormat)

        val dayString = date.days.toString()
        val hourString = date.hours.toString()
        val minutesString = date.minutes.toString()
        val secondsString = date.seconds.toString()

        when (dayString.length) {
            2 -> {
                firstDigitDay.animateTextChange(dayString[0].toString())
                secondDigitDay.animateTextChange(dayString[1].toString())
            }
            1 -> {
                firstDigitDay.animateTextChange("0")
                secondDigitDay.animateTextChange(dayString[0].toString())
            }
            else -> {
                firstDigitDay.animateTextChange("5")
                secondDigitDay.animateTextChange("9")
            }
        }

        when (hourString.length) {
            2 -> {
                firstDigitHour.animateTextChange(hourString[0].toString())
                secondDigitHour.animateTextChange(hourString[1].toString())
            }
            1 -> {
                firstDigitHour.animateTextChange("0")
                secondDigitHour.animateTextChange(hourString[0].toString())
            }
            else -> {
                firstDigitHour.animateTextChange("5")
                secondDigitHour.animateTextChange("9")
            }
        }
        when (minutesString.length) {
            2 -> {
                firstDigitMinute.animateTextChange(minutesString[0].toString())
                secondDigitMinute.animateTextChange(minutesString[1].toString())
            }
            1 -> {
                firstDigitMinute.animateTextChange("0")
                secondDigitMinute.animateTextChange(minutesString[0].toString())
            }
            else -> {
                firstDigitMinute.animateTextChange("5")
                secondDigitMinute.animateTextChange("9")
            }
        }
        when (secondsString.length) {
            2 -> {
                firstDigitSecond.animateTextChange(secondsString[0].toString())
                secondDigitSecond.animateTextChange(secondsString[1].toString())
            }
            1 -> {
                firstDigitSecond.animateTextChange("0")
                secondDigitSecond.animateTextChange(secondsString[0].toString())
            }
            else -> {
                firstDigitSecond.animateTextChange(secondsString[secondsString.length - 2].toString())
                secondDigitSecond.animateTextChange(secondsString[secondsString.length - 1].toString())
            }
        }
    }

    private fun setResetSymbol(resetSymbol: String?) {
        resetSymbol?.let {
            if (it.isNotEmpty()) {
                this.resetSymbol = resetSymbol
            } else {
                this.resetSymbol = ""
            }
        } ?: kotlin.run {
            this.resetSymbol = ""
        }
    }

    private fun setDigitDrawable(digitTopDrawable: Drawable?, digitBottomDrawable: Drawable?) {
        if (digitTopDrawable != null && digitBottomDrawable != null) {
            setDigit(firstDigitDay, digitTopDrawable, digitBottomDrawable)
            setDigit(secondDigitDay, digitTopDrawable, digitBottomDrawable)
            setDigit(firstDigitHour, digitTopDrawable, digitBottomDrawable)
            setDigit(secondDigitHour, digitTopDrawable, digitBottomDrawable)
            setDigit(firstDigitMinute, digitTopDrawable, digitBottomDrawable)
            setDigit(secondDigitMinute, digitTopDrawable, digitBottomDrawable)
            setDigit(firstDigitSecond, digitTopDrawable, digitBottomDrawable)
            setDigit(secondDigitSecond, digitTopDrawable, digitBottomDrawable)
        } else {
            setTransparentBackgroundColor()
        }
    }

    private fun setDigit(
        digit: CountDownDigit?,
        digitDrawable: Drawable,
        digitBottomDrawable: Drawable,
    ) {
        digit?.frontUpper?.background = digitDrawable
        digit?.frontLower?.background = digitBottomDrawable
        digit?.backUpper?.background = digitDrawable
        digit?.backLower?.background = digitBottomDrawable
    }

    private fun setDigitDividerColor(digitDividerColor: Int) {
        var dividerColor = digitDividerColor
        if (dividerColor == 0) {
            dividerColor = ContextCompat.getColor(context, android.R.color.transparent)
        }
        firstDigitDay.digitDivider.setBackgroundColor(dividerColor)
        secondDigitDay.digitDivider.setBackgroundColor(dividerColor)
        firstDigitHour.digitDivider.setBackgroundColor(dividerColor)
        secondDigitHour.digitDivider.setBackgroundColor(dividerColor)
        firstDigitMinute.digitDivider.setBackgroundColor(dividerColor)
        secondDigitMinute.digitDivider.setBackgroundColor(dividerColor)
        firstDigitSecond.digitDivider.setBackgroundColor(dividerColor)
        secondDigitSecond.digitDivider.setBackgroundColor(dividerColor)
    }

    private fun setDigitSplitterColor(digitsSplitterColor: Int) {
        if (digitsSplitterColor != 0) {
            dayDigitsSplitter.setTextColor(digitsSplitterColor)
            hourDigitsSplitter.setTextColor(digitsSplitterColor)
            minuteDigitsSplitter.setTextColor(digitsSplitterColor)
        } else {
            dayDigitsSplitter.setTextColor(ContextCompat.getColor(context,
                android.R.color.transparent))
            hourDigitsSplitter.setTextColor(ContextCompat.getColor(context,
                android.R.color.transparent))
            minuteDigitsSplitter.setTextColor(ContextCompat.getColor(context,
                android.R.color.transparent))
        }
    }

    private fun setSplitterDigitTextSize(digitsTextSize: Float) {
        dayDigitsSplitter.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        hourDigitsSplitter.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        minuteDigitsSplitter.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
    }

    private fun setDigitPadding(digitPadding: Int) {
        firstDigitDay.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
        secondDigitDay.setPadding(digitPadding, digitPadding, 0, digitPadding)
        firstDigitHour.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
        secondDigitHour.setPadding(digitPadding, digitPadding, 0, digitPadding)
        firstDigitMinute.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
        secondDigitMinute.setPadding(digitPadding, digitPadding, 0, digitPadding)
        firstDigitSecond.setPadding(0, digitPadding, digitPadding, digitPadding)
        secondDigitSecond.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
    }

    private fun setSplitterPadding(splitterPadding: Int) {
        dayDigitsSplitter.setPadding(splitterPadding, 0, splitterPadding, 0)
        hourDigitsSplitter.setPadding(splitterPadding, 0, splitterPadding, 0)
        minuteDigitsSplitter.setPadding(splitterPadding, 0, splitterPadding, 0)
    }

    private fun setDigitTextColor(digitsTextColor: Int) {
        var textColor = digitsTextColor
        if (textColor == 0) {
            textColor = ContextCompat.getColor(context, android.R.color.transparent)
        }
        // Upper
        firstDigitDay.frontUpperText.setTextColor(textColor)
        firstDigitDay.backUpperText.setTextColor(textColor)
        secondDigitDay.frontUpperText.setTextColor(textColor)
        secondDigitDay.backUpperText.setTextColor(textColor)

        firstDigitHour.frontUpperText.setTextColor(textColor)
        firstDigitHour.backUpperText.setTextColor(textColor)
        secondDigitHour.frontUpperText.setTextColor(textColor)
        secondDigitHour.backUpperText.setTextColor(textColor)

        firstDigitMinute.frontUpperText.setTextColor(textColor)
        firstDigitMinute.backUpperText.setTextColor(textColor)
        secondDigitMinute.frontUpperText.setTextColor(textColor)
        secondDigitMinute.backUpperText.setTextColor(textColor)

        firstDigitSecond.frontUpperText.setTextColor(textColor)
        firstDigitSecond.backUpperText.setTextColor(textColor)
        secondDigitSecond.frontUpperText.setTextColor(textColor)
        secondDigitSecond.backUpperText.setTextColor(textColor)

        // Lower
        firstDigitDay.frontLowerText.setTextColor(textColor)
        firstDigitDay.backLowerText.setTextColor(textColor)
        secondDigitDay.frontLowerText.setTextColor(textColor)
        secondDigitDay.backLowerText.setTextColor(textColor)

        firstDigitHour.frontLowerText.setTextColor(textColor)
        firstDigitHour.backLowerText.setTextColor(textColor)
        secondDigitHour.frontLowerText.setTextColor(textColor)
        secondDigitHour.backLowerText.setTextColor(textColor)

        firstDigitMinute.frontLowerText.setTextColor(textColor)
        firstDigitMinute.backLowerText.setTextColor(textColor)
        secondDigitMinute.frontLowerText.setTextColor(textColor)
        secondDigitMinute.backLowerText.setTextColor(textColor)

        firstDigitSecond.frontLowerText.setTextColor(textColor)
        firstDigitSecond.backLowerText.setTextColor(textColor)
        secondDigitSecond.frontLowerText.setTextColor(textColor)
        secondDigitSecond.backLowerText.setTextColor(textColor)
    }

    private fun setDigitTextSize(
        digit: CountDownDigit,
        secondDigit: CountDownDigit,
        digitsTextSize: Float,
    ) {
        digit.frontUpperText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        digit.frontLowerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        digit.backUpperText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        digit.backLowerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)

        secondDigit.frontUpperText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        secondDigit.frontLowerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        secondDigit.backUpperText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
        secondDigit.backLowerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
    }

    private fun setHalfDigitHeightAndDigitWidth(halfDigitHeight: Int, digitWidth: Int) {
        setHeightAndWidthToView(firstDigitDay.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitDay.backUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitDay.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitDay.backUpper, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitHour.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitHour.backUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitHour.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitHour.backUpper, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitMinute.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitMinute.backUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitMinute.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitMinute.backUpper, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitSecond.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitSecond.backUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitSecond.frontUpper, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitSecond.backUpper, halfDigitHeight, digitWidth)

        // Lower
        setHeightAndWidthToView(firstDigitDay.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitDay.backLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitDay.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitDay.backLower, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitHour.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitHour.backLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitHour.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitHour.backLower, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitMinute.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitMinute.backLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitMinute.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitMinute.backLower, halfDigitHeight, digitWidth)

        setHeightAndWidthToView(firstDigitSecond.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(firstDigitSecond.backLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitSecond.frontLower, halfDigitHeight, digitWidth)
        setHeightAndWidthToView(secondDigitSecond.backLower, halfDigitHeight, digitWidth)

        // Dividers
        firstDigitDay.digitDivider.layoutParams.width = digitWidth
        secondDigitDay.digitDivider.layoutParams.width = digitWidth

        firstDigitHour.digitDivider.layoutParams.width = digitWidth
        secondDigitHour.digitDivider.layoutParams.width = digitWidth

        firstDigitMinute.digitDivider.layoutParams.width = digitWidth
        secondDigitMinute.digitDivider.layoutParams.width = digitWidth

        firstDigitSecond.digitDivider.layoutParams.width = digitWidth
        secondDigitSecond.digitDivider.layoutParams.width = digitWidth
    }

    private fun setHeightAndWidthToView(view: View, halfDigitHeight: Int, digitWidth: Int) {
        val firstDigitMinuteFrontUpperLayoutParams = view.layoutParams
        firstDigitMinuteFrontUpperLayoutParams.height = halfDigitHeight
        firstDigitMinuteFrontUpperLayoutParams.width = digitWidth
        firstDigitMinute.frontUpper.layoutParams = firstDigitMinuteFrontUpperLayoutParams
    }

    private fun setAnimationDuration(animationDuration: Int) {
        firstDigitDay.setAnimationDuration(animationDuration.toLong())
        secondDigitDay.setAnimationDuration(animationDuration.toLong())
        firstDigitHour.setAnimationDuration(animationDuration.toLong())
        secondDigitHour.setAnimationDuration(animationDuration.toLong())
        firstDigitMinute.setAnimationDuration(animationDuration.toLong())
        secondDigitMinute.setAnimationDuration(animationDuration.toLong())
        firstDigitSecond.setAnimationDuration(animationDuration.toLong())
        secondDigitSecond.setAnimationDuration(animationDuration.toLong())
    }

    private fun setAlmostFinishedCallbackTimeInSeconds(almostFinishedCallbackTimeInSeconds: Int) {
        this.almostFinishedCallbackTimeInSeconds = almostFinishedCallbackTimeInSeconds
    }

    private fun setTransparentBackgroundColor() {
        val transparent = ContextCompat.getColor(context, android.R.color.transparent)

        firstDigitDay.frontLower.setBackgroundColor(transparent)
        firstDigitDay.backLower.setBackgroundColor(transparent)
        secondDigitDay.frontLower.setBackgroundColor(transparent)
        secondDigitDay.backLower.setBackgroundColor(transparent)

        firstDigitHour.frontLower.setBackgroundColor(transparent)
        firstDigitHour.backLower.setBackgroundColor(transparent)
        secondDigitHour.frontLower.setBackgroundColor(transparent)
        secondDigitHour.backLower.setBackgroundColor(transparent)

        firstDigitMinute.frontLower.setBackgroundColor(transparent)
        firstDigitMinute.backLower.setBackgroundColor(transparent)
        secondDigitMinute.frontLower.setBackgroundColor(transparent)
        secondDigitMinute.backLower.setBackgroundColor(transparent)
        firstDigitSecond.frontLower.setBackgroundColor(transparent)
        firstDigitSecond.backLower.setBackgroundColor(transparent)
        secondDigitSecond.frontLower.setBackgroundColor(transparent)
        secondDigitSecond.backLower.setBackgroundColor(transparent)
    }

    ////////////////
    // Listeners  //
    ////////////////

    public fun setCountdownListener(countdownListener: CountdownCallBack) {
        this.countdownListener = countdownListener
    }

    public interface CountdownCallBack {
        fun countdownAboutToFinish()
        fun countdownFinished()
    }
}