package sebastien.andreu.esimed.ui.list.detail.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.model.Rank

open class RecyclerViewAdapter(
    private val context: Context,
    private val display: java.util.ArrayList<Rank>,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val getContent: suspend (Long) -> (List<Pair<Bitmap, Long>>)
): RecyclerView.Adapter<ViewHolder>() {

    private var listener: ListenerRecyclerView? = null

    fun setListener (listener: ListenerRecyclerView) {
        this.listener = listener
        notifyDataSetChanged()
    }

    fun setList(list: java.util.ArrayList<Rank>) {
        display.clear()
        display.addAll(list)
        notifyDataSetChanged()
    }

    fun insert(rank: Rank) {
        display.add(rank)
        notifyItemInserted(display.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((display.size) > position) {
            holder.onBind(context, itemCount, display[position], position, listener, lifecycleScope, getContent)
        }
    }

    override fun getItemCount(): Int {
        return display.size
    }
}
