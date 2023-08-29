import model.product.Attributes
import model.product.CatalogManagementProductRequest
import model.product.CatalogManagementProductRequestItem
import model.product.Value
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DcProductApiTest {

    private var productApi: DcProductApi? = null

    @BeforeEach
    fun setUp() {
        productApi = DcProductApi.Builder().accountId("6452")
            .catalogName("test_akashr_en_d1")
            .baseUrl("https://api.connect.bloomreach.com")
            .authorizationKey("test_akashr-cb617486-c4e9-4de1-83a4-60a3d9f5fb22")
            .build()

    }

    @Test
    fun ingestPutWithJson() {
        val jsonBody = "[\n" +
                "    {\n" +
                "        \"op\": \"add\",\n" +
                "        \"path\": \"/products/ptr092111012213\",\n" +
                "        \"value\": {\n" +
                "            \"attributes\": {\n" +
                "                \"title\": \"TRD Brake Pads\",\n" +
                "                \"url\": \"/products/product/trd-brake-pads-ptr0921110\",\n" +
                "                \"part_number\": \"PTR09-21110\",\n" +
                "                \"model_year_code\": [\n" +
                "                    \"2022:1234,2021:1231\"\n" +
                "                ],\n" +
                "                \"category_paths\": [\n" +
                "                    [\n" +
                "                        {\n" +
                "                            \"id\": \"Home\",\n" +
                "                            \"name\": \"home\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Accessories \",\n" +
                "                            \"name\": \"accessories \"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Performance\",\n" +
                "                            \"name\": \"performance\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Performance Suspension-Chassis\",\n" +
                "                            \"name\": \"performance_suspension_chassis\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Brake Pads\",\n" +
                "                            \"name\": \"brake_pads\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"TRD Brake Pads\",\n" +
                "                            \"name\": \"trd_brake+_pads\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                ]\n" +
                "               \n" +
                "            },\n" +
                "            \"views\": {\n" +
                "                \"national\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 80.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"normreevestoyotasandiego\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 80.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"vancouvertoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 90,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"out_of_stock\",\n" +
                "                        \"shipping\": \"offline\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"moderntoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 87.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"freemantoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 89.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "]"



        val response = productApi?.ingestPut(jsonBody)

        if (response?.error != null) {
            println("Error : ${response?.error?.errorMessage}")
            println("Error : ${response?.error?.errorCode}")
            Assertions.assertTrue(false, "Error occurred")
        } else {
            println("jobid : ${response?.jobId}")
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun ingestPutWithJsonForEmptyRequest() {
        val response = productApi?.ingestPut("")


        if (response?.error != null) {
            Assertions.assertTrue(true, "Error occurred for empty request")
        } else {
            Assertions.assertTrue(false, "Error should occur for empty request")
        }
    }

    @Test
    fun ingestPutWithJsonForFilePaths() {
        val filePaths = "[\n" +
                "\"{catalog name}/{catalog1.jsonl}\",\n" +
                "\"{catalog name}/{catalog2.jsonl}\"\n" +
                "]"

        val arr = ArrayList<String>()
        arr.add("catalog name}/{catalog1.jsonl")
        arr.add("catalog name}/{catalog2.jsonl")

        val response = productApi?.ingestPut(arr)

        if (response?.error != null) {
            println("Error : ${response?.error?.errorMessage}")
            println("Error : ${response?.error?.errorCode}")
            Assertions.assertTrue(false, "Error occurred")
        } else {
            println("jobid : ${response?.jobId}")
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun ingestPutForNoOpDefined() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val view = LinkedHashMap<String, Value>()
        view.put(
            "national",
            Value(Attributes(
                price = 95.0,
                salePrice = 80.75,
                availability = true
            ))
        )

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
//        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr09211109934"
        request.add(productRequestItem)

        val response = productApi?.ingestPut(request)

        if (response?.error != null) {
            Assertions.assertTrue(true, "Error occurred for empty request")
        } else {
            Assertions.assertTrue(false, "Error should occur for empty request")
        }
    }

    @Test
    fun ingestPutWithObject() {

        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val view = LinkedHashMap<String, Attributes>()
        view.put(
            "national",
            Attributes(
                price = 95.0,
                salePrice = 80.75,
                availability = true
            )
        )

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val response = productApi?.ingestPut(request)

        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }

    }

    @Test
    fun ingestPatchWithJson() {

        val jsonBody = "[\n" +
                "    {\n" +
                "        \"op\": \"add\",\n" +
                "        \"path\": \"/products/ptr092111012\",\n" +
                "        \"value\": {\n" +
                "            \"attributes\": {\n" +
                "                \"title\": \"TRD Brake Pads\",\n" +
                "                \"url\": \"/products/product/trd-brake-pads-ptr0921110\",\n" +
                "                \"part_number\": \"PTR09-21110\",\n" +
                "                \"model_year_code\": [\n" +
                "                    \"2022:1234,2021:1231\"\n" +
                "                ],\n" +
                "                \"category_paths\": [\n" +
                "                    [\n" +
                "                        {\n" +
                "                            \"id\": \"Home\",\n" +
                "                            \"name\": \"home\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Accessories \",\n" +
                "                            \"name\": \"accessories \"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Performance\",\n" +
                "                            \"name\": \"performance\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Performance Suspension-Chassis\",\n" +
                "                            \"name\": \"performance_suspension_chassis\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"Brake Pads\",\n" +
                "                            \"name\": \"brake_pads\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\": \"TRD Brake Pads\",\n" +
                "                            \"name\": \"trd_brake+_pads\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                ]\n" +
                "               \n" +
                "            },\n" +
                "            \"views\": {\n" +
                "                \"national\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 80.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"normreevestoyotasandiego\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 80.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"vancouvertoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 90,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"out_of_stock\",\n" +
                "                        \"shipping\": \"offline\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"moderntoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 87.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"freemantoyota\": {\n" +
                "                    \"attributes\": {\n" +
                "                        \"price\": 95,\n" +
                "                        \"sale_price\": 89.75,\n" +
                "                        \"availability\": true,\n" +
                "                        \"inventory\": \"in_stock\",\n" +
                "                        \"shipping\": \"online\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "]"


        val response = productApi?.ingestPut(jsonBody)

        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun ingestPatchWithObject() {

        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val view = LinkedHashMap<String, Attributes>()
        view.put(
            "national",
            Attributes(
                price = 95.0,
                salePrice = 80.75,
                availability = true
            )
        )

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val response = productApi?.ingestPatch(request)

        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }

    }

//    @Test
//    fun injestPutWithFiles() {
//        val response = productApi?.ingestPut(listOf<String>(""))
//
//        if (response?.error != null) {
//            Assertions.assertTrue(false, "Error occurred")
//        } else {
//            Assertions.assertNotNull(response, "Response not null")
//            Assertions.assertNotNull(response?.jobId, "JobId not null")
//        }
//    }

    @Test
    fun index() {
        val response = productApi?.index()
        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun getStatus() {
        val response = productApi?.getStatus("1eb0b08d-32d6-4d7f-b1b8-c784cd85a311")
        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.accountId, "accountId not null")
           // Assertions.assertTrue(response?.accountId == 6452, "accountId is matching")
        }
    }

    @Test
    fun getStatusWithWrongJobId() {
        val response = productApi?.getStatus("8182e04f-71c1-4d15-bc23-3245bd95f9cd-ewrew")
        if (response?.error != null) {
            Assertions.assertTrue(true, "Error occurred")
        } else {
            Assertions.assertTrue(false, "Error should occurr for incorrect jobId")
        }
    }
}