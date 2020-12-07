package com.example.taboogame

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.taboogame.databinding.FragmentTitleBinding
import java.util.*

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentTitleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.startButton.setOnClickListener{
            view?.findNavController()?.navigate(TitleFragmentDirections.actionTitleFragmentToGameConfigurationFragment())
        }

        binding.settingsButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_settingsFragment)
        )

        binding.rulesButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_rulesFragment)
        )

        return binding.root
    }

}