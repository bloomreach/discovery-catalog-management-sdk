package model

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Response object for Status API call
 *
 * @property accountId Account id
 * @property catalogName Catalog name sent
 * @property createdAt Created at datetime in string format
 * @property id id for the request
 * @property startedAt startedAt datetime in string format
 * @property status status of the request
 * @property stoppedAt stoppedAt datetime in string format
 * @property type type of the request
 * @property error Generic Error class for handling errors from API calls
 */
data class StatusResponse(
    @JsonProperty("account_id")
    val accountId: Int? = null,

    @JsonProperty("catalog_name")
    val catalogName: String? = null,

    @JsonProperty("created_at")
    val createdAt: String? = null,

    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("started_at")
    val startedAt: String? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("stopped_at")
    val stoppedAt: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("error")
    var error: BrApiError? = null
) {
    fun getCreatedAt(): LocalDateTime? {
        if (!createdAt.isNullOrEmpty()) {
            return try {
                LocalDateTime.ofInstant(Instant.parse(createdAt), ZoneOffset.UTC)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }

    fun getStoppedAt(): LocalDateTime? {
        if (!stoppedAt.isNullOrEmpty()) {
            return try {
                LocalDateTime.ofInstant(Instant.parse(stoppedAt), ZoneOffset.UTC)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }

    fun getStartedAt(): LocalDateTime? {
        if (!startedAt.isNullOrEmpty()) {
            return try {
                LocalDateTime.ofInstant(Instant.parse(startedAt), ZoneOffset.UTC)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }
}
