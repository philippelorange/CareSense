package ca.ecaconcordia.enggames.caresense;

import android.app.Notification;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity {

    Home home = Home.newInstance();
    Alerts alerts = Alerts.newInstance();
    Sensors sensors = Sensors.newInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                selectedFragment = home;
                break;
            case R.id.navigation_alerts:
                selectedFragment = alerts;
                break;
            case R.id.navigation_sensors:
                selectedFragment = sensors;
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        assert selectedFragment != null;
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        return true;
    };
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        notificationManager = NotificationManagerCompat.from(requireNonNull(this));

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Home.newInstance());
        transaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            return home.onKeyDown(keyCode);
    }
}
