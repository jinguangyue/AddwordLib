package com.funny.addworddemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TextVerticalActivity extends AppCompatActivity implements View.OnClickListener {

    private VerticalTextView verticalTextView;
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_vertical);
        initView();
    }

    private void initView() {
        verticalTextView = (VerticalTextView)findViewById(R.id.verticalTextView);
        verticalTextView.setTextColor(Color.BLACK);
        verticalTextView.setTextSize(30);
        verticalTextView.setTextViewOrientation(LinearLayout.HORIZONTAL);
        verticalTextView.setText("我就是\n觉得是一个一个的吧");

        findViewById(R.id.hengButton).setOnClickListener(this);
        findViewById(R.id.shuButton).setOnClickListener(this);
        findViewById(R.id.zuoduiqi).setOnClickListener(this);
        findViewById(R.id.juzhong).setOnClickListener(this);
        findViewById(R.id.youduiqi).setOnClickListener(this);
        edit_text = (EditText)findViewById(R.id.edit_text);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                verticalTextView.setText(str);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hengButton:
                verticalTextView.setTextViewOrientation(LinearLayout.VERTICAL);
                break;

            case R.id.shuButton:
                verticalTextView.setTextViewOrientation(LinearLayout.HORIZONTAL);
                break;

            case R.id.zuoduiqi:
                verticalTextView.setGravity(Gravity.START);
                break;

            case R.id.juzhong:
                verticalTextView.setGravity(Gravity.CENTER);
                break;

            case R.id.youduiqi:
                verticalTextView.setGravity(Gravity.END);
                break;
        }
    }
}
