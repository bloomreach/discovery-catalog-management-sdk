package model.content

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import model.product.CatalogManagementProductRequestItem
import org.slf4j.LoggerFactory

data class CatalogManagementContentRequestItem(
    @JsonProperty("op")
    var op: String? = null,
    @JsonProperty("path")
    var path: String? = null,
    @JsonProperty("value")
    var value: Value? = null
) {
    fun toJson(): String{
        val objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(this)
    }
}