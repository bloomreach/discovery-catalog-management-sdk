package model.content

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper

data class CatalogManagementContentRequestItem(
    @JsonProperty("op")
    var op: String? = null,
    @JsonProperty("path")
    var path: String? = null,
    @JsonProperty("value")
    var value: Value? = null
) {
    fun toJson(): String{
        return ObjectMapper().writeValueAsString(this)
    }
}