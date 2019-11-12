package com.example.testbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.ViewTreeObserver
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


        constraintMaster.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (constraintMaster.width > 0 && textValue7.width> 0 && textValue1.width>0) {
                    constraintMaster.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    pintadoLinearValue()
                }
            }
        })

    }




    private fun setearLosDatos(list:List<Float>){

        if (!listaMaestraObtenida){
            listaMaestra= list

            //managerPintarLista()
            //iniciarPinatadoDeLista(listaMaestra)

            pintadoLinearValue()

            listaMaestraObtenida= true
        }else{

        }
    }

    /** LINEAR PINTAR MANGER **/

    private fun pintadoLinearValue(){
        var list= listOf(10F,20F,50F,100F,200F,500F,1000F)

        Handler().postDelayed({


            var numberSteps = listaMaestra.size-1
            var stepWidh = (constraintMaster.width-textValue7.width-100)/numberSteps
            Log.e("TAMANO TEXTO",textValue7.width.toString())
            Log.e("CALCULO 1:", "(${constraintMaster.width}-${textValue7.width})/${numberSteps}" )
            Log.e("CALCULO 1 RES", stepWidh.toString() )
            var widhLinearLayoutLast = (textValue7.width/2)+(stepWidh/2)
            var widhLinearLayoutFirts = (textValue1.width/2)+(stepWidh/2)
            Log.e("widhLinearLayoutFirts",widhLinearLayoutFirts.toString())

            textValue1.width = widhLinearLayoutFirts
            textValue7.width = widhLinearLayoutLast


            for (i in list.indices) {

                if (i==0){
                    textValue1.text = list[i].toInt().toString()
                    listTextView.add(textValue1)
                }else if (i == list.size-1){
                    textValue7.text = "1k+"
                    listTextView.add(textValue7)
                }
            }

            textValue2.width = stepWidh
            textValue3.width = stepWidh
            textValue4.width = stepWidh
            textValue5.width = stepWidh
            textValue6.width = stepWidh


        },100)


        Handler().postDelayed({
            Log.e("1",textValue1.width.toString())

            Log.e("2",textValue2.width.toString())
            Log.e("3",textValue3.width.toString())

            Log.e("linear",linearMaster.width.toString())

        },200)


    }
}
