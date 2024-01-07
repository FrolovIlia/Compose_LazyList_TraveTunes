package com.example.compose_lazylist_travetunes.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.data.Datasource
import com.example.compose_lazylist_travetunes.model.InterestingPoint
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDatabase

class InterestingPointViewModel(
//    private val interestingPointDao: InterestingPointDao
): ViewModel() {
    private val dataSource = Datasource()
//    private val db: LiveData<List<InterestingPoint>> = interestingPointDao.getAllInterestingPoints().asLiveData()

    // FIXME: тут заменить на использование для interestingPoints данных из бд вместо DataSource 
//    val interestingPoints : InterestingPointDatabase = db.getDatabase()
    var interestingPoints: LiveData<List<InterestingPoint>> = dataSource.loadInterestingPoints()

    fun addTestItemToDataSource() {
        val interestingPoint = InterestingPoint(
            title = R.string.spb_title1,
            description = R.string.spb_description2,
            picture = R.drawable.spb_1,
            id = 234
        )
        dataSource.addInterestingPoint(point = interestingPoint)
    }

}


class InterestingPointViewModelFactory(
//    private val db: InterestingPointDao,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  InterestingPointViewModel(
//            db = db,
        ) as T
    }

}