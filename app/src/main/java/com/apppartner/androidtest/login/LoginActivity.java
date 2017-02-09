package com.apppartner.androidtest.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.apppartner.androidtest.network.LogInService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A screen that displays a login prompt, allowing the user to login to the AppPartner Web Server.
 * <p>
 * Created on Aug 28, 2016
 *
 * @author Thomas Colligan
 */
public class LoginActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://dev3.apppartner.com/";

    @BindView(R.id.username_et) EditText usernameEt;
    @BindView(R.id.password_et) EditText passwordEt;
    @BindView(R.id.login_btn) Button loginBtn;
    private Context context;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Send 'username' and 'password' to http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity
        // TODO: The only valid login credentials are username:AppPartner password:qwerty
        // TODO: so please use those to test the login.

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkCall(usernameEt.getText().toString(), passwordEt.getText().toString());
            }
        });
    }

    private void networkCall(String username, String password) {


        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Log.d("jjj", "networkCall: " + client.connectTimeoutMillis());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        LogInService service = retrofit.create(LogInService.class);

        Call<ResponseBody> call = service.updateUser(username, password);
        final long t1 = System.nanoTime();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                long t2 = System.nanoTime();
                if (response.body() != null) {
                    long connectionTime = (t2 - t1) / 1000000;
                    String messages = "";
                    try {
                        messages = response.body().string();
                        messages = messages.substring(2, messages.length() - 2);
                        messages = messages.replaceAll("\"", "");
                        messages = messages.replaceAll(",", "\n");
                        messages = messages + "\ntime:" + connectionTime + "ms";

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(messages)
                            .setTitle("Info")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Wrong username/password")
                            .setTitle("Info")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}