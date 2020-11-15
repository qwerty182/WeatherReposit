package com.example.weather.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class Dialog : DialogFragment(){
    val checkItems = BooleanArray(15, {false})

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбор городов")
                .setMultiChoiceItems(DashboardFragment().CitySelect, checkItems){
                        dialog, which, isChecked ->
                    checkItems[which] = isChecked
                    val name = DashboardFragment().CitySelect[which]
                    Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("Готов"){
                        dialog, id ->
                    var count : Int = 0
                    for(i in DashboardFragment().CitySelect.indices){
                        if(checkItems[i]){
                            count++
                        }
                    }
                    var citys = arrayOfNulls<String>(count)

                    count = 0
                    for(i in DashboardFragment().CitySelect.indices){
                        if(checkItems[i]){
                            citys[count] = i.toString()
                            count++
                        }
                    }
                }
                .setNegativeButton("Отмена"){
                        dialog, _ -> dialog.cancel()
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
        super.onCreate(savedInstanceState)
    }
}