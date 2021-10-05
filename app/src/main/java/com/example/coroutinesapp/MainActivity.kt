package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {
    lateinit var tvAdvaice : TextView
    lateinit var submit : Button

    val adviceurl = "https://api.adviceslip.com/advice"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAdvaice=findViewById(R.id.txt1)
        submit = findViewById(R.id.btnAdvaice)

        submit.setOnClickListener {
                requestApi()
        }
    }

     fun requestApi()
    {

        CoroutineScope(Dispatchers.IO).launch {

            val data = async {

                fetchAdvice()

            }.await()

            if (data.isNotEmpty())
            {

                updateAdvice(data)
            }

        }

    }

     fun fetchAdvice():String{

        var ans=""
        try {
            ans = URL(adviceurl).readText(Charsets.UTF_8)

        }catch (e:Exception)
        {
            println("Error $e")

        }
        return ans

    }

     suspend fun updateAdvice(data:String)
    {
        withContext(Dispatchers.Main)
        {

            val jsonObject = JSONObject(data)
            val slip = jsonObject.getJSONObject("slip")
            val advice = slip.getString("advice")
            tvAdvaice.text = advice
        }

    }
}