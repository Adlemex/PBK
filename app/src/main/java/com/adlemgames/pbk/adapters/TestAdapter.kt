package com.adlemgames.pbk.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adlemgames.pbk.R
import com.adlemgames.pbk.models.Teory
import org.w3c.dom.Text
import xdroid.toaster.Toaster.toast

class TestAdapter(context: Activity, questList: List<String>, callback: Callback) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    var questList: List<String>
    var c: Activity
    var callback: Callback

    interface Callback {
        fun paragraph(teory: Teory?)
        fun folder(teory: Teory?)
    }

    init {
        inflater = LayoutInflater.from(context)
        c = context
        this.questList = questList
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.teory_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.text = questList.get(position)
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            toast(isChecked.toString())
        }
    }

    override fun getItemCount(): Int {
        return questList.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var checkBox: CheckBox

        init {
            checkBox = view.findViewById(R.id.checkBox)
        }
    }
}