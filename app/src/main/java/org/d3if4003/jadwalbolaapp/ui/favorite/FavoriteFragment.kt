package org.d3if4003.jadwalbolaapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.d3if4003.jadwalbolaapp.adapter.FavoriteAdapter
import org.d3if4003.jadwalbolaapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
   private lateinit var binding: FragmentFavoriteBinding
   private lateinit var adapterFavorite: FavoriteAdapter
   private val viewModel: FavoriteViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterFavorite = FavoriteAdapter()
        binding.rvFavorite.adapter = adapterFavorite
        binding.rvFavorite.hasFixedSize()
        
        getData()
    }
    
    override fun onRefresh() {
        getData()
        binding.swipeRefresh.isRefreshing = false
    }
    
    private fun getData(){
        viewModel.listFavorite.observe(viewLifecycleOwner){
            adapterFavorite.setData(it)
            binding.txtEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            
        }
    }
}