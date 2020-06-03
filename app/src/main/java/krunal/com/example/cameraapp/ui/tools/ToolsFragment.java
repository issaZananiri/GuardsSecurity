package krunal.com.example.cameraapp.ui.tools;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import krunal.com.example.cameraapp.R;
import krunal.com.example.cameraapp.ui.login.LoginActivity;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of( this ).get( ToolsViewModel.class );
        View root = inflater.inflate( R.layout.fragment_settings, container, false );

        final TextView textView = root.findViewById( R.id.text_tools );
        final Button logOut= root.findViewById( R.id.logout_btn );
        logOut.setOnClickListener( v -> logout() );
        toolsViewModel.getText().observe( this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText( s );
            }
        } );


        return root;
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( getContext(), LoginActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
    }

}