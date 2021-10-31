plugins {
    java
    id("io.izzel.taboolib") version "1.31"
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
}

taboolib {
    install("common")
    install("common-5")
    install("platform-bukkit")
    install("module-lang")
    install("module-ui")
    install("module-chat")
    install("module-configuration")
    install("module-metrics")
    install("module-database")
    install("expansion-player-database")
    classifier = null
    version = "6.0.3-23"
    description {
        contributors {
            name("Leosouthey")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
        }
    }

}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ink.ptms.core:v11701:11701:mapped")
    implementation("ink.ptms.core:v11701:11701:universal")
    compileOnly("com.alibaba:fastjson:1.2.76")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
//    destinationDir = file("F:/Server/spigot 1.17/plugins")
//    destinationDir = file("E:/FaithL/Server/Test1.12.2/plugins")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}