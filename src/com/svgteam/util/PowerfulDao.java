package com.svgteam.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Powerful Dao 1.2
 * 
 * @author <h1>o.kEnnponN</h1> <br/>
 *         This is a framework that allows DAO to be very flexible, and if you
 *         find that you have too many entities and build more entity DAO<br/>
 *         �������������<a href="http://bbs.svgteam.com">����ս��</a>
 *         <h1>���θ�������:</h1> 1.�����µķ���init(String dataBaseName)</br>
 *         ������ݿ������Ϊroot��ֱ����д���ݿ��� 2.�Ż����Ĵ���,�����־��Ϣ 3.�Ż���֪��BUG,���һ���µĲ��Թ���(����ʱ��������)
 *         <br/>
 *         <h1>date:2017��3��21��</h1><br/>
 *         �˴θ������£�<br/>
 *         1.������µĺ��ķ���<br/>
 *         2.����֧��ָ���еĲ�ѯ
 */
public class PowerfulDao extends BaseDao {
	@SuppressWarnings({ "unchecked" })
	public <W> List<W> query(String sql, W obj, Object... args) throws Exception {
		Class<? extends Object> cls = obj.getClass();
		List<W> list = new ArrayList<>();
		long start = System.currentTimeMillis();
		open();
		ps = conn.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i + 1, args[i]);
		}
		rs = ps.executeQuery();
		Field[] fields = cls.getDeclaredFields();
		if (validate(sql)) {
			String[] colName = splitSql(sql);
			while (rs.next()) {
				W o = (W) cls.newInstance();
				for (int i = 0; i < fields.length; i++) {
					for (int j = 0; j < colName.length; j++) {
						if (fields[i].getName().equals(colName[j])) {
							fields[i].setAccessible(true);
							// System.err.println("��Ϣ:"+fields[i].getGenericType().getTypeName());
							if (fields[i].getGenericType().getTypeName().equals("java.lang.Integer"))
								fields[i].set(o, rs.getInt(j+ 1));
							fields[i].set(o, rs.getObject(j + 1));
						}
					}
				}
				list.add(o);
			}
		} else {
			while (rs.next()) {
				W o = (W) cls.newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					// System.err.println("��Ϣ:"+fields[i].getGenericType().getTypeName());
					if (fields[i].getGenericType().getTypeName().equals("java.lang.Integer"))
						fields[i].set(o, rs.getInt(i + 1));
					fields[i].set(o, rs.getObject(i + 1));
				}
				list.add(o);
			}
		}
		System.err.println("��Ϣ������ѯ��:" + list.size() + "��¼,����ʱ��:" + (System.currentTimeMillis() - start) + "ms");
		return list;
	}

	public Boolean validate(String sql) {
		if (sql.indexOf("*") != -1) {
			return false;
		} else
			return true;
	}

	public String[] splitSql(String sql) {
		String[] colName = null;
		colName  =	sql.split(",");
		colName[0] = colName[0].substring(colName[0].indexOf(" ") + 1);
		colName[colName.length - 1] = colName[colName.length - 1].substring(0,
				colName[colName.length - 1].indexOf("from") - 1);
		return colName;
	}

	public Integer operation(String sql, Object... objs) throws Exception {
		// TODO Auto-generated method stub
		super.open();
		super.ps = super.conn.prepareStatement(sql);
		for (int i = 0; i < objs.length; i++) {
			ps.setObject(i + 1, objs[i]);
		}
		Integer i = ps.executeUpdate();
		super.close();
		return i;
	}

}
