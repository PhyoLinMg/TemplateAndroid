package com.elemental.templateapplication.features.Gallery.Photo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elemental.templateapplication.R
import com.elemental.templateapplication.databinding.ItemSelectedPhotoBinding

class SelectedGalleryAdapter(private val context: Context, private var itemList:ArrayList<String>):
    RecyclerView.Adapter<SelectedGalleryAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_selected_photo, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemSelectedPhotoBinding = ItemSelectedPhotoBinding.bind(itemView)

        fun bind(item: String) {
            Glide.with(context)
                .load(item)
                //.placeholder(R.color.colorDimBackground)
                .placeholder(R.drawable.ic_photo_placeholder)
                .centerCrop()
                .into(binding.ivPhoto)

            binding.ivRemove.setOnClickListener { view ->
                onItemClickListener!!.onRemoveItemClick(adapterPosition, view)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onRemoveItemClick(position: Int, v: View?)
    }
}