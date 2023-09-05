
import model.content.Attributes
import model.content.CatalogManagementContentRequest
import model.content.CatalogManagementContentRequestItem
import model.content.Value
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

class CmContentApiTest {

    private var contentApi: CmContentApi? = null

    @BeforeEach
    fun setUp() {
        contentApi = CmContentApi.Builder().accountId("6617")
            .baseUrl("https://api.connect.bloomreach.com")
            .authorizationKey("prosvcs_sandbox-staging-db720fa8-8110-4ab1-8abd-7ef334e76f2d")
            .catalogName("content_en")
            .build()

    }

    @Test
    fun ingestPut() {

        val jsonBody = "[{\n" +
                "\t\t\"op\": \"add\",\n" +
                "\t\t\"path\": \"/items/awesome_omelette2_pdf\",\n" +
                "\t\t\"value\": {\n" +
                "\t\t\t\"attributes\": {\n" +
                "\t\t\t\t\"title\": \"Awesome Omelette2\",\n" +
                "\t\t\t\t\"url\": \"https://www.homeoasis.com/pdf/awesome-omelette.pdf\",\n" +
                "\t\t\t\t\"medium_image_url\": \"https://www.homeoasis.com/images/recipe/201851/img1.jpg\",\n" +
                "\t\t\t\t\"rating\": 4.7,\n" +
                "\t\t\t\t\"category\": [\n" +
                "\t\t\t\t\t\"PDF\",\n" +
                "\t\t\t\t\t\"Breakfast\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"@import\": {\n" +
                "\t\t\t\t\"path\": \"/pdfs/awesome_omelette.pdf\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"op\": \"add\",\n" +
                "\t\t\"path\": \"/items/awesome_omelette_video2\",\n" +
                "\t\t\"value\": {\n" +
                "\t\t\t\"attributes\": {\n" +
                "\t\t\t\t\"title\": \"How to Make Our Awesome Omelette2\",\n" +
                "\t\t\t\t\"url\": \"https://www.homeoasis.com/video/awesome-omelette-video.html\",\n" +
                "\t\t\t\t\"description\": \"Follow along our Awesome Omelette recipe with this companion video.\",\n" +
                "\t\t\t\t\"medium_image_url\": \"https://www.homeoasis.com/images/recipe/201851/img1.jpg\",\n" +
                "\t\t\t\t\"rating\": 4.7,\n" +
                "\t\t\t\t\"video_id\": \"HDRS2748\",\n" +
                "\t\t\t\t\"video_duration\": 5,\n" +
                "\t\t\t\t\"category\": [\n" +
                "\t\t\t\t\t\"Videos\",\n" +
                "\t\t\t\t\t\"Breakfast\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "]"

        val response = contentApi?.ingestPut(jsonBody)

        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred: ${response?.error?.errorMessage}")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }

    }

    @Test
    fun ingestPatch() {

        val jsonBody = "[{\n" +
                "\t\t\"op\": \"add\",\n" +
                "\t\t\"path\": \"/items/awesome_omelette2_pdf\",\n" +
                "\t\t\"value\": {\n" +
                "\t\t\t\"attributes\": {\n" +
                "\t\t\t\t\"title\": \"Awesome Omelette2\",\n" +
                "\t\t\t\t\"url\": \"https://www.homeoasis.com/pdf/awesome-omelette.pdf\",\n" +
                "\t\t\t\t\"medium_image_url\": \"https://www.homeoasis.com/images/recipe/201851/img1.jpg\",\n" +
                "\t\t\t\t\"rating\": 4.7,\n" +
                "\t\t\t\t\"category\": [\n" +
                "\t\t\t\t\t\"PDF\",\n" +
                "\t\t\t\t\t\"Breakfast\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"@import\": {\n" +
                "\t\t\t\t\"path\": \"/pdfs/awesome_omelette.pdf\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"op\": \"add\",\n" +
                "\t\t\"path\": \"/items/awesome_omelette_video2\",\n" +
                "\t\t\"value\": {\n" +
                "\t\t\t\"attributes\": {\n" +
                "\t\t\t\t\"title\": \"How to Make Our Awesome Omelette2\",\n" +
                "\t\t\t\t\"url\": \"https://www.homeoasis.com/video/awesome-omelette-video.html\",\n" +
                "\t\t\t\t\"description\": \"Follow along our Awesome Omelette recipe with this companion video.\",\n" +
                "\t\t\t\t\"medium_image_url\": \"https://www.homeoasis.com/images/recipe/201851/img1.jpg\",\n" +
                "\t\t\t\t\"rating\": 4.7,\n" +
                "\t\t\t\t\"video_id\": \"HDRS2748\",\n" +
                "\t\t\t\t\"video_duration\": 5,\n" +
                "\t\t\t\t\"category\": [\n" +
                "\t\t\t\t\t\"Videos\",\n" +
                "\t\t\t\t\t\"Breakfast\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "]"

        val response = contentApi?.ingestPatch(jsonBody)

        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred: ${response?.error?.errorMessage}")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun ingestPutWithObject() {

        val attributes = Attributes()
        attributes.addField("title", "TRD Brake Pads")
        attributes.addField("url", "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementContentRequest()
        val productRequestItem = CatalogManagementContentRequestItem(value = Value(attributes = attributes))
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val response = contentApi?.ingestPut(request)

        if(response?.error != null ) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }

    }

    @Test
    fun index() {
        val response = contentApi?.index()
        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.jobId, "JobId not null")
        }
    }

    @Test
    fun getStatus() {
        val response = contentApi?.getStatus("294a95ab-be23-491f-9319-d6920aec6f41")
        if (response?.error != null) {
            Assertions.assertTrue(false, "Error occurred")
        } else {
            Assertions.assertNotNull(response, "Response not null")
            Assertions.assertNotNull(response?.accountId, "accountId not null")
            Assertions.assertTrue(response?.accountId == 6617, "accountId is matching")
        }
    }
}