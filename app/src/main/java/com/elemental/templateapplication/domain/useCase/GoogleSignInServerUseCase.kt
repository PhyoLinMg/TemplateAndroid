package com.elemental.templateapplication.domain.useCase

import com.elemental.templateapplication.data.model.GoogleToken
import com.elemental.templateapplication.remote.network.services.ApiService

object GoogleSignInServerUseCase {
    fun logInToServer(googleToken: GoogleToken,api:ApiService){
        //login to server
    }
}