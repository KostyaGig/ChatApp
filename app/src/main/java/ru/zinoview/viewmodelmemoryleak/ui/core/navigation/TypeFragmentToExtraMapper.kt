package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

interface TypeFragmentToExtraMapper :
    ru.zinoview.viewmodelmemoryleak.core.Mapper<TypeFragment, String> {

    class Base(
        private val stringFragment: StringFragment
    ) : TypeFragmentToExtraMapper {

        override fun map(src: TypeFragment) = stringFragment.map(src)
    }
}