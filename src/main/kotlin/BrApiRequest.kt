import enums.Env

/**
 * Class containing initialising parameters for the SDK.
 *
 * @property accountId Account id provided by Bloomreach
 * @property authorizationKey This parameter is only required if you track users via a universal customer ID.
 * @property catalogName Points to where this information will be stored on the backend represented with catalog name and language key. Use the same value as your domain key in your search API requests.
 * @property environment String for base URL
 * @property baseUrl String for base URL
 * @property connectionTimeOut Connection timeout in millis
 * @property maxTotalConnections Max total connections
 * @property responseTimeout Connection timeout for getting response
 */
data class BrApiRequest(
    val accountId: String?,
    var catalogName: String? = null,
    var authorizationKey: String? = null,
    var environment: Env = Env.STAGE,
    var baseUrl: String? = null,
    var connectionTimeOut: Long = 5000,
    var maxTotalConnections: Int = 10,
    var responseTimeout: Long = 5000
)