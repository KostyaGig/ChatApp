package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.SnackBar

interface ShowError{

    fun showError(snackBar: SnackBar<String>)
}