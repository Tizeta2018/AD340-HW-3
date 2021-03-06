package com.example.myapplicationhw3

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
<<<<<<< HEAD
import android.Manifest.permission.ACCESS_FINE_LOCATION
=======
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import java.util.*


<<<<<<< HEAD
const val PERMISSION_REQUEST_LOCATION = 9
=======
const val PERMISSION_REQUEST_LOCATION = 5
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
class CameraMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var TMap: GoogleMap? = null
    private var Data: MutableList<Cam> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

<<<<<<< HEAD
        val actionbar = supportActionBar
        actionbar!!.title = "Traffic Camera Map"
        actionbar.setDisplayHomeAsUpEnabled(true)
=======

>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->

            TMap = googleMap
            Log.d("LOCATION", "has map")


            checkLocationPermission()


            Data(Cam.camUrl)
        }

    }

    private fun checkLocationPermission() {
        Log.d("LOCATION", "checkPermission")
<<<<<<< HEAD
        if ( ContextCompat.checkSelfPermission(this@CameraMap, ACCESS_FINE_LOCATION) ==
=======
        if ( ContextCompat.checkSelfPermission(this@CameraMap, ACCESS_COARSE_LOCATION) ==
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
            PackageManager.PERMISSION_GRANTED) {
            Log.d("LOCATION", "already granted")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    Log.d("LOCATION", location.toString())

                    if (location == null) {
                        val tmpLocation = Location(LocationManager.GPS_PROVIDER)
                        tmpLocation.latitude = 47.620182
                        tmpLocation.longitude = -122.34933
                        updateMap(tmpLocation)
                    }

                    updateMap(location)
                }

        } else {
            Log.d("LOCATION", "should request")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@CameraMap,
<<<<<<< HEAD
                    ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@CameraMap,
                    arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
            } else {
                ActivityCompat.requestPermissions(this@CameraMap,
                    arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
=======
                    ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this@CameraMap,
                    arrayOf(ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
            } else {
                ActivityCompat.requestPermissions(this@CameraMap,
                    arrayOf(ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
<<<<<<< HEAD

=======
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
        if (requestCode == PERMISSION_REQUEST_LOCATION) {

            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.d("LOCATION", "granted")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
<<<<<<< HEAD
                        ACCESS_FINE_LOCATION
=======
                        ACCESS_COARSE_LOCATION
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->

                            Log.d("LOCATION2", location.toString())
                            updateMap(location)
                        }

            } else {

<<<<<<< HEAD
                Toast.makeText(this, "Location Access Denied", Toast.LENGTH_SHORT).show()
=======
                Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show()
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
            }
        }
    }

    private fun Data(dataUrl: String?) {
        val queue = Volley.newRequestQueue(this)
        val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null, { response ->
            Log.d("CAMERAS", response.toString())
            try {
<<<<<<< HEAD
                val features = response.getJSONArray("Features")
=======
                val features = response.getJSONArray("Features") // top-level node
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
                for (i in 1 until features.length()) {
                    val point = features.getJSONObject(i)
                    val pointCoords = point.getJSONArray("PointCoordinate")
                    val camera = point.getJSONArray("Cameras").getJSONObject(0)
                    val c = Cam(
                        camera.getString("Description"),
                        camera.getString("ImageUrl"),
                        camera.getString("Type"),
                        doubleArrayOf(pointCoords.getDouble(0), pointCoords.getDouble(1))
                    )
                    Data.add(c)
                }
                showMarkers()
            } catch (e: JSONException) {
            }
        }) { error -> Log.d("JSON", "Error: " + error.message) }

        queue.add(jsonReq)
    }

    private fun updateMap(location: Location?) {
        Log.d("LOCATION", "updateMap")
        if (location != null) {
            Log.d("LOCATION", "move map")
<<<<<<< HEAD
            TMap?.setMinZoomPreference(6.0f)
=======
            TMap?.setMinZoomPreference(12f)
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
            TMap?.apply {
                val position = LatLng(location.latitude, location.longitude)
                addMarker(
                    MarkerOptions()
                        .position(position)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("My Location")
                )

                moveCamera(CameraUpdateFactory.newLatLng(position))

            }
        }
    }


    private fun showMarkers() {
        Log.d("LOCATION", "Show Markers")
        for (camera in Data) {
            TMap?.apply {
                val position = LatLng(camera.coords[0], camera.coords[1])
                addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(camera.description)
                )
            }
        }
    }

    private fun showToast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true


    }

}