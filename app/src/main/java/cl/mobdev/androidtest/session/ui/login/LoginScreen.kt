package cl.mobdev.androidtest.session.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.mobdev.androidtest.session.ui.navigation.AppScreens
import cl.mobdev.androidtest.R
import cl.mobdev.androidtest.session.presentation.login.LoginViewModel

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onClick: () -> Unit,
    navController: NavController
) {
    val loginViewState by loginViewModel.viewState.collectAsState()
    val navigateLoginUser by loginViewModel.navigateToDetails.collectAsState()
    var navigate by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    if (navigateLoginUser && !navigate) {
        navController.navigate(AppScreens.OnBoardingPaymentsReceipts.route)
        navigate = true
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.left),
            contentDescription = stringResource(id = R.string.button_back),
            modifier = Modifier
                .padding(start = 22.dp)
                .padding(top = 30.dp)
                .clickable { navController.popBackStack() }
        )
        Text(
            text = stringResource(id = R.string.title_start_session), fontWeight = FontWeight.W800,
            modifier = Modifier
                .padding(start = 22.dp)
                .padding(top = 30.dp),
            fontSize = 24.dp.value.sp,
        )
        Text(
            text = "email",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 22.dp)
                .padding(top = 50.dp)
        )
        var email by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = email,
            onValueChange = {
                email = it
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
            ),
            placeholder = {
            }
        )
        if (loginViewState?.isValidEmail == false) {
            Text(
                text = "email",
                modifier = Modifier.padding(start = 22.dp)
            )
        }

        Text(
            text = "password",
            Modifier
                .padding(start = 22.dp)
                .padding(top = 10.dp),
            fontWeight = FontWeight.Bold
        )
        var password by remember { mutableStateOf(TextFieldValue("")) }
        val passwordVisible = mutableStateOf<Boolean>(false)
        val visualTransformation = if (passwordVisible.value)
            VisualTransformation.None
        else PasswordVisualTransformation()

        TextField(
            value = password,
            onValueChange = {
                password = it
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
            ),
            placeholder = {
            }
        )

        if (loginViewState?.isValidPassword == false) {
            Text(
                text = "password valid",
                modifier = Modifier.padding
                    (start = 22.dp)
            )
        }

        if (loginViewState.userNotExist) {
            Text(
                text = "usuario no existe",
                modifier = Modifier.padding
                    (start = 22.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(start = 125.dp)
                .background(Color.White)
        ) {
            ThirdButtonMedium(
                text = stringResource(id = R.string.start_session),
                email.text.length > 2 &&
                        password.text.length > 2
            ) {
                loginViewModel.onLoginSelected(
                    email = email.text,
                    password = password.text,
                    navController, trimmedEmail = "")
            }
        }
        Row(modifier = Modifier.padding(start = 80.dp)) {
            TextButton(onClick = { onClick.invoke() }) {
                Text(
                    text = stringResource(id = R.string.forget_password), modifier = Modifier
                        .padding(start = 22.dp)
                        .padding(top = 22.dp),
                    Color.Black, textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
fun ThirdButtonMedium(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (enabled) {
        Color.Black
    } else {
        Color.White
    }
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .size(
                width = (150.dp),
                height = (38.dp)
            ), colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow,
            disabledBackgroundColor = Color.Blue
        ), enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 14.dp.value.sp,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center
        )
    }
}