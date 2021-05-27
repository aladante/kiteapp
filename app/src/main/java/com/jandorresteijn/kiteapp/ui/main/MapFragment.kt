package com.jandorresteijn.kiteapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jandorresteijn.kiteapp.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapFragment : Fragment() {

    private lateinit var mMap: MapView

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
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = mMap.controller

        val longitude = 3.9041
        val latitude = 52.3676
        val mapPoint = GeoPoint(latitude, longitude)

        controller.setZoom(8.5)

        controller.animateTo(mapPoint)

    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = MainFragmentBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        mMap = binding.map
//        mMap.setTileSource(TileSourceFactory.MAPNIK)
//
//
//        Configuration.getInstance()
//            .load(applicationContext, getSharedPreferences("phonebook_app", MODE_PRIVATE))
//
//        val longitude = intent.getDoubleExtra("longitude", 36.7783)
//        val latitude = intent.getDoubleExtra("latitude", 119.4179)
//
//
//        val controller = mMap.controller
//
//        val mapPoint = GeoPoint(latitude, longitude)
//
//        controller.setZoom(9.5)
//
//        controller.animateTo(mapPoint)
//
//
//    }
}