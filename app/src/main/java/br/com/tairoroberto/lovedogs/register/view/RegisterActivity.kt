package br.com.tairoroberto.lovedogs.register.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.hideKeyboard
import br.com.tairoroberto.lovedogs.base.extension.showProgress
import br.com.tairoroberto.lovedogs.register.contract.RegisterContract
import br.com.tairoroberto.lovedogs.register.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private val presenter: RegisterContract.Presenter = RegisterPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {

        window.sharedElementExitTransition = ChangeBounds()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter.attachView(this)

        register_button.setOnClickListener({
            hideKeyboard()
            showProgress(true)

            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            presenter.sendRegister(
                    editName.text.toString(),
                    editAddress.text.toString(),
                    editPhone.text.toString(),
                    editEmail.text.toString(),
                    editPassword.text.toString())
        })
    }

    override fun getContext(): Context {
        return this
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showErrorRegister(str: String) {
        showProgress(false)
        showSnackBarError(str)
    }

    override fun showSnackBarError(msg: String) {
        val snackbar: Snackbar = Snackbar.make(editEmail, msg, Snackbar.LENGTH_LONG)
                .setAction("OK", null)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        snackbar.show()
    }

    override fun showProgress(show: Boolean) {
        this.showProgress(register_form, progress, show)
    }

    override fun finishActivity() {
        finish()
    }

    override fun setErrorRegister(idView: Int, msg: String) {
        val  editText = findViewById<EditText>(idView)
        editText.error = msg
        editText.requestFocus()
    }
}
