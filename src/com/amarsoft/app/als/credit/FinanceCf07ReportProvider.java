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

public class FinanceCf07ReportProvider extends DefaultDataSourceProvider{

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
        
        //企业2007版现金流量信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0297' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	  
	     	//企业2007版现金流量信
	          currentRecord.getField("M9199").setValue(m.get("z100").toString()); //销售商品和提供劳务收到的现金                  
	          currentRecord.getField("TAXREFUNDS").setValue(m.get("z102").toString()); //收到的税费返还                             	 
	          currentRecord.getField("M9201").setValue(m.get("z103").toString()); //收到其他与经营活动有关的现金  
	          
	          b1 = new BigDecimal(m.get("z100").toString())
			  .add(new BigDecimal(m.get("z102").toString()))
			  .add(new BigDecimal(m.get("z103").toString()));
	          currentRecord.getField("M9202").setValue(b1.toString());//经营活动现金流入小计     9199+9200+9201  
	          
	          currentRecord.getField("CASHPAIDFORGOODSANDSERVICES").setValue(m.get("b95").toString()); //购买商品、接受劳务支付的现金                  
	          currentRecord.getField("M9204").setValue(m.get("a22").toString()); //支付给职工以及为职工支付的现金                
	          currentRecord.getField("PAYMENTSOFALLTYPESOFTAXES").setValue(m.get("z104").toString()); //支付的各项税费                             	 
	          currentRecord.getField("M9206").setValue(m.get("z105").toString()); //支付其他与经营活动有关的现金                  
	          
	          b1 = new BigDecimal(m.get("b95").toString()).add(new BigDecimal(m.get("a22").toString())).add(new BigDecimal(m.get("z104").toString())).add(new BigDecimal(m.get("z105").toString()));
	          currentRecord.getField("M9207").setValue(b1.toString());////经营活动现金流出小计
	          
	          b1 = new BigDecimal(m.get("z100").toString()).add(new BigDecimal(m.get("z102").toString())).add(new BigDecimal(m.get("z103").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("z104").toString())).subtract(new BigDecimal(m.get("z105").toString()));
	          currentRecord.getField("M9208").setValue(b1.toString());//经营活动产生的现金流量净额1      9202-9207
	          
	          currentRecord.getField("M9209").setValue(m.get("z106").toString()); //收回投资所收到的现金                          
	          currentRecord.getField("CASHRECEIVEDFROMONVESTMENTS").setValue(m.get("z107").toString()); //取得投资收益所收到的现金                      
	          currentRecord.getField("M9211").setValue(m.get("z108").toString()); //处置固定资产无形资产和其他长期资产所收回的现金
	          currentRecord.getField("M9212").setValue(m.get("z111").toString()); //处置子公司及其他营业单位收到的现金净额        
	          currentRecord.getField("M9213").setValue(m.get("a32").toString()); //收到其他与投资活动有关的现金                  
	          
	          b1 = new BigDecimal(m.get("z106").toString())
			  .add(new BigDecimal(m.get("z107").toString()))
			  .add(new BigDecimal(m.get("z108").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("a32").toString()));
	          currentRecord.getField("M9214").setValue(b1.toString());//投资活动现金流入小计 9209+9210+9211+9212+9213  
	          
	          currentRecord.getField("M9215").setValue(m.get("z109").toString()); //购建固定资产无形资产和其他长期资产所支付的现金
	          currentRecord.getField("CASHPAYMENTSFORINVESTMENTS").setValue(m.get("z110").toString()); //投资所支付的现金                           	 
	          currentRecord.getField("M9217").setValue(m.get("z111").toString()); //取得子公司及其他营业单位支付的现金净额        
	          currentRecord.getField("M9218").setValue(m.get("z112").toString()); //支付其他与投资活动有关的现金                  
	          
	          b1 = new BigDecimal(m.get("z109").toString())
			  .add(new BigDecimal(m.get("z110").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("z112").toString()));
	          currentRecord.getField("SUBTOTALOFCASHOUTFLOWS").setValue(b1.toString());//投资活动现金流出小计 9209+9210+9211+9212+9213  
	          
	          b1 = new BigDecimal(m.get("z106").toString()).add(new BigDecimal(m.get("z107").toString())).add(new BigDecimal(m.get("z108").toString())).add(new BigDecimal(m.get("z111").toString())).add(new BigDecimal(m.get("a32").toString())).subtract(new BigDecimal(m.get("z109").toString())).subtract(new BigDecimal(m.get("z110").toString())).subtract(new BigDecimal(m.get("z111").toString())).subtract(new BigDecimal(m.get("z112").toString()));
	          currentRecord.getField("M9220").setValue(b1.toString());//投资活动产生的现金流量净额  9214-9219
	          
	          currentRecord.getField("CASHRECEIVEDFROMINVESTORS").setValue(m.get("z113").toString()); //吸收投资收到的现金                          	 
	          currentRecord.getField("CASHFROMBORROWINGS").setValue(m.get("z114").toString()); //取得借款收到的现金                          	 
	          currentRecord.getField("M9223").setValue(m.get("a42").toString()); //收到其他与筹资活动有关的现金                  
	          
	          b1 = new BigDecimal(m.get("z113").toString()).add(new BigDecimal(m.get("z114").toString())).add( new BigDecimal(m.get("a42").toString()));
	          currentRecord.getField("M9224").setValue(b1.toString());//筹资活动现金流入小计        9221+9222+9223   
	          
	          currentRecord.getField("CASHREPAYMENTSFORDEBTS").setValue(m.get("z115").toString()); //偿还债务所支付的现金                        	 
	          currentRecord.getField("M9226").setValue(m.get("z116").toString()); //分配股利、利润或偿付利息所支付的现金          
	          currentRecord.getField("M9227").setValue(m.get("z117").toString()); //支付其他与筹资活动有关的现金                  
	          
	          b1 = new BigDecimal(m.get("z115").toString()).add(new BigDecimal(m.get("z116").toString())).add( new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9228").setValue(b1.toString()); //筹资活动现金流出小计     9225+9226+9227
	        
	          b1 = new BigDecimal(m.get("z113").toString()).add(new BigDecimal(m.get("z114").toString())).add(new BigDecimal(m.get("a42").toString())).subtract(new BigDecimal(m.get("z115").toString())).subtract(new BigDecimal(m.get("z116").toString())).subtract(new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9229").setValue(b1.toString());//筹集活动产生的现金流量净额       9224-9228
	          
	          //currentRecord.getField("M9230").setValue(m.get("").toString()); //汇率变动对现金及现金等价物的影响              
	          //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
	          //a1+a2+a3-(a4+a5+a6+a7) +a8+a9+a10+a11+a12-(a13+a14+a15+a16) +a17+a18+a19-(a20+a21+a22)  
	          
	          b1 = new BigDecimal(m.get("z100").toString())
			  .add(new BigDecimal(m.get("z102").toString()))
			  .add(new BigDecimal(m.get("z103").toString()))
			  .subtract(new BigDecimal(m.get("b95").toString()))
			  .subtract(new BigDecimal(m.get("a22").toString()))
			  .subtract(new BigDecimal(m.get("z104").toString()))
			  .subtract(new BigDecimal(m.get("z105").toString()))
			  .add(new BigDecimal(m.get("z106").toString()))
			  .add(new BigDecimal(m.get("z107").toString()))
			  .add(new BigDecimal(m.get("z108").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("a32").toString()))
			  .subtract(new BigDecimal(m.get("z109").toString()))
			  .subtract(new BigDecimal(m.get("z110").toString()))
			  .subtract(new BigDecimal(m.get("z111").toString()))
			  .subtract(new BigDecimal(m.get("z112").toString()))
			  .add(new BigDecimal(m.get("z113").toString()))
			  .add(new BigDecimal(m.get("z114").toString()))
			  .add(new BigDecimal(m.get("a42").toString()))
			  .subtract(new BigDecimal(m.get("z115").toString()))
			  .subtract(new BigDecimal(m.get("z116").toString()))
			  .subtract(new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9231").setValue(b1.toString());//现金及现金等价物净增加额     9208+9220+9229
	          
	          currentRecord.getField("M9232").setValue(m.get("b82").toString()); //期初现金及现金等价物余额  ""   
	          
	          //setCol2Value23H + b82
	          b1 = new BigDecimal(m.get("z100").toString()).add(new BigDecimal(m.get("z102").toString())).add(new BigDecimal(m.get("z103").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("z104").toString())).subtract(new BigDecimal(m.get("z105").toString())).add(new BigDecimal(m.get("z106").toString())).add(new BigDecimal(m.get("z107").toString())).add(new BigDecimal(m.get("z108").toString())).add(new BigDecimal(m.get("z111").toString())).add(new BigDecimal(m.get("a32").toString())).subtract(new BigDecimal(m.get("z109").toString())).subtract(new BigDecimal(m.get("z110").toString())).subtract(new BigDecimal(m.get("z111").toString())).subtract(new BigDecimal(m.get("z112").toString())).add(new BigDecimal(m.get("z113").toString())).add(new BigDecimal(m.get("z114").toString())).add(new BigDecimal(m.get("a42").toString())).subtract(new BigDecimal(m.get("z115").toString())).subtract(new BigDecimal(m.get("z116").toString())).subtract(new BigDecimal(m.get("z117").toString())).add(new BigDecimal(m.get("b82").toString()));
	          currentRecord.getField("M9233").setValue(b1.toString());//期末现金及现金等价物余额   9231+9232 
	          
	          currentRecord.getField("NETPROFIT").setValue(m.get("517").toString()); //净利润                                  	
	          currentRecord.getField("PROVISIONFORASSETIMPAIRMENT").setValue(m.get("z600").toString()); //资产减值准备                              	
	          currentRecord.getField("DEPRECIATIONOFFIXEDASSETS").setValue(m.get("z601").toString()); //固定资产折旧、油气资产折耗、生产性生物资产折旧
	          currentRecord.getField("M9237").setValue(m.get("b60").toString()); //无形资产摊销                             	
	          currentRecord.getField("M9238").setValue(m.get("b61").toString()); //长期待摊费用摊销                           	 
	          currentRecord.getField("DECREASEOFDEFFEREDEXPENSES").setValue(m.get("z602").toString()); //待摊费用减少                             	
	          currentRecord.getField("ADDITIONOFACCUEDEXPENSE").setValue(m.get("z603").toString()); //预提费用增加                             	
	          currentRecord.getField("M9241").setValue(m.get("z604").toString()); //处置固定资产无形资产和其他长期资产的损失      
	          currentRecord.getField("M9242").setValue(m.get("z605").toString()); //固定资产报废损失                            
	          currentRecord.getField("M9243").setValue(m.get("z606").toString()); //公允价值变动损失                            
	          currentRecord.getField("FINANCEEXPENSE").setValue(m.get("z607").toString()); //财务费用                                	
	          currentRecord.getField("LOSSESARSINGFROMINVESTMENT").setValue(m.get("z608").toString()); //投资损失                                	
	          currentRecord.getField("DEFERREDINCOMETAXASSETS").setValue(m.get("z609").toString()); //递延所得税资产减少                          
	          currentRecord.getField("M9247").setValue(m.get("z610").toString()); //递延所得税负债增加                          	 
	          currentRecord.getField("DECREASEININVENTORIES").setValue(m.get("z611").toString()); //存货的减少                               	
	          currentRecord.getField("M9250").setValue(m.get("z612").toString()); //经营性应收项目的减少                         	
	          currentRecord.getField("M9251").setValue(m.get("z613").toString()); //经营性应付项目的增加                        	 
	          currentRecord.getField("OTHERS").setValue(m.get("z614").toString()); //（净利润调节为经营活动现金流量科目下）其他    
	          
	          b1 = new BigDecimal(m.get("c99").toString()).add(new BigDecimal(m.get("b75").toString()));
	          currentRecord.getField("M9233").setValue(b1.toString()); //经营活动产生的现金流量净额2     
	          
	          currentRecord.getField("DEBTSTRANSFERTOCAPITAL").setValue(m.get("b76").toString()); //债务转为资本                              	
	          currentRecord.getField("ONEYEARDUECONVERTIBLEBONDS").setValue(m.get("b77").toString()); //一年内到期的可转换公司债券                    
	          currentRecord.getField("M9255").setValue(m.get("b78").toString()); //融资租入固定资产                             	
	          currentRecord.getField("NONCASHOTHERS").setValue(m.get("z614").toString()); //其他（不涉及现金收支的投资和筹资活动科目下）  
	          currentRecord.getField("CASHATTHEENDOFPERIOD").setValue(m.get("b79").toString()); //现金的期末余额                              
	          currentRecord.getField("M9258").setValue(m.get("z615").toString()); //现金的期初余额                               
	          currentRecord.getField("M9259").setValue(m.get("z616").toString()); //现金等价物的期末余额                         	
	          currentRecord.getField("M9260").setValue(m.get("z617").toString()); //现金等价物的期初余额                         	
	          currentRecord.getField("M9261").setValue(m.get("z618").toString()); //现金及现金等价物净增加额
        }
	} catch (SQLException e) {
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


