package com.gurcanataman.bottom_sheet_swipe

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.atan2

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    enum class Direction { TOP, DOWN, LEFT, RIGHT }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        gestureDetector = GestureDetectorCompat(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.i("TAG", "onShowPress")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        Log.i("TAG", "onSingleTapUp")
        return true
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        Log.i("TAG", "onDown")
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, p2: Float, p3: Float): Boolean {

        Log.e("OnFling","e1.y"+e1!!.y)
        Log.e("OnFling","e2.y"+e2!!.y)

        return true
        /*
        return when (getDirection(e1!!.x, e1.y, e2!!.x, e2.y)) {
            Direction.TOP -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }
            Direction.LEFT -> true
            Direction.DOWN -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                true
            }
            Direction.RIGHT -> true
            else -> false
        }

         */


    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, p2: Float, p3: Float): Boolean {

        e1?.y?.let { startPos->
            e2?.y?.let { endPos->
                val fark = startPos - endPos
                bottomSheetBehavior.peekHeight =  fark.toInt()
            }
        }
        val startPos =
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.i("TAG", "onLongPress")
    }


    private fun getDirection(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): Direction? {
        val angle =
            Math.toDegrees(atan2(y1 - y2.toDouble(), x2 - x1.toDouble()))
        if (angle > 45 && angle <= 135) return Direction.TOP
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) return Direction.LEFT
        if (angle < -45 && angle >= -135) return Direction.DOWN
        return if (angle > -45 && angle <= 45) Direction.RIGHT else null
    }
}