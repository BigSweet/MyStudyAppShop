package com.demo.swt.mystudyappshop

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.abs


/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 2021/3/24
 */
class ComboProgressView : View {
    private lateinit var linePaint: Paint
    private lateinit var backLinePaint: Paint
    private lateinit var progressPaint: Paint
    private lateinit var startTextPaint: Paint
    private lateinit var endTextPaint: Paint
    private var bgColor: Int = 0
    var currentComboNum = 0f
    var progressHeight = 0f
    var textHeight = 0f
    private var mTotalWidth = 0
    var feverMap = hashMapOf<Int, Int>()

    var feverIndex = 0 //只能从0开始，因为map的下标是从0开始 要和map的size-1比较

    var startPadding = 27
    var lineWidth = 357 //去除了lineY的线

    var startFeverName = "0"
    var endFeverName = "FEV.1"
    var measureName = "FEV.15"//只是用来测量宽度
    var lineY: Float = 0f
    var feverList = mutableListOf<String>()
    private val mStartTextBounds = Rect()
    private val mendTextBounds = Rect()
    var sensorType = ""
    var animatorProgress = 0f
    var backAnimatorProgress = 0f
    var animator = ValueAnimator.ofFloat(0f, 1f)
    var backAnimator = ValueAnimator.ofFloat(1f, 0f)
    var listener: IndexListener? = null

    interface IndexListener {
        fun updateIndex(index: Int)
        fun startNormalCombo(totalComboNum: Int)
        fun startBigCombo(totalComboNum: Int)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context, null) {
        init(context, null)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val a: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ComboProgress)
        bgColor = a.getColor(R.styleable.ComboProgress_comboBgColor, 0x000000)
        var tvColor = a.getColor(R.styleable.ComboProgress_comboTvColor, 0x000000)
        var backColor = a.getColor(R.styleable.ComboProgress_backColor, 0x000000)
        progressHeight = a.getDimension(R.styleable.ComboProgress_comboProgressHeight, 0f)
        sensorType = a.getString(R.styleable.ComboProgress_sensorType) ?: ""
        var levelTextSize = a.getDimension(R.styleable.ComboProgress_comboTextSize, 0f)
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        startTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        endTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint?.let {
            it.strokeCap = Paint.Cap.ROUND
            it.strokeWidth = progressHeight * 2
        }
        backLinePaint?.let {
            it.strokeCap = Paint.Cap.ROUND
            it.strokeWidth = progressHeight
        }
        progressPaint?.let {
            it.strokeCap = Paint.Cap.ROUND
            it.strokeWidth = progressHeight
        }
        startTextPaint.textSize = levelTextSize
        startTextPaint.color = tvColor
        endTextPaint.textSize = levelTextSize
        endTextPaint.color = tvColor
        backLinePaint.color = backColor
        lineY = progressHeight
        initFeverName()
        initComboValue()
        initComboColor()
        currentProgressColor = colorList[0]
        initAnimator()
    }

    private fun initAnimator() {
        animator.addUpdateListener {
            animatorProgress = it.animatedValue as Float
            invalidate()
        }
        animator.interpolator = AccelerateDecelerateInterpolator()
        backAnimator.interpolator = AccelerateDecelerateInterpolator()
        backAnimator.addUpdateListener {
            backAnimatorProgress = it.animatedValue as Float
            invalidate()
        }

    }


    var colorList = mutableListOf<ComboProgressColor>()
    lateinit var currentProgressColor: ComboProgressColor
    private fun initComboColor() {
        val color1 = ComboProgressColor(Color.parseColor("#FFAFFA2A"), Color.parseColor("#FF00FCB4"))
        val color2 = ComboProgressColor(Color.parseColor("#FF00BED6"), Color.parseColor("#FF00FCB4"))
        val color3 = ComboProgressColor(Color.parseColor("#FFFF26E7"), Color.parseColor("#FF00BED6"))
        colorList.add(color1)
        colorList.add(color2)
        colorList.add(color3)
    }

    private fun initComboValue() {
        if (sensorType == Constants.TYPE_DUMBBELL) {
            for (i in 0 until feverList.size) {
                feverMap[i] = Constants.COMBO_DUMBBELL_FEVER1 * (i + 1)
            }
        } else {
            feverMap[0] = Constants.COMBO_CYCLE_FEVER1
            for (i in 1 until feverList.size) {
                feverMap[i] = Constants.COMBO_CYCLE_FEVER1 * (i) * 3
            }
        }
    }

    private fun initFeverName() {
        if (sensorType == Constants.TYPE_DUMBBELL) {
            for (i in 1..6) {
                feverList.add("FEV.${i}")
            }
        } else {
            for (i in 1..15) {
                feverList.add("FEV.${i}")
            }
        }
    }

    fun addComboNumber(rpm: Float, comboNum: Int) {
        backIndex = 0
        isBack = false
        if (feverIndex > feverMap.size - 1) {
            return
        }
        currentComboNum += 1
        if (currentComboNum > (feverMap[feverIndex] ?: 1000000)) {
            //清空当前的progress，更新左右俩边的文字,更新分数加成,回调出去分数加成有变化
            feverIndex++
            if (feverIndex > feverMap.size - 1) {
                return
            }
            currentComboNum = 1f
            listener?.updateIndex(feverIndex)
            currentProgressColor = colorList[feverIndex / 5]
//            lastStop = lineStart + lineWidth / (feverMap[feverIndex] ?: 1)
            lastStop = lineStart
            listener?.startBigCombo(comboNum)
        } else {
            listener?.startNormalCombo(comboNum)
        }
        if (feverIndex == 0) {
            startFeverName = "0"
        } else {
            startFeverName = feverList[feverIndex - 1]
        }
        endFeverName = feverList[feverIndex]
        if (feverIndex > feverMap.size - 1) {
            //达到了最高的段位分数
        } else {
            var time = 0L
            if (rpm == 0f) {
                time = 250L
            } else {
                ((60 / rpm / 2 * 1000) - 100).toLong()
            }
            animator.duration = time
            animator.start()
        }
    }

    fun reset() {
        //本来就是初始状态就不要刷新了
        if (currentComboNum == 0f) {
            return
        }
        feverIndex = 0
        currentComboNum = 0f
        startFeverName = "0"
        endFeverName = "FEV.1"
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (heightMode != MeasureSpec.EXACTLY) {
            textHeight = (startTextPaint.descent() - startTextPaint.ascent())
            val maxHeight = Math.max(progressHeight * 2, textHeight)
            height = (paddingTop + paddingBottom + maxHeight).toInt()
        }

        startTextPaint.getTextBounds(startFeverName, 0, startFeverName.length, mStartTextBounds)
        endTextPaint.getTextBounds(measureName, 0, measureName.length, mendTextBounds)

        var measureWidth = mendTextBounds.width() + startPadding + lineY * 2 + lineWidth + startPadding + mendTextBounds.width()
        lineStart = mendTextBounds.width() + startPadding + lineY
        lastStop = lineStart
        lastStart = lineStart

        if (widthMode != MeasureSpec.EXACTLY) {

        }
        setMeasuredDimension(measureWidth.toInt(), height)
        mTotalWidth = measuredWidth - paddingLeft - paddingRight
    }

    var lineStart = 0f
    var lastStart = 0f
    var lastStop = 0f
    var backLastStop = 0f
    var currentProgressWidth = 0f


    override fun onDraw(canvas: Canvas) {

        val height = mStartTextBounds.height() + paddingTop + paddingBottom
        val tvBaseLine = ((height + (abs(startTextPaint.ascent()) - startTextPaint.descent())) / 2).toInt()

        var startX = 0f
        if (feverIndex == 0) {
            startX = (mendTextBounds.width() - mStartTextBounds.width()).toFloat()
        } else {
//            Log.d("swt", "feverIndex不等于0" + startFeverName)
        }
        canvas.drawText(
                startFeverName,
                startX,
                tvBaseLine * 1f,
                startTextPaint
        )
        val endTvHeight = mendTextBounds.height() + paddingTop + paddingBottom
        val tvEndBaseLine = ((endTvHeight + (abs(endTextPaint.ascent()) - endTextPaint.descent())) / 2).toInt()
        canvas.drawText(
                endFeverName,
                (lineStart + lineWidth + startPadding),
                tvEndBaseLine * 1.0f,
                endTextPaint
        )

        linePaint.color = bgColor
        canvas.drawLine(
                lineStart,
                lineY,
                lineStart + lineWidth,
                lineY,
                linePaint
        )


        //画下面的进度条
        if (currentComboNum > 0) {
            currentProgressWidth = if (feverMap[feverIndex] == null) {
                lineWidth.toFloat()
            } else {
                ((currentComboNum * 1.0f) / (feverMap[feverIndex]
                        ?: 1)) * lineWidth
            }
            // 左边设置进度条的渐变色
            var progressStartColor = currentProgressColor.startColor
            var progressEndColor = currentProgressColor.endColor
            var progressShader: Shader = LinearGradient(
                    0f, getHeight().toFloat(),
                    currentProgressWidth.toFloat(), getHeight().toFloat(),
                    progressStartColor, progressEndColor, Shader.TileMode.CLAMP
            )
            progressPaint.shader = progressShader

            canvas.drawLine(
                    lineStart,
                    lineY,
                    lineStart + currentProgressWidth * animatorProgress,
                    lineY,
                    progressPaint
            )
            if (animatorProgress >= 1) {
                lastStart = lineStart
                lastStop = lineStart + currentProgressWidth
                backLastStop = lastStop
                animatorProgress = 0f
            }
            canvas.drawLine(
                    lastStart,
                    lineY,
                    lastStop,
                    lineY,
                    progressPaint
            )

        }

        if (isBack) {
            //回退当前的1/4
            if (backIndex < 4) {
                //擦除线条
                //回退的1/4进度值
                if (backAnimatorProgress <= 0) {
//                    backAnimatorProgress = 1f
                }
                canvas.drawLine(
                        backLastStop,
                        lineY,
                        backLastStop + backPro * backAnimatorProgress,
                        lineY,
                        backLinePaint
                )
                canvas.drawLine(
                        lineStart,
                        lineY,
                        backLastStop,
                        lineY,
                        backLinePaint
                )
            }
        }


    }

    var isBack = false
    var backPro = 0f
    var backIndex = 0
    fun backAnimator() {
        ++backIndex
        if (backIndex > 4) {
            return
        }
        backPro = (lastStop - lineStart) * 1 / 4
        backLastStop -= backPro
        Log.d("swt", "回退的起点" + backLastStop + "回退的进度" + backPro)
        isBack = true
        backAnimator.duration = 200L
        backAnimator.start()
    }

}