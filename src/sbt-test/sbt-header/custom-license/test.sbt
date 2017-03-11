import de.heikoseeberger.sbtheader.CommentStyle.CStyleBlockComment

headerLicense := HeaderLicense.Custom(
  """|This is a custom License.
     |
     |It has an empty line and a second line with Text.
     |""".stripMargin
)
headerMappings := Map("scala" -> CStyleBlockComment)

val checkFileContents = taskKey[Unit]("Verify file contents match expected contents")

checkFileContents := {
  checkFile("HasHeader.scala")
  checkFile("HasNoHeader.scala")

  def checkFile(name: String) = {
    val actualPath = (scalaSource.in(Compile).value / name).toString
    val expectedPath = (resourceDirectory.in(Compile).value / s"${name}_expected").toString

    val actual = scala.io.Source.fromFile(actualPath).mkString
    val expected = scala.io.Source.fromFile(expectedPath).mkString

    if (actual != expected) sys.error(
      s"""|Actual file contents do not match expected file contents!
          |  actual: $actualPath
          |$actual
          |
          |  expected: $expectedPath
          |$expected
          |""".stripMargin)
  }
}

