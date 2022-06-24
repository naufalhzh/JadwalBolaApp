package org.d3if4003.jadwalbolaapp.ui.home

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.d3if4003.jadwalbolaapp.MainViewModel
import org.d3if4003.jadwalbolaapp.R
import org.d3if4003.jadwalbolaapp.adapter.FixturesAdapter
import org.d3if4003.jadwalbolaapp.databinding.FragmentHomeBinding
import org.d3if4003.jadwalbolaapp.util.LoadingDialog
import org.d3if4003.jadwalbolaapp.util.NetworkStateReceiver

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{
    
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fixturesAdapter: FixturesAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    private var networkAvailable = true
   
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val loadingDialog = LoadingDialog(requireContext())
        fixturesAdapter = FixturesAdapter()
    
        with(binding){
            rvFixtures.adapter = fixturesAdapter
            rvFixtures.hasFixedSize()
            binding.swipeRefresh.setOnRefreshListener(this@HomeFragment)
            
            viewModel.apply {
                isLoading.observe(viewLifecycleOwner){
                    if(it){
                        loadingDialog.show()
                    } else {
                        loadingDialog.dismiss()
                    }
                }
                
                errorMessage.observe(viewLifecycleOwner){
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                
                listFixtures.observe(viewLifecycleOwner){
                    fixturesAdapter.setData(it)
                    txtEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
        mainViewModel.networkAvailable.observe(viewLifecycleOwner){
            networkAvailable = it
            if(it){
                getData()
            }
        }
        getData()
    }
    
    override fun onRefresh() {
        getData()
        binding.swipeRefresh.isRefreshing = false
    }
    
    private fun getData(){
        if(networkAvailable){
            viewModel.getAllFixtures()
        } else {
            Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setting){
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    
}