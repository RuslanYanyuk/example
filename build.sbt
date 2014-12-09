name := "usermgmt"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "commons-io" % "commons-io" % "2.4",
  "com.google.inject" % "guice" % "3.0",
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0" % "test",
  "org.fluentlenium" % "fluentlenium-festassert" % "0.9.0" % "test",
  "be.objectify" %% "deadbolt-java" % "2.2.1-RC2"
)     

resolvers += Resolver.url("Objectify Play Repository", url("http://deadbolt.ws/releases/"))(Resolver.ivyStylePatterns)

play.Project.playJavaSettings

Keys.fork in Test := false