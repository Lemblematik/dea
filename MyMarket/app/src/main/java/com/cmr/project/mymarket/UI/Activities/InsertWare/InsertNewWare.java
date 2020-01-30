package com.cmr.project.mymarket.UI.Activities.InsertWare;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter.GalleryItemAdapter;
import com.cmr.project.mymarket.UI.Activities.InsertWare.ModalBottomSheet.Bottom_sheet_gallery;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Utils.ConstantDataManager;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Utils.Libraries;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class InsertNewWare extends AppCompatActivity {
    public static Bitmap receiveBitmap = null;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private RecyclerView recyclerViewGallery;
    private ArrayList<Picture> pictures;
    private WareResponse wareResponse;
    GalleryItemAdapter adapter;
    Handler handler;
    String pathToFile;

    private ImageView imageViewButtonSend;
    private TextView textViewSelectedCount;
    private RelativeLayout constraintLayoutSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity_main);


        imageViewButtonSend = findViewById(R.id.button_send);
        textViewSelectedCount = findViewById(R.id.textViewSeletedCount);
        constraintLayoutSend = findViewById(R.id.layoutSend);

        ImageView imageViewSendDetail = findViewById(R.id.button_send);
        imageViewSendDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Botttooomm Sheett
                /*
                Intent intentDetail = new Intent(InsertNewWare.this, ListImageSelectedActivity.class);

                ArrayList<Picture> picturesSelected = adapter.getAllPictureSelected();
                intentDetail.putParcelableArrayListExtra("listpicture", picturesSelected);

                startActivity(intentDetail);*/
                ArrayList<Picture> picturesSelected = adapter.getAllPictureSelected();
                Bottom_sheet_gallery bottom_sheet_gallery = new Bottom_sheet_gallery(picturesSelected);
                bottom_sheet_gallery.show(getSupportFragmentManager(),"BottomSheet");
            }
        });

        pictures = new ArrayList<>();
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new GalleryItemAdapter(this, pictures, new GalleryItemAdapter.ItemSelectedChangeListener() {
            @Override
            public void onItemSelectedChange(int number) {
                if (number > 0) {
                    constraintLayoutSend.setVisibility(View.VISIBLE);
                    textViewSelectedCount.setText("5");  //number + ""
                } else {
                    constraintLayoutSend.setVisibility(View.GONE);
                }
            }
        });
        recyclerViewGallery.setAdapter(adapter);

        handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(InsertNewWare.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //lay hinh tu camera
            Libraries.requestPermissionStorage(InsertNewWare.this);
        } else {
            new Thread() {

                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            pictures.clear();
                            pictures.addAll(Picture.getGalleryPhotos(InsertNewWare.this));
                            adapter.notifyDataSetChanged();
                            //imageListRecyclerAdapter.addAll(getGalleryPhotos());
                            //checkImageStatus();
                        }
                    });
                    Looper.loop();
                }


            }.start();
        }

        if(Build.VERSION.SDK_INT > 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Thread() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pictures.clear();

                                pictures.addAll(Picture.getGalleryPhotos(InsertNewWare.this));
                                adapter.notifyDataSetChanged();
                                //imageListRecyclerAdapter.addAll(getGalleryPhotos());
                                //checkImageStatus();
                            }
                        });
                        Looper.loop();
                    }
                }.start();

            } else {
                //deny
                Libraries.requestPermissionStorageDeny(InsertNewWare.this);
            }
        }
    }





    //Generate Path
    private static File getImagesDirectory() {
        File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + Environment.getExternalStorageDirectory());//Environment.getExternalStorageDirectory()
        if (!file.mkdirs() && !file.isDirectory()) {
            Log.e("mkdir", "Directory not created");
        }
        return file;
    }

    public static File generateImagePath(String title, String imgType) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        return new File(getImagesDirectory(), title + "_" + sdf.format(new Date()) + "." + imgType);
    }

    //Compress and save
    public boolean compressAndSaveImage(File file, Bitmap bitmap) {
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (result = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
                Log.w("image manager", "Compression success");
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Add to Gallery
    public String addImageToGallery(ContentResolver cr, File filepath) {
        try {
            return MediaStore.Images.Media.insertImage(cr, filepath.toString(),
                    filepath.getName(), "Image Description");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Zusammengefasst




    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        File storedImagePath = generateImagePath("player", "png");
                        if (!compressAndSaveImage(storedImagePath, selectedImage)) {
                            return;
                        }
                        String url = addImageToGallery(this.getContentResolver(), storedImagePath);
                        Picture.getGalleryPhotos(this).add(new Picture(url));
                    }

                    break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                receiveBitmap = bitmap;
            }
        }
    }
    */



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = BitmapFactory.decodeFile(pathToFile);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(this.getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            Picture.getGalleryPhotos(this).add(new Picture(finalFile.toString()));

            //for refresh
            finish();
            startActivity(getIntent());
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }





    public void buttonCameraPressed(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createPhotoFile();

            if(photoFile != null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(this,"com.thecodecity.cameraandroid.fileprovider.new",photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(cameraIntent,1);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

}
