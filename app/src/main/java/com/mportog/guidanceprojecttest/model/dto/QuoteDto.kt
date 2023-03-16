package com.mportog.guidanceprojecttest.model.dto

import com.google.gson.annotations.SerializedName
import com.mportog.guidanceprojecttest.model.ui.Quote

data class QuoteDto(
    @SerializedName("quote") val quote: String?,
    @SerializedName("error") val error: String?,
)

internal fun QuoteDto.toQuote(): Quote? = this.quote?.let {
    Quote(
        quote = this.quote,
        error = this.error
    )
}