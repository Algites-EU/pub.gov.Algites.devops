java {
    sourceSets {
        val main by getting {
            java.setSrcDirs(listOf("src/product/java", "src/product/kotlin"))
            resources.setSrcDirs(listOf("src/product/resources","src/product/yaml"))
        }
        val test by getting {
            java.setSrcDirs(listOf("src/develop/java", "src/develop/kotlin"))
            resources.setSrcDirs(listOf("src/develop/resources","src/develop/yaml"))
        }
    }
}

group = "eu.algites.tool.build"

base {
    archivesName.set("pub.gov.Algites_build.model.common")
}



plugins {
    `maven-publish`
    `kotlin-dsl`
    `java-gradle-plugin`
}

publishing {

    publications {

        create<MavenPublication>("policyParentPom") {

            groupId = project.group.toString()
            artifactId = "${base.archivesName.get()}"
            version = project.version.toString()

            pom {
                packaging = "pom"
                name.set("Algites DevOps Build Model")
                description.set(
                    "Generated Algites DevOps build model common classes."
                )
            }
        }
    }

}
