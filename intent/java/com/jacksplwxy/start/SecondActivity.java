package com.jacksplwxy.start;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class SecondActivity extends Activity {
    private TextView tv;
    private Button btn2;
    public static final int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("str");
        tv = (TextView) findViewById(R.id.myTextView1);
        tv.setText(str);

        btn2 = (Button) findViewById(R.id.myButton2);
        btn2.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("back", "come from second activity");
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }
}