package com.fourthwardcoder.android.convertthis;

import android.annotation.SuppressLint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/** 
 * Class ConvertUtil 
 * 
 * @author: Chris Hare
 * Created: 1/18/2015
 * 
 * TODO Items
 * > Add comma to numbers above 1000 (DONE)
 * > Format decimal point to 5 places or so (DONE)
 * > When numbers get too large need to add "E+" format to cut off part to fit in box
 * > Negative values in degrees don't fit in decimal format
 */
public class ConvertUtil {
	
	public final static String[] WeightItemNames = new String[] {"lbs (pounds)","oz (ounces)","kg (kilograms)","g (grams)", "mg (milligrams)","tons (US)"};
	public final static String[] VolumeItemNames = new String[] {"gallons","cups","pints","quarts"};
	public final static String[] LengthItemNames = new String[] {"in (inches)","ft (feet)","yard","mile","mm (millimeter)","cm (centimeter)",
		"m (meter)", "km (kilometer)"};
	public final static String[] TemperatureItemNames = new String[] {"F (Fahrenheit)", "C (Celsius)"};
	
	/***********************************************************/
	/*                      Constants                          */
	/***********************************************************/
	public static enum Weight {
		POUNDS_TO_OZ,
		POUNDS_TO_KG,
		POUNDS_TO_G,
		POUNDS_TO_MG,
		POUNDS_TO_TONS,
		OZ_TO_POUNDS,
		OZ_TO_KG,
		OZ_TO_G,
		OZ_TO_MG,
		OZ_TO_TONS,
		KG_TO_POUNDS,
		KG_TO_OZ,
		KG_TO_G,
		KG_TO_MG,
		KG_TO_TONS,
		G_TO_POUNDS,
		G_TO_OZ,
		G_TO_KG,
		G_TO_MG,
		G_TO_TONS,
		MG_TO_POUNDS,
		MG_TO_OZ,
		MG_TO_KG,
		MG_TO_G,
		MG_TO_TONS,
		TONS_TO_POUNDS,
		TONS_TO_OZ,
		TONS_TO_KG,
		TONS_TO_G,
		TONS_TO_MG
	}
	
	public static enum Volume {
		GAL_TO_CUPS,
		GAL_TO_PINT,
		GAL_TO_QUART,
		CUPS_TO_GAL,
		CUPS_TO_PINT,
		CUPS_TO_QUART,
		PINT_TO_GAL,
		PINT_TO_CUPS,
		PINT_TO_QUART,
		QUART_TO_GAL,
		QUART_TO_PINT,
		QUART_TO_CUPS
	}
	
	public static enum Length {
		IN_TO_FEET,
		IN_TO_YARD,
		IN_TO_MILE,
		IN_TO_MM,
		IN_TO_CM,
		IN_TO_M,
		IN_TO_KM,
		FEET_TO_IN,
		FEET_TO_YARD,
		FEET_TO_MILE,
		FEET_TO_MM,
		FEET_TO_CM,
		FEET_TO_M,
		FEET_TO_KM,
		YARD_TO_IN,
		YARD_TO_FEET,
		YARD_TO_MILE,
		YARD_TO_MM,
		YARD_TO_CM,
		YARD_TO_M,
		YARD_TO_KM,
		MILE_TO_IN,
		MILE_TO_FEET,
		MILE_TO_YARD,
		MILE_TO_MM,
		MILE_TO_CM,
		MILE_TO_M,
		MILE_TO_KM,
		MM_TO_IN,
		MM_TO_FEET,
		MM_TO_YARD,
		MM_TO_MILE,
		MM_TO_CM,
		MM_TO_M,
		MM_TO_KM,
		CM_TO_IN,
		CM_TO_FEET,
		CM_TO_YARD,
		CM_TO_MILE,
		CM_TO_MM,
		CM_TO_M,
		CM_TO_KM,
		M_TO_IN,
		M_TO_FEET,
		M_TO_YARD,
		M_TO_MILE,
		M_TO_MM,
		M_TO_CM,
		M_TO_KM,
		KM_TO_IN,
		KM_TO_FEET,
		KM_TO_YARD,
		KM_TO_MILE,
		KM_TO_MM,
		KM_TO_CM,
		KM_TO_M
	}
	
	public static enum Temperature {
		F_TO_C,
		C_TO_F
	}
	/***********************************************************/
	/*                   Private Methods                       */
	/***********************************************************/
	/**
	 * Format the results of the measurement conversion 
	 * @param d double result value
	 * @return string result of the formatted value
	 */
	private static String formatResult(Double d) {
		
		//If value is less that 6 points of the format, then return value as is.
		if(d < .00001)
			return String.valueOf(d);
		
		DecimalFormat formatter = new DecimalFormat("#,###.00000");
		
		String str = formatter.format(d);
		
		//Continue formatting decimal if there is a decimal in the string
		if(str.contains(".")) {
		   String strArray[] = str.split("\\.");
		   
		   int i;
		   String strDec = "";
		   boolean foundInt = false;
		   
		   //Go through decimal part backwards and only collect characters 
		   //when a number greater than zero is found. Leave off extra zeros
		   for(i = 4; i >=0; i--) {
			   if(strArray[1].charAt(i) != '0' && foundInt == false) {
				   strDec = strDec + strArray[1].charAt(i);
				   foundInt = true;
			   }
			  else if(foundInt == true)
				   strDec = strDec + strArray[1].charAt(i);
		   }
		   
		   if(foundInt) {
			   //Reverse decimal string and add it to the whole part
			   String revDec = new StringBuffer(strDec).reverse().toString();
			   str = strArray[0] + "." + revDec;
		   }
		   else {
		       //All zeros in the decimal. Leave it off
			   str = strArray[0];
		   }

		}
		   return str;
	}
	/***********************************************************/
	/*                   Public Methods                        */
	/***********************************************************/
	
	public static String WeightConvert(Weight type, String str) {
		
		switch(type) {
		   case POUNDS_TO_OZ:
			   return String.valueOf(formatResult(16 * Double.valueOf(str)));
		   case POUNDS_TO_KG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 2.2046));
		   case POUNDS_TO_G:
			   return String.valueOf(formatResult(Double.valueOf(str) / .0022046));
		   case POUNDS_TO_MG:
			   return String.valueOf(formatResult(Double.valueOf(str) / .0000022046));
		   case POUNDS_TO_TONS:
			   return String.valueOf(formatResult(Double.valueOf(str) / 2000));
		   case OZ_TO_POUNDS:
			   return String.valueOf(formatResult(Double.valueOf(str) / 16));
		   case OZ_TO_KG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 35.274));
		   case OZ_TO_G:
			   return String.valueOf(formatResult(Double.valueOf(str) / .035274));
		   case OZ_TO_MG:
			   return String.valueOf(formatResult(Double.valueOf(str) / .000035274));
		   case OZ_TO_TONS:
			   return String.valueOf(formatResult(Double.valueOf(str) / 16 / 2000));
		   case KG_TO_POUNDS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 2.2046));
		   case KG_TO_OZ:
			   return String.valueOf(formatResult(Double.valueOf(str) * 35.274));
		   case KG_TO_G:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000));
		   case KG_TO_MG:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000 * 1000));
		   case KG_TO_TONS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0011023));
		   case G_TO_POUNDS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0022046));
		   case G_TO_OZ:
			   return String.valueOf(formatResult(Double.valueOf(str) * .035274));
		   case G_TO_KG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000));
		   case G_TO_MG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.0010000));		   
		   case G_TO_TONS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1.10231e-6));
		   case MG_TO_POUNDS:
			   return String.valueOf(formatResult(Double.valueOf(str) * .0000022046));
		   case MG_TO_OZ:
			   return String.valueOf(formatResult(Double.valueOf(str) * .000035274));
		   case MG_TO_KG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000 / 1000));
		   case MG_TO_G:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0010000));
		   case MG_TO_TONS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1.10231e-9));
		   case TONS_TO_POUNDS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 2000));
		   case TONS_TO_OZ:
			   return String.valueOf(formatResult(Double.valueOf(str) * 16 * 2000));
		   case TONS_TO_KG:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.0011023));
		   case TONS_TO_G:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1.10231e-6));
		   case TONS_TO_MG:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1.10231e-9));
			
		default:
			   return "";
		}
		
	}
	
	public static String LengthConvert(Length type, String str) {
		
		switch(type) {
		   case IN_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) / 12));
		   case IN_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.027778));
		   case IN_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.027778 / 1760));
		   case IN_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.039370));
		   case IN_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.39370));
		   case IN_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) / 39.370));
		   case IN_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 39370));
		   case FEET_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 12));
		   case FEET_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.33333));
		   case FEET_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.00018939));
		   case FEET_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.0032808));
		   case FEET_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.032808));
		   case FEET_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) / 3.2808));
		   case FEET_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 3280.8));
		   case YARD_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 36));
		   case YARD_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) * 3));
		   case YARD_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.00056818));
		   case YARD_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.0010936));
		   case YARD_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.010936));
		   case YARD_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1.0936));
		   case YARD_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1093.6));
		   case MILE_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.027778 * 1760));
		   case MILE_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.00018939));
		   case MILE_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) / 0.00056818));
		   case MILE_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) /0.00000062137));
		   case MILE_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) /0.0000062137));
		   case MILE_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) /0.00062137));
		   case MILE_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) /0.62137));
		   case MM_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.039370));
		   case MM_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0032808));
		   case MM_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0010936));
		   case MM_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.00000062137));
		   case MM_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 10));
		   case MM_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000));
		   case MM_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000 / 1000));
		   case CM_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.39370));
		   case CM_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.032808));
		   case CM_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.010936));
		   case CM_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.0000062137));
		   case CM_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) * 10));
		   case CM_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) / 100));
		   case CM_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000 / 100));
		   case M_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 39.370));
		   case M_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) * 3.2808));
		   case M_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1.0936));
		   case M_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * 0.00062137));
		   case M_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000));
		   case M_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) * 100));
		   case M_TO_KM:
			   return String.valueOf(formatResult(Double.valueOf(str) / 1000));
		   case KM_TO_IN:
			   return String.valueOf(formatResult(Double.valueOf(str) * 39370));
		   case KM_TO_FEET:
			   return String.valueOf(formatResult(Double.valueOf(str) * 3280.8));
		   case KM_TO_YARD:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1093.6));
		   case KM_TO_MILE:
			   return String.valueOf(formatResult(Double.valueOf(str) * .62137));
		   case KM_TO_MM:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000 * 1000));
		   case KM_TO_CM:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000 * 100));
		   case KM_TO_M:
			   return String.valueOf(formatResult(Double.valueOf(str) * 1000));
		   default:
			   return "";
		}
	}
	
	public static String VolumeConvert(Volume type, String str) {
		
		switch(type) {
		   case GAL_TO_CUPS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 16));
		   case GAL_TO_PINT:
			   return String.valueOf(formatResult(Double.valueOf(str) * 8));
		   case GAL_TO_QUART:
			   return String.valueOf(formatResult(Double.valueOf(str) * 4));
		   case CUPS_TO_GAL:
			   return String.valueOf(formatResult(Double.valueOf(str) / 16));
		   case CUPS_TO_PINT:
			   return String.valueOf(formatResult(Double.valueOf(str) / 2));
		   case CUPS_TO_QUART:
			   return String.valueOf(formatResult(Double.valueOf(str) / 4));
		   case PINT_TO_GAL:
			   return String.valueOf(formatResult(Double.valueOf(str) / 8));
		   case PINT_TO_CUPS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 2));
		   case PINT_TO_QUART:
			   return String.valueOf(formatResult(Double.valueOf(str) / 2));
		   case QUART_TO_GAL:
			   return String.valueOf(formatResult(Double.valueOf(str) / 4));
		   case QUART_TO_CUPS:
			   return String.valueOf(formatResult(Double.valueOf(str) * 4));
		   case QUART_TO_PINT:
			   return String.valueOf(formatResult(Double.valueOf(str) * 2));
		   default:
			   return "";
		}
	}
	
	public static String TempConvert(Temperature type, String str) {
		
		switch(type) {
		   case F_TO_C:
			   return String.valueOf(formatResult((Double.valueOf(str) - 32) / 1.8));
		   case C_TO_F:
			   return String.valueOf(formatResult((Double.valueOf(str) * 1.8) + 32));
		   default:
			   return "";
		}
	}
	
	@SuppressLint("NewApi")
	public static void main(String args[]) throws IOException  {
		
        String line = null;
        //BufferedReader br = null;
        	
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
		    
		   //br = new BufferedReader(new InputStreamReader(System.in));
		   System.out.println("Select which measurement to test: (w)Weight, (l)Length, (t)Temperature");
		   System.out.print("Enter: ");
		   
		   
		   
		   char c = br.readLine().charAt(0);
		   
		   if(c == 'w') {
		
			   /************************************* Test Pounds *****************************************/
			   System.out.print("Enter pounds: ");
			   line = br.readLine();

			   System.out.println(line + " lb = " + WeightConvert(Weight.POUNDS_TO_OZ,String.valueOf(line)) + " oz");
			   System.out.println(line + " lb = " + WeightConvert(Weight.POUNDS_TO_KG,String.valueOf(line)) + " kg");
			   System.out.println(line + " lb = " + WeightConvert(Weight.POUNDS_TO_G,String.valueOf(line)) + " g");
			   System.out.println(line + " lb = " + WeightConvert(Weight.POUNDS_TO_MG,String.valueOf(line)) + " mg");
			   System.out.println(line + " lb = " + WeightConvert(Weight.POUNDS_TO_TONS,String.valueOf(line)) + " tons");		

			   /*************************************** Test OZ *******************************************/
			   System.out.print("Enter oz: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " oz = " + WeightConvert(Weight.OZ_TO_POUNDS,String.valueOf(line)) + " lbs");
			   System.out.println(line + " oz = " + WeightConvert(Weight.OZ_TO_KG,String.valueOf(line)) + " kg");
			   System.out.println(line + " oz = " + WeightConvert(Weight.OZ_TO_G,String.valueOf(line)) + " g");
			   System.out.println(line + " oz = " + WeightConvert(Weight.OZ_TO_MG,String.valueOf(line)) + " mg");
			   System.out.println(line + " oz = " + WeightConvert(Weight.OZ_TO_TONS,String.valueOf(line)) + " tons");		

			   /************************************ Test Kilograms ****************************************/
			   System.out.print("Enter kg: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " kg = " + WeightConvert(Weight.KG_TO_POUNDS,String.valueOf(line)) + " lbs");
			   System.out.println(line + " kg = " + WeightConvert(Weight.KG_TO_OZ,String.valueOf(line)) + " oz");
			   System.out.println(line + " kg = " + WeightConvert(Weight.KG_TO_G,String.valueOf(line)) + " g");
			   System.out.println(line + " kg = " + WeightConvert(Weight.KG_TO_MG,String.valueOf(line)) + " mg");
			   System.out.println(line + " kg = " + WeightConvert(Weight.KG_TO_TONS,String.valueOf(line)) + " tons");

			   /*************************************** Test Grams *****************************************/
			   System.out.print("Enter g: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " g = " + WeightConvert(Weight.G_TO_POUNDS,String.valueOf(line)) + " lbs");
			   System.out.println(line + " g = " + WeightConvert(Weight.G_TO_OZ,String.valueOf(line)) + " oz");
			   System.out.println(line + " g = " + WeightConvert(Weight.G_TO_KG,String.valueOf(line)) + " Kg");
			   System.out.println(line + " g = " + WeightConvert(Weight.G_TO_MG,String.valueOf(line)) + " mg");
			   System.out.println(line + " g = " + WeightConvert(Weight.G_TO_TONS,String.valueOf(line)) + " tons");

			   /*************************************** Test MG *****************************************/
			   System.out.print("Enter mg: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " mg = " + WeightConvert(Weight.MG_TO_POUNDS,String.valueOf(line)) + " lbs");
			   System.out.println(line + " mg = " + WeightConvert(Weight.MG_TO_OZ,String.valueOf(line)) + " oz");
			   System.out.println(line + " mg = " + WeightConvert(Weight.MG_TO_KG,String.valueOf(line)) + " Kg");
			   System.out.println(line + " mg = " + WeightConvert(Weight.MG_TO_G,String.valueOf(line)) + " g");
			   System.out.println(line + " mg = " + WeightConvert(Weight.MG_TO_TONS,String.valueOf(line)) + " tons");		 


			   /*************************************** Test Tons *****************************************/
			   System.out.print("Enter tons: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " tons = " + WeightConvert(Weight.TONS_TO_POUNDS,String.valueOf(line)) + " lbs");
			   System.out.println(line + " tons = " + WeightConvert(Weight.TONS_TO_OZ,String.valueOf(line)) + " oz");
			   System.out.println(line + " tons = " + WeightConvert(Weight.TONS_TO_KG,String.valueOf(line)) + " Kg");
			   System.out.println(line + " tons = " + WeightConvert(Weight.TONS_TO_G,String.valueOf(line)) + " g");
			   System.out.println(line + " tons = " + WeightConvert(Weight.TONS_TO_MG,String.valueOf(line)) + " mg");	
		   
		   }
		   else if(c == 'l') {
			   /*
			    * Test Length
			    */
			   
			   /*************************************** Test Inches ****************************************/
			   System.out.print("Enter in: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_FEET,String.valueOf(line)) + " feet");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " in = " + LengthConvert(Length.IN_TO_YARD,String.valueOf(line)) + " yard");
			   
			   /*************************************** Test Feet ******************************************/
			   System.out.print("Enter feet: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_IN,String.valueOf(line)) + " feet");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " feet = " + LengthConvert(Length.FEET_TO_YARD,String.valueOf(line)) + " yard");
			   
			   /*************************************** Test Yard ******************************************/
			   System.out.print("Enter yard: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " yard = " + LengthConvert(Length.YARD_TO_FEET,String.valueOf(line)) + " feet");
			   
			   /*************************************** Test Mile ******************************************/
			   System.out.print("Enter mile: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_YARD,String.valueOf(line)) + " yard");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " mile = " + LengthConvert(Length.MILE_TO_FEET,String.valueOf(line)) + " feet");
			   
			   /*************************************** Test MM ******************************************/
			   System.out.print("Enter mm: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_YARD,String.valueOf(line)) + " yard");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " mm = " + LengthConvert(Length.MM_TO_FEET,String.valueOf(line)) + " feet");
			   
			   /*************************************** Test CM ******************************************/
			   
			   System.out.print("Enter cm: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_FEET,String.valueOf(line)) + " feet");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " cm = " + LengthConvert(Length.CM_TO_YARD,String.valueOf(line)) + " yard");
			   
			   /*************************************** Test M ******************************************/
			   
			   System.out.print("Enter m: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_FEET,String.valueOf(line)) + " feet");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_KM,String.valueOf(line)) + " km");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " m = " + LengthConvert(Length.M_TO_YARD,String.valueOf(line)) + " yard");
			   
			   /*************************************** Test KM ******************************************/
			   
			   System.out.print("Enter km: ");
			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_FEET,String.valueOf(line)) + " feet");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_IN,String.valueOf(line)) + " in");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_M,String.valueOf(line)) + " m");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_CM,String.valueOf(line)) + " cm");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_MILE,String.valueOf(line)) + " mile");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_MM,String.valueOf(line)) + " mm");
			   System.out.println(line + " km = " + LengthConvert(Length.KM_TO_YARD,String.valueOf(line)) + " yard");
		       
		       
		   }

		   else if(c == 't') {
			  
			   /*
			    * Test Temperature
			    */
			   System.out.print("Enter F deg:");

			   //reset line
			   line = null;
			   line = br.readLine();

			   System.out.println(line + " F = " + TempConvert(Temperature.F_TO_C,String.valueOf(line)) + " C");

			   System.out.print("Enter C deg:");

			   //reset line
			   line = null;;
			   line = br.readLine();
			   System.out.println(line + " C = " + TempConvert(Temperature.C_TO_F,String.valueOf(line)) + " F");
		   }
		   

		   
		}
		catch (IOException e) {
		   System.out.println("Error reading from input buffer");	
		}
		catch (NumberFormatException e) {
		   System.out.println("Improper input type " + line + "." + " Must be a number");
		   System.out.println(e.toString());
		   
		} 

	}

}
