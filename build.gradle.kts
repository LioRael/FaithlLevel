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
    install("module-chat")
    install("module-configuration")
    install("module-metrics")
    install("module-database")
    classifier = null
    version = "6.0.4-5"
    description {
        contributors {
            name("Leosouthey")
        }
        links{
            name("homepage").url("https://forum.faithl.com")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
            name("AttributePlus").optional(true)
            name("MythicMobs").optional(true)
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ink.ptms.core:v11701:11701:mapped")
    implementation("ink.ptms.core:v11701:11701:universal")
    compileOnly("com.alibaba:fastjson:1.2.78")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}