HelloWorldThread Example Application
===================
This is thread example application which introduces how to handle threads and use fragments on the VLE environment.


Replacing Fragment
-------------
When this app needs to go to another theme, this is recommended using fragment instead of start a new activity because the VLE schedule cause the app to lose running UI status.
```
 //Ex:
 fragmentTransaction = fragmentManager.beginTransaction();
 fragmentTransaction.replace(layout,fragment);
 fragmentTransaction.commit();       
```
Thread Safe Event Handling
-------------
It is recommended removing threads when the app goes to on pause.
```

//Ex:
/**
 * Remove thread
 */
@Override
public void onPause() {
    super.onPause();
    if(thread != null)
        mHandler.removeCallbacks(thread);
}
/**
 * Start thread
 */
@Override
public void onResume() {
    super.onResume();
    mHandler.post(thread);
}

```
