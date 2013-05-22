<a href="http://projectdanube.org/" target="_blank"><img src="http://peacekeeper.github.com/xdi2/images/projectdanube_logo.png" align="right"></a>
<img src="http://peacekeeper.github.com/xdi2/images/logo64.png"><br>

This is a pluging for the [XDI2](http://github.com/projectdanube/xdi2) server that makes it possible to dynamically provision XDI link contracts via [OAuth 2.0](http://oauth.net/2/).

This is work in progress. 

### Information

* Scopes {TODO}
* Tokens {TODO}
* Example Flow {TODO}

### How to build

First, you need to build the main [XDI2](http://github.com/projectdanube/xdi2) project.

After that, just run

    mvn clean install

To build all components.

### How to run

    mvn jetty:run

Then access the web interface at

	http://localhost:9120/

Or access the server's status page at

	http://localhost:9120/xdi

Or use an XDI client to send XDI messages to

    http://localhost:9120/xdi/oauth2

### How to build as XDI2 plugin

Run

    mvn clean install package -P xdi2-plugin

### Community

Google Group: http://groups.google.com/group/xdi2
