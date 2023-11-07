package com.surveasy.surveasy.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    AppCompatActivity() {
        lateinit var binding : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)
    }

    protected inline fun bind(crossinline action: T.() -> Unit) {
        binding.run(action)
    }
}