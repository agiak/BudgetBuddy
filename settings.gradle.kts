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
include(":features:quicklogin")
include(":features:quicklogin:public")
include(":features:quicklogin:impl")
include(":features:register")
include(":features:register:public")
include(":features:register:impl")
include(":features:guide")
include(":features:guide:public")
include(":features:guide:impl")
include(":features:rules")
include(":features:rules:allRules")
include(":features:rules:allRules:public")
include(":features:rules:allRules:impl")
include(":features:rules:salary")
include(":features:rules:salary:public")
include(":features:rules:salary:impl")
include(":features:account:public")
include(":features:account:impl")
include(":features:accountAdd")
include(":features:accountAdd:public")
include(":features:accountAdd:impl")
