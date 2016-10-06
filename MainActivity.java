package yanbal.com.pe.botonesvoz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "yanbal.com.pe.botones.MESSAGE";
    public static final int REQUEST_CODE= 1234;
    private Dialog match_text_dialog;
    private ListView textlist;
    private ArrayList<String> matches_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void speakButtonClicked(View v){ startVoiceRecognitionActivity();}

    public void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué desea visualizar?");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            match_text_dialog = new Dialog(MainActivity.this);
            match_text_dialog.setContentView(R.layout.flotante);
            match_text_dialog.setTitle("¿Qué dijiste?");
            textlist = (ListView)match_text_dialog.findViewById(R.id.listResultado);
            matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //aca poner la validacion de la voz
            identOpcion();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);
            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //resultado.setText(matches_text.get(position));
                    match_text_dialog.hide();
                }
                                            }
            );
            match_text_dialog.show();
        }
    }

    public void irGrupoPersonal(View view){
        Intent intent = new Intent(this, GrupoPersonal.class);
        intent.putExtra(EXTRA_MESSAGE, "Grupo Personal");
        startActivity(intent);
    }


    public void irCampaniaActual(View view){
        Intent intent = new Intent(this, CampaniaActual.class);
        intent.putExtra(EXTRA_MESSAGE, "Campaña Actual");
        startActivity(intent);
    }

    public void irDesarrolloPersonal(View view){
        Intent intent = new Intent(this, DesarrolloPersonal.class);
        intent.putExtra(EXTRA_MESSAGE, "Desarrollo Personal");
        startActivity(intent);
    }

    public void irReunioNucleo(View view){
        Intent intent = new Intent(this, ReunionNucleo.class);
        intent.putExtra(EXTRA_MESSAGE, "Reunión de Nucleo");
        startActivity(intent);
    }

    public void identOpcion(){
        //Falta poner las validaciones de todo
        if(matches_text.get(0).equalsIgnoreCase("campaña")){
            Intent intent = new Intent(this, CampaniaActual.class);
            intent.putExtra(EXTRA_MESSAGE, "Campaña Actual");
            startActivity(intent);
        }

        else if (matches_text.get(0).equalsIgnoreCase("desarrollo")){
            Intent intent = new Intent(this, DesarrolloPersonal.class);
            intent.putExtra(EXTRA_MESSAGE, "Desarrollo Personal");
            startActivity(intent);
        }

        else if (matches_text.get(0).equalsIgnoreCase("grupo")){
                Intent intent = new Intent(this, GrupoPersonal.class);
            intent.putExtra(EXTRA_MESSAGE, "Grupo Personal");
            startActivity(intent);
        }

        else if (matches_text.get(0).equalsIgnoreCase("núcleo")){
            Intent intent = new Intent(this, ReunionNucleo.class);
            intent.putExtra(EXTRA_MESSAGE, "Reunión de Nucleo");
            startActivity(intent);
        }

    }

}
