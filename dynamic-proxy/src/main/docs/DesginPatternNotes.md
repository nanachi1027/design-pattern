#### Differences between Decorate, Facade, Proxy and Adapter
* Decorator wraps another object and provides a different interface to it
* Facade wraps another object and provides additional behavior for it
* Proxy wraps another object to control the access to it
* Adapter wraps a bunch of objects to simple their interface



#### The Adapter Pattern 
The adapter acts as the middleman by receiving request from the client and converting them into requests 
that make sense on the vendor classes.  
So when we talk about or adopt this pattern in our project, the first thing we should thought is :<b>compatibility</b>. 

Adapter is given in this demo, to get a better understanding of how Adapter is working I write a demo of Adapter Pattern 
by refer to Head First Design Patterns. And this code link is: [Adapter Demo](https://github.com/nanachi1027/JavaProxyTutorial/tree/master/dynamic-proxy/src/main/java/com/nanachi/adapter).

And its UML is shown as below: ![adpater uml](https://raw.githubusercontent.com/nanachi1027/JavaProxyTutorial/master/dynamic-proxy/src/main/docs/adapter_uml.png "Adapter UML")
