package com.estantevirtual.model

import java.time.LocalDateTime

class ErrorMessage(val description: String) {
    val date = LocalDateTime.now()
}
