import ammonite.ops._
import coursier.maven.MavenRepository
import mill._
import mill.scalalib._

object confroggy extends ScalaModule {
  def scalaVersion = "2.12.6"

  def version = T.input {
    import ammonite.ops.ImplicitWd._

    val result = %%('git, "describe", "--always", "--dirty", "--long")
    val version = result.out.string.trim()

    T.ctx().log.info(s"version from git is: $version")

    version
  }

  def generatedSources = T {
    T.ctx().log.info("generating build info ...")

    val dir = T.ctx().dest
    val file = dir / "BuildInfo.scala"

    ammonite.ops.write(
      file,
      s"""|package confroggy
          |
          |object BuildInfo {
          |  val name = "confroggy"
          |  val version = "${version()}"
          |}
          |""".stripMargin
    )

    Seq(PathRef(dir))
  }

  object test extends Tests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.6.4")
    def testFrameworks = Seq("utest.runner.Framework")
  }

  def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/releases")
  )

  def compileIvyDeps = Agg(ivy"org.wartremover::wartremover:2.3.2")
  def scalacPluginIvyDeps = Agg(ivy"org.wartremover::wartremover:2.3.2")

  def scalacOptions =
    "-deprecation" ::
    "-encoding" :: "utf-8" ::
    "-explaintypes" ::
    "-feature" ::
    "-unchecked" ::
    "-P:wartremover:traverser:org.wartremover.warts.ArrayEquals" ::
    "-P:wartremover:traverser:org.wartremover.warts.FinalCaseClass" ::
    "-P:wartremover:traverser:org.wartremover.warts.OptionPartial" ::
    "-P:wartremover:traverser:org.wartremover.warts.TraversableOps" ::
    "-P:wartremover:traverser:org.wartremover.warts.TryPartial" ::
    "-Xcheckinit" ::
    "-Xfatal-warnings" ::
    "-Xfuture" ::
    "-Xlint:_" ::
    "-Yno-adapted-args" ::
    "-Ypartial-unification" ::
    "-Ywarn-adapted-args" ::
    "-Ywarn-dead-code" ::
    "-Ywarn-inaccessible" ::
    "-Ywarn-infer-any" ::
    "-Ywarn-nullary-override" ::
    "-Ywarn-nullary-unit" ::
    "-Ywarn-numeric-widen" ::
    "-Ywarn-unused-import" ::
    "-Ywarn-value-discard" ::
    Nil
}
