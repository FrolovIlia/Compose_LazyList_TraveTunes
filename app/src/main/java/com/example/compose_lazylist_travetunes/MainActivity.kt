package com.example.compose_lazylist_travetunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_lazylist_travetunes.data.Datasource
import com.example.compose_lazylist_travetunes.model.InterestingPoint
import com.example.compose_lazylist_travetunes.ui.theme.Compose_LazyList_TraveTunesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_LazyList_TraveTunesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TravelTunesApp()
                }
            }
        }
    }
}

@Composable
fun TravelTunesApp() {
        InterestingPointList(interestingPointList = Datasource().loadInterestingPoint())
}

@Composable
fun InterestingPointCard(interestingPoint: InterestingPoint, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .height(192.dp)) {
        Row {
            Box {
                Image(
                    painter = painterResource(interestingPoint.picture),
                    contentDescription = stringResource(interestingPoint.title),
                    modifier = modifier
                        .width(width = 151.dp)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }


            Column(modifier = Modifier
                .padding(16.dp)) {
                Text(
                    text = LocalContext.current.getString(interestingPoint.title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = LocalContext.current.getString(interestingPoint.description),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}



@Composable
fun InterestingPointList(interestingPointList: List<InterestingPoint>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(interestingPointList) { interestingPoint ->
            InterestingPointCard(
                interestingPoint = interestingPoint,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_LazyList_TraveTunesTheme {
        InterestingPointCard(InterestingPoint(title = R.string.spb_title1, description = R.string.spb_description1, picture = R.drawable.spb_1))

    }
}