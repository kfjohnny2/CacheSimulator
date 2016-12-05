package com.example.johnnylee.cachesimulator;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnnylee.cachesimulator.dto.Config;
import com.example.johnnylee.cachesimulator.dto.Wrapper;
import com.example.johnnylee.cachesimulator.dto.commands.Read;
import com.example.johnnylee.cachesimulator.dto.commands.Write;
import com.example.johnnylee.cachesimulator.model.memory.Cache;
import com.example.johnnylee.cachesimulator.model.memory.Main;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_CONFIG_FILE = 0;
    public static Config configGlobal;
    private Main mainMemory;
    private Cache cacheMemory;
    private EditText edLog;
    private Dialog dialog;
    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select the configuration file"), RESULT_CONFIG_FILE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No file manager found", Toast.LENGTH_LONG).show();
        }

        edLog = (EditText) findViewById(R.id.edmLog);
//        cacheMemory.write();
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        fab.addButton(new AddFloatingActionButton(getApplicationContext()));
        fab.addButton(new AddFloatingActionButton(getApplicationContext()));
        View fabRead = fab.getChildAt(0);
        View fabWrite = fab.getChildAt(1);
        fabRead.setBackgroundResource(R.drawable.ic_action_read);
        fabWrite.setBackgroundResource(R.drawable.ic_action_write);
        fabRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setTitle("READ MEMORY");
                dialog.setContentView(R.layout.dialog_read_memory);
                Button btCreate = (Button) dialog.findViewById(R.id.btRead);
                final EditText edAdress = (EditText) dialog.findViewById(R.id.edAdress);
                final EditText edValue = (EditText) dialog.findViewById(R.id.edValue);
                edValue.setVisibility(View.GONE);
                btCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edLog.setText("");
                        edLog.setText(wrapper.read(new Read(Integer.parseInt(edAdress.getText().toString()))));
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
                final EditText edAdress = (EditText) dialog.findViewById(R.id.edAdress);
                final EditText edValue = (EditText) dialog.findViewById(R.id.edValue);
                btCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrapper.write(new Write(Integer.parseInt(edAdress.getText().toString()),
                                edValue.getText().toString()));
                        edLog.setText("");
                        edLog.setText(wrapper.show());
                    }
                });
                dialog.show();
            }
        });

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
}
