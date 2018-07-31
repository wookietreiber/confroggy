package confroggy

import utest._

object ParserTests extends TestSuite {
  val a = new Apps {
    def name = "foo"
    def version = "0.1"
    def arguments: List[Argument] = ???
    def description: String = ???
    def help: String = ???
    def options: List[Opt] = ???
  }

  val tests = Tests {
    'help - {
      val h = Gen.help(a)
      val expected =
        """|foo 0.1
           |""".stripMargin

      assert(h == expected)
    }
  }
}
