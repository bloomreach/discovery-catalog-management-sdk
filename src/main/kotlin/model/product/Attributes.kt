package model.product

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Attributes model for Product Api
 */
data class Attributes(
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("category_paths")
    var categoryPaths: List<List<CategoryPath>>? = null,
    @JsonProperty("price")
    var price: Double? = null,
    @JsonProperty("description")
    var description: String? = null,
    @JsonProperty("url")
    var url: String? = null,
    @JsonProperty("availability")
    var availability: Boolean? = null,
    @JsonProperty("brand")
    var brand: String? = null,
    @JsonProperty("thumb_image")
    var thumbImage: String? = null,
    @JsonProperty("best_seller")
    var bestSeller: Double? = null,
    @JsonProperty("bundle_id")
    var bundleId: String? = null,
    @JsonProperty("capacity")
    var capacity: Double? = null,
    @JsonProperty("depth")
    var depth: Double? = null,
    @JsonProperty("flag")
    var flag: Boolean? = null,
    @JsonProperty("gender")
    var gender: String? = null,
    @JsonProperty("height")
    var height: Double? = null,
    @JsonProperty("is_sellable")
    var isSellable: Boolean? = null,
    @JsonProperty("item_no")
    var itemNo: String? = null,
    @JsonProperty("keywords")
    var keywords: String? = null,
    @JsonProperty("large_image")
    var largeImage: String? = null,
    @JsonProperty("launch_date")
    var launch_date: String? = null,
    @JsonProperty("sale_price")
    var salePrice: Double? = null,
    @JsonProperty("size")
    var size: String? = null,

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
