package org.d3if4003.jadwalbolaapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import org.d3if4003.jadwalbolaapp.MainViewModel
import org.d3if4003.jadwalbolaapp.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.isNightMode.observe(viewLifecycleOwner){
            binding.switchTheme.isChecked = it
        }
        
        binding.switchTheme.setOnCheckedChangeListener { compoundButton, b ->
            mainViewModel.setNightMode(b)
        }
    }
    
}