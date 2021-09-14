package com.whh.seriorui.textdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义 TextView
 * 实现在画布上任意位置绘制文本，并实现文字渐变效果
 * author:wuhuihui 2021.09.07
 */
public class SimpleColorChangeTextView extends AppCompatTextView {

    private final String mText = "好好学习";//成员变量

    private float mPercent = 0.0f;

    public float getPercent() {
        return mPercent;
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        invalidate();//重绘
    }

    public SimpleColorChangeTextView(Context context) {
        super(context);
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制文字
        Paint paint = new Paint();
        paint.setTextSize(80);
        float baseline = 100;
        canvas.drawText(mText, 0, baseline, paint); //在(0,100)处绘制文本

        drawCenterLineX(canvas); //绘制垂直中线
        drawCenterLineY(canvas); //绘制水平中线

        float x = getWidth() / 2;

        canvas.drawText(mText, x, baseline, paint); //在(getWidth() / 2,100)处绘制文本

        paint.setTextAlign(Paint.Align.CENTER); //设置文字居中对齐
        //在(getWidth() / 2,baseline + paint.getFontSpacing())处绘制文本，并居中
        canvas.drawText(mText, x, baseline + paint.getFontSpacing(), paint);

        paint.setTextAlign(Paint.Align.RIGHT); //设置文字右对齐
        //在(getWidth() / 2, baseline + paint.getFontSpacing() * 2)处绘制文本，并右对齐
        canvas.drawText(mText, x, baseline + paint.getFontSpacing() * 2, paint);

        //文字渐变效果
        drawCenterText(canvas); //第一层 黑色
        drawCenterText1(canvas); //第一层 红色
    }

    /**
     * 渐变效果：第一层绘制黑色
     * @param canvas
     */
    private void drawCenterText(final Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(80);
        float width = paint.measureText(mText);
        paint.setTextAlign(Paint.Align.LEFT);
        float left = getWidth() / 2 - width / 2;
        float left_x = left + width * mPercent;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = getHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        Rect rect = new Rect((int) left_x, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, left, baseline, paint);
        canvas.restore();
    }

    /**
     * 渐变效果：第二层绘制红色
     * @param canvas
     */
    private void drawCenterText1(final Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(80);
        float width = paint.measureText(mText);
        paint.setTextAlign(Paint.Align.LEFT);
        float left = getWidth() / 2 - width / 2;
        float right = left + width * mPercent;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = getHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        Rect rect = new Rect((int) left, 0, (int) right, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, left, baseline, paint);
        canvas.restore();

    }

    private void drawCenterLineX(final Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }

    private void drawCenterLineY(final Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
    }

}
