package com.example.note_app

import android.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToUpdateContainer(
    item: T,
    onUpdate: (T) -> Unit,
    animationDurationMillis: Int = 500,
    content: @Composable (RowScope.(T) -> Unit)
) {
    var isUpdated by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isUpdated = true
                true
            } else {
                false
            }
        }
    ) // Remember the state

    // Use a side effect to trigger the onUpdate callback after animation
    LaunchedEffect(key1 = isUpdated) {
        if (isUpdated) {
            delay(animationDurationMillis.toLong())
            onUpdate(item)
        }
    }

    AnimatedVisibility(
        visible = !isUpdated,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDurationMillis),
            shrinkTowards = Alignment.Top
        ) + fadeOut(animationSpec = tween(animationDurationMillis))
    ) {
        SwipeToDismissBox(
            state = state, // Pass the state
            backgroundContent = { UpdateBackground(state) },
            content = { content(item) },
            enableDismissFromStartToEnd = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBackground(state: SwipeToDismissBoxState) {
    val navigator = LocalNavigator.currentOrThrow
    val color = if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        androidx.compose.ui.graphics.Color.White
    } else androidx.compose.ui.graphics.Color.Transparent
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .clickable{
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Update",
            tint = androidx.compose.ui.graphics.Color.White
        )
    }
}
