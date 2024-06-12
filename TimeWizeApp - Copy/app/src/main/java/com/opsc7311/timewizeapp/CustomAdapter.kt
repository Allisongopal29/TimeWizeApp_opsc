package com.opsc7311.timewizeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// import com.squareup.picasso.Picasso

class CustomAdapter(var data: List<TimesheetEntry>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.Date)
        val startTime: TextView = view.findViewById(R.id.StartTime)
        val endTime: TextView = view.findViewById(R.id.EndTime)
        val description: TextView = view.findViewById(R.id.Description)
        // val category: TextView = view.findViewById(R.id.Category)
        val imageUrl: ImageView = view.findViewById(R.id.Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.timesheet_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.date.text = item.date
        holder.startTime.text = item.startTime
        holder.endTime.text = item.endTime
        holder.description.text = item.description
        // holder.category.text = item.category
        // Picasso.get().load(item.imageUrl.toString()).into(holder.imageUrl)

    }
}