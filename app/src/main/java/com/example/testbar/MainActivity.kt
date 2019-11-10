package com.example.testbar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import com.github.guilhe.views.SeekBarRangedView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.GridLayout
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {
    private var contadorCargas = 4
    private var listTextView: ArrayList<TextView> =ArrayList()

    private var listaMaestraObtenida = false
    private var listaMaestra:List<Float> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonChange.setOnClickListener{
            if(contadorCargas==0){
                var listFloat= listOf(5F,10F,22F,50F,85F,105F,111F,130F,200F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==1){
                var listFloat= listOf(5F,85F,200F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas == 2){
                var listFloat= listOf(111F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==3){
                var listFloat= listOf(10F,50F,105F,130F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if(contadorCargas==4){
                var listFloat= listOf(5F,200F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else{
                contadorCargas = 0
            }
        }



    }



    private fun setearLosDatos(list:List<Float>){

        if (!listaMaestraObtenida){
            listaMaestra= list
            iniciarPinatadoDeLista(listaMaestra)

            listaMaestraObtenida= true
        }else{

        }

    }


    /** LISTA DE VALORES PINTADA **/

    private fun iniciarPinatadoDeLista(list:List<Float>){

        if (list.size>2){
            gridlayoutValues.columnCount = list.size-2 //se le quitan dos colunan por los dos textview dentro de los linearlayout
        }

        for (i in list.indices) {

            if (i==0){
                textFirtsValue.text = list[i].toInt().toString()
                listTextView.add(textFirtsValue)
            }else if (i == list.size-1){
                textLastValue.text = list[i].toInt().toString()
                listTextView.add(textLastValue)
            }else{
               var posicionColumana = i-1

                var titleText = TextView(this)
                titleText.id = posicionColumana
                titleText.text = list[i].toInt().toString()

                var param = GridLayout.LayoutParams()
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT
                param.width = LinearLayout.LayoutParams.WRAP_CONTENT
                param.columnSpec =  GridLayout.spec(GridLayout.UNDEFINED, 1f)

                titleText.gravity = Gravity.CENTER
                titleText.layoutParams = param

                listTextView.add(titleText)

                gridlayoutValues.addView(titleText,posicionColumana)
            }
        }

        managerPintarLista()
    }

    private fun managerPintarLista(){
        var numeroSteps = listaMaestra.size-1

        Log.e("ANCHO TEX",textLastValue.width.toString()) //ancho por defecto con el texto puesto quemado

        textFirtsValue.width = textLastValue.width
        //textLastValue.width = textLastValue.width

    }
}
