package network

import BrApiRequest
import com.fasterxml.jackson.databind.ObjectMapper
import enums.CmApiType
import enums.Env
import enums.HttpMethod
import model.BrApiError
import model.CatalogManagementResponse
import model.StatusResponse
import model.content.CatalogManagementContentRequest
import model.product.CatalogManagementProductRequest
import org.apache.hc.client5.http.classic.methods.*
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.http.io.entity.StringEntity
import org.slf4j.LoggerFactory

/**
 *  Class to perform API calls for Data Connect
 */
internal class CmHttpClient(private val client: CloseableHttpClient) {

    val logger = LoggerFactory.getLogger(CmHttpClient::class.java.name)
    /**
     * Method to call Http Ingest Request for Product
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param request Request body object for Product Request
     * @param httpMethodType Enum HttpMethod PUT or PATCH
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  CatalogManagementResponse response object
     */
    fun invokeIngest(
        brApiRequest: BrApiRequest,
        request: CatalogManagementProductRequest,
        httpMethodType: HttpMethod,
        apiType: CmApiType
    ): CatalogManagementResponse? {
        logger.trace("invokeIngest() - httpMethodType: ${httpMethodType.name}, apiType: ${apiType.name}")
        return invokeIngest(brApiRequest, ObjectMapper().writeValueAsString(request), httpMethodType, apiType)
    }

    /**
     * Method to call Http Ingest Request for Content
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param path Arraylist to file paths
     * @param httpMethodType Enum HttpMethod PUT or PATCH
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  CatalogManagementResponse response object
     */
    fun invokeIngest(
        brApiRequest: BrApiRequest,
        path: ArrayList<String>,
        httpMethodType: HttpMethod,
        apiType: CmApiType
    ): CatalogManagementResponse? {
        logger.trace("invokeIngest() - path: ${path.joinToString(",")} httpMethodType: ${httpMethodType.name}, apiType: ${apiType.name}")
        return invokeIngest(brApiRequest, ObjectMapper().writeValueAsString(path), httpMethodType, apiType, true)
    }

    /**
     * Method to call Http Ingest Request for Content
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param request Request body object for Content Request
     * @param httpMethodType Enum HttpMethod PUT or PATCH
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  CatalogManagementResponse response object
     */
    fun invokeIngest(
        brApiRequest: BrApiRequest,
        request: CatalogManagementContentRequest,
        httpMethodType: HttpMethod,
        apiType: CmApiType
    ): CatalogManagementResponse? {
        logger.trace("invokeIngest()- httpMethodType: ${httpMethodType.name}, apiType: ${apiType.name}")
        return invokeIngest(brApiRequest, ObjectMapper().writeValueAsString(request), httpMethodType, apiType)
    }

    /**
     * Method to call Http Ingest Request for Product/Content with JSON
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param jsonBody Request body in form of JSON String
     * @param httpMethodType Enum HttpMethod PUT or PATCH
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  CatalogManagementResponse response object
     */
    fun invokeIngest(
        brApiRequest: BrApiRequest,
        jsonBody: String,
        httpMethodType: HttpMethod,
        apiType: CmApiType,
        isFilePaths: Boolean = false
    ): CatalogManagementResponse? {
        logger.trace("invokeIngest() - httpMethodType: ${httpMethodType.name}, apiType: ${apiType.name}, isFilePaths: $isFilePaths , \n JSON : $jsonBody")
        val httpRequest: HttpUriRequestBase = getHttpObject(brApiRequest, httpMethodType, apiType)
        if(isFilePaths) {
            httpRequest.setHeader("Content-Type", "application/json")
        } else {
            httpRequest.setHeader("Content-Type", "application/json-patch+json")
        }

        httpRequest.setHeader("Authorization", brApiRequest.authorizationKey ?: "")
        val stringBody = StringEntity(jsonBody)
        httpRequest.entity = stringBody

        logger.debug("execute() API")
        try {
            client.execute(httpRequest).use { response ->
                // check if response code is 200
                if (response.code in 200..299) {
                    logger.info("API response code: ${response.code} ")
                    val entity = response.entity
                    if (entity != null) {
                        // get response as a String
                        val result = EntityUtils.toString(entity)
                        logger.trace("Response: $result")
                        val responseMapper = ObjectMapper()
                        responseMapper.readValue(result, CatalogManagementResponse::class.java)
                        return responseMapper.readValue(result, CatalogManagementResponse::class.java)
                    } else {
                        logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                        // return error with response code and reason
                        return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                    // return error with response code and reason
                    return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
            logger.error("API call Exception: ${e.localizedMessage} ")
            // return error with exception message
            return CatalogManagementResponse(null, BrApiError(e.localizedMessage, 0))
        }
    }




    /**
     * Method to get HttpUriRequestBase object which contains formatted URL and HttpMethod type defined
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param httpMethodType Enum HttpMethod PUT or PATCH
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  HttpUriRequestBase object
     */
    private fun getHttpObject(
        brApiRequest: BrApiRequest,
        httpMethodType: HttpMethod,
        apiType: CmApiType
    ): HttpUriRequestBase {
        logger.trace("getHttpObject() - httpMethodType: ${httpMethodType.name}, apiType: ${apiType.name}")
        val url = if (CmApiType.PRODUCT == apiType) {
            "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/accounts/${brApiRequest.accountId}/catalogs/${brApiRequest.catalogName}/products"
        } else {
            "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/accounts/${brApiRequest.accountId}/catalogs/${brApiRequest.catalogName}/items"
        }

        logger.trace("getHttpObject()- URL: $url")

        return if (HttpMethod.PUT == httpMethodType) {
            HttpPut(url)
        } else {
            HttpPatch(url)
        }
    }

    /**
     * Method to get the base URL based on Env or if passed by client
     * @param brApiRequest Object of all global parameters to be sent with the API
     *
     * @return Base Url String
     */
    private fun getBaseUrl(brApiRequest: BrApiRequest): String {
        logger.trace("getBaseUrl()")
        return if (!brApiRequest.baseUrl.isNullOrEmpty()) {
            brApiRequest.baseUrl ?: ""
        } else {
            when (brApiRequest.environment) {
                Env.STAGE -> "https://api-staging.connect.bloomreach.com"
                Env.PROD -> "https://api.connect.bloomreach.com"
            }
        }
    }

    /**
     * Method to call Http Index Request for Product/Content
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  CatalogManagementResponse response object
     */
    fun invokeIndex(
        brApiRequest: BrApiRequest,
        apiType: CmApiType
    ): CatalogManagementResponse? {
        logger.trace("invokeIndex() - apiType: ${apiType.name}")
        val httpRequest = HttpPost(getIndexUrl(brApiRequest, apiType))
//        httpRequest.setHeader("Content-Type", "application/json-patch+json")
        httpRequest.setHeader("Authorization", brApiRequest.authorizationKey ?: "")

        try {
            client.execute(httpRequest).use { response ->
                // check if response code is 200
                if (response.code in 200..299) {
                    val entity = response.entity
                    if (entity != null) {
                        // get response as a String
                        val result = EntityUtils.toString(entity)

                        val responseMapper = ObjectMapper()
                        responseMapper.readValue(result, CatalogManagementResponse::class.java)
                        return responseMapper.readValue(result, CatalogManagementResponse::class.java)
                    } else {
                        // return error with response code and reason
                        logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                        return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    // return error with response code and reason
                    logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                    return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
            // return error with exception message
            logger.error("API call Exception: ${e.localizedMessage} ")
            return CatalogManagementResponse(null, BrApiError(e.localizedMessage, 0))

        }
    }

    /**
     * Method to call Http Status Request for Product/Content
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param jobId The job ID returned in the response from sending your catalog data
     *
     * @return  StatusResponse response object
     */
    fun getStatus(
        brApiRequest: BrApiRequest,
        jobId: String
    ): StatusResponse? {
        logger.trace("getStatus() - jobId: $jobId")
        val httpRequest = HttpGet(getStatusUrl(brApiRequest, jobId))
        httpRequest.setHeader("Authorization", brApiRequest.authorizationKey ?: "")

        try {
            client.execute(httpRequest).use { response ->
                // check if response code is 200
                if (response.code in 200..299) {
                    val entity = response.entity
                    if (entity != null) {
                        // get response as a String
                        val result = EntityUtils.toString(entity)
                        logger.trace("Response: $result")

                        val responseMapper = ObjectMapper()
                        return responseMapper.readValue(result, StatusResponse::class.java)
                    } else {
                        // return error with response code and reason
                        logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                        return StatusResponse(error = BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    // return error with response code and reason
                    logger.error("Error code: ${response.code}, Error Message: ${response.reasonPhrase}")
                    return StatusResponse(error = BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
            // return error with exception message
            logger.error("API call Exception: ${e.localizedMessage} ")
            return StatusResponse(error = BrApiError(e.localizedMessage, 0))
        }
    }

    /**
     * Method to get formatted URL for Index API
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param apiType Enum CmApiType - PRODUCT or CONTENT
     *
     * @return  String formatted URL
     */
    private fun getIndexUrl(brApiRequest: BrApiRequest, apiType: CmApiType): String {
        logger.trace("getIndexUrl()")
        return "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/accounts/${brApiRequest.accountId}/catalogs/${brApiRequest.catalogName}/indexes"
    }

    /**
     * Method to get formatted URL for Status API
     * @param brApiRequest Object of all global parameters to be sent with the API
     * @param jobId The job ID returned in the response from sending your catalog data
     *
     * @return  String formatted URL
     */
    private fun getStatusUrl(brApiRequest: BrApiRequest, jobId: String): String {
        logger.trace("getStatusUrl(): jobId: $jobId")
        return "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/jobs/$jobId"
    }
}