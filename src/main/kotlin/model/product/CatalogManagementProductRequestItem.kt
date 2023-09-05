package model.product

import com.fasterxml.jackson.databind.ObjectMapper

data class CatalogManagementProductRequestItem(
    var op: String? = null,
    var path: String? = null,
    var value: Value? = null
) {
    fun toJson(): String{
        return ObjectMapper().writeValueAsString(this)
    }
}