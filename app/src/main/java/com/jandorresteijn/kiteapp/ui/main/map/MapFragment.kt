package com.jandorresteijn.kiteapp.ui.main.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jandorresteijn.kiteapp.R
import com.jandorresteijn.kiteapp.ui.main.MainViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem


class MapFragment : Fragment() {

    private lateinit var mMap: MapView
    private lateinit var mMapControler: IMapController

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
        mMap = v.findViewById<View>(R.id.map) as MapView
        mMap.isClickable
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMapControler = mMap.controller
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val longitude = 3.9041
        val latitude = 52.367
        val mapPoint = GeoPoint(latitude, longitude)
        mMapControler.setCenter(mapPoint)
        mMapControler.setZoom(8.5)
        mMapControler.animateTo(mapPoint)
        addOverlay()
        addEvent()
    }

    private fun addOverlay() {
        val longitude = 3.9041
        val latitude = 52.367
        val mapPoint = GeoPoint(latitude, longitude)
        val marker2 = resources.getDrawable(R.drawable.marker)
        val startMarker = Marker(mMap)
        startMarker.position = mapPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


    }

    private fun addEvent() {
        mMap.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                var lat = p.latitude.toString() + "  " + p.longitude.toString()
                Log.e("MapView", lat)
                return true
            }
            override fun longPressHelper(p: GeoPoint): Boolean {
                Log.e("MapView", "long click")
                return false
            }
        }))
    }

}