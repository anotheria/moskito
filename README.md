![MoSKito](/Users/dmrachko/Pictures/Snagit/Screenshots/MoSKito/moskito_logo.png "MoSKito Monitoring")

# What is MoSKito?
MoSKito is an open source system for monitoring performance and behavior of Java web applications.

### [moskito.org](http://moskito.org) | [Documentation](https://confluence.opensource.anotheria.net/display/MSK/Home) | [Blog](http://blog.anotheria.net)

# What the Project does?
This Project is the heart of MoSKito system. It's what you need to fully monitor a single-node (one JVM) application: collect performance data, view, analyse and store some of it.

We also refer to this project as **MoSKito-Essential**, since it contains the most basic MoSKito functionality.

## Why is it good?

**Multi-purpose:** collects any possible type of performance data, including business-related (registrations, payments, conversion, etc). 

**Non-invasive:** does not require any code change.

**Supports all major app servers:** Tomcat, Jetty, JBoss, Glassfish, WebLogic and others.

**Interval-based:** works simultaneously with short (from 1min) & long (up to 24h) time intervals, allowing instant comparison.

**Data privacy:** keeps collected data locally, with no 3rd party resources involved.

**Performance thresholds:** reports a selected parameter's health  with the traffic-light-like notification system.

**Analysis tools:** displays accumulated performance data in charts.

**Live profiling:** records user actions as system calls.

**Support for mobile platforms:** free iOS apps, Android app's coming soon.

[Read more about the Project and its features](https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Overview)

## Why do I trust it?

1. MoSKito started at 2007 and never stopped developing since then.
2. Since 2008, it's absolutely stable and production-ready. 
3. MoSKito's being used on many production systems ([Allyouneed](https://www.allyouneed.com), [friendscout24](http://www.friendscout24.de), [c-date.com](http://www.c-date.com), [parship.de](http://www.parship.de) and others).

## What's inside?

In MoSKito, we differentiate between Projects (which can be release as standalone applications) and Components (which are parts of a project in *maven* sense). [Read more...](http://blog.anotheria.net/?p=485&preview=true) 

Below is the list of **components** that make up this Project.

[**MoSKito-Core**](https://github.com/anotheria/moskito/tree/master/moskito-core) is the main MoSKito engine that does all performance counting.

[**MoSKito-Web**](https://github.com/anotheria/moskito/tree/master/moskito-web) contains support for web applications: base classes for filters and servlets.

[**MoSKito-WebUI**](https://github.com/anotheria/moskito/tree/master/moskito-webui) is the embedded  web-based user interface.

[**MoSKito-Aop**](https://github.com/anotheria/moskito/tree/master/moskito-aop) contains annotations for *aop-style* integration for *AspectJ* and *spring-aop*.

[**MoSKito-Integration**](https://github.com/anotheria/moskito/tree/master/moskito-integration) offers specific integration tools for CDI (JBoss, Glassfish), SQL, etc.

[**MoSKito-Minimal**](https://github.com/anotheria/moskito/tree/master/moskitominimal) is a small standalone web app, used for basic Tomcat monitoring.

# Install & Config

MoSKito doesn't need any operations with your code. It plugs into any application via filters, proxies, annotations, inheritance, AOP, class loaders or plain calling.

1. [Get it](https://confluence.opensource.anotheria.net/display/MSK/Getting+MoSKito-Essential)
2. [Integrate it into your app](https://confluence.opensource.anotheria.net/display/MSK/Integration+Guide)
3. [Configure it](https://confluence.opensource.anotheria.net/display/MSK/Configuration+Guide)
4. [Set up UI](https://confluence.opensource.anotheria.net/display/MSK/Embedding+Moskito-WebUI+Into+Your+Application)

**Enjoy!**


# [ChangeLog](https://confluence.opensource.anotheria.net/display/MSK/Change+Log)

# License

MoSKito is free and open source (MIT License). Use it as you wish.

[Read License](https://github.com/anotheria/moskito/blob/master/LICENSE)

# Project resources

#### [Webpage](http://www.moskito.org/)
#### [FAQ](https://confluence.opensource.anotheria.net/display/MSK/MoSKito+FAQ)
#### [Documentation](https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential)

# Support and Feedback

**We're willing to help everyone.**

For any questions, write to [moskito@anotheria.net](mailto: moskito@anotheria.net).

# Other MoSKito Projects

### [MoSKito-Central](https://github.com/anotheria/moskito-central)

### [MoSKito-Control](https://github.com/anotheria/moskito-control)