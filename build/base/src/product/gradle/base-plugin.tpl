package eu.algites.tool.build

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Algites build base Gradle policy (public baseline).
 *
 * Naming: AIc = Algites class
 */
object AIoAlgitesBuildBasePublicPolicy {

    const val VERSION =
        "@algites.build.base.pub.policy.version@"
}

/**
 * Algites build base Gradle convention plugin (public baseline).
 *
 * Naming: AIc = Algites class
 */
class AIcAlgitesBuildBasePublicPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        // Base plugins for all projects
        pluginManager.apply("base")
        pluginManager.apply("maven-publish")

        // Common layout conventions
        layout.buildDirectory.set(file("run/bld"))

        // Common repositories (PUBLIC baseline)
        repositories.apply {
            // Releases
			mavenCentral()

            // Snapshots (Cloudsmith public)
			maven {
                name = "algites-public-snapshots"
				url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/maven/")
			}
        }
    }
}
