package net.zulu.startapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.zulu.StartApp.Api;
import net.zulu.StartApp.Dashboard;
import net.zulu.StartApp.R;
import net.zulu.StartApp.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private EditText etnUsername;
    private EditText etPassword;
    private Button btnLogin;
    private RelativeLayout rlContainer;
    public boolean isLoggedin;
    public static String cname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etnUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_id);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cname = etnUsername.getText().toString();
                //new LoginTask(Api.URL_LOGIN, params).execute();

                if (TextUtils.isEmpty(cname) && TextUtils.isEmpty(etPassword.getText())) {

                    Toast.makeText(v.getContext(),"Enter correct Credentials", Toast.LENGTH_LONG).show();

                }else
                {
                    Intent intent = new Intent(v.getContext(), Dashboard.class);
                    startActivity(intent);
                }
            }
        });

    }

    public class LoginTask extends AsyncTask<Void, Void, String> {

        String url;
        HashMap<String, String> params;

        public LoginTask(String url, HashMap<String, String> params) {
            this.url = url;
            this.params = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            return requestHandler.sendPostRequest(url, params);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(s);

                String user = jsonObject.getString("Authorized");
                Log.d("Result",user);
                if (user.equals("true")) {

                    isLoggedin = true;
                }
                else{

                    Toast.makeText(getApplicationContext(),"Enter correct Credentials", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            hideProgressDialog();
        }


    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
