package com.example.ia08;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


//touch events and drawing
public class DoodleView extends View {

    private static final float TOLERANCE = 4;
    Context context;
    private ArrayList<Brush> paths = new ArrayList<>();
    private float mx, my;
    private Path path;
    private Paint brush;
    private int strokeWeight, strokeAlpha;
    private String currentColor;
    private Canvas doodle;
    private Bitmap bitmap;
    private Paint bitmapPaint = new Paint(Paint.DITHER_FLAG);


    public DoodleView(Context context,AttributeSet attrs) {
        super(context,attrs);
        setupCanvas();
        this.context = context;
    }

    private void setupCanvas() {
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setDither(true);
        brush.setColor(Color.BLACK);
        brush.setAlpha(0xff);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeCap(Paint.Cap.ROUND);
    }

    public void clear() {
        doodle.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
        init();
    }

    public void setColor(String color) {
        currentColor = color;
    }

    public void setStrokeWeight(int weight) {
        strokeWeight = weight;
    }

    public void setOpacity(int alpha) {
        strokeAlpha = alpha;
    }

    public void init() {
        setupCanvas();
        currentColor = "#000000";
        strokeWeight = 20;
        strokeAlpha = 0xff;
        doodle = new Canvas(bitmap);
        paths = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        doodle = new Canvas(bitmap);
        currentColor = "#000000";
        strokeWeight = 20;
        strokeAlpha = 0xff;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        for (Brush fp : paths) {
            brush.setColor(Color.parseColor(fp.color));
            brush.setStrokeWidth(fp.strokeWeight);
            brush.setAlpha(fp.strokeAlpha);
            doodle.drawPath(fp.path, brush);
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        path = new Path();
        Brush fp = new Brush(currentColor, strokeWeight, strokeAlpha, path);
        paths.add(fp);

        path.reset();
        path.moveTo(x, y);

        mx = x;
        my = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mx);
        float dy = Math.abs(y - my);

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);
            mx = x;
            my = y;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(mx,my);
                invalidate();
                break;
        }
        return true;
    }

    public Bitmap save() { return bitmap;
    }
}
