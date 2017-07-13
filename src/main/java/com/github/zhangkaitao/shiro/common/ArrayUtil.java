package com.github.zhangkaitao.shiro.common;

import java.util.Arrays;

/**
 * 
 * @author ping
 *
 */
public class ArrayUtil {

	/**
	 * 合并两个数组
	 * @param first
	 * @param second
	 * @return 返回合并后的数组
	 */
	public static <T> T[] concat(T[] first, T[] second) {
//		  if(first == null) return second;
//		  if(second == null) return first;
//		  if(first != null && second !=null){
			  T[] result = Arrays.copyOf(first, first.length + second.length);  
			  System.arraycopy(second, 0, result, first.length, second.length);  
			  return result;  
//		  }
//		  return null;
		}    
	
	public static <T> T[] concatAll(T[] first, T[]... rest) {  
		  int totalLength = first.length;  
		  for (T[] array : rest) { 
			  totalLength += array.length;  
		  }  
		  T[] result = Arrays.copyOf(first, totalLength);  
		  int offset = first.length;  
		  for (T[] array : rest) {  
		    System.arraycopy(array, 0, result, offset, array.length);  
		    offset += array.length;  
		  }  
		  return result;  
		}  
}
