package com.google.tetrisrepoapp.model.response

/** Contains information about pages for the pagination */
data class PagingDataInfo(val page: Int?, val step: PaginationStep)

enum class PaginationStep {
    LAST,
    NEXT,
    PREV,
    FIRST
}
