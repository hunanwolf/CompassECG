package com.test720.auxiliary.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author J
 *         一个自定义view 实现a-z的竖直绘制，和监听滑动事件
 */
public class SideBar extends View {
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    //	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
//			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
//			"W", "X", "Y", "Z", "#" };
    public static ArrayList<String> b = new ArrayList<>();
    private int choose = -1;
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i("kangl", "1111111");
    }

    public void setB(ArrayList<String> b) {
        this.b = b;
    }

    public ArrayList<String> getB() {
        return b;
    }

    public void flush() {
        this.invalidate();
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("kangl", "222222222");
        b.add("#");
        b.add("A");
        b.add("B");
        b.add("C");
        b.add("D");
        b.add("E");
        b.add("F");
        b.add("G");
        b.add("H");
        b.add("I");
        b.add("J");
        b.add("K");
        b.add("L");
        b.add("M");
        b.add("N");
        b.add("O");
        b.add("P");
        b.add("Q");
        b.add("R");
        b.add("S");
        b.add("T");
        b.add("U");
        b.add("V");
        b.add("W");
        b.add("X");
        b.add("Y");
        b.add("Z");


    }

    public SideBar(Context context) {
        super(context);
        Log.i("kangl", "3333333333");
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() - 250;
        int width = getWidth();
        int singleHeight = height / b.size();

        for (int i = 0; i < b.size(); i++) {
            paint.setColor(Color.parseColor("#878787"));
            //	paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setTextSize(32);
            if (i == choose) {
                paint.setColor(Color.parseColor("#029ff0"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(b.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b.get(i), xPos, yPos + 70, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.size());

        switch (action) {
            case MotionEvent.ACTION_UP:
                //设置选中时背景的颜色
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                // setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}