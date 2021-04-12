package com.globant.marvelapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.globant.shared.presentation.iu.widgets.DialogLoader

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {
    private var dialogLoader: DialogLoader? = null

    private lateinit var binding: DB
    protected fun dataBinding(): DB = binding
    protected abstract fun initializeView()
    protected abstract fun initObservers()

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            dialogLoader = DialogLoader(it)
        }
        initializeView()
        initObservers()
    }

    protected fun showLoader() {
        dialogLoader?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    protected fun hideLoader() {
        dialogLoader?.dismiss()
    }
}