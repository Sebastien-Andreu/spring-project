package sebastien.andreu.esimed.ui.picture.add

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.extension.toTreatFor
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class PictureAddActivity: AppCompatActivity() {

    private val viewModel: PictureAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_picture_activity)

        CustomToolBar(
            activity = this,
            title = "Choisir une image",
        )

        viewModel.uploadPicture.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ToastUtils.success(this, it.message)
                    this.finish()
                }

                Status.ERROR -> {
                    ToastUtils.error(this, it.message)
                }
            }
        }

        findViewById<ImageView>(R.id.a)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

        findViewById<ImageView>(R.id.b)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

        findViewById<ImageView>(R.id.c)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

        findViewById<ImageView>(R.id.d)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

        findViewById<ImageView>(R.id.e)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

        findViewById<ImageView>(R.id.f)?.let { imageView ->
            imageView.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.uploadPicture(this@PictureAddActivity, (imageView.drawable as BitmapDrawable).bitmap, list!!, rank!!)
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        private const val TAG: String = "PictureAddActivity"
        private var list: TierList? = null
        private var rank: Rank? = null
        fun start(context: Context, list: TierList, rank: Rank) {
            try {
                context.startActivity(Intent(context, PictureAddActivity::class.java))
                this.list = list
                this.rank = rank
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}