package com.wpfl5.animatorpractice.ui.drawable

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.wpfl5.animatorpractice.R
import com.wpfl5.animatorpractice.base.BaseActivity
import com.wpfl5.animatorpractice.databinding.ActivityDrawableBinding
import com.wpfl5.animatorpractice.ext.toast

class DrawableActivity : BaseActivity<ActivityDrawableBinding>() {
    override fun getLayoutRes(): Int = R.layout.activity_drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aniIcon = AnimatedVectorDrawableCompat
            .create(baseContext, R.drawable.animatorvectordrawable)!!
            .apply {
                registerAnimationCallback(object: Animatable2Compat.AnimationCallback() {
                    override fun onAnimationStart(drawable: Drawable?) {
                        super.onAnimationStart(drawable)
                        toast("start")
                    }

                    override fun onAnimationEnd(drawable: Drawable?) {
                        super.onAnimationEnd(drawable)
                        toast("end")
                    }
                })
            }

        binding.apply {
            icon.setImageDrawable(aniIcon)
            btnStart.setOnClickListener { aniIcon.start() }
            btnStop.setOnClickListener { aniIcon.stop() }
        }


    }
}