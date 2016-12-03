package com.example.johnnylee.cachesimulator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johnnylee.cachesimulator.dto.Config;
import com.example.johnnylee.cachesimulator.model.Line;
import com.example.johnnylee.cachesimulator.model.memory.Cache;
import com.example.johnnylee.cachesimulator.model.memory.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    
    private static final int RESULT_CONFIG_FILE = 0;
    public static Config configGlobal;
    private Main mainMemory;
    private Cache cacheMemory;
    private ListView listView;

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = (ListView) findViewById(R.id.listMemories);
    }

    private String[] printCurrentMemoryStatus(int linesCount){
        String[] memoryListing = new  String[linesCount];
        StringBuilder sCache = new StringBuilder("Cache Memory: \n");
        for(int i = 0; i < linesCount; i++){
            Line line = cacheMemory.getLineAtPosition(i);
            memoryListing = new String[cacheMemory.getLineAmount()];
            sCache.append(line.getId()).append("-")
                    .append(line.getBlock().getId())
                    .append("-");
            for(int j = 0; i < cacheMemory.getLineAtPosition(i).getBlock().getWords().length; j++){
                sCache.append(cacheMemory.getLineAtPosition(i).getBlock().getWordAtPosition(j).getAdress())
                        .append("-").append(cacheMemory.getLineAtPosition(i).getBlock().getWordAtPosition(j).getContent()+"\n");
            }
            memoryListing[i] = sCache.toString();
        }
        return memoryListing;
    }

    private Config readConfig(Uri dataUri) throws IOException {
        FileReader fw = new FileReader(dataUri.getPath());
        BufferedReader in = new BufferedReader(fw);
        String line ;
        int i = 0;
        int[] configInfo = new int[7];
        while((line = in.readLine()) != null && i < 6) {
            configInfo[i] = Integer.parseInt(line);
            i++;
        }
        in.close();
        Config config = new Config(configInfo[0], configInfo[1], configInfo[2], configInfo[3], configInfo[4], configInfo[5], configInfo[6]);
        return config;
    }

    private void initMemories(){
        cacheMemory = new Cache(configGlobal.getBlockSize(), false, false, 0, configGlobal.getCacheLineSize());
        mainMemory = new Main(configGlobal.getBlockSize(), false, false, 0);
//        for (int i = 0; i < cacheMemory.getLineAmount(); i++)
//            cacheMemory.getLineAtPosition(i).getBlock().setWordAt(i, "0");
//        for (int i = 0; i < mainMemory.getBlockAmount(); i++)
//            mainMemory.getBlockAtPosition(i).setWordAt(i, "0");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RESULT_CONFIG_FILE:
                try {
                    configGlobal = readConfig(data.getData());
                    initMemories();
                    String[] lines = printCurrentMemoryStatus(cacheMemory.getLineAmount());

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
