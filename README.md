
<h1 align="center">CircularSeekBar</h1>

<p align="center">
  <a href="https://android.com">
    <img src="https://img.shields.io/badge/Platform-Android-brightgreen?logo=android"
      alt="Platform" />
  </a>

  <a href="https://maven-badges.herokuapp.com/maven-central/com.seosh817/circular_seek_bar_1.0.0">
    <img src="https://img.shields.io/maven-central/v/com.seosh817/circular_seek_bar_1.0.0.svg"
      alt="Maven Central" />
  </a>

  <a href="https://circleci.com/gh/circleci/circleci-docs/tree/teesloane-patch-5.svg">
    <img src="https://circleci.com/gh/seosh817/CircularSeekBar.svg?style=shield&circle-token=a531a56d2822f238992c1588df7432a8aef389c6"
      alt="Build Status" />
  </a>

  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/github/license/seosh817/CircularSeekBar"
      alt="License: MIT" />
  </a>
</p><br>

<p align="center">CircularSeekBar is a circular progress bar/seek bar android library that supports animations, dashes and gradients.</p>

# Getting Started
- [Youtube Demo Video](#youtube-demo-video)
- [Basic Examples](#basic-examples)
  - [Basic SeekBar](#1-basic-seekbar)
  - [Gradient SeekBar](#2-gradient-seekbar)
  - [Dashed SeekBar](#3-dashed-seekbar)
  - [Listen to the progress changed](#4-listen-for-value-changes)
  - [Animations](#5-animations)
- [Installing](#installing)
  - [Depend on it](#1-depend-on-it)
  - [Install it](#2-install-it)
  - [Import it](#3-import-it)
- [Properties](#properties)
- [License](#license)
- [Contribution](#contribution)

# Youtube Demo Video
An example project can be found in the [example directory](https://github.com/seosh817/CircularSeekBar/tree/master/app) of this repository.

[![Demo Video](https://img.youtube.com/vi/3w8WPZhl0TI/hqdefault.jpg)](https://youtu.be/3w8WPZhl0TI)

# Basic Examples

### 1. Basic SeekBar

<img src="https://github.com/seosh817/CircularSeekBar/blob/release/1.0.0/images/start_90_sweep_360.gif?raw=true" width="300"/>

### Xml code:

If you want to use thumb, set the thumb property values.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

  <com.seosh817.circularseekbar.CircularSeekBar
          android:id="@+id/circular_seek_bar"
          android:layout_width="match_parent"
          android:layout_height="300dp"
          app:circularSeekBar_animation="normal"
          app:circularSeekBar_animationDurationMillis="1000"
          app:circularSeekBar_barWidth="8dp"
          app:circularSeekBar_innerThumbRadius="5dp"
          app:circularSeekBar_innerThumbStrokeWidth="3dp"
          app:circularSeekBar_min="0"
          app:circularSeekBar_outerThumbRadius="5dp"
          app:circularSeekBar_outerThumbStrokeWidth="10dp"
          app:circularSeekBar_startAngle="90"
          app:circularSeekBar_sweepAngle="360"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
### 2. Gradient SeekBar

<img src="https://github.com/seosh817/CircularSeekBar/blob/release/1.0.0/images/start_45_sweep_270_gradient_rainbow.gif?raw=true" width="300"/>

### Xml code(colors.xml):

```xml
<resources>
    <color name="red">#FFF44336</color>
    <color name="orange">#FFFFAB40</color>
    <color name="yellow">#FFFFEB3B</color>
    <color name="green">#FF4CAF50</color>
    <color name="blue">#FF2196F3</color>
    <color name="indigo">#FF3F51B5</color>
    <color name="purple">#FF9C27B0</color>

    <array name="rainbow">
        <item>@color/red</item>
        <item>@color/orange</item>
        <item>@color/yellow</item>
        <item>@color/green</item>
        <item>@color/blue</item>
        <item>@color/indigo</item>
        <item>@color/purple</item>
    </array>
</resources>
```

### Xml code(activity_main.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.seosh817.circularseekbar.CircularSeekBar
        android:id="@+id/circular_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:circularSeekBar_animation="normal"
        app:circularSeekBar_animationDurationMillis="1000"
        app:circularSeekBar_barWidth="8dp"
        app:circularSeekBar_innerThumbRadius="5dp"
        app:circularSeekBar_innerThumbStrokeWidth="3dp"
        app:circularSeekBar_min="0"
        app:circularSeekBar_outerThumbRadius="5dp"
        app:circularSeekBar_outerThumbStrokeWidth="10dp"
        app:circularSeekBar_startAngle="45"
        app:circularSeekBar_sweepAngle="270"
        app:circularSeekBar_progressGradientColors="@array/rainbow"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
### 3. Dashed SeekBar

<img src="https://github.com/seosh817/CircularSeekBar/blob/release/1.0.0/images/start_90_sweep_180_dashWidth_50_dashGap_15.gif?raw=true" width="300"/>


### Xml code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.seosh817.circularseekbar.CircularSeekBar
        android:id="@+id/circular_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:circularSeekBar_animation="normal"
        app:circularSeekBar_animationDurationMillis="1000"
        app:circularSeekBar_barWidth="8dp"
        app:circularSeekBar_dashGap="15"
        app:circularSeekBar_dashWidth="50"
        app:circularSeekBar_innerThumbRadius="5dp"
        app:circularSeekBar_innerThumbStrokeWidth="3dp"
        app:circularSeekBar_min="0"
        app:circularSeekBar_outerThumbRadius="5dp"
        app:circularSeekBar_outerThumbStrokeWidth="10dp"
        app:circularSeekBar_progressGradientColors="@array/rainbow"
        app:circularSeekBar_startAngle="90"
        app:circularSeekBar_sweepAngle="180"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
<img src="https://github.com/seosh817/CircularSeekBar/blob/release/1.0.0/images/start_45_sweep_270_dashWidth_80_dashGap_15.gif?raw=true" width="300"/>

### Xml code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.seosh817.circularseekbar.CircularSeekBar
        android:id="@+id/circular_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:circularSeekBar_animation="normal"
        app:circularSeekBar_animationDurationMillis="1000"
        app:circularSeekBar_barWidth="8dp"
        app:circularSeekBar_innerThumbRadius="5dp"
        app:circularSeekBar_innerThumbStrokeWidth="3dp"
        app:circularSeekBar_min="0"
        app:circularSeekBar_outerThumbRadius="5dp"
        app:circularSeekBar_outerThumbStrokeWidth="10dp"
        app:circularSeekBar_progressGradientColors="@array/rainbow"
        app:circularSeekBar_startAngle="45"
        app:circularSeekBar_dashWidth="80"
        app:circularSeekBar_dashGap="15"
        app:circularSeekBar_sweepAngle="270"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```


<img src="https://github.com/seosh817/CircularSeekBar/blob/release/1.0.0/images/start_45_sweep_270_dashWidth_1_dashGap_2.gif?raw=true" width="300"/>

### Xml code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.seosh817.circularseekbar.CircularSeekBar
        android:id="@+id/circular_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:circularSeekBar_animation="normal"
        app:circularSeekBar_animationDurationMillis="1000"
        app:circularSeekBar_barStrokeCap="butt"
        app:circularSeekBar_barWidth="8dp"
        app:circularSeekBar_innerThumbRadius="5dp"
        app:circularSeekBar_innerThumbStrokeWidth="3dp"
        app:circularSeekBar_min="0"
        app:circularSeekBar_outerThumbRadius="5dp"
        app:circularSeekBar_outerThumbStrokeWidth="10dp"
        app:circularSeekBar_progressGradientColors="@array/rainbow"
        app:circularSeekBar_startAngle="45"
        app:circularSeekBar_dashWidth="1"
        app:circularSeekBar_dashGap="2"
        app:circularSeekBar_sweepAngle="270"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### 4. Listen to the progress changed.

### Xml code:
If you want to listen for value changes, implement the interface `setOnProgressChangedListener` or `setOnAnimationEndListener`.
```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            circularSeekBar.setOnProgressChangedListener { progress ->
                tvProgressValue.text = progress
                    .roundToInt()
                    .toString()
            }

            circularSeekBar.setOnAnimationEndListener { _ ->
                // listen
            }
        }
    }
}

```

### 5. Animations

<img src="https://github.com/seosh817/CircularSeekBar/blob/hotfix/1.0.3/images/animations_bounceOut.gif?raw=true" width="300"/>

### Xml code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.seosh817.circularseekbar.CircularSeekBar
        android:id="@+id/circular_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:circularSeekBar_animation="bounce"
        app:circularSeekBar_animationDurationMillis="1000"
        app:circularSeekBar_barStrokeCap="butt"
        app:circularSeekBar_barWidth="8dp"
        app:circularSeekBar_innerThumbRadius="5dp"
        app:circularSeekBar_innerThumbStrokeWidth="3dp"
        app:circularSeekBar_min="0"
        app:circularSeekBar_outerThumbRadius="5dp"
        app:circularSeekBar_outerThumbStrokeWidth="10dp"
        app:circularSeekBar_progressGradientColors="@array/rainbow"
        app:circularSeekBar_startAngle="45"
        app:circularSeekBar_dashWidth="1"
        app:circularSeekBar_dashGap="2"
        app:circularSeekBar_sweepAngle="270"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Kotlin code:

If you want to change the SeekBar's animation, implement the CircularSeekBar Animation properties.

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
    
            circularSeekBar.circularSeekBarAnimation = CircularSeekBarAnimation.BOUNCE // NORMAL, BOUNCE, DECELERATE, ACCELERATE_DECELERATE
            circularSeekBar.animationDurationMillis = 1000
        }
    }
}
```

If you want to apply a custom animation, implement the animationInterpolator property.

```kotlin
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {

      circularSeekBar.animationInterpolator = AccelerateInterpolator()
      circularSeekBar.animationDurationMillis = 1000
    }
  }
}
```

# Installing

### Gradle

Add ```mavenCentral()``` to your project build.gradle file.

```
allprojects {
    repositories {
        mavenCentral()
    }
}
```
Then, add dependency to your module's build.gradle file.

```
dependencies {
    implementation "com.seosh817:circularseekbar:1.0.0"
}
```

## Properties

You can customize the CircularSeekBar using the following properties:

|Property|Type|Default|Description|
|:---|:---|:---|:---|
| progress | `Float` | 0 | Current value of seek bar. |
| min | `Float` | 0 | Minimum value of seek bar.|
| max | `Float` | 100 | Maximum value of seek bar.|
| startAngle | `Float` | 0 | The Angle to start drawing this seek bar from.|
| sweepAngle | `Float` | 360 | The Angle through which to draw the seek bar.|
| barWidth | `double` | 6 | The thickness of the seek bar.|
| trackColor | `Color` | Color.LTGRAY | Background track color of seek bar.|
| trackGradientColors | `IntArray` | intArrayOf() | Background track gradient colors of seek bar.<br>If [trackGradientColors] is not empty, [trackColor] is not applied.|
| progressColor | `Color` | Color.parseColor("#FF189BFA") | Foreground progress color of seek bar.|
| progressGradientColors | `IntArray` | intArrayOf() | Foreground progressGradientColors of seek bar.<br>If [progressGradientColors] is not empty, [progressColor] is not applied.|
| strokeCap | `BarStrokeCap` | BarStrokeCap.ROUND | Styles to use for arcs endings.|
| showAnimation | `Boolean` | true | Active seek bar animation.|
| circularSeekBarAnimation | `CircularSeekBarAnimation` | CircularSeekBarAnimation.BOUNCE | Animation of [CircularSeekBar].|
| animDurationMillis | `Int` | 1000 | Animation duration milliseconds.|
| innerThumbRadius | `Float` | 0 | The radius of the [CircularSeekBar] inner thumb.|
| innerThumbStrokeWidth | `Float` | 0 | The stroke width of the [CircularSeekBar] inner thumb.|
| innerThumbColor | `Color` | Color.parseColor("#FF189BFA") | Color of the [CircularSeekBar] inner thumb.|
| innerThumbStyle | `ThumbStyle` | ThumbStyle.FILL_AND_STROKE | Style of the [CircularSeekBar] of inner thumb.|
| outerThumbRadius | `Float` | 0 | The radius of the [CircularSeekBar] outer thumb.|
| outerThumbStrokeWidth | `Float` | 0 | The stroke width of the [CircularSeekBar] outer thumb.|
| outerThumbColor | `Color` | Color.WHITE | Color of the [CircularSeekBar] outer thumb.|
| outerThumbStyle | `ThumbStyle` | ThumbStyle.FILL_AND_STROKE | Style of the [CircularSeekBar] of outer thumb.|
| dashWidth | `Float` | 0 | Dash width of [CircularSeekBar].|
| dashGap | `Float` | 0 |  Dash gap of [CircularSeekBar].|
| interactive | `Boolean` | true | Set to true if you want to interact with TapDown to change the seekbar's progress.|

# License
```
MIT License

Copyright (c) 2022 seosh817

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

# Contribution
Feel free to file an [issue](https://github.com/seosh817/CircularSeekBar/issues) if you find a problem or make [pull requests](https://github.com/seosh817/CircularSeekBar/pulls).<br>
All contributions are welcome 😁

