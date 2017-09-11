package br.com.tairoroberto.lovedogs.petshopdetail

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ShareCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.telephony.PhoneNumberUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.loadImage
import br.com.tairoroberto.lovedogs.petshop.model.PetShop
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_petshop_detail.*
import kotlinx.android.synthetic.main.content_petshop_detail.*
import java.util.*
import android.R.attr.bitmap
import android.content.pm.PackageManager
import android.provider.MediaStore.Images
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PetshopDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private var petShop: PetShop? = null
    private lateinit var googleMap: GoogleMap
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petshop_detail)
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        petShop = savedInstanceState?.getParcelable("petshop")

        if (intent.extras != null) {
            petShop = intent.extras.getParcelable("petShop")
        }

        imageViewPetShop.isDrawingCacheEnabled = true
        imageViewPetShop.loadImage(petShop?.imageUrl)
        toolbar_layout.title = petShop?.name

        textViewAddress.text = petShop?.address
        textViewOpen.text = petShop?.open
        textViewClose.text = petShop?.close
        textViewDescription.text = petShop?.description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textViewPhone.text = PhoneNumberUtils.formatNumber(petShop?.phone, Locale.getDefault().country)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_petshop_detail, menu)

        val shareItem = menu.findItem(R.id.menu_share)

        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE)
        } else {
            setShareIntent()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setShareIntent()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setShareIntent() {
        if (shareActionProvider != null) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"

            val bitmap = imageViewPetShop.drawingCache

            val bitmapPath = Images.Media.insertImage(contentResolver, bitmap, "image_petshop", null)
            val bitmapUri = Uri.parse(bitmapPath)

            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${petShop?.name} \n\n ${petShop?.address}")
            shareActionProvider?.setShareIntent(shareIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("petshop", petShop)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val locale = LatLng(petShop?.latitude as Double, petShop?.longitude as Double)
        this.googleMap.addMarker(MarkerOptions().position(locale).title(petShop?.name))
        this.googleMap.setMinZoomPreference(15.0f)
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(locale))
    }

    companion object {
        val WRITE_EXTERNAL_STORAGE = 2
    }
}
