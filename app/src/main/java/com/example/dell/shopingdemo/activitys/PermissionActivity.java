package com.example.dell.shopingdemo.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.base.IActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionActivity extends IActivity {

    @BindView(R.id.to_camear_btn)
    Button toCamearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        ButterKnife.bind(this);
    }

    public void toCall(){
        camear();
    }



    @OnClick(R.id.to_camear_btn)
    public void onClick(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                new AlertDialog.Builder(this).setTitle("title")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.CAMERA},1);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }else {
                ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.CAMERA},1);
            }
        }else {
            camear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "User Permission", Toast.LENGTH_SHORT).show();
                camear();
            }else {
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void camear() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
