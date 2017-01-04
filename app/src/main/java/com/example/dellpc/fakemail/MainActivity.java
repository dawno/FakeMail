package com.example.dellpc.fakemail;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button send ;
    Toolbar toolbar;
    private static final String url = "http://skywalker.org.in/fakemail.php";
    private EditText uemail,temail,subject,message;
    private TextInputLayout inputLayoutMy, inputLayoutyour, inputLayoutsubject, layoutmessage;
    private static final String TAG = "ho gya";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fake Mail");
        send = (Button)findViewById(R.id.send);
        uemail = (EditText)findViewById(R.id.uemail);
        temail = (EditText)findViewById(R.id.temail);
        subject = (EditText)findViewById(R.id.subject);
        message = (EditText)findViewById(R.id.message);
        inputLayoutMy=(TextInputLayout)findViewById(R.id.first);
        inputLayoutyour=(TextInputLayout)findViewById(R.id.second);
        inputLayoutsubject=(TextInputLayout)findViewById(R.id.third);
        layoutmessage=(TextInputLayout)findViewById(R.id.fourth);
        uemail.addTextChangedListener(new MyTextWatcher(uemail));
        temail.addTextChangedListener(new MyTextWatcher(temail));

             send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String memail = uemail.getText().toString();
                String yemail = temail.getText().toString();
                String text = message.getText().toString();
                String sub = subject.getText().toString();
                if(submitForm()==true) {
                    messagesending(memail, yemail, sub, text);
                    Toast.makeText(MainActivity.this,"Please wait for 2-3 mins....the mail is in process",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"EMAIL is not valid",Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
    private void messagesending( final String uemail , final String temail,final  String subject, final String message ) {


        StringRequest movieRe = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e(TAG, "message loading  " + error.getMessage());
                Toast.makeText(MainActivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uemail", uemail);

                params.put("temail", temail);

                params.put("message", message);
                params.put("subject", subject);

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(movieRe);
    }
    private boolean submitForm() {


        if (!validateEmail()) {
            return false;
        }

       return true;
        }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateEmail() {
        String email = uemail.getText().toString().trim();
        String oemail = temail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutMy.setError(getString(R.string.err_msg_email));
            requestFocus(uemail);
            return false;
        } else {
            inputLayoutMy.setErrorEnabled(false);
        }
        if (oemail.isEmpty() || !isValidEmail(oemail)) {
            inputLayoutyour.setError(getString(R.string.err_msg_email));
            requestFocus(temail);
            return false;
        } else {
            inputLayoutyour.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
private class MyTextWatcher implements TextWatcher {

    private View view;

    private MyTextWatcher(View view) {
        this.view = view;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        switch (view.getId()) {

            case R.id.uemail:
                validateEmail();
                break;
            case R.id.temail:
                validateEmail();
                break;

    }
    }}}