package com.abrarkhalifa.whetherapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        window.statusBarColor = Color.parseColor("#1383C3")


        getJsonData(lat, long)


    }

    private fun getJsonData(lat: String?, long: String?) {

        val API_KEY = "c3207985ee470d46fbcff6ae0b1f6116"
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null, Response.Listener
            { response ->
                setValue(response)

            }, Response.ErrorListener { Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show() })

        queue.add(jsonRequest)

    }

    private fun setValue(response:JSONObject){

        city.text = response.getString("name")
        val lat = "lat : "+response.getJSONObject("coord").getString("lat")
        val lon = "lon : "+response.getJSONObject("coord").getString("lon")

        coordinates.text = "${lat} , ${lon}"
        weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

        var temper = response.getJSONObject("main").getString("temp")
        temper = (((temper).toFloat()-273.15).toInt()).toString()
        temp.text = temper+" °C "

        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat()-273.15)).toInt()).toString()
        min_temp.text = "Min :"+mintemp+"°C "

        var maxtemp = response.getJSONObject("main").getString("temp_max")
        maxtemp = ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
        max_temp.text = "Max :"+maxtemp+"°C "

        pressure.text = response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity")+"%"

        wind.text = response.getJSONObject("wind").getString("speed")

        degree.text = "Degree :"+response.getJSONObject("wind").getString("deg")+"°"







    }


}