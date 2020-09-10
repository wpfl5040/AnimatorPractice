package com.wpfl5.animatorpractice.ui.crossfade

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wpfl5.animatorpractice.R
import com.wpfl5.animatorpractice.base.BaseActivity
import com.wpfl5.animatorpractice.databinding.ActivityCrossfadeBinding
import com.wpfl5.animatorpractice.ext.gone

class CrossfadeActivity : BaseActivity<ActivityCrossfadeBinding>() {
    override fun getLayoutRes(): Int = R.layout.activity_crossfade

    private var shortAnimationDuration: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shortAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)

        binding.apply {
            content.apply {
                alpha = 0f
                this.gone()
                animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration.toLong())
                    .setListener(null)
            }

            loadingSpinner.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        loadingSpinner.gone()
                    }
                })
        }


    }


}