package com.sharon.trollbabatelugu;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


public class AddRemovePagesActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkPage1, checkPage2, checkPage3, checkPage4, checkPage5, checkPage6, checkPage7, checkPage8,
            checkPage9, checkPage10, checkPage11, checkPage12, checkPage13, checkPage14,
            checkPage15;
    Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remove_layout);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_to_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            linearLayout.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
        }

        preferences = new Preferences(this);

        initialiseCheckBoxes();
        setChecks();
        setCheckListeners();
    }


    private void initialiseCheckBoxes() {
        checkPage1 = (CheckBox) findViewById(R.id.checkPage1);
        checkPage2 = (CheckBox) findViewById(R.id.checkPage2);
        checkPage3 = (CheckBox) findViewById(R.id.checkPage3);
        checkPage4 = (CheckBox) findViewById(R.id.checkPage4);
        checkPage5 = (CheckBox) findViewById(R.id.checkPage5);
        checkPage6 = (CheckBox) findViewById(R.id.checkPage6);
        checkPage7 = (CheckBox) findViewById(R.id.checkPage7);
        checkPage8 = (CheckBox) findViewById(R.id.checkPage8);
        checkPage9 = (CheckBox) findViewById(R.id.checkPage9);
        checkPage10 = (CheckBox) findViewById(R.id.checkPage10);
        checkPage11 = (CheckBox) findViewById(R.id.checkPage11);
        checkPage12 = (CheckBox) findViewById(R.id.checkPage12);
        checkPage13 = (CheckBox) findViewById(R.id.checkPage13);
        checkPage14 = (CheckBox) findViewById(R.id.checkPage14);
        checkPage15 = (CheckBox) findViewById(R.id.checkPage15);

    }

    private void setChecks() {
        checkPage1.setChecked(preferences.getCheckPref("page1"));
        checkPage2.setChecked(preferences.getCheckPref("page2"));
        checkPage3.setChecked(preferences.getCheckPref("page3"));
        checkPage4.setChecked(preferences.getCheckPref("page4"));
        checkPage5.setChecked(preferences.getCheckPref("page5"));
        checkPage6.setChecked(preferences.getCheckPref("page6"));
        checkPage7.setChecked(preferences.getCheckPref("page7"));
        checkPage8.setChecked(preferences.getCheckPref("page8"));
        checkPage9.setChecked(preferences.getCheckPref("page9"));
        checkPage10.setChecked(preferences.getCheckPref("page10"));
        checkPage11.setChecked(preferences.getCheckPref("page11"));
        checkPage12.setChecked(preferences.getCheckPref("page12"));
        checkPage13.setChecked(preferences.getCheckPref("page13"));
        checkPage14.setChecked(preferences.getCheckPref("page14"));
        checkPage15.setChecked(preferences.getCheckPref("page15"));

    }

    private void setCheckListeners() {
        checkPage1.setOnCheckedChangeListener(this);
        checkPage2.setOnCheckedChangeListener(this);
        checkPage3.setOnCheckedChangeListener(this);
        checkPage4.setOnCheckedChangeListener(this);
        checkPage5.setOnCheckedChangeListener(this);
        checkPage6.setOnCheckedChangeListener(this);
        checkPage7.setOnCheckedChangeListener(this);
        checkPage8.setOnCheckedChangeListener(this);
        checkPage9.setOnCheckedChangeListener(this);
        checkPage10.setOnCheckedChangeListener(this);
        checkPage11.setOnCheckedChangeListener(this);
        checkPage12.setOnCheckedChangeListener(this);
        checkPage13.setOnCheckedChangeListener(this);
        checkPage14.setOnCheckedChangeListener(this);
        checkPage15.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.checkPage1:
                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page1, 1);
                    checkPage1.setChecked(true);
//                    preferences.putCheckPref("page1", true);
                } else {
//                    MainActivity.result.removeItem(1);
                    checkPage1.setChecked(true);
//                    preferences.putCheckPref("page1", false);
                }
                break;
            case R.id.checkPage2:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page2, 2);
                    checkPage2.setChecked(true);
                    preferences.putCheckPref("page2", true);
                } else {
                    MainActivity.result.removeItem(2);
                    checkPage2.setChecked(false);
                    preferences.putCheckPref("page2", false);
                }
                break;
            case R.id.checkPage3:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page3, 3);
                    checkPage3.setChecked(true);
                    preferences.putCheckPref("page3", true);
                } else {
                    MainActivity.result.removeItem(3);
                    checkPage3.setChecked(false);
                    preferences.putCheckPref("page3", false);
                }
                break;
            case R.id.checkPage4:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page4, 4);
                    checkPage4.setChecked(true);
                    preferences.putCheckPref("page4", true);
                } else {
                    MainActivity.result.removeItem(4);
                    checkPage4.setChecked(false);
                    preferences.putCheckPref("page4", false);
                }
                break;
            case R.id.checkPage5:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page5, 5);
                    checkPage5.setChecked(true);
                    preferences.putCheckPref("page5", true);
                } else {
                    MainActivity.result.removeItem(5);
                    checkPage5.setChecked(false);
                    preferences.putCheckPref("page5", false);
                }
                break;
            case R.id.checkPage6:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page6, 5);
                    checkPage6.setChecked(true);
                    preferences.putCheckPref("page6", true);
                } else {
                    MainActivity.result.removeItem(6);
                    checkPage6.setChecked(false);
                    preferences.putCheckPref("page6", false);
                }
                break;
            case R.id.checkPage7:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page7, 5);
                    checkPage7.setChecked(true);
                    preferences.putCheckPref("page7", true);
                } else {
                    MainActivity.result.removeItem(7);
                    checkPage7.setChecked(false);
                    preferences.putCheckPref("page7", false);
                }
                break;
            case R.id.checkPage8:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page8, 5);
                    checkPage8.setChecked(true);
                    preferences.putCheckPref("page8", true);
                } else {
                    MainActivity.result.removeItem(8);
                    checkPage8.setChecked(false);
                    preferences.putCheckPref("page8", false);
                }
                break;
            case R.id.checkPage9:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page9, 5);
                    checkPage9.setChecked(true);
                    preferences.putCheckPref("page9", true);
                } else {
                    MainActivity.result.removeItem(9);
                    checkPage9.setChecked(false);
                    preferences.putCheckPref("page9", false);
                }
                break;
            case R.id.checkPage10:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page10, 5);
                    checkPage10.setChecked(true);
                    preferences.putCheckPref("page10", true);
                } else {
                    MainActivity.result.removeItem(10);
                    checkPage10.setChecked(false);
                    preferences.putCheckPref("page10", false);
                }
                break;
            case R.id.checkPage11:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page11, 5);
                    checkPage11.setChecked(true);
                    preferences.putCheckPref("page11", true);
                } else {
                    MainActivity.result.removeItem(11);
                    checkPage11.setChecked(false);
                    preferences.putCheckPref("page11", false);
                }
                break;
            case R.id.checkPage12:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page12, 5);
                    checkPage12.setChecked(true);
                    preferences.putCheckPref("page12", true);
                } else {
                    MainActivity.result.removeItem(12);
                    checkPage12.setChecked(false);
                    preferences.putCheckPref("page12", false);
                }
                break;
            case R.id.checkPage13:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page13, 5);
                    checkPage13.setChecked(true);
                    preferences.putCheckPref("page13", true);
                } else {
                    MainActivity.result.removeItem(13);
                    checkPage13.setChecked(false);
                    preferences.putCheckPref("page13", false);
                }
                break;
            case R.id.checkPage14:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page14, 5);
                    checkPage14.setChecked(true);
                    preferences.putCheckPref("page14", true);
                } else {
                    MainActivity.result.removeItem(14);
                    checkPage14.setChecked(false);
                    preferences.putCheckPref("page14", false);
                }
                break;
            case R.id.checkPage15:
                if (compoundButton.isChecked()) {
                    MainActivity.result.addItemAtPosition(MainActivity.item_page15, 5);
                    checkPage15.setChecked(true);
                    preferences.putCheckPref("page15", true);
                } else {
                    MainActivity.result.removeItem(15);
                    checkPage15.setChecked(false);
                    preferences.putCheckPref("page15", false);
                }
                break;
//            case R.id.checkPage16:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page16, 5);
//                    checkPage16.setChecked(true);
//                    preferences.putCheckPref("page16", true);
//                } else {
//                    MainActivity.result.removeItem(16);
//                    checkPage16.setChecked(false);
//                    preferences.putCheckPref("page16", false);
//                }
//                break;
//            case R.id.checkPage17:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page17, 5);
//                    checkPage17.setChecked(true);
//                    preferences.putCheckPref("page17", true);
//                } else {
//                    MainActivity.result.removeItem(17);
//                    checkPage17.setChecked(false);
//                    preferences.putCheckPref("page17", false);
//                }
//                break;
//            case R.id.checkPage18:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page18, 5);
//                    checkPage18.setChecked(true);
//                    preferences.putCheckPref("page18", true);
//                } else {
//                    MainActivity.result.removeItem(18);
//                    checkPage18.setChecked(false);
//                    preferences.putCheckPref("page18", false);
//                }
//                break;
//            case R.id.checkPage19:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page19, 5);
//                    checkPage19.setChecked(true);
//                    preferences.putCheckPref("page19", true);
//                } else {
//                    MainActivity.result.removeItem(19);
//                    checkPage19.setChecked(false);
//                    preferences.putCheckPref("page19", false);
//                }
//                break;
//            case R.id.checkPage20:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page20, 5);
//                    checkPage20.setChecked(true);
//                    preferences.putCheckPref("page20", true);
//                } else {
//                    MainActivity.result.removeItem(20);
//                    checkPage20.setChecked(false);
//                    preferences.putCheckPref("page20", false);
//                }
//                break;
//            case R.id.checkPage21:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page21, 5);
//                    checkPage21.setChecked(true);
//                    preferences.putCheckPref("page21", true);
//                } else {
//                    MainActivity.result.removeItem(21);
//                    checkPage21.setChecked(false);
//                    preferences.putCheckPref("page21", false);
//                }
//                break;
//            case R.id.checkPage22:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page22, 5);
//                    checkPage22.setChecked(true);
//                    preferences.putCheckPref("page22", true);
//                } else {
//                    MainActivity.result.removeItem(22);
//                    checkPage22.setChecked(false);
//                    preferences.putCheckPref("page22", false);
//                }
//                break;
//            case R.id.checkPage23:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page23, 5);
//                    checkPage23.setChecked(true);
//                    preferences.putCheckPref("page23", true);
//                } else {
//                    MainActivity.result.removeItem(23);
//                    checkPage23.setChecked(false);
//                    preferences.putCheckPref("page23", false);
//                }
//                break;
//            case R.id.checkPage24:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page24, 5);
//                    checkPage24.setChecked(true);
//                    preferences.putCheckPref("page24", true);
//                } else {
//                    MainActivity.result.removeItem(24);
//                    checkPage24.setChecked(false);
//                    preferences.putCheckPref("page24", false);
//                }
//                break;
//            case R.id.checkPage25:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page25, 5);
//                    checkPage25.setChecked(true);
//                    preferences.putCheckPref("page25", true);
//                } else {
//                    MainActivity.result.removeItem(25);
//                    checkPage25.setChecked(false);
//                    preferences.putCheckPref("page25", false);
//                }
//                break;
//            case R.id.checkPage26:
//                if (compoundButton.isChecked()) {
//                    MainActivity.result.addItemAtPosition(MainActivity.item_page26, 5);
//                    checkPage26.setChecked(true);
//                    preferences.putCheckPref("page26", true);
//                } else {
//                    MainActivity.result.removeItem(26);
//                    checkPage26.setChecked(false);
//                    preferences.putCheckPref("page26", false);
//                }
//                break;
        }
    }
}
