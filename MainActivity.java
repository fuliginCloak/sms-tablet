package womboCombo.exa.busted;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient mClient = new OkHttpClient();
    private Context mContext;
    private EditText mTo;
    private EditText mBody;
    private ImageButton mChange;
    private Button mSend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTo = (EditText) findViewById(R.id.txtNumber);
        mBody = (EditText) findViewById(R.id.txtMessage);
        mSend = (Button) findViewById(R.id.btnSend);
        mChange = (ImageButton) findViewById(R.id.set_default);
        mContext = getApplicationContext();


        Intent intent = getIntent();
        String message = intent.getStringExtra(SetDefaultMessage.CHANGE_MESSAGE);
        mBody.setText(message);

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetDefaultMessage.class);
                startActivity(intent);
            }
        });


        mSend.setOnClickListener(new View.OnClickListener() {
            Call post(String url, Callback callback) throws IOException{
                RequestBody formBody = new FormBody.Builder()
                        .add("To", mTo.getText().toString())
                        .add("Body", mBody.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                Call response = mClient.newCall(request);
                response.enqueue(callback);
                return response;

            }

            @Override
            public void onClick(View view) {
                try {
                    post(mContext.getString(R.string.backend_url), new  Callback(){

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTo.setText("");
                                    mBody.setText("");
                                    Toast.makeText(getApplicationContext(),"SMS Sent!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}