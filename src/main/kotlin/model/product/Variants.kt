package model.product

import com.fasterxml.jackson.annotation.JsonProperty

data class Variants(
    @JsonProperty("exclude_from_view")
    var excludeFromGroup: Boolean? = null,
    var attributes: Attributes? = null,
)