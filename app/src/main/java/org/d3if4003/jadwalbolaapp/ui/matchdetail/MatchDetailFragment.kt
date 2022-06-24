package org.d3if4003.jadwalbolaapp.ui.matchdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import org.d3if4003.jadwalbolaapp.MainViewModel
import org.d3if4003.jadwalbolaapp.adapter.HeadToHeadAdapter
import org.d3if4003.jadwalbolaapp.databinding.FragmentMatchDetailBinding
import org.d3if4003.jadwalbolaapp.model.Favorite
import org.d3if4003.jadwalbolaapp.model.HeadToHead
import org.d3if4003.jadwalbolaapp.util.Const
import org.d3if4003.jadwalbolaapp.util.LoadingDialog
import org.d3if4003.jadwalbolaapp.workmanager.NotifyWorker
import java.util.concurrent.TimeUnit

class MatchDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentMatchDetailBinding
    private lateinit var favorite: Favorite
    private lateinit var adapterHeadToHead: HeadToHeadAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: MatchDetailViewModel by viewModels()
    private var networkAvailable = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            favorite = it.getParcelable(FAVORITE)!!
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val loadingDialog = LoadingDialog(requireContext())
        adapterHeadToHead = HeadToHeadAdapter()
        
        mainViewModel.networkAvailable.observe(viewLifecycleOwner){
            networkAvailable = it
        }
        
        with(binding){
            tvHome.text = favorite.homeTeam
            tvAway.text = favorite.awayTeam
            tvDate.text = Const.parseDate(favorite.date!!)
            tvTime.text = Const.getTime(favorite.date!!)
            loadImage(favorite.homeLogo!!, imgHome)
            loadImage(favorite.awayLogo!!, imgAway)
            
            val message = "${favorite.homeTeam} vs ${favorite.awayTeam} on ${Const.parseDate(favorite.date!!)} at ${Const.getTime(favorite.date!!)}"
            
            btnAddNotif.setOnClickListener {
                createWorkRequest(
                    message,
                    10
                )
            }
            
            btnShareMatches.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
                if (shareIntent.resolveActivity(
                    requireActivity().packageManager) != null) {
                    startActivity(shareIntent)
                }
            }
            
            rvHeadToHead.adapter = adapterHeadToHead
            rvHeadToHead.hasFixedSize()
            
            viewModel.apply {
                isLoading.observe(viewLifecycleOwner){
                    if (it){
                        loadingDialog.show()
                    } else {
                        loadingDialog.dismiss()
                    }
                }
                
                listHeadToHead.observe(viewLifecycleOwner){
                    adapterHeadToHead.setData(it)
                }
                
                getFavorite(favorite.id!!).observe(viewLifecycleOwner){
                    changeLayout(it)
                }
                
                if(networkAvailable){
                    getHeadToHead(favorite.id!!)
                } else {
                    Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun loadImage(url: String, imageView: ImageView){
        Glide.with(requireContext())
            .load(url)
            .into(imageView)
    }
    
    private fun changeLayout(isFavorite: Boolean){
        if(isFavorite){
            binding.btnAddToFavorite.text = "Delete From Favorite"
            binding.btnAddToFavorite.setOnClickListener {
                viewModel.deleteFavorite(favorite)
            }
        } else {
            binding.btnAddToFavorite.text = "Add To Favorite"
            binding.btnAddToFavorite.setOnClickListener {
                viewModel.addToFavorite(favorite)
            }
        }
    }
    
    private fun createWorkRequest(message: String,timeDelayInSeconds: Long  ) {
        val myWorkRequest = OneTimeWorkRequestBuilder<NotifyWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to "Jadwal Bola App",
                "message" to message,
            )).build()
        Toast.makeText(requireContext(), "Notification added", Toast.LENGTH_SHORT).show()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }
    
    companion object {
        const val FAVORITE = "favorite"
    }
}