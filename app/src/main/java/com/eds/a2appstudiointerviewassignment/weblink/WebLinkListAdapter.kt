package com.eds.a2appstudiointerviewassignment.weblink

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eds.a2appstudiointerviewassignment.databinding.ListItemWeblinkBinding
import com.eds.a2appstudiointerviewassignment.model.WebLink


class WebLinkListAdapter : ListAdapter<WebLink, WebLinkListAdapter.ViewHolder>(WebLinkDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemWeblinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(binding: ListItemWeblinkBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titleTextView: TextView = binding.textViewTitle
        private val urlTextView: TextView = binding.textViewUrl
        private val logoImageView: ImageView = binding.imageViewLogo

        fun bind(webLink: WebLink) {
            titleTextView.text = webLink.title
            urlTextView.text = webLink.webLink
//            val imageLoader = ImageLoader.Builder(itemView.context).componentRegistry {
//                if (SDK_INT >= 28) {
//                    add(ImageDecoderDecoder(itemView.context))
//                } else {
//                    add(GifDecoder())
//                }
//            }.build()
//            val imageRequest = ImageRequest.Builder(itemView.context).data(webLink.imageUrl).target(logoImageView).build()
//            imageLoader.enqueue(imageRequest)
            Toast.makeText(itemView.context, webLink.imageUrl, Toast.LENGTH_LONG).show()
        }
    }

    class WebLinkDiffUtil : DiffUtil.ItemCallback<WebLink>() {
        override fun areItemsTheSame(oldItem: WebLink, newItem: WebLink) =
                oldItem.webLink == newItem.webLink

        override fun areContentsTheSame(oldItem: WebLink, newItem: WebLink) =
                oldItem == newItem
    }
}