import com.gilcloud.sbt.gitlab.{GitlabCredentials,GitlabPlugin}

GitlabPlugin.autoImport.gitlabGroupId     :=  Some(73679838)
GitlabPlugin.autoImport.gitlabProjectId   :=  Some(50550924)

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
    resolvers += ("gitlab" at "https://gitlab.com/api/v4/projects/50550924/packages/maven"),
    credentials += Credentials("GitLab Packages Registry", "gitlab.com", "Job-Token", sys.env.get("CI_JOB_TOKEN").get),
//    resolvers         ++= Seq(
//      "MDC Nexus Releases" at "https://nexus.wopr.inf.mdc/repository/maven-releases/",
//      "MDC Nexus Snapshots" at "https://nexus.wopr.inf.mdc/repository/maven-snapshots/"),
//    credentials       += {
//      sys.env.get("NEXUS_PASSWORD") match {
//        case Some(p) =>
//          Credentials("Sonatype Nexus Repository Manager", "nexus.wopr.inf.mdc", "gitlab", p)
//        case None =>
//          Credentials(Path.userHome / ".sbt" / ".credentials")
//      }
//    },
    libraryDependencies ++= {
      val configVersion = "1.4.1"
      val playVersion = "2.9.2"
      val betterFilesVersion = "3.9.1"
      val prometheusClientVersion = "0.9.0"
      val scalacticVersion = "3.2.10"
      val scalaLoggingVersion = "3.9.4"
      val logbackClassicVersion = "1.2.10"
      val commonsIOVersion = "2.11.0"
      val monixVersion = "3.4.0"
      val commonsLangVersion = "3.12.0"
      val scalaTestVersion = "3.2.11"
      val scalaMockVersion = "5.2.0"
      // Don't forget to match the org.scalatestplus artifact with the mockito version
      // If you change the mockito version you need to match the scalatestplus group ie org.scalatestplus.mockito-4-2 is for mockito-core 4.2.x
      val mockitoVersion = "4.2.0"
      val scalaTestPlusMockitoArtifact = "mockito-4-2"
      val scalaTestPlusMockitoVersion = "3.2.11.0"
      val scalaCheckVersion = "1.15.4"

      Seq(
        "org.scalactic" %% "scalactic"                       % scalacticVersion % Test,
        "org.scalatest" %% "scalatest"                       % scalaTestVersion % Test,
        "org.scalamock" %% "scalamock"                       % scalaMockVersion % Test,
        "org.scalacheck" %% "scalacheck"                     % scalaCheckVersion % Test,
        "org.mockito" % "mockito-core"                       % mockitoVersion % Test,
        "org.scalatestplus" %% scalaTestPlusMockitoArtifact  % scalaTestPlusMockitoVersion % Test,
        "com.typesafe.scala-logging" %% "scala-logging"      % scalaLoggingVersion,
        "com.typesafe" % "config"                            % configVersion,
        "com.typesafe.play" %% "play-json"                   % playVersion,
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
//  .settings(
//    publishSettings: _*
//  )
//
//lazy val publishSettings = Seq(
//  publishTo := {
//    val version = if (isSnapshot.value) "snapshots" else "releases"
//    Some("MDC Maven Repo" at s"https://nexus.wopr.inf.mdc/repository/maven-$version/")
//  },
//  credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
//)
