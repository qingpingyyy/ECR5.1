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

public class FinancePs02ReportProvider extends DefaultDataSourceProvider{

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

		        //2002利润及利润分配表
		        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0012' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
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
		        	 
		        	 
			     	  //企业2002版利润及利润分配信息
					  currentRecord.getField("MAINREVENUEFREVENUE").setValue(m.get("603").toString()); // 主营业务收入501} 9675       
			          //currentRecord.getField("M9677").setValue(m.get("").toString()); // 主营业务收入出口产品销售收入""}   
			          // currentRecord.getField("M9679").setValue(m.get("").toString()); // 主营业务收入进口产品销售收入""}   
			          //currentRecord.getField("DISCOUNTANDALLOWANCE").setValue(m.get("").toString()); // 折扣与拆让""}  9681                  
			          currentRecord.getField("M9683").setValue(m.get("603").toString()); // 主营业务收入净额9675-9681}     9683       
			          
			          currentRecord.getField("MAINOPERATINGCOST").setValue(m.get("440").toString()); // 主营业务成本440}        9685          
			          //currentRecord.getField("SALESINCOMEOFEXPORTPRODUCTS").setValue(m.get("").toString()); // 主营业务成本出口产品销售成本""}  
			          //currentRecord.getField("M9689").setValue(m.get("").toString()); // 主营业务税金及附加}     9689     
			          //currentRecord.getField("OPERATIONEXPENSE").setValue(m.get("").toString()); // 经营费用}         9693            
			          //currentRecord.getField("OTHERSOPERATINGCOST").setValue(m.get("").toString()); // 其他（业务成本""）}     9691        
			          //currentRecord.getField("DEFERREDINCOME").setValue(m.get("").toString()); // 递延收益""}       9695              
			          //currentRecord.getField("INCOMEFROMSALESAGENCY").setValue(m.get("").toString()); // 代购代销收入""}     9697            
			          //currentRecord.getField("OTHEROPERATINGINCOME").setValue(m.get("").toString()); // 其他（收入）""}     9699            

			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString()));
			          currentRecord.getField("PRINCIPLEBUSINESSPROFIT").setValue(b1.toString());// 主营业务利润  9683-9685-9689-9693-9691+9695+9697+9699

			          currentRecord.getField("OTHERBUSINESSPROFIT").setValue(m.get("526").toString()); // 其他业务利润526              
			          //currentRecord.getField("SELLINGEXPENSES").setValue(m.get("").toString()); // 营业费用""}     9705                
			          currentRecord.getField("M9707").setValue(m.get("507").toString()); // 管理费用507}  9707                   
			          currentRecord.getField("FINANCIALEXPENSES").setValue(m.get("508").toString()); // 财务费用508}   9709                 
			          //currentRecord.getField("OTHERSEXPENSES").setValue(m.get("").toString()); // 其他（费用）}   9907          
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString()));
			          currentRecord.getField("OPERATINGPROFITS").setValue(b1.toString());//营业利润    9701+9703-9705-9707-9709-9907 
			          
			          currentRecord.getField("INVESTMENTINCOME").setValue(m.get("466").toString()); // 投资收益510}    9713
			          currentRecord.getField("FUTURESINCOME").setValue(m.get("532").toString()); // 期货收益532}             9715        
			          currentRecord.getField("ALLOWANCEINCOME").setValue(m.get("511").toString()); // 补贴收入511}     9717                
			          currentRecord.getField("M9719").setValue(m.get("447").toString()); // 补贴收入补贴前亏损的企业补贴收入""}  9719
			          currentRecord.getField("NONOPERATINGINCOME").setValue(m.get("512").toString()); // 营业外收入512}                      	
			          currentRecord.getField("M9723").setValue(m.get("448").toString()); // 营业外收入处置固定资产净收益""}      9721
			          //currentRecord.getField("INCOMEFROMNONCURRENCYTRADE").setValue(m.get("").toString()); // -营业外收入非货币性交易收益""}       
			          //currentRecord.getField("M9727").setValue(m.get("").toString()); // 营业外收入出售无形资产收益""}        
			          //currentRecord.getField("INCOMEFROMPENALTY").setValue(m.get("").toString()); // 营业外收入罚款净收入""}              
			          currentRecord.getField("OTHERSINCOME").setValue(m.get("526").toString()); // 其他（利润）514}                     	
			          //currentRecord.getField("M9731").setValue(m.get("").toString()); // 其他用以前年度含量工资节余弥补利润""}
			          currentRecord.getField("NONOPERATINGEXPENSES").setValue(m.get("513").toString()); // 营业外支出513}                     	
			          //currentRecord.getField("M9735").setValue(m.get("").toString()); // 营业外支出处置固定资产净损失""}    
			          //currentRecord.getField("M9737").setValue(m.get("").toString()); // 营业外支出债务重组损失}         
			          //currentRecord.getField("LOSSOFLAWSUITS").setValue(m.get("").toString()); // 营业外支出罚款支出}             
			          //currentRecord.getField("PAYMENTFORDONATION").setValue(m.get("").toString()); // 营业外支出捐赠支出}             
			          currentRecord.getField("OTHERPAYMENTS").setValue(m.get("449").toString()); // 其他支出}                       
			          //currentRecord.getField("BALANCEOFCONTENTSALARY").setValue(m.get("").toString()); // 其他支出结转的含量工资包干节余}  
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString()));
			          currentRecord.getField("TOTALPROFIT").setValue(b1.toString());// 利润总额   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745
			          
			          currentRecord.getField("INCOMETAX").setValue(m.get("450").toString()); // 所得税                      
			          //currentRecord.getField("IMPARIMENTLOSS").setValue(m.get("").toString()); // 少数股东损益523}                   
			          //currentRecord.getField("UNREALIZEDINVESTMENTLOSSES").setValue(m.get("").toString()); // 未确认的投资损失""}               
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString()));
			          currentRecord.getField("NETPROFIT").setValue(b1.toString());//净利润
			          
			          
			          currentRecord.getField("M9757").setValue(m.get("589").toString()); // 年初未分配利润""}                 
			          currentRecord.getField("COMPENSATIONOFSURPLUSRESERVE").setValue(m.get("590").toString()); // 盈余公积补亏""}                   
			          currentRecord.getField("OTHERADJUSTMENTFACTORS").setValue(m.get("591").toString()); // 其他调整因素""}                   
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString()));
			          currentRecord.getField("PROFITAVAILABLEFORDISTRIBUTION").setValue(b1.toString());// 可供分配的利润}     9755+9757+9759+9761
			          
			          //currentRecord.getField("PROFITRESERVEDFORASINGLEITEM").setValue(m.get("").toString()); // 单项留用的利润""}                 
			          currentRecord.getField("SUPPLEMENTARYCURRENTCAPITAL").setValue(m.get("593").toString()); // 补充流动资本""}                   
			          currentRecord.getField("M9769").setValue(m.get("594").toString()); // 提取法定盈余公积""}               
			          currentRecord.getField("M9771").setValue(m.get("595").toString()); // 提取法定公益金""}                 
			          currentRecord.getField("M9773").setValue(m.get("596").toString()); // 提取职工奖励及福利基金""}         
			          currentRecord.getField("APPROPRIATIONOFRESERVEFUND").setValue(m.get("597").toString()); // 提取储备基金""}                   
			          currentRecord.getField("M9777").setValue(m.get("598").toString()); // 提取企业发展基金""}               
			          currentRecord.getField("M9779").setValue(m.get("599").toString()); // 利润归还投资""}                   
			          currentRecord.getField("OTHERS4").setValue(m.get("441").toString()); // 其他（可供分配的利润科目下）""}   
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString())).subtract(new BigDecimal(m.get("593").toString())).subtract(new BigDecimal(m.get("594").toString())).subtract(new BigDecimal(m.get("595").toString())).subtract(new BigDecimal(m.get("596").toString())).subtract(new BigDecimal(m.get("597").toString())).subtract(new BigDecimal(m.get("598").toString())).subtract(new BigDecimal(m.get("599").toString())).subtract(new BigDecimal(m.get("441").toString()));
			          currentRecord.getField("M9781").setValue(b1.toString());// 可供投资者分配的利润} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
			          
			          
			          currentRecord.getField("PREFERREDSTOCKDIVIDENDSPAYABLE").setValue(m.get("585").toString()); // 应付优先股股利""}                 
			          currentRecord.getField("M9785").setValue(m.get("586").toString()); // 提取任意盈余公积""}               
			          currentRecord.getField("PAYABLEDIVIDENDSOFCOMMONSTOCK").setValue(m.get("572").toString()); // 应付普通股股利""}                 
			          currentRecord.getField("M9789").setValue(m.get("452").toString()); // 转作资本的普通股股利""}           
			          currentRecord.getField("OTHERS5").setValue(m.get("453").toString()); // 其他（可供投资者分配的利润科目下""}   

			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString())).subtract(new BigDecimal(m.get("593").toString())).subtract(new BigDecimal(m.get("594").toString())).subtract(new BigDecimal(m.get("595").toString())).subtract(new BigDecimal(m.get("596").toString())).subtract(new BigDecimal(m.get("597").toString())).subtract(new BigDecimal(m.get("598").toString())).subtract(new BigDecimal(m.get("599").toString())).subtract(new BigDecimal(m.get("441").toString())).subtract(new BigDecimal(m.get("585").toString())).subtract(new BigDecimal(m.get("586").toString())).subtract(new BigDecimal(m.get("572").toString())).subtract(new BigDecimal(m.get("452").toString())).subtract(new BigDecimal(m.get("453").toString()));
			          currentRecord.getField("UNAPPROPRIATEDPROFIT").setValue(b1.toString());
			          
			          //currentRecord.getField("LOSSCOMPENSATEDBEFORETHETAX").setValue(m.get("").toString()); // 未分配利润应由以后年度税前利润弥补的亏损""} 
				          
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
                logger.debug("关闭connection成功");
            } catch (SQLException e) {
                logger.debug("关闭connection出错", e);
            }
        }
        super.close();
    }

}


