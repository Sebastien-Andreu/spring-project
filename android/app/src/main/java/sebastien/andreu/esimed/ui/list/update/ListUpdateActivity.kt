package sebastien.andreu.esimed.ui.list.update

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
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.shared.custom.CustomEditTextRounded
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.utils.ToastUtils
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ListUpdateActivity: AppCompatActivity() {

    private val viewModel: ListUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_list_activity)

        CustomToolBar(
            activity = this,
            title = "Ajouter une tier list",
        )

        findViewById<CustomEditTextRounded>(R.id.editTextTitle).setupEditText(text = tierList!!.title)
        findViewById<CustomEditTextRounded>(R.id.editTextTag).setupEditText(text = tierList!!.tag)

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
            TierList(
                listId = tierList!!.listId,
                title = findViewById<CustomEditTextRounded>(R.id.editTextTitle).getValue(),
                tag = findViewById<CustomEditTextRounded>(R.id.editTextTag).getValue(),
                updateTime = DateTimeFormatter
                    .ofPattern("HH:mm:ss.SSS")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
            ).let { tierList ->
                if (tierList.allDataIsValid()) {
                    viewModel.updateTierList(this, tierList)
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
        private const val TAG: String = "ClientAddActivity"
        private var tierList: TierList? = null

        fun start(context: Context, tierList: TierList) {
            try {
                context.startActivity(Intent(context, ListUpdateActivity::class.java))
                Companion.tierList = tierList
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}