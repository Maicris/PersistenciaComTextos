package br.com.appdev.persistenciacomtextos;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private AssetManager assetManager;
    private EditText txtNome, txtCidade, txtEmail;
    private CheckBox chkPref;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetManager = this.getAssets();
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtCidade = (EditText) findViewById(R.id.txtCidade);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        chkPref = (CheckBox) findViewById(R.id.chkPref);
        preferences = getSharedPreferences(
                "persistenciacomtextos_preferences", MODE_PRIVATE);
        editor = preferences.edit();

        int chkValue = preferences.getInt("chkpref", 0);
        if (chkValue > 0)
            chkPref.setChecked(true);
    }

    public void btnAssetOnClick(View view) {
        try {
            InputStream inputStream = assetManager.open("textInit.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            txtNome.setText(reader.readLine());
            txtCidade.setText(reader.readLine());
            txtEmail.setText(reader.readLine());
            reader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnClick(View view) {
        try {
            OutputStream outputStream = openFileOutput("datastore.txt", MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write(txtNome.getText().toString() + "\n");
            writer.write(txtCidade.getText().toString() + "\n");
            writer.write(txtEmail.getText().toString());
            writer.flush();
            writer.close();
            outputStreamWriter.close();
            outputStream.close();
            txtNome.setText("");
            txtCidade.setText("");
            txtEmail.setText("");
            Toast.makeText(this, "Dados salvos com sucesso!!!", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnMemoryOnClick(View view) {
        try {
            InputStream inputStream = openFileInput("datastore.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            txtNome.setText(reader.readLine());
            txtCidade.setText(reader.readLine());
            txtEmail.setText(reader.readLine());
            reader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chkSaveOnClick(View view) {

        if (chkPref.isChecked())
            editor.putInt("chkpref", 1);
        else
            editor.putInt("chkpref", 0);
        editor.commit();
    }
}
