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
 *         更多详情请访问<a href="http://bbs.svgteam.com">亡灵战线</a>
 *         <h1>本次更新如下:</h1> 1.加入新的方法init(String dataBaseName)</br>
 *         如果数据库密码均为root可直接填写数据库名 2.优化核心代码,添加日志信息 3.优化已知的BUG,添加一个新的测试功能(调试时遍历集合)
 *         <br/>
 *         <h1>date:2017年3月21日</h1><br/>
 *         此次更新如下：<br/>
 *         1.添加了新的核心方法<br/>
 *         2.现已支持指定列的查询
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
							// System.err.println("信息:"+fields[i].getGenericType().getTypeName());
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
					// System.err.println("信息:"+fields[i].getGenericType().getTypeName());
					if (fields[i].getGenericType().getTypeName().equals("java.lang.Integer"))
						fields[i].set(o, rs.getInt(i + 1));
					fields[i].set(o, rs.getObject(i + 1));
				}
				list.add(o);
			}
		}
		System.err.println("信息：共查询到:" + list.size() + "记录,消耗时间:" + (System.currentTimeMillis() - start) + "ms");
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
