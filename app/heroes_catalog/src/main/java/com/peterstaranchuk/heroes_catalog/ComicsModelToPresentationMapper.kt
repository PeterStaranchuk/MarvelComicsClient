package com.peterstaranchuk.heroes_catalog

import com.peterstaranchuk.common.Mapper

class ComicsModelToPresentationMapper : Mapper<ComicsModel, ComicsPresentation> {
    override fun map(from: ComicsModel): ComicsPresentation {
        return ComicsPresentation(
            id = from.id,
            imageUrl = from.resourceURI
        )
    }
}