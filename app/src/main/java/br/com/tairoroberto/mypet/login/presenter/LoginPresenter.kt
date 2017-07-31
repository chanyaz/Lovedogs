package br.com.tairoroberto.mypet.login.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.home.HomeActivity
import br.com.tairoroberto.mypet.login.contract.LoginContract

/**
 * Created by tairo on 7/30/17.
 */
class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null

    private var mAuthTask: UserLoginTask? = null

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                view?.populateAutoComplete()
            }
        }
    }

    override fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (view?.getContext()?.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if ((view?.getContext() as Activity).shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            view?.showSnackBar(view?.getContext()?.getString(R.string.permission_rationale) as String)
        } else {
            (view?.getContext() as Activity).requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    override fun attemptLogin(email: AppCompatAutoCompleteTextView, password: EditText) {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = view?.getContext()?.getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = view?.getContext()?.getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = view?.getContext()?.getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            view?.showProgress(true)
            mAuthTask = UserLoginTask(email, password)
            mAuthTask?.execute(null as Void?)
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val mEmail: AppCompatAutoCompleteTextView, private val mPassword: EditText) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            return DUMMY_CREDENTIALS
                    .map { it.split(":") }
                    .firstOrNull { it[0] == mEmail.text.toString() }
                    ?.let {
                        // Account exists, return true if the password matches.
                        it[1] == mPassword.text.toString()
                    }
                    ?: true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            view?.showProgress(false)

            if (success == true) {
                val intent: Intent = Intent(view?.getContext(), HomeActivity::class.java)
                view?.getContext()?.startActivity(intent)

                (view?.getContext() as Activity).finish()
            } else {
                view?.setError(mPassword, view?.getContext()?.getString(R.string.error_incorrect_password))
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            view?.showProgress(false)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }


    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("tairo@teste.com:teste", "teste@teste.com:teste")
    }
}