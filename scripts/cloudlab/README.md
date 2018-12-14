To run scripts within the cloudlab folder, you need to create two files *WITHIN* this folder.

# Provide your Cloudlab.us username 
- Create a file called "myUserName.txt"
- Enter your Cloudlab.us username in one single line

# Provide node information from Cloudlab.us experiment
- Login cloudlab.us and goto the Manifest tab of your experiment
- Create a new file "Manifest.xml"
- Copy&Past all contents from the Manifest tab into "Manifest.xml"

The content should look like the following:
```xml
<rspec xmlns="http://www.geni.net/resources/rspec/3" xmlns:emulab="http://www.protogeni.net/resources/rspec/ext/emulab/1" xmlns:tour="http://www.protogeni.net/resources/rspec/ext/apt-tour/1" xmlns:jacks="http://www.protogeni.net/resources/rspec/ext/jacks/1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.geni.net/resources/rspec/3    http://www.geni.net/resources/rspec/3/request.xsd" type="request">
... contents removed for privacy reason ...
</rspec>

```

 

