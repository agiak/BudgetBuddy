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
    }
}

rootProject.name = "MyWallet"
include(":app")
include(":common")
include(":features")
include(":features:home")
include(":features:home:public")
include(":features:home:impl")
include(":features:statics")
include(":features:statics:public")
include(":core")
include(":features:statics:impl")
