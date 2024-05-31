import scala.collection.Seq

lazy val creds = {
  sys.env.get("CI_JOB_TOKEN") match {
    case Some(token) =>
      Credentials("GitLab Packages Registry", "gitlab.com", "gitlab-ci-token", token)
    case _ =>
      Credentials(Path.userHome / ".sbt" / ".credentials")
  }
}

// Registry ID is the project ID of the project where the package is published, this should be set in the CI/CD environment
val registryId = sys.env.get("REGISTRY_HOST_PROJECT_ID").getOrElse("")

lazy val scala_2_13 = "2.13.14"

val configVersion = "1.4.3"
val playVersion = "3.0.2"
val betterFilesVersion = "3.9.2"
val prometheusClientVersion = "0.9.0"
val scalacticVersion = "3.2.18"
val scalaLoggingVersion = "3.9.5"
val logbackClassicVersion = "1.5.6"
val commonsIOVersion = "2.16.1"
val monixVersion = "3.4.1"
val commonsLangVersion = "3.14.0"
val scalaTestVersion = "3.2.18"
val scalaMockVersion = "6.0.0"
// Don't forget to match the org.scalatestplus artifact with the mockito version
// If you change the mockito version you need to match the scalatestplus group ie org.scalatestplus.mockito-4-2 is for mockito-core 4.2.x
val mockitoVersion = "5.11.0"
val scalaTestPlusMockitoArtifact = "mockito-4-2"
val scalaTestPlusMockitoVersion = "3.2.11.0"
val scalaCheckVersion = "1.18.0"

lazy val root = (project in file("."))
  .settings(
    name := "util",
    organization := "io.mdcatapult.klein",
    scalaVersion := scala_2_13,
    useCoursier := false,
    crossScalaVersions := scala_2_13 :: Nil,
    scalacOptions ++= Seq(
      "-encoding", "utf-8",
      "-unchecked",
      "-deprecation",
      "-explaintypes",
      "-feature",
      "-Xlint",
      "-Xfatal-warnings",
    ),
    resolvers ++= Seq(
      "gitlab" at s"https://gitlab.com/api/v4/projects/$registryId/packages/maven",
      "Maven Public" at "https://repo1.maven.org/maven2"),
    publishTo := {
      Some("gitlab" at s"https://gitlab.com/api/v4/projects/$registryId/packages/maven")
    },
    libraryDependencies ++= {
      Seq(
        "org.scalactic" %% "scalactic"                       % scalacticVersion % Test,
        "org.scalatest" %% "scalatest"                       % scalaTestVersion % Test,
        "org.scalamock" %% "scalamock"                       % scalaMockVersion % Test,
        "org.scalacheck" %% "scalacheck"                     % scalaCheckVersion % Test,
        "org.mockito" % "mockito-core"                       % mockitoVersion % Test,
        "org.scalatestplus" %% scalaTestPlusMockitoArtifact  % scalaTestPlusMockitoVersion % Test,
        "com.typesafe.scala-logging" %% "scala-logging"      % scalaLoggingVersion,
        "com.typesafe" % "config"                            % configVersion,
        "org.playframework" %% "play-json"                   % playVersion,
        "io.monix" %% "monix"                                % monixVersion,
        "com.github.pathikrit"  %% "better-files"            % betterFilesVersion,
        "commons-io" % "commons-io"                          % commonsIOVersion,
        "io.prometheus" % "simpleclient"                     % prometheusClientVersion,
        "io.prometheus" % "simpleclient_hotspot"             % prometheusClientVersion,
        "io.prometheus" % "simpleclient_httpserver"          % prometheusClientVersion,
        "ch.qos.logback" % "logback-classic"                 % logbackClassicVersion,
        "org.apache.commons" % "commons-lang3"               % commonsLangVersion
      )
    }
  )