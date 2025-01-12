package com.example.myauth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.authorisation.MainViewModelProvider
import com.example.myauth.db.MainViewModel
import com.example.myauth.db.MyDatabase
import com.example.myauth.db.User
import com.example.myauth.db.UserRepository
import com.example.myauth.databinding.ActivityMainBinding
import com.example.myauth.retrofit.AuthRepository
import com.example.myauth.retrofit.AuthViewModel
import com.example.myauth.retrofit.AuthViewModelFactory
import com.example.myauth.retrofit.RegisterRequest
import com.example.myauth.retrofit.SignUpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityMainBinding
    var accessToken:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
/*

//        <-----Retrofit----->
        val authRepository = AuthRepository()
        val factory = AuthViewModelFactory(authRepository)
        val authViewModel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)

        dataBinding.btnSignUp.setOnClickListener {
            val registerRequest = RegisterRequest("john@mail.com","changeme")
            authViewModel.signup(registerRequest)

        }

        // observing signup response
        authViewModel.signupResponse_.observe(this){ signUpResponse ->
            signUpResponse?.let {
                accessToken = signUpResponse.access_token
                Log.d("Result","$accessToken")
                Toast.makeText(this,"Registered",Toast.LENGTH_LONG).show()
            }
        }

        // observing error
        authViewModel.error_.observe(this){ error ->
            error?.let {
                Toast.makeText(this,"Error: $error",Toast.LENGTH_LONG).show()
                Log.d("Result","Error: $error")
            }

        }

        // Login
        dataBinding.btnLogin.setOnClickListener {
            if(accessToken!=null)
            {
                authViewModel.login("Bearer ${accessToken}")
            } else
            {
                Toast.makeText(this,"Please Signup first",Toast.LENGTH_LONG).show()
                Log.d("Result","Please Signup first")
            }
        }

        authViewModel.loginResponse_.observe(this){ loginResponse ->
            loginResponse?.let {
                Log.d("Result",loginResponse.toString())
                Toast.makeText(this,"Logged In via API",Toast.LENGTH_LONG).show()
            }

        }



//        <-----Room Database----->

        val dao = MyDatabase.getDatabase(applicationContext).userDao()
        val repository = UserRepository(dao)

        val mainViewModel = ViewModelProvider(this, MainViewModelProvider(repository))
            .get(MainViewModel::class.java)

       CoroutineScope(Dispatchers.IO).launch {
           dataBinding.btnLogin.setOnClickListener {
               mainViewModel.insertUser(User(0,dataBinding.username.text.toString(),
                   dataBinding.password.text.toString()))
               Log.d("Result","Inserted")
               Toast.makeText(applicationContext,"Inserted in Room",Toast.LENGTH_LONG).show()
           }

       }

        CoroutineScope(Dispatchers.IO).launch {
             val res = mainViewModel.getUserById(0)
            Log.d("Result","Retrieved "+res)
        }

        CoroutineScope(Dispatchers.IO).launch {
            dataBinding.username.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }


                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        Toast.makeText(this@MainActivity,s.toString(), Toast.LENGTH_LONG).show()

                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }
*/



//       <-----Integration of Room and Retrofit----->
        val myDao = MyDatabase.getDatabase(applicationContext).userDao()
        val repo = Repository(myDao)

        val myViewModel = ViewModelProvider(this, MainViewModelProvider2(repo))
            .get(ViewModel::class.java)

        dataBinding.btnLogin.setOnClickListener {
            myViewModel.authenticate("john@mail.com", "changeme")
        }

        myViewModel.signupResponse.observe(this) { signupResponse ->
            Log.d("Result: SignupSuccess", "Access Token: ${signupResponse.access_token}")
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_LONG).show()
        }

        myViewModel.error.observe(this) { errorMessage ->
            Log.e("Result: Error", errorMessage)
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }



        myViewModel.loginResponse.observe(this) { loginResponse ->
            Log.d("Result: LoginSuccess", "User Profile: ${loginResponse}")
        }



    }
}