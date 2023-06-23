package enig.junior_entreprise.wolvesfeedback;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
public class ViewDataActivity extends AppCompatActivity {
    private TextView userName,Email;
    private String ExtractDataUrl="https://codewithme20.000webhostapp.com/test/ExtractData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        userName=findViewById(R.id.username);
        Email=findViewById(R.id.Email);
        ExtractData();
    }

    private void ExtractData(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ExtractDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++){
                        JSONObject data=array.getJSONObject(i);
                        String username=data.getString("username");
                        String email=data.getString("email");
                        userName.setText(username);
                        Email.setText(email);
                    }

                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
