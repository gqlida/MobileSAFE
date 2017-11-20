package com.dastudio.mobilesafe.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dastudio.mobilesafe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    final int START_INSTALL_APP = 0;
    private String url = "http://10.0.2.2:8080/update.json";
    private OkHttpClient mOkhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        SplashTimer();
        CheckUpdate();
    }

    private int CheckPackageCode() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 99;
        }
    }

    private void CheckUpdate() {
        //builder     OKHttp + request + okhttp.newcall + call.enqueue --> response  .body
        mOkhttp = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();
        //request
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //通过okhttp调用newcall传入request 获取call对象
        Call call = mOkhttp.newCall(request);
        //异步请求网络
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                enterMainActivity();
                System.out.println("fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject json = new JSONObject(result);
                    String content = json.getString("vtitle");
                    String url = json.getString("url");
                    int versionCode = json.getInt("vc");
                    if (versionCode > CheckPackageCode()) {

                        showDownDialog(url);

                    }else{
                        enterMainActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    enterMainActivity();
                }

            }
        });

    }

    private void showDownDialog(final String url) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Update")
                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DownApk(url);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                enterMainActivity();
                            }
                        }).show()
                ;
            }
        });
    }


    private void DownApk(String url) {
        final ProgressDialog Dialog = new ProgressDialog(this);
        Dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Dialog.setTitle("Downloading......");
        Dialog.show();

        //创建路径
        final File file = new File(Environment.getExternalStorageDirectory(),"safe.apk");
        //获取请求对象
        final Request request = new Request.Builder().url(url).get().build();
        //创建一个call对象
        Call call = mOkhttp.newCall(request);
        //异步请求网络 enqueue
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                enterMainActivity();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    long contentMax = response.body().contentLength();
                    Dialog.setMax((int) contentMax);
                    //从响应中获取输入流
                    InputStream is = response.body().byteStream();
                    int currentPosition = 0;
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while((len = is.read(buffer)) != -1){
                        fos.write(buffer,0,len);
                        currentPosition += len;
                        Dialog.setProgress(currentPosition);
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(file);
                    //开启安装应用的intent
                    intent.setDataAndType(uri,"application/vnd.android.package-archive");
                    //调用onActivityResult来回调code的正确性
                    startActivityForResult(intent,START_INSTALL_APP);

                } catch (IOException e) {
                    e.printStackTrace();
                    enterMainActivity();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_INSTALL_APP) {
            enterMainActivity();
        }
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
//        System.out.println("~~~~~~~~~~~~~~");
    }

    private void SplashTimer() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
