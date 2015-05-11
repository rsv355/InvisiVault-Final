package com.krishna.invisivault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Android on 11-05-2015.
 */
public class HomeActivity extends Activity implements View.OnClickListener{
    Button btnShowList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        btnShowList = (Button)findViewById(R.id.btnShowList);
        btnShowList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShowList:
                Intent i= new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
                break;
        }
    }


    // end of main class
}
