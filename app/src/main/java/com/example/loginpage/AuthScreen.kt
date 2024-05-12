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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun AuthScreen(navController: NavController) {
    val viewModel: AuthViewModel = koinInject()
    var firstName by remember {
        mutableStateOf("")
    }
    var secondName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    var isDialog by remember {
        mutableStateOf(false)
    }
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "SignUp")

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = firstName, onValueChange = {
                    firstName = it
                }, placeholder = {
                    Text(text = "First Name")
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = secondName, onValueChange = {
                    secondName = it
                }, placeholder = {
                    Text(text = "Second Name")
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = email, onValueChange = {
                    email = it
                }, placeholder = {
                    Text(text = "Enter Email")
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = password, onValueChange = {
                    password = it
                }, placeholder = {
                    Text(text = "Enter Password")
                })


                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    scope.launch {
                        viewModel.createUser(
                            AuthUser(
                                email,
                                password,
                                firstName,
                                secondName
                            )
                        ).collect {
                            when (it) {
                                is ResultState.Error -> {
                                    isDialog = false

                                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                ResultState.Loading -> {
                                    isDialog = true
                                }

                                is ResultState.Success -> {
                                    isDialog = false
                                    Toast.makeText(
                                        context,
                                        "Sign Up SuccessFully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            if (it is ResultState.Success) {
                                val intent=Intent(context,MainActivity::class.java)
                                context.startActivity(intent)
                                (context as Activity).finishAffinity()
                            }
                        }
                    }
                }) {
                    Text(text = "Sign Up")
                }


                Spacer(modifier = Modifier.height(20.dp))


                TextButton(onClick = {
                    navController.navigate(Screen.Login.route)
                }) {
                    Text(text = "Login")
                }
            }
        }
    }
}

