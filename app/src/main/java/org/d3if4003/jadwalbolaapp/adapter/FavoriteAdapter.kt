package org.d3if4003.jadwalbolaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if4003.jadwalbolaapp.R
import org.d3if4003.jadwalbolaapp.databinding.ItemFixturesBinding
import org.d3if4003.jadwalbolaapp.model.Favorite
import org.d3if4003.jadwalbolaapp.model.FixturesResponse
import org.d3if4003.jadwalbolaapp.ui.favorite.FavoriteFragmentDirections
import org.d3if4003.jadwalbolaapp.ui.home.HomeFragmentDirections
import org.d3if4003.jadwalbolaapp.util.Const
import java.text.SimpleDateFormat
import java.util.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemFixturesBinding.bind(view)
    }
    
    private val listFavorite = arrayListOf<Favorite>()
    
    fun setData(listFavorite: List<Favorite>){
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fixtures, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = listFavorite[position]
        
        with(holder){
            binding.apply {
                tvHome.text = favorite.homeTeam
                tvAway.text = favorite.awayTeam
                tvDate.text = Const.parseDate(favorite.date!!)
                tvTime.text = Const.getTime(favorite.date!!)
                
                Glide.with(itemView.context)
                    .load(favorite.homeLogo)
                    .into(imgHome)
    
                Glide.with(itemView.context)
                    .load(favorite.awayLogo)
                    .into(imgAway)
            }
            
            itemView.setOnClickListener {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToMatchDetailFragment(favorite = favorite)
                view.findNavController().navigate(action)
            }
        }
    }
    
    override fun getItemCount(): Int = listFavorite.size
}