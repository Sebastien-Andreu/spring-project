package sebastien.andreu.esimed.shared.toolbar

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import sebastien.andreu.esimed.R

class CustomToolBar(
    private val activity: AppCompatActivity,
    private val title: String? = null,
    isReturnable: Boolean = true
) {

    init {
        activity.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar?.setCustomView(R.layout.custom_toolbar)

        activity.supportActionBar?.customView?.let { toolbar ->
            toolbar.findViewById<TextView>(R.id.toolbar_title)?.text = title
        }

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(isReturnable)
    }
}