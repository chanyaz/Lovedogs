package br.com.tairoroberto.lovedogs.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.hideKeyboard
import br.com.tairoroberto.lovedogs.base.extension.showProgress
import br.com.tairoroberto.lovedogs.base.extension.showSnackBarError
import br.com.tairoroberto.lovedogs.home.HomeActivity
import br.com.tairoroberto.lovedogs.login.contract.LoginContract
import br.com.tairoroberto.lovedogs.login.presenter.LoginPresenter
import br.com.tairoroberto.lovedogs.register.view.RegisterActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View, FacebookCallback<LoginResult> {

    private val presenter: LoginContract.Presenter = LoginPresenter()
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }

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

        email_sign_in_button.setOnClickListener {
            hideKeyboard()
            presenter.attemptLogin(email, password, checkBox)
        }

        mFirebaseAnalytics?.logEvent("login", Bundle())
        register.setOnClickListener { startActivity<RegisterActivity>() }

        val config = presenter.getConfig()
        email.setText(config?.user)
        password.setText(config?.password)

        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()

        login_button.setReadPermissions("email")
        login_button.registerCallback(callbackManager, this)

        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity<HomeActivity>()
            finishActivity()
        }
    }

    override fun setUseFromFacebook(emailStr: String, name: String?, picture: String) {
        email.setText(emailStr)
        Picasso.with(this).load(picture).into(user_profile_photo)
        startActivity<HomeActivity>()
        finishActivity()
    }

    override fun onSuccess(result: LoginResult?) {
        Log.i("LOG", "result ${result.toString()}")
        val parameters = Bundle()
        parameters.putString("fields", "name, last_name, email, picture")
        val request = GraphRequest.newMeRequest(result?.accessToken) { userJson, graphResponse ->
            if (userJson != null) {
                setUseFromFacebook(userJson["email"].toString(), userJson["name"].toString(), ((userJson["picture"] as JSONObject)["data"] as JSONObject)["url"] as String)
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

    override fun getActivity(): Activity {
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
        Snackbar.make(email, msg, Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, null)
    }

    override fun showSnackBarError(msg: String) {
        this.showSnackBarError(email, msg)
        showProgress(false)
        hideKeyboard()
    }

    override fun showProgress(show: Boolean) {
        this.showProgress(login_form, login_progress, show)
    }

    override fun finishActivity() {
        finish()
    }
}
