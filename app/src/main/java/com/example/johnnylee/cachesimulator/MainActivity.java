package com.example.johnnylee.cachesimulator;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnnylee.cachesimulator.dto.Config;
import com.example.johnnylee.cachesimulator.dto.Wrapper;
import com.example.johnnylee.cachesimulator.dto.commands.Read;
import com.example.johnnylee.cachesimulator.dto.commands.Write;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_CONFIG_FILE = 0;
    public static Config configGlobal;
    private static final String TAG = "MainActivity";
    private EditText edLog;
    private Dialog dialog;
    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        grantStoragePermission();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select the configuration file"), RESULT_CONFIG_FILE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No file manager found", Toast.LENGTH_LONG).show();
        }
        edLog = (EditText) findViewById(R.id.edmLog);
        FloatingActionButton fabRead = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_read);
        FloatingActionButton fabWrite = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_write);

        fabRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setTitle("READ MEMORY");
                dialog.setContentView(R.layout.dialog_read_memory);
                Button btCreate = (Button) dialog.findViewById(R.id.btRead);
                final EditText edAdress = (EditText) dialog.findViewById(R.id.edAdress);
                final EditText edValue = (EditText) dialog.findViewById(R.id.edValue);
                final TextView txtReading = (TextView) dialog.findViewById(R.id.txtReadingLog);
                edValue.setVisibility(View.GONE);
                txtReading.setVisibility(View.VISIBLE);
                btCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtReading.setText("Value at adress '"+ edAdress.getText().toString()+"': " +
                                ""+wrapper.read(new Read(Integer.parseInt(edAdress.getText().toString()))));
                    }
                });
                dialog.show();
            }
        });
        fabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setTitle("WRITE MEMORY");
                dialog.setContentView(R.layout.dialog_read_memory);
                Button btCreate = (Button) dialog.findViewById(R.id.btRead);
                btCreate.setText("Write");
                final EditText edAdress = (EditText) dialog.findViewById(R.id.edAdress);
                final EditText edValue = (EditText) dialog.findViewById(R.id.edValue);
                btCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrapper.write(new Write(Integer.parseInt(edAdress.getText().toString()),
                                edValue.getText().toString()));
                        edLog.setText("");
                        edLog.setText(wrapper.show());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        fab.setEnabled(true);
    }

    private Config readConfig(Uri dataUri) throws IOException {
        FileReader fw = new FileReader(dataUri.getPath());
        BufferedReader in = new BufferedReader(fw);
        String line;
        int i = 0;
        int[] configInfo = new int[7];
        while ((line = in.readLine()) != null && i < 7) {
            configInfo[i] = Integer.parseInt(line);
            i++;
        }
        in.close();
        return new Config(configInfo[0], configInfo[1], configInfo[2], configInfo[3], configInfo[4], configInfo[5], configInfo[6]);
    }

    private void initMemories() {
        wrapper = new Wrapper(configGlobal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CONFIG_FILE:
                try {
                    configGlobal = readConfig(data.getData());
                    initMemories();
                    edLog.setText("");
                    edLog.setText(wrapper.show());
//                    edLog.setText(printCurrentMemoryStatus());
//                    String[] lines = printCurrentMemoryStatus(cacheMemory.getLineAmount());

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public boolean grantStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }

        // permission is automatically granted on sdk < 23 upon installation
        Log.v(TAG, "Permission is granted");
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + " was " + grantResults[0]);
        }
    }
}
