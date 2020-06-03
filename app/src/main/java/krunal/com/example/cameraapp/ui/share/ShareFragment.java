package krunal.com.example.cameraapp.ui.share;

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

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of( this ).get( ShareViewModel.class );
        View view1 = inflater.inflate( R.layout.fragment_violation, container, false );
        Button send = view1.findViewById( R.id.SendViolation );
        EditText number = view1.findViewById( R.id.editTextV );
        EditText type = view1.findViewById( R.id.editTextVT );
        EditText details = view1.findViewById( R.id.editTextVD );

        send.setOnClickListener( v -> {
            submitData1( number.getText().toString() ,type.getText().toString(),details.getText().toString()  );
        } );

        return view1;
    }

    private void submitData1(String num, String type, String detail){

        if ( num.equals( null ) || type.equals( "" ) || detail.equals( "" )){
            Toast.makeText( getContext(), "Please Enter All Data", Toast.LENGTH_SHORT ).show();
        }else {
            save(num,type,detail);
            Toast.makeText( getContext(), "Violation successfully  added", Toast.LENGTH_SHORT ).show();
        }

    }

    private void save(String num, String type, String detail){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("name", num+"");
        data.put("type", type+"");
        data.put("detail", detail+"");


        db.collection("Violations").document( user.getEmail()+"")
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