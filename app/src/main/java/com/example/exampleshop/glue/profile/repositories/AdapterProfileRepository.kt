package com.example.exampleshop.glue.profile.repositories


import com.example.core.Container
import com.example.data.AccountsDataRepository
import com.example.profile.domain.entities.Profile
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
): ProfileRepository {

    override fun getProfile(): Flow<Container<Profile>> {
        return accountsDataRepository.getAccount().map { container ->
            container.map {
                Profile(
                    username = it.username,
                    email = it.email
                )
            }
        }
    }

    override fun reload() {
        accountsDataRepository.reload()
    }

    override suspend fun editUsername(newUsername: String) {
        accountsDataRepository.setAccountUsername(newUsername)
    }

}