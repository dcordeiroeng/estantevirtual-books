package com.estantevirtual.validator

import com.estantevirtual.exception.OrderByException
import com.estantevirtual.model.Options
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class OptionsValidator {

    val orderByValues = listOf("id", "isbn", "title", "pages")

    fun validateParams(options: Options) {
        require(options.page >= 0) { "Page must be equal or greater than 0" }
        require(options.pageSize > 0) { "Page size must be greater than 0" }
        require(!(options.sort != Sort.Direction.ASC.toString() && options.sort != Sort.Direction.DESC.toString())) {
            "Sort must be ASC or DESC"
        }
        isValid(options.orderBy)
    }

    private fun isValid(value: String?) {
        if (!orderByValues.contains(value)) throw OrderByException()
    }
}