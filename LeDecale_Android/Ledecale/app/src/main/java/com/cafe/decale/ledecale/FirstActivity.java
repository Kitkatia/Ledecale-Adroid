package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by manut on 24/06/2017.
 */

public class FirstActivity extends Activity {

    Button connexion;
    Button leDecale;
    TextView register;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        connexion = (Button) findViewById(R.id.connexion);
        leDecale = (Button) findViewById(R.id.leDecale);

        leDecale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySessionManager sessionManager = new MySessionManager(getApplicationContext());
                if(sessionManager.isLogged()) {
                    alert.showAlertDialog(FirstActivity.this, "Login Status", "You're already connected", true);
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(FirstActivity.this, ConnectionActivity.class);
                    startActivity(intent);
                }
            }
        });
        SpannableString ss = new SpannableString("Register now!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        register = (TextView) findViewById(R.id.register);
        register.setText(ss);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setHighlightColor(Color.TRANSPARENT);
    }
}
