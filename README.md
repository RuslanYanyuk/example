Xi User Management Module
==========================

How to use the module
----------------------

* After clone project run locally (with activator) ```publish-local```

* Add module dependency to ```build.sbt```:

```"co.wds" % "usermgmt_2.11" % "1.0" classifier "assets"```

* For downloading modules routes add to routes file the following line:

```->     /        usermgmt.Routes```

* In main application add empty file ```1.sql``` in evolution.usermgmt

* Use ```@Dynamic``` annotation.  If your method has annotated like  ```@Dynamic("Logged in")```, then to method have access all logined users. Use annotation ```@Dynamic("ROLE_NAME")``` for allow access only user with role ```ROLE_NAME```