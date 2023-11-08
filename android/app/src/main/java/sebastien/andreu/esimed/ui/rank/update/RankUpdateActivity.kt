package sebastien.andreu.esimed.ui.rank.update

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
class RankUpdateActivity: AppCompatActivity() {

    private val viewModel: RankUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_rank_activity)

        CustomToolBar(
            activity = this,
            title = "Modifier un rang",
        )

        findViewById<CustomEditTextRounded>(R.id.editTextTitle).setContentText(rank!!.title)

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
                rankId = rank!!.rankId,
                listId = rank!!.listId,
                title = findViewById<CustomEditTextRounded>(R.id.editTextTitle).getValue(),
                ordering = 0,
            ).let { rank ->
                if (rank.allDataIsValid()) {
                    viewModel.updateRank(this, rank)
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
        private const val TAG: String = "RankUpdateActivity"
        private var rank: Rank? = null
        fun start(context: Context, rank: Rank) {
            try {
                context.startActivity(Intent(context, RankUpdateActivity::class.java))
                this.rank = rank
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}