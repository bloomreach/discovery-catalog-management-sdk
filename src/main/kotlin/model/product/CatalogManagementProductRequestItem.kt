package model.product

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.tools.sjavac.Util.set
import org.jetbrains.annotations.NotNull
import org.slf4j.LoggerFactory

data class CatalogManagementProductRequestItem(
    var op: String? = null,
    var path: String? = null,
    var value: Value? = null
) {
    fun toJson(): String{
        val objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(this)
    }
}