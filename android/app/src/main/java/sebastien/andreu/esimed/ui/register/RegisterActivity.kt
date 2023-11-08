package sebastien.andreu.esimed.ui.register

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
import sebastien.andreu.esimed.model.User
import sebastien.andreu.esimed.shared.custom.CustomEditTextRounded
import sebastien.andreu.esimed.shared.custom.InputFilterMinMax
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.utils.ToastUtils

@AndroidEntryPoint
class RegisterActivity: AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        CustomToolBar(
            activity = this,
            title = "Enregistrement",
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
            User(
                pseudo = findViewById<CustomEditTextRounded>(R.id.editTextPseudo).getValue(),
                email = findViewById<CustomEditTextRounded>(R.id.editTextEmail).getValue(),
                password = findViewById<CustomEditTextRounded>(R.id.editTextPassword).getValue()
            ).let { user ->
                if (user.allDataIsValid()) {
                    viewModel.createAccount(this, user)
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
        this.finish()
    }

    companion object {
        private const val TAG: String = "RegisterActivity"

        fun start(context: Context) {
            try {
                context.startActivity(Intent(context, RegisterActivity::class.java))
            } catch (exception: Exception) {
                exception.toTreatFor(TAG)
            }
        }
    }
}