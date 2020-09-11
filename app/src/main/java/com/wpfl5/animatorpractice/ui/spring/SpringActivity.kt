package com.wpfl5.animatorpractice.ui.spring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.wpfl5.animatorpractice.R
import com.wpfl5.animatorpractice.base.BaseActivity
import com.wpfl5.animatorpractice.databinding.ActivitySpringBinding

class SpringActivity : BaseActivity<ActivitySpringBinding>() {
    override fun getLayoutRes(): Int = R.layout.activity_spring


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

            imageView.also { img ->
                SpringAnimation(img, DynamicAnimation.TRANSLATION_Y).apply {
                    spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY

                }
            }


        }
    }
}