package com.svgteam.page;

import java.util.ArrayList;
import java.util.List;

import com.svgteam.util.PowerfulDao;
/**
 * Powerful Split Pages 1.0
 * 
 * @author <h1>o.kEnnponN</h1> <br/>
 *         �������������<a href="http://bbs.svgteam.com">����ս��</a> OR <a href="https://github.com/kennponn/PowerfulDAO">GitHub</a>
 *         <h1>date:2017��3��28��</h1><br/>
 *         �˴θ������£�<br/>
 *         1.�ṩ��ʵ�����ݵķ�ҳ����<br/>
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
			System.err.println("PowerfulSplitPages --> ����:��⵽����ҳ������,������ҳ���Զ���ҳ�����õ�["+currPage+"]ҳ");
		}
		if(currPage>=pageSign.getMaxPages()){
			currPage = pageSign.getMaxPages();
			System.err.println("PowerfulSplitPages --> ����:��⵽����ҳ������,������ҳ���Զ���ҳ�����õ�["+currPage+"]ҳ");
		}
		for (int i =(currPage - 1) * pageSign.getRows(); i < currPage * pageSign.getRows(); i++) {
			if(i<pageSign.getRecords())
				newList.add((W) data.get(i));
		}
		System.err.println("PowerfulSplitPages --> ��Ϣ:��ǰҳ:"+currPage+"��ҳ��:"+pageSign.getMaxPages()+"�ܼ�¼��:"+pageSign.getRecords());
		return newList;
	}
	@Override
	public <W> List<W> getRecord(W obj) throws Exception {
		// TODO Auto-generated method stub
		return powerfulDao.query("select * from " + (obj.getClass().getName()).substring((obj.getClass().getName()).lastIndexOf(".")+1), obj);
	}

}
