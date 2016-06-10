package com.videri.core.utils;

/**
 * Created by yiminglin on 5/19/16.
 */
public class CoreUtil {

    public static String GenerateVleArg(String vleCommandAction, String giphyName, String url){
        String arg = vleCommandAction + CoreConstants.C_E + giphyName +
                CoreConstants.OR_BAR + url + CoreConstants.END_0;
        return arg;
    }

}
