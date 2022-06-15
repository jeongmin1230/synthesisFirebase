package com.example.synthesisfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.thing.text = itemList[position].thing
        holder.needCount.text = itemList[position].needCount
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val thing: TextView = itemView.findViewById(R.id.tvInputThing)
        val needCount: TextView = itemView.findViewById(R.id.tvInputNeedCount)
    }
}