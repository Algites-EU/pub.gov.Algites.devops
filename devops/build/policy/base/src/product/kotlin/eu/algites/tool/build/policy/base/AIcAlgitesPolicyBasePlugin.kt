package eu.algites.tool.build.policy.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

class AIcAlgitesPolicyBasePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // ------------------------------------------------------------
        // Build directory redirection
        // ------------------------------------------------------------
        project.layout.buildDirectory.set(
            project.rootProject.layout.projectDirectory.dir(
                "run/bld/gradle/${project.name}"
            )
        )

        // ------------------------------------------------------------
        // Source layout convention (Java / Kotlin / Gradle plugins)
        // ------------------------------------------------------------
        project.plugins.withId("java") {
            configureSourceLayout(project)
        }
        project.plugins.withId("java-gradle-plugin") {
            configureSourceLayout(project)
        }

        val propsFile = project.rootProject.file("algites-repository.properties")
        require(propsFile.exists()) {
            "Algites governance error: missing algites-repository.properties in root project"
        }

        val props = Properties().apply {
            propsFile.inputStream().use { load(it) }
        }

        val lane = props.getProperty("algites.repository.lane")
            ?: error("Missing property: algites.repository.lane")

        val rev = props.getProperty("algites.repository.lane.revision")
            ?: error("Missing property: algites.repository.lane.revision")

        val suffix = props.getProperty("algites.repository.lane.revision.suffix") ?: ""

        val computedVersion = "$lane.$rev$suffix"

        // Force
        if (project.version != Project.DEFAULT_VERSION &&
            project.version.toString() != computedVersion
        ) {
            error(
                """
            Algites governance violation:
            Project version is explicitly set to '$project.version'
            but Algites policy requires version '$computedVersion'
            """.trimIndent()
            )
        }

        project.version = computedVersion
    }

    private fun configureSourceLayout(project: Project) {

        val sourceSets = project.extensions.getByType<SourceSetContainer>()

        // main -> product
        sourceSets.getByName("main").java.setSrcDirs(
            listOf("src/product/java", "src/product/javagen", "src/product/javaextgen", "src/product/kotlin")
        )
        sourceSets.getByName("main").resources.setSrcDirs(
            listOf("src/product/resources", "src/product/config", "src/product/configgen", "src/product/configextgen","src/product/yaml")
        )

        // test -> develop
        sourceSets.getByName("test").java.setSrcDirs(
            listOf("src/develop/java","src/develop/javagen", "src/develop/javaextgen", "src/develop/kotlin")
        )
        sourceSets.getByName("test").resources.setSrcDirs(
            listOf("src/develop/resources", "src/develop/config", "src/develop/configgen", "src/develop/configextgen","src/develop/yaml")
        )
    }
}
