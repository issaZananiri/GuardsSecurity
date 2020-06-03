package krunal.com.example.cameraapp.ui.send;

import android.arch.lifecycle.ViewModelProviders;
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

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of( this ).get( SendViewModel.class );
        View root1 = inflater.inflate( R.layout.fragment_report, container, false );
        EditText plate = root1.findViewById( R.id.editTextR );
        EditText note = root1.findViewById( R.id.editTextRN );
        EditText location = root1.findViewById( R.id.editTextRL );
        Button bt=root1.findViewById( R.id.SendReport );
        bt.setOnClickListener( v -> {
            submitData1( plate.getText().toString() ,note.getText().toString(),location.getText().toString()  );
        } );
        return root1;
    }

    private void submitData1(String plate, String note,String location){

        if ( plate.equals( null ) || note.equals( "" ) ){
            Toast.makeText( getContext(), "Please Enter All Data", Toast.LENGTH_SHORT ).show();
        }else {
            save(plate,note,location);
            Toast.makeText( getContext(), "Report Sent for Car: "+plate, Toast.LENGTH_SHORT ).show();
        }

    }

    private void save(String plate, String note,String location){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("Plate", plate+"");
        data.put("Note", note+"");
        data.put("Location",location);



        db.collection("Emergency").document( user.getEmail()+"")
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