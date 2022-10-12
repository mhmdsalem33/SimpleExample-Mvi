package com.example.mvi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi.data.Model.Posts
import com.example.mvi.databinding.PostsRowBinding

class MainAdapter() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val diffUtilCallback = object :DiffUtil.ItemCallback<Posts>()
    {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return newItem == newItem
        }
    }

    val differ = AsyncListDiffer(this , diffUtilCallback)

    inner class ViewHolder (val binding: PostsRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostsRowBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data = differ.currentList[position]
        holder.binding.apply {
            title.text = data.title
            body.text  = data.body
        }
    }

    override fun getItemCount() = differ.currentList.size

}