package com.adlemgames.pbk.blocks

import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.adlemgames.pbk.BlocksFragment.Companion.blocks
import com.adlemgames.pbk.BlocksFragment.Companion.calc
import com.adlemgames.pbk.R

class InpBlock(view: View, id: String, interfaces: BlocksInterface) : Block(view, id, interfaces) {
    init {
        super.type = "input"
        val state = view.findViewById<TextView>(R.id.state)
        state?.setOnClickListener {
            val text = (it as TextView)
            if (cur_state) {

                cur_state = !cur_state
                text.text = "0"
                text.setBackgroundColor(Color.RED)
            }
            else {
                cur_state = !cur_state
                text.text = "1"
                text.setBackgroundColor(Color.GREEN)
            }
            calc()
        }
    }

    companion object {
        const val ID = R.layout.block_inp
    }
}