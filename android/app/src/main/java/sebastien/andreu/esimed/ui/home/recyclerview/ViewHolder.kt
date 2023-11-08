package sebastien.andreu.esimed.ui.home.recyclerview

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.model.TierList
class ViewHolder(
    view: View
): ViewHolder(view) {

    fun onBind(
        context: Context,
        itemCount: Int,
        tierList: TierList,
        position: Int,
        listener: ListenerRecyclerView?
    ) {
        itemView.findViewById<TextView>(R.id.title)?.text = tierList.title
        itemView.findViewById<TextView>(R.id.tag)?.text = tierList.tag

        itemView.findViewById<LinearLayout>(R.id.element)?.setOnClickListener {
            listener?.onClick(position, tierList)
        }

        itemView.findViewById<LinearLayout>(R.id.edit)?.setOnClickListener {
            listener?.onUpdate(position, tierList)
        }

//        itemView.findViewById<LinearLayout>(R.id.remove)?.setOnClickListener {
//            listener?.onRemove(position, tierList)
//        }
    }
}
