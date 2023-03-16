package com.mportog.guidanceprojecttest.flow.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import com.mportog.guidanceprojecttest.flow.presentation.viewmodel.DataFlowViewModel
import guidance_project.app.databinding.LivedataFlowStateSharedActivityBinding

class FlowActivity : AppCompatActivity() {

    private val dataFlowViewModel: DataFlowViewModel by viewModel()
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
        mbLivedataButton.setOnClickListener { dataFlowViewModel.onLiveDataButtonClick() }
        mbStateflowButton.setOnClickListener { dataFlowViewModel.onStateFlowButtonClick() }
        mbSharedflowButton.setOnClickListener { dataFlowViewModel.onSharedFlowButtonClick() }

        mbFlowButton.setOnClickListener {
            lifecycleScope.launch {
                dataFlowViewModel.onFlowButtonClick().collectLatest {
                    tvFlowLabel.text = it
                }
            }
        }
    }

    private fun setupLabelObservers() {
        dataFlowViewModel.liveData.observe(this@FlowActivity) {
            binding.tvLivedataLabel.text = resources.getString(it)
        }
        lifecycleScope.launchWhenStarted {
            dataFlowViewModel.sharedFlow.collectLatest {
                binding.tvSharedflowLabel.text = resources.getString(it)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                dataFlowViewModel.stateFlow.collectLatest {
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
        dataFlowViewModel.liveData.observe(this@FlowActivity) {
            showSnackbar()
        }
        lifecycleScope.launchWhenStarted {
            dataFlowViewModel.stateFlow.collectLatest(showSnackbar())
        }
        lifecycleScope.launchWhenStarted {
            dataFlowViewModel.sharedFlow.collectLatest(showSnackbar())
        }
    }

    private fun showSnackbar(): suspend (value: Int) -> Unit = {
        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
    }
}