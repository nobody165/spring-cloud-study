package com.radlly.utils;

public class StringUtil {

	public static void removeChar(int index,String Str){  
	    Str=Str.substring(0,index)+Str.substring(index+1,Str.length());//substring的取值范围是:[,)  
	}  
}
