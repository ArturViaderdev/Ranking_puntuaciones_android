package com.stucom.arturvex1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QuantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quant);

        ImageButton bcamera = findViewById(R.id.bmaps);
        //Event del butó de imatge per a anar al mapa
        bcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrem un mapa amb la posició de Stucom centrada i marcada.
                Uri gmmIntentUri = Uri.parse("geo:41.3857089,2.1651619?q=41.3857089,2.1651619(Stucom)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }

        }
        );
    }
}
