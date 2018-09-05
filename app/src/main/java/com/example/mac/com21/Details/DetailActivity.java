package com.example.mac.com21.Details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mac.com21.Pojos.Chiste;
import com.example.mac.com21.R;
import com.example.mac.com21.speack.SpeakRequest;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "sv.edu.udb.id";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private static String IMAGE_PREFIX = "http://34.211.243.185:8080/images/";


    Chiste chiste;
    ImageView imagenExtendida;
    SpeakRequest speakRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        chiste = (Chiste) getIntent().getSerializableExtra(EXTRA_PARAM_ID);

        //imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);

        //cargarContenido();

        if(speakRequest == null) {
            speakRequest = new SpeakRequest(this);
        }

    }


    private void cargarContenido() {

        Glide.with(imagenExtendida.getContext())
                .load(IMAGE_PREFIX+chiste.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(imagenExtendida);



    }




    @Override
    protected void onPause() {
        speakRequest.onDestroy();
        super.onPause();
    }

    public void hablar(View view) {
        if(speakRequest.isSpeaking()){
            speakRequest.stopSpeak();
        }
        speakRequest.speak(chiste.getTexto());

    }


}