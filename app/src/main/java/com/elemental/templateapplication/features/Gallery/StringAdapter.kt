package com.elemental.templateapplication.features.Gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elemental.templateapplication.MyApp
import com.elemental.templateapplication.R
import com.elemental.templateapplication.databinding.ItemStringBinding

class StringAdapter(private val context: Context) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var items: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder(parent)
    }

    fun setData(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class StringViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding: ItemStringBinding = ItemStringBinding.bind(itemView)

        constructor(parent: ViewGroup) :
                this(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_string, parent, false)
                )

        fun bind(item: String) {
            binding.tvOccupation.text = item

            binding.llHolder.setOnClickListener {
                onItemClickListener!!.onItemClick(absoluteAdapterPosition, item, itemView)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: String, v: View?)
    }
}