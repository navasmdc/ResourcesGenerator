package com.gc.resourcesgenerator

import org.intellij.lang.annotations.Language
import java.io.File
import java.lang.StringBuilder

class ResourcesGenerator(
    private val rFile: File,
    private val outputFile: File,
    private val packageName: String,
    private val filePrinter: (File, String) -> Unit = ::printInFile,
    private val logPrinter: (String) -> Unit = ::printInLog
) {

    fun generate() {

        if (rFile.exists()) {

            val variables = rFile.extractVariables()

            val variablesOutput = StringBuilder()
            variables.forEach { variablesOutput.append(String.format(variableTemplate, it, it)) }

            outputFile.parentFile.mkdirs()
            filePrinter(outputFile, String.format(fileTemplate, packageName, variablesOutput))

        } else logPrinter("R file wasn't generated")
    }

    // region extract variables
    private fun File.extractVariables() =
        readText()
        .removeJumpLines()
        .getStringsBlock()
        .splitVariables()
        .mapToVariables()

    private fun String.getStringsBlock() =
        "public static final class string.*?\\}".toRegex().findAll(this).first().value

    private fun String.removeJumpLines() = remove("\n")

    private fun String.splitVariables() =
            "public static final int.*?;".toRegex().findAll(this)

    private fun Sequence<MatchResult>.mapToVariables() = this.map { variable ->
        variable.value.remove("public static final int ").run {
            substring(0,indexOf("="))
        }
    }

    private fun String.remove(string: String) = replace(string, "")
    // endregion
}

private fun printInFile(file: File, output: String) = file.writeText(output)

private fun printInLog(output: String) = println(output)

@Language("kotlin")
private val variableTemplate = """
    val %s: String
        get() = checkNotNull(context).getString(R.string.%s)
"""

@Language("kotlin")
private val fileTemplate = """
package %s

import android.content.Context

object Strings {

    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
    }

    fun dispose() {
        context = null
    }
%s
}
"""
