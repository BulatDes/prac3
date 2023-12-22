package com.example.zd3_2up

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FillScreen(private val context: Context, private val list: ArrayList<Movie>) : RecyclerView.Adapter<FillScreen.MyVH>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val root = LayoutInflater.from(context).inflate(R.layout.activity_recycle_item, parent, false)
        return MyVH(root)
    }
    fun updateList(newList: List<Movie>) {
        list.clear()
        list.addAll(newList)
    }
    inner class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.questsImageView)
        val title: TextView = itemView.findViewById(R.id.titleQuestsTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        Picasso.get().load(list[position].img).into(holder.imageView)
        //holder.imageView.setImageResource(list[position].img)
        holder.title.setText(list[position].name)
        holder.description.setText(list[position].info)
    }

    override fun getItemCount(): Int{
        return list.size
    }
}


