package dataMainCenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi2.util.SystemOutLogger;

import java.util.Map.Entry;

import dataConnect.DataModelingConnect;

public class DataModelingExport {
	public static void main(String args[]){
//		try {
//			//DataModelingConnect.dataStructure("om_data_dmp_value");
//			//DataModelingConnect.dataTables();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("cxn1", "320721199107210431");
		map.put("cxn2", "320721199107210432");
		map.put("cxn3", "320721199107210433");
		map.put("cxn4", "320721199107210434");
		map.put("cxn5", "320721199107210435");
		//第一种方式：使用entrySet方式，并用iterator迭代
		/*Iterator<Entry<String, String>> entrySet = map.entrySet().iterator();
		while(entrySet.hasNext()){
			Entry<String, String> next = entrySet.next();
			System.out.println(next.getValue());
			System.out.println(next.getKey());
			
		}
		System.out.println(entrySet);*/
		//第一种方式：使用entrySet方式，并用for-Each循环
		/*Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry:entrySet) {
			System.out.println(entry.getValue());
			System.out.println(entry.getKey());
		}*/
		
		/*Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			System.out.println(next);
		}*/
		
		Set<String> keySet = map.keySet();
		for (String str:keySet) {
			System.out.println(str);
		}
		
	}
}

