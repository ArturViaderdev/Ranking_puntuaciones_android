package com.stucom.arturvex1;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    //Constants que indiquen nombre de operació al cridar intents externs
    static final int AGAFAIMATGE = 1;
    static final int AGAFACAMERA = 2;

    //Camps de text
    EditText tnom;
    EditText tmail;
    //Camp de imatge
    ImageView ifoto;
    //Variable que recorda si s'està sel.leccionant una foto per anul.lar un onpause.
    //boolean fentfoto=false;
    //Recull la url de la imatge quan es pren una foto amb la camera
    Uri camerauri = null;

    //Aquest mètode s'executa quan s'acaben d'executar intents externs como el de la càmera i la galeria.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Si es rep un resultat de la pantalla de triar una imatge de la galeria
        if (requestCode == AGAFAIMATGE) {
            if(resultCode== RESULT_OK)
            {
                //s'obté la uri de la imatge
                Uri imatgeuri = data.getData();

                /*
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ifoto.setImageBitmap(bitmap);
                    fentfoto=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

                //Es guarda la ruta de la imatge a shared preferences
                if(imatgeuri!=null)
                {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor ed = prefs.edit();
                    String imatge = imatgeuri.toString();
                    ed.putString("fotouri",imatge);
                    ed.commit();
                }

                //String simatge = BitMapToString(((BitmapDrawable)ifoto.getDrawable()).getBitmap());
                //ifoto.setImageURI(imatgeuri);
            }
        }
        //Si es rep un resultat de la pantalla de fer una foto amb la càmera.
        else if(requestCode == AGAFACAMERA)
        {
           /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ifoto.setImageBitmap(imageBitmap);*/
          // obteimatge();
            if(resultCode== RESULT_OK)
            {
                //Es guarda la uri de la imatge al shared preferences
                if(camerauri!=null)
                {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor ed = prefs.edit();
                    String imatge = camerauri.toString();
                    ed.putString("fotouri",imatge);
                    ed.commit();
                }
               // try {
                    //Es llegeix el bitmap de la imatge a partir de la uri
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imatgeuri);
                    //Es posa el bitmap de la imatge perque es vegi
                    //ifoto.setImageBitmap(bitmap);
                    //fentfoto=true;
               // } catch (IOException e) {
                 //   e.printStackTrace();
               // }
            }
        }
    }

    //S'executa al inici
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Es carreguen els camps
        tnom = findViewById(R.id.tname);
        tmail = findViewById(R.id.tmail);
        ifoto = findViewById(R.id.ifoto);
        //Event del butó de fer foto amb càmera
        Button bcamera = findViewById(R.id.bcamera);
        bcamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Creem un intent de càmera
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Li donem permisos
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    try {
                        //Es crea un nom d'arxiu temporal
                        File arxiu = createImageFile();
                        //Es crea una uri amb la adreça del arxiu
                        camerauri = Uri.fromFile(arxiu);
                        //S'esborra l'arxiu si existía
                        arxiu.delete();
                        //Li posem al intent de càmera la uri perque grabi en aquest arxiu
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,camerauri);
                        //Iniciem la càmera
                        //fentfoto = true;
                        startActivityForResult(takePictureIntent, AGAFACAMERA);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            }
        );
        //Event del butó de boto de la galería
        Button butofoto = findViewById(R.id.bfoto);
        butofoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Fem un intent per a sel.leccionar foto de la galería i l'executem
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                //fentfoto = true;
                startActivityForResult(Intent.createChooser(intent, "@string/pickima"), AGAFAIMATGE);
            }

        }
        );
    }

    //Crea una adreça de arxiu temporal
    private File createImageFile() throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String imageFileName = "foto";
        File image;
       // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

          //  File storageDir = getCacheDir();
        File storageDir = this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStorageDirectory();
            image = File.createTempFile(

                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );



        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    //Al tornar a activar la activity
    @Override
    public void onResume()
    {
        super.onResume();
        //Carregar les dades
            //llegeixo de sharedpreferences el nom, email i foto.
            //SharedPreferences prefs = getSharedPreferences(getPackageName(),MODE_PRIVATE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String name = prefs.getString("name","");
            String mail = prefs.getString("email","");
            String foto = prefs.getString("fotouri",null);
            //Poso les dades als camps
            tnom.setText(name);
            tmail.setText(mail);

            if(foto!=null) {
                //Carrego la foto a partir de la uri guardada a sharedpreferences
                //ifoto.setImageBitmap(StringToBitMap(foto));
                Uri imatgeuri = Uri.parse(foto);
                ifoto.setImageURI(imatgeuri);
            }
    }

    //Converteix una imatge bitmap en un string, no s'utilitza
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    //Converteix un string en una imatge bitmap, no s'utilitza
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    //Al pausar la activity
    @Override
    public void onPause()
    {
        super.onPause();
        //if(!fentfoto)
        //{
            //Es guarden les dades a sharedpreferences
            String name = tnom.getText().toString();
            String mail = tmail.getText().toString();
            //Passo la imatge a string
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            //SharedPreferences prefs = getSharedPreferences(getPackageName(),MODE_PRIVATE);
            SharedPreferences.Editor ed = prefs.edit();
            //String simatge = BitMapToString(((BitmapDrawable)ifoto.getDrawable()).getBitmap());
            ed.putString("name",name);
            ed.putString("email",mail);
            ed.commit();
        //}
        //else
        //{
            //fentfoto = false;
        //}
    }
}
