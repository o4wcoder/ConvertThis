package com.fourthwardcoder.android.convertthis;
import com.fourthwardcoder.android.convertthis.ConvertUtil.Length;
import com.fourthwardcoder.android.convertthis.ConvertUtil.Temperature;
import com.fourthwardcoder.android.convertthis.ConvertUtil.Volume;
import com.fourthwardcoder.android.convertthis.ConvertUtil.Weight;
import com.fourthwardcoder.android.tabexample.R;

import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * ConvertThisFragment
 * 
 * Main Fragment for the measurement converstion utilitiy
 * 
 * @author Chris Hare
 * 1/24/2015
 */
public class ConvertThisFragment extends Fragment implements Constants{

	/****************************************************************/
	/*                        Constants                             */
	/****************************************************************/
	final static String TAG = "ConvertThisFragment";
	/****************************************************************/
	/*                      Local Data                              */
	/****************************************************************/
	TabName tab;
	View view;
	Spinner unitDropdown;
	
    //Get TextViews of Values
	TextView tvRow0Value;
	TextView tvRow1Value; 
	TextView tvRow2Value; 
	TextView tvRow3Value; 
	TextView tvRow4Value; 
	TextView tvRow5Value;
	TextView tvRow6Value; 
	TextView tvRow7Value; 
	
	//GetTextViews of Units
	TextView tvRow0Unit; 
	TextView tvRow1Unit; 
	TextView tvRow2Unit; 
	TextView tvRow3Unit; 
	TextView tvRow4Unit; 
	TextView tvRow5Unit; 
	TextView tvRow6Unit;
	TextView tvRow7Unit; 

    /****************************************************************************/
	/*                           Override Methods                               */
	/****************************************************************************/
    @Override
    public void onCreate(Bundle savedInstance) {
    	super.onCreate(savedInstance);
       
    	Bundle data = getArguments();
    	int i = data.getInt("idx");
    	tab = TabName.values()[i];
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState){
		view = inflater.inflate(R.layout.tab, container, false);
				
		/********************* Set up dropdown box spinner **********************/
	    unitDropdown = (Spinner)view.findViewById(R.id.unitDropDownSpinner);

		ArrayAdapter<String> adapter;
		String[] items = {};
		
		if(tab == TabName.WEIGHT) {
		    items = ConvertUtil.WeightItemNames;
		}
		else if(tab == TabName.LENGTH) {
			 items = ConvertUtil.LengthItemNames;
		}
		else if(tab == TabName.VOLUME) {
			 items = ConvertUtil.VolumeItemNames;
		}
		else if(tab == TabName.TEMPERATURE) {
			 items = ConvertUtil.TemperatureItemNames;
		}

		adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
				android.R.layout.simple_spinner_dropdown_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		unitDropdown.setAdapter(adapter);
	   
		unitDropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			   public void onItemSelected(AdapterView<?> parent, View view, 
			            int pos, long id) {
			        // An item was selected. You can retrieve the selected item using
			        // parent.getItemAtPosition(pos)
				   Log.d(TAG,"Spinner selected at " + parent.getItemIdAtPosition(pos));
				  //((TextView) parent.getChildAt(pos)).setTextColor(Color.BLUE);
				   setDataBasedOnUnitSelected(parent.getItemIdAtPosition(pos));
			    }

			    public void onNothingSelected(AdapterView<?> parent) {}
			
		});
		
		/******************************* Setup Input Text Box *************************/
		TextView inputTextView = (TextView)view.findViewById(R.id.editUnitText);

		//Set up listener for textView
		inputTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				setDataBasedOnUnitSelected(unitDropdown.getSelectedItemId());
			}

			//Unused abstact methods
			@Override
			public void afterTextChanged(Editable s) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
		});
		
        //Get TextViews of Values
		tvRow0Value = (TextView) view.findViewById(R.id.TextView_Row0_Value);
		tvRow1Value = (TextView) view.findViewById(R.id.TextView_Row1_Value);
		tvRow2Value = (TextView) view.findViewById(R.id.TextView_Row2_Value);
		tvRow3Value = (TextView) view.findViewById(R.id.TextView_Row3_Value);
		tvRow4Value = (TextView) view.findViewById(R.id.TextView_Row4_Value);
		tvRow5Value = (TextView) view.findViewById(R.id.TextView_Row5_Value);
		tvRow6Value = (TextView) view.findViewById(R.id.TextView_Row6_Value);
		tvRow7Value = (TextView) view.findViewById(R.id.TextView_Row7_Value);
		
		//GetTextViews of Units
		tvRow0Unit = (TextView)view.findViewById(R.id.TextView_Row0_Unit);
		tvRow1Unit = (TextView)view.findViewById(R.id.TextView_Row1_Unit);
		tvRow2Unit = (TextView)view.findViewById(R.id.TextView_Row2_Unit);
		tvRow3Unit = (TextView)view.findViewById(R.id.TextView_Row3_Unit);
		tvRow4Unit = (TextView)view.findViewById(R.id.TextView_Row4_Unit);
		tvRow5Unit = (TextView)view.findViewById(R.id.TextView_Row5_Unit);
		tvRow6Unit = (TextView)view.findViewById(R.id.TextView_Row6_Unit);
		tvRow7Unit = (TextView)view.findViewById(R.id.TextView_Row7_Unit);
		
		//Set Row visibility
		setRowVisibility();
		
		return view;
	}
	
	/*****************************************************************************/
	/*                              Private Methods                              */
	/*****************************************************************************/
	/**
	 * Sets which row should be visible on each measurement type/tab
	 */
	private void setRowVisibility() {
		
		TableRow tRow0 = (TableRow)view.findViewById(R.id.TableRow0);
		TableRow tRow1 = (TableRow)view.findViewById(R.id.TableRow1);
		TableRow tRow2 = (TableRow)view.findViewById(R.id.TableRow2);
		TableRow tRow3 = (TableRow)view.findViewById(R.id.TableRow3);
		TableRow tRow4 = (TableRow)view.findViewById(R.id.TableRow4);
		TableRow tRow5 = (TableRow)view.findViewById(R.id.TableRow5);
		TableRow tRow6 = (TableRow)view.findViewById(R.id.TableRow6);
		TableRow tRow7 = (TableRow)view.findViewById(R.id.TableRow7);
		
		if(tab == TabName.WEIGHT) {
			tRow0.setVisibility(View.VISIBLE);
			tRow1.setVisibility(View.VISIBLE);
			tRow2.setVisibility(View.VISIBLE);
			tRow3.setVisibility(View.VISIBLE);
			tRow4.setVisibility(View.VISIBLE);
			tRow5.setVisibility(View.VISIBLE);
			tRow6.setVisibility(View.INVISIBLE);
			tRow7.setVisibility(View.INVISIBLE);
		    
		}
		else if(tab == TabName.LENGTH) {
			tRow0.setVisibility(View.VISIBLE);
			tRow1.setVisibility(View.VISIBLE);
			tRow2.setVisibility(View.VISIBLE);
			tRow3.setVisibility(View.VISIBLE);
			tRow4.setVisibility(View.VISIBLE);
			tRow5.setVisibility(View.VISIBLE);
			tRow6.setVisibility(View.VISIBLE);
			tRow7.setVisibility(View.VISIBLE);
			 
		}
		else if(tab == TabName.VOLUME) {
			tRow0.setVisibility(View.VISIBLE);
			tRow1.setVisibility(View.VISIBLE);
			tRow2.setVisibility(View.VISIBLE);
			tRow3.setVisibility(View.VISIBLE);
			tRow4.setVisibility(View.INVISIBLE);
			tRow5.setVisibility(View.INVISIBLE);
			tRow6.setVisibility(View.INVISIBLE);
			tRow7.setVisibility(View.INVISIBLE);
			
		}
		else if(tab == TabName.TEMPERATURE) {
			tRow0.setVisibility(View.VISIBLE);
			tRow1.setVisibility(View.VISIBLE);
			tRow2.setVisibility(View.INVISIBLE);
			tRow3.setVisibility(View.INVISIBLE);
			tRow4.setVisibility(View.INVISIBLE);
			tRow5.setVisibility(View.INVISIBLE);
			tRow6.setVisibility(View.INVISIBLE);
			tRow7.setVisibility(View.INVISIBLE);
			 
		}
	}
	
	/**
	 * Set which type of measurement should be used based
	 * on selection from the dropdown box
	 * 
	 * @param pos dropdown box position showing the measure unit
	 */
	private void setDataBasedOnUnitSelected(long pos) {
		
		if(tab == TabName.WEIGHT) {
		    setWeightData(pos);
		}
		else if(tab == TabName.LENGTH) {
			setLengthData(pos);
		}
		else if(tab == TabName.VOLUME) {
			setVolumeData(pos);
		}
		else if(tab == TabName.TEMPERATURE) {
			setTemperatureData(pos);
		}
		
	}
	
	/**
	 * Set weight data
	 * @param pos dropdown box position showing the measure unit
	 */
	private void setWeightData(long pos) {

		TextView tvInput = (TextView)view.findViewById(R.id.editUnitText);
        String inputStr = tvInput.getText().toString();
        
        //Can't have empty input, set to 0
        if(inputStr.equals("") || inputStr.equals("."))
        	inputStr = "0";
        
		//Select dropdown position
		switch((int)pos){
		case 0:
			//pounds to pounds
			tvRow0Value.setText(inputStr);
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//pounds to oz
			tvRow1Value.setText(ConvertUtil.WeightConvert(Weight.POUNDS_TO_OZ,inputStr));
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//pounds to kg
			tvRow2Value.setText(ConvertUtil.WeightConvert(Weight.POUNDS_TO_KG,inputStr));
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//pounds to g
			tvRow3Value.setText(ConvertUtil.WeightConvert(Weight.POUNDS_TO_G,inputStr));
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//pounds to mg
			tvRow4Value.setText(ConvertUtil.WeightConvert(Weight.POUNDS_TO_MG,inputStr));
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//pounds to tons
			tvRow5Value.setText(ConvertUtil.WeightConvert(Weight.POUNDS_TO_TONS,inputStr));
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		case 1:
			//oz to pounds
			tvRow0Value.setText(ConvertUtil.WeightConvert(Weight.OZ_TO_POUNDS,inputStr));
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//oz to oz
			tvRow1Value.setText(inputStr);
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//oz to kg
			tvRow2Value.setText(ConvertUtil.WeightConvert(Weight.OZ_TO_KG,inputStr));
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//oz to g
			tvRow3Value.setText(ConvertUtil.WeightConvert(Weight.OZ_TO_G,inputStr));
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//oz to mg
			tvRow4Value.setText(ConvertUtil.WeightConvert(Weight.OZ_TO_MG,inputStr));
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//oz to tons
			tvRow5Value.setText(ConvertUtil.WeightConvert(Weight.OZ_TO_TONS,inputStr));
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		case 2:
			//kg to pounds
			tvRow0Value.setText(ConvertUtil.WeightConvert(Weight.KG_TO_POUNDS,inputStr));
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//kg to oz
			tvRow1Value.setText(ConvertUtil.WeightConvert(Weight.KG_TO_OZ,inputStr));
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//kg to kg
			tvRow2Value.setText(inputStr);
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//kg to g
			tvRow3Value.setText(ConvertUtil.WeightConvert(Weight.KG_TO_G,inputStr));
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//kg to mg
			tvRow4Value.setText(ConvertUtil.WeightConvert(Weight.KG_TO_MG,inputStr));
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//kg to tons
			tvRow5Value.setText(ConvertUtil.WeightConvert(Weight.KG_TO_TONS,inputStr));
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		case 3:
			//g to pounds
			tvRow0Value.setText(ConvertUtil.WeightConvert(Weight.G_TO_POUNDS,inputStr));
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//g to oz
			tvRow1Value.setText(ConvertUtil.WeightConvert(Weight.G_TO_OZ,inputStr));
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//g to kg
			tvRow2Value.setText(ConvertUtil.WeightConvert(Weight.G_TO_KG,inputStr));
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//g to g
			tvRow3Value.setText(inputStr);
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//g to mg
			tvRow4Value.setText(ConvertUtil.WeightConvert(Weight.G_TO_MG,inputStr));
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//g to tons
			tvRow5Value.setText(ConvertUtil.WeightConvert(Weight.G_TO_TONS,inputStr));
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		case 4:
			//mg to pounds
			tvRow0Value.setText(ConvertUtil.WeightConvert(Weight.MG_TO_POUNDS,inputStr));
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//mg to oz
			tvRow1Value.setText(ConvertUtil.WeightConvert(Weight.MG_TO_OZ,inputStr));
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//mg to kg
			tvRow2Value.setText(ConvertUtil.WeightConvert(Weight.MG_TO_KG,inputStr));
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//mg to g
			tvRow3Value.setText(ConvertUtil.WeightConvert(Weight.MG_TO_G,inputStr));
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//mg to mg
			tvRow4Value.setText(inputStr);
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//mg to tons
			tvRow5Value.setText(ConvertUtil.WeightConvert(Weight.MG_TO_TONS,inputStr));
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		case 5:
			//tons to pounds
			tvRow0Value.setText(ConvertUtil.WeightConvert(Weight.TONS_TO_POUNDS,inputStr));
			tvRow0Unit.setText(ConvertUtil.WeightItemNames[0]);
			
			//tons to oz
			tvRow1Value.setText(ConvertUtil.WeightConvert(Weight.TONS_TO_OZ,inputStr));
			tvRow1Unit.setText(ConvertUtil.WeightItemNames[1]);
			
			//tons to kg
			tvRow2Value.setText(ConvertUtil.WeightConvert(Weight.TONS_TO_KG,inputStr));
			tvRow2Unit.setText(ConvertUtil.WeightItemNames[2]);
			
			//tons to g
			tvRow3Value.setText(ConvertUtil.WeightConvert(Weight.TONS_TO_G,inputStr));
			tvRow3Unit.setText(ConvertUtil.WeightItemNames[3]);
			
			//tons to mg
			tvRow4Value.setText(ConvertUtil.WeightConvert(Weight.TONS_TO_MG,inputStr));
			tvRow4Unit.setText(ConvertUtil.WeightItemNames[4]);
			
			//tons to tons
			tvRow5Value.setText(inputStr);
			tvRow5Unit.setText(ConvertUtil.WeightItemNames[5]);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Set length data
	 * @param pos dropdown box position showing the measure unit
	 */
	private void setLengthData(long pos) {
		
		TextView tvInput = (TextView)view.findViewById(R.id.editUnitText);
        String inputStr = tvInput.getText().toString();
        
        //Can't have empty input, set to 0
        if(inputStr.equals("") || inputStr.equals("."))
        	inputStr = "0";
        
		//Select dropdown position
		switch((int)pos){
		case 0:
			//in to in
			tvRow0Value.setText(inputStr);
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//in to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//in to yard
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//in to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//in to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//in to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//in to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//in to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.IN_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 1:
			//feet to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//feet to feet
			tvRow1Value.setText(inputStr);
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//feet to yard
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//feet to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//feet to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//feet to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//feet to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//feet to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.FEET_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 2:
			//yards to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//yards to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//yards to yards
			tvRow2Value.setText(inputStr);
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//yards to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//yards to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//yards to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//yards to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//yards to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.YARD_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 3:
			//miles to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//miles to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//miles to yards
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//miles to miles
			tvRow3Value.setText(inputStr);
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//miles to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//miles to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//miles to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//miles to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.MILE_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 4:
			//mm to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//mm to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//mm to yards
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//mm to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//mm to mm
			tvRow4Value.setText(inputStr);
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//mm to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//mm to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//mm to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.MM_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[5]);
			break;
		case 5:
			//cm to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//cm to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//cm to yards
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//cm to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//cm to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//cm to cm
			tvRow5Value.setText(inputStr);
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//cm to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//cm to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.CM_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 6:
			//m to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.M_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//m to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.M_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//m to yards
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.M_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//m to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.M_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//m to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.M_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//m to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.M_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//m to m
			tvRow6Value.setText(inputStr);
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//m to km
			tvRow7Value.setText(ConvertUtil.LengthConvert(Length.M_TO_KM,inputStr));
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		case 7:
			//km to in
			tvRow0Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_IN,inputStr));
			tvRow0Unit.setText(ConvertUtil.LengthItemNames[0]);
			
			//km to feet
			tvRow1Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_FEET,inputStr));
			tvRow1Unit.setText(ConvertUtil.LengthItemNames[1]);
			
			//km to yards
			tvRow2Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_YARD,inputStr));
			tvRow2Unit.setText(ConvertUtil.LengthItemNames[2]);
			
			//km to miles
			tvRow3Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_MILE,inputStr));
			tvRow3Unit.setText(ConvertUtil.LengthItemNames[3]);
			
			//km to mm
			tvRow4Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_MM,inputStr));
			tvRow4Unit.setText(ConvertUtil.LengthItemNames[4]);
			
			//km to cm
			tvRow5Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_CM,inputStr));
			tvRow5Unit.setText(ConvertUtil.LengthItemNames[5]);
			
			//km to m
			tvRow6Value.setText(ConvertUtil.LengthConvert(Length.KM_TO_M,inputStr));
			tvRow6Unit.setText(ConvertUtil.LengthItemNames[6]);
			
			//km to km
			tvRow7Value.setText(inputStr);
			tvRow7Unit.setText(ConvertUtil.LengthItemNames[7]);
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * Set volume data
	 * @param pos dropdown box position showing the measure unit
	 */
	private void setVolumeData(long pos) {
		
		TextView tvInput = (TextView)view.findViewById(R.id.editUnitText);
        String inputStr = tvInput.getText().toString();
        
        //Can't have empty input, set to 0
        if(inputStr.equals("") || inputStr.equals("."))
        	inputStr = "0";
        

		
		//Select dropdown position
		switch((int)pos){
		case 0:
			//gal to gal
			tvRow0Value.setText(inputStr);
			tvRow0Unit.setText(ConvertUtil.VolumeItemNames[0]);
			
			//gal to cups
			tvRow1Value.setText(ConvertUtil.VolumeConvert(Volume.GAL_TO_CUPS,inputStr));
			tvRow1Unit.setText(ConvertUtil.VolumeItemNames[1]);
			
			//gal to pints
			tvRow2Value.setText(ConvertUtil.VolumeConvert(Volume.GAL_TO_PINT,inputStr));
			tvRow2Unit.setText(ConvertUtil.VolumeItemNames[2]);

			//gal to quart
			tvRow3Value.setText(ConvertUtil.VolumeConvert(Volume.GAL_TO_QUART,inputStr));
			tvRow3Unit.setText(ConvertUtil.VolumeItemNames[3]);
			break;
		case 1:
			//cups to gal
			tvRow0Value.setText(ConvertUtil.VolumeConvert(Volume.CUPS_TO_GAL,inputStr));
			tvRow0Unit.setText(ConvertUtil.VolumeItemNames[0]);
			
			//cups to cups
			tvRow1Value.setText(inputStr);
			tvRow1Unit.setText(ConvertUtil.VolumeItemNames[1]);
			
			//cups to pints
			tvRow2Value.setText(ConvertUtil.VolumeConvert(Volume.CUPS_TO_PINT,inputStr));
			tvRow2Unit.setText(ConvertUtil.VolumeItemNames[2]);
			
			//cups to quarts
			tvRow3Value.setText(ConvertUtil.VolumeConvert(Volume.CUPS_TO_QUART,inputStr));
			tvRow3Unit.setText(ConvertUtil.VolumeItemNames[3]);
			break;
		case 2:
			//pints to gal
			tvRow0Value.setText(ConvertUtil.VolumeConvert(Volume.PINT_TO_GAL,inputStr));
			tvRow0Unit.setText(ConvertUtil.VolumeItemNames[0]);
			
			//pints to cups
			tvRow1Value.setText(ConvertUtil.VolumeConvert(Volume.PINT_TO_CUPS,inputStr));
			tvRow1Unit.setText(ConvertUtil.VolumeItemNames[1]);
			
			//pints to pints
			tvRow2Value.setText(inputStr);
			tvRow2Unit.setText(ConvertUtil.VolumeItemNames[2]);
			
			//pints to quart
			tvRow3Value.setText(ConvertUtil.VolumeConvert(Volume.PINT_TO_QUART,inputStr));
			tvRow3Unit.setText(ConvertUtil.VolumeItemNames[3]);
			break;
		case 3:
			//quarts to gal
			tvRow0Value.setText(ConvertUtil.VolumeConvert(Volume.QUART_TO_GAL,inputStr));
			tvRow0Unit.setText(ConvertUtil.VolumeItemNames[0]);
			
			//quarts to cups
			tvRow1Value.setText(ConvertUtil.VolumeConvert(Volume.QUART_TO_CUPS,inputStr));
			tvRow1Unit.setText(ConvertUtil.VolumeItemNames[1]);
			
			//quarts to pints
			tvRow2Value.setText(ConvertUtil.VolumeConvert(Volume.QUART_TO_PINT,inputStr));
			tvRow2Unit.setText(ConvertUtil.VolumeItemNames[2]);
			
			//quarts to quarts
			tvRow3Value.setText(inputStr);
			tvRow3Unit.setText(ConvertUtil.VolumeItemNames[3]);
		    break;	
		default:
				break;
		}
		
	}
	
	/**
	 * Set temperature data
	 * @param pos dropdown box position showing the measure unit
	 */
	private void setTemperatureData(long pos) {
		TextView tvInput = (TextView)view.findViewById(R.id.editUnitText);
		String inputStr = tvInput.getText().toString();

		//Can't have empty input, set to 0
		if(inputStr.equals("") || inputStr.equals("."))
			inputStr = "0";

		//Select dropdown position
		switch((int)pos){
		case 0:
			//F to F
			tvRow0Value.setText(inputStr);
			tvRow0Unit.setText(ConvertUtil.TemperatureItemNames[0]);

			//F to C
			tvRow1Value.setText(ConvertUtil.TempConvert(Temperature.F_TO_C,inputStr));
			tvRow1Unit.setText(ConvertUtil.TemperatureItemNames[1]);
			break;
		case 1:
			//gal to gal
			tvRow0Value.setText(inputStr);
			tvRow0Unit.setText(ConvertUtil.TemperatureItemNames[0]);

			//gal to cups
			tvRow1Value.setText(ConvertUtil.TempConvert(Temperature.C_TO_F,inputStr));
			tvRow1Unit.setText(ConvertUtil.TemperatureItemNames[1]);
			break;
		default:
			break;
		}
	}
}
