package com.adlemgames.pbk.blocks

import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.adlemgames.pbk.BlocksFragment
import com.adlemgames.pbk.R
import xdroid.toaster.Toaster.toast

class XOrNoBlock(view: View, id: String, interfaces: BlocksInterface) : Block(view, id, interfaces) {
    val out_text: TextView
    init {
        out_text = view.findViewById(R.id.out_text)
        super.type = "xor_no"
    }

    fun calc(){
        val connects = mutableListOf<Transition>()
        for (conn in BlocksFragment.connections){
            if (conn.to?.id == this.id) connects.add(conn)
        }
        if (connects.size == 2){
            out_text.visibility = View.VISIBLE
            val one = connects[0].from?.cur_state
            val two = connects[1].from?.cur_state
            cur_state = two?.let { one?.xor(it) }  == false
            out_text.text = two?.let { one?.xor(it) == false }?.toInt().toString()
        }
        else {
            out_text.visibility = View.GONE
            cur_state = false
        }
    }

    companion object {
        const val ID = R.layout.block_or_no
    }
}