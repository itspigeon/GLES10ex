package jp.ac.titech.itpro.sdl.gles10ex;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private final static String TAG = "MainActivity";

    private GLSurfaceView glView;
    private SimpleRenderer renderer;
    private SeekBar rotationBarX, rotationBarY, rotationBarZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        glView = (GLSurfaceView) findViewById(R.id.glview);

        rotationBarX = (SeekBar) findViewById(R.id.rotation_bar_x);
        rotationBarY = (SeekBar) findViewById(R.id.rotation_bar_y);
        // rotationBarZ = (SeekBar) findViewById(R.id.rotation_bar_z);
        rotationBarX.setOnSeekBarChangeListener(this);
        rotationBarY.setOnSeekBarChangeListener(this);
        // rotationBarZ.setOnSeekBarChangeListener(this);

        renderer = new SimpleRenderer();
        renderer.addObj(new Cube(0.5f, 0, 0.2f, -3));
        renderer.addObj(new Pyramid(0.5f, 0, 0, 0));
        glView.setRenderer(renderer);

        glView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, final MotionEvent motionEvent) {
                if (motionEvent != null) {
                    final float normalizedX = motionEvent.getX() / (float) view.getWidth();
                    final float normalizedY = motionEvent.getY() / (float) view.getHeight();

                    if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        glView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                renderer.handleDrag(normalizedY, normalizedX);
                            }
                        });
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        glView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                renderer.handleDragStart(normalizedY, normalizedX);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == rotationBarX)
            renderer.setRotationX(progress);
        else if (seekBar == rotationBarY)
            renderer.setRotationY(progress);
        // else if (seekBar == rotationBarZ)
        //     renderer.setRotationZ(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}
