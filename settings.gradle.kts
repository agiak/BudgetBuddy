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

// Shared modules
include(":common")
include(":core")
include(":features")

// Home feature
include(":features:home")
include(":features:home:public")
include(":features:home:impl")

// Statics feature
include(":features:statics")
include(":features:statics:public")
include(":features:statics:impl")

// Quick login feature
include(":features:quicklogin")
include(":features:quicklogin:public")
include(":features:quicklogin:impl")

// Register feature
include(":features:register")
include(":features:register:public")
include(":features:register:impl")

// Guide feature
include(":features:guide")
include(":features:guide:public")
include(":features:guide:impl")

// Rules feature
include(":features:rules")
include(":features:rules:public")
include(":features:rules:impl")

// Account details and edit feature
include(":features:account:public")
include(":features:account:impl")

// Add account feature
include(":features:accountAdd")
include(":features:accountAdd:public")
include(":features:accountAdd:impl")

// Add transaction feature
include(":features:transactionAdd")
include(":features:transactionAdd:public")
include(":features:transactionAdd:impl")

// Account feature
include(":features:accounts")
include(":features:accounts:public")
include(":features:accounts:impl")

// Transactions feature
include(":features:transactions")
include(":features:transactions:public")
include(":features:transactions:impl")

// Transaction feature
include(":features:transaction")
include(":features:transaction:public")
include(":features:transaction:impl")
