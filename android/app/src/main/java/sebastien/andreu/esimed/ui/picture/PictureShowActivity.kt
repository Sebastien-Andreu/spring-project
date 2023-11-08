package sebastien.andreu.esimed.ui.picture

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.extension.toTreatFor
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.ui.list.detail.ListDetailActivity
import sebastien.andreu.esimed.ui.picture.add.PictureAddActivity
import sebastien.andreu.esimed.ui.rank.add.RankAddActivity
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class PictureShowActivity: AppCompatActivity() {

    private val viewModel: PictureShowViewModel by viewModels()
    private lateinit var grid: GridLayout

    private val listBitmap = arrayListOf<Pair<Bitmap, Long>>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuAdd -> {
                PictureAddActivity.start(this, list!!, rank!!)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_picture_activity)

        CustomToolBar(
            activity = this,
            title = "Ajouter une image",
        )

        grid = findViewById(R.id.gridLayout)

        viewModel.apiResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    lifecycleScope.launch {
                        it.data?.forEach { pictureShow ->
                            viewModel.downloadPicture(this@PictureShowActivity, pictureShow.pictureId!!).let {
                                when (it.status) {
                                    Status.SUCCESS -> {
                                        it.data?.bytes()?.let { byte ->
                                            byteArrayToBitmap(byte).let { bitmap ->
                                                listBitmap.add(Pair(bitmap, pictureShow.pictureId))
                                            }
                                        }
                                    }
                                    Status.ERROR -> {
                                        ToastUtils.error(this@PictureShowActivity, it.message)
                                    }
                                }
                            }
                        }
                        addBitmapToGrid(listBitmap)
                    }
                }

                Status.ERROR -> {
                    ToastUtils.error(this, it.message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getPictureByTag(this@PictureShowActivity, list!!.tag)
        }
    }

    private fun addBitmapToGrid(bitmaps: ArrayList<Pair<Bitmap, Long>>) {
        val columnCount = 3
        val rowCount = 20
        val pictureChunks = bitmaps.chunked(columnCount * rowCount)
        for (chunk in pictureChunks) {
            for (bitmap in chunk) {
                ImageView(this).let { image ->
                    val layoutParams = GridLayout.LayoutParams().apply {
                        width = resources.getDimension(com.intuit.sdp.R.dimen._95sdp).toInt()
                        height = resources.getDimension(com.intuit.sdp.R.dimen._95sdp).toInt()
                    }
                    image.setPadding(resources.getDimension(com.intuit.sdp.R.dimen._2sdp).toInt())
                    image.layoutParams = layoutParams
                    image.setImageBitmap(bitmap.first)
                    grid.addView(image)
                    image.setOnClickListener {
                        lifecycleScope.launch {
                            Log.e("ezffffff", "${rank!!.rankId!!}, ${bitmap.second}")
                            viewModel.uploadPicture(this@PictureShowActivity, bitmap.second, rank!!.rankId!!)
                        }
                    }
                }
            }
        }
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

    companion object {
        private const val TAG: String = "PictureShowActitivy"
        private var list: TierList? = null
        private var rank: Rank? = null
        fun start(context: Context, list: TierList, rank: Rank) {
            try {
                context.startActivity(Intent(context, PictureShowActivity::class.java))
                this.list = list
                this.rank = rank
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}