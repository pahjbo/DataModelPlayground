import org.gradle.kotlin.dsl.vodml

plugins {
    id("net.ivoa.vo-dml.vodmltools") version "0.3.21"
    application
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    signing

}
group = "org.javastro.ivoa.vo2"
version = "0.1-SNAPSHOT"

vodml {
    vodmlDir.set(file("vo-dml"))
    vodslDir.set(file("models"))
//    vodmlFiles.setFrom(files (vodmlDir.file("Characterization.vo-dml.xml"), vodmlDir.file("IVOA-v1.0.vo-dml.xml"), vodmlDir.file("Coords-v1.0.vo-dml.xml"))) // if you want to operate on single model
    bindingFiles.setFrom(files(vodmlDir.asFileTree.matching(PatternSet().include("*.vodml-binding.xml"))))
    outputDocDir.set(layout.projectDirectory.dir("doc/generated"))
}
/* uncomment below to run the generation of vodml from vodsl automatically */
//tasks.named("vodmlJavaGenerate") {
//    dependsOn("vodslToVodml")
//}


tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

    implementation("org.slf4j:slf4j-api:1.7.32")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.apache.derby:derby:10.14.2.0")
    testImplementation("org.javastro:jaxbjpa-utils:0.1.2")
    testImplementation("org.javastro:jaxbjpa-utils:0.1.2:test")
}


application {
    mainClass.set("Genschema")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11)) // moved to Java 11
    }
    withJavadocJar()
    withSourcesJar()
}



//make the fact that sources are generated explicit (gets rid of warning that it will not work in gradle 8)- see https://melix.github.io/blog/2021/10/gradle-quickie-dependson.html
tasks.named<Jar>("sourcesJar") {
    from(tasks.named("vodmlGenerateJava"))
}
val tjar = tasks.register<Jar>("testJar") {
    from(sourceSets.test.get().output)
    archiveClassifier.set("test")
}
val pjar = tasks.register<Jar>("JarWithoutPersistence") {
    from(sourceSets.main.get().output)
    archiveClassifier.set("quarkus")
    exclude("META-INF/persistence.xml")
}

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INCLUDE } //IMPL bugfix - see https://stackoverflow.com/questions/67265308/gradle-entry-classpath-is-a-duplicate-but-no-duplicate-handling-strategy-has-b
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tjar)
            artifact(pjar)
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("IVOA2 Data models")
                description.set("Unified Data Models in the IVOA")
                url.set("https://wiki.ivoa.net/twiki/bin/view/IVOA/IvoaDataModel")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("pahjbo")
                        name.set("Paul Harrison")
                        email.set("paul.harrison@manchester.ac.uk")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/pahjbo/DataModelPlayground.git")
                    developerConnection.set("scm:git:ssh://github.com/pahjbo/DataModelPlayground.git")
                    url.set("https://github.com/pahjbo/DataModelPlayground")
                }
            }
        }
    }
}
nexusPublishing {
    repositories {
        sonatype()
    }
}

//do not generate extra load on Nexus with new staging repository if signing fails
tasks.withType<io.github.gradlenexus.publishplugin.InitializeNexusStagingRepository>().configureEach{
    shouldRunAfter(tasks.withType<Sign>())
}

signing {
    setRequired { !project.version.toString().endsWith("-SNAPSHOT") && !project.hasProperty("skipSigning") }

    if (!project.hasProperty("skipSigning")) {
        useGpgCmd()
        sign(publishing.publications["mavenJava"])
    }
}
