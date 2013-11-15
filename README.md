# What is MoSKito?
MoSKito is an open source system for monitoring performance and behavior of Java web applications.

Read more: [moskito.org](http://moskito.org) and [Confluence Documentation space](https://confluence.opensource.anotheria.net/display/MSK/Home).

# Project Overview
This project is the heart of MoSKito system. It is what you need to fully monitor a single-node (one JVM) application: collect performance data, view and analyse it, and store some of that info.

We also refer to this project as **MoSKito-Essential**, since it contains the most basic MoSKito functionality.

Read more: [About MoSKito-Essential](https://confluence.opensource.anotheria.net/display/MSK/About+MoSKito-Essential) and [Start working, step by step](https://confluence.opensource.anotheria.net/display/MSK/Start+working%2C+step+by+step).

The full documentation is on [Project's Confluence space](https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential). 

# Components

In MoSKito, we differentiate between Projects (which can be release as standalone applications) and Components (which are parts of a project in *maven* sense). Below is the list of components that make up this Project.

[**MoSKito-Core**](https://github.com/anotheria/moskito/tree/master/moskito-core) is the main MoSKito engine that does all performance counting.

[**MoSKito-Web**](https://github.com/anotheria/moskito/tree/master/moskito-web) contains general support for web applications: base classes for filters and servlets.

[**MoSKito-WebUI**](https://github.com/anotheria/moskito/tree/master/moskito-webui) is the embedded  web-based user interface.

[**MoSKito-Aop**](https://github.com/anotheria/moskito/tree/master/moskito-aop) contains annotations for *aop-style* integration for *AspectJ* and *spring-aop*.

[**MoSKito-Integration**](https://github.com/anotheria/moskito/tree/master/moskito-integration) offers specific integration tools for CDI (JBoss, Glassfish), SQL, etc.

[**MoSKito-Minimal**](https://github.com/anotheria/moskito/tree/master/moskitominimal) is a small standalone web app, used for basic Tomcat monitoring.

