package com.gurcanataman.bottom_sheet_swipe

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.atan2

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    enum class Direction { TOP, DOWN, LEFT, RIGHT }
    var isExpand:Boolean = false


    var translateExpandLimit: Float = 0f
    var screenHeight: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        gestureDetector = GestureDetectorCompat(this, this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
        translateExpandLimit =
            resources.getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height).toFloat()

        bottomSheet.translationY = screenHeight //

    }




    fun collapse() {
        val animatorSet = AnimatorSet()

        val animator = ObjectAnimator.ofFloat(
            bottomSheet,
            "translationY",
            screenHeight.toFloat()
        )
        animator.duration = 200
        animator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(animator)
        animator.start()
        toolbar.alpha = 0f
    }

    fun expand() {
        val animatorSet = AnimatorSet()

        val animator = ObjectAnimator.ofFloat(
            bottomSheet,
            "translationY",
            translateExpandLimit.toFloat()
        )
        animator.duration = 200
        animator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(animator)
        animator.start()

        toolbar.alpha = 1f
    }


    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        var y: Float = -1 * distanceY

        Log.i("event",e1!!.action.toString())
        Log.i("event",e2!!.action.toString())


        if (bottomSheet.translationY > 100 && y < 0) { // Yukarı çektiğimde
            bottomSheet.translationY += y
            isExpand=true
        } else if (y > 0 && bottomSheet.scrollY==0) { // Aşağı çektiğimde
            bottomSheet.translationY += y
            isExpand = false
        }
        toolbar.alpha = (translateExpandLimit/bottomSheet.translationY)
        return true
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        if(ev!!.action==MotionEvent.ACTION_UP){
            if (!isExpand) { // Aşağı çekildiğinde
                collapse()
            } else if(isExpand) { //yukarı çekildiğinde
                expand()
            }
        }



        return gestureDetector.onTouchEvent(ev)
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

        Log.e("OnFling", "e1.y" + e1!!.y)
        Log.e("OnFling", "e2.y" + e2!!.y)

        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.i("TAG", "onShowPress")
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.i("TAG", "onLongPress")
    }

}