/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                           *
 *  Copyright  (C)  2018  Christian Krause                                   *
 *                                                                           *
 *  Christian Krause  <christian.krause@mailbox.org>                         *
 *                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                           *
 *  This file is part of confroggy.                                          *
 *                                                                           *
 *  Confroggy is free software: you can redistribute it and/or modify        *
 *  it under the terms of the GNU General Public License as published by     *
 *  the Free Software Foundation, either version 3 of the license, or any    *
 *  later version.                                                           *
 *                                                                           *
 *  Confroggy is distributed in the hope that it will be useful, but         *
 *  WITHOUT ANY WARRANTY; without even the implied warranty of               *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU         *
 *  General Public License for more details.                                 *
 *                                                                           *
 *  You should have received a copy of the GNU General Public License along  *
 *  with confroggy. If not, see <http://www.gnu.org/licenses/>.              *
 *                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package confroggy

object Gen {
  import java.time.LocalDate
  import scala.collection.mutable.StringBuilder

  def help(app: Apps): String = {
    val sb = new StringBuilder

    sb ++= s"${app.name} ${app.version}\n"

    sb.result()
  }

  def man(app: Apps): String = {
    val sb = new StringBuilder

    { // header
      val title = app.name.toUpperCase
      val section = 1
      val date = LocalDate.now
      val source = s"version ${app.version}"
      val manual = s"${app.name} manual"

      sb ++= s"""|.TH $title $section $date "$source" "$manual"
                 |""".stripMargin
    }

    { // name section
      sb ++= s"""|.SH NAME
                 |${app.name} \\- ${app.brief}
                 |""".stripMargin
    }

    { // synopsis section
      val args = app.arguments.map(_.name).mkString(" ")

      val opts = if (app.options.nonEmpty) {
        "[options] [--]"
      } else {
        ""
      }

      sb ++= s"""|.SH SYNOPSIS
                 |.B ${app.name}
                 |${opts} ${args}
                 |""".stripMargin
    }

    { // description section
      sb ++= s"""|.SH DESCRIPTION
                 |${app.description}
                 |""".stripMargin
    }

    if (app.arguments.nonEmpty) { // arguments section
      val args = app.arguments map { a =>
        s"""|.TP
            |\\fB${a.name}\\fR
            |${a.description}
            |""".stripMargin
      }

      sb ++= s"""|.SH ARGUMENTS
                 |${args.mkString("\n")}
                 |""".stripMargin
    }

    if (app.options.nonEmpty) { // options section
      val opts = app.options map { o =>
        val argname =
          o.argument.fold("")(_.name)

        s"""|.TP
            |\\fB\\-${o.short}\\fR, \\fB\\-\\-${o.name}\\fR $argname
            |${o.description}
            |""".stripMargin
      }

      sb ++= s"""|.SH OPTIONS
                 |${opts.mkString("\n")}
                 |""".stripMargin
    }

    { // other options section
      // TODO put deprecated, hidden and ignored options here
      sb ++= s"""|.SH OTHER OPTIONS
                 |.TP
                 |\\fB\\-?\\fR, \\fB\\-\\-help\\fR
                 |Shows help text and exit.
                 |.TP
                 |\\fB\\-\\-version\\fR
                 |Shows version and exit.
                 |.TP
                 |\\fB\\-\\-\\fR
                 |Separates \\fBOPTIONS\\fR from \\fBARGUMENTS\\fR. This is
                 |useful when arguments start with dashes, e.g. file names.
                 |""".stripMargin
    }

    sb.result()
  }
}

object confroggy extends App {
  // println(s"this is ${BuildInfo.name} ${BuildInfo.version}")

  val a = Apps (
    name = "app",
    version = "0.1",

    brief = "a pretty standard app",
    description =
      """|A pretty standard app converting some input into some output.
         |
         |This is another paragraph.
         |""".stripMargin,

    arguments =
      Argument (
        name = "input",
        brief = "brief input",
        description = "description input",
        `type` = Type.File
      ) ::
      Argument (
        name = "output",
        brief = "brief output",
        description = "description output",
        `type` = Type.File
      ) ::
      Nil,

    options =
      Opt (
        name = "threads",
        short = "t",

        brief = "number CPU cores to utiliize",
        description = "blah longer text",

        argument = Some(
          Argument (
            name = "n",
            brief = "brief n",
            description = "description n",
            `type` = Type.UInt
          )
        )
      ) ::
      Nil
  )

  println(Gen.man(a))

  // println("done")
}
