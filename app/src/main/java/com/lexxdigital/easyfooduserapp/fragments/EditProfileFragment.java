package com.lexxdigital.easyfooduserapp.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.api.EditProfileInterface;
import com.lexxdigital.easyfooduserapp.model.edit_account_request.EditAccountRequest;
import com.lexxdigital.easyfooduserapp.model.edit_account_response.EditAccountResponse;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class EditProfileFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.top_address)
    LinearLayout topAddress;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.add_image)
    LinearLayout addImage;
    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;
    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    Unbinder unbinder;
    @BindView(R.id.email)
    EditText email;
    private GlobalValues val;
    private Dialog dialog;
    private Context mContext;
    private String profileImage = "";

    @SuppressLint("ValidFragment")
    public EditProfileFragment(Context mContext) {
        this.mContext = mContext;
    }

    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
    private final int PICK_IMAGE_CAMERA = 101, PICK_IMAGE_GALLERY = 102;
    private File imgFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) mContext;
        dialog = new Dialog(mContext);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.add_image, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_image:
                selectImage();
                break;
            case R.id.btn_confirm:
                if (editFirstName.getText().toString().trim().length() <= 0) {

                } else if (editLastName.getText().toString().trim().length() <= 0) {

                } else if (editMobile.getText().toString().trim().length() <= 0) {

                } else {
                    updateAccountDetail();
                }
                break;
        }
    }

    public void updateAccountDetail() {
        EditProfileInterface apiInterface = ApiClient.getClient(getContext()).create(EditProfileInterface.class);
        EditAccountRequest request = new EditAccountRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setFirstName(editFirstName.getText().toString());
        request.setLastName(editLastName.getText().toString());
        request.setPhoneNumber(editMobile.getText().toString());
        request.setProfilePic(profileImage);


        Call<EditAccountResponse> call3 = apiInterface.mupdate(request);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        showDialog("Account details changed successfully.");
                    } else {

                        showDialog(response.body().getErrors().getPhoneNumber());
                    }
                } catch (Exception e) {
                    dialog.hide();
                    showDialog("Failed to change account details. Please try again.");
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                showDialog("Failed to change account details. Please try again.");
                dialog.hide();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void selectImage() {
        try {
            PackageManager pm = mContext.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, mContext.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);

                            Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                            Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
                            m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(m_intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else {
                EasyPermissions.requestPermissions(mContext, "All permissions are required in oder to run this application", 101, permissions);
                Toast.makeText(mContext, "Camera Permission error", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                    String[] perms = {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.CAMERA
                    };


                    if (!EasyPermissions.hasPermissions(mContext, perms)) {
                        EasyPermissions.requestPermissions(mContext, "All permissions are required in oder to run this application", 101, perms);
                    }


                }
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Camera Permission error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                String[] perms = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CAMERA
                };


                if (!EasyPermissions.hasPermissions(mContext, perms)) {
                    EasyPermissions.requestPermissions(mContext, "All permissions are required in oder to run this application", 101, perms);
                }


            }
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                if (requestCode == 101) {
                    imgFile = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
                    //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
                    profileImg.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    profileImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == PICK_IMAGE_GALLERY) {

            try {
                if (requestCode == 102) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                    profileImg.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    profileImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.requestPermissions(this, "All permissions are required to run this application", requestCode, permissions);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

}
