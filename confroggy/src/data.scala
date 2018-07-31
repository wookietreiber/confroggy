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

sealed abstract class Type

object Type {
  case object String extends Type
  case object Int extends Type
  case object UInt extends Type
  case object Double extends Type
  case object File extends Type
}

// TODO maybe merge brief and description like a git commit message:
// brief is the subject line
// and description everything together
// this way you would only need to write the subject and if simple enough, can
// leave the description out of it

final case class Argument (
  name: String,
  brief: String,
  description: String,
  `type`: Type
)

final case class Opt (
  name: String,
  short: String,
  brief: String,
  description: String,

  // TODO maybe have a short argument without description
  argument: Option[Argument] = None
)

final case class Apps (
  name: String,
  version: String,

  brief: String,
  description: String,

  arguments: List[Argument] = Nil,
  options: List[Opt] = Nil
)
