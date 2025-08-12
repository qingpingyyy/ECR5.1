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

public class FinanceBsReportProvider extends DefaultDataSourceProvider{

    private Connection conn1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private Log logger = ARE.getLog();
    String database = "loan";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate ="";
    String sReportType="";//报表类型
    String s1="";
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        sReportType =currentRecord.getField("SHEETTYPE").getString(); if(sReportType==null) sReportType= "" ;
        
        String sQueryReportNo = "select distinct rr.ReportNo as ReportNo  from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0011' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' " ;
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1,sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
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
	          
        	  //currentRecord.getField("SHEETYEAR").setValue(s1);//报表年份
        	  //ps = conn1.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");	     	  
        	  setCol2Value("CURRENCYFUNDS","101");//货币资金101
	          setCol2Value("SHORTTERMINVESTMENTS","102");//短期投资
	          setCol2Value("NOTESRECEIVBLE","103");//应收票据 103
	          setCol2Value("DIVIDENDSRECEIVBLE","134");//应收股利 134
	          setCol2Value("INTERESTRECEIVBLE","19c");//应收利息19c
	          setCol2Value("ACCOUNTSRECEIVBLE","104");//应收账款104
	          setCol2Value("OTHERRECEIVBLES","108");//其他应收款 108
	          setCol2Value("PREPYMENTS","19n");//#预付账款19n
	          setCol2Value("FUTUREGURNTEE","129");//期货保证金""
	          setCol2Value("ALLOWNCERECEIVBLE","10806");//应收补贴款10806
	          setCol2Value("EXPORTDRWBCKRECEIVBLE","131");//应收出口退税""
	          setCol2Value("INVENTORIES","110");//存货
	          setCol2Value("RWMTERILS","11001");//存货原材料""
	          setCol2Value("FINISHEDPRODUCTS","11002");//#存货产成品""
	          setCol2Value("DEFERREDEXPENSES","109");//待摊费用109
	          setCol2Value("UNSETTLEDGLONCURRENTSSETS","113");//待处理流动资产净损失 113
	          setCol2Value("M9533","114");//一年内到期的长期债权投资 114
	          setCol2Value("OTHERCURRENTSSETS","115");//其他流动资产115
	          
	          //流动资产合计：9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","103","134","19c","104","108","19n","129","10806","131","110","109","113","114","115");
	          //setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115");//流动资产合计 9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          
	          setCol2Value("LONGTERMINVESTMENT","116");//长期投资116
	          setCol2Value("LONGTERMEQUITYINVESTMENT","139");//长期股权投资""
	          setCol2Value("LONGTERMSECURITIESINVESTMENT","140");//长期债权投资""
	          setCol2Value("INCORPORTINGPRICEDIFFERENCE","135");//合并价差135
	          setCol2Value2Jia("TOTLLONGTERMINVESTMENT","116","135");//长期投资合计 9539+9545
	          
	          setCol2Value("ORIGINLCOSTOFFIXEDSSET","117");//#固定资产原价 117
	          setCol2Value("FIXEDSSETCCUMULTEDDEPRECITION","118");//累计折旧118
	          setCol2Value2Jian("FIXEDSSETSNETVLUE","117","118");//固定资产净值 9549-9551}
	          
	          
	          setCol2Value("M9555","141");//固定资产值减值准备 141
	          setCol2Value3Jian("NETVLUEOFFIXEDSSETS","117","118","141");//固定资产净额 9553-9555
	          
	          setCol2Value("FIXEDSSETSPENDINGFORDISPOSL","121");//固定资产清理 121
	          setCol2Value("CONSTRUCTIONMTERILS","143");//工程物资""
	          setCol2Value2Jian("CONSTRUCTIONINPROGRESS","120","19a");//在建工程  120-19a
	          setCol2Value("UNSETTLEDGLONFIXEDSSETS","122");//待处理固定资产净损失   122
	          //117-118-128+121+(120-19a)+122 
	          setCol2Value8Jia("TOTLFIXEDSSETS","117","118","141"  ,"121" ,"143","120","19a" ,"122");//固定资产合计 9557+9559+9561+9563+9565
	          // a1-a2-a3+a4+a5+(a6-a7)+a8
	          
	          setCol2Value("INTNGIBLESSETS","123");//无形资产 123
	          setCol2Value("LNDUSERIGHTS","12301");//无形资产土地使用权 ""
	          setCol2Value("DEFERREDSSETS","181");//递延资产
	          setCol2Value("INCLUDINGFIXEDSSETSREPIR","12401");//递延资产固定资产修理 ""
	          setCol2Value("M9577","12402");//#递延资产固定资产改良支出 ""
	          setCol2Value("OTHERLONGTERMSSETS","133");//其他长期资产  125+181
	          setCol2Value("M9581","31405");//其他长期资产特准储备物资 ""
	          //123-199+124+125+181
	          setCol2Value3Jia("M9583","123","181" ,"133" );//无形及其他资产合计 9569+9573+9579  
	          
	          setCol2Value("DEFERREDSSETSDEBITS","126");//递延税款借项 126
	          //资产总计 9537+9547+9567+9583+9585
	          setCol2ValueZCZJ("TOTLSSETS","101","102","103","134","19c","104","108","19n","129","10806","131","110","109","113","114","115"       ,"116","135"  ,"117","118","141"  ,"121" ,"143","120","19a" ,"122"    ,"123","181" ,"133"  ,"126"   );
	          
	          setCol2Value("SHORTTERMBORROWINGS","201");//短期借款 201
	          setCol2Value("NOTESPYBLE","202");//应付票据 202
	          setCol2Value("ACCOUNTSPYBLE","203");//应付账款 203
	          setCol2Value("RECEIPTSINDVNCE","204");//预收账款 204
	          setCol2Value("WGESORSLRIESPYBLES","218");//应付工资 218}
	          setCol2Value("EMPLOYEEBENEFITS","205");//应付福利费 205
	          setCol2Value3Jia("INCOMEPYBLE","219","230","274");//应付利润 219+230+274
	          setCol2Value("TXESPYBLE","207");//应交税金 207
	          setCol2Value("OTHERPYBLETOGOVERNMENT","208");//其他应交款 208
	          setCol2Value("OTHERPYBLE","209");//其他应付款 209
	          setCol2Value("PROVISIONFOREXPENSES","210");//预提费用 210
	          //setCol2Value("PROVISIONS","");//#预计负债""
	          setCol2Value("M9613","211");//一年内到期的长期负债 211
	          setCol2Value("OTHERCURRENTLIBILITIES","212");//其他流动负债 212}
	          
	          setCol2ValueLDFZHJ("TOTLCURRENTLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212");//流动负债合计 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
	          
	          setCol2Value("LONGTERMBORROWINGS","213");//长期借款 213 
	          setCol2Value("BONDSPYBLE","214");//应付债券 214
	          setCol2Value("LONGTERMPYBLES","215");//长期应付款 215
	          setCol2Value("GRNTSPYBLE","223");//专项应付款 220
	          setCol2Value("OTHERLONGTERMLIBILITIES","427");//其他长期负债 216
	          setCol2Value("SPECILRESERVEFUND","21601");//其他长期负债特准储备基金 ""
	          setCol2Value5Jia("TOTLLONGTERMLIBILITIES","213","214","215","223","427");//长期负债合计 9619+9621+9623+9625+9627
	          setCol2Value("DEFERREDTXTIONCREDIT","217");//递延税款贷项 217
	          
	          setCol2ValueFZHJ("TOTLLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212"    ,"213","214","215","223","427"  ,"217" );//负债合计 9617+9631+9633
	
	          setCol2Value("MINORITYINTEREST","307");//少数股东权益 307
	          setCol2Value("PIDINCPITL","301");//实收资本 301
	          setCol2Value("NTIONLCPITL","30101");//国家资本 ""
	          setCol2Value("COLLECTIVECPITL","30102");//集体资本""
	          setCol2Value("LEGLPERSONSCPITL","301003");//法人资本""
	          setCol2Value("STTEOWNEDLEGLPERSONSCPITL","3010301");//法人资本国有法人资本""
	          setCol2Value("COLLECTIVELEGLPERSONSCPITL","3010302");//法人资本集体法人资本""
	          setCol2Value("PERSONLCPITL","30104");//个人资本""
	          setCol2Value("FOREIGNBUSINESSMENSCPITL","30105");//#外商资本""
	          setCol2Value("CPITLRRSERVE","302");//资本公积 302
	          setCol2Value("SURPLUSRESERVE","303");//盈余公积 303
	          //setCol2Value("STTUTORYSURPLUSRESERVE","");//盈余公积法定盈余公积""
	          //setCol2Value("PUBLICWELFREFUND","");//盈余公积公益金
	          //setCol2Value("SUPPLERMENTRYCURRENTCPITL","");//#盈余公积补充流动资本 ""
	          setCol2Value("UNFFIRMEDINVESTMENTLOSS","306");//未确认的投资损失""
	          setCol2Value("UNPPROPRITEDPROFIT","305");//未分配利润 305
	          setCol2Value2Jian("M9669","308","31401");//外币报表折算差额""   308-31401
	          setCol2ValueSYZQY("TOTLEQUITY","301",   "302","303","306" ,"305" ,"308","31401");//所有者权益合计 9639+9655+9657+9665+9667+9669
	          
	          setCol2ValueFZHSYZQY("TOTLEQUITYNDLIBILITIES" ,"201","202","203","204","218","205","219","230","274","207","208","209","210","211","212"    ,"213","214","215","223","427"  ,"217"    ,"307" ,"301",   "302","303","306" ,"305" ,"308","31401"  );//负债和所有者权益总计 9635+9671+9637
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }
    
    
	private void setCol2ValueFZHSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29) {
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
				double a14 = getCol2Value(rowSubject14);
				double a15 = getCol2Value(rowSubject15);
				double a16 = getCol2Value(rowSubject16);
				double a17 = getCol2Value(rowSubject17);
				double a18 = getCol2Value(rowSubject18);
				double a19 = getCol2Value(rowSubject19);
				double a20 = getCol2Value(rowSubject20);
				double a21 = getCol2Value(rowSubject21);
				double a22 = getCol2Value(rowSubject22);
				double a23 = getCol2Value(rowSubject23);
				double a24 = getCol2Value(rowSubject24);
				double a25 = getCol2Value(rowSubject25);
				double a26 = getCol2Value(rowSubject26);
				double a27 = getCol2Value(rowSubject27);
				double a28 = getCol2Value(rowSubject28);
				double a29 = getCol2Value(rowSubject29);
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16 +a17+a18   +a19+a20+a21+a22+a23+a24+a25+a26  +a27+(a28-a29)  ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		ARE.getLog().info("负债和所有者权益总计出错！");
		e.printStackTrace();
	}
	
		
	}





	private void setCol2ValueSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7) {
		try {
				if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double a6 = getCol2Value(rowSubject6);
				double a7 = getCol2Value(rowSubject7);
				double col2Value=a1+a2+a3+a4+a5+(a6-a7 ) ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		ARE.getLog().info("所有者权益合计出错！");
		e.printStackTrace();
	}
		
	}





	private void setCol2ValueLDFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15) {
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
					double a14 = getCol2Value(rowSubject14);
					double a15 = getCol2Value(rowSubject15);
					double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15 ;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				ARE.getLog().info("流动负债合计出错！");
				e.printStackTrace();
			}
			
	}





	private void setCol2ValueZCZJ( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29, String rowSubject30) {
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
				double a14 = getCol2Value(rowSubject14);
				double a15 = getCol2Value(rowSubject15);
				double a16 = getCol2Value(rowSubject16);
				double a17 = getCol2Value(rowSubject17);
				double a18 = getCol2Value(rowSubject18);
				double a19 = getCol2Value(rowSubject19);
				double a20 = getCol2Value(rowSubject20);
				double a21 = getCol2Value(rowSubject21);
				double a22 = getCol2Value(rowSubject22);
				double a23 = getCol2Value(rowSubject23);
				double a24 = getCol2Value(rowSubject24);
				double a25 = getCol2Value(rowSubject25);
				double a26 = getCol2Value(rowSubject26);
				double a27 = getCol2Value(rowSubject27);
				double a28 = getCol2Value(rowSubject28);
				double a29 = getCol2Value(rowSubject29);
				double a30 = getCol2Value(rowSubject30);
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16 +a17+a18 +a19+a20+a21  +a22    +a23+a24+a25+a26  +a27+a28+a29 +a30  ;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			ARE.getLog().info("资产总计出错！");
			e.printStackTrace();
		}
	}

	private void setCol2ValueLDZCHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16) {
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
				double a14 = getCol2Value(rowSubject14);
				double a15 = getCol2Value(rowSubject15);
				double a16 = getCol2Value(rowSubject16);
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16;
			currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			ARE.getLog().info("流动资产合计出错！");
			e.printStackTrace();
		}
		
	}

    
    
    private void setCol2ValueFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21) {
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
						double a14 = getCol2Value(rowSubject14);
						double a15 = getCol2Value(rowSubject15);
						double a16 = getCol2Value(rowSubject16);
						double a17 = getCol2Value(rowSubject17);
						double a18 = getCol2Value(rowSubject18);
						double a19 = getCol2Value(rowSubject19);
						double a20 = getCol2Value(rowSubject20);
						double a21 = getCol2Value(rowSubject21);
						double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16+a17+a18+a19+a20+a21;
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					ARE.getLog().info("负债合计合计出错！");
					e.printStackTrace();
				}
	}
    
    private void setCol2Value5Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4, String rowSubject5) {
	    	try {
					if(currentRecord.getField(item) == null) return;
					double a1 = getCol2Value(rowSubject1);
					double a2 = getCol2Value(rowSubject2);
					double a3 = getCol2Value(rowSubject3);
					double a4 = getCol2Value(rowSubject4);
					double a5 = getCol2Value(rowSubject5);
					double col2Value=a1+a2+a3+a4+a5;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
    
	private void setCol2Value8Jia(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7,String rowSubject8) {
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
				double col2Value=a1-a2-a3+a4+a5+(a6-a7)+a8;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	private void setCol2Value3Jian(String item, String rowSubject1, String rowSubject2, String rowSubject3) {
       	try {
       		if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double col2Value=a1-a2-a3;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    
	private void setCol2Value2Jian(String item, String rowSubject1, String rowSubject2) {
       	try {
       			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double col2Value=a1-a2;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	private void setCol2Value3Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3) {
    	try {
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double col2Value=a1+a2+a3;
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

	
	private void setCol2Value2Jia(String item, String rowSubject1, String rowSubject2) throws SQLException {
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


