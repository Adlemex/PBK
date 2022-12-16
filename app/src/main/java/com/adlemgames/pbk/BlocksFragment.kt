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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.adlemgames.pbk.databinding.BlockAndBinding
import com.adlemgames.pbk.databinding.FragmentBlocksBinding


class BlocksFragment : Fragment() {

    private var _binding: FragmentBlocksBinding? = null
    private var xDelta = 0
    private var yDelta = 0

    private var mainLayout: ViewGroup? = null
    private val binding get() = _binding!!
    val blocks = mutableListOf<View>()
    val connections = mutableListOf<MutableList<View>>()
    private var dpCalculation = 1f
    var selected: View? = null

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
        mainLayout = binding.frameLayout2 as ViewGroup
        binding.undo.setOnClickListener {
            binding.canvas.lines.removeLastOrNull()
            binding.canvas.invalidate()
        }
        val items = mutableListOf(R.layout.block_and,
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
    fun rerender(){
        for (block in blocks){
            val out = block.findViewById<ImageView>(R.id.output)
            val inp1 = block.findViewById<ImageView>(R.id.input1)
            val inp2 = block.findViewById<ImageView>(R.id.input2)
            out.setOnClickListener {
                selected?.setBackgroundColor(Color.TRANSPARENT)
                if (selected != null){
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
                it.setBackgroundColor(Color.GREEN)
            }
            inp1?.setOnClickListener {
                selected?.setBackgroundColor(Color.TRANSPARENT)
                if (selected != null){
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
                it.setBackgroundColor(Color.GREEN)
            }
            inp2?.setOnClickListener {
                selected?.setBackgroundColor(Color.TRANSPARENT)
                if (selected != null){
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
                it.setBackgroundColor(Color.GREEN)
            }
        }
    }
    fun recalc_connections(){
        binding.canvas.lines.clear()
        for (connection in connections){
            val sparent = (connection[0].parent as View)
            val sparent2 = (connection[1].parent as View)
            binding.canvas.lines.add(mutableListOf(
                connection[0].x+sparent.x+(connection[0].height/2),
                connection[0].y+sparent.y+(connection[0].width/2),
                connection[1].x+sparent2.x+(connection[1].height/2),
                connection[1].y+sparent2.y+(connection[1].width/2)))
        }
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
                recalc_connections()
                val layoutParams = view
                    .layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = x - xDelta
                layoutParams.topMargin = y - yDelta
                layoutParams.rightMargin = 0
                layoutParams.bottomMargin = 0
                view.layoutParams = layoutParams
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
                Toast.makeText(requireContext(), dragData, Toast.LENGTH_SHORT).show()
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
                blocks.add(vview)
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
}