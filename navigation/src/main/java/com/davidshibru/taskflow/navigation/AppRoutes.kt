package com.davidshibru.taskflow.navigation


interface Routes {
    val feature: String
}

data object InitRoute : Routes {
    override val feature: String = "init"
}

data object SignInRoute : Routes{
    override val feature: String = "sign-in"
}
