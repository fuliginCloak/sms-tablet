package womboCombo.exa.busted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetDefaultMessage extends AppCompatActivity {
    public static final String CHANGE_MESSAGE = "womboCombo.exa.busted.MESSAGE";

    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default_message);
    }
    public void setMessage(View view){

        mSubmit = (Button) findViewById(R.id.submit_message);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetDefaultMessage.this, MainActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String message = editText.getText().toString();
                intent.putExtra(CHANGE_MESSAGE, message);
                startActivity(intent);
            }
        });
    }
}