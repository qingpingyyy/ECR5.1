/*
 * 文件名：ReportProvider.java
 * 版权：Copyright by www.amarsoft.com
 * 描述：
 * 修改人：amarsoft
 * 修改时间：2018年9月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.amarsoft.app.als.credit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.are.ARE;
import com.amarsoft.are.dpx.recordset.DefaultDataSourceProvider;
import com.amarsoft.are.dpx.recordset.RecordSet;
import com.amarsoft.are.dpx.recordset.RecordSetException;
import com.amarsoft.are.log.Log;

public class FinanceBs07ReportProvider extends DefaultDataSourceProvider{

    // 定义数据库连接和预编译语句
    private Connection conn1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private Log logger = ARE.getLog();
    String database = "ecr";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate = "";
    BigDecimal b1 ;
    String sSql="";
    ResultSet rs = null;
    
    // 填充记录
    public void fillRecord() {
        try {
			//调用父类的fillRecord方法
			super.fillRecord();
			//获取当前记录的CUSTOMERID字段值，如果为空则赋值为空字符串
			sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
	        //获取当前记录的SHEETYEAR字段值，如果为空则赋值为空字符串
	        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
	        
	        //2007资产负债表信息记录
	        //构造 SQL 查询 report_record 和 report_data，根据客户号和年份（objectno 和 reportdate），
			//查找资产负债表（modelno='0291'）的 reportno。
	        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo 
										from report_record rr ,report_data rd 
										where rr.reportno =rd.reportno  
										and rr.modelno ='0291' 
										and rr.reportscope <>'03' 
										and rr.objectno =? 
										and rr.REPORTDATE  ='"+sReportDate+"'";
	        //获取数据库连接
	        conn1 = ARE.getDBConnection(database);
	        //查询REPORT_DATA表，获取rowSubject和col2Value字段值
	        sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
	        //预编译SQL语句
	        ps=conn1.prepareStatement(sSql);
	        ps1=conn1.prepareStatement(sQueryReportNo);
	        //设置SQL语句中的参数
	        ps1.setString(1, sCustomerID);
	        //执行查询
	        rs1= this.ps1.executeQuery();
	        
	        while(rs1.next()){
	        	 //获取reportno字段值，如果为空则赋值为空字符串
	        	 sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo =""; 
	        	 //创建一个Map，用于存储rowSubject和col2Value字段值
	        	 Map m = new HashMap();
	        	 //清空SQL语句中的参数
	        	 ps.clearParameters();
		     	 //执行查询
		     	 rs= this.ps.executeQuery();
		     	 while(rs.next()){
		     		  //如果Map中已经存在rowSubject字段值，则跳出循环
		     		  if(!m.containsKey(rs.getString("rowSubject")))//如果有重复的rowsubject则跳出
		     		  {
		     			 //将rowSubject和col2Value字段值存入Map中
		     			 m.put(rs.getString("rowSubject"), rs.getDouble("col2Value"));  
		     		  }else{
		     			  //如果Map中已经存在rowSubject字段值，则跳出循环
		     			  continue;
		     		  }
		     		  
		     	  }
		     	  //关闭结果集和预编译语句
		     	  rs.close();
		     	  ps.close();
	        	 
		     	 //2007资产负债表信息记录
		     	  //将Map中的值赋给当前记录的相应字段
		     	  currentRecord.getField("CURRENCYFUNDS").setValue(m.get("101").toString()); //货币资金101
		          currentRecord.getField("M9101").setValue(m.get("147").toString()); //交易性金融资产   631    
		          currentRecord.getField("NOTESRECEIVABLE").setValue(m.get("103").toString()); //应收票据  103          	
		          currentRecord.getField("ACCOUNTSRECEIVABLE").setValue(m.get("104").toString()); //应收账款  106          	
		          currentRecord.getField("PREPAYMENTS").setValue(m.get("107").toString()); //预付账款   107         	
		          currentRecord.getField("INTERESTRECEIVABLE").setValue(m.get("19c").toString()); //应收利息   19c         	
		          currentRecord.getField("DIVIDENDSRECEIVABLE").setValue(m.get("134").toString()); //应收股利 134           	
		          currentRecord.getField("OTHERRECEIVABLES").setValue(m.get("108").toString()); //其他应收款  108         
		          currentRecord.getField("INVENTORIES").setValue(m.get("110").toString()); //存货       110        	
		          currentRecord.getField("M9109").setValue(m.get("114").toString()); //一年内到期的非流动资产 632
		          currentRecord.getField("OTHERCURRENTASSETS").setValue(m.get("115").toString()); //其他流动资产    115+109     
		          
		          //计算流动资产合计
		          b1 = new BigDecimal(m.get("101").toString())
				  .add(new BigDecimal(m.get("147").toString()))
				  .add(new BigDecimal(m.get("103").toString()))
				  .add(new BigDecimal(m.get("104").toString()))
				  .add(new BigDecimal(m.get("107").toString()))
				  .add(new BigDecimal(m.get("19c").toString()))
				  .add(new BigDecimal(m.get("134").toString()))
				  .add(new BigDecimal(m.get("108").toString()))
				  .add(new BigDecimal(m.get("110").toString()))
				  .add(new BigDecimal(m.get("114").toString()))
				  .add(new BigDecimal(m.get("115").toString()));
		          currentRecord.getField("TOTALCURRENTASSETS").setValue(b1.toString());//流动资产合计 //9100+9101+9102+9103+9104+9105+9106+9107+9108+9109+9110
		          
		          //将Map中的值赋给当前记录的相应字段
		          currentRecord.getField("M9112").setValue(m.get("162").toString()); //可供出售的金融资产   633
		          currentRecord.getField("M9113").setValue(m.get("163").toString()); //持有至到期投资       634
		          currentRecord.getField("LONGTERMEQUITYINVESTMENT").setValue(m.get("116").toString()); //长期股权投资   648      
		          currentRecord.getField("LONGTERMRECEIVABLES").setValue(m.get("164").toString()); //长期应收款       635    
		          currentRecord.getField("INVESTMENTPROPERTIES").setValue(m.get("165").toString()); //投资性房地产   636      
		          currentRecord.getField("FIXEDASSETS").setValue(m.get("142").toString()); //固定资产    119        	
		          currentRecord.getField("CONSTRUCTIONINPROGRESS").setValue(m.get("120").toString()); //在建工程 120           	
		          currentRecord.getField("CONSTRUCTIONMATERIALS").setValue(m.get("143").toString()); //工程物资  127          	
		          currentRecord.getField("M9120").setValue(m.get("121").toString()); //固定资产清理  121       
		          currentRecord.getField("M9121").setValue(m.get("166").toString()); //生产性生物资产  637     
		          currentRecord.getField("OILANDGASASSETS").setValue(m.get("167").toString()); //油气资产 638          	
		          currentRecord.getField("INTANGIBLEASSETS").setValue(m.get("123").toString()); //无形资产  129          	
		          currentRecord.getField("DEVELOPMENTDISBURSEMENTS").setValue(m.get("168").toString()); //开发支出130           	
		          currentRecord.getField("GOODWILL").setValue(m.get("169").toString()); //商誉       131       	 
		          currentRecord.getField("LONGTERMDEFERREDEXPENSES").setValue(m.get("181").toString()); //长期待摊费用       132  
		          currentRecord.getField("DEFERREDTAXASSETS").setValue(m.get("126").toString()); //递延所得税资产 133     	
		          currentRecord.getField("OTHERNONCURRENTASSETS").setValue(m.get("170").toString()); //其他非流动资产   639+125    
		          
		          //计算非流动资产合计
		          b1 = new BigDecimal(m.get("162").toString())
				  .add(new BigDecimal(m.get("163").toString()))
				  .add(new BigDecimal(m.get("116").toString()))
				  .add(new BigDecimal(m.get("164").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("142").toString()))
				  .add(new BigDecimal(m.get("120").toString()))
				  .add(new BigDecimal(m.get("143").toString()))
				  .add(new BigDecimal(m.get("121").toString()))
				  .add(new BigDecimal(m.get("166").toString()))
				  .add(new BigDecimal(m.get("167").toString()))
				  .add(new BigDecimal(m.get("123").toString()))
				  .add(new BigDecimal(m.get("168").toString()))
				  .add(new BigDecimal(m.get("169").toString()))
				  .add(new BigDecimal(m.get("181").toString()))
				  .add(new BigDecimal(m.get("126").toString()))
				  .add(new BigDecimal(m.get("170").toString()));
		          currentRecord.getField("TOTALNONCURRENTASSETS").setValue(b1.toString());//非流动资产合计     9112+9113+9114+9115+9116+9117+9118+9119+9120+9121+9122+9123+9124+9125+9126+9127+9128
		          
		          //计算资产总计
		          b1 = new BigDecimal(m.get("101").toString())
				  .add(new BigDecimal(m.get("147").toString()))
				  .add(new BigDecimal(m.get("103").toString()))
				  .add(new BigDecimal(m.get("104").toString()))
				  .add(new BigDecimal(m.get("107").toString()))
				  .add(new BigDecimal(m.get("19c").toString()))
				  .add(new BigDecimal(m.get("134").toString()))
				  .add(new BigDecimal(m.get("108").toString()))
				  .add(new BigDecimal(m.get("110").toString()))
				  .add(new BigDecimal(m.get("114").toString()))
				  .add(new BigDecimal(m.get("115").toString()))
				  .add(new BigDecimal(m.get("162").toString()))
				  .add(new BigDecimal(m.get("163").toString()))
				  .add(new BigDecimal(m.get("116").toString()))
				  .add(new BigDecimal(m.get("164").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("120").toString()))
				  .add(new BigDecimal(m.get("143").toString()))
				  .add(new BigDecimal(m.get("121").toString()))
				  .add(new BigDecimal(m.get("166").toString()))
				  .add(new BigDecimal(m.get("167").toString()))
				  .add(new BigDecimal(m.get("123").toString()))
				  .add(new BigDecimal(m.get("168").toString()))
				  .add(new BigDecimal(m.get("169").toString()))
				  .add(new BigDecimal(m.get("181").toString()))
				  .add(new BigDecimal(m.get("126").toString()))
				  .add(new BigDecimal(m.get("170").toString()));
				  //流动资产和非流动资产合计
		          currentRecord.getField("TOTALASSETS").setValue(b1.toString()); //资产总计  9111+9129
		          
		          //将Map中的值赋给当前记录的相应字段
		          currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("201").toString()); //短期借款          201  	
		          currentRecord.getField("M9132").setValue(m.get("234").toString()); //交易性金融负债  640     
		          currentRecord.getField("NOTESPAYABLE").setValue(m.get("202").toString()); //应付票据  202          	
		          currentRecord.getField("ACCOUNTSPAYABLE").setValue(m.get("203").toString()); //应付账款  203           
		          currentRecord.getField("RECEIPTSINADVANCE").setValue(m.get("204").toString()); //预收账款 204           	
		          currentRecord.getField("INTERESTPAYABLE").setValue(m.get("274").toString()); //应付利息274            	
		          currentRecord.getField("EMPLOYEEBENEFITSPAYABLE").setValue(m.get("218").toString()); //应付职工薪酬  206       
		          currentRecord.getField("TAXSPAYABLE").setValue(m.get("207").toString()); //应交税费     651       	
		          currentRecord.getField("DIVIDENDSPAYABLE").setValue(m.get("209").toString()); //应付股利    这里设计为编号209，在表格中出现        	
		          currentRecord.getField("OTHERPAYABLES").setValue(m.get("208").toString()); //其他应付款           
		          currentRecord.getField("M9141").setValue(m.get("211").toString()); //一年内到期的非流动负债 643
		          currentRecord.getField("OTHERCURRENTLIABILITIES").setValue(m.get("212").toString()); //其他流动负债   212    
		          
		          
		          //计算流动负债合计
		          b1 = new BigDecimal(m.get("201").toString())
				  .add(new BigDecimal(m.get("234").toString()))
				  .add(new BigDecimal(m.get("202").toString()))
				  .add(new BigDecimal(m.get("203").toString()))
				  .add(new BigDecimal(m.get("204").toString()))
				  .add(new BigDecimal(m.get("274").toString()))
				  .add(new BigDecimal(m.get("218").toString()))
				  .add(new BigDecimal(m.get("207").toString()))
				  .add(new BigDecimal(m.get("208").toString()))
				  .add(new BigDecimal(m.get("209").toString()))
				  .add(new BigDecimal(m.get("211").toString()))
				  .add(new BigDecimal(m.get("212").toString()));
		          currentRecord.getField("TOTALCURRENTLIABILITIES").setValue(b1.toString());//流动负债合计 9131+9132+9133+9134+9135+9136+9137+9138+9139+9140+9141+9142
		          
		          //将Map中的值赋给当前记录的相应字段
		          currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("213").toString()); //长期借款213            	
		          currentRecord.getField("BONDSPAYABLES").setValue(m.get("214").toString()); //应付债券    214        	
		          currentRecord.getField("LONGTERMPAYABLES").setValue(m.get("215").toString()); //长期应付款    215       
		          currentRecord.getField("GRANTSPAYABLE").setValue(m.get("223").toString()); //专项应付款     644      
		          currentRecord.getField("PROVISIONS").setValue(m.get("222").toString()); //预计负债            	
		          currentRecord.getField("DEFERREDTAXLIABILITIES").setValue(m.get("217").toString()); //递延所得税负652债       
		          currentRecord.getField("M9150").setValue(m.get("245").toString()); //其他非流动负债       
		          
		          //计算非流动负债合计
		          b1 = new BigDecimal(m.get("213").toString())
				  .add(new BigDecimal(m.get("214").toString()))
				  .add(new BigDecimal(m.get("215").toString()))
				  .add(new BigDecimal(m.get("223").toString()))
				  .add(new BigDecimal(m.get("222").toString()))
				  .add(new BigDecimal(m.get("217").toString()))
				  .add(new BigDecimal(m.get("245").toString()));
		          currentRecord.getField("M9151").setValue(b1.toString());//非流动负债合计   9144+9145+9146+9147+9148+9149+9150
		          
		          //计算负债合计
		          //负债合计            	9143+9151
				  //流动负债+非流动负债
		          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("234").toString())).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("207").toString())).add(new BigDecimal(m.get("208").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("217").toString())).add(new BigDecimal(m.get("245").toString()));
		          currentRecord.getField("TOTALLIABILITIES").setValue(b1.toString());//
		          
		          //将Map中的值赋给当前记录的相应字段
		          currentRecord.getField("M9153").setValue(m.get("301").toString()); //实收资本（或股本）   
		          currentRecord.getField("CAPITALRRSERVE").setValue(m.get("302").toString()); //资本公积   302          
		          currentRecord.getField("LESSTREASURYSTOCKS").setValue(m.get("309").toString()); //减：库存股           
		          currentRecord.getField("SURPLUSRESERVE").setValue(m.get("303").toString()); //盈余公积            	
		          currentRecord.getField("UNAPPROPRIATEDPROFIT").setValue(m.get("305").toString()); //未分配利润        
		          
		          //计算所有者权益合计
		          b1 = new BigDecimal(m.get("301").toString())
				  .add(new BigDecimal(m.get("302").toString()))
				  .add(new BigDecimal(m.get("309").toString()))
				  .add(new BigDecimal(m.get("303").toString()))
				  .add(new BigDecimal(m.get("305").toString()));
		          currentRecord.getField("TOTALEQUITY").setValue(b1.toString());//所有者权益合计       9153+9154-9155+9156+9157
		          
		          
		          //计算负债和所有者权益总计
		          b1 = new BigDecimal(m.get("201").toString())
				  .add(new BigDecimal(m.get("234").toString()))
				  .add(new BigDecimal(m.get("202").toString()))
				  .add(new BigDecimal(m.get("203").toString()))
				  .add(new BigDecimal(m.get("204").toString()))
				  .add(new BigDecimal(m.get("274").toString()))
				  .add(new BigDecimal(m.get("218").toString()))
				  .add(new BigDecimal(m.get("207").toString()))
				  .add(new BigDecimal(m.get("208").toString()))
				  .add(new BigDecimal(m.get("211").toString()))
				  .add(new BigDecimal(m.get("212").toString()))
				  .add(new BigDecimal(m.get("213").toString()))
				  .add(new BigDecimal(m.get("214").toString()))
				  .add(new BigDecimal(m.get("215").toString()))
				  .add(new BigDecimal(m.get("223").toString()))
				  .add(new BigDecimal(m.get("222").toString()))
				  .add(new BigDecimal(m.get("217").toString()))
				  .add(new BigDecimal(m.get("245").toString()))
				  .add(new BigDecimal(m.get("301").toString()))
				  .add(new BigDecimal(m.get("302").toString()))
				  .add(new BigDecimal(m.get("309").toString()))
				  .add(new BigDecimal(m.get("303").toString()))
				  .add(new BigDecimal(m.get("305").toString()));
		          currentRecord.getField("TOTALEQUITYANDLIABILITIES").setValue(b1.toString());
	     	   
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				//关闭结果集和预编译语句
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn1 != null) conn1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
    }
    
    // 打开记录集
    public void open(RecordSet recordSet) throws RecordSetException {
        super.open(recordSet);
        
    }
    
    /**
     * 关闭数据库资源 */
    
    public void close() throws RecordSetException {
        if (ps != null) {
            try {
                ps.close();
                ps = null;
                logger.debug("关闭preparedstatement成功");
            } catch (SQLException e) {
                logger.debug("关闭preparedstatement出错", e);
            }
        }
        if (conn1 != null) {
            try {
            	conn1.close();
            	conn1 = null;
                logger.debug("关闭connection成功");
            } catch (SQLException e) {
                logger.debug("关闭connection出错", e);
            }
        }
        super.close();
    }

}


