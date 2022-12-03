package com.example.ia08;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private DoodleView doodleView;
    private ImageButton currentColor, strokeWeight, opacity, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doodleView = findViewById(R.id.canvas);
        currentColor = findViewById(R.id.black);

        strokeWeight = findViewById(R.id.brushSize);
        opacity = findViewById(R.id.opacity);
        clear = findViewById(R.id.clear);
    }

    public void clear(View v) {
        doodleView.clear();
    }

    public void changeColor(View v){
        String color = (String) v.getTag();
        doodleView.setColor(color);
    }

    public void changeStrokeWeight(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.stroke_menu);
        popupMenu.show();
    }

    public void changeOpacity(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.opacity_menu);
        popupMenu.show();
    }

    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()) {
            case R.id.small:
                doodleView.setStrokeWeight(5);
                return true;
            case R.id.medium:
                doodleView.setStrokeWeight(50);
                return true;
            case R.id.large:
                doodleView.setStrokeWeight(100);
                return true;
            case R.id.opacity50:
                doodleView.setOpacity(0xA);
                return true;
            case R.id.opacity75:
                doodleView.setOpacity(0x14);
                return true;
            case R.id.opacity100:
                doodleView.setOpacity(0xFF);
                return true;
            default:
                return false;
        }
    }
}