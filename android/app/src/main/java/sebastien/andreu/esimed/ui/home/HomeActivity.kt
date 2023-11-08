package sebastien.andreu.esimed.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.extension.toTreatFor
import sebastien.andreu.esimed.listener.ListenerDialogLogout
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.shared.view.dialog.DialogLogout
import sebastien.andreu.esimed.ui.home.recyclerview.ListenerRecyclerView
import sebastien.andreu.esimed.ui.home.recyclerview.RecyclerViewAdapter
import sebastien.andreu.esimed.ui.list.add.ListAddActivity
import sebastien.andreu.esimed.ui.list.detail.ListDetailActivity
import sebastien.andreu.esimed.ui.list.update.ListUpdateActivity
import sebastien.andreu.esimed.utils.StoreUser
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuAdd -> {
                ListAddActivity.start(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        CustomToolBar(
            activity = this,
            title = "Tier list",
        )

        findViewById<RecyclerView>(R.id.recyclerview)?.let { recyclerView ->
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
            recyclerView.adapter = RecyclerViewAdapter(
                context = this,
                display = arrayListOf()
            ).also { adapter ->
                adapter.setListener(object : ListenerRecyclerView {
                    override fun onUpdate(position: Int, tierList: TierList) {
                        ListUpdateActivity.start(this@HomeActivity, tierList)
                    }

                    override fun onClick(position: Int, tierList: TierList) {
                        ListDetailActivity.start(this@HomeActivity, tierList)
                    }
                })
            }

            viewModel.getListOfTierList.observe(this) {
                when (it.first) {
                    Status.SUCCESS -> {
                        (recyclerView.adapter as RecyclerViewAdapter).setList(it.third!!)
                    }
                    Status.ERROR -> {
                        ToastUtils.error(this, it.second)
                    }
                }
            }

            viewModel.getListOfTierList(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        DialogLogout.newInstance().let {
            it.setListener(object: ListenerDialogLogout {
                override fun onLogout() {
                    this@HomeActivity.finish()
                    StoreUser.user = null
                }
                override fun onCancel() {}
            })
            it.isCancelable = false
            it.show(supportFragmentManager, "DialogLogout")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListOfTierList(this)
    }

    companion object {
        private const val TAG: String = "HomeActivity"

        fun start(context: Context) {
            try {
                context.startActivity(Intent(context, HomeActivity::class.java))
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}