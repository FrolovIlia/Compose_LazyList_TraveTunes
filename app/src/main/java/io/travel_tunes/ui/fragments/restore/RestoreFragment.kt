package io.travel_tunes.ui.fragments.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentRestoreBinding
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.copyToClipboard
import io.travel_tunes.utils.prefs.PreferenceManager

class RestoreFragment : Fragment() {

    private lateinit var viewModelFactory: RestoreDataViewModelFactory
    private lateinit var viewModel: RestoreDataViewModel

    private lateinit var binding: FragmentRestoreBinding

    companion object {
        fun getInstance(): RestoreFragment {
            return RestoreFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        initToolbar()
        with(binding) {
            toolbarLayout.toolbarTitle.setText(R.string.restore_screen_title)
            descriptionContacts.setOnClickListener {
                // FIXME: также тут можно запускать почту и вставлять наш адрес - возможно это будет лучшим решением
                requireActivity().copyToClipboard(
                    label = "email",
                    textForCopy = "pixel.rabbit.soft@gmail.com",
                    textForToast = "Е-mail скопирован"
                )
            }
            actionCopyID.setOnClickListener {
                viewModel.getUID { uniqueId ->
                    requireActivity().copyToClipboard(
                        label = "label",
                        textForCopy = uniqueId,
                        textForToast = "UID скопирован"
                    )
                }
            }
            actionSyncRemoteData.setOnClickListener {
                viewModel.syncRemoteData { resultStringRes ->
                    Toast.makeText(requireContext(), resultStringRes, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbarLayout) {
            toolbarBackArrow.apply {
                changeVisibility(true)
                setOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun initViewModel() {
        val preferenceManager = PreferenceManager(requireContext())
        val pathFirebaseDB = resources.getString(R.string.fb_db_path)
        viewModelFactory = RestoreDataViewModelFactory(preferenceManager, pathFirebaseDB)

        viewModel = ViewModelProvider(this, viewModelFactory)[RestoreDataViewModel::class.java]
    }
}