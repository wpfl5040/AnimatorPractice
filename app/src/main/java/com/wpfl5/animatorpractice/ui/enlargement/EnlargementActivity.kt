package com.wpfl5.animatorpractice.ui.enlargement

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.wpfl5.animatorpractice.R
import com.wpfl5.animatorpractice.base.BaseActivity
import com.wpfl5.animatorpractice.databinding.ActivityEnlargementBinding
import com.wpfl5.animatorpractice.ext.gone
import com.wpfl5.animatorpractice.ext.visible

class EnlargementActivity : BaseActivity<ActivityEnlargementBinding>() {
    override fun getLayoutRes(): Int = R.layout.activity_enlargement

    private var currentAnimator: Animator? = null
    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            thumbButton1.setOnClickListener {
                zoomImageFromThumb(this.thumbButton1, R.drawable.image1)
            }
        }
    }


    /**
    * 뷰 확대/축소
    * 이제 필요에 따라 일반 크기의 뷰에서 확대/축소된 뷰로 애니메이션해야 합니다. 대개 일반 크기의 뷰 경계에서 확대된 크기의 뷰 경계로 애니메이션해야 합니다. 다음 메서드는 아래 작업을 실행하여 미리보기 이미지에서 더 큰 뷰로 확대하는 확대/축소 애니메이션을 구현하는 방법을 보여줍니다.
    * 고해상도 이미지를 숨겨진 '확대'된 ImageView에 할당합니다. 다음 예에서는 편의를 위해 UI 스레드에 큰 이미지 리소스를 로드합니다. UI 스레드에서의 차단을 방지하기 위해 이 로드를 별도의 스레드에서 수행한 다음, UI 스레드에 비트맵을 설정할 수 있습니다. 비트맵이 화면 크기보다 크지 않은 것이 이상적입니다.
    * ImageView의 시작 경계와 끝 경계를 계산합니다.
    * 시작 경계에서 끝 경계까지 4개의 각 위치 지정 및 크기 조정 속성 X, Y, SCALE_X, SCALE_Y를 동시에 애니메이션합니다. 이러한 4개의 애니메이션은 동시에 시작될 수 있도록 AnimatorSet에 추가됩니다.
    * 이미지가 확대된 상태에서 사용자가 화면을 터치하는 경우 유사한 애니메이션을 반대로 실행하여 다시 축소합니다. ImageView에 View.OnClickListener를 추가하여 이 작업을 실행할 수 있습니다. ImageView는 클릭 시 미리보기 이미지 크기로 다시 축소되고 공개 상태를 GONE으로 설정하여 숨깁니다.
     * **/
    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        //val expandedImageView: ImageView = findViewById(R.id.expanded_image)
        val expandedImageView = binding.expandedImage
        expandedImageView.setImageResource(imageResId)
       // expandedImageView.setImageResource(imageResId)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)

        binding.container.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visible(true)


        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        expandedImageView,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }
}