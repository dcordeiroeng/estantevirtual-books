package com.estantevirtual.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter
import org.springframework.data.domain.Page
import java.io.Serializable

@Getter
@Setter
class Books(
    books: Page<Book?>,
    totalElements: Long,
    page: Int,
    pageSize: Int,
    nextPageUrl: String?,
    previousPageUrl: String?
) : Serializable {

    var books: MutableList<Book?> = books.content
    var total: Long = totalElements
    var page: Int = page

    @JsonProperty("per_page")
    var perPage: Int = pageSize

    @JsonProperty("next_page")
    var nextPage: String? = nextPageUrl

    @JsonProperty("prev_page")
    var prevPage: String? = previousPageUrl
}