package model.content

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter

/**
 * Attributes model for Content Api
 */
data class Attributes(
    @JsonAnySetter
    @get:JsonAnyGetter
    var fields: HashMap<String, Any?> = hashMapOf()
) {
    fun addField(key: String, value: Any?) {
        if(key.isNotEmpty()) {
            fields[key] = value
        }
    }
}
