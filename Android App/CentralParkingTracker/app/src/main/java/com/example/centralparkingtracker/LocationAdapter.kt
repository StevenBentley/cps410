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
    /*
    Location Adapter is used for creating the area cards displayed on the home page
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.home_card,
                parent,
                false
            )
        )
    }
    /*
    Binds the areas sent from home activity to the given cards.
     */
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        area = areas[holder.adapterPosition]
        holder.bind(area, listener)
    }
    /*
    returns the count of how many areas there are
     */
    override fun getItemCount(): Int = areas.size
    /*
      Actual construction of card. Binds the data of each area to a card then sets an
      on click listener to call onAreaClick when clicked by the user.
       */
    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(area: LocationModel, listener: AreaListener) {
            itemView.numOfLots.text = "Currently Tracking ${area.lotNum} Parking Lots"
            itemView.area_of_lots.text = area.title

            itemView.setOnClickListener { listener.onAreaClick(area) }
        }
    }
}