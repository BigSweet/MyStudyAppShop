package widght

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * introduce：这里写介绍
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 11/3/2019
 */
class SquareVoteView : View {

    private lateinit var paintSupport: Paint
    private lateinit var paintOpp: Paint
    private var viewWidth = 0
    private var viewHeight = 0
    private var mProgress = 0f
    private var weight = 0f
    private var oppWeight = 0f
    var duration = 200L
    private var valueAnimator = ValueAnimator.ofFloat(0f, 1f)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paintSupport = Paint(Paint.ANTI_ALIAS_FLAG)
        paintOpp = Paint(Paint.ANTI_ALIAS_FLAG)
        paintSupport.style = Paint.Style.FILL
        paintOpp.style = Paint.Style.FILL
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener { animation ->
            mProgress = animation.animatedValue as Float
            invalidate()
        }
    }


    fun startAnimation() {
        valueAnimator.start()
    }

    fun setAnimDuration(time: Long) {
        valueAnimator.duration = time
        this.duration = time
    }

    fun setSupportColor(color: Int) {
        paintSupport.color = color
    }

    fun setOppColor(color: Int) {
        paintOpp.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(viewWidth, viewHeight)
    }

    fun setSupportWeight(supportNumber: Float, oppNumber: Float) {
        if (supportNumber == 0f && oppNumber == 0f) {
            this.weight = 0f
            this.oppWeight = 0f
        }
        val radio = supportNumber / (oppNumber + supportNumber)
        try {
            if (radio > 1) throw  java.lang.Exception()
        } catch (e: Exception) {
            Log.d("sw", "radio不能大于1")
            return
        }
//        if (viewWidth * radio < viewHeight / 2 || viewWidth * (1 - radio) < viewHeight / 2) {
//            try {
//                throw  java.lang.Exception()
//            } catch (e: Exception) {
//                Log.d("sw", "比值相差过大无法绘制，不能少于高度的一半")
//                return
//            }
//        }

        this.weight = radio
        this.oppWeight = 1 - weight
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val H = viewHeight.toFloat()
        val W = viewWidth.toFloat()
        val radio = weight * (W - 3 * H / 2)
        val radioOpp = oppWeight * (W - 3 * H / 2)
        if (weight > 0) {
            var rect = RectF(0f, 0f, (H), (H))
            canvas?.drawArc(rect, 90f, 180f, false, paintSupport)
            var path = Path()
            path.moveTo((H / 2), 0f)
            path.lineTo((radio) * mProgress + (H / 2), 0f)
            path.lineTo((radio - H / 2) * mProgress + H / 2, H)
            path.lineTo(H / 2.toFloat(), H)
            path.close()
            canvas?.drawPath(path, paintSupport)
        }

        if (oppWeight > 0) {
            var rectOpp = RectF(((W - H)), 0f, W, (H))
            canvas?.drawArc(rectOpp, -90f, 180f, false, paintOpp)
            var pathOpp = Path()
            pathOpp.moveTo((W - H / 2), 0f)
            pathOpp.lineTo(W - H / 2 - (radioOpp) * mProgress, 0f)
            pathOpp.lineTo((W - H) - (radioOpp) * mProgress, height.toFloat())
            pathOpp.lineTo((W - height / 2), height.toFloat())
            pathOpp.close()
            canvas?.drawPath(pathOpp, paintOpp)
        }

    }
}