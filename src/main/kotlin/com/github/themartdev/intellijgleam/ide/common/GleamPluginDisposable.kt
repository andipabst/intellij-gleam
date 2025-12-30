package com.github.themartdev.intellijgleam.ide.common

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class GleamPluginDisposable : Disposable {
    override fun dispose() {
    }
    
    companion object {
        @JvmStatic
        fun getInstance(project: Project): GleamPluginDisposable = project.service()
    }
}