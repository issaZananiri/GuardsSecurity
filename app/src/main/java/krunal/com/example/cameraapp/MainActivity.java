package krunal.com.example.cameraapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdtoolkit.anpr.api.AnprEngineFactory;
import com.sdtoolkit.anpr.api.IAnprEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final int REQUEST_INTERNET_PERMISSION = 1;
    private static String plates = "Plates";

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";

    private AppExecutor mAppExcutor;

    private ImageView mImageView;

    private Button mStartCamera;

    private String mTempPhotoPath;

    private TextView mtextView;

    private Bitmap mResultsBitmap;
    File photoFile;
    private FloatingActionButton mClear, mSave, mShare;
    Uri photoURI;

    IAnprEngine mAnprEngine = AnprEngineFactory.createAnprEngine(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppExcutor = new AppExecutor();

        mImageView = findViewById(R.id.imageView);
        mClear = findViewById(R.id.clear);
        mSave = findViewById(R.id.Save);
        mShare = findViewById(R.id.Share);
        mStartCamera = findViewById(R.id.startCamera);
        mtextView = findViewById(R.id.textView);


        mImageView.setVisibility(View.GONE);
        mShare.setVisibility(View.GONE);
        mSave.setVisibility(View.GONE);
        mClear.setVisibility(View.GONE);
        mtextView.setVisibility(View.VISIBLE);
        //mtextView.setText("HIIIIIIIIIIIIIIII");

        mStartCamera.setOnClickListener(v -> {

            //request internet permission
            if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        REQUEST_INTERNET_PERMISSION);
            }

            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });

        mSave.setOnClickListener((View v) -> {
            final String[] ok = new String[1];
            mAppExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
//                BitmapUtils.deleteImageFile(this, mTempPhotoPath);

                // Save the image
                try {
                    ok[0] = BitmapUtils.saveImage(this, mResultsBitmap);
                } catch (Exception e) {
                    ok[0] = e.getMessage();
                }

                try {
                    sendImage(mtextView);
               Toast.makeText(this, "Image sent", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

//            Toast.makeText(this, "Image Save", Toast.LENGTH_LONG).show();

        });

        mClear.setOnClickListener(v -> {
            // Clear the image and toggle the view visibility
            mImageView.setImageResource(0);
            mStartCamera.setVisibility(View.VISIBLE);
            mSave.setVisibility(View.GONE);
            mShare.setVisibility(View.GONE);
            mClear.setVisibility(View.GONE);

            mAppExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
                BitmapUtils.deleteImageFile(this, mTempPhotoPath);
            });

        });

        mShare.setOnClickListener((View v) -> {

            mAppExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
                BitmapUtils.deleteImageFile(this, mTempPhotoPath);

                // Save the image
                BitmapUtils.saveImage(this, mResultsBitmap);

            });

            // Share the image
            BitmapUtils.shareImage(this, mTempPhotoPath);

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera();
                }
//                else {
                    // If you do not get permission, show a Toast
//                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
//                }
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        // Toggle Visibility of the views
        mStartCamera.setVisibility(View.GONE);
        mSave.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        mClear.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.VISIBLE);

        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);


        // Set the new bitmap to the ImageView
        mImageView.setImageBitmap(mResultsBitmap);
    }

    void sendImage(TextView textView) throws IOException {

//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.INTERNET)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // If you do not have permission, request it
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.INTERNET},
//                    REQUEST_STORAGE_PERMISSION);
//        } else {
            // Launch the camera if the permission exists
            try {
                String secret_key = "sk_b21a58ed1395b3be6083f6e3";

                // Read image file to byte array
//                Path path;
//                //path = FileSystems.getDefault().getPath(mTempPhotoPath);
//                path = FileSystems.getDefault().getPath(mTempPhotoPath);
//                byte[] data = Files.readAllBytes(path);
//
//
//                // Encode file bytes to base64
//                byte[] encoded = Base64.getEncoder().encode(data);

                File imagefile = new File(mTempPhotoPath);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imagefile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bm = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                byte[] encoded = Base64.encode(b,Base64.DEFAULT);

                // Setup the HTTPS connection to api.openalpr.com
                URL url = new URL("https://api.openalpr.com/v2/recognize_bytes?recognize_vehicle=1&country=us&secret_key=" + secret_key);
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST"); // PUT is another valid option
                http.setFixedLengthStreamingMode(encoded.length);
                http.setDoOutput(true);

                // Send our Base64 content over the stream
                try (OutputStream os = http.getOutputStream()) {
                    os.write(encoded);
                }
                Log.d("HTTP REQUEST", "About to send HTTP Request");
                int status_code = http.getResponseCode();

                if (status_code == 200) {

                    // Read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            http.getInputStream()));
                    String json_content = "";
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        json_content += inputLine;
                    in.close();

                    http.disconnect();
                    Log.d("HTTP REQUEST", json_content);
                    try {

                        JSONObject jsonObject = new JSONObject(json_content);
                        JSONArray results = jsonObject.getJSONArray("results");
                        JSONObject jsonObject1 = results.getJSONObject(0);
                        Log.e("content",""+jsonObject1);
                        plates = jsonObject1.getString("plate");
                        Log.e("plate",""+plates);

                        displayPlate(plates,textView);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("HTTP REQUEST", "Non 200 status code :" + status_code);
                }



            } catch (MalformedURLException e) {
                {
                    Log.e("HTTP REQUEST", e.getMessage());
                }
            }

//        }
    }

    private void displayPlate(String plate , TextView textView){
        textView.setText(plate+"");
    }

}