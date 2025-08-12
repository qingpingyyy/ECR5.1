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

public class FinanceCfInReportProvider extends DefaultDataSourceProvider{

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

        //事业单位收入支出信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0022' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        ARE.getLog().info("----------sCustomerID ->"+sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
	     	  ARE.getLog().info("==============="+sReportNo);
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
	     	  //事业单位收入支出信息
	     	//事业单位收入支出
	          setCol2Value("M9501",""); //本期财政补助结转结余    没有   ？？？
	          setCol2Value("FINANCIALSUBSIDYREVENUE","559"); //财政补助收入       559
	          setCol2Value("M9345","z679"); //事业支出（财政补助支出）              财政补助支出
	          setCol2Value("M9502",""); //本期事业结转结余      9336-9350 事业结余    
	          setCol2Value("UNDERTAKINGSCLASSREVENUE",""); //事业类收入   ？？        
	          setCol2Value("UNDERTAKINGSREVENUE",""); //事业收入                
	          setCol2Value("SUPERIORSUBSIDYREVENUE","560"); //上级补助收入    560         
	          setCol2Value("M9503","565"); //附属单位上缴收入          	
	          setCol2Value("OTHERREVENUE","526"); //其他收入               
	          setCol2Value("DONATIONINCOME",""); //（其他收入科目下）捐赠收入 ？？？
	          setCol2Value("UNDERTAKINGSCLASSEXPENDITURE",""); //事业类支出              	 
	          setCol2Value("M9505",""); //事业支出（非财政补助支出）      ？？？？？
	          setCol2Value("PAYMENTTOTHEHIGHERAUTHORITY","556"); //上缴上级支出             	 
	          setCol2Value("M9508","557"); //对附属单位补助支出        	
	          setCol2Value("OTHEREXPENDITURE",""); //其他支出        ？？？？       	 
	          setCol2Value("CURRENTOPERATINGBALANCE",""); //本期经营结余            ？？？	 
	          setCol2Value("OPERATINGREVENUE","564"); //经营收入               
	          setCol2Value("OPERATINGEXPENDITURE","552"); //经营支出               	 
	          setCol2Value("M9506",""); //弥补以前年度亏损后的经营结余} 以前年度经营亏损
	          setCol2Value("M9507",""); //本年非财政补助结转结余     ？？
	          setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //非财政补助结转            ？？	
	          setCol2Value("NONFINANCIALAIDBALANCETHISYEAR",""); //本年非财政补助结余        	
	          setCol2Value("ENTERPRISEINCOMETAXPAYABLE",""); //应缴企业所得税         
	          setCol2Value("SPECIALFUNDSTOEXTRACT",""); //提取专用基金             	 
	          setCol2Value("PUBLICFUNDTURNEDINTO",""); //转入事业基金
	          
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
        //企业2002版资产负债表信息
        //setCol2Value("MainRevenuefRevenue","501");
        //setCol2Value("MainOperatingCost","502");
        
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
                logger.debug("关闭conn1成功");
            } catch (SQLException e) {
                logger.debug("关闭conn1出错", e);
            }
        }
        super.close();
    }

}


