# Kickstarter-Parser
Basically this tool is supposed to watch a pledge of your choice on (hopefully) any kickstarter project page. It will inform you about any occurring changes on your watched pledge. You can use this tool to watch, for example, a pledge that is "out of stock".  
It's self-explaining. The application will terminate itself if changes occur on one of your watched pledges.


# Features
- Watches one Kickstarter Project
- Watches multiple pledge options(should be out of stock) of that one kickstarter project 
- Opens the "change pledge" website of your watched project in your default web browser, when one of your watched pledges' available quantity changes



# Requirements
- [Java Runtime Environment] - Also known as JRE
- For the feature "open change pledge website in default browser" to work,  you need to log in to your kickstarter account and tick the "Remember me" checkbox in order for your default browser to directly redirect you to your "change pledge site"

# Usage

Open the cmd.exe (Windows) or the terminal (Linux & macOS)  
Navigate with "cd foldername" to the folder containing th downloaded '.jar file'  
Open the .jar file and follow the on-screen instructions.  

```sh
cd Desktop
java -jar Kickstarter-Parser.jar
```

[Java Runtime Environment]:http://java.com/de/download/

