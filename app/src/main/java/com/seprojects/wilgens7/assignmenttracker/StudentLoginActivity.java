package com.seprojects.wilgens7.assignmenttracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class StudentLoginActivity extends AppCompatActivity {

    EditText studentNumber;
    EditText studentPassword;

    static JSONObject jObj = null;
    static String json = "";
    private String username;
    private String password;
    protected String loginResultMessage;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        loginResultMessage = "";

        studentNumber = findViewById(R.id.login_student_username_editext);
        studentPassword = findViewById(R.id.login_student_password_edittext);
        //studentRegistration();
    }

    //login button code
    public void onLogin(View v){

        username = studentNumber.getText().toString();
         password = studentPassword.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            loginResultMessage = "Check Missing Input Value";
            Toast.makeText(this,loginResultMessage,Toast.LENGTH_LONG).show();
        }
        else {
            StudentLoginBT studentLoginBT = new StudentLoginBT(this);
            studentLoginBT.execute(username,password);

        }

    }

    //code for link
    public void linkToRegister(View view){
        startActivity(new Intent(StudentLoginActivity.this,StudentRegistrationActivity.class));

    }

    //========AsyncTask Class=============================
    private class StudentLoginBT extends AsyncTask<String,String,String> {

        Context context;

        public StudentLoginBT(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String student_login_url =
                    "http://10.0.2.2/AssignmentTrackingApp/StudentLogin.php";
            username = params[0];
            password = params[1];

            try{
                URL url = new URL(student_login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        outputStream, "UTF-8"
                ));

                String data = URLEncoder.encode("student_No", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                //
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line;

                while((line = bufferedReader.readLine()) != null){

                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;


            }catch (MalformedURLException e){

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//TODO: getJSON method to work
//            studentInfo = json.getJSONArray("studentInfo");
//
//            if(studentInfo.isNull(0)){
//
//            }
//            JSONObject j = studentInfo.getJSONObject(0);
//
//            student.setFirstName(j.optString("firstName"));
//            student.setLastName(j.optString("lastName"));
//            student.setPassword(j.optString("password"));
//            student.setStudentNumber(j.optString("student_No"));
//
//            DB_student_Id = student.getUserName();
//            DB_password = student.getPassword();
//
//            if(DB_student_Id.equalsIgnoreCase(username)&& DB_password.equalsIgnoreCase(password)){
//
//                Intent intent = new Intent("com.example.feranmi.assignmentttrackingapp.StudentPage");
//                intent.putExtra("student",student);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(intent);
//            }
//
            try{
                loginResultMessage = result;
                Toast.makeText(context,loginResultMessage,Toast.LENGTH_LONG).show();

            }catch (NullPointerException e){
                Toast.makeText(context,"Login error"+e.toString(),Toast.LENGTH_LONG).show();
                Log.e("Null Pointer Error", "My error: "+e.toString());
            }
        }
    }
}
