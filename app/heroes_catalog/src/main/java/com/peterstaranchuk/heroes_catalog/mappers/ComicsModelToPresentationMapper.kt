package com.peterstaranchuk.heroes_catalog.mappers

import com.peterstaranchuk.common.Mapper
import com.peterstaranchuk.heroes_catalog.model.ComicsModel
import com.peterstaranchuk.heroes_catalog.presentation.ComicsPresentation

class ComicsModelToPresentationMapper : Mapper<ComicsModel, ComicsPresentation> {
    override fun map(from: ComicsModel): ComicsPresentation {
        return ComicsPresentation(
            id = from.id,
            imageUrl = /*from.images.firstOrNull()?.getUrl() ?: */from.thumbnail.getUrl(),
            title = from.title,
            description = from.description
        )
    }
}