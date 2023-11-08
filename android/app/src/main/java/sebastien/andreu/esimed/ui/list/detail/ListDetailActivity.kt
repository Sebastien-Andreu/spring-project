package sebastien.andreu.esimed.ui.list.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.api.moduleApi.LOGIN
import sebastien.andreu.esimed.extension.toTreatFor
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.ui.list.detail.recyclerview.ListenerRecyclerView
import sebastien.andreu.esimed.ui.list.detail.recyclerview.RecyclerViewAdapter
import sebastien.andreu.esimed.ui.picture.PictureShowActivity
import sebastien.andreu.esimed.ui.rank.add.RankAddActivity
import sebastien.andreu.esimed.ui.rank.update.RankUpdateActivity
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class ListDetailActivity: AppCompatActivity() {

    private val viewModel: ListDetailViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuAdd -> {
                RankAddActivity.start(this, tierList!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_detail_activity)

        CustomToolBar(
            activity = this,
            title = tierList!!.title,
        )

        findViewById<RecyclerView>(R.id.recyclerview)?.let { recyclerView ->
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = RecyclerViewAdapter(
                context = this,
                display = arrayListOf(),
                lifecycleScope = lifecycleScope,
                getContent = {
                    getContent(it)
                }
            ).also { adapter ->
                adapter.setListener(object : ListenerRecyclerView {
                    override fun onUpdate(position: Int, rank: Rank) {
                        RankUpdateActivity.start(this@ListDetailActivity, rank)
                    }

                    override fun onAddPicture(position: Int, rank: Rank) {
                        PictureShowActivity.start(this@ListDetailActivity, tierList!!, rank)
                    }

                    override fun onClick(position: Int, rank: Rank) {

                    }
                })
            }

            viewModel.getListOfRank.observe(this) {
                when (it.first) {
                    Status.SUCCESS -> {
                        (recyclerView.adapter as RecyclerViewAdapter).setList(it.third!!)
                    }
                    Status.ERROR -> {
                        ToastUtils.error(this, it.second)
                    }
                }
            }

            viewModel.getListOfRank(this, tierList!!)
        }

    }

    suspend fun getContent(rankId: Long): List<Pair<Bitmap, Long>> {
        val list = mutableListOf<Pair<Bitmap, Long>>()
        return lifecycleScope.async {
            viewModel.getPictureByRank(this@ListDetailActivity, rankId).let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.forEach { pictureShow ->
                            viewModel.downloadPicture(this@ListDetailActivity, pictureShow.pictureId!!).let {
                                when (it.status) {
                                    Status.SUCCESS -> {
                                        it.data?.bytes()?.let { byte ->
                                            byteArrayToBitmap(byte).let { bitmap ->
                                                list.add(Pair(bitmap, pictureShow.pictureId))
                                            }
                                        }
                                    }
                                    Status.ERROR -> {
                                        ToastUtils.error(this@ListDetailActivity, it.message)
                                    }
                                }
                            }
                        }
                        list
                    }
                    Status.ERROR -> {
                        ToastUtils.error(this@ListDetailActivity, it.message)
                        emptyList<Pair<Bitmap, Long>>()
                    }
                }
            }
        }.await()
    }

    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListOfRank(this, tierList!!)
    }

    companion object {
        private const val TAG: String = "ListDetailActivity"
        private var tierList: TierList? = null
        fun start(context: Context, list: TierList) {
            try {
                context.startActivity(Intent(context, ListDetailActivity::class.java))
                this.tierList = list
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}