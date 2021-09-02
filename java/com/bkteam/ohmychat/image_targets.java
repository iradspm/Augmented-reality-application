package com.bkteam.ohmychat;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class image_targets extends AppCompatActivity {
    ImageView gear,heart,cube,isometric,ringBase;
    Button start;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef,ref;
    WebView webView;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_targets);
       // toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        gear=findViewById(R.id.gear);
        start=(Button) findViewById(R.id.button_start);
        webView=findViewById(R.id.about_html_text);
        webView.loadUrl("file:///android_asset/index.html");
        webView.setWebViewClient(new WebViewClient());
        heart=findViewById(R.id.heart);
        cube=findViewById(R.id.cube);
        isometric=findViewById(R.id.isometric);
        ringBase=findViewById(R.id.ringBase);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),unityHolder.class));
            }
        });
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadHeart();
            }
        });
        cube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadCube();
            }
        });
        gear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadGear();
            }
        });
        isometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadIsometric();
            }
        });
        ringBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadRing();
            }
        });
    }

    private void downloadRing() {
        storageRef=firebaseStorage.getInstance().getReference();
        ref=storageRef.child("ring.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadRingFiles(image_targets.this,"ring base",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downloadRingFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(),"Download success, check on your download directory",Toast.LENGTH_SHORT).show();
    }


    private void downloadIsometric() {
        storageRef=firebaseStorage.getInstance().getReference();
        ref=storageRef.child("isometric-t.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadIsometricFiles(image_targets.this,"isometric view",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downloadIsometricFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(),"Download success, check on your download directory",Toast.LENGTH_SHORT).show();
    }

    private void downloadCube() {
        storageRef=firebaseStorage.getInstance().getReference();
        ref=storageRef.child("cube.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadCubeFiles(image_targets.this,"cube",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downloadCubeFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(),"Download success, check on your download directory",Toast.LENGTH_SHORT).show();
    }

    private void downloadHeart() {
        storageRef=firebaseStorage.getInstance().getReference();
        ref=storageRef.child("heart.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadHeartFiles(image_targets.this,"heart",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downloadHeartFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(),"Download success, check on your download directory",Toast.LENGTH_SHORT).show();
    }

    private void downloadGear() {
        storageRef=firebaseStorage.getInstance().getReference();
        ref=storageRef.child("gear.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadGearFiles(image_targets.this,"gear teeth",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void downloadGearFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(),"Download success, check on your download directory",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        WebView webz = (WebView) findViewById(R.id.about_html_text);
        if (webz.canGoBack()) {
            webz.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
