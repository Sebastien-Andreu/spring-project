package sebastien.andreu.esimed.ui.connection

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.shared.custom.CustomEditTextRounded
import sebastien.andreu.esimed.shared.toolbar.CustomToolBar
import sebastien.andreu.esimed.shared.view.dialog.DialogLeaveApp
import sebastien.andreu.esimed.shared.view.dialog.listener.ListenerDialog
import sebastien.andreu.esimed.ui.client.ClientActivity
import sebastien.andreu.esimed.ui.register.RegisterActivity
import sebastien.andreu.esimed.utils.ToastUtils
import sebastien.andreu.esimed.utils.Token

@AndroidEntryPoint
class ConnectionActivity: AppCompatActivity() {

    private val viewModel: ConnectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connection_activity)

        CustomToolBar(
            activity = this,
            title = "Connexion",
        )

        viewModel.apiResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Token.value = it.message
                    ClientActivity.start(this)
                }

                Status.ERROR -> {
                    ToastUtils.error(this, it.message)
                }
            }
        }

        findViewById<Button>(R.id.buttonLogin)?.setOnClickListener {
            findViewById<CustomEditTextRounded>(R.id.editTextLogin)?.let { email ->
                findViewById<CustomEditTextRounded>(R.id.editTextPassword)?.let { password ->
                    if (!email.isEmpty() && !password.isEmpty()) {
                        viewModel.connect(this, email.getValue(), password.getValue())
                    } else {
                        ToastUtils.error(this, getString(R.string.input_failed))
                    }
                }
            }
        }

        findViewById<Button>(R.id.buttonRegister)?.setOnClickListener {
            RegisterActivity.start(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        DialogLeaveApp.newInstance().let {
            it.setListener(object: ListenerDialog {
                override fun onValidate() {
                    this@ConnectionActivity.finishAndRemoveTask()
                }
            })
            it.isCancelable = false
            it.show(supportFragmentManager, "TAG_DIALOG_LEAVE_APP")
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<CustomEditTextRounded>(R.id.editTextLogin)?.let {
            it.setContentText("")
            it.requestFocus()
        }
        findViewById<CustomEditTextRounded>(R.id.editTextPassword)?.setContentText("")
    }
}