package com.github.themartdev.intellijgleam.ide.lsp

import com.intellij.testFramework.LightPlatformTestCase

class GleamSettingsConfigurableMockKTest : LightPlatformTestCase() {

    fun `test if inlay hint settings change, a notification should be sent`() {
        // arrange
        val settings = GleamServiceSettings.getInstance(project)
        settings.showInlayHintsPipelines = true
        settings.showInlayHintsFunctionParameterTypes = true
        settings.showInlayHintsFunctionReturnTypes = true

        var messageCounter = 0
        project.messageBus
            .connect(testRootDisposable)
            .subscribe(
                GleamServiceSettings.INLAY_SETTINGS_CHANGED,
                object : InlaySettingsChangeListener {
                    override fun onInlaySettingChanged() {
                        messageCounter++
                    }
                })

        // act
        settings.showInlayHintsPipelines = false
        settings.showInlayHintsFunctionParameterTypes = false
        settings.showInlayHintsFunctionReturnTypes = false

        // assert
        assertEquals(3, messageCounter)
    }

    fun `test if inlay hint settings stay the same, no notification should be sent`() {
        // arrange
        val settings = GleamServiceSettings.getInstance(project)
        settings.showInlayHintsPipelines = true
        settings.showInlayHintsFunctionParameterTypes = true
        settings.showInlayHintsFunctionReturnTypes = true

        var messageCounter = 0
        project.messageBus
            .connect(testRootDisposable)
            .subscribe(
                GleamServiceSettings.INLAY_SETTINGS_CHANGED,
                object : InlaySettingsChangeListener {
                    override fun onInlaySettingChanged() {
                        messageCounter++
                    }
                })

        // act
        settings.showInlayHintsPipelines = true
        settings.showInlayHintsFunctionParameterTypes = true
        settings.showInlayHintsFunctionReturnTypes = true

        // assert
        assertEquals(0, messageCounter)
    }
}
