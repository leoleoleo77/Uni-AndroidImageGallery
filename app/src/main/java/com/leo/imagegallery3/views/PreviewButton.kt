package com.leo.imagegallery3.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.leo.imagegallery3.R
import com.leo.imagegallery3.utils.Painting

@Composable
fun PreviewButton(
    painting: Painting,
    navController: androidx.navigation.NavController
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth() // Fill the parent width
            .height(screenHeight * 0.14f) // 14% of screen height
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))// Ensure the shape is respected
    ) {
        // Background Image
        Image(
            painter = painterResource(id = painting.imageResourceId),
            contentDescription = getContentDescription(context, painting.title),
            contentScale = ContentScale.Crop, // Make the image fill the box (cropped if necessary)
            modifier = Modifier
                .fillMaxSize() // Make the image fill the entire button area
        )

        // Button overlay
        Button(
            onClick = {
                navController.navigate("gallery_view/${painting.id}")
            },
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = getContentDescription(context, painting.title) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent background
            elevation = ButtonDefaults.elevatedButtonElevation(0.dp) // No shadow to make it flat
        ) {}
    }
}

private fun getContentDescription(
    context: Context,
    title: String
) : String {
    return "${context.getString(R.string.preview__image__semantics_label)} $title"
}
