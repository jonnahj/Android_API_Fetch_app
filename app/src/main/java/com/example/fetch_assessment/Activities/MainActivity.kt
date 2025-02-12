package com.example.fetch_assessment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import com.example.fetch_assessment.Activities.LoadingAndDisplay
import com.example.fetch_assessment.ViewModels.ListIdViewModel
import com.example.fetch_assessment.ui.theme.Fetch_AssessmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class MainActivity : ComponentActivity() {
//    lateinit var item_map: MutableMap<String, MutableList<Item>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ListIdViewModel(this)
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.makeApiCall()
        }

        enableEdgeToEdge()
        setContent {
            Fetch_AssessmentTheme {
                LoadingAndDisplay(viewModel)
//                StickyHeaderList(data = item_map)
            }
        }
    }
}




