package com.gc.resourcesgenerator

import org.gradle.api.Plugin
import org.gradle.api.Project

class ResourcesGeneratorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val projectConfiguration = project.extensions.create("resourcesGenerator", ResourcesGeneratorConfiguration::class.java)
        val task = (project.tasks.create("generateStringsResources", ResourcesGeneratorTask::class.java) as ResourcesGeneratorTask).apply {
            configuration = projectConfiguration
        }
        project.afterEvaluate {
            project.tasks
                .filter { it.name.startsWith("process") && it.name.endsWith("Resources") }
                .forEach { project.getTasksByName(it.name, true).first().finalizedBy(task) }
        }
    }
}
