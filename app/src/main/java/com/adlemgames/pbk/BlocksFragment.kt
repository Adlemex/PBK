package com.adlemgames.pbk

import android.content.ClipData
import android.content.ClipDescription
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.widget.*
import androidx.fragment.app.Fragment
import com.adlemgames.pbk.blocks.*
import com.adlemgames.pbk.databinding.BlockAndBinding
import com.adlemgames.pbk.databinding.FragmentBlocksBinding
import kotlin.random.Random


class BlocksFragment : Fragment(), BlocksInterface {

    private var _binding: FragmentBlocksBinding? = null
    private var xDelta = 0
    private var yDelta = 0
    private var mainLayout: ViewGroup? = null
    private val binding get() = _binding!!
    private var dpCalculation = 1f
    companion object {
        val blocks = mutableListOf<Block>()
        val connections = mutableListOf<Transition>()
        var selectedId: String? = null
        var selectedItem: String? = null
        fun calc (){
            for (i in 0..4)
            for (block in blocks) {
                if (block.type == "and") (block as AndBlock).calc()
                if (block.type == "and_no") (block as AndNoBlock).calc()
                if (block.type == "or") (block as OrBlock).calc()
                if (block.type == "or_no") (block as OrNoBlock).calc()
                if (block.type == "no") (block as InvBlock).calc()
                if (block.type == "xor") (block as XOrBlock).calc()
                if (block.type == "xor_no") (block as XOrNoBlock).calc()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dpCalculation = resources.displayMetrics.density
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlocksBinding.inflate(inflater, container, false)
        blocks.clear()
        connections.clear()
        mainLayout = binding.frameLayout2 as ViewGroup
        binding.undo.setOnClickListener {
            connections.removeLastOrNull()
            this.recalc_connections()
            calc()
        }
        val items = mutableListOf(
            R.layout.block_inp,
            R.layout.block_and,
            R.layout.block_and_no,
            R.layout.block_no,
            R.layout.block_exclude_or,
            R.layout.block_exclude_or_no,
            R.layout.block_or,
            R.layout.block_or_no)
        for (item in items){
            val view = View.inflate(context, item, null)
            view.setOnLongClickListener {
                it.startDragAndDrop(
                    ClipData("Drag", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), ClipData.Item(item.toString())),
                    View.DragShadowBuilder(it),
                    it, 0
                )
            }
            binding.linear.addView(view)
            view.layoutParams = LinearLayout.LayoutParams((100 * dpCalculation).toInt(), (100 * dpCalculation).toInt())
            //img.layoutParams.height = 200;
            //img.layoutParams.width = 200;
        }
        binding.frameLayout2.setOnDragListener(dragListener)
        //binding.frameLayout2.addView(IV)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun rerender(){
        for (block in blocks){
        /*
            val out = block.findViewById<ImageView>(R.id.output)
            val inp1 = block.findViewById<ImageView>(R.id.input1)
            val inp2 = block.findViewById<ImageView>(R.id.input2)
            val state = block.findViewById<TextView>(R.id.state)
            out.setOnClickListener {
                if (selected != null){
                    (selected as ImageView).setColorFilter(Color.TRANSPARENT)
                    val sparent = (selected!!.parent as View)
                    connections.add(mutableListOf(selected!!, it))
                    binding.canvas.draw(selected!!.x+sparent.x+(selected!!.height/2),
                        selected!!.y+sparent.y+(selected!!.width/2),
                        it.x+block.x+(it.height/2),
                        it.y+block.y+(it.width/2))
                    selected = null
                    return@setOnClickListener
                }
                selected = it
                (it as ImageView).setColorFilter(Color.GREEN)
            }
            inp1?.setOnClickListener {
                if (selected != null){
                    (selected as ImageView).setColorFilter(Color.TRANSPARENT)
                    val sparent = (selected!!.parent as View)
                    connections.add(mutableListOf(selected!!, it))
                    binding.canvas.draw(selected!!.x+sparent.x+(selected!!.height/2),
                        selected!!.y+sparent.y+(selected!!.width/2),
                        it.x+block.x+(it.height/2),
                        it.y+block.y+(it.width/2))
                    selected = null
                    return@setOnClickListener
                }
                selected = it
                (it as ImageView).setColorFilter(Color.GREEN)
            }
            inp2?.setOnClickListener {
                if (selected != null){
                    (selected as ImageView).setColorFilter(Color.TRANSPARENT)
                    val sparent = (selected!!.parent as View)
                    connections.add(mutableListOf(selected!!, it))
                    binding.canvas.draw(selected!!.x+sparent.x+(selected!!.height/2),
                        selected!!.y+sparent.y+(selected!!.width/2),
                        it.x+block.x+(it.height/2),
                        it.y+block.y+(it.width/2))
                    selected = null
                    return@setOnClickListener
                }
                selected = it
                (it as ImageView).setColorFilter(Color.GREEN)
            }
            state?.setOnClickListener {
                val text = (it as TextView)
                val cur_state = text.text.toString().toInt()
                if (cur_state == 1) {
                    text.text = "0"
                    text.setBackgroundColor(Color.RED)
                }
                else {
                    text.text = "1"
                    text.setBackgroundColor(Color.GREEN)
                }

            }*/
        }
    }
    fun recalc_connections(){
        binding.canvas.lines.clear()
        for (connection in connections){
            connection.getValues()?.let { binding.canvas.lines.add(it) }
        }
        calc()
        binding.canvas.invalidate()
    }

    val onTouchListener = OnTouchListener { view, event ->
        view.performClick()
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams = view.layoutParams as RelativeLayout.LayoutParams
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
            }
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = view
                    .layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = x - xDelta
                layoutParams.topMargin = y - yDelta
                layoutParams.rightMargin = 0
                layoutParams.bottomMargin = 0
                view.layoutParams = layoutParams
                recalc_connections()
                if (x*dpCalculation > 2400) {
                    if (y*dpCalculation < 1160) binding.delete.root.visibility = View.VISIBLE
                }
                else binding.delete.root.visibility = View.INVISIBLE
                //>2400 <1160
            }
            MotionEvent.ACTION_UP -> {
                if (x*dpCalculation > 2400) {
                    if (y*dpCalculation < 1160) {
                        for (block in blocks){
                            if (block.out == view.findViewById(R.id.output)){
                                connections.removeIf {
                                    if (it.from?.id == block.id){
                                        return@removeIf true
                                    }
                                    if (it.to?.id == block.id){
                                        return@removeIf true
                                    }
                                    return@removeIf false
                                }
                            }
                        }
                        blocks.removeIf { it.out == view.findViewById(R.id.output) }
                        binding.frameLayout2.removeView(view)
                    }
                }
                binding.delete.root.visibility = View.INVISIBLE
                recalc_connections()
            }
        }
        view.invalidate()
        true
    }


    val dragListener = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                /*Toast.makeText(requireContext(), dragData, Toast.LENGTH_SHORT).show()*/
                val v = event.localState as View
                view.invalidate()
                val vview = View.inflate(requireContext(), dragData.toString().toInt(), null)
                vview.setOnTouchListener(onTouchListener)
                (mainLayout as RelativeLayout).addView(vview)
                val layoutParams = vview.layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = (event.x-20).toInt()
                vview.setOnLongClickListener {
                    (mainLayout as RelativeLayout).removeView(it)
                    return@setOnLongClickListener true
                }
                val block = when (dragData.toString().toInt()){
                    InpBlock.ID -> InpBlock(vview, blocks.size.toString(), this)
                    AndBlock.ID -> AndBlock(vview, blocks.size.toString(), this)
                    AndNoBlock.ID -> AndNoBlock(vview, blocks.size.toString(), this)
                    InvBlock.ID -> InvBlock(vview, blocks.size.toString(), this)
                    OrBlock.ID -> OrBlock(vview, blocks.size.toString(), this)
                    OrNoBlock.ID -> OrNoBlock(vview, blocks.size.toString(), this)
                    XOrNoBlock.ID -> XOrNoBlock(vview, blocks.size.toString(), this)
                    XOrBlock.ID -> XOrBlock(vview, blocks.size.toString(), this)
                    else -> Block(vview, blocks.size.toString(), this)
                }
                blocks.add(block)
                rerender()
                layoutParams.topMargin = (event.y-20).toInt()
                vview.layoutParams.width = (100 * dpCalculation).toInt()
                vview.layoutParams.height = (100 * dpCalculation).toInt()
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }

    override fun draw() {
        recalc_connections()
    }
}