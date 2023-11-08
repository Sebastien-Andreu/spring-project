package sebastien.andreu.esimed.ui.rank.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.extension.toTreatFor
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.custom.CustomEditTextRounded
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class RankAddActivity: AppCompatActivity() {

    private val viewModel: RankAddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_rank_activity)

        CustomToolBar(
            activity = this,
            title = "Ajouter un rang",
        )

        viewModel.apiResponse.observe(this) {
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


        findViewById<Button>(R.id.buttonRegister)?.setOnClickListener {
            Rank(
                listId = list!!.listId!!,
                title = findViewById<CustomEditTextRounded>(R.id.editTextTitle).getValue(),
                ordering = 0,
            ).let { tierList ->
                if (tierList.allDataIsValid()) {
                    viewModel.createRank(this, tierList)
                } else {
                    ToastUtils.error(this, getString(R.string.input_failed))
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
        private const val TAG: String = "RankAddActivity"
        private var list: TierList? = null
        fun start(context: Context, list: TierList) {
            try {
                context.startActivity(Intent(context, RankAddActivity::class.java))
                Companion.list = list
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}