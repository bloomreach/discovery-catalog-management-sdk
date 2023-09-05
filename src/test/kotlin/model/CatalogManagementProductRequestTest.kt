package model

import com.fasterxml.jackson.databind.ObjectMapper
import model.product.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CatalogManagementProductRequestTest {

    @Test
    fun checkIfObjectIsProperRequest() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        Assertions.assertEquals(
            "[{\"op\":add,\"path\":\"/products/ptr0921110\",\"value\":{\"attributes\":{\"availableInAppOnly\":null,\"brand\":null,\"description\":null,\"division\":null,\"giftwrappable\":null,\"images\":null,\"isproducthide\":null,\"onlineFlag\":null,\"pfs\":null,\"pfsUsage\":null,\"rls\":null,\"searchableFlag\":null,\"sfccClass\":null,\"sfccSubClass\":null,\"sfccSubcategory\":null,\"title\":\"TRD Brake Pads\",\"url\":\"/products/product/trd-brake-pads-ptr0921110\",\"currency\":null,\"price\":null,\"color\":null,\"cost\":null,\"material\":null,\"materialnumber\":null,\"pattern\":null,\"primarysize\":null,\"refid\":null,\"size\":null,\"upc\":null,\"availability\":null,\"inventory\":null,\"shipping\":null,\"catalogCode\":null,\"divisionApi\":null,\"kidsSubClassSFCC\":null,\"longDescription\":null,\"permissions\":null,\"sizechartCid\":null,\"categoryPaths\":null,\"classX\":null,\"colorGroup\":null,\"colorGroupsApi\":null,\"largeImage\":null,\"sizeGroup\":null,\"thumbImage\":null,\"vendorStyle\":null,\"salePrice\":null,\"hazmat\":null,\"ipbrand\":null,\"ipdepartment\":null,\"part_number\":\"PTR09-21110\",\"model_year_code\":[\"2022:1234,2021:1231\"]},\"variants\":null,\"views\":null}}]",
            ObjectMapper().writeValueAsString(request),
            "Values are matching"
        )
    }

    @Test
    fun checkIfObjectDoesntHaveOp() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.fields["part_number"] = "PTR09-21110"
        attributes.fields["model_year_code"] = listOf("2022:1234,2021:1231")

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
//        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)

        if(json.contains("\"op\":null")) {
            Assertions.assertTrue(true, "op should not be null")
        } else {
            Assertions.assertTrue(false, "op is added")
        }
    }

    @Test
    fun checkIfFieldAreAddedToAttributes() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()
        val productRequestItem = CatalogManagementProductRequestItem(value = Value(attributes = attributes))
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)

        if(json.contains("\"part_number\":\"PTR09-21110\"")) {
            Assertions.assertTrue(true, "Other Field is added to attribute")
        } else {
            Assertions.assertTrue(false, "Other Field is not added to attribute")
        }
    }

    @Test
    fun checkIfAttributeOtherFieldIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()
        val value = Value(attributes = attributes)
        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        //here other field is added
        productRequestItem.value?.addAttributeField("part_number", "PTR09-21110")

        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)

        if(json.contains("\"part_number\":\"PTR09-21110\"")) {
            Assertions.assertTrue(true, "Other Field is added to attribute")
        } else {
            Assertions.assertTrue(false, "Other Field is not added to attribute")
        }
    }

    @Test
    fun checkIfVariantIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val variant = LinkedHashMap<String, Attributes>()
        variant.put("national", Attributes(price = 95.0, salePrice = 80.75, availability = true) )

        val request = CatalogManagementProductRequest()

        val value = Value(attributes = attributes)
        //to check if this worked
        value.addVariant("national", Variants(attributes = Attributes(price = 95.0, salePrice = 80.75, availability = true)))

        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)
        println(json)

        if(json.contains("\"variants\": {\"national\":")) {
            Assertions.assertTrue(true, "Variant is added to Value")
        } else {
            Assertions.assertTrue(false, "Variant is not added to Value")
        }
    }


    @Test
    fun checkIfVariantAttributeIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()

        val value = Value(attributes = attributes)
        value.addVariant("national", Variants(attributes = Attributes(price = 95.0, salePrice = 80.75, availability = true)))
        //to check if this worked
        value.addVariantAttributeField("national", "shipping", "online")

        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)
        println(json)

        if(json.contains("\"shipping\":\"online\"")) {
            Assertions.assertTrue(true, "Variant is added to Value")
        } else {
            Assertions.assertTrue(false, "Variant is not added to Value")
        }
    }


    @Test
    fun checkIfViewIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()

        val value = Value(attributes = attributes)
        //to check if this worked
        value.addView("national", Value(Attributes(
                price = 95.0,
            salePrice = 80.75,
            availability = true,
        )))


        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)
        println(json)

        if(json.contains("\"views\":{\"national\":{\"attributes\"")) {
            Assertions.assertTrue(true, "View is added to Value")
        } else {
            Assertions.assertTrue(false, "View is not added to Value")
        }
    }

    @Test
    fun checkIfViewAttributeIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()

        val value = Value(attributes = attributes)
        //to check if this worked
        value.addAttributesForView("national", Attributes(
            price = 95.0,
            salePrice = 80.75,
            availability = true
        ))


        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)
        println(json)

        if(json.contains("\"views\":{\"national\":{\"attributes\"")) {
            Assertions.assertTrue(true, "View is added to Value")
        } else {
            Assertions.assertTrue(false, "View is not added to Value")
        }
    }


    @Test
    fun checkIfAttributeFieldForViewIsAddedForValue() {
        val attributes = Attributes(title = "TRD Brake Pads", url = "/products/product/trd-brake-pads-ptr0921110")
        attributes.addField("part_number", "PTR09-21110")
        attributes.addField("model_year_code", listOf("2022:1234,2021:1231"))

        val request = CatalogManagementProductRequest()

        val value = Value(attributes = attributes)
        value.addAttributesForView("national", Attributes(
            price = 95.0,
            salePrice = 80.75,
            availability = true
        ))

        //to check if this worked
        value.addAttributeFieldForView("national", "myViewKey", "myViewValue")


        val productRequestItem = CatalogManagementProductRequestItem(value = value)
        productRequestItem.op = "add"
        productRequestItem.path = "/products/ptr0921110"
        request.add(productRequestItem)

        val json = ObjectMapper().writeValueAsString(request)
        println(json)

        if(json.contains("\"myViewKey\":\"myViewValue\"")) {
            Assertions.assertTrue(true, "View is added to Value")
        } else {
            Assertions.assertTrue(false, "View is not added to Value")
        }
    }

}