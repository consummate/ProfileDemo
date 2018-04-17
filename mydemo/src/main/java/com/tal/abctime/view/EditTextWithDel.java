package com.tal.abctime.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tal.abctime.R;

/**
 * Created by irene on 2018/4/16.
 */

public class EditTextWithDel extends AppCompatEditText {
    private Context mContext;
    private Drawable mImgDelete;
    private OnTextClearListener mOnTextClearListener;
    private boolean isDelVisible = true;

    public EditTextWithDel(Context context) {
        this(context, null);
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.EditTextWithDel, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.EditTextWithDel_del_visible:
                    isDelVisible = a.getBoolean(attr, true);
            }
        }
        init();
    }

    public interface OnTextClearListener {
        void onEmpty();
        void onNotEmpty();
    }

    public void setOnTextClearListener(OnTextClearListener listener) {
        mOnTextClearListener = listener;
    }

    private void init() {
        if (isDelVisible) {
            mImgDelete = mContext.getResources().getDrawable(R.drawable.btn_delete_all, null);
            setDrawable();
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDrawable();
                if (mOnTextClearListener!=null) {
                    if (length()<1) {
                        mOnTextClearListener.onEmpty();
                    } else {
                        mOnTextClearListener.onNotEmpty();
                    }
                }
            }
        });

    }

    private void setDrawable() {
        if (length() <1) {
            setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mImgDelete,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mImgDelete != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right-50;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
