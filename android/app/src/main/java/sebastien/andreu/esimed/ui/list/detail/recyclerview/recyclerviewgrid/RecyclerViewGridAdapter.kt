package sebastien.andreu.esimed.ui.list.detail.recyclerview.recyclerviewgrid

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sebastien.andreu.esimed.R

class RecyclerViewGridAdapter(
    private val context: Context,
    private val display: ArrayList<Pair<Bitmap, Long>>
) : RecyclerView.Adapter<ViewHolder>() {

    private var listener: ListenerRecyclerViewGrid? = null
    fun setListener (listener: ListenerRecyclerViewGrid) {
        this.listener = listener
        notifyDataSetChanged()
    }

    fun setList(list: List<Pair<Bitmap, Long>>) {
        display.clear()
        display.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_grid_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((display.size) > position) {
            holder.onBind(context, itemCount, display[position], position, listener)
        }
    }

    override fun getItemCount() = display.size
}