HelloWorld Example Application
===================


This is hello world example application which introduces how to create an basic app run on the VLE environment.

----------

Set Target Api Level 18
-------------
Modify build.gradle file in HelloWorld/app/ directory as following:
```
defaultConfig {
        ......
        minSdkVersion 18
        targetSdkVersion 18
        ......
    }
```

No Splash Full Screen Theme
-------------
Add FullScreenTheme tag in /HelloWorld/app/src/main/res/values/styles.xml as following:
```
<style name="FullScreenTheme" parent="android:Theme">
      <item name="android:windowFullscreen">true</item>
      <item name="android:windowNoTitle">true</item>
      <item name="android:windowContentOverlay">@null</item>
      <item name="android:windowAnimationStyle">@null</item>
</style>
```
Modify/Add theme tag in HelloWorld/app/src/main/AndroidManifest.xml file
```
<application
   ......
   android:theme="@style/FullScreenTheme">
   ......
</application>
```
> **Note:**

> - This is only an example to create a no splash full screen theme. Dependent on the project requirements, you may create different themes.

Sample Code to handle thread
-------------
```
handle all removing/pausing thread in onPause. 
Ex: timer, renter code hereunnable, thread, bus

@Override
protected void onPause() {
    super.onPause();
     if(timer != null)
         timer.cancel();
     if(runnable != null)
         handler.removeCallbacks(runnable);
     if(bus != null)
         bus.unregister(this);
}
```
