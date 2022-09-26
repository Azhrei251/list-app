package com.azhapps.listapp.main.model

sealed class MainAction {

    object NavigateToLogin : MainAction()

    object NavigateToLists : MainAction()

    object NavigateToRegister : MainAction()
}