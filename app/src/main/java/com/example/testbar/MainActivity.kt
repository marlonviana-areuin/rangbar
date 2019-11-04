package com.example.testbar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.guilhe.views.SeekBarRangedView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.GridLayout
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {

    private var listTextView: ArrayList<TextView> =ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listFloat= listOf(10F,22F,50F,85F,105F)

        /** SEEKBAR **/
        seekBar.setThumbNormalImageResource(R.drawable.ic_ucoin)
        seekBar.setProgressStepRadius(10F)

        seekBar.minValue = 0F
        seekBar.maxValue = (listFloat.size-1).toFloat()

        seekBar.enableProgressBySteps(true)
        seekBar.progressSteps = itemStep(listFloat)

        seekBar.setOnSeekBarRangedChangeListener(object :
            SeekBarRangedView.OnSeekBarRangedChangeListener {
            override fun onChanged(view: SeekBarRangedView, minValue: Float, maxValue: Float) {
                updateLayout(minValue, maxValue)

                paintText( getValuoFloat(minValue,listFloat),getValuoFloat(maxValue,listFloat),listFloat)
            }

            override fun onChanging(view: SeekBarRangedView, minValue: Float, maxValue: Float) {
                updateLayout(minValue, maxValue)

            }

            private fun updateLayout(minValue: Float, maxValue: Float) {

            }
        })
        //seekBar.setSelectedMinValue(20F, true)
        /** GRIDLAYOUT **/
        gridlayoutValues.columnCount = listFloat.size
        for (i in listFloat.indices){
            var titleText = TextView(applicationContext)
            titleText.id = i
            titleText.text = listFloat[i].toString()


            var param = GridLayout.LayoutParams()
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.WRAP_CONTENT
            if (i != listFloat.size-1){
                param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            }

            titleText.layoutParams = param

            listTextView.add(titleText)
            gridlayoutValues.addView(titleText,i)
        }

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

    @SuppressLint("ResourceAsColor", "NewApi")
    private fun paintText(inital:Float, end:Float, list:List<Float>){
        for (i in list.indices){
          if (list[i]>= inital && list[i]<= end){
              listTextView[i].setTextColor(getColor(R.color.colorPrimary))
          }else{
              listTextView[i].setTextColor(getColor(R.color.grayLight))
          }
        }
    }
}
