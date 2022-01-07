plugins {
    java
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.0"
}

taboolib {
    install("common")
    install("common-5")
    install("platform-bukkit")
    install("module-lang")
    install("module-chat")
    install("module-kether")
    install("module-configuration")
    install("module-metrics")
    install("module-database")
    install("expansion-javascript")
    classifier = null
    version = "6.0.7-16"
    description {
        contributors {
            name("Leosouthey")
        }
        links{
            name("homepage").url("https://faithl.com")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
            name("AttributePlus").optional(true)
            name("MythicMobs").optional(true)
            name("Zaphkiel").optional(true)
        }
    }
}

repositories {
    maven { url = uri("https://repo.tabooproject.org/repository/releases/") }
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v11800:11800:api")
    compileOnly("ink.ptms.core:v11800:11800:mapped")
    compileOnly("ink.ptms.core:v11800:11800:universal")
    compileOnly("com.alibaba:fastjson:1.2.79")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}