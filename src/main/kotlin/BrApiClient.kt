import enums.Env
import model.StatusResponse
import network.CmHttpClient
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.apache.hc.core5.util.Timeout
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/**
 * Abstract Class for building different type of API objects with some common parameters.
 */
abstract class BrApiClient(private val brApiRequest: BrApiRequest) {

    private val logger = LoggerFactory.getLogger(BrApiClient::class.java.name)

    internal var cmHttpClient: CmHttpClient
    init {
        //Common Pooling manager for API calls
        val poolingConnManager = PoolingHttpClientConnectionManager()
        poolingConnManager.maxTotal = brApiRequest.maxTotalConnections

        //          maxTotal – Set the maximum number of total open connections
        poolingConnManager.maxTotal = brApiRequest.maxTotalConnections

//          Timeout
        val timeout = Timeout.of(brApiRequest.connectionTimeOut, TimeUnit.MILLISECONDS)

        val config: RequestConfig = RequestConfig.custom()
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .setResponseTimeout(brApiRequest.responseTimeout, TimeUnit.MILLISECONDS)
            .build()

//          set pooling and config paramters to client
        val client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .setDefaultRequestConfig(config)
            .build()

        cmHttpClient = CmHttpClient(client)
    }


    abstract class Builder<T>(
        var accountId: String? = null,
        var catalogName: String? = null,
        var baseUrl: String? = null,
        var authorizationKey: String? = null,
        var environment: Env = Env.STAGE,
        var connectionTimeOut: Long = 5000,
        var maxTotalConnections: Int = 10,
        var responseTimeout: Long = 5000
    ) {
        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun accountId(accountId: String) = apply { this.accountId = accountId }
        fun catalogName(catalogName: String) = apply { this.catalogName = catalogName }
        fun authorizationKey(authorizationKey: String) = apply { this.authorizationKey = authorizationKey }
        fun environment(environment: Env) = apply { this.environment = environment }
        fun connectionTimeOut(connectionTimeOut: Long) = apply { this.connectionTimeOut = connectionTimeOut }
        fun maxTotalConnections(maxTotalConnections: Int) = apply {
            this.maxTotalConnections = maxTotalConnections
        }

        fun responseTimeout(responseTimeout: Long) = apply { this.responseTimeout = responseTimeout }
        abstract fun build(): T?
    }

    /**
     * Method to obtain status for a particular Job ID
     * @param jobId The job ID returned in the response from sending your catalog data
     *
     * @return  StatusResponse response object
     */
    public fun getStatus(jobId: String): StatusResponse? {
        logger.debug("getStatus() - jobId: $jobId")
        return cmHttpClient.getStatus(brApiRequest, jobId)
    }
}