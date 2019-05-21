package com.example.librarymanager.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.IDataListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public abstract class AbstractCustomFragment extends Fragment implements IDataListener {
    static final int CAMERA = 0, GALLERY = 1;

    public static final String ADD_BOOK = "add_book";
    public static final String LIST_BOOK = "list_book";
    static final String EDIT_BOOK = "edit_book";

    AbstractCustomFragment fragment;
    FragmentTransaction fragmentTransaction;

    void capturePicture() {
//        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(captureImage, CAMERA);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA);
        }
    }

    void choosePicture() {
        Intent pickImage = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, GALLERY);
    }

    String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    Bitmap convertStringToBitmap(String encodedImageString) {
        Bitmap bitmap = null;
        try{
            byte [] encodeByte=Base64.decode(encodedImageString,Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e){
            Log.d("Exception", e.getMessage());
        }
        return bitmap;
    }

    Bitmap getScaledVersion(Bitmap bitmap, ImageView imageView) {
        return Bitmap.createScaledBitmap(bitmap,
                imageView.getWidth(),
                imageView.getHeight(),
                false);
    }

    public void finish() {
        if (getFragmentManager() != null) {
            if (getFragmentManager().findFragmentByTag(LIST_BOOK) == null) {
                fragment = new AddBookFragment();
            } else {
                fragment = (AbstractCustomFragment) getFragmentManager().findFragmentByTag(LIST_BOOK);
            }

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, fragment, LIST_BOOK);

            if (getFragmentManager().findFragmentByTag(LIST_BOOK) == null) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.commit();
        }
    }
}
