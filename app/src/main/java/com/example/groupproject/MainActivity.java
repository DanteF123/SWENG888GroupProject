package com.example.groupproject;

import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  //  private Button mLoginButton;
  //  private Button mCreateanAccountButton;
 //   private EditText mLoginName;
  //  private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Initialize Login screen elements
      //  mLoginName = findViewById(R.id.homeScreenSearchEditText);
      //  mPassword = findViewById(R.id.logInEditTextPassword);
     //   mLoginButton = findViewById(R.id.logInLogInButton);
     //   mCreateanAccountButton = findViewById(R.id.logInCreateAccountButton);

      //  mLoginButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View view) {
                //Get entered uname and password
       //         String username = mLoginName.getText().toString();
        //        String password = mPassword.getText().toString();
                //Passing default values for now
               //Implement login "Logic"
        //        if(username.equals("Admin") && password.equals("admin")){
         //           Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
          //      } else {
                    //Fail case
         //           Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_LONG).show();
         //       }
        //    }
      //  });

      //  mCreateanAccountButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View view) {
                //do nothing for now
       //     }
       // });
    }
}