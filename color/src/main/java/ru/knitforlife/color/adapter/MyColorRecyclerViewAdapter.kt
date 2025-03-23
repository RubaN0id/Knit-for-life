package ru.knitforlife.color.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.knitforlife.color.databinding.FragmentCollectionBinding
import ru.knitforlife.color.listner.ColorClickListner
import ru.knitforlife.core.model.Color
import androidx.core.graphics.toColorInt

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [PlaceholderItem].
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
       holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    fun  remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addList(list: List<Color>) {
        values.addAll(list)
        notifyItemRangeInserted(values.size-list.size,list.size)
    }


    inner class ViewHolder(val binding: FragmentCollectionBinding,val listner: ColorClickListner) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val tv: TextView = binding.tvColor

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        fun bind(item: Color) {

            idView.text = item.name
            contentView.text = item.toColorString()
            tv.setBackgroundColor(item.toColorString().toColorInt())
            binding.root.setOnClickListener{
                listner.onItemClick(item.id)
            }
        }



    }

}