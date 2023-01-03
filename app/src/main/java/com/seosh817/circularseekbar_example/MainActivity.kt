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

            circularSeekBar.setOnAnimationEndListener { _ ->
                // listen
            }

            sbProgress.progress = circularSeekBar.progress.roundToInt()
            sbStartAngle.progress = circularSeekBar.startAngle.roundToInt()
            sbSweepAngle.progress = circularSeekBar.sweepAngle.roundToInt()
            sbBarWidth.progress = circularSeekBar.barWidth.roundToInt()

            switchUseGradient.isChecked = circularSeekBar.progressGradientColorsArray.isNotEmpty()
            switchRounded.isChecked = circularSeekBar.barStrokeCap == BarStrokeCap.ROUND
            switchAnimation.isChecked = circularSeekBar.showAnimation
            switchThumbVisible.isChecked = circularSeekBar.innerThumbRadius > 0f && circularSeekBar.outerThumbRadius > 0f
            switchInteractive.isChecked = circularSeekBar.interactive

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
                    circularSeekBar.barWidth = progress.toFloat().dp
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

            switchUseGradient.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    circularSeekBar.progressGradientColorsArray = resources.getIntArray(R.array.rainbow)
                } else {
                    circularSeekBar.progressGradientColorsArray = intArrayOf()
                }
            }

            switchRounded.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    circularSeekBar.barStrokeCap = BarStrokeCap.ROUND
                } else {
                    circularSeekBar.barStrokeCap = BarStrokeCap.SQUARE
                }
            }

            switchAnimation.setOnCheckedChangeListener { _, isChecked ->
                circularSeekBar.showAnimation = isChecked
            }

            switchThumbVisible.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    circularSeekBar.innerThumbRadius = 3f.dp
                    circularSeekBar.innerThumbStrokeWidth = 3f.dp
                    circularSeekBar.outerThumbRadius = 5f.dp
                    circularSeekBar.outerThumbStrokeWidth = 5f.dp
                } else {
                    circularSeekBar.innerThumbRadius = 0f
                    circularSeekBar.innerThumbStrokeWidth = 0f
                    circularSeekBar.outerThumbRadius = 0f
                    circularSeekBar.outerThumbStrokeWidth = 0f
                }
            }

            switchInteractive.setOnCheckedChangeListener { _, isChecked ->
                circularSeekBar.interactive = isChecked
            }
        }
    }
}
