name := "mainapp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0" % "test",
  "org.fluentlenium" % "fluentlenium-festassert" % "0.9.0" % "test",
  "co.wds" %% "usermgmt" % "1.0-SNAPSHOT",
  "co.wds" %% "usermgmt" % "1.0-SNAPSHOT" classifier "assets",
  "co.wds" %% "usermgmt" % "1.0-SNAPSHOT" classifier "tests",
  "cpsuite" % "cpsuite" % "1.2.5" % "test"
)

resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://maven.xwiki.org/externals"