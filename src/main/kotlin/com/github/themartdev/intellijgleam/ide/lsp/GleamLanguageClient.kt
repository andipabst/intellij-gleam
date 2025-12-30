package com.github.themartdev.intellijgleam.ide.lsp

import com.github.themartdev.intellijgleam.ide.common.GleamPluginDisposable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl


class GleamLanguageClient(project: Project) : LanguageClientImpl(project), InlaySettingsChangeListener{

    private fun getGleamSettings(): GleamServiceSettings = GleamServiceSettings.getInstance(project)

    init {
        project.messageBus
            .connect(GleamPluginDisposable.getInstance(project))
            .subscribe(GleamServiceSettings.INLAY_SETTINGS_CHANGED, this)
    }

    override fun createSettings(): Any {
        val settings = getGleamSettings()

        return JsonObject().apply {
            add(
                "gleam", Gson().toJsonTree(
                    GleamLspConfiguration(
                        inlayHints = InlayHintsConfiguration(
                            pipelines = settings.showInlayHintsPipelines,
                            functionParameterTypes = settings.showInlayHintsFunctionParameterTypes,
                            functionReturnTypes = settings.showInlayHintsFunctionReturnTypes
                        )
                    )
                )
            )
        }
    }

    override fun onInlaySettingChanged() {
        triggerChangeConfiguration()
    }
}