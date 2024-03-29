package com.jandorresteijn.kiteapp.ui.main.notification

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jandorresteijn.kiteapp.databinding.NotificationFragmentBinding
import com.jandorresteijn.kiteapp.entity.UserRepository
import kotlinx.coroutines.runBlocking
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
    ): View {
        notificationViewModel =
            ViewModelProvider(this).get(NotificationViewModel::class.java)

        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        repo = UserRepository(activity!!)


        binding.timePickerbtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, _ ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                runBlocking { updateUser(hour) }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            val uri = intent?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            if (uri != null) {
                runBlocking {
                    val user = repo.getUser()
                    user.sound_notification = uri.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(activity)) {
                            RingtoneManager.setActualDefaultRingtoneUri(
                                activity,
                                RingtoneManager.TYPE_RINGTONE,
                                uri
                            )
                        }
                    }
                    repo.addUser(user)
                }
            }
        }
    }

    private suspend fun updateUser(hour: Int) {
        val user = repo.getUser()
        user.hour_notification = hour
        repo.addUser(user)
    }
}