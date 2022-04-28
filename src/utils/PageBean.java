package utils;

import entity.UserDemo;

import java.util.ArrayList;
import java.util.List;

public class PageBean {

    //分页查询的工具类
    //封装分页查询的数据
    //后面分页查询时，把pageBean对象放入域对象，响应到页面上
    private Integer pageTotal;
    private Integer pageSize;
    private Integer pageCount;
    private Integer p;
    private List<UserDemo> dataList;

    public List<UserDemo> getDataList() {
        return dataList;
    }

    public void setDataList(List<UserDemo> dataList) {
        this.dataList = dataList;
    }

    public PageBean() {
        //如果不进行设置，默认每页显示3条记录
        pageSize = 3;
        //初始化创建数据对象
        dataList = new ArrayList<>();
    }

    public Integer getPageTotal() {
        return pageTotal;
    }
    //设置总共有多少条记录，同时把pageCount给计算出来
    public void setPageTotal(Integer pageTotal) {

        this.pageTotal = pageTotal;
        if (pageTotal%pageSize==0){
            pageCount = pageTotal / pageSize;
        }else {
            pageCount = pageTotal / pageSize + 1;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }
// 设置 pageCount在设置pageTotal 时就计算完成 所以 此处可以不用set方法获取
    /*public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }*/

    public Integer getP() {
        return p;
    }

    //设置当前是第几页
    //1.如果是首页，就不能上一页了
    //2.如果是尾页，就不能下一页了
    public void setP(Integer p) {
        //如果小于首页
        if(p < 1){
            //默认第1页
            p = 1;
        }else if(p > pageCount){ //如果大于了尾页
            //默认尾页
            p = pageCount;
        }
        this.p = p;
    }


}
