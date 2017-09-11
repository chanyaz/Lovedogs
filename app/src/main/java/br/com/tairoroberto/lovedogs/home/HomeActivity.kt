package br.com.tairoroberto.lovedogs.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.about.AboutActivity
import br.com.tairoroberto.lovedogs.login.view.LoginActivity
import br.com.tairoroberto.lovedogs.petshop.view.ListFavoritesFragment
import br.com.tairoroberto.lovedogs.petshop.view.ListPetshopsFragment
import br.com.tairoroberto.lovedogs.settings.SettingsActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private var mGoogleApiClient: GoogleApiClient? = null
    private var fragmentManager: FragmentManager? = null
    private var fragment: Fragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(ListPetshopsFragment(), false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(ListFavoritesFragment(), false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentManager = supportFragmentManager
        replaceFragment(ListPetshopsFragment(), false)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            disconnectFromFacebook()
        }

        if( item?.itemId == R.id.settings ) {
            startActivity<SettingsActivity>()
        }

        if( item?.itemId == R.id.about) {
            startActivity<AboutActivity>()
        }

        return super.onOptionsItemSelected(item)
    }

    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            startActivity<LoginActivity>()
            finish()
            return
        }
        GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                GraphRequest.Callback {
                    LoginManager.getInstance().logOut()

                    startActivity<LoginActivity>()
                    finish()
                }).executeAsync()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    private fun replaceFragment(fragment: Fragment?, addToStack: Boolean) {
        if (addToStack) {
            fragmentManager?.beginTransaction()?.replace(R.id.content, fragment, null)?.addToBackStack("fragment")?.commit()
        } else {
            fragmentManager?.beginTransaction()?.replace(R.id.content, fragment, null)?.commit()
        }
    }
}
