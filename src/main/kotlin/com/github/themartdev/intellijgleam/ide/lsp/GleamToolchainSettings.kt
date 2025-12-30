package com.github.themartdev.intellijgleam.ide.lsp

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic

@Service(Service.Level.PROJECT)
@State(name = "GleamToolchainSettings", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class GleamServiceSettings(val project: Project) :
    SimplePersistentStateComponent<GleamToolchainSettings>(GleamToolchainSettings()) {
    var lspMode
        get() = state.lspMode
        set(value) {
            state.lspMode = value
        }

    var gleamPath
        get() = state.gleamPath ?: ""
        set(value) {
            state.gleamPath = value
        }

    var erlangPath
        get() = state.erlangPath ?: ""
        set(value) {
            state.erlangPath = value
        }

    var showInlayHintsPipelines
        get() = state.showInlayHintsPipelines
        set(value) {
            if (value != state.showInlayHintsPipelines) {
                state.showInlayHintsPipelines = value
                listener.onInlaySettingChanged()
            }
        }

    var showInlayHintsFunctionParameterTypes
        get() = state.showInlayHintsFunctionParameterTypes
        set(value) {
            if (value != state.showInlayHintsFunctionParameterTypes) {
                state.showInlayHintsFunctionParameterTypes = value
                listener.onInlaySettingChanged()
            }
        }

    var showInlayHintsFunctionReturnTypes
        get() = state.showInlayHintsFunctionReturnTypes
        set(value) {
            if (value != state.showInlayHintsFunctionReturnTypes) {
                state.showInlayHintsFunctionReturnTypes = value
                listener.onInlaySettingChanged()
            }
        }

    companion object {
        fun getInstance(project: Project): GleamServiceSettings = project.service()

        @Topic.ProjectLevel
        @JvmField
        val INLAY_SETTINGS_CHANGED = Topic(InlaySettingsChangeListener::class.java)
    }

    private val listener
        get() = project.messageBus.syncPublisher(INLAY_SETTINGS_CHANGED)
}

class GleamToolchainSettings : BaseState() {
    var lspMode by enum(GleamLspMode.ENABLED)
    var gleamPath by string("")
    var erlangPath by string("")
    var showInlayHintsPipelines by property(true)
    var showInlayHintsFunctionParameterTypes by property(true)
    var showInlayHintsFunctionReturnTypes by property(true)
}

enum class GleamLspMode {
    ENABLED,
    DISABLED
}

interface InlaySettingsChangeListener {
    fun onInlaySettingChanged()
}
