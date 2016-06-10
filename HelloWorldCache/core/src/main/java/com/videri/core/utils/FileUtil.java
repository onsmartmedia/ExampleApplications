package com.videri.core.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.channels.FileLockInterruptionException;

/**
 * Created by yiminglin on 5/19/16.
 */
public class FileUtil {


    private static final  String TAG = "Core_FileUtil";

    public static void createDirectory(String root){

        File rootFolder = new File(root );
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }

    }

    public static void writeFile(String root,String fileName,String data){
        try{
            File rootFolder = new File(root );
            createDirectory(root);
            File gpxfile = new File(rootFolder,fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();

        }
        catch(IOException e)
        {
            Log.e(TAG, "Write file Error: " + e.toString());
        }
    }


    public static String readFile(String path, String fileName){
        String data ="";
        try{
            File myFile = new File(path + "/" + fileName);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow;
            }

            myReader.close();
            data = aBuffer.toString();
        }
        catch (Exception e){
            Log.e(TAG, "Read file Error: " + e.toString());
        }

        return data;
    }


    public static boolean isFileExists(String path, String fileName){
        Log.v(TAG, path + "/" + fileName);
        File file = new File(path+"/"+fileName);
        if(file.exists())
            return true;
        return false;
    }


    public static Object loadSerializedObject(String path,String fileName){
       return loadSerializedObject(path + "/" + fileName);
    }
    public static Object loadSerializedObject(String path){
        return loadSerializedObject(new File(path ));
    }



    public static Object loadSerializedObject(File f)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            return o;
        }
        catch (FileLockInterruptionException e){

            Log.v(TAG,"FileLockInterruptionException: " + e.toString());

            return null;
        }
        catch(Exception ex)
        {
            Log.v(TAG, "Error:" + ex.toString());

            return null;
        }
    }


}
