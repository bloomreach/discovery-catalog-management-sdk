package model

import com.fasterxml.jackson.databind.ObjectMapper
import model.content.Attributes
import model.content.CatalogManagementContentRequest
import model.content.CatalogManagementContentRequestItem
import model.content.Value
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CatalogManagementContentRequestTest {

    @Test
    fun checkIfObjectIsProperRequest() {
        val attributes = Attributes()
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementContentRequest()
        val contentRequest = CatalogManagementContentRequestItem(value = Value(attributes = attributes))
        contentRequest.op = "add"
        contentRequest.path = "/products/ptr0921110"
        request.add(contentRequest)

        println(ObjectMapper().writeValueAsString(request))

        Assertions.assertEquals(
            "[{\"op\":\"add\",\"path\":\"/products/ptr0921110\",\"value\":{\"attributes\":{\"part_number\":\"PTR09-21110\",\"model_year_code\":[\"2022:1234,2021:1231\"]},\"views\":null}}]",
            ObjectMapper().writeValueAsString(request),
            "Values are matching"
        )
    }

    @Test
    fun checkIfObjectDoesntHaveOp() {
        val attributes = Attributes()
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementContentRequest()
        val contentRequest = CatalogManagementContentRequestItem(value = Value(attributes = attributes))
//        contentRequest.op = "add"
        contentRequest.path = "/products/ptr0921110"
        request.add(contentRequest)

        val json = ObjectMapper().writeValueAsString(request)
        if(json.contains("\"op\":null")) {
            Assertions.assertTrue(true, "op should not be null")
        } else {
            Assertions.assertTrue(false, "op is added")
        }
    }

    @Test
    fun checkIfFieldAreAddedToAttributes() {
        val attributes = Attributes()
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementContentRequest()
        val contentRequest = CatalogManagementContentRequestItem(value = Value(attributes = attributes))
        contentRequest.op = "add"
        contentRequest.path = "/products/ptr0921110"
        request.add(contentRequest)

        val json = ObjectMapper().writeValueAsString(request)

        if(json.contains("\"part_number\":\"PTR09-21110\"")) {
            Assertions.assertTrue(true, "Other Field is added to attribute")
        } else {
            Assertions.assertTrue(false, "Other Field is not added to attribute")
        }
    }

}