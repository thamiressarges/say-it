package com.example.sayit

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.example.sayit.datasource.LanguagePreferences
import com.example.sayit.ui.theme.Blue900
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Home()
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var language by remember { mutableStateOf("") }

    var idiomaSelecionado by remember { mutableStateOf("Espanhol") }
    var bandeiraSelecionada by remember { mutableStateOf(R.drawable.espanha) }

    var mostrarTraducao by remember { mutableStateOf(false) }

    var idiomaCode by remember { mutableStateOf("es") }


    LaunchedEffect(Unit) {
        LanguagePreferences.getLanguage(context = context).collect {
            language = it
            updateLocale(context, language)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Say It",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue900
                )
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {

            var expanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F2F9)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.brasil),
                            contentDescription = "Bandeira do Brasil",
                            Modifier.size(50.dp)
                        )

                        Text(
                            text = "Português",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.arrow_left_arrow_right_svgrepo_com),
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )

                    Box {


                        Row(
                            modifier = Modifier.clickable { expanded = true },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = idiomaSelecionado,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Image(
                                painter = painterResource(bandeiraSelecionada),
                                contentDescription = "Bandeira da Espanha",
                                Modifier.size(50.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {



                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.eua),
                                            contentDescription = "Bandeira EUA",
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Inglês")
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        LanguagePreferences.saveLanguage(context = context, languageCode = "en")
                                    }
                                    idiomaSelecionado = "Inglês"
                                    bandeiraSelecionada = R.drawable.eua
                                    mostrarTraducao = false
                                    idiomaCode = "en"
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.espanha),
                                            contentDescription = "Bandeira Espanha",
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Espanhol")
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        LanguagePreferences.saveLanguage(context = context, languageCode = "es")
                                    }
                                    idiomaSelecionado = "Espanhol"
                                    bandeiraSelecionada = R.drawable.espanha
                                    mostrarTraducao = false
                                    idiomaCode = "es"
                                }
                            )

                        }


                    }
                }
            }

            ContentCard(
                idioma = "Português",
                texto = "Meu nome é Thamires Sarges, eu moro no Brasil, e sou desenvolvedora mobile",
                mostrarBotaoTraduzir = true,
                onTraduzirClick = {
                    updateLocale(context, idiomaCode)
                    mostrarTraducao = true
                }
            )

            ContentCard(
                idioma = stringResource(R.string.idioma),
                texto = if (mostrarTraducao) stringResource(R.string.descricao) else "Clique em 'Traduzir'",
                mostrarBotaoTraduzir = false,
                onTraduzirClick = {}
            )
        }
    }

}

@Composable
fun ContentCard(
    idioma: String,
    texto: String,
    mostrarBotaoTraduzir: Boolean,
    onTraduzirClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F2F9)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = idioma,
                style = TextStyle(
                    color = Color(0xFF0047A1),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = texto,
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (mostrarBotaoTraduzir) {
                    Button(
                        onClick = {
                            onTraduzirClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF6600)
                        )
                    ) {
                        Text(
                            text = "Traduzir"
                        )

                    }
                }
            }

        }
    }

}

private fun updateLocale(context: Context, languageCode: String){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = android.os.LocaleList.forLanguageTags(languageCode)
    }else{
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun HomePreview() {
    Home()

}




