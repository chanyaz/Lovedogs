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
import android.transition.ChangeBounds
import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.base.extension.getConfig
import br.com.tairoroberto.lovedogs.base.extension.showSnackBarError
import br.com.tairoroberto.lovedogs.settings.Configuracoes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PetshopDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private var petShop: PetShop? = null
    private lateinit var googleMap: GoogleMap
    private var shareActionProvider: ShareActionProvider? = null
    var config: Configuracoes? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petshop_detail)
        setSupportActionBar(toolbar)

        config = getConfig()

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

        textViewPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${petShop?.phone}")
            startActivity(intent)
        }

        imageViewPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${petShop?.phone}")
            startActivity(intent)
        }

        fab.setOnClickListener {
            val favorite = petShop?.favorite as Boolean

            petShop?.favorite = !favorite
            updatePetshop(petShop as PetShop)
        }

        if(petShop?.favorite == true) {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp)
        }else {
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        if( config?.share == true) {
            menuInflater.inflate(R.menu.menu_petshop_detail, menu)
            val shareItem = menu.findItem(R.id.menu_share)

            shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider

            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE)
            } else {
                setShareIntent()
            }
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

    fun updatePetshop(petShop: PetShop) {
        ApiUtils.getApiService()?.updatePetshop(petShop)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if( it.success && petShop.favorite) {
                        fab.setImageResource(R.drawable.ic_favorite_black_24dp)
                        Toast.makeText(this, "Adicionado aos favoritos :)", Toast.LENGTH_SHORT).show()
                    }else {
                        fab.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                        Toast.makeText(this, "Removido dos favoritos :)", Toast.LENGTH_SHORT).show()
                    }

                }, { error ->
                    Log.i("LOG", "${error.message}")
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    showSnackBarError(textViewClose, "Falha na comunicação :(")
                })
    }

    companion object {
        val WRITE_EXTERNAL_STORAGE = 2
    }
}
