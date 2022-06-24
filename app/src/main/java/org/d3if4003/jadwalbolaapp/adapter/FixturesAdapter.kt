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
import org.d3if4003.jadwalbolaapp.ui.home.HomeFragmentDirections
import org.d3if4003.jadwalbolaapp.util.Const
import java.text.SimpleDateFormat
import java.util.*

class FixturesAdapter: RecyclerView.Adapter<FixturesAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemFixturesBinding.bind(view)
    }
    
    private val listFixture = arrayListOf<FixturesResponse>()
    
    fun setData(listFixture: List<FixturesResponse>){
        this.listFixture.clear()
        this.listFixture.addAll(listFixture)
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fixtures, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fixture = listFixture[position]
        
        with(holder){
            binding.apply {
                tvDate.text = Const.parseDate(fixture.fixture?.date!!)
                tvTime.text = Const.getTime(fixture.fixture.date)
                tvHome.text = fixture.teams?.home?.name!!
                tvAway.text = fixture.teams.away?.name!!
                
                Glide.with(itemView.context)
                    .load(fixture.teams.home.logo)
                    .into(imgHome)
    
                Glide.with(itemView.context)
                    .load(fixture.teams.away.logo)
                    .into(imgAway)
            }
            
            itemView.setOnClickListener {
                val favorite = Favorite(
                    fixture.fixture?.id,
                    fixture.teams?.home?.name,
                    fixture.teams?.home?.logo,
                    fixture.teams?.away?.name,
                    fixture.teams?.away?.logo,
                    fixture.fixture?.date
                )
                val action = HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(favorite = favorite)
                view.findNavController().navigate(action)
            }
        }
    }
    
    override fun getItemCount(): Int = listFixture.size
}