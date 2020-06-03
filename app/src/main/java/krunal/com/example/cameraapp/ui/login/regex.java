package krunal.com.example.cameraapp.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AF on 2/11/2017.
 */

public class regex {

    private Context c;
    //regex input email and name
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public regex(Context context){
        this.c = context;
    }

    public void showSnackBar(String message, LinearLayout linear) {
        Snackbar snackbar = Snackbar
                .make(linear,message, Snackbar.LENGTH_LONG)
                .setAction("موافق", onSnackBarClickListener());
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(173,17,17));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private View.OnClickListener onSnackBarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public boolean validateEmail(EditText regex){
        matcher = pattern.matcher(regex.getText().toString().trim());
        return  matcher.matches();
    }
    public boolean validateField(EditText inputLayout) {
        if (inputLayout.getText().toString().trim().equals("")) {
            inputLayout.setError("مطلوب*");
            return false;
        } else {
            return true;
        }
    }

}