package com.example.centralparkingtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import model.LocationModel
import kotlinx.android.synthetic.main.home_card.view.*

class LocationAdapter constructor(private var areas: List<LocationModel>,
                                    private val listener: AreaListener) :
    RecyclerView.Adapter<LocationAdapter.MainHolder>() {


    var area = LocationModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.home_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        area = areas[holder.adapterPosition]
        holder.bind(area, listener)
    }

    override fun getItemCount(): Int = areas.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(area: LocationModel, listener: AreaListener) {
            itemView.numOfLots.text = "Currently Tracking ${area.lotNum} Parking Lots"
            itemView.area_of_lots.text = area.title

            itemView.setOnClickListener { listener.onAreaClick(area) }
        }
    }
}