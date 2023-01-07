package com.test.apitest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.apitest.MainActivity
import com.test.apitest.R
import com.test.apitest.adapter.PopulationAdapter
import com.test.apitest.api.ApiService
import com.test.apitest.databinding.FragmentHomeBinding
import com.test.apitest.models.Data
import com.test.apitest.repo.PopulationRepository
import com.test.apitest.utils.NetworkStatusTracker
import com.test.apitest.viewModels.PopulationViewModel
import com.test.apitest.viewModels.viewModelFactory.PopulationViewModelFactory


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private var viewModel: PopulationViewModel? = null
    private var adapter: PopulationAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        backPressCallBack()
        initViews()

        return binding?.root
    }

    private fun backPressCallBack() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (findNavController().previousBackStackEntry?.destination?.id == R.id.splachFragment)
                    requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun initViews() {
        binding?.populationDataRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        adapter = PopulationAdapter()
        binding?.populationDataRecyclerView?.adapter = this.adapter

        viewModel = ViewModelProvider(
            this,
            PopulationViewModelFactory(
                PopulationRepository(ApiService.retrofitInstance()!!),
                NetworkStatusTracker(requireContext())
            )
        )[PopulationViewModel::class.java]

        changeVisibility(View.GONE)

        viewModel?.dataAvailable?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.animationView?.visibility = View.GONE
                changeVisibility(View.VISIBLE)
            } else {
                binding?.animationView?.visibility = View.VISIBLE
                changeVisibility(View.GONE)
            }
        }

        viewModel?.usPopulations?.observe(viewLifecycleOwner) {
            adapter?.list = it.data as ArrayList<Data>
            if (it.source.isNotEmpty()) {
                val firstSource = it.source[0]
                binding?.sourceName?.text = firstSource.annotations.source_name
                binding?.sourceDescription?.text = firstSource.annotations.source_description
                binding?.dataSetName?.text = firstSource.annotations.dataset_name
            }
        }

        viewModel?.state?.observe(viewLifecycleOwner) {
            when (it) {
                PopulationViewModel.NetState.Error -> {
                    binding?.noInternet?.visibility = View.VISIBLE
                    changeVisibility(View.GONE)
                }
                PopulationViewModel.NetState.Fetched -> {
                    binding?.noInternet?.visibility = View.GONE
                    viewModel?.usPopulation()
                }
            }
        }

        viewModel?.errorMessage?.observe(viewLifecycleOwner) {
            changeVisibility(View.GONE)
            binding?.root?.let { view ->
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel?.usPopulation()
    }

    private fun changeVisibility(visibility: Int) {
        binding?.sourceNameTxt?.visibility = visibility
        binding?.sourceName?.visibility = visibility
        binding?.sourceDescriptionTxt?.visibility = visibility
        binding?.sourceDescription?.visibility = visibility
        binding?.dataSetNameTxt?.visibility = visibility
        binding?.dataSetName?.visibility = visibility
        binding?.populationTxt?.visibility = visibility
        binding?.populationDataRecyclerView?.visibility = visibility
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}