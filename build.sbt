import scala.collection.Seq

lazy val scala_2_13 = "2.13.14"

ThisBuild / versionScheme := Some("early-semver")

val configVersion = "1.4.3"
val playVersion = "3.0.4"
val betterFilesVersion = "3.9.2"
val prometheusClientVersion = "0.9.0"
val scalacticVersion = "3.2.19"
val scalaLoggingVersion = "3.9.5"
val logbackClassicVersion = "1.5.6"
val commonsIOVersion = "2.16.1"
val monixVersion = "3.4.1"
val commonsLangVersion = "3.14.0"
val scalaTestVersion = "3.2.19"
val scalaMockVersion = "6.0.0"
// Don't forget to match the org.scalatestplus artifact with the mockito version
// If you change the mockito version you need to match the scalatestplus group ie org.scalatestplus.mockito-4-2 is for mockito-core 4.2.x
val mockitoVersion = "5.11.0"
val scalaTestPlusMockitoArtifact = "mockito-4-2"
val scalaTestPlusMockitoVersion = "3.2.11.0"
val scalaCheckVersion = "1.18.0"
val apachePoiVersion = "5.2.5"
val apachePoiXMLVersion = "4.1.2"
val tikaVersion = "2.9.2"
val commonsFileUpload = "1.5"
val jaiImageJPEG2000Version = "1.4.0"
val jbig2ImageioVersion = "3.0.4"
val log4jVersion = "2.23.1"

lazy val root = (project in file("."))
  .settings(
    name := "common-util",
    organization := "io.doclib",
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
    releaseIgnoreUntrackedFiles := true,
    githubOwner := sys.env.getOrElse("GITHUB_USERNAME", ""),
    githubRepository := sys.env.getOrElse("GITHUB_PACKAGE_REPO", "scala-packages"),
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
        "org.apache.commons" % "commons-lang3"               % commonsLangVersion,
        "commons-fileupload" % "commons-fileupload"          % commonsFileUpload,
        "org.apache.logging.log4j" % "log4j-core"            % log4jVersion,
        "org.apache.tika" % "tika-core"                      % tikaVersion,
        "org.apache.tika" % "tika-parsers-standard-package"  % tikaVersion,
        "org.apache.tika" % "tika-parsers"                   % tikaVersion,
        "org.apache.tika" % "tika-langdetect"                % tikaVersion,
        "org.apache.poi" % "poi"                             % apachePoiVersion,
        "org.apache.poi" % "poi-ooxml"                       % apachePoiVersion,
        "org.apache.pdfbox" % "jbig2-imageio"                % jbig2ImageioVersion,
        "com.github.jai-imageio" % "jai-imageio-jpeg2000"    % jaiImageJPEG2000Version
      )
    }
  )