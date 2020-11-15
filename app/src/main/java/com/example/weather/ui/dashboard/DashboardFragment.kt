package com.example.weather.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weather.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    val CitySelect = arrayOf(
        "Не выбрано",
        "Берлин",
        "Буэнос-Айрес",
        "Ватутинки",
        "Воронеж",
        "Гуанчжоу",
        "Дели",
        "Долгопрудный",
        "Казань",
        "Канада",
        "Каир",
        "Королев",
        "Красногорск",
        "Лондон",
        "Лос-Анджелес",
        "Москва",
        "Мытищи",
        "Нью-Йорк",
        "Одинцово",
        "Париж",
        "Пекин",
        "Подольск",
        "Прага",
        "Сан-Паулу",
        "Санкт-Петербург",
        "Тяньцзинь",
        "Уфа",
        "Химки",
        "Чунцин",
        "Шанхай",
        "Якутск"
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.IzCity)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GlobalScope.launch(Dispatchers.Main){
            ButtonSelectIzbrannoe.setOnClickListener{
                val mdf = Dialog()
                val manager = activity?.supportFragmentManager
                if (manager != null) {mdf.show(manager, "myDialog")}
            }
        }
    }
}