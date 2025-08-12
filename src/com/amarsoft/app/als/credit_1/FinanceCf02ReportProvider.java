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

public class FinanceCf02ReportProvider extends DefaultDataSourceProvider{

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

        //2002版现金流量信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0018' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
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
	     	  
	     	  
	          currentRecord.getField("M9799").setValue(m.containsKey("b94")?m.get("b94"):"0.00"); //收到的其他与经营活动有关的现金 
	          currentRecord.getField("M9823").setValue(m.containsKey("b94")?m.get("b94").toString():"0.00"); //经营活动现金流入小计 9795+9797+9799} 9823
	          
	          
	          currentRecord.getField("CASHPAIDFORGOODSANDSERVICES").setValue(m.containsKey("b95")?m.get("b95"):"0.00"); //购买商品、接受劳务支付的现金a02}   
	          currentRecord.getField("M9805").setValue(m.get("a22").toString()); //支付给职工以及为职工支付的现金a22}  
	         
	          b1 = new BigDecimal(m.get("b112").toString()).add(new BigDecimal(m.get("b114").toString()).add(new BigDecimal(m.get("a49").toString())));
	          currentRecord.getField("PAYMENTSOFALLTYPESOFTAXES").setValue(b1.toString());//支付的各项税费a23+a24+a25}   
	          
	          b1 = new BigDecimal(m.get("a19").toString()).add(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9809").setValue(b1.toString()); //支付的其他与经营活动有关的现金a03+a26} 
	          
	          //double col2Value=a1-a2-a3+a4+(a5-a6)+a7; b95-a22- b112+ b114+ a49- a19+ a26
	          b1 = new BigDecimal(m.get("b95").toString()).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).add(new BigDecimal(m.get("b114").toString())).add(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).add(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9831").setValue(b1.toString());
	          //double col2Value=a1-(a2+a3+a4+a5+a6+a7+a8);
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9813").setValue(b1.toString());//经营活动产生的现金流量净额 
	          
	          currentRecord.getField("M9815").setValue(m.get("a28")); //收回投资所收到的现金a28
	          currentRecord.getField("CASHRECEIVEDFROMONVESTMENTS").setValue(m.get("b100")); //取得投资收益所收到的现金a30}      
	          currentRecord.getField("M9819").setValue(m.get("b101")); //处置固定资产无形资产和其他长期资产所收回的现金净额a31}
	          //b119+b59+c98
	          //setCol2Value3Jia("M9821").setValue(m.get("b119").setValue(m.get("b59").setValue(m.get("c98")); //收到的其他与投资活动有关的现金a29+a32} 
	          b1 = new BigDecimal(m.get("b119").toString()).add( new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString()));
	          currentRecord.getField("M9821").setValue(b1.toString());
	          
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString()));
	          currentRecord.getField("M9917").setValue(b1.toString());//投资活动现金流入小计
	          
	          b1 = new BigDecimal(m.get("b67").toString()).add(new BigDecimal(m.get("b120").toString()));
	          currentRecord.getField("CASHPAYMENTSFORINVESTMENTS").setValue(b1.toString()); //投资所支付的现金}     a35+a36 
	          
	          currentRecord.getField("M9829").setValue(m.get("a37")); //支付的其他与投资活动有关的现金a37}  
	          
	          b1 = new BigDecimal(m.get("b67").toString()).add(new BigDecimal(m.get("b120").toString())).add(new BigDecimal(m.get("a37").toString()));//投资活动现金流出小计
	          currentRecord.getField("M9919").setValue(b1.toString()); 
	          
	          //double col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9);9917
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9833").setValue(b1.toString()); //投资活动产生的现金流量净额
	          
	          currentRecord.getField("CASHRECEIVEDFROMINVESTORS").setValue(m.get("b109")); //吸收投资所收到的现金a39}          
	          currentRecord.getField("CASHFROMBORROWINGS").setValue(m.get("a41")); //借款所收到的现金a41}       
	          
        	  b1 = new BigDecimal(m.get("b71").toString()).add(new BigDecimal(m.get("b72").toString()));
	          currentRecord.getField("M9839").setValue(b1.toString()); //收到的其他与筹资活动有关的现金
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString()));
	          currentRecord.getField("M9921").setValue(b1.toString()); ////筹资活动现金流入小计
	          
	          currentRecord.getField("CASHREPAYMENTSFORDEBTS").setValue(m.get("a44")); //偿还债务所支付的现金a44}        
	          currentRecord.getField("M9845").setValue(m.get("b112")); //分配股利、利润或偿付利息所支付的现金a46}           
	          
	          b1 = new BigDecimal(m.get("b76").toString()).add(new BigDecimal(m.get("b77").toString())).add(new BigDecimal(m.get("b78").toString())).add(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9847").setValue(b1.toString()); //支付的其他与筹资活动有关的现金
	        
	          b1 = new BigDecimal(m.get("a44").toString()).add(new BigDecimal(m.get("b112").toString())).add(new BigDecimal(m.get("b76").toString())).add(new BigDecimal(m.get("b77").toString())).add(new BigDecimal(m.get("b78").toString())).add(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9923").setValue(b1.toString()); //筹资活动现金流出小计
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9851").setValue(b1.toString()); //筹集活动产生的现金流量净额
	          
	          currentRecord.getField("M9853").setValue(m.get("a51")); //汇率变动对现金的影响a51
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString())).add(new BigDecimal(m.get("b94").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9855").setValue(b1.toString()); //现金及现金等价物净增加额1}   9813+9833+9851+9853
	          
	          //currentRecord.getField("NETPROFIT").setValue(m.get("")); //净利润z85}              
	          //currentRecord.getField("PROVISIONFORASSETS").setValue(m.get("")); //计提的资产减值准备""}    
	          //currentRecord.getField("DEPRECIATIONOFFIXEDASSETS").setValue(m.get("")); //固定资产拆旧z87}           
	          //currentRecord.getField("M9863").setValue(m.get("")); //无形资产摊销z88}          
	          //currentRecord.getField("M9865").setValue(m.get("")); //长期待摊费用摊销z78}      
	          //currentRecord.getField("DECREASEOFDEFFEREDEXPENSES").setValue(m.get("")); //待摊费用减少""}           
	          //currentRecord.getField("ADDITIONOFACCUEDEXPENSE").setValue(m.get("")); //预提费用增加""}           
	          //currentRecord.getField("M9871").setValue(m.get("")); //处置固定资产无形资产和其他长期资产的损失z89}         	
	          //currentRecord.getField("M9873").setValue(m.get("")); //固定资产报废损失z90}      
	          //currentRecord.getField("FINANCEEXPENSE").setValue(m.get("")); //财务费用z91}             
	          //currentRecord.getField("LOSSESARSINGFROMINVESTMENT").setValue(m.get("")); //投资损失}z92              
	          //currentRecord.getField("DEFERREDTAXCREDIT").setValue(m.get("")); //递延税款贷项z93}           
	          //currentRecord.getField("DECREASEININVENTORIES").setValue(m.get("")); //存货的减少z94}            
	          //currentRecord.getField("M9883").setValue(m.get("")); //经营性应收项目的减少z95}         
	          //currentRecord.getField("M9885").setValue(m.get("")); //经营性应付项目的增加z96} 
	          
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("OTHERS1").setValue(b1.toString());//
	          
	          //currentRecord.getField("M1813").setValue(m.get("")); //经营活动产生的现金流量净额1z99}    
	          //currentRecord.getField("DEBTSTRANSFERTOCAPITAL").setValue(m.get("")); //债务转为资本""}          
	          //currentRecord.getField("ONEYEARDUECONVERTIBLEBONDS").setValue(m.get("")); //一年内到期的可转换公司债券""}               		 
	          //currentRecord.getField("M9895").setValue(m.get("")); //融资租入固定资产""}      
	          //currentRecord.getField("OTHERS2").setValue(m.get("")); //其他（不涉及现金收支的投资和筹资活动科目下""}       
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString()));
	          currentRecord.getField("CASHATTHEENDOFPERIOD").setValue(b1.toString());//现金的期末余额 
	          
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9813").setValue(b1.toString());///经营活动产生的现金流量净额 
	          
	          //col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9)
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9833").setValue(b1.toString()); //投资活动产生的现金流量净额
	          
	          
	          //a1-(a2+a3+a4+a5+a6+a7+a8) + a9+a10+a11+a12+a13+a14-(a15+a16+a17)
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9903").setValue(b1.toString()); //现金等价物的期末余额
	         
	          //currentRecord.getField("M9905").setValue(m.get("")); //现金等价物的期初余额z104}   
	          
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString())).add(new BigDecimal(m.get("b94").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));	        		  
	          currentRecord.getField("M1855").setValue(b1.toString()); //现金及现金等价物净增加额2
	         
	          m.clear();
        
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
                logger.debug("关闭conn1成功");
            } catch (SQLException e) {
                logger.debug("关闭conn1出错", e);
            }
        }
        super.close();
    }

}


