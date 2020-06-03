package krunal.com.example.cameraapp.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import krunal.com.example.cameraapp.R;
import krunal.com.example.cameraapp.data.Drivers;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_search, container, false );

        final Button searchBtn = root.findViewById(R.id.startCameraSearch);
        EditText editText = root.findViewById(R.id.editText2);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_search);

        searchBtn.setOnClickListener(v -> searchValue(editText, recyclerView));

        return root;
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
                        HomeSearchAdatper homeSearchAdatper = new HomeSearchAdatper(getContext(), driversArrayList);
                        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(st);
                        recyclerView.setAdapter(homeSearchAdatper);
                        homeSearchAdatper.notifyDataSetChanged();


                    } else {
                        Toast.makeText(getContext(), "No Such Elements", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "get Failed with ", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getContext(), "Plate can not be empty", Toast.LENGTH_SHORT).show();
        }



        }
}