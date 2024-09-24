package ru.knitforlife.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


import ru.knitforlife.databinding.FragmentCollectionBinding
import ru.knitforlife.listner.ColorClickListner
import ru.knitforlife.model.Color
import javax.inject.Inject

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyColorRecyclerViewAdapter (val listner: ColorClickListner)
    : RecyclerView.Adapter<MyColorRecyclerViewAdapter.ViewHolder>() {
    private val values: MutableList<Color> = ArrayList<Color>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listner
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = values[position]
//        holder.idView.text = item.name
//        holder.contentView.text = item.toColorString()
//        holder.contentView.setBackgroundColor(android.graphics.Color.parseColor(item.toColorString()))
//        holder.tv.setBackgroundColor(android.graphics.Color.parseColor(item.toColorString()))
        holder.bind(values[position])

    }

    override fun getItemCount(): Int = values.size

    fun  remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addList(list: List<Color>) {
        values.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: FragmentCollectionBinding,val listner: ColorClickListner) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
//        val tv: ImageView = binding.tvColor
        val tv: TextView = binding.tvColor

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        fun bind(item:Color) {

            idView.text = item.name
            contentView.text = item.toColorString()
            contentView.setBackgroundColor(android.graphics.Color.parseColor(item.toColorString()))
            tv.setBackgroundColor(android.graphics.Color.parseColor(item.toColorString()))
            binding.root.setOnClickListener{
                listner.onItemClick(item.id)
            }
        }



    }

}