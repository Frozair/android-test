package com.frozair.androidgames;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

@TargetApi(5)
public class MultiTouchTest extends Activity implements OnTouchListener{
    StringBuilder builder = new StringBuilder();
    TextView textView;
    // 10 because android can only track 10 fingers at a time
    float[] x = new float[10];
    float[] y = new float[10];
    boolean[] touched = new boolean[10];
    int[] id = new int[10];

    private void updateTextView()
    {
        builder.setLength(0);
        for(int i = 0; i < 10; i++)
        {
            builder.append(touched[i]);
            builder.append(", ");
            builder.append(id[i]);
            builder.append(", ");
            builder.append(x[i]);
            builder.append(", ");
            builder.append(y[i]);
            builder.append("\n");
        }
        textView.setText(builder.toString());
    }

    @Override
    public void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        textView = new TextView(this);
        textView.setText("Touch and drag (multiple fingers supported)");
        textView.setOnTouchListener(this);
        setContentView(textView);
        for(int i = 0; i < 10; i++)
            id[i] = -1;
        updateTextView();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                            MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerCount = motionEvent.getPointerCount();
        for(int i= 0; i < 10; i++)
        {
            if(i >= pointerCount)
            {
                touched[i] = false;
                id[i] = -1;
                continue;
            }
            if(motionEvent.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex)
            {
                // if it's an up/down/cancel/out event, mask the id to see if we should process
                // it for the touch point
                continue;
            }
            int pointerId = motionEvent.getPointerId(i);
            switch(action)
            {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touched[i] = true;
                    id[i] = pointerId;
                    x[i] = (int) motionEvent.getX(i);
                    y[i] = (int) motionEvent.getY(i);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    touched[i] = false;
                    id[i] = -1;
                    x[i] = (int) motionEvent.getX(i);
                    y[i] = (int) motionEvent.getY(i);
                    break;
                case MotionEvent.ACTION_MOVE:
                    touched[i] = true;
                    id[i] = pointerId;
                    x[i] = (int) motionEvent.getX(i);
                    y[i] = (int) motionEvent.getY(i);
                    break;
            }
        }
        updateTextView();
        return true;
    }
}
