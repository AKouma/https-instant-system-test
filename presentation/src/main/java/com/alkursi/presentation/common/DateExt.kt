package com.alkursi.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alkursi.presentation.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun OffsetDateTime.formatRelative(): String {
    val now = OffsetDateTime.now(this.offset)
    val today = now.toLocalDate()
    val targetDate = this.toLocalDate()

    val timeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val timePart = this.format(timeFormatter)

    return when {
        targetDate.isEqual(today) -> stringResource(R.string.today_at, timePart)
        targetDate.isEqual(today.minusDays(1)) -> stringResource(R.string.yesterday_At, timePart)
        ChronoUnit.DAYS.between(targetDate, today) in 2..6 -> {
            val dayName = this.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            stringResource(R.string.at, dayName, timePart)
        }

        else -> {
            val dateFormatter = DateTimeFormatter.ofPattern(fullPattern, Locale.getDefault())
            this.format(dateFormatter)
        }
    }
}

private const val pattern = "HH'h'mm"
private const val fullPattern = "dd/MM/yyyy 'à' HH'h'mm"
