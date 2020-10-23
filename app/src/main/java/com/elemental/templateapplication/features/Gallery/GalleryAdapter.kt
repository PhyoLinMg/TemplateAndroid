package com.elemental.templateapplication.features.Gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.elemental.templateapplication.R
import com.elemental.templateapplication.databinding.ItemMediaGalleryBinding
import com.elemental.templateapplication.databinding.ItemMediaPickerBinding
import com.elemental.templateapplication.features.Gallery.data.GalleryMedia

class GalleryAdapter(private val context: Context,private var mediaList:List<GalleryMedia>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onItemClickListener:onRecyclerItemClickedListener ? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MEDIA_LIST) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_media_gallery, parent, false)
            MediaListViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_media_picker, parent, false)
            MediaPickerViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == MEDIA_LIST) {
            (holder as MediaListViewHolder).bind(mediaList[position])
        } else {
            (holder as MediaPickerViewHolder).bind(mediaList[position])
        }
    }
    inner class MediaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMediaGalleryBinding = ItemMediaGalleryBinding.bind(itemView)

        fun bind(item: GalleryMedia) {
            Glide.with(context)
                .load(item.name)
                .placeholder(R.color.colorAccent)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(binding.ivPhoto)

            binding.btnCheck.isChecked = item.isSelected

            binding.rlImage.setOnClickListener { view ->
                onItemClickListener!!.onRecyclerItemClicked(absoluteAdapterPosition, item.name, view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        //TODO :: Folder Selection (position < 2)
        return if (position < 1 && mediaList[position].title != "") MEDIA_PICKER else MEDIA_LIST
    }

    inner class MediaPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMediaPickerBinding = ItemMediaPickerBinding.bind(itemView)

        fun bind(item: GalleryMedia) {
            binding.ivPhoto.setImageResource(item.resImg)
            binding.tvTitle.text = item.title

            binding.rlImage.setOnClickListener { view ->
                onItemClickListener!!.onRecyclerItemClicked(absoluteAdapterPosition, item.name, view)
            }

        }
    }
    override fun getItemCount(): Int {
        return mediaList.size
    }
    fun setData(mediaList: List<GalleryMedia>) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(onItemClickListener:onRecyclerItemClickedListener){
        this.onItemClickListener=onItemClickListener
    }
    interface onRecyclerItemClickedListener{
        fun onRecyclerItemClicked(position:Int,name:String,v: View?)
    }

    companion object {
        const val MEDIA_LIST = 0
        const val MEDIA_PICKER = 1
    }
}