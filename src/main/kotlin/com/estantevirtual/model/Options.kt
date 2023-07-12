package com.estantevirtual.model

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
data class Options(
    var page: Int,
    var pageSize: Int,
    var orderBy: String?,
    var sort: String?
)
