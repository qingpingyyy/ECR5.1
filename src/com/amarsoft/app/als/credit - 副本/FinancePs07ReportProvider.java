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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    String database = "loan";
    String sReportNo="";
    String sCustomerID="";
    String sReportType="";//报表类型
    String s1="";
    ResultSet rs1 = null;
    String sReportDate = "";
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        sReportType =currentRecord.getField("SHEETTYPE").getString(); if(sReportType==null) sReportType= "" ;
        
        //2007利润及利润分配表
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0292' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        ARE.getLog().info("----------sCustomerID ->"+sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
	     	  ARE.getLog().info("==============="+sReportNo);
	     	//ps = connection.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");
	     	  s1=sReportDate.substring(0,4);//截取财务报表年份
	     	  //030半年报
	          if("030".equals(sReportType)&&!"".equals(sReportDate)){
	          	if(sReportDate.compareTo(s1+"/06") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("20");//上半年
	          	}else{
	          		currentRecord.getField("SHEETTYPE").setValue("30");//下半年
	          	}
	          }
	          //020季报
	          if("020".equals(sReportType)&&!"".equals(sReportDate)){
	          	if(sReportDate.compareTo(s1+"/03") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("40");//第一季度
	          	}else if (sReportDate.compareTo(s1+"/06") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("50");//第二季度
	          	}else if (sReportDate.compareTo(s1+"/09") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("60");//第三季度
	          	}else{
	          		currentRecord.getField("SHEETTYPE").setValue("70");//第四季度
	          	}
	          }
	          //040年报
	          if("040".equals(sReportType)&&!"".equals(sReportDate)){
	          		currentRecord.getField("SHEETTYPE").setValue("10");//年报
	          	}
	     	  //企业2007版利润及利润分配信息
	     	 setCol2Value("REVENUEOFSALES","5a1"); //营业收入                     	
	         setCol2Value("COSTOFSALES","440"); //营业成本                    	
	         setCol2Value("BUSINESSANDOTHERTAXES","504"); //营业税金及附加                
	         setCol2Value("SELLINGEXPENSES","527"); //销售费用                      
	         setCol2Value("M9174","507"); //管理费用                     	
	         setCol2Value("FINANCIALEXPENSE","508"); //财务费用508                     	
	         setCol2Value("IMPAIRMENTLOSSOFASSETS","b58"); //资产减值损失                  
	         setCol2Value("M9177","5b4"); //公允价值变动净收益            
	         setCol2Value("INVESTMENTINCOME","446"); //投资净收益                    
	         setCol2Value("M9179","5b5"); //对联营企业和合营企业的投资收益
	         setCol2ValueYELR("OPERATINGPROFITS","5a1","440","504","527","507","508","b58","5b4","446","5b5"); //营业利润      9170-9171-9172-9173-9174-9175-9176+9177+9178
	         
	         setCol2Value("NONOPERATINGINCOME","512"); //营业外收入                    
	         setCol2Value("NONOPERATINGEXPENSES","513"); //营业外支出                    
	         setCol2Value("NONCURRENTASSETS","5b6"); //非流动资产损失                
	         setCol2ValueLRZE("PROFITBEFORETAX","5a1","440","504","527","507","508","b58","5b4","446","5b5" ,"512","513"); //利润总额    9180+91819182
	         
	         setCol2Value("INCOMETAXEXPENSE","450"); //所得税费用516                    
	         setCol2ValueJLR("NETPROFIT","5a1","440","504","527","507","508","b58","5b4","446","5b5" ,"512","513","450"); //净利润               9184-9185         	
	         setCol2Value("BASICEARNINGSPERSHARE","657"); //基本每股收益                  
	         setCol2Value("DILUTEDEARNINGSPERSHARE","553"); //稀释每股收益  
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
        
    }
    
    //"501","-502","-504","-503","-507","-508","-653","654","510","512","-513" ,"-516"
    private void setCol2ValueJLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10, String rowSubject11, String rowSubject12, String rowSubject13) {
    	try {
    			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double a6 = getCol2Value(rowSubject6);
				double a7 = getCol2Value(rowSubject7);
				double a8 = getCol2Value(rowSubject8);
				double a9 = getCol2Value(rowSubject9);
				double a10 = getCol2Value(rowSubject10);
				double a11 = getCol2Value(rowSubject11);
				double a12 = getCol2Value(rowSubject12);
				double a13 = getCol2Value(rowSubject13);
				double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10+a11-a12-a13;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
  //"501","-502","-504","-503","-507","-508","-653","654","510","512","-513" ,"-516"
    private void setCol2ValueLRZE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10, String rowSubject11, String rowSubject12) {
    	try {
    			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double a6 = getCol2Value(rowSubject6);
				double a7 = getCol2Value(rowSubject7);
				double a8 = getCol2Value(rowSubject8);
				double a9 = getCol2Value(rowSubject9);
				double a10 = getCol2Value(rowSubject10);
				double a11 = getCol2Value(rowSubject11);
				double a12 = getCol2Value(rowSubject12);
				double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10+a11-a12;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    //9170-9171-9172-9173-9174-9175-9176+9177+9178
    private void setCol2ValueYELR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9,String rowSubject10) {
	    	try {
	    			if(currentRecord.getField(item) == null) return;
					double a1 = getCol2Value(rowSubject1);
					double a2 = getCol2Value(rowSubject2);
					double a3 = getCol2Value(rowSubject3);
					double a4 = getCol2Value(rowSubject4);
					double a5 = getCol2Value(rowSubject5);
					double a6 = getCol2Value(rowSubject6);
					double a7 = getCol2Value(rowSubject7);
					double a8 = getCol2Value(rowSubject8);
					double a9 = getCol2Value(rowSubject9);
					double a10 = getCol2Value(rowSubject10);
					double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
 
	
    
	private double getCol2Value(String rowSubject1) throws SQLException {
		ps = conn1.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");
        ps.setString(1, sReportNo);
        ps.setString(2, rowSubject1);
        ResultSet rs = ps.executeQuery();
        double col2Value = 0.0;
        if (rs.next()) {
            col2Value = rs.getDouble(1);
        }
        rs.close();
        ps.close();
        return col2Value;
	}

	
	
	private void setCol2Value(String item, String rowSubject) throws SQLException {
		if(currentRecord.getField(item) == null) return;
		double a1 = getCol2Value(rowSubject);
		currentRecord.getField(item).setValue(a1);
		
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


