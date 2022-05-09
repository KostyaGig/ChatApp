package ru.zinoview.viewmodelmemoryleak.core

interface FailureMapper<T,P> {

    fun mapFailure(message: P) : T

    interface Unit<T> : FailureMapper<T,kotlin.Unit>

    interface String<T> : FailureMapper<T,kotlin.String>
}