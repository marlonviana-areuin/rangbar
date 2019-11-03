package com.example.testbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.guilhe.views.SeekBarRangedView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listFloat= listOf(10F,22F,50F,85F)

        /** SEEKBAR **/
        seekBar.setThumbNormalImageResource(R.drawable.ic_ucoin)
        seekBar.setProgressStepRadius(10F)

        seekBar.minValue = 0F
        seekBar.maxValue = (listFloat.size-1).toFloat()

        textMin.text = seekBar.minValue.toString()
        textMax.text = seekBar.maxValue.toString()

        seekBar.enableProgressBySteps(true)
        seekBar.progressSteps = itemStep(listFloat)

        seekBar.setOnSeekBarRangedChangeListener(object :
            SeekBarRangedView.OnSeekBarRangedChangeListener {
            override fun onChanged(view: SeekBarRangedView, minValue: Float, maxValue: Float) {
                updateLayout(minValue, maxValue)
                textMin.text = getValuoFloat(minValue,listFloat).toString()
                textMax.text = getValuoFloat(maxValue,listFloat).toString()

                Log.e("ENTRO","MIN $minValue  MAX $maxValue")
            }

            override fun onChanging(view: SeekBarRangedView, minValue: Float, maxValue: Float) {
                updateLayout(minValue, maxValue)

            }

            private fun updateLayout(minValue: Float, maxValue: Float) {

            }
        })
        //seekBar.setSelectedMinValue(20F, true)
        /** GRIDLAYOUT **/


    }

    private fun itemStep(list:List<Float>):List<Float>{
        var listParce: ArrayList<Float> = ArrayList()

        for(i in 0 until list.size){
            listParce.add(i.toFloat())
        }

        return listParce
    }

    private fun getValuoFloat(itemLis:Float,list:List<Float>):Float{
        return list[itemLis.toInt()]
    }
}
