package com.adlemgames.pbk.blocks

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.adlemgames.pbk.BlocksFragment
import com.adlemgames.pbk.BlocksFragment.Companion.blocks
import com.adlemgames.pbk.BlocksFragment.Companion.connections
import com.adlemgames.pbk.BlocksFragment.Companion.selectedId
import com.adlemgames.pbk.BlocksFragment.Companion.selectedItem
import com.adlemgames.pbk.R
import xdroid.toaster.Toaster.toast

open class Block(view: View, id: String, interfaces: BlocksInterface) {
    var type: String = ""
    val id = id
    var cur_state: Boolean = false
    var selectedEl = ""
    var out: ImageView
    var input1: ImageView?
    var in1_toId: String = ""
    var in2_toId: String = ""
    var input2: ImageView?
    val interfaces = interfaces
    init {
        out = view.findViewById<ImageView>(R.id.output)
        out.setOnClickListener {
            select_out(true)
            val selected = blocks.find { it.id == BlocksFragment.selectedId }
            selectedEl = "out"
            if (selected != null) {
                validate_conn(selected)
                return@setOnClickListener
            }
            selectedItem = "out"
            BlocksFragment.selectedId = this.id
        }
        input1 = view.findViewById<ImageView>(R.id.input1)
        input1?.setOnClickListener {
            select_in1(true)
            val selected = blocks.find { it.id == BlocksFragment.selectedId }
            selectedEl = "inp1"
            if (selected != null) {
                validate_conn(selected)
                return@setOnClickListener
            }
            selectedItem = "inp1"
            BlocksFragment.selectedId = this.id
        }
        input2 = view.findViewById<ImageView>(R.id.input2)
        input2?.setOnClickListener {
            select_in2(true)
            val selected = blocks.find { it.id == BlocksFragment.selectedId }
            selectedEl = "inp2"
            if (selected != null) {
                validate_conn(selected)
                return@setOnClickListener
            }
            selectedItem = "inp2"
            BlocksFragment.selectedId = this.id
        }
    }
    fun select_out(state: Boolean) {
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        out.setColorFilter(color)
    }
    fun validate_conn(selected: Block){
        //toast("$selectedItem>$selectedEl")
        if (selectedItem != null) {
            val trans = Transition(selected, selectedItem!!, this, selectedEl)
            toast(trans.validate().toString())
        }
        /*
            when (selectedItem){
                "out" -> {
                    selected.select_out(false)
                    when (selectedEl){
                        "out" -> {
                            this.select_out(false)
                            toast("Нельзя соединить два выхода")
                        }
                        "inp1" -> {
                            this.select_in1(false)
                            mutableListOf(selected.out as View, input1 as View).let { connections.add(it) }
                        }
                        "inp2" -> {
                            this.select_in2(false)
                            mutableListOf(selected.out as View, input2 as View).let { connections.add(it) }
                        }
                    }
                    selected.selectedEl = ""
                }
                "inp1" -> {
                    selected.select_in1(false)
                    when (selectedEl){
                        "out" -> {
                            this.select_out(false)
                            mutableListOf(selected.input1 as View, out as View).let { connections.add(it) }
                        }
                        "inp1" -> {
                            this.select_in1(false)
                            toast("Нельзя соединить два входа")
                        }
                        "inp2" -> {
                            this.select_in2(false)
                            toast("Нельзя соединить два входа")
                        }
                    }
                    selected.selectedEl = ""
                }
                "inp2" -> {
                    selected.select_in2(false)
                    when (selectedEl){
                        "out" -> {
                            this.select_out(false)
                            mutableListOf(selected.input2 as View, out as View).let { connections.add(it) }
                        }
                        "inp1" -> {
                            this.select_in1(false)
                            toast("Нельзя соединить два входа")
                        }
                        "inp2" -> {
                            this.select_in2(false)
                            toast("Нельзя соединить два входа")
                        }
                    }
                    selected.selectedEl = ""
                }
        }*/
        selectedItem = ""
        selectedEl = ""
        selectedId = null
        interfaces.draw()
    }
    fun select_in1(state: Boolean) {
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        input1?.setColorFilter(color)
    }
    fun select_in2(state: Boolean) {
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        input2?.setColorFilter(color)
    }
    fun deselect_all(){
        select_out(false)
        if (input1 != null) select_in1(false)
        if (input2 != null) select_in2(false)
    }
}