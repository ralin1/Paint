package com.example.oleksandrfilippov.paint_filippov_cw5;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by oleksandrfilippov on 11.05.2018.
 */

public class MainActivity extends AppCompatActivity {

    //private Rysunek powierzchnia;
    private Button colorRed;
    private Button colorYellow;
    private Button colorBlue;
    private Button colorGreen;
    private Button colorBlack;
    private Button Czysc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorRed = (Button) findViewById(R.id.red);
        colorYellow = (Button) findViewById(R.id.yellow);
        colorBlue = (Button) findViewById(R.id.blue);
        colorGreen = (Button) findViewById(R.id.green);
        colorBlack = (Button) findViewById(R.id.black);
        Czysc = (Button) findViewById(R.id.clear);

        colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.farba.setColor(Color.RED);
            }
        });
        colorYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.farba.setColor(Color.YELLOW);
            }
        });
        colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.farba.setColor(Color.BLUE);
            }
        });
        colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.farba.setColor(Color.GREEN);
            }
        });
        colorBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.farba.setColor(Color.BLACK);
            }
        });

        Czysc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rysunek.czysc();
            }
        });
    }
}
