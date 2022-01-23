package com.example.myapp_natalygiron;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapp_natalygiron.model.ProfileEntry;
import com.google.android.material.button.MaterialButton;

public class EditProfileFragment extends Fragment {

    ImageView imageEditProfile;
    ProgressDialog progressDialog;
    private  static final int CAMERA_REQUEST = 100;
    private static  final int IMAGE_PICK_CAMERA_REQUEST = 400;

    String cameraPermission[];
    Uri imageUri;
    String profileOrCoverImage;
    MaterialButton editImage;
    MaterialButton saveChanges;

    EditText txt_name, txt_phone;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        /* Imagen */
        imageEditProfile = view.findViewById(R.id.profile_image);
        editImage = view.findViewById(R.id.edit_image);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        /* User Info */
        saveChanges = view.findViewById(R.id.save_button);
        String txtName = view.findViewById(R.id.text_name).toString();
        String txtPhone = view.findViewById(R.id.text_phone).toString();
        ProfileEntry profile = new ProfileEntry(txtName,txtPhone);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Updating Profile Picture");
                profileOrCoverImage = "image";
                showImagePicDialog();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", txt_name.getText().toString().trim());
                bundle.putString("phone",txt_phone.getText().toString().trim());
                getParentFragmentManager().setFragmentResult("key", bundle);

                ((NavigationHost) getActivity()).navigateTo(new ProfileFragment(), false);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_name = view.findViewById(R.id.text_name);
        txt_phone = view.findViewById(R.id.text_phone);
        saveChanges = view.findViewById(R.id.save_button);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", txt_name.getText().toString().trim());
                bundle.putString("phone",txt_phone.getText().toString().trim());
                getParentFragmentManager().setFragmentResult("key", bundle);

                ((NavigationHost) getActivity()).navigateTo(new ProfileFragment(), false);

            }
        });
    }

    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pick image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which ==0) {
                    if(!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                }
            }
        });
        builder.create().show();
    }

    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void  requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "User profile picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "User profile picture description");
        imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_REQUEST);
    }

    @Override
    public  void  onPause(){
        super.onPause();
        Glide.with(this).load(imageUri).into(imageEditProfile);
    }

    @Override
    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        switch (requestCode){
            case CAMERA_REQUEST: {
                if(grantResult.length > 0) {
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResult[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(getContext(),"Please enable camera and storage permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong! try again...", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

}
