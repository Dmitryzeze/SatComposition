package com.example.satcomposition.domain.interactors

import com.example.satcomposition.domain.GameRepository
import com.example.satcomposition.domain.entity.GameSettings
import com.example.satcomposition.domain.entity.Level

class GetGameSettingsInteractor(private val repository: GameRepository) {
    operator fun invoke (gameLevel:Level):GameSettings{

        return repository.getGameSettings(gameLevel)
    }
}