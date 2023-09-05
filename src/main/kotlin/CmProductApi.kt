import enums.CmApiType
import enums.HttpMethod
import model.CatalogManagementResponse
import model.product.CatalogManagementProductRequest

/**
 * DcProductApi class contains methods to initiate a BrApiClient object and API call methods
 */
class CmProductApi private constructor(
    brApiRequest: BrApiRequest) : BrApiClient(brApiRequest) {

    var brApiRequestData: BrApiRequest

    init {
        this.brApiRequestData = brApiRequest
    }

     class Builder : BrApiClient.Builder<CmProductApi>() {
        /**
         * Method to create object for DcProductApi
         *
         * @return  DcProductApi instance
         */
        override fun build(): CmProductApi {
            return CmProductApi(BrApiRequest(
                accountId = accountId,
                catalogName = catalogName,
                authorizationKey = authorizationKey,
                environment = environment,
                baseUrl = baseUrl,
                connectionTimeOut = connectionTimeOut,
                maxTotalConnections = maxTotalConnections,
                responseTimeout = responseTimeout
            ))
        }
     }


    /**
     * Method to call Http Ingest PUT Request for Product
     * @param jsonBody Request body in form of JSON String
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(jsonBody: String): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, jsonBody, HttpMethod.PUT, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Ingest PUT Request for Product
     * @param productRequest Request body for Product Api
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(productRequest: CatalogManagementProductRequest): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, productRequest, HttpMethod.PUT, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Ingest PUT Request for Product
     * @param pathToFiles Arraylist to file paths
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPut(pathToFiles: ArrayList<String>): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, pathToFiles, HttpMethod.PUT, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Product
     * @param pathToFiles Arraylist to file paths
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(pathToFiles: ArrayList<String>): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, pathToFiles, HttpMethod.PATCH, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Product
     * @param jsonBody Request body in form of JSON String
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(jsonBody: String): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, jsonBody, HttpMethod.PATCH, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Ingest PATCH Request for Product
     * @param productRequest Request body for Product Api
     *
     * @return  CatalogManagementResponse response object
     */
    public fun ingestPatch(productRequest: CatalogManagementProductRequest): CatalogManagementResponse? {
        return cmHttpClient.invokeIngest(brApiRequestData, productRequest, HttpMethod.PATCH, CmApiType.PRODUCT)
    }

    /**
     * Method to call Http Index Request for Product
     *
     * @return  CatalogManagementResponse response object
     */
    public fun index(): CatalogManagementResponse? {
        return cmHttpClient.invokeIndex(brApiRequestData, CmApiType.PRODUCT)
    }
}