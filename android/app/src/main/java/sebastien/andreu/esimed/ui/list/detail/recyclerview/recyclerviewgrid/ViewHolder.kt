package sebastien.andreu.esimed.ui.list.detail.recyclerview.recyclerviewgrid

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sebastien.andreu.esimed.R

class ViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    fun onBind(
        context: Context,
        itemCount: Int,
        value: Pair<Bitmap, Long>,
        position: Int,
        listener: ListenerRecyclerViewGrid?
    ) {
        Log.e("-----", "${value.first}, ${value.second}")

        itemView.findViewById<ImageView>(R.id.image).setImageBitmap(value.first)
    }
}
