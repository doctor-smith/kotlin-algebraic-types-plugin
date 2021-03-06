plugins{
    `kotlin-dsl`
}
repositories{
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://plugins.gradle.org/m2/")
}

configure<JavaPluginConvention> {
    sourceSets{
        getByName("main"){
            resources.srcDirs("src/main/resources")
        }
    }
}