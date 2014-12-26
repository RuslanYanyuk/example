import play.PlayImport.PlayKeys._

name := "usermgmt"

organization := "co.wds"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

packagedArtifacts in publishLocal := {
  val artifacts: Map[sbt.Artifact, java.io.File] = (packagedArtifacts in publishLocal).value
  val assets: java.io.File = (playPackageAssets in Compile).value
  artifacts + (Artifact(moduleName.value, "jar", "jar", "assets") -> assets)
}

// enable publishing the jar produced by `test:package`
publishArtifact in (Test, packageBin) := true

// enable publishing the test API jar
publishArtifact in (Test, packageDoc) := true

// enable publishing the test sources jar
publishArtifact in (Test, packageSrc) := true

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