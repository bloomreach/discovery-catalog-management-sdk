package model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Response object for the request sent for APIs calls
 *
 * @property jobId jobId for the request
 * @property error Generic Error class for handling errors from API calls
 */
 class CatalogManagementResponse(
    @JsonProperty("jobId")
    var jobId: String? = null,
    @JsonProperty("error")
    var error: BrApiError? = null
)