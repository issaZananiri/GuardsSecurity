package krunal.com.example.cameraapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import krunal.com.example.cameraapp.R;

public class ViewProfile extends AppCompatActivity {

    private ImageView image;
    private TextView name;
    private TextView id;
    private TextView carSpec;
    private TextView parking;
    private TextView violations;
    private Button addViolation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent profile = getIntent();
        Driver d = (Driver)profile.getSerializableExtra("Driver");


        image.findViewById(R.id.imageView2);
        name.findViewById(R.id.textView2);
        id.findViewById(R.id.textView3);
        carSpec.findViewById(R.id.textView4);
        parking.findViewById(R.id.textView5);
        violations.findViewById(R.id.textView6);

        publishData(d);

    }

    private void publishData(Driver driver) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayData(driver);
            }
        });
    }

    private void displayData(Driver driver){

        name.setText(driver.name);
        id.setText(driver.id);
        carSpec.setText(driver.CarType + " : "+ driver.SubType + " : " );
        parking.setText("Parking #1");
        violations.setText("0");

    }



}
