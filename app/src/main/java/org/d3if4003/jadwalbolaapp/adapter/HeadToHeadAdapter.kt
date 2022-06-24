package org.d3if4003.jadwalbolaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if4003.jadwalbolaapp.R
import org.d3if4003.jadwalbolaapp.databinding.ItemHeadToHeadBinding
import org.d3if4003.jadwalbolaapp.model.HeadToHead
import org.d3if4003.jadwalbolaapp.ui.home.HomeFragmentDirections
import java.util.*

class HeadToHeadAdapter: RecyclerView.Adapter<HeadToHeadAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemHeadToHeadBinding.bind(view)
    }
    
    private val listHeadToHead = arrayListOf<HeadToHead>()
    
    fun setData(listFixture: List<HeadToHead>){
        this.listHeadToHead.clear()
        this.listHeadToHead.addAll(listFixture)
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_head_to_head, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val headToHead = listHeadToHead[position]
        
        with(holder){
            binding.apply {
                val score = "${headToHead.goals?.home} : ${headToHead.goals?.away}"
                tvScore.text = score
                tvHome.text = headToHead.teams?.home?.name!!
                tvAway.text = headToHead.teams.away?.name!!
                
                Glide.with(itemView.context)
                    .load(headToHead.teams.home.logo)
                    .into(imgHome)
    
                Glide.with(itemView.context)
                    .load(headToHead.teams.away.logo)
                    .into(imgAway)
            }
        }
    }
    
    override fun getItemCount(): Int = listHeadToHead.size
}