package com.example.fetch_assessment.Activities

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fetch_assessment.Entities.Item
import com.example.fetch_assessment.ViewModels.ListIdViewModel
import kotlinx.coroutines.delay

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun LoadingAndDisplay(viewModel: ListIdViewModel) {
    val data by viewModel.data.observeAsState(initial = mutableMapOf())

    LaunchedEffect(Unit) {
        while(data.isEmpty()){
            delay(1000)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (data.isEmpty()) {
            CircularProgressIndicator() // Show loading spinner
        } else {
            StickyHeaderList(data)
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderList(d: MutableMap<String, MutableList<Item>>) {

    val data = d.toSortedMap()
    val listState = rememberLazyListState()


    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 56.dp),
        state = listState,
    ) {
        data.forEach { (header, items) ->
            // Sticky header for each category
//            items.sortBy { it.name.replace("\"","").split(" ")[1].toInt() }

            try{
            items.sortBy { it.name.replace("\"","").split(" ")[1].toInt() }
            }
            catch (e: Exception){
                println("The exception is at $items")
            }

            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(40.dp)
                ) {
                    Text(text = "The listId is: $header", fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center))
                }

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(text = "ID", color = Color.White)
                    Text(text = "NAME", color = Color.White)
                }
            }

            items(items) { item ->
                print("The name is: ")
                println(item.name.split(" "))
                Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(text = item.id)
                    Text(text = item.name.replace("\"", ""))
                }
            }
        }
    }
}