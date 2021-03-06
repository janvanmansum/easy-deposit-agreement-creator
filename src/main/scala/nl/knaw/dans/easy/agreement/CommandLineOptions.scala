/**
 * Copyright (C) 2016 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.easy.agreement

import java.io.File

import org.rogach.scallop.{ ScallopConf, ScallopOption, Subcommand }

class CommandLineOptions(args: Array[String], configuration: Configuration) extends ScallopConf(args) {
  appendDefaultToDescription = true
  editBuilder(_.setHelpWidth(110))
  printedName = "easy-deposit-agreement-creator"
  private val SUBCOMMAND_SEPARATOR = "---\n"
  val description: String = s"""Create a agreement for the given datasetID. The agreement will be saved at the indicated location."""
  val synopsis: String = s""" $printedName [{--sample|-s}] <datasetID> <agreement-file>"""
  version(s"$printedName v${ configuration.version }")
  banner(
    s"""
       |  $description
       |
       |Usage:
       |
       |$synopsis
       |
       |Options:
       |""".stripMargin)

  val datasetID: ScallopOption[DatasetID] = trailArg(name = "dataset-id",
    descr = "The ID of the dataset of which a agreement has to be created", required = false)

  val outputFile: ScallopOption[File] = trailArg(name = "agreement-file",
    descr = "The file location where the agreement needs to be stored", required = false)

  val isSample: ScallopOption[Boolean] = opt(name = "sample", short = 's', default = Option(false),
    descr = "Indicates whether or not a sample agreement needs to be created")

  val runService = new Subcommand("run-service") {
    descr("Starts EASY Deposit Agreement Creator as a daemon that services HTTP requests")
    footer(SUBCOMMAND_SEPARATOR)
  }
  addSubcommand(runService)

  footer("")
}
