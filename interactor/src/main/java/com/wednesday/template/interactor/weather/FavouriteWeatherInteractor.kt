package com.wednesday.template.interactor.weather

import com.wednesday.template.presentation.base.UIResult
import com.wednesday.template.presentation.weather.UICity
import kotlinx.coroutines.flow.Flow

interface FavouriteWeatherInteractor {

    suspend fun setCityFavourite(uiCity: UICity): UIResult<Unit>

    suspend fun removeCityFavourite(uiCity: UICity): UIResult<Unit>

    fun getFavouriteCitiesFlow(): Flow<List<UICity>>
}