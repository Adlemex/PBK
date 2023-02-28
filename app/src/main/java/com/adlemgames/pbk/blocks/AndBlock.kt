package com.adlemgames.pbk.blocks

import android.graphics.Color
import android.graphics.Rect
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import com.adlemgames.pbk.BlocksFragment
import com.adlemgames.pbk.R
import xdroid.toaster.Toaster.toast
fun Boolean.toInt() = if (this) 1 else 0 // нужно
fun ViewParent.increaseTouchArea(increaseBy: Int) {
    val rect = Rect()
    val view = this as View
    view.getHitRect(rect)
    rect.top -= increaseBy    // increase top hit area
    rect.left -= increaseBy   // increase left hit area
    rect.bottom += increaseBy // increase bottom hit area
    rect.right += increaseBy  // increase right hit area
    view.touchDelegate = TouchDelegate(rect, view)
}
class AndBlock(view: View, id: String, interfaces: BlocksInterface) : Block(view, id, interfaces) {
    val out_text: TextView
    init {
        out_text = view.findViewById(R.id.out_text)
        super.type = "and" // блок и
    }

    fun calc(){
        val connects = mutableListOf<Transition>()
        for (conn in BlocksFragment.connections){
            if (conn.to?.id == this.id) connects.add(conn)
        }
        if (connects.size == 2){ // есть 2 соединения можно показывать результат
            out_text.visibility = View.VISIBLE
            val one = connects[0].from?.cur_state
            val two = connects[1].from?.cur_state
            cur_state = two?.let { one?.and(it) } == true // логика работы этого элемента
            out_text.text = two?.let { one?.and(it)?.toInt() }.toString()
        }
        else {
            out_text.visibility = View.GONE
            cur_state = false
        }
    }

    companion object { // id этого блока
        const val ID = R.layout.block_and
    }
}