# OpenScriptingEnd

#### What is OpenScriptingEnd ?
OpenScriptingEnd is a functionality that will be included with JShell.  
It make even the average user more productive. It uses javascript parser nashorn to parse javascript files.  

#### Parsing Javascript? Even a browser can do that what's special ?
Unlike regular JavaScript OpenScriptingEnd runs on the system rather than the browser.
Therefore, it has great power in controling user's files, storing configuration, compressing files and folders,  
Archiving, etc. OpenScriptingEnd can also interact with Java. Making it possible for creating TCP Servers and Clients.
All of this doesn't require any sort of special code. They can all be programmed in JavaScript.
OpenScriptingEnd will soon get a native feature where, you can load compiled c/c++ native code from .dll and .so files and use it.
OpenScriptingEnd will by default include certain libraries implementations. For a custom .dll or .so file you'll have to write or use a .jar package/implementation. And this can be done using Packages. Any jar can be put into a pkgs/ directory and all those jars will be loaded on runtime.

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
