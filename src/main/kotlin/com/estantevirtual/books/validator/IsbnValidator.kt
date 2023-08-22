package com.estantevirtual.books.validator

import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class IsbnValidator {

    fun isValid(text: String?): Boolean {
        val p = Pattern.compile("^\\d{3}-\\d{10}$")
        val m = p.matcher(text!!)
        return m.matches()
    }
}