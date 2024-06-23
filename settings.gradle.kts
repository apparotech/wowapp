pluginManagement {
    repositories {
        google()
        mavenCentral()

        jcenter()
        maven  ( "https://jitpack.io" )
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "wowapp"
include(":app")
