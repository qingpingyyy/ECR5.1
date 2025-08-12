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

public class FinanceCfInReportProvider extends DefaultDataSourceProvider{

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
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;

        //事业单位收入支出信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0022' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        //ARE.getLog().info("----------sCustomerID ->"+sCustomerID);
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
       	 
	     	//事业单位收入支出
	          //currentRecord.getField("M9501").setValue(m.get("").toString()); //本期财政补助结转结余    没有   ？？？
	          currentRecord.getField("FINANCIALSUBSIDYREVENUE").setValue(m.get("559").toString()); //财政补助收入       559
	          currentRecord.getField("M9345").setValue(m.get("z679").toString()); //事业支出（财政补助支出）              财政补助支出
	          //currentRecord.getField("M9502").setValue(m.get("").toString()); //本期事业结转结余      9336-9350 事业结余    
	          //currentRecord.getField("UNDERTAKINGSCLASSREVENUE").setValue(m.get("").toString()); //事业类收入   ？？        
	          //currentRecord.getField("UNDERTAKINGSREVENUE").setValue(m.get("").toString()); //事业收入                
	          currentRecord.getField("SUPERIORSUBSIDYREVENUE").setValue(m.get("560").toString()); //上级补助收入    560         
	          currentRecord.getField("M9503").setValue(m.get("565").toString()); //附属单位上缴收入          	
	          currentRecord.getField("OTHERREVENUE").setValue(m.get("526").toString()); //其他收入               
	          //currentRecord.getField("DONATIONINCOME").setValue(m.get("").toString()); //（其他收入科目下）捐赠收入 ？？？
	          //currentRecord.getField("UNDERTAKINGSCLASSEXPENDITURE").setValue(m.get("").toString()); //事业类支出              	 
	          //currentRecord.getField("M9505").setValue(m.get("").toString()); //事业支出（非财政补助支出）      ？？？？？
	          currentRecord.getField("PAYMENTTOTHEHIGHERAUTHORITY").setValue(m.get("556").toString()); //上缴上级支出             	 
	          currentRecord.getField("M9508").setValue(m.get("557").toString()); //对附属单位补助支出        	
	          //currentRecord.getField("OTHEREXPENDITURE").setValue(m.get("").toString()); //其他支出        ？？？？       	 
	          //currentRecord.getField("CURRENTOPERATINGBALANCE").setValue(m.get("").toString()); //本期经营结余            ？？？	 
	          currentRecord.getField("OPERATINGREVENUE").setValue(m.get("564").toString()); //经营收入               
	          currentRecord.getField("OPERATINGEXPENDITURE").setValue(m.get("552").toString()); //经营支出               	 
	          //currentRecord.getField("M9506").setValue(m.get("").toString()); //弥补以前年度亏损后的经营结余} 以前年度经营亏损
	          //currentRecord.getField("M9507").setValue(m.get("").toString()); //本年非财政补助结转结余     ？？
	          //currentRecord.getField("NONFINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //非财政补助结转            ？？	
	          //currentRecord.getField("NONFINANCIALAIDBALANCETHISYEAR").setValue(m.get("").toString()); //本年非财政补助结余        	
	          //currentRecord.getField("ENTERPRISEINCOMETAXPAYABLE").setValue(m.get("").toString()); //应缴企业所得税         
	          //currentRecord.getField("SPECIALFUNDSTOEXTRACT").setValue(m.get("").toString()); //提取专用基金             	 
	          //currentRecord.getField("PUBLICFUNDTURNEDINTO").setValue(m.get("").toString()); //转入事业基金
	          
     	   
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


