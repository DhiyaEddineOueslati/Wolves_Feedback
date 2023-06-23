package enig.junior_entreprise.wolvesfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
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

public class MainActivity extends AppCompatActivity {

    private EditText nom,email,filiere,feedback;
    private Button save,viewData;
    private String sendUrl="https://wolvesfeedback.000webhostapp.com/wolves/getData.php";
    private RequestQueue requestQueue;
    private  static  final  String TAG=MainActivity.class.getSimpleName();
    int sucess;
    private String TAG_SUCESS="sucess";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom=findViewById(R.id.txtUsername);
        email=findViewById(R.id.email);
        filiere=findViewById(R.id.txtFiliere);
        feedback=findViewById(R.id.feedback);
        save=findViewById(R.id.btnSave);
        viewData=findViewById(R.id.btnViewData);
        requestQueue=Volley.newRequestQueue(getApplicationContext());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewDataActivity.class);
                startActivity(intent);
            }
        });

    }

    private  void sendData(){
        StringRequest request=new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    sucess = jobj.getInt(TAG_SUCESS);
                    if (sucess == 1) {
                        Toast.makeText(MainActivity.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getParams(){
                Map<String, String> params=new HashMap<String, String>();
                params.put("nom",nom.getText().toString());
                params.put("email",email.getText().toString());
                params.put("filiere",filiere.getText().toString());
                params.put("feedback",feedback.getText().toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);

    }

}
