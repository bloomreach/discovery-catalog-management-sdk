package model.content

import com.fasterxml.jackson.annotation.JsonProperty

data class Value(
    @JsonProperty("attributes")
    var attributes: Attributes? = null,
    var views: LinkedHashMap<String, Value>? = null,
) {
    //Attribute
    fun addAttributeField(key: String, value: Any?) {
        if(attributes == null) {
            attributes = Attributes()
        }
        attributes!!.addField(key, value)
    }

    // Views
    fun addView(key: String, value: Value) {
        if(views == null) {
            views = linkedMapOf()
        }
        views!![key] = value
    }

    fun addAttributesForView(viewName: String, attributes: Attributes) {
        if(views == null) {
            views = linkedMapOf()
        }
        if(views!!.contains(viewName)) {
            views!![viewName]?.attributes = attributes
        } else {
            views!![viewName] = Value(attributes = attributes)
        }
    }

    fun addAttributeFieldForView(viewName: String, fieldKey: String, fieldValue: Any?) {
        if(views == null) {
            views = linkedMapOf()
        }
        if(views!!.contains(viewName)) {
            views!![viewName]?.addAttributeField(fieldKey, fieldValue)
        } else {
            val value = Value()
            value.addAttributeField(fieldKey, fieldValue)
            views!![viewName] = value
        }
    }
}