package com.svgteam.page;

import java.util.ArrayList;
import java.util.List;

import com.svgteam.util.PowerfulDao;
/**
 * Powerful Split Pages 1.0
 * 
 * @author <h1>o.kEnnponN</h1> <br/>
 *         更多详情请访问<a href="http://bbs.svgteam.com">亡灵战线</a> OR <a href="https://github.com/kennponn/PowerfulDAO">GitHub</a>
 *         <h1>date:2017年3月28日</h1><br/>
 *         此次更新如下：<br/>
 *         1.提供了实体数据的分页功能<br/>
 */
@SuppressWarnings("hiding")
public class PowerfulSplitPages<W> implements AbstractPageTool,Record {
	private W obj;
	private PowerfulDao powerfulDao ;
	private PageSign pageSign;
	private List<W> data;
	public PowerfulSplitPages(Integer rows) {
		if(pageSign==null)
			pageSign = new PageSign();
		pageSign.setRows(rows);
	}
	public void init(W obj,PowerfulDao powerfulDao) throws Exception{
		this.powerfulDao = powerfulDao;
		this.obj = obj;
	}
	private void getMaxPages(){
		pageSign.setMaxPages(pageSign.getRecords()/10==0?pageSign.getRecords()/10:pageSign.getRecords()/10+1);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public <W> List<W> split(Integer currPage) throws Exception {
		// TODO Auto-generated method stub
		List<W> newList = new ArrayList<>();
		data = getRecord(obj);
		pageSign.setRecords(data.size());
		getMaxPages();
		if(currPage<=0){
			currPage = 1;
			System.err.println("PowerfulSplitPages --> 警告:检测到您的页码有误,给力分页已自动将页码设置第["+currPage+"]页");
		}
		if(currPage>=pageSign.getMaxPages()){
			currPage = pageSign.getMaxPages();
			System.err.println("PowerfulSplitPages --> 警告:检测到您的页码有误,给力分页已自动将页码设置第["+currPage+"]页");
		}
		for (int i =(currPage - 1) * pageSign.getRows(); i < currPage * pageSign.getRows(); i++) {
			if(i<pageSign.getRecords())
				newList.add((W) data.get(i));
		}
		System.err.println("PowerfulSplitPages --> 信息:当前页:"+currPage+"总页数:"+pageSign.getMaxPages()+"总记录数:"+pageSign.getRecords());
		return newList;
	}
	@Override
	public <W> List<W> getRecord(W obj) throws Exception {
		// TODO Auto-generated method stub
		return powerfulDao.query("select * from " + (obj.getClass().getName()).substring((obj.getClass().getName()).lastIndexOf(".")+1), obj);
	}

}
