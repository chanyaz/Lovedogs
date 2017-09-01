package br.com.tairoroberto.mypet.login.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.login.contract.LoginContract
import br.com.tairoroberto.mypet.login.presenter.LoginPresenter
import br.com.tairoroberto.mypet.register.view.RegisterActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter = LoginPresenter()

    override fun getContext(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.attachView(this)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        // Set up the login form.

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                presenter.attemptLogin(email, password)
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { presenter.attemptLogin(email, password) }
        mFirebaseAnalytics?.logEvent("login", Bundle())
        register.setOnClickListener { startActivity<RegisterActivity>() }
    }

    override fun setError(editText: EditText, string: String?) {
        editText.error = string
    }

    override fun showSnackBar(msg: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        Snackbar.make(email, msg, Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, null)
    }

    override fun showSnackBarError(msg: String) {
        val snackbar: Snackbar = Snackbar.make(email, msg, Snackbar.LENGTH_LONG)
                .setAction("OK", null)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        snackbar.show()
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    override fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }
}
