package com.stucom.arturvex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //S'executa al crear la pantalla
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A continuació els events del butons del menú que mostren altres activitats.
        Button butoplay = findViewById(R.id.btnplay);
        butoplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,PlayActivity.class);
                startActivity(intent);
            }
        });

        Button butoranking = findViewById(R.id.btnranking);
        butoranking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RankingActivity.class);
                startActivity(intent);
            }
        });

        Button butoquant = findViewById(R.id.btnquant);
        butoquant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuantActivity.class);
                startActivity(intent);
            }
        });

        Button butosettings = findViewById(R.id.btnsettings);
        butosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
