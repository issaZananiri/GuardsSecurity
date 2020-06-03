package krunal.com.example.cameraapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import krunal.com.example.cameraapp.R;
import krunal.com.example.cameraapp.data.Drivers;
import krunal.com.example.cameraapp.ui.home.HomeSearchAdatper;

import static krunal.com.example.cameraapp.R.layout.activity_scan_result;

public class ScanResultActivity extends AppCompatActivity {
    private Button button,buttonB;
    private String plate = "";
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( activity_scan_result );

        setTitle( "Confirmation" );
        textView = findViewById( R.id.scan_result_plate );
        button = findViewById(R.id.button);
        buttonB= findViewById(R.id.buttonB);
       EditText editText = findViewById(R.id.scan_result_plate);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_search);

        if (getIntent().hasExtra( "plate" )) {
             plate = Objects.requireNonNull( getIntent().getExtras() ).getString( "plate" );
        }
        buttonB.setOnClickListener(v -> {

            finish();

        });
        button.setOnClickListener(v -> {
            if (!plate.equals( "" )){

searchValue( editText,recyclerView );
            OpenSearchResult(plate);}
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!plate.equals( "" )){
            textView.setText( plate );
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void OpenSearchResult(String plate){






    }
    private void searchValue(EditText searchValue, RecyclerView recyclerView){

        Editable editable = searchValue.getEditableText();

        if (!editable.toString().equals("") || !editable.toString().isEmpty()) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("Drivers").document(editable.toString());

            docRef.get().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {


                        ArrayList<Drivers> driversArrayList = new ArrayList<>();
                        // Get keys and values

                        //enter loop here
                        // fetch data from document //

                        //for (int i = 0; i < Objects.requireNonNull(Objects.requireNonNull(document.getData()).size()) ; i++){

                        Drivers drivers = new Drivers();
                        drivers.setCarType(document.get("CarType")+"");
                        drivers.setID(Objects.requireNonNull(document.get("ID")).toString());
                        drivers.setPlate(document.get("Plate")+"");
                        drivers.setName(document.get("Name")+"");
                        drivers.setSubType(document.get("SubType")+"");
                        drivers.setViolation(document.get("Violation")+"");

                        //add to array list
                        driversArrayList.add(drivers);

                        // }

                        //set array list into search adapter
                        krunal.com.example.cameraapp.ui.home.HomeSearchAdatper homeSearchAdatper = new HomeSearchAdatper(getApplicationContext(), driversArrayList);
                        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(st);
                        recyclerView.setAdapter(homeSearchAdatper);
                        homeSearchAdatper.notifyDataSetChanged();


                    } else {
                       Toast.makeText(getApplicationContext(), "No Such Elements", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   Toast.makeText(getApplicationContext(), "get Failed with ", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
           Toast.makeText(getApplicationContext(), "Plate can not be empty", Toast.LENGTH_SHORT).show();
        }



    }
}
