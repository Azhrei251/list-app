package com.azhapps.listapp.more.model

import com.azhapps.listapp.network.model.Environment

data class DeveloperOptionsState(
    val currentEnvironment: Environment = Environment.currentlySelected,
    val availableEnvironments: List<Environment> = listOf(Environment.Prod, Environment.Test),
)