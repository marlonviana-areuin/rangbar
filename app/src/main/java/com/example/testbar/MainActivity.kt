package com.example.testbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var contadorCargas = 0
    private var listTextView: ArrayList<TextView> =ArrayList()
    private var listLinearLayoutView: ArrayList<LinearLayout> =ArrayList()

    private var listaMaestraObtenida = false
    private var listaMaestra:List<Float> = ArrayList()
    private var paddingReaddy =false

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


        manejadorVisual()

    }

    private fun  manejadorVisual(){

        constraintMaster.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                constraintMaster.viewTreeObserver.removeOnGlobalLayoutListener(this)
                //pintadoLinearValue()
            }
        })


    }




    private fun setearLosDatos(list:List<Float>){

        if (!listaMaestraObtenida){
            listaMaestra= list
            listaMaestraObtenida= true

            pintadoLinearValue(listaMaestra)
        }else{

        }
    }

    /** LINEAR PINTAR MANGER **/

    private fun pintadoLinearValue(list: List<Float>){

        for (i in list.indices){

            var textViewValue = TextView(this)
            textViewValue.id = i
            textViewValue.text = list[i].toString()

            var param =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            textViewValue.gravity = Gravity.CENTER
            textViewValue.layoutParams = param

            if (i==0){
                var linearFirst= LinearLayout(this)
                linearFirst.id = i
                linearFirst.gravity = Gravity.LEFT
                var paramLinear =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)

                linearFirst.layoutParams = paramLinear

                listLinearLayoutView.add(linearFirst)

                linearFirst.addView(textViewValue)
                linearLayoutValues.addView(linearFirst)
            }else  if (i == list.size-1){
                var linearLayoutLast= LinearLayout(this)
                linearLayoutLast.id = i
                linearLayoutLast.gravity = Gravity.RIGHT
                var paramLinear =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)

                linearLayoutLast.layoutParams = paramLinear

                listLinearLayoutView.add(linearLayoutLast)

                linearLayoutLast.addView(textViewValue)
                linearLayoutValues.addView(linearLayoutLast)
            }else{
                linearLayoutValues.addView(textViewValue)
            }

            listTextView.add(textViewValue)

        }


        Handler().postDelayed({

            var numberSteps = list.size-1
            var stepWidth = (constraintMaster.width- listTextView[listTextView.size-1].width-100)/numberSteps

            var widhLinearLayoutLast = (listTextView[listTextView.size-1].width/2)+(stepWidth/2)
            //var widhLinearLayoutFirts = (listTextView[0].width/2)+(stepWidh/2)


            for (i in listTextView.indices){
                if (i==0){
                    var param =  LinearLayout.LayoutParams(widhLinearLayoutLast,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    listLinearLayoutView[0].layoutParams = param
                    listTextView[i].width = listTextView[listTextView.size-1].width
                }else  if (i == list.size-1){
                    var param =  LinearLayout.LayoutParams(widhLinearLayoutLast,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    listLinearLayoutView[1].layoutParams = param
                }else{
                    listTextView[i].width = stepWidth
                }
            }

            /** Ajusta El ancho del imageview que contiene la lista**/
            var widthImageView = stepWidth*numberSteps
            imageViewLine.layoutParams.width = widthImageView


            linearLayoutRangbar.layoutParams.width = stepWidth*list.size

        },1)

          /*  Log.e("--------------","--------------------")
            var numberSteps = list.size-1
            var stepWidh = (constraintMaster.width-textValue7.width-100)/numberSteps
            Log.e("TAMANO TEXTO",textValue7.width.toString())
            Log.e("CALCULO 1", "(${constraintMaster.width}-${textValue7.width}-100)/${numberSteps}" )
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




        Handler().postDelayed({

            Log.e("1",textValue1.width.toString())

            Log.e("2",textValue2.width.toString())
            Log.e("3",textValue3.width.toString())


            var paddingRangeBar = ((constraintMaster.width-linearLayoutValues.width)/2)/2
            Log.e("View", "${constraintMaster.width}-${linearLayoutValues.width}")
            Log.e("PaddingRangerBar",paddingRangeBar.toString())

            val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT)
            params.setMargins(paddingRangeBar, 0, paddingRangeBar, 0)

            linearLayoutRangbar.layoutParams = params
            //imageViewLine.layoutParams = params

        },1)*/




    }
}
