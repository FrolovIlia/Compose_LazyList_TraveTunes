package com.example.compose_lazylist_travetunes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.compose_lazylist_travetunes.databinding.ActivityInterestingPointsBinding
import com.example.compose_lazylist_travetunes.ui.InterestingPointViewModel
import com.example.compose_lazylist_travetunes.ui.InterestingPointViewModelFactory
import com.example.compose_lazylist_travetunes.utils.adapters.InterestingPointsAdapter
import com.example.compose_lazylist_travetunes.utils.adapters.MySpaceItemDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: InterestingPointViewModelFactory
    private lateinit var viewModel: InterestingPointViewModel

    private lateinit var binding: ActivityInterestingPointsBinding
    private lateinit var adapterIP: InterestingPointsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestingPointsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModel()
    }

    private fun initViewModel() {
//        val database: InterestingPointDatabase
        // FIXME: добавить тут передачу бд
        viewModelFactory = InterestingPointViewModelFactory()
        viewModel = ViewModelProvider(this)[InterestingPointViewModel::class.java]

        viewModel.interestingPoints.observe(this) { points ->
            adapterIP.updateData(points)
        }
    }

    private fun initViews() {
        adapterIP = InterestingPointsAdapter { interestingPoint ->
            /**
             * вот тут мы понимаем на какой элемент произошло нажатие в RecyclerView
             */
            Toast.makeText(this, "${this.resources.getText(interestingPoint.title)}", Toast.LENGTH_SHORT).show()
        }

        binding.cityRecyclerView.adapter = adapterIP
        val divider = MySpaceItemDecoration(spaceSize = resources.getDimensionPixelSize(R.dimen.spacing_8))
        binding.cityRecyclerView.addItemDecoration(divider)

        binding.buttonAdd.setOnClickListener { viewModel.addTestItemToDataSource() }
    }
}
