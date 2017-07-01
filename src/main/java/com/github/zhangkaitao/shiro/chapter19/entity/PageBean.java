package com.github.zhangkaitao.shiro.chapter19.entity;

import java.util.List;

import org.springframework.util.Assert;

public class PageBean<T> {
	
	public static final  int  DEFAULT_PAGE_SIZE = 10;
	
	private int pageSize;  //每页显示多少条记录
	
	private int currentPage; //当前第几页数据
	
	private int totalRecord; // 一共多少条记录
	
	private int totalPage;
	
	private List<T> datas;
	
	public PageBean(){
	}

	public PageBean(int pageSize, int pageNum, List<T> sourceList){
		this.pageSize = pageSize;
		this.totalRecord = sourceList.size();
		this.totalPage = this.totalRecord/this.pageSize;
		if(this.totalRecord % this.pageSize !=0)
			this.totalPage = this.totalPage + 1;
		this.currentPage=this.totalPage<pageNum?this.totalPage:pageNum;
		int fromIndex = this.pageSize * (pageNum -1);
		int toIndex = pageSize*pageNum> totalRecord? totalRecord:pageSize*pageNum;
		this.datas = sourceList.subList(fromIndex, toIndex);
	}
	
	public PageBean(int pageSize, int currentPage, int totalRecord,  List<T> datas) {
		Assert.isTrue(datas.size()<=pageSize);
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecord = totalRecord;
		this.totalPage = this.totalRecord/this.pageSize;
		if(this.totalRecord % this.pageSize !=0)
			this.totalPage = this.totalPage + 1;
		this.datas = datas;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getTatalPage() {
		return totalPage;
	}

	public List<T> getList() {
		return datas;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void setTatalPage(int tatalPage) {
		this.totalPage = tatalPage;
	}

	public void setList(List<T> list) {
		this.datas = list;
	}

	
	
	
}
