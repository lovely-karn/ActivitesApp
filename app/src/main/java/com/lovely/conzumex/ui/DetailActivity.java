package com.lovely.conzumex.ui;

import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lovely.conzumex.R;
import com.lovely.conzumex.viewmodel.ActivityViewModel;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.lovely.conzumex.constants.AppConstant.ACTIVITY_IMAGE;
import static com.lovely.conzumex.constants.AppConstant.ACTIVITY_NAME;
import static com.lovely.conzumex.constants.AppConstant.ACTIVITY_TENNIS;
import static com.lovely.conzumex.constants.AppConstant.BUNDLE_ISPOPUP;

/**
 * DetailActivity
 */
public class DetailActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        ActivityListFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    private ActivityViewModel activityViewModel;

    //utility instances
    private TimePickerDialog timePickerDialog;

    private AlertDialog.Builder dialogBuilderDuration, dialogBuilderDistance;

    // selected img, name of activity
    private String activityImage, activityName;

    public DetailActivity() {
        // Required empty public constructor
    }

    // ids
    private LinearLayoutCompat llCard;
    private TextView tvCardInfo;
    private View includeCard;
    private ImageView ivActivity, ivDistance;
    private TextView tvActivityName;
    private RelativeLayout rlArrow;
    private AppCompatTextView tvStartTime, tvDuration, tvDistance;
    private Spinner spinnerTennis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // fetch the values from recycler view
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        // get intent data
        Intent intent = getIntent();
        if (intent != null) {

            activityImage = intent.getStringExtra(ACTIVITY_IMAGE);
            activityName = intent.getStringExtra(ACTIVITY_NAME);
        }

        init();

        // now set the values
        setData();

        // attach pickers to the views
        setClickListenersToViews();

        // handle spinner
        handleSpinner();
    }

    private void init() {

        // finding views id
        llCard = findViewById(R.id.llCard);
        tvCardInfo = findViewById(R.id.tvCardInfo);
        includeCard = findViewById(R.id.includeCard);
        ivActivity = findViewById(R.id.ivActivity);
        ivDistance = findViewById(R.id.ivDistance);
        tvActivityName = findViewById(R.id.tvActivityName);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvDuration = findViewById(R.id.tvDuration);
        tvDistance = findViewById(R.id.tvDistance);
        rlArrow = findViewById(R.id.rlArrow);
        spinnerTennis = findViewById(R.id.spinnerTennis);
    }

    public ActivityViewModel getViewModel() {

        return activityViewModel;
    }

    private void handleSpinner() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tennis_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerTennis.setAdapter(adapter);
    }

    private void setClickListenersToViews() {

        // start time to open up time picker -
        // with timepicker showing current time of the system when it is opened up
        tvStartTime.setOnClickListener(this);

        // set onCLickListener for -> duration, distance
        tvDistance.setOnClickListener(this);

        tvDuration.setOnClickListener(this);

        rlArrow.setOnClickListener(this);

        // for spinner
        spinnerTennis.setOnItemSelectedListener(this);
    }

    private void showActivityListFragmentAsDialog() {

        // create ActivityListFragment->
        ActivityListFragment fragment = new ActivityListFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_ISPOPUP, true);

        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();

        fragment.setArguments(bundle);

        // attach activity list fragment
        fragment.show(ft1, fragment.getClass().getSimpleName());
    }

    private String getTime(final int hr, final int min) {
        Time tme = new Time(hr, min, 0);
        //seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

    private void setImgAndNameOfSelectedActivity(final String img, final String activityName) {

        // set activity name
        tvActivityName.setText(activityName);

        // set image
        // had to use glide to set the image ..

        checkIfNameIsOfTennis(activityName);

    }

    private void checkIfNameIsOfTennis(String activityName) {

        if (activityName != null && activityName.equalsIgnoreCase(ACTIVITY_TENNIS)) {

            // hide tvDistance & attach spinner & also change the image
            tvDistance.setVisibility(View.GONE);
            spinnerTennis.setVisibility(View.VISIBLE);

            //TODO :- for now same image is all over
            ivDistance.setImageResource(R.drawable.ic_nature_24dp);

        } else {

            tvDistance.setVisibility(View.VISIBLE);
            spinnerTennis.setVisibility(View.GONE);

            // TODO
            ivDistance.setImageResource(R.drawable.ic_place_24dp);
        }
    }

    private void setData() {

        // in card info text

        // getEmojiByUnicode(0x1F3C3) -> runner
        // getEmojiByUnicode(0x1F3CA) -> swimmer
        // getEmojiByUnicode(0x1F3C3) -> cycling
        // getEmojiByUnicode(0x1F3C3) -> weight lifter
        tvCardInfo.setText(String.format("Please log your activity%s%s%s%s " +
                        " details in the card below:",
                getEmojiByUnicode(0x1F3C3),
                getEmojiByUnicode(0x1F3CA),
                getEmojiByUnicode(0xD83D),
                getEmojiByUnicode(0xD83C)));

        // set image,name of selected activity
        Log.e("selected card data", "" + activityName);
        tvActivityName.setText(activityName);

        checkIfNameIsOfTennis(activityName);
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void openHrMinPicker() {

        final NumberPicker np, np2, np3, np4;
        final String[] npValue, np2Value, np3Value, np4Value;

        dialogBuilderDuration = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_hr_minute, null);
        dialogBuilderDuration.setView(dialogView);

        // setting all 3 pickers values
        np = dialogView.findViewById(R.id.numberPicker1);
        np2 = dialogView.findViewById(R.id.numberPicker2);
        np3 = dialogView.findViewById(R.id.numberPicker3);
        np4 = dialogView.findViewById(R.id.numberPicker4);

        // then setting the values
        // for first number picker
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        np.setWrapSelectorWheel(true);
        np2.setWrapSelectorWheel(true);
        np3.setWrapSelectorWheel(true);

        // set first picker values
        String[] values = new String[24];

        for (int i = 0; i <= 23; i++) {
            values[i] = String.valueOf(i);
        }
        np.setMaxValue(values.length - 1);
        np.setMinValue(0);
        np.setDisplayedValues(values);

        npValue = values;

        // SET second PICKER VALUES
        final String[] values2 = {"hour"};

        np2Value = values2;
        np2.setMaxValue(values2.length - 1);
        np2.setMinValue(0);
        np2.setDisplayedValues(values2);


        // set second picker vLUES
        String[] values3 = new String[60];

        for (int i = 0; i <= 59; i++) {
            values3[i] = String.valueOf(i);
        }
        np3Value = values3;

        np3.setMaxValue(values3.length - 1);
        np3.setMinValue(0);
        np3.setDisplayedValues(values3);

        // SET THIRD PICKER VALUES
        final String[] values4 = {"min"};

        np4Value = values4;
        np4.setMaxValue(values4.length - 1);
        np4.setMinValue(0);
        np4.setDisplayedValues(values4);

        dialogBuilderDuration.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO :- proper time format parsing is pending ..

                if (np3.getValue() == 0) {
                    tvDuration.setText(String.format("%s %s",
                            npValue[np.getValue()],
                            np2Value[np2.getValue()]));
                } else {
                    tvDuration.setText(String.format("%s %s %s %s",
                            npValue[np.getValue()],
                            np2Value[np2.getValue()],
                            np3Value[np3.getValue()],
                            np4Value[np4.getValue()]));
                }
                dialog.dismiss();
            }
        });

        dialogBuilderDuration.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilderDuration.show();
    }

    private void openKmMilesPicker() {

        final NumberPicker np, np2, np3;
        final String[] npValue, np2Value, np3Value;

        dialogBuilderDistance = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_km_miles, null);
        dialogBuilderDistance.setView(dialogView);


        // setting all 3 pickers values
        np = dialogView.findViewById(R.id.numberPicker1);
        np2 = dialogView.findViewById(R.id.numberPicker2);
        np3 = dialogView.findViewById(R.id.numberPicker3);


        // then setting the values
        // for first number picker
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        np.setWrapSelectorWheel(true);
        np2.setWrapSelectorWheel(true);
        np3.setWrapSelectorWheel(true);

        // set first picker values
        String[] values = new String[251];

        for (int i = 0; i <= 250; i++) {
            values[i] = String.valueOf(i);
        }
        np.setMaxValue(values.length - 1);
        np.setMinValue(0);
        np.setDisplayedValues(values);

        npValue = values;

        // set second picker vLUES
        final String[] values2 = {".0",
                ".1",
                ".2",
                ".3",
                ".4",
                ".5",
                ".6",
                ".7",
                ".8",
                ".9",
        };

        np2Value = values2;

        np2.setMaxValue(values2.length - 1);
        np2.setMinValue(0);
        np2.setDisplayedValues(values2);

        // SET THIRD PICKER VALUES
        final String[] values3 = {"km", "miles"};

        np3Value = values3;
        np3.setMaxValue(values3.length - 1);
        np3.setMinValue(0);
        np3.setDisplayedValues(values3);


        dialogBuilderDistance.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (np3.getValue() == 0) {
                    tvDistance.setText(String.format("%s %s",
                            npValue[np.getValue()],
                            np3Value[np3.getValue()]));
                } else {
                    tvDistance.setText(String.format("%s%s %s",
                            npValue[np.getValue()],
                            np2Value[np2.getValue()],
                            np3Value[np3.getValue()]));
                }
                dialog.dismiss();
            }
        });

        dialogBuilderDistance.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilderDistance.show();
    }

    @Override
    public void onItemClicked(Bundle bundle) {

        if (bundle != null) {

            if (bundle.getString(ACTIVITY_IMAGE) != null) {
                activityImage = bundle.getString(ACTIVITY_IMAGE);
            }

            if (bundle.getString(ACTIVITY_NAME) != null) {
                activityName = bundle.getString(ACTIVITY_NAME);
            }

            setImgAndNameOfSelectedActivity(activityImage, activityName);
        }
    }

    private void openTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        // adding TimePickerDialog
        new TimePickerDialog(DetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                String time = getTime(hourOfDay, minutes);
                tvStartTime.setText(time);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartTime:
                openTimePicker();
                break;

            case R.id.tvDuration:
                // open duration picker
                openHrMinPicker();
                break;

            case R.id.tvDistance:
                // open distance picker
                openKmMilesPicker();
                break;

            case R.id.rlArrow:
                // launch that  fragment of listing :-
                showActivityListFragmentAsDialog();
                break;

            default:
                break;
        }
    }
}
