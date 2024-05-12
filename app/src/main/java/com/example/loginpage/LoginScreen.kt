package com.example.loginpage

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = koinInject()

    var email1 by remember {
        mutableStateOf("")
    }
    var password1 by remember {
        mutableStateOf("")
    }

    var scope = rememberCoroutineScope()

    var isDialog by remember {
        mutableStateOf(false)
    }

    var context = LocalContext.current

    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login")

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = email1, onValueChange = {
                    email1=it
                }, placeholder = {
                    Text(text = "Enter Email")
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = password1, onValueChange = {
                    password1=it
                }, placeholder = {
                    Text(text = "Enter Password")
                })

                Spacer(modifier = Modifier.height(17.dp))

                Button(onClick = {
                    scope.launch {
                        viewModel.loginUser(
                            AuthUser(
                                email1,
                                password1,
                                "",
                                ""
                            )
                        ).collect{
                            when(it){
                                is ResultState.Error -> {
                                    isDialog = false
                                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()

                                }
                                ResultState.Loading -> {
                                    isDialog = true

                                }
                                is ResultState.Success -> {
                                    isDialog = false
                                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                                }
                            }
                            if (it is ResultState.Success){
                                val intent= Intent(context,MainActivity::class.java)
                                context.startActivity(intent)
                                (context as Activity).finishAffinity()
                            }
                        }
                    }
                }) {
                    Text(text = "Login")
                }
            }
        }
    }
}