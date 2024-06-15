package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mcarr.a2fauthcompose.ui.screens.SetupScreen
import dev.mcarr.a2fauthcompose.ui.theme._2FAuthComposeTheme
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel

@Composable
fun ToggleRow(
    title: String,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit
) {

    ElevatedCard(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .toggleable(
                    value = checkedState,
                    onValueChange = { onStateChange(!checkedState) },
                    role = Role.Checkbox
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = title,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}

@Preview
@Composable
fun PreviewSetupScreen(){
    _2FAuthComposeTheme {
        Surface {
            Column {
                ToggleRow(
                    title = "Checked",
                    checkedState = true,
                    onStateChange = {}
                )
                ToggleRow(
                    title = "Unchecked",
                    checkedState = false,
                    onStateChange = {}
                )
            }
        }
    }
}