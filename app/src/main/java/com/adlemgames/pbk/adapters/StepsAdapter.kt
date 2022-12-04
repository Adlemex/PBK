package com.adlemgames.pbk.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.adlemgames.pbk.adapters.StepsAdapter.ItemClickListener
import android.view.ViewGroup
import com.adlemgames.pbk.R
import android.widget.TextView

class StepsAdapter internal constructor(context: Context?, steps: Array<String>) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    private val steps: Array<String>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.step_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = steps[position]
        holder.stepText.text = step
    }

    // total number of rows
    override fun getItemCount(): Int {
        return steps.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var stepText: TextView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            stepText = itemView.findViewById(R.id.step_text)
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return steps[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    // steps is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.steps = steps
    }
}