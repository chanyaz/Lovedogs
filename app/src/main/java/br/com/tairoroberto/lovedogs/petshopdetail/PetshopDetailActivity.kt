package br.com.tairoroberto.lovedogs.petshopdetail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.loadImage
import br.com.tairoroberto.lovedogs.petshop.model.PetShop
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_petshop_detail.*
import kotlinx.android.synthetic.main.content_petshop_detail.*

class PetshopDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private var petShop: PetShop? = null
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petshop_detail)
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        petShop = savedInstanceState?.getParcelable("petshop")

        if( intent.extras != null ) {
            petShop = intent.extras.getParcelable("petShop")
        }

        imageViewPetShop.loadImage(petShop?.imageUrl)
        toolbar_layout.title = petShop?.name

        textViewAddress.text = petShop?.address
        textViewOpen.text = petShop?.open
        textViewClose.text = petShop?.close
        textViewPhone.text = petShop?.phone
        textViewDescription.text = petShop?.description

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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
}
