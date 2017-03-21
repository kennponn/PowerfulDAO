package com.svgteam.util;
//
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
///***
// * 
// * 
// * */
public class EntityFramework extends BaseDao {
//	List<String> cols = new ArrayList<String>();
//	List<String> names = new ArrayList<String>();
//	private String tableName = "";
//	
//	public void getEntity(String tableName) throws SQLException{
//		String sql = "select * from "+tableName;
//		super.open();
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		ResultSetMetaData rsmd  = rs.getMetaData();
//		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//			names.add(rsmd.getColumnName(i))  ;
//			cols.add(getEntityType(rsmd.getColumnType(i)));
//		}
//		
//	}
//	
//	public String getCode(){
//		StringBuffer code = new StringBuffer();
//		for (int i = 0; i < cols.size(); i++) {
//			code.append("public class "+tableName.substring(0, 1).toUpperCase()+tableName.substring(1).toLowerCase());
//			code.append("{");
//			for (int j = 0; j < ; j++) {
//				
//			}
//			code.append("")
//		}
//	}
//	
//	private String getEntityType(int type){
//		switch(type){
//		case 4:return "Integer";
//		case 12:return "String";
//		
//		}
//		return null;
//	}
}
