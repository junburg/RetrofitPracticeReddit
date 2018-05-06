package moon.the.on.junburg.com.retrofitpracticereddit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import moon.the.on.junburg.com.retrofitpracticereddit.model.Feed;
import moon.the.on.junburg.com.retrofitpracticereddit.model.children.Children;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.reddit.com/";
    private static final String LOGIN_URL = "https://www.reddit.com/api/login/";
    private static final String TAG = "MainActivity";

    private Button getDataBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataBtn = (Button)findViewById(R.id.get_data_btn);
        loginBtn = (Button)findViewById(R.id.btn_login);
        final EditText etName = (EditText)findViewById(R.id.input_username);
        final EditText etPass = (EditText)findViewById(R.id.input_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(LOGIN_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RedditApi redditApi = retrofit.create(RedditApi.class);

                HashMap<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-type", "application/json");


                Call<ResponseBody> call = redditApi.login(headerMap, name, name, pass, "json");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onResponse: Server Response: " + response.toString());
                        try {
                            String json = response.body().string();
                            Log.d(TAG, "onResponse: json: " + json);
                            JSONObject data = null;
                            data = new JSONObject(json);
                            Log.d(TAG, "onResponse: data: " + data.optString("json"));
                        } catch(JSONException e) {
                            Log.e(TAG, "onResponse: JSONException: " + e.getMessage());
                        } catch(IOException e) {
                            Log.e(TAG, "onResponse: IOException: " + e.getMessage());
                        }

                        if(response.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, LoginSuccessActivity.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RedditApi redditApi = retrofit.create(RedditApi.class);
                Call<Feed> call = redditApi.getData();

                call.enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        Log.d(TAG, "onResponse: Server Response: " + response.toString());
                        Log.d(TAG, "onResponse: received information" + response.body().toString());

                        ArrayList<Children> childrenArrayList = response.body().getData().getChildren();
                        for(int i=0 ; i < childrenArrayList.size(); i++) {
                            Log.d(TAG, "onResponse: \n" +
                                    "kind: " + childrenArrayList.get(i).getKind() + "\n" +
                                    "contest_mode: " + childrenArrayList.get(i).getData().getContest_mode() + "\n" +
                                    "subreddit: " + childrenArrayList.get(i).getData().getSubreddit() + "\n" +
                                    "author: " + childrenArrayList.get(i).getData().getAuthor() + "\n" +
                                    "-------------------------------------------------------------------\n\n");
                        }



                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }
}
