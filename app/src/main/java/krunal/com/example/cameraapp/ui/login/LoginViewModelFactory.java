package krunal.com.example.cameraapp.ui.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import krunal.com.example.cameraapp.data.LoginDataSource;
import krunal.com.example.cameraapp.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom( LoginViewModel.class )) {
            return (T) new LoginViewModel( LoginRepository.getInstance( new LoginDataSource() ) );
        } else {
            throw new IllegalArgumentException( "Unknown ViewModel class" );
        }
    }
}
