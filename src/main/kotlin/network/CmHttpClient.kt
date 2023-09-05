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

/**
 *  Class to perform API calls for Data Connect
 */
internal class CmHttpClient(private val client: CloseableHttpClient) {

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
        println("path  ${ObjectMapper().writeValueAsString(path)}")
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
        val httpRequest: HttpUriRequestBase = getHttpObject(brApiRequest, httpMethodType, apiType)
        if(isFilePaths) {
            httpRequest.setHeader("Content-Type", "application/json")
        } else {
            httpRequest.setHeader("Content-Type", "application/json-patch+json")
        }


        httpRequest.setHeader("Authorization", brApiRequest.authorizationKey ?: "")
        val stringBody = StringEntity(jsonBody)
        httpRequest.entity = stringBody

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
                        return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    // return error with response code and reason
                    return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
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

        val url = if (CmApiType.PRODUCT == apiType) {
            "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/accounts/${brApiRequest.accountId}/catalogs/${brApiRequest.catalogName}/products"
        } else {
            "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/accounts/${brApiRequest.accountId}/catalogs/${brApiRequest.catalogName}/items"
        }

        println("url: $url")
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
                        return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    // return error with response code and reason
                    return CatalogManagementResponse(null, BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
            // return error with exception message
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
        val httpRequest = HttpGet(getStatusUrl(brApiRequest, jobId))
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
                        println(result)

                        val responseMapper = ObjectMapper()
                        return responseMapper.readValue(result, StatusResponse::class.java)
                    } else {
                        // return error with response code and reason
                        return StatusResponse(error = BrApiError(response.reasonPhrase, response.code))
                    }
                } else {
                    // return error with response code and reason
                    return StatusResponse(error = BrApiError(response.reasonPhrase, response.code))
                }
            }
        } catch (e: Exception) {
            // return error with exception message
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
        return "${getBaseUrl(brApiRequest)}/dataconnect/api/v1/jobs/$jobId"
    }
}