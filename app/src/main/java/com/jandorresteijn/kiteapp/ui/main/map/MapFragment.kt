package com.jandorresteijn.kiteapp.ui.main.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jandorresteijn.kiteapp.R
import com.jandorresteijn.kiteapp.entity.Location
import com.jandorresteijn.kiteapp.entity.LocationRepository
import com.jandorresteijn.kiteapp.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.osmdroid.api.IMapController
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*


const val BUNDLE_REMINDER_KEY = "bundle_location"
const val REQ_REMINDER_KEY = "req_location"

class MapFragment : Fragment() {

    private lateinit var mMap: MapView
    private lateinit var mMapControler: IMapController
    private lateinit var confirm_button: Button
    private var repo: LocationRepository? = null
    private var loc: Location? = null
    var myMarkers: ArrayList<Marker?>? = ArrayList()

    //    var myItemizedOverlay: MyItemizedOverlay? = null
    var marker: Drawable? = null

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.map_fragment, null)
        repo = LocationRepository(activity!!)
        mMap = v.findViewById<View>(R.id.map) as MapView
        mMap.isClickable
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMapControler = mMap.controller
        confirm_button = v.findViewById(R.id.confirm_button)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val longitude = 3.9041
        val latitude = 52.367
        loc = Location(longitude = longitude, latidude = latitude)
        val mapPoint = GeoPoint(latitude, longitude)
        mMapControler.setCenter(mapPoint)
        mMapControler.setZoom(8.5)
        mMapControler.animateTo(mapPoint)
        addEvent()
        triggerCoroutine()

    }

    private fun addOverlay(location: Location) {
        for (m in myMarkers!!) {
            mMap.getOverlays().remove(m)
        }
        val longitude: Double = location.longitude?.toDouble() ?: 3.9041
        val latitude: Double = location.latidude?.toDouble() ?: 52.367
        val mapPoint = GeoPoint(latitude, longitude)
        val icon = resources.getDrawable(R.drawable.ic_menu_camera)
        val startMarker = Marker(mMap)
        startMarker.position = mapPoint
        startMarker.icon = icon
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
        myMarkers?.add(startMarker)
        mMap.overlays.add(startMarker)
    }

    private fun addEvent() {
        mMap.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                var long: Double = p.longitude
                var lat: Double = p.latitude
                loc = Location(latidude = lat, longitude = long)
                addOverlay(loc!!)
                return true
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                Log.e("MapView", "long click")
                return false
            }
        }))

        confirm_button.setOnClickListener {
            if (loc != null) {
                repo?.addLocation(loc!!)
            } else {
                Toast.makeText(activity, "No location set", Toast.LENGTH_LONG)
            }
        }
    }

    fun triggerCoroutine() {
        runBlocking {
            var location = repo?.getLocation()
            Log.e("MapView", location.toString())
            if (location != null) {
                for (loc_loop in location) {
                    addOverlay(loc_loop)
                }
            }
        }
    }

}