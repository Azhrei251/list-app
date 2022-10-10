package com.azhapps.listapp.main.model

sealed class MainAction {

    object NavigateToLogin : MainAction()

    object NavigateToLanding : MainAction()

    object NavigateToRegister : MainAction()
}