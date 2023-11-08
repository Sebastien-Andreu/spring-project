package sebastien.andreu.esimed.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.model.TierList

open class RecyclerViewAdapter(
    private val context: Context,
    private val display: java.util.ArrayList<TierList>
): RecyclerView.Adapter<ViewHolder>() {

    private var listener: ListenerRecyclerView? = null

    fun setListener (listener: ListenerRecyclerView) {
        this.listener = listener
        notifyDataSetChanged()
    }

    fun setList(list: java.util.ArrayList<TierList>) {
        display.clear()
        display.addAll(list)
        notifyDataSetChanged()
    }

    fun insert(tierList: TierList) {
        display.add(tierList)
        notifyItemInserted(display.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_tier_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((display.size) > position) {
            holder.onBind(context, itemCount, display[position], position, listener)
        }
    }

    override fun getItemCount(): Int {
        return display.size
    }
}
