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
import com.amarsoft.are.util.StringFunction;

public class FinanceBs02ReportProvider extends DefaultDataSourceProvider{

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
        
        //2002资产负债表
        String sQueryReportNo = "select distinct rr.ReportNo as ReportNo  from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0011' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' " ;
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1,sCustomerID);
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
	     	  
			  currentRecord.getField("CURRENCYFUNDS").setValue(m.get("101").toString());//货币资金101
	          currentRecord.getField("SHORTTERMINVESTMENTS").setValue(m.get("102").toString());//短期投资
	          currentRecord.getField("NOTESRECEIVBLE").setValue(m.get("103").toString());//应收票据 103
	          currentRecord.getField("DIVIDENDSRECEIVBLE").setValue(m.get("134").toString());//应收股利 134
	          currentRecord.getField("INTERESTRECEIVBLE").setValue(m.get("19c").toString());//应收利息19c
	          currentRecord.getField("ACCOUNTSRECEIVBLE").setValue(m.get("104").toString());//应收账款104
	          currentRecord.getField("OTHERRECEIVBLES").setValue(m.get("108").toString());//其他应收款 108
	          currentRecord.getField("PREPYMENTS").setValue(m.get("19n").toString());//#预付账款19n
	          currentRecord.getField("FUTUREGURNTEE").setValue(m.get("129").toString());//期货保证金""
	          currentRecord.getField("ALLOWNCERECEIVBLE").setValue(m.get("10806").toString());//应收补贴款10806
	          currentRecord.getField("EXPORTDRWBCKRECEIVBLE").setValue(m.get("131").toString());//应收出口退税""
	          currentRecord.getField("INVENTORIES").setValue(m.get("110").toString());//存货
	          currentRecord.getField("RWMTERILS").setValue(m.get("11001").toString());//存货原材料""
	          currentRecord.getField("FINISHEDPRODUCTS").setValue(m.get("11002").toString());//#存货产成品""
	          currentRecord.getField("DEFERREDEXPENSES").setValue(m.get("109").toString());//待摊费用109
	          currentRecord.getField("UNSETTLEDGLONCURRENTSSETS").setValue(m.get("113").toString());//待处理流动资产净损失 113
	          currentRecord.getField("M9533").setValue(m.get("114").toString());//一年内到期的长期债权投资 114
	          currentRecord.getField("OTHERCURRENTSSETS").setValue(m.get("115").toString());//其他流动资产115
	          
	          b1 = new BigDecimal(m.get("101").toString()).add(new BigDecimal(m.get("102").toString())).add(new BigDecimal(m.get("103").toString())).add(new BigDecimal(m.get("134").toString())).add(new BigDecimal(m.get("19c").toString())).add(new BigDecimal(m.get("104").toString())).add(new BigDecimal(m.get("108").toString())).add(new BigDecimal(m.get("19n").toString())).add(new BigDecimal(m.get("129").toString())).add(new BigDecimal(m.get("10806").toString())).add(new BigDecimal(m.get("131").toString())).add(new BigDecimal(m.get("110").toString())).add(new BigDecimal(m.get("11001").toString())).add(new BigDecimal(m.get("11002").toString())).add(new BigDecimal(m.get("109").toString())).add(new BigDecimal(m.get("113").toString())).add(new BigDecimal(m.get("114").toString())).add(new BigDecimal(m.get("115").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//流动资产合计：9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          
	          currentRecord.getField("LONGTERMINVESTMENT").setValue(m.get("116").toString());//长期投资116
	          currentRecord.getField("LONGTERMEQUITYINVESTMENT").setValue(m.get("139").toString());//长期股权投资""
	          currentRecord.getField("LONGTERMSECURITIESINVESTMENT").setValue(m.get("140").toString());//长期债权投资""
	          currentRecord.getField("INCORPORTINGPRICEDIFFERENCE").setValue(m.get("135").toString());//合并价差135
	          
	          b1 = new BigDecimal(m.get("116").toString()).add(new BigDecimal(m.get("135").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//长期投资合计 9539+9545
	          
	          currentRecord.getField("ORIGINLCOSTOFFIXEDSSET").setValue(m.get("117").toString());//#固定资产原价 117
	          currentRecord.getField("FIXEDSSETCCUMULTEDDEPRECITION").setValue(m.get("118").toString());//累计折旧118
	          //setCol2Value2Jian("FIXEDSSETSNETVLUE").setValue(m.get("117").setValue(m.get("118").toString());//固定资产净值 9549-9551}
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//长期投资合计 9539+9545
	          
	          currentRecord.getField("M9555").setValue(m.get("141").toString());//固定资产值减值准备 141
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//固定资产净额 9553-9555
	          
	          currentRecord.getField("FIXEDSSETSPENDINGFORDISPOSL").setValue(m.get("121").toString());//固定资产清理 121
	          currentRecord.getField("CONSTRUCTIONMTERILS").setValue(m.get("143").toString());//工程物资""
	         
	          
	          b1 = new BigDecimal(m.get("120").toString());
	          currentRecord.getField("CONSTRUCTIONINPROGRESS").setValue(b1.toString());////在建工程  120
	          
	          currentRecord.getField("UNSETTLEDGLONFIXEDSSETS").setValue(m.get("122").toString());//待处理固定资产净损失   122
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString())).add(new BigDecimal(m.get("121").toString())).add(new BigDecimal(m.get("143").toString())).add(new BigDecimal(m.get("120").toString())).add(new BigDecimal(m.get("122").toString()));
	          currentRecord.getField("TOTLFIXEDSSETS").setValue(b1.toString());//;//固定资产合计 9557+9559+9561+9563+9565
	          
	          currentRecord.getField("INTNGIBLESSETS").setValue(m.get("123").toString());//无形资产 123
	          currentRecord.getField("LNDUSERIGHTS").setValue(m.get("12301").toString());//无形资产土地使用权 ""
	          currentRecord.getField("DEFERREDSSETS").setValue(m.get("181").toString());//递延资产
	          currentRecord.getField("INCLUDINGFIXEDSSETSREPIR").setValue(m.get("12401").toString());//递延资产固定资产修理 ""
	          currentRecord.getField("M9577").setValue(m.get("12402").toString());//#递延资产固定资产改良支出 ""
	          currentRecord.getField("OTHERLONGTERMSSETS").setValue(m.get("133").toString());//其他长期资产  125+181
	          currentRecord.getField("M9581").setValue(m.get("31405").toString());//其他长期资产特准储备物资 ""
	          
	          b1 = new BigDecimal(m.get("123").toString()).add(new BigDecimal(m.get("181").toString())).add(new BigDecimal(m.get("133").toString()));
	          //无形及其他资产合计 9569+9573+9579  
	          currentRecord.getField("M9583").setValue(b1.toString());
	          
	          currentRecord.getField("DEFERREDSSETSDEBITS").setValue(m.get("126").toString());//递延税款借项 126
	          
	          b1 = new BigDecimal(m.get("101").toString()).add(new BigDecimal(m.get("102").toString())).add(new BigDecimal(m.get("103").toString())).add(new BigDecimal(m.get("134").toString())).add(new BigDecimal(m.get("19c").toString())).add(new BigDecimal(m.get("104").toString())).add(new BigDecimal(m.get("108").toString())).add(new BigDecimal(m.get("19n").toString())).add(new BigDecimal(m.get("129").toString())).add(new BigDecimal(m.get("10806").toString())).add(new BigDecimal(m.get("131").toString())).add(new BigDecimal(m.get("110").toString())).add(new BigDecimal(m.get("11001").toString())).add(new BigDecimal(m.get("11002").toString())).add(new BigDecimal(m.get("109").toString())).add(new BigDecimal(m.get("113").toString())).add(new BigDecimal(m.get("114").toString())).add(new BigDecimal(m.get("115").toString())).add(new BigDecimal(m.get("117").toString())).subtract(new BigDecimal(m.get("118").toString())).add(new BigDecimal(m.get("117").toString())).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString())).add(new BigDecimal(m.get("121").toString())).add(new BigDecimal(m.get("143").toString())).add(new BigDecimal(m.get("120").toString())).add(new BigDecimal(m.get("122").toString())).add(new BigDecimal(m.get("123").toString())).add(new BigDecimal(m.get("181").toString())).add(new BigDecimal(m.get("133").toString())).add(new BigDecimal(m.get("126").toString()));
	          currentRecord.getField("TOTLSSETS").setValue(b1.toString());//资产总计 9537+9547+9567+9583+9585
	          
	          currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("201").toString());//短期借款 201
	          currentRecord.getField("NOTESPYBLE").setValue(m.get("202").toString());//应付票据 202
	          currentRecord.getField("ACCOUNTSPYBLE").setValue(m.get("203").toString());//应付账款 203
	          currentRecord.getField("RECEIPTSINDVNCE").setValue(m.get("204").toString());//预收账款 204
	          currentRecord.getField("WGESORSLRIESPYBLES").setValue(m.get("218").toString());//应付工资 218}
	          currentRecord.getField("EMPLOYEEBENEFITS").setValue(m.get("205").toString());//应付福利费 205
	          
	          b1 = new BigDecimal(m.get("31404").toString()).add(new BigDecimal(m.get("274").toString()));
	          currentRecord.getField("INCOMEPYBLE").setValue(b1.toString());//应付利润 314040+274
	          
	          
	          currentRecord.getField("TXESPYBLE").setValue(m.get("276").toString());//应交税金
	          currentRecord.getField("OTHERPYBLETOGOVERNMENT").setValue(m.get("94704").toString());//其他应交款
	          currentRecord.getField("OTHERPYBLE").setValue(m.get("209").toString());//其他应付款 209
	          
	          b1 = new BigDecimal(m.get("210").toString()).add(new BigDecimal(m.get("31403").toString()));
	          currentRecord.getField("PROVISIONFOREXPENSES").setValue(b1.toString());//预提费用 210
	          //currentRecord.getField("PROVISIONS").setValue(m.get("").toString());//#预计负债""
	          
	          currentRecord.getField("M9613").setValue(m.get("211").toString());//一年内到期的长期负债 211
	          currentRecord.getField("OTHERCURRENTLIBILITIES").setValue(m.get("212").toString());//其他流动负债 212}
	          
	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString()));
	          currentRecord.getField("TOTLCURRENTLIBILITIES").setValue(b1.toString());//流动负债合计 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
	          
	          currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("213").toString());//长期借款 213 
	          currentRecord.getField("BONDSPYBLE").setValue(m.get("214").toString());//应付债券 214
	          currentRecord.getField("LONGTERMPYBLES").setValue(m.get("215").toString());//长期应付款 215
	          currentRecord.getField("GRNTSPYBLE").setValue(m.get("223").toString());//专项应付款 220
	          currentRecord.getField("OTHERLONGTERMLIBILITIES").setValue(m.get("427").toString());//其他长期负债 216
	          currentRecord.getField("SPECILRESERVEFUND").setValue(m.get("21601").toString());//其他长期负债特准储备基金 ""
	          
	          b1 = new BigDecimal(m.get("213").toString()).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString()));
	          currentRecord.getField("TOTLLONGTERMLIBILITIES").setValue(b1.toString());//长期负债合计 9619+9621+9623+9625+9627
	          
	          currentRecord.getField("DEFERREDTXTIONCREDIT").setValue(m.get("217").toString());//递延税款贷项 217
	
	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString())).add(new BigDecimal(m.get("217").toString()));
	          currentRecord.getField("TOTLLIBILITIES").setValue(b1.toString());//负债合计 9617+9631+9633
	          
	          currentRecord.getField("MINORITYINTEREST").setValue(m.get("307").toString());//少数股东权益 307
	          currentRecord.getField("PIDINCPITL").setValue(m.get("301").toString());//实收资本 301
	          currentRecord.getField("NTIONLCPITL").setValue(m.get("30101").toString());//国家资本 ""
	          currentRecord.getField("COLLECTIVECPITL").setValue(m.get("30102").toString());//集体资本""
	          currentRecord.getField("LEGLPERSONSCPITL").setValue(m.get("30103").toString());//法人资本""
	          currentRecord.getField("STTEOWNEDLEGLPERSONSCPITL").setValue(m.get("3010301").toString());//法人资本国有法人资本""
	          currentRecord.getField("COLLECTIVELEGLPERSONSCPITL").setValue(m.get("3010302").toString());//法人资本集体法人资本""
	          currentRecord.getField("PERSONLCPITL").setValue(m.get("30104").toString());//个人资本""
	          currentRecord.getField("FOREIGNBUSINESSMENSCPITL").setValue(m.get("30105").toString());//#外商资本""
	          currentRecord.getField("CPITLRRSERVE").setValue(m.get("302").toString());//资本公积 302
	          currentRecord.getField("SURPLUSRESERVE").setValue(m.get("303").toString());//盈余公积 303
	          //currentRecord.getField("STTUTORYSURPLUSRESERVE").setValue(m.get("").toString());//盈余公积法定盈余公积""
	          //currentRecord.getField("PUBLICWELFREFUND").setValue(m.get("").toString());//盈余公积公益金
	          //currentRecord.getField("SUPPLERMENTRYCURRENTCPITL").setValue(m.get("").toString());//#盈余公积补充流动资本 ""
	          currentRecord.getField("UNFFIRMEDINVESTMENTLOSS").setValue(m.get("306").toString());//未确认的投资损失""
	          currentRecord.getField("UNPPROPRITEDPROFIT").setValue(m.get("305").toString());//未分配利润 305
	          
	          
	          b1 = new BigDecimal(m.get("308").toString()).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("M9669").setValue(b1.toString());//外币报表折算差额""
	          
	          b1 = new BigDecimal(m.get("301").toString()).add(new BigDecimal(m.get("302").toString())).add(new BigDecimal(m.get("303").toString())).add(new BigDecimal(m.get("306").toString()).add(new BigDecimal(m.get("305").toString()))).add(new BigDecimal(m.get("308").toString())).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("TOTLEQUITY").setValue(b1.toString());////所有者权益合计 9639+9655+9657+9665+9667+9669

	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString())).add(new BigDecimal(m.get("217").toString())).add(new BigDecimal(m.get("307").toString())).add(new BigDecimal(m.get("301").toString())).add(new BigDecimal(m.get("302").toString())).add(new BigDecimal(m.get("303").toString())).add(new BigDecimal(m.get("306").toString()).add(new BigDecimal(m.get("305").toString()))).add(new BigDecimal(m.get("308").toString())).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("TOTLEQUITYNDLIBILITIES").setValue(b1.toString());//负债和所有者权益总计
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


