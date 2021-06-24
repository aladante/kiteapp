package com.jandorresteijn.kiteapp.ui.main.map

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jandorresteijn.kiteapp.R
import com.jandorresteijn.kiteapp.entity.User
import com.jandorresteijn.kiteapp.entity.UserRepository
import kotlinx.android.synthetic.main.map_fragment.*
import kotlinx.coroutines.runBlocking
import org.osmdroid.api.IMapController
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*


class MapFragment : Fragment() {

    private lateinit var mMap: MapView
    private lateinit var mMapControler: IMapController
    private lateinit var confirmButton: Button
    private var longitude: Double = 3.9041
    private var latitude: Double = 52.367
    private var repo: UserRepository? = null
    private var user: User? = null
    private var myMarkers: ArrayList<Marker?>? = ArrayList()

    companion object;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.map_fragment, null)
        repo = UserRepository(activity!!)
        mMap = v.findViewById<View>(R.id.map) as MapView
        mMap.isClickable
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMapControler = mMap.controller
        confirmButton = v.findViewById(R.id.confirm_button)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = User(longitude = longitude, latidude = latitude)
        val mapPoint = GeoPoint(latitude, longitude)
        mMapControler.setCenter(mapPoint)
        mMapControler.setZoom(8.5)
        mMapControler.animateTo(mapPoint)
        addEvent()
        triggerCoroutine()

    }

    private fun addOverlay(location: User) {
        for (m in myMarkers!!) {
            mMap.overlays.remove(m)
        }

        val icon = resources.getDrawable(R.drawable.marker_default, null)
        longitude = location.longitude ?: 3.9041
        latitude = location.latidude ?: 52.367
        val mapPoint = GeoPoint(latitude, longitude)
        val startMarker = Marker(mMap)
        startMarker.position = mapPoint
        startMarker.icon = icon
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
        myMarkers?.add(startMarker)
        mMap.overlays.add(startMarker)
        mMapControler.animateTo(mapPoint)
    }

    private fun addEvent() {
        mMap.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                val long: Double = p.longitude
                val lat: Double = p.latitude
                user = User(latidude = lat, longitude = long)
                addOverlay(user!!)
                return true
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                Log.e("MapView", "long click")
                return false
            }
        }))

        confirmButton.setOnClickListener {
            if (user != null) {
                AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.map_fragment_confirm))
                    .setMessage(getString(R.string.map_fragment_message))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(
                        android.R.string.yes,
                    ) { _, _->
                            repo?.addUser(user!!)
                    }
                    .setNegativeButton(android.R.string.no, null).show()

            } else {
                Toast.makeText(
                    activity,
                    R.string.map_fragment_no_location_chosen.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // this button is to test intent, boot intent can only by trigger on a real phone or trigger it
        // with terminal but this is easier and also learn about intent

        test_button.setOnClickListener {
            Intent().also { intent ->
                intent.action = "com.jandorresteijn.LEAN"
                intent.putExtra("data", "Nothing to see here, move along.")
                context!!.sendBroadcast(intent)
            }

        }
    }

    private fun triggerCoroutine() {
        runBlocking {
            val user: User? = repo?.getUser()
            if (user != null) {
                addOverlay(user)
            }
        }
    }

}