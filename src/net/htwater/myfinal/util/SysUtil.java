package net.htwater.myfinal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class SysUtil {

	public static void logError(String msg, HttpServletRequest request) {

		MyLog.e(msg + ",is moblie=" + HttpRequest.isMobileDevice(request) + "ip=" + HttpRequest.getRemortIP(request));
	}
	

	 public static List<Integer> getRandomNum(List<Integer> list, int selected) {
	        List<Integer> reList = new ArrayList<Integer>();
	 
	        Random random = new Random();
	        // 先抽取，备选数量的个数
	        if (list.size() >= selected) {
	            for (int i = 0; i < selected; i++) {
	                // 随机数的范围为0-list.size()-1;
	                int target = random.nextInt(list.size());
	                reList.add(list.get(target));
	                list.remove(target);
	            }
	        } else {
	            selected = list.size();
	            for (int i = 0; i < selected; i++) {
	                // 随机数的范围为0-list.size()-1;
	                int target = random.nextInt(list.size());
	                reList.add(list.get(target));
	                list.remove(target);
	            }
	        }
	       
	        return reList;
	    }
	 
	 
	 public static void main(String[] args) {
		 
		 List<Integer>testList  = new ArrayList<Integer>();
		 
		 testList.add(1);
		 testList.add(2);
		 testList.add(3);
		 testList.add(4);
		 testList.add(5);
		 testList.add(6);
		 testList.add(7);
		 testList.add(8);
		 
		 
	  List<Integer>retList = getRandomNum(testList,8);
	  for(Integer i:retList){
		  
		  System.out.println("number=" + i);
		  
	  }
		
	 }
	

	 public static boolean validateParams(Map<String, Object> params,String sign){
			if(params==null || sign==null || params.size()==0) return false;
			return sign.equals(buildSign(params))?true:false;
		}
	
	 
	 private static String buildSign(Map<String, Object> params){
			List<String> names=new ArrayList<String>(params.keySet());
			Collections.sort(names, new Comparator<String>() {
				public int compare(String o1, String o2) {return o1.compareTo(o2);}
			});
			StringBuffer sb=new StringBuffer();
			for(String name : names){
				sb.append(name+params.get(name).toString());
			}
			sb.append(security_key);
			String result=CipherUtil.generatePassword(sb.toString());
			return result;
		}
	
	 private static final String security_key="test_123456679890123456";
	

}
