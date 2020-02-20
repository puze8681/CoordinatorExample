package kr.puze.coordinatorexample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import android.animation.Animator
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class CustomBehaviorOpposite(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
    private var ANIMATION_DURATION: Long = 200
    private var dyDirectionSum: Int = 0
    private var isShowing: Boolean = false
    private var isHiding: Boolean = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        // 스크롤이 반대방향으로 전환
        if (dy > 0 && dyDirectionSum < 0 || dy < 0 && dyDirectionSum > 0) {
            child.animate().cancel()
            dyDirectionSum = 0
        }

        dyDirectionSum += dy

        if (dyDirectionSum > child.height) {
            showView(child)
        } else if (dyDirectionSum < -child.height) {
            hideView(child)
        }
    }

    fun hideView(view: View){
        if (isHiding || view.visibility !== View.VISIBLE) {
            return
        }

        val animator = view.animate()
            .translationY(view.height.toFloat())
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)

        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isHiding = true
            }

            override fun onAnimationEnd(animator: Animator) {
                isHiding = false
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animator: Animator) {
                // 취소되면 다시 보여줌
                isHiding = false
                showView(view)
            }

            override fun onAnimationRepeat(animator: Animator) {
                // no-op
            }
        })

        animator.start()
    }

    fun showView(view: View){
        if (isShowing || view.visibility === View.VISIBLE) {
            return
        }
        val animator = view.animate()
            .translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)

        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isShowing = true
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animator: Animator) {
                isShowing = false
            }

            override fun onAnimationCancel(animator: Animator) {
                // 취소되면 다시 숨김
                isShowing = false
                hideView(view)
            }

            override fun onAnimationRepeat(animator: Animator) {
                // no-op
            }
        })

        animator.start()
    }
}