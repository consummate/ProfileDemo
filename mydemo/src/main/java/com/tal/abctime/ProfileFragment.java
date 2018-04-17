package com.tal.abctime;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tal.abctime.model.User;
import com.tal.abctime.utils.LocalConstants;
import com.tal.abctime.utils.UserDaoOperator;
import com.tal.abctime.view.CircleImageView;
import com.tal.abctime.view.EditTextWithDel;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";
    public static final String PHOTO_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/tal/" + "temp.jpg";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 0;


    @BindView(R.id.imageButton_profile)
    CircleImageView mProfileImage;
    @BindView(R.id.imageButton_takephoto)
    ImageButton mBtnTakePhoto;

    @BindView(R.id.profile_btn_submit)
    Button mBtnSubmit;

    @BindView(R.id.profile_et_name)
    EditTextWithDel mEtName;
    @BindView(R.id.profile_age_rg)
    RadioGroup mRgAge;
    @BindView(R.id.profile_gender_rg)
    RadioGroup mRgGender;
    @BindView(R.id.profile_btn_old_account)
    Button mBtnOldAccount;


    private View mRootView;
    Uri mContentUri;
    boolean isRequireCheck;
    Dialog mProfileDialog;
    String mUserAge;
    String mUserGender;

    /**
     * 定义三种状态
     */
    private static final int REQUESTCODE_PIC = 1;//相册
    private static final int REQUESTCODE_CAM = 2;//相机
    private static final int REQUESTCODE_CUT = 3;//图片裁剪

    private Bitmap mBitmap;
    private Context mContext;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        isRequireCheck = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.profile_settings_page, container, false);
        ButterKnife.bind(this, mRootView);

        mEtName.setOnTextClearListener(new EditTextWithDel.OnTextClearListener() {
            @Override
            public void onEmpty() {
                checkSubmitButtonState();
            }
            @Override
            public void onNotEmpty() {
                Log.i("sy__test","onNotEmpty: mBtnSubmit.isEnabled():"+mBtnSubmit.isEnabled());
                if (!mBtnSubmit.isEnabled()) {
                    checkSubmitButtonState();
                }
            }
        });
        if (mRgAge!=null) {
            mRgAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = mRootView.findViewById(checkedId);
                    if (radioButton!=null) {
                        Log.i("sy__test","Age:"+radioButton.getText().toString());

                        mUserAge = radioButton.getText().toString();
                    }
                    checkSubmitButtonState();
                }
            });
        }

        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = mRootView.findViewById(checkedId);
                if (radioButton!=null) {
                    Log.i("sy__test","Gender:"+group.getCheckedRadioButtonId());
                    mUserGender = radioButton.getText().toString();
                }
                checkSubmitButtonState();
            }
        });

        checkSubmitButtonState();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getUserInfo();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Background thread destroyed");
    }

    @OnClick({R.id.imageButton_profile, R.id.imageButton_takephoto})
    void callImage() {
        showProfileDialog();
    }


    //TODO: skip
    @OnClick(R.id.profile_btn_old_account)
    void hasOldAccount() {
        callLoginPage();
    }

    @OnClick(R.id.profile_btn_submit)
    void submitInfo() {

        SharedPreferences sp = mContext.getSharedPreferences(LocalConstants.LOCAL_SP, mContext.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean("isfirst", false);

//        et.putString("name",mEtName.getText().toString());
//        et.putInt("age",2);
//        et.putInt("gender",0);

        et.apply();

        User user = new User();
        user.setId(1);
        user.setAge(mUserAge);
        user.setGender(mUserGender);
        user.setName(mEtName.getText().toString());

        Toast.makeText(mContext, "sumbit:"+user, Toast.LENGTH_SHORT).show();

        callLoginPage();

    }

   void callLoginPage() {
       Intent intent = new Intent(getActivity(), LoginActivity.class);
       startActivity(intent);
   }


    void checkSubmitButtonState() {
        Log.i("sy__test","radiostate:"+mRgGender.getCheckedRadioButtonId());
        if (mRgAge.getCheckedRadioButtonId() != -1 && mRgGender.getCheckedRadioButtonId() != -1
                && mEtName.getText().length() > 0) {
            mBtnSubmit.setEnabled(true);
        } else {
            mBtnSubmit.setEnabled(false);
        }
    }



    private void showProfileDialog() {
        TextView update_dialog_TK, update_dialog_PZ, update_dialog_cancel;
        View view = getLayoutInflater().inflate(R.layout.dialog_photo_type, null);
        mProfileDialog = new Dialog(mContext, R.style.dialog_animal);

//        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
//        mScreenWidth = metric.widthPixels;
        mProfileDialog.setContentView(view, new ViewGroup.LayoutParams(
                metric.widthPixels
                , metric.heightPixels
        ));
        //图库
        update_dialog_TK = (TextView) view.findViewById(R.id.update_dialog_TK);
        update_dialog_TK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, REQUESTCODE_PIC);
                mProfileDialog.dismiss();
            }
        });
        //相机拍照
        update_dialog_PZ = (TextView) view.findViewById(R.id.update_dialog_PZ);
        update_dialog_PZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动系统相机
                requestPermissions();
                mProfileDialog.dismiss();
            }
        });
        //取消
        update_dialog_cancel = (TextView) view.findViewById(R.id.update_dialog_cancel);
        update_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileDialog.dismiss();
            }
        });
        mProfileDialog.show();
    }

    /**
     * 调用相机
     */
    private void openCamera() {

        File file = new File(PHOTO_DIR);
        if (!file.exists()){
            file.getParentFile().mkdirs();
        }

        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageCaptureUri;
        // 判断7.0android系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mContentUri = FileProvider.getUriForFile(mContext,
                    "com.tal.abctime.fileProvider",
                    file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mContentUri);
        } else {
//            mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
            mImageCaptureUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        }
        startActivityForResult(intent, REQUESTCODE_CAM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("sy__test","resultCode:"+resultCode+",requestCode="+requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_CAM:
//                    startPhotoZoom(Uri.fromFile(mFile));
                    Uri pictur;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
                        pictur = mContentUri;
                    } else {
                        pictur = Uri.fromFile(new File(PHOTO_DIR));
                    }
                    startPhotoZoom(pictur);
                    break;
                case REQUESTCODE_PIC:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case REQUESTCODE_CUT:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

        private void setPicToView(Intent data) {
            Bundle bundle =  data.getExtras();
            if (bundle != null){
                //这里也可以做文件上传
                mBitmap = bundle.getParcelable("data");
                mProfileImage.setImageBitmap(mBitmap);
            }
        }

        /**
         * 打开系统图片裁剪功能
         * @param uri
         */
        private void startPhotoZoom(Uri uri) {
            Log.i("sy__test","startPhotoZoom");
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(uri,"image/*");
            intent.putExtra("crop",true);
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            intent.putExtra("outputX",300);
            intent.putExtra("outputY",300);
            intent.putExtra("scale",true); //黑边
            intent.putExtra("scaleUpIfNeeded",true); //黑边
            intent.putExtra("return-data",true);
            intent.putExtra("noFaceDetection",true);
            startActivityForResult(intent,REQUESTCODE_CUT);
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(),
                            "READ_CONTACTS Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                return;
            }
        }

    }

      public void requestPermissions() {
          Log.i("sy__test","requestPermissions");
          if (ContextCompat.checkSelfPermission(getActivity(),
                  Manifest.permission.WRITE_EXTERNAL_STORAGE)
                  != PackageManager.PERMISSION_GRANTED) {
              Log.i("sy__test","requestPermissions2");
              // Should we show an explanation?
              if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                      Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                  // Show an expanation to the user *asynchronously* -- don't block
                  // this thread waiting for the user's response! After the user
                  // sees the explanation, try again to request the permission.

              } else {

                  ActivityCompat.requestPermissions(getActivity(),
                          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                          MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

              }
          } else {
              openCamera();
          }
      }

}
