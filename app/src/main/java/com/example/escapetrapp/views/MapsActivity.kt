package com.example.escapetrapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        val destination = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(destination).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 10f))

        val query = "pontos turísticos"
        val zoom = 10
        val geo = "geo:0,0?q=$query&z=$zoom"
        val geoUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoUri )
        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }

}