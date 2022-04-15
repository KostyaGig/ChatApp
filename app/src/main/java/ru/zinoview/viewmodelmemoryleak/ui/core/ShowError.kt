package ru.zinoview.viewmodelmemoryleak.ui.core

import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar

interface ShowError{

    fun showError(snackBar: SnackBar<String>)
}