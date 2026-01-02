import org.gradle.api.publish.PublishingExtension

plugins {
    // root je jen orchestrátor; publication bude až v subprojektech
    `maven-publish`
}

// defaults (subprojects inherit unless they override)
group = "eu.algites.tool.build"
version = "1.0.0-SNAPSHOT"

// Put Gradle build outputs under run/
layout.buildDirectory.set(file("run/bld"))
subprojects {
    layout.buildDirectory.set(file("${project.projectDir}/run/bld"))
}

// --- GitHub Packages target repo (dynamic in CI) ---
val ghRepo: String = (
        System.getenv("ALGITES_GITHUB_REPOSITORY")
            ?: System.getenv("GITHUB_REPOSITORY")
            ?: "Algites-EU/pub.tool.Java" // local fallback
        ).trim()

val ghPackagesUrl = uri("https://maven.pkg.github.com/$ghRepo")

fun ghUser(): String? =
    (findProperty("gpr.user") as String?)?.takeIf { it.isNotBlank() }
        ?: System.getenv("GITHUB_ACTOR")?.takeIf { it.isNotBlank() }

fun ghToken(): String? =
    (findProperty("gpr.token") as String?)?.takeIf { it.isNotBlank() }
        ?: System.getenv("GITHUB_TOKEN")?.takeIf { it.isNotBlank() }

// --- Dependency resolution repos (read) ---
allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/maven/")
        }
    }

    // --- Publishing repo (write) for projects that apply maven-publish ---
    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension>("publishing") {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = ghPackagesUrl

                    val u = ghUser()
                    val t = ghToken()
                    if (u != null && t != null) {
                        credentials {
                            username = u
                            password = t
                        }
                    }
                }
            }
        }
    }
}

// Simple sanity task
tasks.register("ciHelp") {
    doLast { println("Algites root Gradle build detected.") }
}
