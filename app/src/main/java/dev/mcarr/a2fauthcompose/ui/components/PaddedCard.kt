package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaddedCard(
    onTap: () -> Unit = {},
    content: @Composable () -> Unit
) {

    ElevatedCard(
        modifier = Modifier.padding(4.dp),
        onClick = onTap
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            content()
        }
    }

}

@Preview
@Composable
fun PreviewPaddedCard(){
    PreviewComponent {
        PaddedCard{
            Text(text = "Text1")
            Text(text = "Text2...")
        }
    }
}