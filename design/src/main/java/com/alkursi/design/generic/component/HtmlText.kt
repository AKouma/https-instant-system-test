package com.alkursi.design.generic.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.alkursi.design.theme.AppGlobalTheme

@Composable
fun HtmlText(html: String) {
    val parsedText = parseHtmlToAnnotatedString(html)
    BasicText(text = parsedText)
}

@Composable
fun parseHtmlToAnnotatedString(html: String): AnnotatedString {
    return buildAnnotatedString {
        val tagRegex = """<(/?[^>]+)>""".toRegex()
        var lastIndex = 0
        tagRegex.findAll(html).forEach { matchResult ->
            val tag = matchResult.value
            val startIndex = matchResult.range.first
            append(html.substring(lastIndex, startIndex))
            when (tag) {
                "<h2>" -> withStyle(
                    style = SpanStyle(
                        fontSize = 24.sp,
                        color = AppGlobalTheme.colorScheme.onSurface
                    )
                ) {}

                "</h2>" -> append("\n")
                "<p>" -> {}
                "</p>" -> append("\n")
            }
            lastIndex = matchResult.range.last + 1
        }
        append(html.substring(lastIndex))
    }
}