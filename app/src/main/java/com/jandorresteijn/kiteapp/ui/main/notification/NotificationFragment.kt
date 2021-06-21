package com.jandorresteijn.kiteapp.ui.main.notification

import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jandorresteijn.kiteapp.databinding.NotificationFragmentBinding
import com.jandorresteijn.kiteapp.entity.UserRepository
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class NotificationFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var repo: UserRepository
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

        repo = UserRepository(activity!!)

        val textView: TextView = binding.notificationText
        notificationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.timePickerbtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                runBlocking { UpdateUser(hour) }

                //TODO use @String module with variable
                textView.text =
                    "You will be notified at " + SimpleDateFormat("HH:mm").format(hour)
            }
            TimePickerDialog(
                activity,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.notificationSoundButton.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone")
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, null as Uri?)
            this.startActivityForResult(intent, 5)

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    suspend fun UpdateUser(hour: Int) {
        val user = repo.getUser()
        user.hour_notification = hour
        repo.addUser(user)
    }
}