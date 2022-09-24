package com.azhapps.listapp.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.azhapps.listapp.R
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.navigation.Main
import dev.enro.annotations.NavigationDestination
import dev.enro.core.navigationHandle

@NavigationDestination(Main::class)
class MainActivity : BaseActivity<Main>() {

    override val navigationHandle by navigationHandle<Main>()
    override val shouldShowBackArrow = false
    override fun getToolbarTitle() = getString(R.string.app_name)//TODO

    @Composable
    override fun Content() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Greeting("Android")
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ListAppTheme {
        Greeting("Android")
    }
}