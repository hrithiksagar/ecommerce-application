package in.ac.sharda.loginactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import in.ac.sharda.productListActivity.ProductListActivity;

public class MainActivity extends AppCompatActivity {
EditText editTextUserName,editTextPassword;
Button btnSubmit;
AsyncHttpClient client;
RequestParams params;
ListView list;
ArrayList listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        list=(ListView)findViewById(R.id.list);
        listdata=new ArrayList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username1 = editTextUserName.getText().toString();
                String Password1 = editTextPassword.getText().toString();
                if (TextUtils.isEmpty(Username1)) {
                    editTextUserName.setError("Enter Username");
                }
                else if (TextUtils.isEmpty(Password1)) {
                    editTextPassword.setError("Enter Password");
                }
                else{
                    client=new AsyncHttpClient();
                    params=new RequestParams();

                    client.get("https://jsonplaceholder.typicode.com/posts",new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String data=new String(responseBody);
                            Log.d("Ganesh",data);
                            try{
                                JSONArray array=new JSONArray(data);
                                for(int i=1;i<array.length();i++){
                                    JSONObject jsnobj=array.getJSONObject(i);
                                    int userId=jsnobj.getInt("userId");
                                    int id=jsnobj.getInt("id");
                                    String title=jsnobj.getString("title");
                                    String body=jsnobj.getString("body");
                                    listdata.add(userId+"\n"+id+"\n"+title+"\n"+body);
                                    ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_expandable_list_item_1,listdata);
                                    list.setAdapter(adapter);
                                }
                            }
                            catch (JSONException je){
                                je.printStackTrace();                           }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
