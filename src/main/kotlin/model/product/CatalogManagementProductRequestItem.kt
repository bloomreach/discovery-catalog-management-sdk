package model.product

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory

data class CatalogManagementProductRequestItem(
    val op: String,
    val path: String,
    val value: Value
) {

    val logger = LoggerFactory.getLogger(CatalogManagementProductRequestItem::class.java.name)
    init {
        if (op.isEmpty()) {
            logger.warn("op should not be empty")
        }

        if (path.isEmpty()) {
            logger.warn("path should not be empty")
        }
    }
    fun toJson(): String{
        val objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper.writeValueAsString(this)
    }
}