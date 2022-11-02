package com.seosh817.circularseekbar_example

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.seosh817.circularseekbar.BarStrokeCap
import com.seosh817.circularseekbar_example.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            circularSeekBar.setOnProgressChangedListener { progress ->
                tvProgressValue.text = progress.toString()
            }

            circularSeekBar.setOnAnimationEndListener { progress ->
                // listen
            }

            sbProgress.progress = circularSeekBar.progress.roundToInt()
            sbStartAngle.progress = circularSeekBar.startAngle.roundToInt()
            sbSweepAngle.progress = circularSeekBar.sweepAngle.roundToInt()
            sbBarWidth.progress = circularSeekBar.barWidth.roundToInt()

            switchRounded.isChecked = circularSeekBar.barStrokeCap == BarStrokeCap.ROUND
            switchThumbVisible.isChecked = circularSeekBar.innerThumbRadius > 0f && circularSeekBar.outerThumbRadius > 0f

            sbProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.progress = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            sbStartAngle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.startAngle = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            sbSweepAngle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.sweepAngle = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            sbBarWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.barWidth = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            sbDashWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.dashWidth = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            sbDashGap.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    circularSeekBar.dashGap = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            switchThumbVisible.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    circularSeekBar.innerThumbRadius = 20f
                    circularSeekBar.innerThumbStrokeWidth = 12f
                    circularSeekBar.outerThumbRadius = 20f
                    circularSeekBar.outerThumbStrokeWidth = 40f
                } else {
                    circularSeekBar.innerThumbRadius = 0f
                    circularSeekBar.innerThumbStrokeWidth = 0f
                    circularSeekBar.outerThumbRadius = 0f
                    circularSeekBar.outerThumbStrokeWidth = 0f
                }
            }

            switchRounded.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    circularSeekBar.barStrokeCap = BarStrokeCap.ROUND
                } else {
                    circularSeekBar.barStrokeCap = BarStrokeCap.SQUARE
                }
            }

            switchInteractive.setOnCheckedChangeListener { _, isChecked ->
                circularSeekBar.interactive = isChecked
            }
        }
    }
}
