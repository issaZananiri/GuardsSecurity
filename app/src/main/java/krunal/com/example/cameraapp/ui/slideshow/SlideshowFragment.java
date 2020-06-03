package krunal.com.example.cameraapp.ui.slideshow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import krunal.com.example.cameraapp.R;
import krunal.com.example.cameraapp.data.Parkings;
import krunal.com.example.cameraapp.ui.Parking;

import krunal.com.example.cameraapp.ui.slideshow.parkingAdatper;

import static android.support.constraint.Constraints.TAG;

public class SlideshowFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_searchp, container, false );

        final Button searchBtn = root.findViewById(R.id.startCameraSearch);
        EditText editText = root.findViewById(R.id.editText2);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_search);


        searchBtn.setOnClickListener(v -> searchValue(editText, recyclerView));

        return root;
    }



    private void searchValue(EditText searchValue, RecyclerView recyclerView){

        Editable editable = searchValue.getEditableText();

        if (!editable.toString().equals("") || !editable.toString().isEmpty()) {

            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            db1.setFirestoreSettings(settings);

            DocumentReference docRef = db1.collection("Parking").document(editable.toString());

            docRef.get().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {


                        ArrayList<Parkings> parkingsArrayList = new ArrayList<>();
                        // Get keys and values

                        //enter loop here
                        // fetch data from document //

                        //for (int i = 0; i < Objects.requireNonNull(Objects.requireNonNull(document.getData()).size()) ; i++){

                        Parkings parkings = new Parkings();
                        parkings.setCapacity(document.get("Capacity")+"");
                        parkings.setName(document.get("Name")+"");
                       // parkings.setCarplates(document.get("CarPlates")+"");


                        //add to array list
                        parkingsArrayList.add(parkings);

                        // }

                        //set array list into search adapter
                        parkingAdatper parkingAdatper = new parkingAdatper(getContext(),parkingsArrayList);
                        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(st);
                        recyclerView.setAdapter(parkingAdatper);
                        parkingAdatper.notifyDataSetChanged();


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