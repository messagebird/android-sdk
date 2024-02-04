pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // START FOR LOCAL DEVELOPMENT
        mavenLocal()
        // END FOR LOCAL DEVELOPMENT
    }
}

rootProject.name = "PushNotifications"
include(":app")
 