package com.example.testbar
import android.content.res.Resources
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
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity : AppCompatActivity() {
    private var contadorCargas = 0
    private var listTextView: ArrayList<TextView> =ArrayList()
    private var listLinearLayoutView: ArrayList<LinearLayout> =ArrayList()

    private var listaMaestraObtenida = false
    private var listaMaestra:List<Float> = ArrayList()
    private var numberStepMaster:Int=0
    private var stepWidth:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonChange.setOnClickListener{
            if (contadorCargas==5){
                contadorCargas = 0
            }

            if(contadorCargas==0){
                var listFloat= listOf(10F,15F,20F,50F,80F,85F,100F,150F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==1){
                var listFloat= listOf(20F,50F,150F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas == 2){
                var listFloat= listOf(85F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==3){
                var listFloat= listOf(10F,50F,80F,100F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if(contadorCargas==4){
                var listFloat= listOf(100F,150F)
                setearLosDatos(listFloat)
                contadorCargas++
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
            seekbarPaddingManager(list)
        }
    }

    /** LINEAR PINTAR MANGER **/
    private fun pintadoLinearValue(list: List<Float>){

        for (i in list.indices){

            var textViewValue = TextView(this)
            textViewValue.id = i
            textViewValue.text = list[i].toInt().toString()

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
            /** obtiene el numero de step y el ancho entre step**/
            numberStepMaster = list.size-1
            stepWidth = (constraintMaster.width- listTextView[listTextView.size-1].width-100)/numberStepMaster

            var widhLinearLayoutBorder = (listTextView[listTextView.size-1].width/2)+(stepWidth/2)

            for (i in listTextView.indices){
                if (i==0){
                    var param =  LinearLayout.LayoutParams(widhLinearLayoutBorder,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    listLinearLayoutView[0].layoutParams = param
                    listTextView[i].width = listTextView[listTextView.size-1].width
                }else  if (i == list.size-1){
                    var param =  LinearLayout.LayoutParams(widhLinearLayoutBorder,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    listLinearLayoutView[1].layoutParams = param
                }else{
                    listTextView[i].width = stepWidth
                }
            }

            /** Ajusta el ancho del imageview**/
            var widthImageView = stepWidth*numberStepMaster
            imageViewLine.layoutParams.width = widthImageView

            /** Ajusta el ancho del LinearLayoout que dentro contiene el linearlayout que contiene el rangbar**/
            linearLayoutContainerRangBar.layoutParams.width = widthImageView+rangerSlider.width
            /** Ajusta el padding del seekbar**/
            seekbarPaddingManager(listaMaestra)

        },1)


    }

    private fun seekbarPaddingManager(newList: List<Float>){

        linearLayoutRangbar.layoutParams.width = imageViewLine.width + rangerSlider.width
        var  contStepLeft = 0
        var firtsValue = newList[0]
        for (i in listaMaestra.indices){
            if (listaMaestra[i] == firtsValue){
                contStepLeft = i
                break
            }
        }


        var  contStepRight = 0
        var  lastValue = newList[newList.size-1]
        var reverListMaster = listaMaestra.reversed()
        for (i in reverListMaster.indices){
            if (reverListMaster[i] == lastValue){
                contStepRight = i
                break
            }

        }


        var paddingLeftBar = 0
        if (contStepLeft>0){
            if (contStepLeft*stepWidth==0){
                paddingLeftBar = 0
            }else{
                paddingLeftBar = (contStepLeft*stepWidth) - (rangerSlider.width/2)
            }

        }

        var paddingRightBar = 0
        if (contStepRight>0){
            if (contStepRight*stepWidth==0){
                paddingRightBar = 0
            }else{
                paddingRightBar = (contStepRight*stepWidth) - (rangerSlider.width/2)
            }

        }
        linearLayoutRangbar.setPadding(paddingLeftBar, 0, paddingRightBar, 0)

        Log.e("-----------------","---------------------------")
        Log.e("RANGO DE VALORES", "minimo $firtsValue   maximo $lastValue")
        Log.e("CONTADORES", "izquierda $contStepLeft   derecha $contStepRight")
        Log.e("COMPONENTES CALCULO  DERECHA", "contStepRight $contStepRight   stepWidth $stepWidth  rangerSlider.width ${rangerSlider.width}")
        Log.e("CALCULO DERECHA","${paddingRightBar}")
        Log.e("COMPONENTES CALCULO  IZQUIERDA", "contStepRight $contStepLeft   stepWidth $stepWidth  rangerSlider.width ${rangerSlider.width}")
        Log.e("CALCULO IZQUIERDA","${paddingLeftBar}")


    }



}
