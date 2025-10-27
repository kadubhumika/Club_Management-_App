package com.example.club_mangement_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import com.example.club_mangement_app.authentication.utils.SharedPrefManager


@Composable
fun On_BoardingScreen(navController: NavController){
    Box(
        contentAlignment=Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    )
    {
        Column(
            horizontalAlignment= Alignment.CenterHorizontally){
        Image(
            painter=painterResource(id=R.drawable.swag_logo),
            contentDescription="Swag logo",
            modifier=Modifier.size(250.dp)
        )
            Spacer(modifier=Modifier.height(20.dp))

    Text(
        text ="Welcome To ClubApp :)",
        color=Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize=30.sp
    )
            Spacer(modifier=Modifier.height(10.dp))

            Text(
                text="Streamline your club management and connect with members effortlessly",
                color=Color.Black,
                fontSize=12.sp,
                textAlign=TextAlign.Center
            )

            Spacer(modifier=Modifier.height(40.dp))

 Button(
     onClick = {
         val sharedPrefManager = SharedPrefManager(navController.context)
         sharedPrefManager.setOnboardingSeen()
         navController.navigate("login") {
             popUpTo("onboarding") { inclusive = true }
         }
     },
     modifier = Modifier
         .width(150.dp)
         .height(56.dp),
     colors = ButtonDefaults.buttonColors(
         containerColor = Color.Black
     ),
     shape = RoundedCornerShape(12.dp)
 )
 {
     Text(
         text = "Get started",
         color = Color.White,
         fontWeight = FontWeight.Bold,
         fontSize = 18.sp
     )
 }

            Spacer(modifier=Modifier.height(15.dp))

            Text(
                text = "Already have an account? Sign in ",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
       }
    }
}

@Preview(showBackground=true, showSystemUi=true)
@Composable
fun OnBoardingPreviewScreen(){
    val navController=rememberNavController()
    On_BoardingScreen(navController=navController)
}