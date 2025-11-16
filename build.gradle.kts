plugins {
    `java-library`
    alias(libs.plugins.teavm) // order matters?
    alias(libs.plugins.blossom)
}

val thisVersion = "0.1.2"

group = "run.slicer"
version = "$thisVersion-${libs.versions.procyon.get()}"
description = "A JavaScript port of the Procyon decompiler."

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://teavm.org/maven/repository")
}

dependencies {
    api(libs.procyon.compilertools)
    compileOnly(libs.teavm.core)
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(21)
}

/*teavm.js {
    mainClass = "run.slicer.procyon.Main"
    moduleType = org.teavm.gradle.api.JSModuleType.ES2015
    // obfuscated = false
    // optimization = org.teavm.gradle.api.OptimizationLevel.NONE
}*/

teavm.wasmGC {
    mainClass = "run.slicer.procyon.Main"
    modularRuntime = true
    /*obfuscated = false
    optimization = org.teavm.gradle.api.OptimizationLevel.NONE
    disassembly = true*/
}

/*tasks.disasmWasmGC {
    html = false
}*/

tasks {
    register<Copy>("copyDist") {
        group = "build"

        from(
            "README.md", "LICENSE", "LICENSE-PROCYON", "procyon.js", "procyon.d.ts",
            generateWasmGC, copyWasmGCRuntime
        )
        into("dist")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        doLast {
            file("dist/package.json").writeText(
                """
                    {
                      "name": "@run-slicer/procyon",
                      "version": "${project.version}",
                      "description": "A JavaScript port of the Procyon decompiler (https://github.com/mstrobel/procyon).",
                      "main": "procyon.js",
                      "types": "procyon.d.ts",
                      "keywords": [
                        "decompiler",
                        "java",
                        "decompilation",
                        "procyon"
                      ],
                      "author": "run-slicer",
                      "license": "Apache License 2.0, MIT"
                    }
                """.trimIndent()
            )
        }
    }

    build {
        dependsOn("copyDist")
    }

    clean {
        delete("dist")
    }
}

val kotlin.reflect.KClass<*>.bytes: ByteArray
    get() = (java.classLoader ?: ClassLoader.getSystemClassLoader())
        .getResourceAsStream(java.name.replace('.', '/') + ".class")!!
        .use(`java.io`.InputStream::readAllBytes)

val ByteArray.base64String: String
    get() = `java.util`.Base64.getEncoder().encodeToString(this)

sourceSets.teavm {
    blossom {
        javaSources {
            property("java_lang_Object", Object::class.bytes.base64String)
            property("java_lang_Class", file("classes/Class.class").readBytes().base64String)
        }
    }
}
