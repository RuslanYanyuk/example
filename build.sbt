name := "usermgmt"

organization := "co.wds"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "commons-io" % "commons-io" % "2.4",
  "com.google.inject" % "guice" % "3.0",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0" % "test",
  "org.fluentlenium" % "fluentlenium-festassert" % "0.9.0" % "test",
  "be.objectify" %% "deadbolt-java" % "2.3.1"
)

resolvers += Resolver.url("Objectify Play Repository", url("http://deadbolt.ws/releases/"))(Resolver.ivyStylePatterns)

Keys.fork in Test := false