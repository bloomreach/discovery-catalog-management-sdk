# Catalog Management SDK Integration

[GO BACK](README.md)
## SFTP Upload Reference

### To create json for product or content Library can be used.

For example: Create an Product Request Object

```
//Create Attribute object
        Attributes attributes = new Attributes();
        attributes.setTitle("Title");
        attributes.setUrl("example/url/path");

//Create Value Object
        Value value = new Value();
        value.setAttributes(attributes);
        value.setViews(views);

//Create DataConnectProductRequestItem and set Create Value to the item
        DataConnectProductRequestItem item = new DataConnectProductRequestItem();
        item.setOp("add");
        item.setPath("/products/ptr0921110");
        item.setValue(value);
```

### Convert the object to JSON string

```
        String json = item.toJson();
//Write the json to jsonl file.
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("fileName.jsonl"));
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            // handle exception 
        }
```
Note: Any of the File creation Java libraries can be used here.


### Upload the file to SFTP server

Note: Here [JSch Library](http://www.jcraft.com/jsch/) has been used to demonstrate the SFTP File upload process. Any other Libraries can be used
```
        try {
            jSch.addIdentity(<privateKeyPath>, "<passphrase>");

            session = jSch.getSession(“<SFTPUSER>”, “<SFTPHOST>”, “<SFTPPORT>”);
       
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(<SFTPWORKINGDIR>);
            //putting the file
            channelSftp.put("<src/fileName.jsonl>", "fileName.jsonl");
        } catch (JSchException e) {
            //handle exception
        } catch (SftpException e) {
            //handle exception
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
                channelSftp.exit();
            }
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
```

### Ingest PUT the feed using following API

```
ArrayList<String> request = new ArrayList<>();
request.add("<catalog name>/<catalog1.jsonl>");
request.add("<catalog name>/<catalog2.jsonl>");

CatalogManagementResponse response = productApi.ingestPut(request);

if(response.getError() != null) {
// error
} else {
//  success
 }
```

For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).
