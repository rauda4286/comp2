package com.example.mac.com21.speack;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class SpeakRequest implements TextToSpeech.OnInitListener{

    TextToSpeech tts ;
    Locale spanish = new Locale("es", "ES");
    Locale english = new Locale("us", "US");
    Context context;


    public SpeakRequest(Context context){
        this.context = context;
        tts = new TextToSpeech(context,this);
    }

    @Override
    public void onInit(int status) {

        if(status == TextToSpeech.SUCCESS){
            int result=tts.setLanguage(spanish);
            if(result== TextToSpeech.LANG_MISSING_DATA ||
                    result== TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("error", "This Language is not supported");
            }
        }
        else
            Log.e("error", "Initilization Failed!");
    }

    public void speak(final String text) {
        if(text==null||"".equals(text))
        {
            String errorText = "No existe contenido asociado";
            tts.speak(errorText, TextToSpeech.QUEUE_FLUSH, null);
        }else {

            while(isSpeaking()){
                //Don't do anything
            }

            new Thread(new Runnable() {

                @Override
                public void run() {
                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                }
            }).start();
        }
    }

    public void speak(final String[] lines){

        String text="";

        for (String tmp : lines) {
            text += tmp+".";
        }


        speak(text);

    }

    public void speak(ArrayList<String> lines){
        speak((String[])lines.toArray());
    }

    public void stopSpeak(){
        tts.stop();
    }

    public boolean isSpeaking(){
        return this.tts.isSpeaking();
    }



    public void onDestroy(){

        if(tts!=null) {
            tts.stop();
            tts.shutdown();
        }

    }

}


