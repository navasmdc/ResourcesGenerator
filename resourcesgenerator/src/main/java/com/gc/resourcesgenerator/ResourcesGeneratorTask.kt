package com.gc.resourcesgenerator

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ResourcesGeneratorTask : DefaultTask() {

    lateinit var configuration: ResourcesGeneratorConfiguration

    @TaskAction
    fun generateResources() {
        val packageDir = configuration.packageName.replace(".","/")
        val rFile = File("${project.buildDir.absolutePath}/generated/not_namespaced_r_class_sources/debug/processDebugResources/r/$packageDir/R.java")
        val outputFile = File("${project.buildDir.absolutePath}/generated/source/rs/debug/$packageDir/Strings.kt")
        ResourcesGenerator(rFile, outputFile, configuration.packageName).generate()
    }
}
