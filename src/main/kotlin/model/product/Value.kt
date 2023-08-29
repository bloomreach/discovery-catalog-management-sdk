package model.product

data class Value(
    var attributes: Attributes? = null,
    var variants: LinkedHashMap<String, Variants>? = null,
    var views: LinkedHashMap<String, Value>? = null,
) {

    //Attribute
    fun addAttributeField(key: String, value: Any?) {
        if(attributes == null) {
            attributes = Attributes()
        }
        attributes!!.addField(key, value)
    }

    //Variant
    fun addVariant(key: String,  variant: Variants) {
        if(variants == null) {
            variants = linkedMapOf()
        }
        variants!![key] = variant
    }

    fun addVariantAttributes(key: String,  attributes: Attributes) {
        if(variants == null) {
            variants = linkedMapOf()
        }
        val variant = Variants()
        variant.attributes = attributes
        variants!![key] = variant
    }

    fun addVariantAttributeField(variantName: String, attributeKey: String, attributeValue: Any?) {
        if(!variants.isNullOrEmpty()) {
            if(!variantName.isNullOrEmpty()) {
                if(variants!!.contains(variantName)) {
                    variants!![variantName]?.attributes?.addField(attributeKey, attributeValue)
                } else {
                    val variant = Variants()
                    val attributes = Attributes()
                    attributes.addField(attributeKey, attributeValue)
                    variant.attributes = attributes
                    addVariant(variantName, variant)
                }
            }
        } else {
            val variant = Variants()
            val attributes = Attributes()
            attributes.addField(attributeKey, attributeValue)
            variant.attributes = attributes
            addVariant(variantName, variant)
        }
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

    fun addVariantForView(viewName: String, variantsKey: String, attributes: Attributes) {
        if(views == null) {
            views = linkedMapOf()
        }
        if(views!!.contains(viewName)) {
            views!![viewName]?.addVariantAttributes(variantsKey, attributes)
        } else {
            val value = Value()
            value.addVariantAttributes(variantsKey, attributes)
            views!![viewName] = value
        }
    }

    fun addVariantAttributeFieldForView(viewName: String, variantsKey: String, fieldKey: String, fieldValue: Any?) {
        if(views == null) {
            views = linkedMapOf()
        }
        if(views!!.contains(viewName)) {
            val variants = views!![viewName]?.variants
            if(!variants.isNullOrEmpty() && variants.contains(variantsKey)){
                variants[variantsKey]?.attributes?.addField(fieldKey, fieldValue)
            } else {
                if(variants == null) {
                    views!![viewName]?.variants = linkedMapOf()
                }
                val attributes = Attributes()
                attributes.addField(fieldKey, fieldValue)
                views!![viewName]?.variants?.get(variantsKey)?.attributes = attributes
            }

        } else {
            val value = Value()
            val attributes = Attributes()
            attributes.addField(fieldKey, fieldValue)
            value.addVariantAttributes(variantsKey, attributes)
            views!![viewName] = value
        }
    }
}