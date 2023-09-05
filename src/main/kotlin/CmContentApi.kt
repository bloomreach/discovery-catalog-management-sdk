import enums.CmApiType
import enums.HttpMethod
import model.CatalogManagementResponse
import model.content.CatalogManagementContentRequest

/**
 * DcContentApi class contains methods to initiate a BrApiClient object and API call methods
 */
class CmContentApi private constructor(
    brApiRequest: BrApiRequest
) : BrApiClient(brApiRequest) {

    var brApiRequestData: BrApiRequest
    init {
        this.brApiRequestData = brApiRequest
    }

    class Builder : BrApiClient.Builder<CmContentApi>() {

        /**
         * Method to create object for DcContentApi
         *
         * @return DcContentApi instance
         */
        override fun build(): CmContentApi {
            return CmContentApi(
                BrApiRequest(
                    accountId = accountId,
                    catalogName = catalogName,
                    authorizationKey = authorizationKey,
                    environment = environment,
                    baseUrl = baseUrl,
                    connectionTimeOut = connectionTimeOut,
                    maxTotalConnections = maxTotalConnections,
                    responseTimeout = responseTimeout
                )
            )
        }
    }

    /**
     * Method to call Http Ingest PUT Request for Content
     * @param jsonBody Request body in form of JSON String
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(jsonBody: String): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, jsonBody, HttpMethod.PUT, CmApiType.CONTENT)
    }

    /**
     * Method to call Http Ingest PUT Request for Content
     * @param contentRequest Request body for Content Api
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(contentRequest: CatalogManagementContentRequest): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, contentRequest, HttpMethod.PUT, CmApiType.CONTENT)
    }

    /**
     * Method to call Http Ingest PUT Request for Content
     * @param pathToFiles Arraylist to file paths
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(pathToFiles: ArrayList<String>): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, pathToFiles, HttpMethod.PUT, CmApiType.CONTENT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Content
     * @param pathToFiles Arraylist to file paths
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(pathToFiles: ArrayList<String>): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, pathToFiles, HttpMethod.PATCH, CmApiType.CONTENT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Content
     * @param jsonBody Request body in form of JSON String
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(jsonBody: String): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, jsonBody, HttpMethod.PATCH, CmApiType.CONTENT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Content
     * @param contentRequest Request body for Content Api
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(contentRequest: CatalogManagementContentRequest): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, contentRequest, HttpMethod.PATCH, CmApiType.CONTENT)
    }


    /**
     * Method to call Http Index Request for Content
     *
     * @return  CatalogManagementResponse response object
     */
    public fun index(): CatalogManagementResponse? {
        return cmHttpClient.invokeIndex(brApiRequestData, CmApiType.CONTENT)
    }
}