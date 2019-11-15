package com.example.testbar
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.github.guilhe.views.SeekBarRangedView
import kotlinx.android.synthetic.main.activity_main.*



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
                var listFloat= listOf(10F,12F,20F,30F,36F,40F,48F,50F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==1){
                var listFloat= listOf(12F,30F,36F,40F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas == 2){
                var listFloat= listOf(48F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if (contadorCargas==3){
                var listFloat= listOf(10F,36F,50F)
                setearLosDatos(listFloat)
                contadorCargas++
            }else if(contadorCargas==4){
                var listFloat= listOf(20F,40F)
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
    //region Pintado De Barra de valores y RangBar
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
            Handler().postDelayed({
                seekbarPaddingManager(listaMaestra)
            },1)

        },1)


    }

    private fun seekbarPaddingManager(newList: List<Float>){
        /** Cuenta los steps de izquierda y derecha **/
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

        /** Calcula el padding de izquierda y derecha **/
        var paddingLeftBar = 0
        if (contStepLeft>0){
            paddingLeftBar = (contStepLeft*stepWidth)
        }

        var paddingRightBar = 0
        if (contStepRight>0){
            paddingRightBar = (contStepRight*stepWidth)
        }
        linearLayoutRangbar.setPadding(paddingLeftBar, 0, paddingRightBar, 0)

        managerRangeBar(newList)
    }
    //endregion

    //region MANAGER RANGBAR
    private fun managerRangeBar(listFloat: List<Float>){
        linearLayoutRangbar.removeAllViews()

        var seekBarNew = reloadRangBar(listFloat)


        seekBarNew.setOnSeekBarRangedChangeListener(object :
            SeekBarRangedView.OnSeekBarRangedChangeListener {
            override fun onChanged(view: SeekBarRangedView, minValue: Float, maxValue: Float) {
                Log.e("RANGO VALOR","MIN $minValue  MAX $maxValue")
                Log.e("RANGO VALOR","MIN ${calculateListRange(listFloat)[minValue.toInt()]}  MAX ${calculateListRange(listFloat)[maxValue.toInt()]}")
            }

            override fun onChanging(view: SeekBarRangedView, minValue: Float, maxValue: Float) {

            }

            private fun updateLayout(minValue: Float, maxValue: Float) {

            }
        })

        linearLayoutRangbar.addView(seekBarNew)

    }

    @SuppressLint("NewApi")
    private fun reloadRangBar(list:List<Float>):SeekBarRangedView{
        var seekbarNew = SeekBarRangedView(this)
        seekbarNew.setThumbsImageResource(R.drawable.ic_ucoin)
        seekbarNew.setBackgroundColor(getColor(R.color.grayLight))
        seekbarNew.setProgressColor(getColor(R.color.colorPrimary))
        seekbarNew.setBackgroundHeight(12F)
        seekbarNew.setProgressHeight(12F)

        seekbarNew.setProgressStepRadius(25F)

        seekbarNew.minValue = 0F
        seekbarNew.maxValue = calculateRangerValue(list)


        seekbarNew.progressSteps = calculateSteps(list,calculateListRange(list))
        seekbarNew.enableProgressBySteps(true)

        return  seekbarNew
    }

    private fun calculateRangerValue(newList:List<Float>):Float{
        var firstItem = newList[0]
        var lastItem = newList[newList.size-1]
        var countValues = 0

        for (i in listaMaestra.indices){
            if (i == 0){
                if (listaMaestra[i] == firstItem){
                    countValues++
                }
            }else if (i == listaMaestra.size-1){
                if (listaMaestra[i]== lastItem){
                    countValues++
                }
            }else{
                if (listaMaestra[i] in firstItem..lastItem){
                    countValues++
                }
            }
        }

        var stringValues = "Steps: $countValues \n"
        for (i in newList.indices){
            stringValues = stringValues+"\n"+newList[i].toString()
        }
        textValues.text = stringValues

        return (countValues-1).toFloat()

    }

    private fun calculateListRange(newList:List<Float>):List<Float>{
        var listNewRang:ArrayList<Float> = ArrayList()
        var firstItem = newList[0]
        var lastItem = newList[newList.size-1]

        for (i in listaMaestra.indices){
            if (i == 0){
                if (listaMaestra[i] == firstItem){
                    listNewRang.add(listaMaestra[i])
                }
            }else if (i == listaMaestra.size-1){
                if (listaMaestra[i]== lastItem){
                    listNewRang.add(listaMaestra[i])
                }
            }else{
                if (listaMaestra[i] in firstItem..lastItem){
                    listNewRang.add(listaMaestra[i])
                }
            }
        }

        return  listNewRang
    }

    private fun  calculateSteps(newList:List<Float>,listNewRang:List<Float>):List<Float>{
        var listStep: ArrayList<Float> = ArrayList()

        for (i in listNewRang.indices){
            for (j in newList.indices){
                if (listNewRang[i] == newList[j]){
                    listStep.add(i.toFloat())
                }
            }
        }
        return  listStep
    }

    //endregion



}
