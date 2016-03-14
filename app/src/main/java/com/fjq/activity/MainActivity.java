package com.fjq.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fjq.layout.Game2048Layout;
import com.fjq.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity implements Game2048Layout.OnGame2048Listener {
    public static final String FILE_NAME = "Game_2048";
    public static final String KEY_BESTSCORE="bestScore";
    private Game2048Layout game2048Layout;
    private TextView mScore;
    private TextView mBestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        Init();
        setListener();
    }

    /*界面的初始化工作*/
    private void Init() {
     }

    private void load(){
        String bestScore = (String)SharedPreferencesUtil.get(MainActivity.this, FILE_NAME,KEY_BESTSCORE,"0");
        if(bestScore!=null){
            mBestScore.setText(bestScore);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        String bestScore = mBestScore.getText().toString();
        SharedPreferencesUtil.put(this,FILE_NAME,KEY_BESTSCORE,bestScore);
    }

    @Override
    protected void onStart() {
        load();
        super.onStart();
    }

    /*为控件设置事件监听*/
    private void setListener() {
        game2048Layout.setOnGame2048Listener(this);
    }

    /*实例化布局文件的控件*/
    private void findViewById() {
        game2048Layout = (Game2048Layout) findViewById(R.id.id_game2048);
        mScore = (TextView) findViewById(R.id.id_score);
        mBestScore = (TextView) findViewById(R.id.best_score);
    }

    @Override
    public void onGameOver() {
        new AlertDialog.Builder(this).setTitle("GAME OVER")
                .setMessage("YOU HAVE GOT " + mScore.getText())
                .setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game2048Layout.restart();
                    }
                }).setNegativeButton("EXIT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onScoreChange(int score) {
        mScore.setText(Integer.toString(score));
        String bestScore = mBestScore.getText().toString();
        if(score>Integer.parseInt(bestScore)){
            mBestScore.setText(Integer.toString(score));
        }
    }


}
