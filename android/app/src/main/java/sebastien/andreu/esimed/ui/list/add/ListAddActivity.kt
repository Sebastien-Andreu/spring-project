package sebastien.andreu.esimed.ui.list.add

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

@AndroidEntryPoint
class ListAddActivity: AppCompatActivity() {

    private val viewModel: ListAddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_list_activity)

        CustomToolBar(
            activity = this,
            title = "Ajouter une tier list",
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
            TierList(
                title = findViewById<CustomEditTextRounded>(R.id.editTextTitle).getValue(),
                tag = findViewById<CustomEditTextRounded>(R.id.editTextTag).getValue(),
            ).let { tierList ->
                if (tierList.allDataIsValid()) {
                    viewModel.createTierList(this, tierList)
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

        fun start(context: Context) {
            try {
                context.startActivity(Intent(context, ListAddActivity::class.java))
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}