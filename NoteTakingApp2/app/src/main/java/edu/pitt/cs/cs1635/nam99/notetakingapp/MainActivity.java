//Nick Miller
//Individual Assignment 2
//Thursday February 8

package edu.pitt.cs.cs1635.nam99.notetakingapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.content.*;
import android.widget.Button;

import android.util.AttributeSet;



public class MainActivity extends AppCompatActivity {

    private Sub_Canvas sc;
    private Button setting_button, clear_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sc = (Sub_Canvas) findViewById(R.id.drawing);
        clearListener();
        settingListener();

    }

    public void clearListener() {
        clear_button = (Button) findViewById(R.id.clearButton);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.clear();
            }
        }) ;
    }

    public void settingListener() {
        setting_button = (Button) findViewById(R.id.settingButton);
        final Context context = this;
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, settings.class);
                startActivityForResult(intent, 1);
            }
        }) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //System.out.println("In here");
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)

                String colorSet = data.getStringExtra("setColor");
                System.out.println(colorSet);
                sc.setColor(colorSet);
            }
        }
    }
}

class Sub_Canvas extends View{
    private Paint paint = new Paint();
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private int paintColor;


    //private GestureDetector gestureDetector;

    protected Sub_Canvas(Context context, AttributeSet ats) {
        super(context, ats);
        //gestureDetector = new GestureDetector(context, new GestureListener());
        drawPath = new Path();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }


    // skipping measure calculation and drawing

    // delegate the event to the gesture detector


//    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//        // event when double tap occurs
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            float x = e.getX();
//            float y = e.getY();
//
//            System.out.println("Double Tap" + ",Tapped at: (" + x + "," + y + ")");
//
//            return true;
//        }
//    }

    public void setColor(String color){
        invalidate();
        paintColor = Color.parseColor(color);
        paint.setColor(paintColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, paint);
        canvas.drawPath(drawPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //boolean eventConsumed=gestureDetector.onTouchEvent(event);
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, paint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;

    }

    public void clear(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }


}

//    public void showSimpleColorPicker(View view) {
//        Class nextActivity = ColorPickerActivity.class;
//        Intent intent = new Intent(this, nextActivity);
//        this.startActivity(intent);
//    }
