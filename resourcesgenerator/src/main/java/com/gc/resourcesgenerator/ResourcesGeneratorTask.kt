package com.gc.resourcesgenerator

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class ResourcesGeneratorTask : DefaultTask() {

    lateinit var configuration: ResourcesGeneratorConfiguration

    @TaskAction
    fun generateResources() {
        print("BUUUUUUUUUUUUU ${configuration.packageName}")
    }


}