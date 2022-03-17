package by.godevelopment.newsappsample.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.newsappsample.R
import by.godevelopment.newsappsample.commons.TAG
import by.godevelopment.newsappsample.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        setupUi()
        setupEvent()
        return binding.root
    }

    private fun setupUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                uiState?.let {
                    Log.i(TAG, "setupUi: lifecycleScope.launchWhenStarted.collect")
                    val swipeContainer = binding.swipeContainer
                    if (!uiState.isFetchingData) {
                        binding.progress.visibility = View.GONE
                        swipeContainer.isRefreshing = false
                    } else binding.progress.visibility = View.VISIBLE

                    showToast(getString(R.string.fragment_message_total) + it.model.totalResults)

                    val adapter = NewsAdapter().apply {
                        newsList = it.model.articles
                    }
                    val manager = LinearLayoutManager(requireContext()) // GridLayoutManager(requireContext(), 2)
                    binding.rv.adapter = adapter
                    binding.rv.layoutManager = manager

                    binding.swipeContainer.apply {
                        setOnRefreshListener {
                            Log.i(
                                TAG,
                                "setupUi: setOnRefreshListener fetchImagesList()"
                            )
                            viewModel.fetchImagesList()
                        }
                        setColorSchemeResources(
                            android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light
                        )
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                Snackbar.make(binding.root,
                    R.string.alert_error_loading,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.snackbar_btn_reload))
                    { viewModel.fetchImagesList() }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
