package dataEditExcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi2.hssf.usermodel.HSSFCell;
import org.apache.poi2.hssf.usermodel.HSSFCellStyle;
import org.apache.poi2.hssf.usermodel.HSSFRow;
import org.apache.poi2.hssf.usermodel.HSSFSheet;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;
import org.apache.poi2.hssf.util.Region;

import dataConnect.DataModelingConnect;

public class DataModelingEditExcel {
	private static HSSFWorkbook wb = new HSSFWorkbook();  //--->������һ��excel�ļ� 
	private static HSSFCellStyle style = wb.createCellStyle();
	private static DataModelingEditExcelModel dmeModel = new DataModelingEditExcelModel();
	private static DataModelingEditExcelModel dmEditExcelModel = new DataModelingEditExcelModel();
	static {
		
	}
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception{
		 DataModelingEditExcel dMEditExcel = new DataModelingEditExcel();
		 DataModelingConnect dmConnect = new DataModelingConnect();
		 HSSFCellStyle style = wb.createCellStyle();
		 
		 Map<String, List<List<String>>> map = dmConnect.tableResultSet() ;
		 int i = 0 ;
	
		 
		 for(Map.Entry<String, List<List<String>>> objectList:map.entrySet()){
			
			 String tableDesc = dMEditExcel.tableDesc(dmConnect, dMEditExcel,objectList.getKey());
			 String sheetName = dMEditExcel.sheetTableNameHandle(tableDesc,objectList.getKey(),i);
			 HSSFSheet sheeti = wb.createSheet(sheetName);   //--->������һ�������� 
			 HSSFCell cell = null ;
			 HSSFCellStyle stylei = wb.createCellStyle();
			 //���ݽṹ��ͷ����(����)
			 dMEditExcel.tableDetailed(sheeti,cell,dmConnect,dMEditExcel,objectList.getKey());
			 //���ݽṹ����ͷ��
			 dMEditExcel.bodyDataTop(sheeti,cell,dMEditExcel);
			 //���ݽṹ���岿��
			 dMEditExcel.bodyData(sheeti,cell,dMEditExcel,stylei,objectList);
			  i++;
		 }
		 
		 FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("E://paperzy.xls");
			wb.write(fileOut);
			fileOut.close();
			System.out.print("OK");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	//���ݽṹ��ͷ����(����)
	public String tableDetailed(HSSFSheet sheet,HSSFCell cell1, DataModelingConnect dmConnect, DataModelingEditExcel dMEditExcel, String table){
		 HSSFCellStyle style = wb.createCellStyle();
		 Map<String, Map<String,String>> map=dmConnect.getDataBaseTableDetailed(table);
		 for(Map.Entry maps: map.entrySet()){
			// System.out.println(maps.getValue());
		 }
		 Iterator mapKeyArray=map.get("DataBaseTableDetailed").keySet().iterator();
		// System.out.println("111="+map.get("DataBaseTableDetailed").get("����"));
		 int rowi = 1;
		 while(mapKeyArray.hasNext()){
		 HSSFRow row = sheet.createRow(rowi);
		 HSSFCell cell2 =row.createCell((short) 2);
		 String key=(String) mapKeyArray.next();
		 cell1 =row.createCell((short) (2));
		 cell1.setCellValue(key);
		 //sheet.addMergedRegion(new Region(rowi, (short) 2, rowi, (short) 2));  // �ĸ������ֱ��ǣ���ʼ�У���ʼ�У������У�������   
		 dMEditExcel.styleBorderTop(style,cell1);
		 cell1 =row.createCell((short) (3));
		  //System.out.println("111="+map.get("DataBaseTableDetailed").get("����"));
		 cell1.setCellValue(map.get("DataBaseTableDetailed").get(key));
		 style.setAlignment(style.ALIGN_CENTER);//ˮƽ����
		 dMEditExcel.styleBorderTop(style,cell1);
		 cell1 =row.createCell((short) (4));
		 dMEditExcel.styleBorderTop(style,cell1);
		 cell1 =row.createCell((short) (5));
		 dMEditExcel.styleBorderTop(style,cell1);
		 cell1 =row.createCell((short) (6));
		 dMEditExcel.styleBorderTop(style,cell1);
		 cell1 =row.createCell((short) (7));
		// dMEditExcel.styleBorderTop(style,cell1);
		 sheet.addMergedRegion(new Region(rowi, (short) 3, rowi, (short) 6));  // �ĸ������ֱ��ǣ���ʼ�У���ʼ�У������У�������   
		 //sheet.setSelected(true);
		 rowi++;
		 }
		 return map.get("DataBaseTableDetailed").get("����");
	}
	//���ݽṹ���岿��
	public void bodyData(HSSFSheet sheet, HSSFCell cell, DataModelingEditExcel dMEditExcel, HSSFCellStyle style, Entry<String, List<List<String>>> objectList){
		for (int i = 0; i < objectList.getValue().size(); i++) {
			 HSSFRow row = sheet.createRow(i+7);
			 cell = row.createCell((short) 0);
			 cell.setCellValue(i+1);  
			 dMEditExcel.styleBorderBody(style, cell);
			 for (int j = 0; j < objectList.getValue().get(i).size(); j++) {
				 cell = row.createCell((short)(j+1));
				 cell.setCellValue(objectList.getValue().get(i).get(j)); 
				 dMEditExcel.styleBorderBody(style,cell);
			}
		}
	}
	//���ݽṹ����ͷ��
	public void bodyDataTop(HSSFSheet sheet, HSSFCell cell, DataModelingEditExcel dMEditExcel){
		 HSSFRow row1 = sheet.createRow(5);
		 HSSFRow row2 = sheet.createRow(6);
		 HSSFCellStyle style = wb.createCellStyle();
		 String[] array = {"#", "��Ŀ��", "��ĿID","����","����","����","�ֽ���","NOT NULL","KEY/����","�������","��ע"}; 
		 for(int i=0;i < 11;i++){
			 sheet.setColumnWidth((short) i, (short) 2700);
			  cell = row1.createCell((short)i);
			  cell.setCellValue(i+1);  
		 }
		 for(int i=0;i<array.length;i++){
			 cell = row2.createCell((short)i);
			  cell.setCellValue(array[i]);
			  style.setAlignment(style.ALIGN_CENTER);//ˮƽ����
			  dMEditExcel.styleBorderTop(style,cell);
		 }
	}
	public String tableDesc(DataModelingConnect dmConnect, DataModelingEditExcel dMEditExcel, String table){
		Map<String, Map<String,String>> map=dmConnect.getDataBaseTableDetailed(table);
		 Iterator mapKeyArray=map.get("DataBaseTableDetailed").keySet().iterator();
		 return map.get("DataBaseTableDetailed").get("����");
	}
	public String sheetTableNameHandle(String tableDesc,String sheetTable, int i){
		//System.out.println(tableDesc);
		//System.out.println(tableDesc.equals(""));
		String sheetName = "";
		if(tableDesc.equals("")){
			sheetName = sheetTable.length() > 31 ? sheetTable.substring(0,27)+"_"+i : sheetTable;
		}else {
			sheetName = tableDesc.length() > 31 ? tableDesc.substring(0,31) : tableDesc+"_"+i;
		}
		System.out.println(sheetName);
		System.out.println(sheetName.length());
		return sheetName;
	}
	public void styleBorderTop(HSSFCellStyle style, HSSFCell cell){
		    style.setBorderTop(style.SOLID_FOREGROUND);   //�����±߿�  
		    style.setBorderBottom(style.SOLID_FOREGROUND );
	        style.setBorderLeft(style.SOLID_FOREGROUND);   //������߿�  
	        style.setBorderRight(style.SOLID_FOREGROUND);   //�����б߿�  
	        cell.setCellStyle(style);  
	}
	public void styleBorderBody(HSSFCellStyle style,HSSFCell cell){
	    style.setBorderTop(style.BORDER_DOTTED);   //�����±߿�  
	    style.setBorderBottom(style.BORDER_DOTTED );
        style.setBorderLeft(style.BORDER_DOTTED);   //������߿�  
        style.setBorderRight(style.BORDER_DOTTED);   //�����б߿�  
        cell.setCellStyle(style);  
}
	public static void setHeigthAndColumnWidth(HSSFSheet sheet, HSSFRow row, int i){
		row.setHeight((short) 8);
		sheet.setColumnWidth((short) i, (short) 3000);
	}
}
