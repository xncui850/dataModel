package dataConnect;

import java.util.List;

public class DataModelingAnalyticDataStructure {
	 public static void main(String args[]){
		 DataModelingConnect dmConnect = new DataModelingConnect();
		 List<String> list = dmConnect.TableStructureIteration();
		 System.out.println(list.get(0)); 
	 }
}
