package cl.mobdev.androidtest.session.login.ui.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cl.mobdev.androidtest.session.login.presentation.login.events.LoginViewState

@Composable
fun EmailInputComponent(email: MutableState<String>, loginViewState: LoginViewState) {
    Text(
        text = email.value,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 22.dp)
            .padding(top = 50.dp)
    )
    TextField(
        value = email.value,
        onValueChange = { textFieldEmail ->
            email.value = textFieldEmail
        },
        modifier = Modifier
            .height((50.dp))
            .padding(start = 22.dp)
            .padding(end = 22.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.Black,
            backgroundColor = Color.White
        )
    )
    if (loginViewState?.isValidEmail == false) {
        Text(
            text = email.value,
            modifier = Modifier.padding(start = 22.dp)
        )
    }
}

@Composable
fun PasswordInputComponent(password: MutableState<String>) {
    Text(
        text = password.value,
        Modifier
            .padding(start = 22.dp)
            .padding(top = 10.dp),
        fontWeight = FontWeight.Bold
    )
    val passwordVisible = remember{mutableStateOf<Boolean>(false)}
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    TextField(
        value = password.value,
        onValueChange = {passwordState ->
            password.value = passwordState
        },
        modifier = Modifier
            .padding(start = 22.dp)
            .padding(end = 22.dp)
            .height((45.dp))
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.White
        )
    )
}