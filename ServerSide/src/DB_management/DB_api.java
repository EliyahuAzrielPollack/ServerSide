package DB_management;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;
import net.sourceforge.zmanim.hebrewcalendar.JewishDate;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DB_api {

	data_base_helper myDbHelper;
	String chosen_string = "";
	JewishCalendar jc = new JewishCalendar();
	HebrewDateFormatter hdf = new HebrewDateFormatter();
	enum Pray {shaharit , minha , arvit};
	
	public  DB_api(Context context){
		
	  //super(context, SQLite_names.DATABASE_NAME, null, SQLite_names.DATABASE_VERSION);
	  jc.setInIsrael(true); //default false for clarity but not needed. Set to true for Israel
	  hdf.setHebrewFormat(true);
	  myDbHelper = new data_base_helper(context);
		    try {
		    	myDbHelper.createDataBase();
		
			} catch (IOException ioe) {
		
				throw new Error("Unable to create database");
			}
		
			try {
				myDbHelper.openDataBase();
		
			} catch(SQLException sqle){
		
				throw sqle;
		}
	}
	
	/*public String getPray (String Pray , String select) {
		
	   String selectQuery = "SELECT * FROM " + Pray +
	   " WHERE " + Pray + ".id IN (SELECT " + select + " FROM select_prayer)";   

	   SQLiteDatabase db = myDbHelper.getReadableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   String res ="";
	   // looping through all rows and adding to list
	   if (cursor.moveToFirst()) {
	        do {
	        	res += cursor.getString(4);
	        } while (cursor.moveToNext());
	    }
		return res;
	}*/
	
	public ArrayList<NavDrawerItem> getAnchorsLabels (String Pray , String select){
		   
       String selectQuery = "SELECT anchor_label FROM " + Pray +
	   " WHERE " + Pray + ".id IN (SELECT " + select + " FROM select_prayer)";   

	   SQLiteDatabase db = myDbHelper.getReadableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   ArrayList<NavDrawerItem> res = new ArrayList<NavDrawerItem>();
	   // looping through all rows and adding to list
	   if (cursor.moveToFirst()) {
	        do {
	        	if(cursor.getString(0) != null)
	        	   res.add(new NavDrawerItem(cursor.getString(0)));
	        } while (cursor.moveToNext());
	    }
		return res;   
	}
	
	public ArrayList<String> getAnchorsCodes (String Pray , String select){
		
       String selectQuery = "SELECT anchor_code FROM " + Pray +
	   " WHERE " + Pray + ".id IN (SELECT " + select + " FROM select_prayer)";   

	   SQLiteDatabase db = myDbHelper.getReadableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   ArrayList<String> res = new ArrayList<String>();
	   // looping through all rows and adding to list
	   if (cursor.moveToFirst()) {
	        do {
	        	if(cursor.getString(0) != "")
	        	   res.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }
		return res; 
	}
	
	public String getPray (String Pray , String select) {
		
	   String selectQuery = "SELECT " + select + " FROM select_prayer";
	   SQLiteDatabase db = myDbHelper.getReadableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   List<String> res = new ArrayList<String>();
	   String res_2 = "";
	   if (cursor.moveToFirst()) {
	        do {
	        	res.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	   }

	   for (int i = 0; i < res.size(); i++) {
		   selectQuery = "SELECT contents FROM " + Pray +
		   " WHERE " + Pray + ".id = " + res.get(i) + " "; 
		   
		   db = myDbHelper.getReadableDatabase();
		   cursor = db.rawQuery(selectQuery, null);
		   // looping through all rows and adding to list
		   if (cursor.moveToFirst()) {
		        do {
		        	res_2 += cursor.getString(0);
		        } while (cursor.moveToNext());
		   }
		   
		   if (cursor.moveToFirst()) {
		        do {
		        	res_2 += cursor.getString(0);
		        } while (cursor.moveToNext());
		   }
	   }
	   return res_2;
	}
	
	public String getPray1(String Pray, String Select){
		
       String res = "";
	
	   String selectQuery = "SELECT * FROM " + Pray ;   
	   SQLiteDatabase db = myDbHelper.getReadableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   ArrayList<String> pray = new ArrayList<String>();
	   // looping through all rows and adding to list
	   if (cursor.moveToFirst()) {
	        do {
	        	pray.add(cursor.getString(2));
	        } while (cursor.moveToNext());
	   }
	   
	   selectQuery = "SELECT " + Select + " FROM select_prayer";   
	   db = myDbHelper.getReadableDatabase();
	   cursor = db.rawQuery(selectQuery, null);
	   ArrayList<Integer> select = new ArrayList<Integer>();
	   // looping through all rows and adding to list
	   if (cursor.moveToFirst()) {
	        do {
	        	select.add(cursor.getInt(0));
	        } while (cursor.moveToNext());
	   }
	   
	   for(int i = 1 ;i < select.size();i++)
       {
		   res +=  pray.get(select.get(i));
       }
       
	   return res;
	}
	
	//������� ������ ��� ����� �����
	public boolean isWinter(){
		//�� ������ ��� ���� ��"� ���� ����� �����
		return ((jc.getJewishMonth() == JewishDate.TISHREI && jc.getJewishDayOfMonth() >= 22) 
				|| (jc.getJewishMonth() > JewishDate.TISHREI)
				|| (jc.getJewishMonth() == JewishDate.NISSAN && jc.getJewishDayOfMonth() <= 15));
	}
	
	
    
	/*String get_shaharit (){
	
	}
	
	String get_minha (){
		
	}*/
	
	public String get_arvit(){
		
		String arvit_string = "";
		
		if(isWinter()){//���� - ��"� ���� �� �� ����
			//��"� ���� �� ��� ����� - ���� ���� 
            if(jc.getJewishMonth() == JewishDate.TISHREI && jc.getJewishDayOfMonth() >= 22)
            	arvit_string = "";
            //�"� ����� - ���� ���� ��� ���� ���� �����
		    else if(jc.isRoshChodesh() && jc.getJewishMonth() == JewishDate.CHESHVAN)
		    	arvit_string = "";
            //�� �'����� - ���� ���� ��� ����
			else if(jc.getJewishMonth() == JewishDate.CHESHVAN && 
					jc.getJewishDayOfMonth() < 7)
				arvit_string = "";
            //� �'����� �� ��� ����� - ���� ���� ��� �� ����
			else if(jc.getJewishMonth() == JewishDate.CHESHVAN && 
					jc.getJewishDayOfMonth() < 7)
				arvit_string = "";
            //����� ��� ���� -���� ���� ��� �� ���� ��� ������
			else if(jc.isChanukah() && !jc.isRoshChodesh())
				arvit_string = "";
            //����� �"� ��� -���� ���� ��� �� ���� �� ������ ����� �����
			else if(jc.isChanukah() && jc.isRoshChodesh())
				arvit_string = "";
            //�� ��� ���� ������ �� ��� ����� -���� ���� ��� �� ���� ����� �����
			else if (jc.isRoshChodesh())
				arvit_string = "";
            //����� ������ �������� - �� ������ 
			else if(!jc.isJewishLeapYear() && jc.getJewishMonth() == JewishDate.ADAR 
					&& (jc.getJewishDayOfMonth() == 14 || jc.getJewishDayOfMonth() == 15))
				arvit_string = "";
            //����� ������ ����� - ���� ���� , ��� �� ���� 
			else
				arvit_string = "";	
		}
		
		else if (!isWinter()) {//��� - ��� ���� �� �"� ����
			//��� �����  ��� - ���� ����� ��� �����,����� ���,��� ����,����� �����
			if(jc.getJewishMonth() == JewishDate.NISSAN && jc.getJewishDayOfMonth() >= 16 && jc.isCholHamoed())
				arvit_string = "";
			//��� ���� ���� �����  ������ ����� - ����� ���,��� ����,����� �����
			else if ((jc.getJewishMonth() == JewishDate.NISSAN && jc.getJewishDayOfMonth() >= 16 && !jc.isCholHamoed())
		              || jc.getJewishMonth() == JewishDate.NISSAN || (jc.getJewishMonth() == JewishDate.SIVAN && 
		              jc.getJewishDayOfMonth() <= 5))
				arvit_string = "";
			//���� ��� - ����� ���,��� ����,�����,����� ����
			else if (jc.getJewishMonth() == JewishDate.AV && jc.getJewishDayOfMonth() == 9)
				arvit_string = "";
			//���� ���� - ����� ���,��� ����,����� ����� ���� �'����
			else if (jc.getJewishMonth() == JewishDate.ELUL)
				arvit_string = "";
			//���� ���� ���"� - ����� ���,��� ����,������ ����"�,���� �'����
			else if(jc.getJewishMonth() == JewishDate.TISHREI && jc.getJewishDayOfMonth() <= 10)
				arvit_string = "";
			//���� ���� ��� ��"� ������ - ����� ���,��� ����,���� �'����
			else if(jc.getJewishMonth() == JewishDate.TISHREI && jc.getJewishDayOfMonth() > 10 && jc.getJewishDayOfMonth() < 15)
				arvit_string = "";
			//��� ����� ����� - ����� ���,��� ����,���� �����,���� �'���� (����������:������ ��� ������) 
			else if(jc.getJewishMonth() == JewishDate.TISHREI && jc.isCholHamoed())
				arvit_string = "";
			//����� ������ ����� - ����� ���,��� ����
			else
				arvit_string = "";	
		}
		//����� ����� ���� ����� ���������� �����
		if(jc.getJewishDayOfMonth() >= 7 && jc.getJewishDayOfMonth() <=15)
			return arvit_string += "";
		else
			return arvit_string;
	}
  }

