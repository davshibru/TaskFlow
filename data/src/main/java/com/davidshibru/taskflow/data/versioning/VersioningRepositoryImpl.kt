package com.davidshibru.taskflow.data.versioning

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.data.R
import com.davidshibru.taskflow.data.VersioningRepository
import com.davidshibru.taskflow.data.versioning.entities.KeyFeatureDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class VersioningRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private var dataStore: DataStore<Preferences>
) : VersioningRepository {
    override suspend fun getKeyFeatures(): List<KeyFeatureDataEntity> = Id.idGenerator {
        listOf(
            KeyFeatureDataEntity(
                id = generateId(),
                title = context.getString(R.string.feature_1_title),
                description = context.getString(R.string.feature_description_1),
                imageSource = ImageSource.Remote("https://minecraft-inside.ru/uploads/nick/3d/ky_lema.png")
            ),
            KeyFeatureDataEntity(
                id = generateId(),
                title = context.getString(R.string.feature_2_title),
                description = context.getString(R.string.feature_description_2),
                imageSource = ImageSource.Remote("https://upload.wikimedia.org/wikipedia/en/9/90/Silk%2C_Vol._3%2C_no._1_textless_cover_%28variant%29.png")
            ),
            KeyFeatureDataEntity(
                id = generateId(),
                title = context.getString(R.string.feature_3_title),
                description = context.getString(R.string.feature_description_3),
                imageSource = ImageSource.Resource(R.drawable.feature3)
            ),
        )
    }

    override suspend fun getDisplayPeriod(): Period {
        return Period.ofDays(21)
    }

    override suspend fun saveLastDisplayTime(
        keyFeatureId: Id,
        time: ZonedDateTime
    ) {
        dataStore.edit { preferences ->
            preferences[key(keyFeatureId)] = DateTimeFormatter.ISO_DATE_TIME.format(time)
        }
    }

    override suspend fun getLastDisplayTime(keyFeatureId: Id): ZonedDateTime {
        return dataStore.data
            .map { preferences ->
                preferences[key(keyFeatureId)]
                    ?.let {
                        ZonedDateTime.parse(it)
                    }
                    ?: ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC)
            }.first()
    }

    private fun key(id: Id): Preferences.Key<String> {
        return stringPreferencesKey("key-feature-display-time-$id")
    }
}