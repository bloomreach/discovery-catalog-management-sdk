
### SFTP Upload Reference
[GO BACK](README.md)

The Bloomreach Catalog Management SDK does not provide native SFTP support. The following section provides sample SFTP code to send product data using the [JSch Library](http://www.jcraft.com/jsch/).

1. Create an Product data

```
//Create an Attribute object
        Attributes attributes = new Attributes();
        attributes.setTitle("Title");
        attributes.setUrl("example/url/path");

//Create a Value object
        Value value = new Value();
        value.setAttributes(attributes);

//Create DataConnectProductRequestItem and set Create Value to the item
        DataConnectProductRequestItem item = new DataConnectProductRequestItem("add", "/products/ptr0921110", value);
```

2. Convert the object to a JSON string

```
        String json = item.toJson();
```

3. Write the JSON representation to a file.
```
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("fileName.jsonl"));
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            // handle exception 
        } 
```

### Upload the file to SFTP server

Note: Here [JSch Library](http://www.jcraft.com/jsch/) has been used to demonstrate the SFTP File upload process. Any other Libraries can be used
```
        try {
            jSch.addIdentity(<privateKeyPath>, "<passphrase>");
            session = jSch.getSession(“<SFTPUSER>”, “<SFTPHOST>”, “<SFTPPORT>”);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(<SFTPWORKINGDIR>);
            channelSftp.put("<src/fileName.jsonl>", "fileName.jsonl");
        } catch (JSchException e) {
            // handle exception
        } catch (SftpException e) {
            // handle exception
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
                channelSftp.exit();
            }
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
```

5. Ingest the feed
```
        List<String> request = new ArrayList<>();
        request.add("<catalog name>/<catalog1.jsonl>");
        request.add("<catalog name>/<catalog2.jsonl>");
        
        CatalogManagementResponse response = productApi.ingestPut(request);
        
        if(response.getError() != null) {
        // error
        } else {
        // success
        }
```

For more information on the underlying API call and the associated parameters, please refer to the related [Catalog Management API page](https://documentation.bloomreach.com/discovery/reference/send-your-data-product).
