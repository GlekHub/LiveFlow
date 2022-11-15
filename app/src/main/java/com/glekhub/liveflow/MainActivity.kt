package com.glekhub.liveflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.glekhub.liveflow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            liveBtn.setOnClickListener {
                viewModel.triggerLiveData()
            }
            stateBtn.setOnClickListener {
                viewModel.triggerStateFlow()
            }
            flowBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.triggerFlow().collectLatest {
                        binding.flowText.text = it.toString()
                    }
                }
            }
            sharedBtn.setOnClickListener {
                viewModel.triggerSharedFlow()
            }
        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.liveText.text = it
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.stateText.text = it
                Snackbar.make(
                    binding.root, it, Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                binding.sharedText.text = it
                Snackbar.make(
                    binding.root, it, Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}