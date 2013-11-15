# What is MoSKito?
MoSKito is an open source system for monitoring performance and behavior of Java web applications.

### Website
[moskito.org](http://moskito.org)

### Documentation
[MoSKito Confluence space](https://confluence.opensource.anotheria.net/display/MSK/Home)

### Blog
[Anotheria DevBlog](http://blog.anotheria.net)

# Project Overview
This project is the heart of MoSKito system. It is what you need to fully monitor a single-node (one JVM) application: collect performance data, view, analyse and store some of it.

We also refer to this project as **MoSKito-Essential**, since it contains the most basic MoSKito functionality.

## Features

**Multi-purpose:** collects any type of performance data, including business-related (registrations, payments, conversion, etc). 

**Non-invasive:** does not require any code change. Pluggable into any application via filters, proxies, annotations, inheritance, AOP, class loaders or plain calling.

**Supports all major app servers:** Tomcat, Jetty, JBoss, Glassfish, WebLogic and others.

**Interval-based:** works simultaneously with short (from 1min) & long (up to 24h) time intervals, allowing instant comparison.

**Data privacy:** keeps collected data locally, with no 3rd party resources involved.

**Performance alarms:** watches custom functional areas, displaying performance state in the traffic-light-like notification system..

**Analysis tools:** displays accumulated performance data in charts.

**Live profiling:** records user actions as system calls.

**Support for mobile platforms:** free iOS apps, Android app's coming soon.

# Components

In MoSKito, we differentiate between Projects (which can be release as standalone applications) and Components (which are parts of a project in *maven* sense). Below is the list of components that make up this Project.

[**MoSKito-Core**](https://github.com/anotheria/moskito/tree/master/moskito-core) is the main MoSKito engine that does all performance counting.

[**MoSKito-Web**](https://github.com/anotheria/moskito/tree/master/moskito-web) contains general support for web applications: base classes for filters and servlets.

[**MoSKito-WebUI**](https://github.com/anotheria/moskito/tree/master/moskito-webui) is the embedded  web-based user interface.

[**MoSKito-Aop**](https://github.com/anotheria/moskito/tree/master/moskito-aop) contains annotations for *aop-style* integration for *AspectJ* and *spring-aop*.

[**MoSKito-Integration**](https://github.com/anotheria/moskito/tree/master/moskito-integration) offers specific integration tools for CDI (JBoss, Glassfish), SQL, etc.

[**MoSKito-Minimal**](https://github.com/anotheria/moskito/tree/master/moskitominimal) is a small standalone web app, used for basic Tomcat monitoring.

# Install & Config

Integration & Configuration guides.

# ChangeLog

# License

# Support and Feedback
