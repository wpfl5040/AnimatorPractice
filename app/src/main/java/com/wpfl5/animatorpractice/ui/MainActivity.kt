package com.wpfl5.animatorpractice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wpfl5.animatorpractice.R
import com.wpfl5.animatorpractice.base.BaseActivity
import com.wpfl5.animatorpractice.databinding.ActivityMainBinding
import com.wpfl5.animatorpractice.ext.startAct
import com.wpfl5.animatorpractice.ui.cardflip.CardFlipActivity
import com.wpfl5.animatorpractice.ui.crossfade.CrossfadeActivity
import com.wpfl5.animatorpractice.ui.drawable.DrawableActivity
import com.wpfl5.animatorpractice.ui.enlargement.EnlargementActivity
import com.wpfl5.animatorpractice.ui.spring.SpringActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

            btnDrawable.setOnClickListener { startAct<DrawableActivity>() }
            btnCrossFade.setOnClickListener { startAct<CrossfadeActivity>() }
            btnCardFlip.setOnClickListener { startAct<CardFlipActivity>() }
            btnEnlargement.setOnClickListener { startAct<EnlargementActivity>() }
            btnSpring.setOnClickListener { startAct<SpringActivity>() }

        }
    }
}