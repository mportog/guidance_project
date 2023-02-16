package com.mportog.guidanceprojecttest.flow.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mportog.guidanceprojecttest.databinding.LivedataFlowStateSharedActivityBinding
import com.mportog.guidanceprojecttest.flow.presentation.viewmodel.FlowViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlowActivity : AppCompatActivity() {

    private val flowViewModel: FlowViewModel by viewModel()
    private lateinit var binding: LivedataFlowStateSharedActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LivedataFlowStateSharedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLabelListeners()

        //setupLabelObservers()
        setupSnackActionObservers()
    }

    private fun setupLabelListeners() = with(binding) {
        mbLivedataButton.setOnClickListener { flowViewModel.onLiveDataButtonClick() }
        mbStateflowButton.setOnClickListener { flowViewModel.onStateFlowButtonClick() }
        mbSharedflowButton.setOnClickListener { flowViewModel.onSharedFlowButtonClick() }

        mbFlowButton.setOnClickListener {
            lifecycleScope.launch {
                flowViewModel.onFlowButtonClick().collectLatest {
                    tvFlowLabel.text = it
                }
            }
        }
    }

    private fun setupLabelObservers() {
        flowViewModel.liveData.observe(this@FlowActivity) {
            binding.tvLivedataLabel.text = resources.getString(it)
        }
        lifecycleScope.launchWhenStarted {
            flowViewModel.sharedFlow.collectLatest {
                binding.tvSharedflowLabel.text = resources.getString(it)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                flowViewModel.stateFlow.collectLatest {
                    binding.tvStateflowLabel.text = resources.getString(it)
                }
            }
        }

//        lifecycleScope.launchWhenStarted {
//            flowViewModel.stateFlow.collectLatest {
//                binding.tvStateflowLabel.text = resources.getString(it)
//            }
//        }
    }

    private fun setupSnackActionObservers() {
        flowViewModel.liveData.observe(this@FlowActivity) {
            showSnackbar()
        }
        lifecycleScope.launchWhenStarted {
            flowViewModel.stateFlow.collectLatest(showSnackbar())
        }
        lifecycleScope.launchWhenStarted {
            flowViewModel.sharedFlow.collectLatest(showSnackbar())
        }
    }

    private fun showSnackbar(): suspend (value: Int) -> Unit = {
        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
    }
}