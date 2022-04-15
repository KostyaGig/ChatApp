package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

interface ExtraToTypeFragmentMapper :
    ru.zinoview.viewmodelmemoryleak.core.Mapper<String, TypeFragment> {

    class Base(
        private val stringFragment: StringFragment
    ) : ExtraToTypeFragmentMapper {

        override fun map(src: String) = stringFragment.map(src)
    }
}