package com.krishna.invisivault;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button btnShowList,btnVideos,btnrecordings,btnNotes,btnMessage,btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowList = (Button)findViewById(R.id.btnShowList);

        btnVideos = (Button)findViewById(R.id.btnVideos);
        btnrecordings = (Button)findViewById(R.id.btnrecordings);
        btnNotes = (Button)findViewById(R.id.btnNotes);
        btnMessage = (Button)findViewById(R.id.btnMessage);
        btnSettings = (Button)findViewById(R.id.btnSettings);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(MainActivity.this,MultiPhotoSelectActivity.class);
                startActivity(intent);*/
            }
        });

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });
    }


}
