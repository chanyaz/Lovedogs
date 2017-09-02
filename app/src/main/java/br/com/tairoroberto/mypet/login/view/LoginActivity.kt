package br.com.tairoroberto.mypet.login.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.login.contract.LoginContract
import br.com.tairoroberto.mypet.login.presenter.LoginPresenter
import br.com.tairoroberto.mypet.register.model.User
import br.com.tairoroberto.mypet.register.view.RegisterActivity
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import com.facebook.login.LoginManager
import org.json.JSONObject


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View, FacebookCallback<LoginResult> {

    private val presenter: LoginContract.Presenter = LoginPresenter()
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.attachView(this)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        // Set up the login form.

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                presenter.attemptLogin(email, password, checkBox)
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { presenter.attemptLogin(email, password, checkBox) }
        mFirebaseAnalytics?.logEvent("login", Bundle())
        register.setOnClickListener { startActivity<RegisterActivity>() }

        email.setText(presenter.getStringPreference("email"))
        password.setText(presenter.getStringPreference("password"))

        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()

        login_button.setReadPermissions("email")
        login_button.registerCallback(callbackManager, this)
    }

    override fun onSuccess(result: LoginResult?) {
        Log.i("LOG", "result ${result.toString()}")
        val parameters = Bundle()
        parameters.putString("fields", "name,last_name,email,picture")
        val request = GraphRequest.newMeRequest(result?.accessToken) { jObj, graphResponse ->
            if (jObj != null) {
//                callbackManager?.onUserLogged(User(
  //                      jObj["email"] as String,
    //                    jObj["name"] as String,
      //                  ((jObj["picture"] as JSONObject)["data"] as JSONObject)["url"] as String))

                Log.i("LOG", "jObj $jObj")
            }
        }
        request.parameters = parameters
        request.executeAsync()
    }

    override fun onCancel() {
        Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: FacebookException?) {
        showSnackBarError("Falha ao fazeer login :(")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun getContext(): Context {
        return this
    }

    override fun getActivity(): Activity? {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
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
