package com.jandorresteijn.kiteapp.ui.main.notification

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jandorresteijn.kiteapp.R
import com.jandorresteijn.kiteapp.databinding.NotificationFragmentBinding
import org.osmdroid.views.MapView
import java.text.SimpleDateFormat
import java.util.*

class NotificationFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: NotificationFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationViewModel =
            ViewModelProvider(this).get(NotificationViewModel::class.java)

        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.notificationText
        notificationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.timePickerbtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}