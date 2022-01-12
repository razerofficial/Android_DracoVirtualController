package com.example.dracovirtualcontroller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final Float AXIS_SCALER = 4f;

    private TextView txtSystem = null;
    private TextView txtKeyCode = null;
    private ImageView imgControllerA = null;
    private ImageView imgControllerX = null;
    private ImageView imgControllerY = null;
    private ImageView imgControllerB = null;
    private ImageView imgControllerL1 = null;
    private ImageView imgControllerL2 = null;
    private ImageView imgControllerL3 = null;
    private ImageView imgControllerR1 = null;
    private ImageView imgControllerR2 = null;
    private ImageView imgControllerR3 = null;
    private ImageView imgControllerDpadDown = null;
    private ImageView imgControllerDpadLeft = null;
    private ImageView imgControllerDpadRight = null;
    private ImageView imgControllerDpadUp = null;
    private ImageView imgControllerLS = null;
    private ImageView imgControllerRS = null;
    private ImageView imgButtonB = null;
    private ImageView imgDpadDown = null;
    private ImageView imgDpadLeft = null;
    private ImageView imgDpadRight = null;
    private ImageView imgDpadUp = null;
    private ImageView imgLeftStick = null;
    private ImageView imgLeftBumper = null;
    private ImageView imgLeftTrigger = null;
    private ImageView imgButtonA = null;
    private ImageView imgRightStick = null;
    private ImageView imgRightBumper = null;
    private ImageView imgRightTrigger = null;
    private ImageView imgLeftThumb = null;
    private ImageView imgRightThumb = null;
    private ImageView imgButtonX = null;
    private ImageView imgButtonY = null;
    private ImageView imgControllerTap = null;
    private ImageView imgControllerMenu = null;
    private ImageView imgControllerSelect = null;
    private ImageView imgControllerStart = null;

    private static final int MAX_CONTROLLERS = 1;

    private static SparseArray<HashMap<Integer, Float>> sAxisValues = new SparseArray<HashMap<Integer, Float>>();
    private static SparseArray<HashMap<Integer, Boolean>> sButtonValues = new SparseArray<HashMap<Integer, Boolean>>();

    private static int getPlayerNum() {
        return 0;
    }

    static {
        for (int index = 0; index < MAX_CONTROLLERS; ++index) {
            HashMap<Integer, Float> axisMap = new HashMap<Integer, Float>();
            axisMap.put(MotionEvent.AXIS_HAT_X, 0f);
            axisMap.put(MotionEvent.AXIS_HAT_Y, 0f);
            sAxisValues.put(index, axisMap);
            HashMap<Integer, Boolean> buttonMap = new HashMap<Integer, Boolean>();
            sButtonValues.put(index, buttonMap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ViewGroup mainLayout = (ViewGroup)this.findViewById(android.R.id.content);
        mainLayout.setKeepScreenOn(true);

        txtSystem = (TextView)findViewById(R.id.txtSystem);
        txtKeyCode = (TextView)findViewById(R.id.txtKeyCode);
        imgControllerA = (ImageView)findViewById(R.id.imgControllerA);
        imgControllerX = (ImageView)findViewById(R.id.imgControllerX);
        imgControllerY = (ImageView)findViewById(R.id.imgControllerY);
        imgControllerB = (ImageView)findViewById(R.id.imgControllerB);
        imgControllerL1 = (ImageView)findViewById(R.id.imgControllerL1);
        imgControllerL2 = (ImageView)findViewById(R.id.imgControllerL2);
        imgControllerL3 = (ImageView)findViewById(R.id.imgControllerl3);
        imgControllerR1 = (ImageView)findViewById(R.id.imgControllerR1);
        imgControllerR2 = (ImageView)findViewById(R.id.imgControllerR2);
        imgControllerR3 = (ImageView)findViewById(R.id.imgControllerR3);
        imgControllerDpadDown = (ImageView)findViewById(R.id.imgControllerDpadDown);
        imgControllerDpadLeft = (ImageView)findViewById(R.id.imgControllerDpadLeft);
        imgControllerDpadRight = (ImageView)findViewById(R.id.imgControllerDpadRight);
        imgControllerDpadUp = (ImageView)findViewById(R.id.imgControllerDpadUp);
        imgControllerLS = (ImageView)findViewById(R.id.imgControllerLS);
        imgControllerRS = (ImageView)findViewById(R.id.imgControllerRS);
        imgDpadDown = (ImageView)findViewById(R.id.imgDpadDown);
        imgDpadLeft = (ImageView)findViewById(R.id.imgDpadLeft);
        imgDpadRight = (ImageView)findViewById(R.id.imgDpadRight);
        imgDpadUp = (ImageView)findViewById(R.id.imgDpadUp);
        imgLeftStick = (ImageView)findViewById(R.id.imgLeftStick);
        imgLeftBumper = (ImageView)findViewById(R.id.imgLeftBumper);
        imgLeftTrigger = (ImageView)findViewById(R.id.imgLeftTrigger);
        imgRightStick = (ImageView)findViewById(R.id.imgRightStick);
        imgRightBumper = (ImageView)findViewById(R.id.imgRightBumper);
        imgRightTrigger = (ImageView)findViewById(R.id.imgRightTrigger);
        imgLeftThumb = (ImageView)findViewById(R.id.imgLeftThumb);
        imgRightThumb = (ImageView)findViewById(R.id.imgRightThumb);
        imgButtonA = (ImageView)findViewById(R.id.imgButtonA);
        imgButtonB = (ImageView)findViewById(R.id.imgButtonB);
        imgButtonX = (ImageView)findViewById(R.id.imgButtonX);
        imgButtonY = (ImageView)findViewById(R.id.imgButtonY);
        imgControllerTap = (ImageView)findViewById(R.id.imgControllerTap);
        imgControllerMenu = (ImageView)findViewById(R.id.imgControllerMenu);
        imgControllerSelect = (ImageView)findViewById(R.id.imgControllerSelect);
        imgControllerStart = (ImageView)findViewById(R.id.imgControllerStart);

        mainLayout.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imgControllerTap.setVisibility(View.VISIBLE);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                imgControllerTap.setVisibility(View.INVISIBLE);
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        //super.onBackPressed();
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        /*
        if (null != txtKeyCode) {
            InputDevice device = motionEvent.getDevice();
            if (null != device) {
                txtKeyCode.setText("Original MotionEvent device=" + device.getName());
            }
        }
        */
        //DebugInput.debugMotionEvent(motionEvent);
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        //Log.i(TAG, "dispatchKeyEvent");
        if (null != txtKeyCode) {
            InputDevice device = keyEvent.getDevice();
            if (null != device) {
                txtKeyCode.setText("Original KeyEvent device=" + device.getName() + " KeyCode=(" + keyEvent.getKeyCode() + ") "
                        + DebugInput.debugGetButtonName(keyEvent.getKeyCode())+" source="+keyEvent.getSource());
            }
        }
        if (keyEvent.getKeyCode() == DracoInput.BUTTON_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    void updateDPad(int playerNum) {
        if (null == sButtonValues.get(playerNum).get(DracoInput.BUTTON_DPAD_LEFT) &&
                null == sButtonValues.get(playerNum).get(DracoInput.BUTTON_DPAD_RIGHT)) {
            float dpadX = sAxisValues.get(playerNum).get(MotionEvent.AXIS_HAT_X);
            if (dpadX > 0.25f) {
                imgDpadRight.setVisibility(View.VISIBLE);
            } else {
                imgDpadRight.setVisibility(View.INVISIBLE);
            }
            if (dpadX < -0.25f) {
                imgDpadLeft.setVisibility(View.VISIBLE);
            } else {
                imgDpadLeft.setVisibility(View.INVISIBLE);
            }
        }

        if (null == sButtonValues.get(playerNum).get(DracoInput.BUTTON_DPAD_DOWN) &&
                null == sButtonValues.get(playerNum).get(DracoInput.BUTTON_DPAD_UP)) {
            float dpadY = sAxisValues.get(playerNum).get(MotionEvent.AXIS_HAT_Y);
            if (dpadY > 0.25f) {
                imgDpadDown.setVisibility(View.VISIBLE);
            } else {
                imgDpadDown.setVisibility(View.INVISIBLE);
            }

            if (dpadY < -0.25f) {
                imgDpadUp.setVisibility(View.VISIBLE);
            } else {
                imgDpadUp.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        //Log.i(TAG, "onGenericMotionEvent");
        //DebugInput.debugMotionEvent(motionEvent);
        //DebugInput.debugAxisMotionEvent(motionEvent);

        int playerNum = getPlayerNum();
        /*
        if (null != txtKeyCode2) {
            InputDevice device = motionEvent.getDevice();
            if (null != device) {
                txtKeyCode.setText("Original MotionEvent device=" + device.getName());
            }
        }
        */

        float dpadX = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
        float dpadY = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);
        sAxisValues.get(playerNum).put(MotionEvent.AXIS_HAT_X, dpadX);
        sAxisValues.get(playerNum).put(MotionEvent.AXIS_HAT_Y, dpadY);
        updateDPad(playerNum);

        float lsX = motionEvent.getAxisValue(DracoInput.AXIS_LS_X);
        float lsY = motionEvent.getAxisValue(DracoInput.AXIS_LS_Y);
        float rsX = motionEvent.getAxisValue(DracoInput.AXIS_RS_X);
        float rsY = motionEvent.getAxisValue(DracoInput.AXIS_RS_Y);
        float l2 = motionEvent.getAxisValue(DracoInput.AXIS_L2);
        float r2 = motionEvent.getAxisValue(DracoInput.AXIS_R2);

        //rotate input by N degrees to match image
        float degrees = 135;
        float radians = degrees / 180f * 3.14f;
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        imgLeftStick.setX(AXIS_SCALER * (lsX * cos - lsY * sin));
        imgLeftThumb.setY(AXIS_SCALER * (lsX * cos - lsY * sin));

        imgLeftStick.setY(AXIS_SCALER * (lsX * sin + lsY * cos));
        imgLeftThumb.setY(AXIS_SCALER * (lsX * sin + lsY * cos));

        imgRightStick.setX(AXIS_SCALER * (rsX * cos - rsY * sin));
        imgRightThumb.setX(AXIS_SCALER * (rsX * cos - rsY * sin));

        imgRightStick.setY(AXIS_SCALER * (rsX * sin + rsY * cos));
        imgRightThumb.setY(AXIS_SCALER * (rsX * sin + rsY * cos));

        //Log.i(TAG, "Unrecognized GenericMotionEvent="+motionEvent.getAction());

        if (l2 > 0.25f) {
            imgLeftTrigger.setVisibility(View.VISIBLE);
        } else {
            imgLeftTrigger.setVisibility(View.INVISIBLE);
        }

        if (r2 > 0.25f) {
            imgRightTrigger.setVisibility(View.VISIBLE);
        } else {
            imgRightTrigger.setVisibility(View.INVISIBLE);
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        Log.i(TAG, "onKeyDown keyCode="+keyCode+" source="+keyEvent.getSource());

        if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK) {
            return false;
        }

        int playerNum = getPlayerNum();

        switch (keyCode)
        {
            case DracoInput.BUTTON_L1:
                imgLeftBumper.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_L2:
                imgLeftTrigger.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_L3:
                imgLeftStick.setVisibility(View.INVISIBLE);
                imgLeftThumb.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_R1:
                imgRightBumper.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_R2:
                imgRightTrigger.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_R3:
                imgRightStick.setVisibility(View.INVISIBLE);
                imgRightThumb.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_X:
                imgButtonX.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_B:
                imgButtonB.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_Y:
                imgButtonY.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_A:
                imgButtonA.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_DPAD_DOWN:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_DOWN, true);
                    imgDpadDown.setVisibility(View.VISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_LEFT:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_LEFT, true);
                    imgDpadLeft.setVisibility(View.VISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_RIGHT:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(playerNum).put(DracoInput.BUTTON_DPAD_RIGHT, true);
                    imgDpadRight.setVisibility(View.VISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_UP:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_UP, true);
                    imgDpadUp.setVisibility(View.VISIBLE);
                }
                break;
            case DracoInput.BUTTON_MENU:
                imgControllerMenu.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_SELECT:
                imgControllerSelect.setVisibility(View.VISIBLE);
                break;
            case DracoInput.BUTTON_START:
                imgControllerStart.setVisibility(View.VISIBLE);
                break;
            default:
                Log.i(TAG, "Unrecognized KeyDown="+keyCode);
                break;
        }

        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        Log.i(TAG, "onKeyUp keyCode="+keyCode+" source="+keyEvent.getSource());

        if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK) {
            return false;
        }

        int playerNum = getPlayerNum();

        switch (keyCode)
        {
            case DracoInput.BUTTON_L1:
                imgLeftBumper.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_L2:
                imgLeftTrigger.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_L3:
                imgLeftStick.setVisibility(View.VISIBLE);
                imgLeftThumb.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_R1:
                imgRightBumper.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_R2:
                imgRightTrigger.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_R3:
                imgRightStick.setVisibility(View.VISIBLE);
                imgRightThumb.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_X:
                imgButtonX.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_B:
                imgButtonB.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_Y:
                imgButtonY.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_A:
                imgButtonA.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_DPAD_DOWN:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_DOWN, false);
                    imgDpadDown.setVisibility(View.INVISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_LEFT:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_LEFT, false);
                    imgDpadLeft.setVisibility(View.INVISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_RIGHT:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(0).put(DracoInput.BUTTON_DPAD_RIGHT, false);
                    imgDpadRight.setVisibility(View.INVISIBLE);
                }
                break;
            case DracoInput.BUTTON_DPAD_UP:
                if (keyEvent.getSource() == InputDevice.SOURCE_JOYSTICK ) {
                    updateDPad(playerNum);
                } else {
                    sButtonValues.get(playerNum).put(DracoInput.BUTTON_DPAD_UP, false);
                    imgDpadUp.setVisibility(View.INVISIBLE);
                }
                break;
            case DracoInput.BUTTON_MENU:
                imgControllerMenu.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_SELECT:
                imgControllerSelect.setVisibility(View.INVISIBLE);
                break;
            case DracoInput.BUTTON_START:
                imgControllerStart.setVisibility(View.INVISIBLE);
                break;
        }

        return true;
    }

}