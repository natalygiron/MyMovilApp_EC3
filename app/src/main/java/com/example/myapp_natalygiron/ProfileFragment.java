package com.example.myapp_natalygiron;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    ImageView imageProfile;
    TextView txtname, txtphone;
    String txtName, txtPhone;
    Uri myuri;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        imageProfile = view.findViewById(R.id.profile_image);
        MaterialButton editButton = view.findViewById(R.id.edit_button);
        MaterialButton startButton = view.findViewById(R.id.start_button);
        txtName = view.findViewById(R.id.text_name).toString();
        txtPhone = view.findViewById(R.id.text_phone).toString();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new EditProfileFragment(), false);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new EditProfileFragment(), false);
            }
        });

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String name = bundle.getString("name");
                String phone = bundle.getString("phone");
                String uri = bundle.getString("image");

                if(txtname.equals("") || txtphone.equals("")){
                    txtname.setText(txtName);
                    txtphone.setText(txtPhone);
                } else {
                    txtname.setText(name);
                    txtphone.setText(phone);
                }
                myuri = Uri.parse(uri);
                if(!uri.equals("")){
                    Glide.with(getContext()).load(myuri).into(imageProfile);
//                    imageProfile.setImageURI(myuri);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtname = view.findViewById(R.id.text_name);
        txtphone = view.findViewById(R.id.text_phone);
    }


}
