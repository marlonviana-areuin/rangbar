package com.example.testbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
    private var contadorCargas = 0
    private var listTextView: ArrayList<TextView> =ArrayList()

    private var listaMaestraObtenida = false
    private var listaMaestra:List<Float> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonChange.setOnClickListener{
            if(contadorCargas==0){
                var listFloat= listOf(10F,20F,50F,100F,200F,500F,1000F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==1){
                var listFloat= listOf(10F,500F,200F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas == 2){
                var listFloat= listOf(500F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==3){
                var listFloat= listOf(10F,50F,100F,1000F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if(contadorCargas==4){
                var listFloat= listOf(10F,200F)
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

            //managerPintarLista()
            //iniciarPinatadoDeLista(listaMaestra)

            pintadoLinearValue(listaMaestra)

            listaMaestraObtenida= true
        }else{

        }
    }


    /** GRID DE VALORES PINTADA **/

    private fun iniciarPinatadoDeLista(list:List<Float>){
        var numberSteps = listaMaestra.size-1
        var stepWidh = (constraintMaster.width-10/4*textLastValue.width)/numberSteps //ancho de la separacion entre steps

        if (list.size>2){
            gridlayoutValues.columnCount = list.size-2 //se le quitan dos colunan por los dos textview dentro de los linearlayout
            gridlayoutValues.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        }



        for (i in list.indices) {

            if (i==0){
                textFirtsValue.text = list[i].toInt().toString()
                listTextView.add(textFirtsValue)
            }else if (i == list.size-1){
                //textLastValue.text = list[i].toInt().toString()
                textLastValue.text = "1k+"
                listTextView.add(textLastValue)
            }else{
               var posicionColumana = i-1

                var titleText = TextView(this)
                titleText.id = posicionColumana
                titleText.text = list[i].toInt().toString()

                var param = GridLayout.LayoutParams()
                //param.height = LinearLayout.LayoutParams.WRAP_CONTENT
                //param.width = stepWidh
                //param.columnSpec =  GridLayout.spec(GridLayout.UNDEFINED, 1f)

                titleText.gravity = Gravity.CENTER
                //titleText.width = stepWidh
                //titleText.layoutParams = param

                listTextView.add(titleText)

                gridlayoutValues.addView(titleText,posicionColumana)
            }
        }

        Handler().postDelayed({
            for (i in listTextView.indices){
                Log.e("$i",listTextView[i].width.toString())
            }

        },200)

    }

    private fun managerPintarLista(){

        Log.e("ANCHO TEX",textLastValue.width.toString()) //ancho por defecto con el texto puesto quemado

        textFirtsValue.width = textLastValue.width
        textLastValue.width = textLastValue.width

        var numberSteps = listaMaestra.size-1
        var stepWidh = (constraintMaster.width-textFirtsValue.width)/numberSteps //ancho de la separacion entre steps

        Log.e("ANCHO STEP",stepWidh.toString())

        var widhLinearLayoutFirts = (textLastValue.width/2)+(stepWidh/2)
        Log.e("ANCHO LINEAR",widhLinearLayoutFirts.toString())
        linearLayoutFirts.layoutParams.width = widhLinearLayoutFirts
        linearLayoutLast.layoutParams.width = widhLinearLayoutFirts
    }


    /** LINEAR PINTAR MANGER **/

    private fun pintadoLinearValue(list:List<Float>){

        var numberSteps = listaMaestra.size-1
        var stepWidh = (constraintMaster.width-textValue7.width)/numberSteps
        Log.e("CALCULO 1:", "(${constraintMaster.width}-${textValue7.width})/${numberSteps}" )
        var widhLinearLayoutFirts = (textValue7.width/2)+(stepWidh/2)
        Log.e("widhLinearLayoutFirts",widhLinearLayoutFirts.toString())

        textValue1.width = widhLinearLayoutFirts
        textValue7.width = widhLinearLayoutFirts

        Handler().postDelayed({

            for (i in list.indices) {

                if (i==0){
                    textValue1.text = list[i].toInt().toString()
                    listTextView.add(textFirtsValue)
                }else if (i == list.size-1){
                    textValue7.text = "1k+"
                    listTextView.add(textLastValue)
                }
            }

            Log.e("TAMANO TEXTO",textValue1.width.toString())

            Log.e("CALCULO",stepWidh.toString())

            textValue2.width = stepWidh
            textValue3.width = stepWidh
            textValue4.width = stepWidh
            textValue5.width = stepWidh
            textValue6.width = stepWidh

            Log.e("2",textValue2.left.toString())
            Log.e("3",textValue3.left.toString())


        },2000)

    }
}
