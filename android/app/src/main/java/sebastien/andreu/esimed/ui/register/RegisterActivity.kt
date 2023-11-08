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

        findViewById<CustomEditTextRounded>(R.id.editTextTauxCharge)?.setFilter(InputFilterMinMax(0,100))


        findViewById<Button>(R.id.buttonRegister)?.setOnClickListener {
            User(
                name = findViewById<CustomEditTextRounded>(R.id.editTextName).getValue(),
                surname = findViewById<CustomEditTextRounded>(R.id.editTextSurname).getValue(),
                birthday = findViewById<CustomEditTextRounded>(R.id.editTextBirthday).getValue(),
                address = findViewById<CustomEditTextRounded>(R.id.editTextAddress).getValue(),
                email = findViewById<CustomEditTextRounded>(R.id.editTextEmail).getValue(),
                tel = findViewById<CustomEditTextRounded>(R.id.editTextTelNumber).getValue(),
                caMax = findViewById<CustomEditTextRounded>(R.id.editTextCAMax).getValue(),
                tauxCharge = findViewById<CustomEditTextRounded>(R.id.editTextTauxCharge).getValue(),
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