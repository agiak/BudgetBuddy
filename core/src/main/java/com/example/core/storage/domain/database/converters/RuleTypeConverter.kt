package com.example.core.storage.domain.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.core.data.rule.Rule

@ProvidedTypeConverter
class RuleTypeConverter {
    @TypeConverter
    fun fromRule(rule: Rule): String {
        return rule.name
    }

    @TypeConverter
    fun toRule(rule: String): Rule {
        return Rule.valueOf(rule)
    }
}