package com.alkursi.design.generic.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.alkursi.core.NO_OP
import com.alkursi.design.theme.AppGlobalTheme

@Composable
fun ClickableLinkText(
    text: String,
    linkText: String? = text,
    linkUrl: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    linkColor: Color = AppGlobalTheme.colorScheme.primary,
    onClick: (String) -> Unit
) {

    val annotatedString = buildAnnotatedString {
        append(text)
        pushStringAnnotation(
            tag = "URL",
            annotation = linkUrl
        )
        withStyle(
            style = SpanStyle(
                color = linkColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(linkText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = textStyle,
        modifier = modifier,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                onClick(annotation.item)
            }
        }
    )
}

@Preview
@Composable
fun ClickableLinkTextPreview() {
    MaterialTheme {
        ClickableLinkText(
            text = "Pour plus d'information, visitez ce lien : ",
            linkText = "Google",
            linkUrl = "https://www.google.com",
            onClick = { NO_OP }
        )
    }
}