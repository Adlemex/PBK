package com.adlemgames.pbk.blocks

import android.view.View
import com.adlemgames.pbk.BlocksFragment
import xdroid.toaster.Toaster.toast

class Transition(from: Block, item: String, to: Block, item2: String) {
    var from: Block? = null
    var from_item: String = ""
    var to: Block? = null
    var to_item: String = ""

    var success: Boolean
    var reason: String = ""
    init {
        when(item){
            "out" -> {
                when(item2) {
                    "out" -> {
                        success = false
                        reason = "Нельзя соединить два выхода"
                        this.from = from
                        this.to = to
                    }
                    else -> {
                        success = true
                        this.from = from
                        this.from_item = item
                        this.to = to
                        this.to_item = item2
                    }
                }
            }
            "inp1", "inp2" -> {
                when(item2) {
                    "out" -> {
                        success = true
                        this.from = to
                        this.from_item = item2
                        this.to = from
                        this.to_item = item
                    }
                    else -> {
                        this.from = from
                        this.to = to
                        success = false
                        reason = "Нельзя соединить два входа"
                    }
                }
            }
            else -> success = false
        }
    }
    fun validate(): Boolean {
        toast("$from_item>$to_item")
        from?.deselect_all()
        to?.deselect_all()
        if (success){
            BlocksFragment.connections.add(this)
        }
        return success
    }
    fun getValues(): MutableList<Float>? {
        val sparent = (from?.out?.parent as View)
        val sparent2 = when(to_item){
            "inp1" -> (to?.input1?.parent as View)
            "inp2" -> (to?.input2?.parent as View)
            else -> null
        }
        val from_view = from?.out
        val to_view = when(to_item){
            "inp1" -> to?.input1?.let { it as View }
            "inp2" -> to?.input2?.let { it as View }
            else -> null
        }
        if (from_view != null && to_view != null && sparent != null && sparent2 != null)
        return mutableListOf(
            from_view.x+sparent.x+(from_view.height/2),
            from_view.y+sparent.y+(from_view.width/2),
            to_view.x+sparent2.x+(to_view.height/2),
            to_view.y+sparent2.y+(to_view.width/2))
        return null
    }

}