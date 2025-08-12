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

public class FinanceBsInReportProvider extends DefaultDataSourceProvider{

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
    
    public void fillRecord(){
        try {
			super.fillRecord();
			 sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
		        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;

		        //事业单位资产负债信息
		        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo 
										from report_record rr ,report_data rd 
										where rr.reportno =rd.reportno  
										and rr.modelno ='0021' 
										and rr.reportscope <>'03' 
										and rr.objectno =? 
										and rr.REPORTDATE  ='"+sReportDate+"'";
		        conn1 = ARE.getDBConnection(database);
		        sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
		    	ps=conn1.prepareStatement(sSql);
		        ps1=conn1.prepareStatement(sQueryReportNo);
		        ps1.setString(1, sCustomerID);
		        rs1= this.ps1.executeQuery();
		        while(rs1.next()){
		        	 sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo =""; 
		       	 	 Map m = new HashMap();
		       	 	 ps.clearParameters();
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
		       	 
			     	  
						//事业单位资产负债
						//事业单位资产负债
			          //currentRecord.getField("CURRENCYFUNDS").setValue(m.get("").toString()); //货币资金     ？？
			          //currentRecord.getField("SHORTTERMINVESTMENTS").setValue(m.get("").toString()); //短期投资    ??    
			          //currentRecord.getField("M9408").setValue(m.get("").toString()); //财政应返还额度  ？？
			          currentRecord.getField("NOTESRECEIVABLE").setValue(m.get("103").toString()); //应收票据 103       
			          currentRecord.getField("ACCOUNTSRECEIVABLE").setValue(m.get("104").toString()); //应收账款19f        
			          currentRecord.getField("PREPAYMENTS").setValue(m.get("z631").toString()); //预付账款     19n   
			          currentRecord.getField("OTHERRECEIVABLES").setValue(m.get("108").toString()); //其他应收款   19n   
			          //currentRecord.getField("INVENTORIES").setValue(m.get("").toString()); //存货          	
			          //currentRecord.getField("OTHERCURRENTASSETS").setValue(m.get("").toString()); //其他流动资产    ???
			          //currentRecord.getField("TOTALCURRENTASSETS").setValue(m.get("").toString()); //流动资产合计   ??? 
			          //currentRecord.getField("LONGTERMINVESTMENT").setValue(m.get("").toString()); //长期投资        
			          currentRecord.getField("FIXEDASSETS").setValue(m.get("703").toString()); //固定资产        
			          //currentRecord.getField("M9407").setValue(m.get("").toString()); //固定资产原价    ??
			          //currentRecord.getField("M9401").setValue(m.get("").toString()); //累计折旧      ??  
			          //currentRecord.getField("CONSTRUCTIONINPROCESS").setValue(m.get("").toString()); //在建工程      ??  
			          currentRecord.getField("INTANGIBLEASSETS").setValue(m.get("123").toString()); //无形资产      123+120+19g  
			          //currentRecord.getField("M9402").setValue(m.get("").toString()); //无形资产原价   ?? 
			          //currentRecord.getField("ACCUMULATEDAMORTIZATION").setValue(m.get("").toString()); //累计摊销       	
			          //currentRecord.getField("M9403").setValue(m.get("").toString()); //待处置资产损溢  ??
			          //currentRecord.getField("TOTALNONCURRENTASSETS").setValue(m.get("").toString()); //非流动资产合计  ??
			          //currentRecord.getField("TOTALASSETS").setValue(m.get("").toString()); //资产总计       	
			          //currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("").toString()); //短期借款       ??	
			          //currentRecord.getField("TAXPAYABLE").setValue(m.get("").toString()); //应缴税费       ??	
			          //currentRecord.getField("TREASURYPAYABLE").setValue(m.get("").toString()); //应缴国库款      ??
			          //currentRecord.getField("M9404").setValue(m.get("").toString()); //应缴财政专户款  ??
			          //currentRecord.getField("EMPLOYEEBENEFITSPAYABLE").setValue(m.get("").toString()); //应付职工薪酬    ??
			          
			          currentRecord.getField("NOTESPAYABLE").setValue(m.get("202").toString()); //应付票据       	
			          currentRecord.getField("ACCOUNTSPAYABLE").setValue(m.get("203").toString()); //应付账款       	
			          currentRecord.getField("RECEIPTSINADVANCE").setValue(m.get("94705").toString()); //预收账款       	
			          currentRecord.getField("OTHERPAYABLES").setValue(m.get("209").toString()); //其他应付款      
			          //currentRecord.getField("OTHERCURRENTLIABILITIES").setValue(m.get("").toString()); //其他流动负债    ??
			          //currentRecord.getField("TOTALCURRENTLIABILITIES").setValue(m.get("").toString()); //流动负债合计    ??
			          //currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("").toString()); //长期借款       	??
			          //currentRecord.getField("LONGTERMPAYABLES").setValue(m.get("").toString()); //长期应付款      ??
			          //currentRecord.getField("M9405").setValue(m.get("").toString()); //非流动负债合计  ??
			          
			          b1 = new BigDecimal(m.get("202").toString())
					  .add(new BigDecimal(m.get("203").toString()))
					  .add(new BigDecimal(m.get("94705").toString()))
					  .add(new BigDecimal(m.get("209").toString()));
			          currentRecord.getField("TOTALLIABILITIES").setValue(b1.toString());//负债合计     9295+9296+9297+9298+9299+9300+9301+9302
			          
			          currentRecord.getField("ENTERPRISEFUND").setValue(m.get("319").toString()); //事业基金       	
			          //currentRecord.getField("NONCURRENTASSETSFUND").setValue(m.get("").toString()); //非流动资产基金  ??
			          //currentRecord.getField("SPECIALPURPOSEFUNDS").setValue(m.get("").toString()); //专用基金       	??
			          //currentRecord.getField("FINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //财政补助结转    
			          //currentRecord.getField("FINANCIALAIDBALANCE").setValue(m.get("").toString()); //财政补助结余    
			          //currentRecord.getField("NONFINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //非财政补助结转  
			          //currentRecord.getField("NONFINANCIALAIDBALANCE").setValue(m.get("").toString()); //非财政补助结余  
			          currentRecord.getField("UNDERTAKINGSBALANCE").setValue(m.get("322").toString()); //事业结余        
			          currentRecord.getField("OPERATINGBALANCE").setValue(m.get("19x").toString()); //经营结余       19x+277	
			          b1 = new BigDecimal(m.get("322").toString())
					  .add(new BigDecimal(m.get("19x").toString()));
			          currentRecord.getField("TOTALNETASSETS").setValue(b1.toString());//净资产合计    9304+9306+9307+9308+9309+9310 
			          //currentRecord.getField("M9406").setValue(m.get("").toString()); //负债和净资产总计
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn1 != null) conn1.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			} 
		}
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


