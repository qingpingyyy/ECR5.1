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

public class FinanceBsInReportProvider extends DefaultDataSourceProvider{

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

        //事业单位资产负债信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0021' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	  
	     	 //事业单位资产负债
	          setCol2Value("CURRENCYFUNDS",""); //货币资金     ？？
	          setCol2Value("SHORTTERMINVESTMENTS",""); //短期投资    ??    
	          setCol2Value("M9408",""); //财政应返还额度  ？？
	          setCol2Value("NOTESRECEIVABLE","103"); //应收票据 103       
	          setCol2Value("ACCOUNTSRECEIVABLE","104"); //应收账款19f        
	          setCol2Value("PREPAYMENTS","z631"); //预付账款     19n   
	          setCol2Value("OTHERRECEIVABLES","108"); //其他应收款   19n   
	          setCol2Value("INVENTORIES",""); //存货          	
	          setCol2Value("OTHERCURRENTASSETS",""); //其他流动资产    ???
	          setCol2Value("TOTALCURRENTASSETS",""); //流动资产合计   ??? 
	          setCol2Value("LONGTERMINVESTMENT",""); //长期投资        
	          setCol2Value("FIXEDASSETS","703"); //固定资产        
	          setCol2Value("M9407",""); //固定资产原价    ??
	          setCol2Value("M9401",""); //累计折旧      ??  
	          setCol2Value("CONSTRUCTIONINPROCESS",""); //在建工程      ??  
	          setCol2Value("INTANGIBLEASSETS","123"); //无形资产      123+120+19g  
	          setCol2Value("M9402",""); //无形资产原价   ?? 
	          setCol2Value("ACCUMULATEDAMORTIZATION",""); //累计摊销       	
	          setCol2Value("M9403",""); //待处置资产损溢  ??
	          setCol2Value("TOTALNONCURRENTASSETS",""); //非流动资产合计  ??
	          setCol2Value("TOTALASSETS",""); //资产总计       	
	          setCol2Value("SHORTTERMBORROWINGS",""); //短期借款       ??	
	          setCol2Value("TAXPAYABLE",""); //应缴税费       ??	
	          setCol2Value("TREASURYPAYABLE",""); //应缴国库款      ??
	          setCol2Value("M9404",""); //应缴财政专户款  ??
	          setCol2Value("EMPLOYEEBENEFITSPAYABLE",""); //应付职工薪酬    ??
	          
	          setCol2Value("NOTESPAYABLE","202"); //应付票据       	
	          setCol2Value("ACCOUNTSPAYABLE","203"); //应付账款       	
	          setCol2Value("RECEIPTSINADVANCE","94750"); //预收账款       	
	          setCol2Value("OTHERPAYABLES","209"); //其他应付款      
	          setCol2Value("OTHERCURRENTLIABILITIES",""); //其他流动负债    ??
	          setCol2Value("TOTALCURRENTLIABILITIES",""); //流动负债合计    ??
	          setCol2Value("LONGTERMBORROWINGS",""); //长期借款       	??
	          setCol2Value("LONGTERMPAYABLES",""); //长期应付款      ??
	          setCol2Value("M9405",""); //非流动负债合计  ??
	          setCol2Value4Jia("TOTALLIABILITIES","202","203","94750","209"); //负债合计     9295+9296+9297+9298+9299+9300+9301+9302
	          
	          setCol2Value("ENTERPRISEFUND","319"); //事业基金       	
	          setCol2Value("NONCURRENTASSETSFUND",""); //非流动资产基金  ??
	          setCol2Value("SPECIALPURPOSEFUNDS",""); //专用基金       	??
	          setCol2Value("FINANCIALAIDCARRIEDOVER",""); //财政补助结转    
	          setCol2Value("FINANCIALAIDBALANCE",""); //财政补助结余    
	          setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //非财政补助结转  
	          setCol2Value("NONFINANCIALAIDBALANCE",""); //非财政补助结余  
	          setCol2Value("UNDERTAKINGSBALANCE","322"); //事业结余        
	          setCol2Value("OPERATINGBALANCE","19x"); //经营结余       19x+277	
	          setCol2Value2("TOTALNETASSETS","322","19x"); //净资产合计    9304+9306+9307+9308+9309+9310  
	          setCol2Value("M9406",""); //负债和净资产总计
	          
	          
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }
    
    
	private void setCol2Value4Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) {
    	try {
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double col2Value=a1+a2+a3+a4;
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

	
	private void setCol2Value2(String item, String rowSubject1, String rowSubject2) throws SQLException {
		if(currentRecord.getField(item) == null) return;
		double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double col2Value=a1+a2;
    	currentRecord.getField(item).setValue(col2Value);
		
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
                logger.debug("关闭conn1成功");
            } catch (SQLException e) {
                logger.debug("关闭conn1出错", e);
            }
        }
        super.close();
    }

}


