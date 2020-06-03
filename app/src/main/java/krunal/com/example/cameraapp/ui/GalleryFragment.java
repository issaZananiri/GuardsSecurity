package krunal.com.example.cameraapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import krunal.com.example.cameraapp.R;

import static android.support.constraint.Constraints.TAG;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;


    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guest, container, false);
        Button send = view.findViewById( R.id.sendGuest );
        EditText name = view.findViewById( R.id.editTextGN );
        EditText reason = view.findViewById( R.id.editTextGR );
        EditText number = view.findViewById( R.id.editTextNum );
        EditText park = view.findViewById( R.id.editTextP );

        send.setOnClickListener( v -> {
            submitData( name.getText().toString() ,number.getText().toString(),park.getText().toString(),reason.getText().toString()  );
        } );
        return view;
    }

    private void submitData(String name, String plate, String park, String reason){

        if ( name.equals( null ) || plate.equals( "" ) || park.equals( "" )){
            Toast.makeText( getContext(), "Please Enter All Data", Toast.LENGTH_SHORT ).show();
        }else {
            save(name,plate,park,reason);
            Toast.makeText( getContext(), "Guest successfully  added", Toast.LENGTH_SHORT ).show();
        }

    }

     private void save(String name, String plate, String park, String reason){

         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         FirebaseFirestore db = FirebaseFirestore.getInstance();
         Map<String, Object> data = new HashMap<>();
         data.put("name", name+"");
         data.put("plate", plate+"");
         data.put("reason", reason+"");
         data.put( "park",park+"" );

         db.collection("Guest").document( user.getEmail()+"")
                 .set(data)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d(TAG, "DocumentSnapshot successfully written!");
                     }
                 })
                 .addOnFailureListener( e -> Log.w(TAG, "Error writing document", e) );

    }
}