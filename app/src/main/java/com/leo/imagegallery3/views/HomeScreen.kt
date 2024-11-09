package com.leo.imagegallery3.views

import Painting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leo.imagegallery3.R

@Composable
fun HomeScreen(
    paintings: List<Painting>,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val mainColumnModifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(Color.Black)

    Column(
        modifier = mainColumnModifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top // Align everything at the top
    ) {
        Title()
        Subtitle()
        GetPreviewButtons(paintings, navController)
    }
}

@Composable
private fun GetPreviewButtons(
    paintings: List<Painting>,
    navController: NavController
) {
    for ((index, painting) in paintings.withIndex()) {
        PreviewButton(
            paintingIndex = index,
            painting = painting,
            navController = navController
        )
        Spacer(modifier = Modifier.height(8.dp)) // Add some space between buttons
    }
}

@Composable
private fun Title() {
    return Text(
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 32.dp,
                bottom = 4.dp
            ),
        text = stringResource(id = R.string.main_menu_title),
        style = TextStyle(
            color = Color.White,
            fontSize = 32.sp,
            letterSpacing = 2.sp
        )
    )
}

@Composable
private fun Subtitle() {
    return Text(
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 8.dp
            ),
        text = stringResource(id = R.string.main_menu_label),
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            letterSpacing = 2.sp
        )
    )
}