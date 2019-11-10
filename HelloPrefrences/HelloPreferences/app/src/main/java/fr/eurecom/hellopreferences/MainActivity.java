package fr.eurecom.hellopreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
// We have only one menu option
            case R.id.action_settings:
// Launch Preference activity
                Intent i = new Intent(MainActivity.this, Preferences.class);
                startActivity(i);
// A toast is a view containing a quick little message for the
                Toast.makeText(MainActivity.this, "Here you can store your user credentials.", Toast.LENGTH_LONG).show();
                        Log.i("Main", "sent an intent to the Preference class!");
                break;
        }
        return true;
    }
    android.content.SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = preferences.getString("username",
                        "n/a");
                String password = preferences.getString("password",
                        "n/a");
// A toast is a view containing a quick little message for the user.
                showPrefs(username, password);
            }
            Button button2 = (Button)findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);
            Button button4 = (Button)findViewById(R.id.button4);




        });
    }
    private void showPrefs(String username, String password){
        Toast.makeText(MainActivity.this,
                "You kept user: " + username + " and password: " + password,
                Toast.LENGTH_LONG).show();
    }
    public void openDialog(View v) {
        // Create out AlterDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Reset username and password?");
        builder.setCancelable(true);
        builder.setPositiveButton("I agree", new DialogInterface().OnClickListener(){
            public void onClick( DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Resetting Credential",Toast.LENGTH_LONG).show();
                reset_preferences();
            }
        });
        builder.setNegativeButton("No, no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Keep Credential",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void reset_preferences(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("username", null);
        edit.putString("password", null);
        edit.commit(); // Apply changes
// A toast is a view containing a quick little message for the
// user. We give a little feedback
        Toast.makeText(MainActivity.this,
                "Reset user name and password",
                Toast.LENGTH_LONG).show();
    }
    public void createNotification() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
// prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this) .setContentTitle("Hello Preferences") .setContentText("Successfully reset user Credential")
                .setSmallIcon(R.mipmap.ic_launcher) .setContentIntent(activity) .setAutoCancel(true)
                .build();
        notificationManager.notify(0,notification); }

    public void exitPreferences(View view){
        this.finish();
    }
    public void createNotification(View view){
        createNotification();
    }


}
