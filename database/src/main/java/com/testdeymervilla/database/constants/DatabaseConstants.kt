package com.testdeymervilla.database.constants

import com.testdeymervilla.database.BuildConfig

object DatabaseConstants {

    const val DATABASE_VERSION = 1
    const val KEY_NAME_DATABASE = BuildConfig.DATABASE_NAME

    object Tables {
        const val USER_TABLE = "user_table"
        const val SCHEMA_TABLE = "schema_table"
        const val LOCALITY_TABLE = "locality_table"
    }

    object Columns {
        const val ID = "id"
        const val USERNAME = "username"
        const val IDENTIFICATION = "identification"
        const val FULL_NAME = "full_name"
        const val TABLE_NAME = "table"
        const val CONTENT = "content"
        const val PRIMARY_KEY = "pk"
        const val BATCH_SIZE = "batch_size"
        const val FILTER = "filter"
        const val FIELDS_COUNT = "fields_count"
        const val UPDATE_DATE = "update_date"
        const val CITY_ABBREVIATION = "city_abbreviation"
        const val NAME = "name"
        const val ZIP_CODE = "zip_code"
        const val PICKUP_VALUE = "pickup_value"
    }
}