# FabTextView
An fab that transform into textview..


<img src="https://cloud.githubusercontent.com/assets/1988156/19845683/6bdf2900-9f7c-11e6-88b9-2ba57d2dfdc3.gif" width="250px"/>

### Install

**Step 1**
```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

**Step 2**

Add this to your `app/build.gradle`.

If you don't want to include the dependency (`com.android.support:appcompat-v7`, `com.android.support:design`) from this project, use this:

```
compile('com.github.worker8:FabTextView:1.0.2') {
    transitive = false
}
```

Otherwise, use this:


```
compile 'com.github.worker8:FabTextView:1.0.2'
```

### Usage

In xml:

```
<beepbeep.fabtextview.FabTextView
        android:id="@+id/fab_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_marginBottom="20dp"
        android:layout_marginEnd="16dp"
        app:fab_backgroundColor="@color/colorPrimaryDark"
        app:fab_diameter="55dp"
        app:fab_iconSrc="@mipmap/ic_launcher"
        app:fab_text="Android Robot"
        app:fab_textSize="20sp" />
```

To control the open and closing:

 - `fabTextView.toggle()`: call `shrink()` if expanded, call `expand()` if shrunk
 - `fabTextView.shrink()`: shrink to FAB
 - `fabTextView.expand()`: expand from FAB to the long button

# License

    The MIT License (MIT)
    
    Copyright (c) 2016 Tan Jun Rong
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
