package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SearchFieldComponent(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    placeHolder: String
) {

    OutlinedTextField(
        modifier = modifier,
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        placeholder = {
            Text(text = placeHolder)
        }
    )
}