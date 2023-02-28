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

open class Block(view: View, id: String, interfaces: BlocksInterface) { // класс для блоков, остальные наследуют его
    var type: String = "" // основные параметры
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
        out = view.findViewById<ImageView>(R.id.output) // настраиваем выход
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
        out.parent.increaseTouchArea(1000)
        input1 = view.findViewById<ImageView>(R.id.input1)// настраиваем вход 1 если есть
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
        input1?.parent?.increaseTouchArea(1000)
        input2 = view.findViewById<ImageView>(R.id.input2)// настраиваем вход 2 если есть
        input2?.parent?.increaseTouchArea(1000)
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
    fun select_out(state: Boolean) { // нажали оут
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        out.setColorFilter(color)
    }
    fun validate_conn(selected: Block){ // проверить возможно ли соединение
        //toast("$selectedItem>$selectedEl")
        if (selectedItem != null) {
            val trans = Transition(selected, selectedItem!!, this, selectedEl)
            if (!trans.validate()) toast(trans.reason)
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
    fun select_in1(state: Boolean) { // выбран вход 1
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        input1?.setColorFilter(color)
    }
    fun select_in2(state: Boolean) { //выбран вход 2
        val color = when(state){
            true -> Color.GREEN
            false -> Color.TRANSPARENT
        }
        input2?.setColorFilter(color)
    }
    fun deselect_all(){ // снять выделение со всего
        select_out(false)
        if (input1 != null) select_in1(false)
        if (input2 != null) select_in2(false)
    }
}