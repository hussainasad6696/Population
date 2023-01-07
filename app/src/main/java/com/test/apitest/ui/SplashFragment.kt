package com.test.apitest.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.apitest.R
import com.test.apitest.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        initViews()

        return binding?.root
    }

    private fun initViews() {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                findNavController().navigate(R.id.action_splachFragment_to_homeFragment)
            },4000
        )
    }


}