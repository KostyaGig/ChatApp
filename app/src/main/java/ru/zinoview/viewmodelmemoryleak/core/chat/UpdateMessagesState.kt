package ru.zinoview.viewmodelmemoryleak.core.chat

import ru.zinoview.viewmodelmemoryleak.core.Update

interface UpdateMessagesState : Update<Pair<Int,Int>>