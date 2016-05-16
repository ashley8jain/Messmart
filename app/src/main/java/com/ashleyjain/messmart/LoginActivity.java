package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mobileno,pass;
    Button login,fb,gp;
    TextView signup;

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "cookie";
    private static final String SESSION_COOKIE = "PHPSESSID";
    private static LoginActivity _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static LoginActivity get() {
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);  //for saving cookies
        _requestQueue = Volley.newRequestQueue(this);

        final Context context = LoginActivity.this;

        Typeface font = Typeface.createFromAsset(getAssets(),"YuppySC-Regular.ttf");

        mobileno = (EditText) findViewById(R.id.phone);
        mobileno.setTypeface(font);
        mobileno.addTextChangedListener(new checkError(mobileno));

        pass = (EditText) findViewById(R.id.password);
        pass.setTypeface(font);
        pass.addTextChangedListener(new checkError(pass));

        login = (Button) findViewById(R.id.loginbutton);
        login.setTypeface(font);

        signup = (TextView) findViewById(R.id.signup);
        signup.setTypeface(font);

        fb = (Button) findViewById(R.id.fblogin);
        fb.setTypeface(font);
        gp = (Button) findViewById(R.id.gplogin);
        gp.setTypeface(font);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(context, "", "Authenticating...", true);
                final String mbnb = mobileno.getText().toString();
                final String pwd = pass.getText().toString();

                String url = "http://www.messmart.com/index.php/ajaxactions";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                //response JSON from url
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String message = jsonResponse.getString("ec");
                                    System.out.println("Message: " + message);
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Log.d("debug", "posting param");
                        Map<String, String> params = new HashMap<String, String>();

                        // the POST parameters:
                        params.put("loginphone", mbnb);
                        params.put("loginpass", pwd);
                        params.put("action", "login");
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                Volley.newRequestQueue(context).add(postRequest);
            }
        });
    }

    //checking session cookie
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

    //Volley request queue
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }
}