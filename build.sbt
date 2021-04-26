lazy val scala_2_13 = "2.13.3"

lazy val root = (project in file("."))
  .settings(
    Defaults.itSettings,
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
    resolvers         ++= Seq(
      "MDC Nexus Releases" at "https://nexus.wopr.inf.mdc/repository/maven-releases/",
      "MDC Nexus Snapshots" at "https://nexus.wopr.inf.mdc/repository/maven-snapshots/"),
    credentials       += {
      sys.env.get("NEXUS_PASSWORD") match {
        case Some(p) =>
          Credentials("Sonatype Nexus Repository Manager", "nexus.wopr.inf.mdc", "gitlab", p)
        case None =>
          Credentials(Path.userHome / ".sbt" / ".credentials")
      }
    },
    libraryDependencies ++= {
      val prometheusClientVersion = "[0.9.0,1["
      Seq(
        "org.scalactic" %% "scalactic"                  % "[3.2.0,4[" % Test,
        "org.scalatest" %% "scalatest"                  % "[3.2.0,4[" % Test,
        "org.scalamock" %% "scalamock"                  % "[4.4.0,5[" % Test,
        "org.scalacheck" %% "scalacheck"                % "[1.14.3,2[" % Test,
        "org.mockito" % "mockito-core"                  % "[3.3.3,4["    % Test,
        "org.scalatestplus" %% "mockito-3-3"            % "[3.2.2.0,4[" % Test,
        "com.typesafe.scala-logging" %% "scala-logging" % "[3.9.2,4[",
        "com.typesafe" % "config"                       % "[1.3.2,2[",
        "com.typesafe.play" %% "play-json"              % "[2.9.0,3[",
        "io.monix" %% "monix"                           % "[3.2.2,4[",
        "com.github.pathikrit"  %% "better-files"       % "[3.9.1,4[",
        "commons-io" % "commons-io"                     % "[2.7,3[",
        "io.prometheus" % "simpleclient"                % prometheusClientVersion,
        "io.prometheus" % "simpleclient_hotspot"        % prometheusClientVersion,
        "io.prometheus" % "simpleclient_httpserver"     % prometheusClientVersion,
        "ch.qos.logback" % "logback-classic"            % "[1.2.3,2[",
        "org.apache.commons" % "commons-lang3"          % "[3.9,4["
      )
    }
  )
  .settings(
    publishSettings: _*
  )

lazy val publishSettings = Seq(
  publishTo := {
    val version = if (isSnapshot.value) "snapshots" else "releases"
    Some("MDC Maven Repo" at s"https://nexus.wopr.inf.mdc/repository/maven-$version/")
  },
  credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
)
