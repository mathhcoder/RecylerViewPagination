package com.androidwave.recyclerviewpagination

import android.R.layout
import android.kotlin.recylerviewpagination.R

import android.kotlin.recylerviewpagination.BaseViewHolder
import android.kotlin.recylerviewpagination.PostItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

import butterknife.ButterKnife

class PostRecyclerAdapter(private val mPostItems: MutableList<PostItem>?) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            )
            VIEW_TYPE_LOADING -> ProgressHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )
            else -> null!!
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == mPostItems!!.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        Log.e("ENENT" , "PostRcyc getItemCount")
        return mPostItems?.size ?: 0
    }

    fun addItems(postItems: List<PostItem>?) {
        Log.e("ENENT" , "PostRcyc add item")
        mPostItems!!.addAll(postItems!!)
        notifyDataSetChanged()
    }

    fun addLoading() {
        Log.e("ENENT" , "PostRcyc add loading")
        isLoaderVisible = true
        mPostItems!!.add(PostItem())
        notifyItemInserted(mPostItems.size - 1)
    }

    fun removeLoading() {
        Log.e("ENENT" , "PostRcyc remove loading")
        isLoaderVisible = false
        val position = mPostItems!!.size - 1
        val item = getItem(position)
        if (item != null) {
            mPostItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        Log.e("ENENT" , "PostRcyc clear")
        mPostItems!!.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): PostItem {
        Log.e("ENENT" , "PostRcyc getItem")
        return mPostItems!![position]
    }

    inner class ViewHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView) {

        var textViewTitle: TextView? = itemView?.findViewById(R.id.textViewTitle)
        var textViewDescription: TextView? = itemView?.findViewById(R.id.textViewDescription)



        override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            Log.e("EVENT" , "onBind View")
            val item = mPostItems!![position]
            textViewTitle!!.text= item.title
            textViewDescription!!.text = item.description
        }


        init {
            Log.e("EVENT" , "init innerClassViewHolder i")
            ButterKnife.bind(this, itemView!!)
        }
    }

    inner class ProgressHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView) {

        override fun clear() {
            Log.e("EVENT" , "inner class Progress Holder clear")
        }

        init {
            Log.e("EVENT" , "inner class PrgoressHolder init")
            ButterKnife.bind(this, itemView!!)
        }
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }
}