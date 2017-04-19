# OpenScriptingEnd

#### What is OpenScriptingEnd ?
OpenScriptingEnd is a functionality that will be included with JShell.  
It make even the average user more productive. It uses javascript parser nashorn to parse javascript files.  

#### Parsing Javascript? Even a browser can do that what's special ?
The special thing is it's not regular javascript, but a better one.  
This javascript is able to make TCPServers and TCPClients ( more to come soon ).

#### Why Javascript?
The aim of OpenScriptingEnd is to make Scripting at users side simple and also provide control to the user.
Javascript is extremely easy to learn and understand, which gives it just right edge for OpenScriptingEnd.

#### Note: 
Using functions like alert,etc.. will not work as the window object is absent.
Same goes for the document Object. Also alert() function can work if commons.js is included ( `load()` ).
The commons.js file also contains useful methods and global variables. The commons.js file can be loaded in two ways -  
  [1]. Dowloading commons.js file and using file path `load([relative path]commons.js);`  
  [2]. Using url to find commons.js and load. `load([url to commons.js]);`  

#### External Links
[Learn Javascript](https://www.w3schools.com/js/)  
[Nashorn](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine))  
