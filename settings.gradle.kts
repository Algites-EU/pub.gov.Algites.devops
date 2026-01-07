pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "algites-public-releases"
            url = uri("https://repo.maven.apache.org/maven2")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            name = "algites-public-snapshots"
            url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/")
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            name = "algites-public-releases"
            url = uri("https://repo.maven.apache.org/maven2")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            name = "algites-public-snapshots"
            url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/")
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

rootProject.name = "pub.gov.Algites"
include(":devops:build:model:common")
include(":devops:build:policy:base")
