package com.wpfl5.animatorpractice.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VDB: ViewDataBinding>
    : AppCompatActivity() {
    lateinit var binding: VDB

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this@BaseActivity

    }


    fun <T> LiveData<T>.observing(function: (T) -> Unit) {
        observe(this@BaseActivity, Observer{ function(it) })
    }


}