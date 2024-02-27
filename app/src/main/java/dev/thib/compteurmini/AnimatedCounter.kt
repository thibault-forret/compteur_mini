@file:OptIn(ExperimentalAnimationApi::class)

package dev.thib.compteurmini

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for(i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if(oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    if(oldCount < count) {
                        slideInVertically{ it } with  slideOutVertically { -it }
                    } else {
                        slideInVertically{ -it } with  slideOutVertically { it }
                    }
                },
                label = "",
            ) {char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}