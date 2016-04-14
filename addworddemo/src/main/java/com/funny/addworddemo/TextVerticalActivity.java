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

    private AddWordOutsideLinearLayout addWordOutsideLinearLayout;
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_vertical);
        initView();
    }

    private void initView() {
        addWordOutsideLinearLayout = (AddWordOutsideLinearLayout) findViewById(R.id.verticalTextView);
        addWordOutsideLinearLayout.setTextColor(Color.BLACK);
        addWordOutsideLinearLayout.setTextSize(30);
        addWordOutsideLinearLayout.setTextViewOrientation(LinearLayout.VERTICAL);
        addWordOutsideLinearLayout.setText("靳广越\n" +
                "靳广越靳广越\n" +
                "我要飞");

        findViewById(R.id.hengButton).setOnClickListener(this);
        findViewById(R.id.shuButton).setOnClickListener(this);
        findViewById(R.id.zuoduiqi).setOnClickListener(this);
        findViewById(R.id.juzhong).setOnClickListener(this);
        findViewById(R.id.youduiqi).setOnClickListener(this);
        edit_text = (EditText) findViewById(R.id.edit_text);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                addWordOutsideLinearLayout.setText(str);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hengButton:
                addWordOutsideLinearLayout.setTextViewOrientation(LinearLayout.VERTICAL);
                break;

            case R.id.shuButton:
                addWordOutsideLinearLayout.setTextViewOrientation(LinearLayout.HORIZONTAL);
                break;

            case R.id.zuoduiqi:
                if (addWordOutsideLinearLayout.getOrientation() == LinearLayout.HORIZONTAL) {
                    addWordOutsideLinearLayout.setMyGravity(Gravity.TOP);
                } else {
                    addWordOutsideLinearLayout.setMyGravity(Gravity.LEFT);
                }

                break;

            case R.id.juzhong:
                addWordOutsideLinearLayout.setMyGravity(Gravity.CENTER);
                break;

            case R.id.youduiqi:
                if (addWordOutsideLinearLayout.getOrientation() == LinearLayout.HORIZONTAL) {
                    addWordOutsideLinearLayout.setMyGravity(Gravity.BOTTOM);
                } else {
                    addWordOutsideLinearLayout.setMyGravity(Gravity.RIGHT);
                }
                break;
        }
    }
}
