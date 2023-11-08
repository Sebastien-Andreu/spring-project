package sebastien.andreu.esimed.ui.list.detail.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.ui.list.detail.recyclerview.recyclerviewgrid.ListenerRecyclerViewGrid
import sebastien.andreu.esimed.ui.list.detail.recyclerview.recyclerviewgrid.RecyclerViewGridAdapter

class ViewHolder(
    view: View
): ViewHolder(view) {

    fun onBind(
        context: Context,
        itemCount: Int,
        rank: Rank,
        position: Int,
        listener: ListenerRecyclerView?,
        lifecycleScope: LifecycleCoroutineScope,
        getContent: suspend (Long) -> (List<Pair<Bitmap, Long>>)
    ) {
        itemView.findViewById<TextView>(R.id.title)?.text = rank.title

        itemView.findViewById<ImageView>(R.id.show)?.let { view ->
            itemView.findViewById<RecyclerView>(R.id.recyclerview)?.let { recycler ->
                if (recycler.visibility == View.VISIBLE) {
                    recycler.visibility = View.GONE
                    view.setImageResource(R.drawable.arrow_up)
                } else {
                    view.setImageResource(R.drawable.arrow_down)
                    recycler.visibility = View.VISIBLE
                }

                view.setOnClickListener {
                    if (recycler.visibility == View.VISIBLE) {
                        recycler.visibility = View.GONE
                        view.setImageResource(R.drawable.arrow_up)
                    } else {
                        view.setImageResource(R.drawable.arrow_down)
                        recycler.visibility = View.VISIBLE
                    }
                }
            }
        }

        itemView.findViewById<ImageView>(R.id.update)?.setOnClickListener {
            listener?.onUpdate(position, rank)
        }

        itemView.findViewById<ImageView>(R.id.addPicture)?.setOnClickListener {
            listener?.onAddPicture(position, rank)
        }

        itemView.findViewById<RecyclerView>(R.id.recyclerview)?.let { recyclerView ->
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.layoutManager = GridLayoutManager(context, 4)
            recyclerView.adapter = RecyclerViewGridAdapter(
                context = context,
                display = arrayListOf()
            ).also { adapter ->
                adapter.setListener(object: ListenerRecyclerViewGrid {
                    override fun onDelete(position: Int, string: String) {

                    }
                })

                lifecycleScope.launch {
                    adapter.setList(getContent(rank.rankId!!))
                }
            }
        }
    }
}
