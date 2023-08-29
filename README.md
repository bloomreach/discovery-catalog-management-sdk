# Catalog Management SDK Integration

## Product

### Initializing the Product API object:
```
DcProductApi productApi = new DcProductApi.Builder()
     		.accountId("<ACCOUNT_ID>")
                .catalogName("<CATALOG_NAME>")
                .authorizationKey("<AUTHORIZATION_KEY>")
                .build();
```

| Parameter  | Description |
| ------------- | ------------- |
| accountId  | Account ID provided by Bloomreach  |
| catalogName  | Catalog name  |
| authorizationKey  | Points to where this information will be stored on the backend represented with catalog name and language key. Use the same value as your domain key in your search API requests.|
| environment  | Server environment to be execute the request against, STAGE or PROD |
| baseUrl  | Base URL for a custom staging or performance testing environment  |
| connectionTimeOut  | Connection timeout in milliseconds  |
| maxTotalConnections  | Max total connections  |
| responseTimeout  | CConnection timeout for getting a response  |


### PUT

1. ** PUT request using a JSON String **

```
DataConnectResponse response = productApi.ingestPut(“<JSON_STRING>”);

if(response.getError() != null) {
// error
} else {
//  success
 }
```

For more information on the format of the JSON String, please refer to the [Format your Data (Product)](https://documentation.bloomreach.com/discovery/reference/format-your-data-product) page.


2. PUT request using an Object


```
// Create Attributes object
Attributes attributes = new Attributes();
attributes.setTitle("Title");
attributes.setUrl("example/url/path");

```

```
// Create View object
LinkedHashMap<String, Value> views = new LinkedHashMap<String,Value>();
Value valueForView = new Value();

Attributes attributesForView = new Attributes();
attributesForView.setTitle("Title");
attributesForView.setUrl("example/url/path");
valueForView.setAttributes(attributesForView);

views.put("view-key", valueForView);
```

```
// Create Value Object
Value value = new Value();
value.setAttributes(attributes);
value.addView("view-key",valueForView);
```
```
// Create DataConnectProductRequestItem and set Create Value to the item
DataConnectProductRequestItem item = new DataConnectProductRequestItem("add", "/products/ptr0921110", value);
```

```
// Create DataConnectProductRequest object and add DataConnectProductRequestItem
DataConnectProductRequest request = new DataConnectProductRequest();
request.add(item);
DataConnectResponse response = productApi.ingestPut(request);

if(response.getError() != null) {
// error
} else {
//  success
 }
```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).


### PATCH

1. PATCH request using a JSON String

```
DataConnectResponse response = productApi.ingestPatch(“<JSON_STRING>”);

if(response.getError() != null) {
// error
} else {
//  success
 }
```

2. PATCH request using an Object
```
DataConnectResponse response = productApi.ingestPut(request);

if(response.getError() != null) {
// error
} else {
//  success
}

```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).


### INDEX

```
DataConnectResponse response = productApi.index(“<JOB_ID>”);

if(response.getError() != null) {
// error
} else {
//  success
 }
```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).


### STATUS

```
StatusResponse response = productApi.getStatus(“<JOB_ID>”);

if(response.getError() != null) {
// error
} else {
//  success
}
```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).


-----

## Content

### Initializing the Content API object:
```
DcContentApi contentApi = new DcContentApi.Builder()
     		.accountId("<ACCOUNT_ID>")
           	.catalogName("<CATALOG_NAME>")
            	.authorizationKey("<AUTHORIZATION_KEY>")
            	.build();

```

| Parameter  | Description |
| ------------- | ------------- |
| accountId  | Account ID provided by Bloomreach  |
| catalogName  | Catalog name  |
| authorizationKey  | Points to where this information will be stored on the backend represented with catalog name and language key. Use the same value as your domain key in your search API requests.|
| environment  | Server environment to be execute the request against, STAGE or PROD |
| baseUrl  | Base URL for a custom staging or performance testing environment  |
| connectionTimeOut  | Connection timeout in milliseconds  |
| maxTotalConnections  | Max total connections  |
| responseTimeout  | CConnection timeout for getting a response  |


### PUT

1. ** PUT request using a JSON String **

```
DataConnectResponse response = contentApi.ingestPut(jsonBody);

if(response.getError() != null) {
// error
} else {
//  success
 }
```

For more information on the format of the JSON String, please refer to the [Format your Data (Product)](https://documentation.bloomreach.com/discovery/reference/format-your-data-content) page.


2. PUT request using an Object


```
// Create Attributes object

Attributes attributes = new Attributes();
attributes.setTitle("Title");
attributes.setUrl("example/url/path");

```
```
//Create Value Object

Value value = new Value();
value.setAttributes(attributes);
value.setViews(views);
```

```
 //Create DataConnectProductRequestItem and set Create Value to the item

DataConnectProductRequestItem item = new DataConnectProductRequestItem();
item.setOp("add");
item.setPath("/products/ptr0921110");
item.setValue(value);

```

```
// Create DataConnectContentRequest object and add DataConnectContentRequest

DataConnectContentRequest request = new DataConnectContentRequest();
        request.add(item);

DataConnectResponse response = contentApi.ingestPut(request);

if(response.getError() != null) {
// error
} else {
//  success
}

```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-content).


### PATCH

1. PATCH request using a JSON String

```
DataConnectResponse response = contentApi.ingestPatch(“<JSON_STRING>”);

if(response.getError() != null) {
// error
} else {
//  success
 }

```

2. PATCH request using an Object
```
DataConnectResponse response = contentApi.ingestPut(request);

if(response.getError() != null) {
// error
} else {
//  success
 }
```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-content).



### INDEX

```
DataConnectResponse response = contentApi.index(“<JOB_ID>”);

if(response.getError() != null) {
// error
} else {
//  success
}

```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).


### STATUS

```
StatusResponse response = contentApi.getStatus(“<JOB_ID>”);

if(response.getError() != null) {
// error
} else {
//  success

}
```
For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).




