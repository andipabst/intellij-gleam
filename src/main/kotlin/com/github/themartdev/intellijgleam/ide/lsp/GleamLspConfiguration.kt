package com.github.themartdev.intellijgleam.ide.lsp

data class GleamLspConfiguration(var inlayHints: InlayHintsConfiguration)

data class InlayHintsConfiguration(
    /** Whether to show type inlay hints of multiline pipelines */
    var pipelines: Boolean,
    /** Whether to show type inlay hints of function parameters */
    var functionParameterTypes: Boolean,
    /** Whether to show type inlay hints of return types of functions */
    var functionReturnTypes: Boolean
)
