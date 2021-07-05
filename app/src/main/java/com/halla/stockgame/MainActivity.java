package com.halla.stockgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button StartBtn;
    Button HelpBtn;
    Button QuitBtn;
    Button HaveNoSidemoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "SeedMoney", null, 1);

        SharedPreferences pref = getSharedPreferences("checkFirst",MainActivity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst",true);


        if(checkFirst == true) { // 어플리케이션이 최초로 실행되었을 경우에만 수행하는 if문

            SharedPreferences.Editor editor2 = pref.edit();
            dbHelper.insert("MainUser",10000);
            editor2.putBoolean("checkFirst", false);
            editor2.commit();

        }



        Log.d("실험123", String.valueOf(checkFirst));
        Log.d("실험123123", "onCreate: " + dbHelper.getResult());


        HaveNoSidemoney = (Button) findViewById(R.id.HaveNoSidemoney);
        HaveNoSidemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.update("MainUser",10000);
                Log.d("시드머니 충전버튼", "시드머니 충전완료");

            }
        });

        StartBtn = (Button) findViewById(R.id.StartBtn);
            StartBtn.setOnClickListener(new View.OnClickListener() { // 게임시작 버튼 . 주가정보 나오는 엑티비티로 이동
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), Stockinfo.class);
                    startActivity(intent);

                }
            });


            HelpBtn = (Button) findViewById(R.id.HelpBtn);
            HelpBtn.setOnClickListener(new View.OnClickListener() { //도움말 버튼
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("모의 주식 투자");
                    dlg.setMessage("가상의 종목들을 활용해, 주식 투자를 체험해보세요!" + "\n"+"투자금액은 만원으로 모두 동일해요"+"\n"+"투자금을 모두 잃었다면, 리필버튼을 클릭해 보세요!");
                    dlg.setIcon(R.drawable.ic_launcher_background);
                    dlg.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"게임을 즐겨보세요.",Toast.LENGTH_SHORT).show();
                        }
                    });

                    dlg.show();
                }
            });


            QuitBtn = (Button) findViewById(R.id.QuitBtn);
            QuitBtn.setOnClickListener(new View.OnClickListener() { // 게임종료 버튼
                @Override
                public void onClick(View view) {
                    finishAffinity();
                    System.runFinalization();
                    System.exit(0);
                }
            });



    }

}