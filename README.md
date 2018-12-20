# Video and Audio Broadcast App with Java

### Summary:

This is simple stream app written with Java. Client/Server architecture and sockets used. Video and audio captured Server's webcam and microphone. Clients can watch Server's stream.

###   Requirements:
* Webcam and Microphone.
* JRE 1.8+ or JDK 1.8+.
* Eclipse IDE. (Recommended)
  
### How to Run on Eclipse:

* Download or clone this repository. Then open Eclipse IDE.
* File **>** Open Projects from File System... **>** Directory **>** Select Broadcast Folder **>** Finish.
* Right click Broadcast Project at Package Explorer and go Properties **>** Java Build Path (Left side) **>** Libraries (Top side).
* Select 4 JARs and delete:
	* bridj-0.7.0.jar
	* slf4j-api-1.7.25.jar
	* slf4j-nop-1.7.25.jar
	* webcam-capture-0.3.12.jar
* Then click Add External JARs (Right side) and go to jar folder in Broadcast folder.
* Select 4 JARs and Appy changes.
* Go Broadcast **>** src **>** broadcast and double click RunClient.java and RunServer.java at Package Explorer.
* You can edit Audio Port, Video Port. (They can't be the same.)
* Run Server first. Then run Client. You can run more than one Client.

### Broadcast Test:
* Use two computer one of Server and one of Client.
* Server and Client must be in same network.
* Run Server on computer which have working webcam and microphone.
* Edit Client code and write Server's IP address instead of localhost.
* Run Client and you can see Server's webcam video and hear voice which captured Server's microphone.

### Standalone Audio/Video Broadcast:

* Audio and Video stream can work standalone. All classes have main method.
* Use AudioServer.java and AudioClient.java in audio package for stream only audio.
* Use WebcamServer.java and WebcamClient.java in webcam package for stream only video.

### Thanks:

* Thanks [@sarxos](https://github.com/sarxos) for [Webcam Capture API](https://github.com/sarxos/webcam-capture).

---

**Alperen Cubuk**