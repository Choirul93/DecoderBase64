package com.arif.laroon;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by choirularifin on 24/01/2018.
 */

public class DecoderBase64 extends AsyncTask<String,Void,File> {
    private static final String TAG = DecoderBase64.class.getSimpleName();
    private ActivityHandler handler;

    public DecoderBase64(ActivityHandler handler){
        this.handler = handler;
    }

    public interface ActivityHandler{
        public void onFinishEncodeing(File file);
    }

    private void decodeFile(File filePath, String data)
    {
        if(filePath != null)
        {
            try
            {
                byte[] decodedData = Base64.decode(data.getBytes(), Base64.DEFAULT);
                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(decodedData);
                fos.close();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error decoding", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected File doInBackground(String... params) {
        File filePath = null;
        String data = null;
        if(params != null && params.length == 3)
        {
            filePath = new File(params[2]);
            data = params[0];
        }
        decodeFile(filePath, data);
        return filePath;
    }

    @Override
    protected void onPostExecute(File file) {
       if(handler !=null){
           handler.onFinishEncodeing(file);
       }
    }
}
