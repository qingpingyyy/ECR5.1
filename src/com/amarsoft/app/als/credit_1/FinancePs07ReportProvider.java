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

public class FinancePs07ReportProvider extends DefaultDataSourceProvider{

	private Connection conn1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private Log logger = ARE.getLog();
    String database = "ecr";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate ="";
    BigDecimal b1 ;
    
    String sSql="";
    ResultSet rs = null;
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        
        //2007利润及利润分配表
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0292' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
        	  sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo ="";
	     	 
        	  Map m = new HashMap();
	     	  sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
	     	  ps=conn1.prepareStatement(sSql);
	     	  rs= this.ps.executeQuery();
	     	  while(rs.next()){
	     		  if(!m.containsKey(rs.getString("rowSubject")))//如果有重复的rowsubject则跳出
	     		  {
	     			 m.put(rs.getString("rowSubject"), rs.getDouble("col2Value"));  
	     		  }else{
	     			  continue;
	     		  }
	     		  
	     	  }
	     	  rs.close();
	     	  ps.close();
	     	  
	     	 //企业2007版利润及利润分配信息
	     	 currentRecord.getField("REVENUEOFSALES").setValue(m.get("5a1").toString()); //营业收入                     	
	         currentRecord.getField("COSTOFSALES").setValue(m.get("440").toString()); //营业成本                    	
	         currentRecord.getField("BUSINESSANDOTHERTAXES").setValue(m.get("504").toString()); //营业税金及附加                
	         currentRecord.getField("SELLINGEXPENSES").setValue(m.get("527").toString()); //销售费用                      
	         currentRecord.getField("M9174").setValue(m.get("507").toString()); //管理费用                     	
	         currentRecord.getField("FINANCIALEXPENSE").setValue(m.get("508").toString()); //财务费用508                     	
	         currentRecord.getField("IMPAIRMENTLOSSOFASSETS").setValue(m.get("b58").toString()); //资产减值损失                  
	         currentRecord.getField("M9177").setValue(m.get("5b4").toString()); //公允价值变动净收益            
	         currentRecord.getField("INVESTMENTINCOME").setValue(m.get("446").toString()); //投资净收益                    
	         currentRecord.getField("M9179").setValue(m.get("5b5").toString()); //对联营企业和合营企业的投资收益
	         
	         //double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10;
	         b1 = new BigDecimal(m.get("5a1").toString()).subtract(new BigDecimal(m.get("440").toString())).subtract(new BigDecimal(m.get("504").toString())).subtract(new BigDecimal(m.get("527").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).subtract(new BigDecimal(m.get("b58").toString())).add(new BigDecimal(m.get("5b4").toString())).add(new BigDecimal(m.get("446").toString())).add(new BigDecimal(m.get("5b5").toString()));
	         currentRecord.getField("OPERATINGPROFITS").setValue(b1.toString());//营业利润      9170-9171-9172-9173-9174-9175-9176+9177+9178
	         
	         
	         currentRecord.getField("NONOPERATINGINCOME").setValue(m.get("512").toString()); //营业外收入                    
	         currentRecord.getField("NONOPERATINGEXPENSES").setValue(m.get("513").toString()); //营业外支出                    
	         currentRecord.getField("NONCURRENTASSETS").setValue(m.get("5b6").toString()); //非流动资产损失                
	         
	         b1 = new BigDecimal(m.get("5a1").toString()).subtract(new BigDecimal(m.get("440").toString())).subtract(new BigDecimal(m.get("504").toString())).subtract(new BigDecimal(m.get("527").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).subtract(new BigDecimal(m.get("b58").toString())).add(new BigDecimal(m.get("5b4").toString())).add(new BigDecimal(m.get("446").toString())).add(new BigDecimal(m.get("5b5").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("513").toString()));
	         currentRecord.getField("PROFITBEFORETAX").setValue(b1.toString());//利润总额    9180+91819182
	         
	         currentRecord.getField("INCOMETAXEXPENSE").setValue(m.get("450").toString()); //所得税费用516                    
	         
	         b1 = new BigDecimal(m.get("5a1").toString()).subtract(new BigDecimal(m.get("440").toString())).subtract(new BigDecimal(m.get("504").toString())).subtract(new BigDecimal(m.get("527").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).subtract(new BigDecimal(m.get("b58").toString())).add(new BigDecimal(m.get("5b4").toString())).add(new BigDecimal(m.get("446").toString())).add(new BigDecimal(m.get("5b5").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("513").toString())).add(new BigDecimal(m.get("450").toString()));
	         currentRecord.getField("NETPROFIT").setValue(b1.toString());//净利润               9184-9185
	         
	         currentRecord.getField("BASICEARNINGSPERSHARE").setValue(m.get("5b8").toString()); //基本每股收益                  
	         currentRecord.getField("DILUTEDEARNINGSPERSHARE").setValue(m.get("5b9").toString()); //稀释每股收益  
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
        
    }
	
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


