package com.example.weather.ui.home


import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.data.ApiWeather
import com.example.weather.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = ApiWeather()
        var C = 0
        var Clike = 0
        var shared : SharedPreferences = requireParentFragment().requireContext().getSharedPreferences(MainActivity().PREF_NAME, MainActivity().PRIVATE_MODE)
        val editor = shared.edit()
        val adapter = ArrayAdapter(
            requireParentFragment().requireContext(),
            android.R.layout.simple_spinner_item,
            DashboardFragment().CitySelect
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        SelectSpinner?.adapter = adapter
        SelectSpinner.setSelection(shared.getInt(MainActivity().SELECT_CITY, 0))
        SelectSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                (parent.getChildAt(0) as TextView).setTextColor(Color.BLUE)
                (parent.getChildAt(0) as TextView).textSize = 20f
                text_home.text = "Загрузка..."
                val city : String = parent.getItemAtPosition(position).toString()
                editor.putInt(MainActivity().SELECT_CITY, position)
                editor.apply()

                if(position == 0){
                    text_home.text = "Не выбрано"
                    image_id.setBackgroundResource(R.color.Back)

                }
                else{
                    val apiService = ApiWeather()
                    GlobalScope.launch(Dispatchers.Main) {
                        val currentWeather = apiService.getCurrentWeather(parent.getItemAtPosition(position).toString()).await()
                        C = currentWeather.main.temp.toInt() - 273
                        Clike = currentWeather.main.feels_like.toInt() - 273
                        if (currentWeather.weather[0].main == "Clouds") {
                            text_home.text = "\nТемпература: " + C.toString() + " C" + "\nТемпература по ощущениям: " + Clike.toString() + " C" + "\nСкорость ветра: " + currentWeather.wind.speed.toString() + " м/с" + "\nВлажность: " + currentWeather.main.humidity.toString() + "\nСтатус: облачно"
                            image_id.setBackgroundResource(R.drawable.anim_loading1)
                            (image_id.background  as AnimationDrawable).start()
                        }
                        if (currentWeather.weather[0].main == "Clear") {
                            text_home.text = "\nТемпература: " + C.toString() + " C" + "\nТемпература по ощущениям: " + Clike.toString() + " C" + "\nСкорость ветра: " + currentWeather.wind.speed.toString() + " м/с" + "\nВлажность: " + currentWeather.main.humidity.toString() + "\nСтатус: Ясно"
                            image_id.setBackgroundResource(R.drawable.anim_loading2)
                            (image_id.background  as AnimationDrawable).start()
                        }
                        if (currentWeather.weather[0].main == "Mist") {
                            text_home.text = "\nТемпература: " + C.toString() + " C" + "\nТемпература по ощущениям: " + Clike.toString() + " C" + "\nСкорость ветра: " + currentWeather.wind.speed.toString() + " м/с" + "\nВлажность: " + currentWeather.main.humidity.toString() + "\nСтатус: туманно"
                            image_id.setBackgroundResource(R.drawable.anim_loading3)
                            (image_id.background  as AnimationDrawable).start()
                        }
                        if (currentWeather.weather[0].main == "Rain") {
                            text_home.text = "\nТемпература: " + C.toString() + " C" + "\nТемпература по ощущениям: " + Clike.toString() + " C" + "\nСкорость ветра: " + currentWeather.wind.speed.toString() + " м/с" + "\nВлажность: " + currentWeather.main.humidity.toString() + "\nСтатус: дождь"
                            image_id.setBackgroundResource(R.drawable.anim_loading4)
                            (image_id.background  as AnimationDrawable).start()
                        }
                        if (currentWeather.weather[0].main == "Snow") {
                            text_home.text = "\nТемпература: " + C.toString() + " C" + "\nТемпература по ощущениям: " + Clike.toString() + " C" + "\nСкорость ветра: " + currentWeather.wind.speed.toString() + " м/с" + "\nВлажность: " + currentWeather.main.humidity.toString() + "\nСтатус: снег"
                            image_id.setBackgroundResource(R.drawable.anim_loading5)
                            (image_id.background  as AnimationDrawable).start()
                        }

                        if (currentWeather.weather[0].main == "Clouds" && currentWeather.main.feels_like.toInt() < 5) {
                            Sovet.text = "Совет: Погода так себе, дома оставайтесь"
                        }
                        if (currentWeather.weather[0].main == "Clouds" && currentWeather.main.feels_like.toInt() > 5) {
                            Sovet.text = "Совет: Ну до магазина и обратно норм"
                        }
                        if (currentWeather.weather[0].main == "Clear" && currentWeather.main.feels_like.toInt() < 5) {
                            Sovet.text = "Совет: На ясную погоду и из окна смотреть можно"
                        }
                        if (currentWeather.weather[0].main == "Clear" && currentWeather.main.feels_like.toInt() > 5) {
                            Sovet.text = "Совет: Не ну погулять можно"
                        }
                        if (currentWeather.weather[0].main == "Mist" && currentWeather.main.feels_like.toInt() < 5) {
                            Sovet.text = "Совет: Ничего не видно, еще и холодно"
                        }
                        if (currentWeather.weather[0].main == "Mist" && currentWeather.main.feels_like.toInt() > 5) {
                            Sovet.text = "Совет: Ничего не видно, но вроде тепло"
                        }
                        if (currentWeather.weather[0].main == "Rain" && currentWeather.main.feels_like.toInt() < 5) {
                            Sovet.text = "Совет: В дождь надо дома сидеть"
                        }
                        if (currentWeather.weather[0].main == "Rain" && currentWeather.main.feels_like.toInt() > 5) {
                            Sovet.text = "Совет: Хоть и тепло, но дома лучше"
                        }
                        if (currentWeather.weather[0].main == "Snow" && currentWeather.main.feels_like.toInt() < 5) {
                            Sovet.text = "Совет: Пора в снежки играть"
                        }
                        if (currentWeather.weather[0].main == "Snow" && currentWeather.main.feels_like.toInt() > 5) {
                            Sovet.text = "Совет: Я не знаю как такое может быть"
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>){ }
        }
    }
}