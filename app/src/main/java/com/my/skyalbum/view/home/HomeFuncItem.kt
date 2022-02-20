package com.my.skyalbum.view.home

class HomeFuncItem(
    val onUserSelect: ((Long) -> Unit) = { _-> },
    val onAlbumSelect: ((Long) -> Unit) = { _-> }
)