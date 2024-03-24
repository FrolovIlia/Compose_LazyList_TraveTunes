package io.travel_tunes.ui.fragments.points.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentPointInfoBinding
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable

class PointInfoFragment : Fragment() {

    private lateinit var viewModelFactory: PointInfoViewModelFactory
    private lateinit var viewModel: PointInfoViewModel

    private lateinit var binding: FragmentPointInfoBinding

    companion object {
        const val POINT_INFO_BOTTOM = "point_info_bottom"
        private const val EXTRA_POINT_INFO = "point_info"
        fun getInstance(pointItemFullInfo: PointItemFullInfo): PointInfoFragment {
            val args = Bundle()
            val fragment = PointInfoFragment()
            args.putParcelable(EXTRA_POINT_INFO, pointItemFullInfo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    fun updateData(pointItemFullInfo: PointItemFullInfo) {
        viewModel.updateData(pointItemFullInfo)
    }

    private fun initViews() {
        binding.showMoreButton.setOnClickListener {
            Toast.makeText(requireContext(), "add me later", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewModel() {
        val pointItemFullInfo = arguments?.parcelable<PointItemFullInfo>(EXTRA_POINT_INFO) ?: return
        viewModelFactory = PointInfoViewModelFactory(pointItemFullInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[PointInfoViewModel::class.java]

        viewModel.pointItemFullInfo.observe(viewLifecycleOwner) { pointInfo ->
            with(binding) {
                val drawableRes = pointInfo.getDrawableRes() ?: R.drawable.pic_default
                pointDrawable.setImageResource(drawableRes)

                pointTitle.changeText(pointInfo.getTitle())

                val audioRes = pointInfo.getAudioRes()
                if (audioRes != null) {
                    playerView.setAudioRaw(audioRes)
                    playerView.changeVisibility(true)
                } else {
                    playerView.changeVisibility(false)
                }
            }
        }
    }

}