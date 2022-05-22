package com.example.cibtaskfxkotlin

import tornadofx.App
import tornadofx.launch

class AppLauncher: App(Main::class) {
    fun main(args: Array<String>) {
        launch<AppLauncher>()
    }
}
