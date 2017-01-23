package dataConnect;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import util.PropertiesResolve;

public class DataModelingConnect {
	private static final int HashMap = 0;
	static PropertiesResolve pr = new PropertiesResolve();
	private static Properties props =new Properties();
	//����Connection����
	static Connection con;
	static String dbConnectionDriverClass = pr.readMapByKey("/dataParameter/dataModelParameters.properties", "db.connection.driver_class");
	static String dbConnectionUrl = pr.readMapByKey("/dataParameter/dataModelParameters.properties", "db.connection.url");
	static String dbConnectionUsername = pr.readMapByKey("/dataParameter/dataModelParameters.properties", "db.connection.username");
	static String dbConnectionPassword = pr.readMapByKey("/dataParameter/dataModelParameters.properties", "db.connection.password");
	static {
		try {
			Class.forName(dbConnectionDriverClass);
		    con = DriverManager.getConnection(dbConnectionUrl,dbConnectionUsername,dbConnectionPassword);
	        //con.set//setAutoCommit("remarks","true");// setProperty("remarks", "true"); //���ÿ��Ի�ȡremarks��Ϣ 
	        props.setProperty("user", dbConnectionUsername);
            props.setProperty("password", dbConnectionPassword);
            props.setProperty("remarks", "true"); //���ÿ��Ի�ȡremarks��Ϣ 
            props.setProperty("useInformationSchema", "true");//���ÿ��Ի�ȡtables remarks��Ϣ
            con = DriverManager.getConnection(dbConnectionUrl, props);
		    if(!con.isClosed())
	        System.out.println("Succeeded connecting to the Database!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
	}
	
	/**
	 * ��ȡ����ϸ�Ľṹ���������������ԡ����ȡ�ע�͵���Ϣ
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static String dataStructure(String tableName) throws Exception{
		  Statement statement = con.createStatement();
          //Ҫִ�е�SQL���
          String sql = "show create table "+tableName;
          //3.ResultSet�࣬������Ż�ȡ�Ľ��������
          ResultSet rs = statement.executeQuery(sql);
          String name;
          String id = null;
          String tableStructure = null;
          while(rs.next()){
             /* //��ȡstuname��������
              name = rs.getString("table");
              //��ȡstuid��������
              id = rs.getString("create table");
              //����ʹ��ISO-8859-1�ַ�����name����Ϊ�ֽ����в�������洢�µ��ֽ������С�
              //Ȼ��ʹ��GB2312�ַ�������ָ�����ֽ����顣
              name = new String(name.getBytes("ISO-8859-1"),"gb2312");
              //������
              System.out.println(id + "\t" + name);*/
        	  tableStructure = rs.getString("create table");
        	 
        	  
        	  
          }
          
		return tableStructure;
	}
	/**
	 * ��ȡ���ݿ������еı���Ϣ
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<String> dataTables() throws Exception{
		  Statement statement = con.createStatement();
          //Ҫִ�е�SQL���
          String sql = "show tables";
          //3.ResultSet�࣬������Ż�ȡ�Ľ��������
          ResultSet rs = statement.executeQuery(sql);
          List<String> list = new ArrayList<String>();
          while(rs.next()){
        	  list.add(rs.getString("tables_in_paperzy")); 
          }
		return list;
		
	}
	
	/**
	 * ��ȡ���ݱ�����ϸ��Ϣ
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, List<List<String>>> tableResultSet() throws Exception{
		 List<String> tableList = dataTables();
		 DatabaseMetaData dbmd = con.getMetaData(); 
		 Map<String, List<List<String>>> map = new HashMap<String,List<List<String>>>();
		
         //ResultSet rs2 = dbmd.getPrimaryKeys(null, "%", "sys_user_info");  
         String columnName = null;//��Ŀ��
         String columnType = null;//��ĿID
         String columnRemarks = null;//��Ŀ����
         int columnSize; //�ֶγ���
         int accuracy;//����
         int numberOfBytes; //�ֽ���
         int nullAble ; //not null
         int primaryKey;
         String codeReference;
         String remarks;
         for(int i=0;i<tableList.size();i++){
        	 ResultSet rs = dbmd.getColumns(null,"%",tableList.get(i),"%");
        	 List<List<String>> lists = new ArrayList<List<String>>();
             while(rs.next()){
              List<String> list = new ArrayList<String>();
           	  columnName = rs.getString("COLUMN_NAME"); 
           	  columnType = rs.getString("TYPE_NAME"); 
           	  columnRemarks = rs.getString("REMARKS"); 
           	  columnSize = rs.getInt("COLUMN_SIZE");
              nullAble = rs.getInt("NULLABLE");
             	// pkName = rs2.getString("PK_NAME"); //��������    
             	
           	 // System.out.println(columnRemarks +"--"+columnName+"--"+columnType+"--"+columnSize);
           	  list.add(columnName);
           	  list.add(columnType);
           	  list.add(columnRemarks);
           	  list.add(String.valueOf(columnSize));
           	  list.add("");
              list.add("");
              if(0 == nullAble){
           		list.add("0");
         	  }else {
         		 list.add("");  
         	  }
              list.add("");
              list.add("");
              list.add("");
              //list.add(pkName);
              lists.add(list);
             } 
             map.put(tableList.get(i), lists);
         }
        
          
		return map;
	} 
	
	 public Map<String, Map<String,String>> getDataBaseTableDetailed(String table){
		 List<String> list ;
		 String[] tables = {table};
		 Map<String, Map<String,String>> map = new HashMap<String, Map<String,String>>();
		 DatabaseMetaData dbmd;
		try {
			dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null,null,table,null);  
			 while (rs.next()) { 
				 //System.out.println(rs.getString("REMARKS"));
				 List<String> list1 = new ArrayList<String>();
				 Map<String, String> maps = new HashMap<String, String>();
				 maps.put("����", rs.getString(5));
				 maps.put("����", rs.getString("TABLE_NAME"));
				 maps.put("��ע", "");
				 list1.add(table);
				 map.put("DataBaseTableDetailed", maps);
			 }  
		} catch (SQLException e) {
			System.out.println("����");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 return map;
	 }
	/**
	 * ��̬�Ļ�ȡ��ṹ����
	 * @return
	 */
	public List<String> TableStructureIteration(){
		List<String> list = new ArrayList<String>();
		try {
			List<String> tablesList = DataModelingConnect.dataTables();
			 for(String table:tablesList){
				 String tableStructure = DataModelingConnect.dataStructure(table);
				 list.add(tableStructure);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String args[]) throws SQLException{
		DataModelingConnect dmConnect = new DataModelingConnect();
		try {
			 dmConnect.getDataBaseTableDetailed("sys_api_info_value") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
