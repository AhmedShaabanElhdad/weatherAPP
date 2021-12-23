package com.example.feature

import android.os.Bundle
import android.view.LayoutInflater
import com.example.base.BaseActivity
import com.example.feature.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindLayout: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        // do nothing



    }

}