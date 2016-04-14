package com.funny.addworddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyTextView text;
    private float alpha = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        text = (MyTextView) findViewById(R.id.text);
        findViewById(R.id.black_background).setOnClickListener(this);
        findViewById(R.id.white_background).setOnClickListener(this);
        findViewById(R.id.less_alpha).setOnClickListener(this);
        findViewById(R.id.add_alpha).setOnClickListener(this);
        findViewById(R.id.have_shadow).setOnClickListener(this);
        findViewById(R.id.no_shadow).setOnClickListener(this);
        findViewById(R.id.have_Stroke).setOnClickListener(this);
        findViewById(R.id.no_Stroke).setOnClickListener(this);
        findViewById(R.id.have_background).setOnClickListener(this);
        findViewById(R.id.heng).setOnClickListener(this);
        findViewById(R.id.shu).setOnClickListener(this);
    }

    private void initData() {
        text.setText("靳广越\n靳广越靳广越\n我要飞");
        text.setTextSize(30);
        text.setRotation(90);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.black_background:
                text.setTextColor(Color.BLACK);
                break;

            case R.id.white_background:
                text.setTextColor(Color.WHITE);
                break;

            case R.id.less_alpha:
                if (alpha > 0) {
                    alpha = alpha - 0.2f;
                    text.setAlpha(alpha);
                }
                break;

            case R.id.add_alpha:
                if (alpha < 1) {
                    alpha = alpha + 0.2f;
                    text.setAlpha(alpha);
                }
                break;

            case R.id.have_shadow:
                text.setShadowLayer(5F, 3F, 3F, Color.parseColor("#aaaaaa"));
                break;

            case R.id.no_shadow:
                text.setShadowLayer(0F, 0F, 0F, Color.TRANSPARENT);
                break;

            case R.id.have_Stroke:
                if (text.getCurrentTextColor() == Color.BLACK) {
                    if (text.getBorderColor() == Color.WHITE) {
                        text.setBorderColor(Color.TRANSPARENT);
                    } else {
                        text.setBorderColor(Color.WHITE);
                    }
                } else {
                    if (text.getBorderColor() == Color.BLACK) {
                        text.setBorderColor(Color.TRANSPARENT);
                    } else {
                        text.setBorderColor(Color.BLACK);
                    }

                }
                break;

            case R.id.no_Stroke:
                text.setBorderColor(Color.TRANSPARENT);
                break;

            case R.id.have_background:
                if (text.getCurrentTextColor() == Color.BLACK) {
                    text.setBackgroundColor(Color.WHITE);
                } else {
                    text.setBackgroundColor(Color.BLACK);
                }
                break;

            case R.id.heng:
                text.setIsheng(true);
               /* text.setFirst(false);
                if(!text.isheng()){
                    text.setIsheng(true);
                    text.setRotate(90);
                }*/
                break;

            case R.id.shu:
                /*String s = text.getText().toString();
                StringBuffer str = new StringBuffer(s);
                for(int i=2; i<s.length(); i+=3){
                    str.insert(i, '\n');
                }
                Log.e("yue", str.toString());*/
                /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(text.getTextSize() * text.getLineCount()), ViewGroup.LayoutParams.WRAP_CONTENT);
                text.setLayoutParams(params);*/

                text.setIsheng(false);
                /*
                text.setFirst(false);
                if(text.isheng()){
                    text.setIsheng(false);
                    text.setRotate(-90);
                }*/

                break;

        }
    }
}
















