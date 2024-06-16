package io.travel_tunes.ui.fragments.restore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.appComponent
import io.travel_tunes.databinding.FragmentRestoreBinding
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.copyToClipboard
import io.travel_tunes.utils.extencions.openEmailApp
import javax.inject.Inject

class RestoreFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: RestoreDataViewModelFactory
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

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun initViews() {
        initToolbar()
        with(binding) {
            toolbarLayout.toolbarTitle.setText(R.string.restore_screen_title)
            // FIXME: возможно стоит выпилить копирование адреса по клику на текстовое поле 
            descriptionContacts.setOnClickListener {
                requireActivity().copyToClipboard(
                    label = "email",
                    textForCopy = "pixel.rabbit.soft@gmail.com",
                    textForToast = "Е-mail скопирован"
                )
            }
            actionSendMail.setOnClickListener {
                requireActivity().openEmailApp("pixel.rabbit.soft@gmail.com")
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
        viewModel = ViewModelProvider(this, viewModelFactory)[RestoreDataViewModel::class.java]
    }
}