package com.example.delawebtestapp.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.serma.fintechtestapp.presentation.factory.DaggerViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        initViewModule()
    }

    abstract fun initDagger()

    abstract fun initViewModule()
}