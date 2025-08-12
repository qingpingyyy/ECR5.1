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

public class FinanceCf02ReportProvider extends DefaultDataSourceProvider{

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

        //2002版现金流量信息
        //String sQueryReportNo ="select ReportNo from report_record where objectno =? and modelno ='0007' and reportscope <>'03' ";
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0018' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
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
	     	  
	     	  
	     	  //2002版现金流量信息
	          //setCol2Value("M9795",""); //销售商品和提供劳务收到的现金a01}   
	          //setCol2Value2("TAXREFUNDS","",""); //收到的税费返还a17+a18}         
	          setCol2Value("M9799","b94"); //收到的其他与经营活动有关的现金a16+a19} 
	          
	          setCol2Value("M9823","b94"); //经营活动现金流入小计 9795+9797+9799} 9823
	          
	          setCol2Value("CASHPAIDFORGOODSANDSERVICES","b95"); //购买商品、接受劳务支付的现金a02}   
	          setCol2Value("M9805","a22"); //支付给职工以及为职工支付的现金a22}  
	          setCol2Value3Jia("PAYMENTSOFALLTYPESOFTAXES","b112","b114","a49"); //支付的各项税费a23+a24+a25}         
	          setCol2Value2Jia("M9809","a19","a26"); //支付的其他与经营活动有关的现金a03+a26} 
	          
	          //
	          setCol2Value7Jia("M9831","b95","a22","b112","b114","a49","a19","a26"); //经营活动现金流出小计} 9803+9805+9807+9809  
	          
	          setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //经营活动产生的现金流量净额 }     9823-9831
	          
	          setCol2Value("M9815","a28"); //收回投资所收到的现金a28}   
	          setCol2Value("CASHRECEIVEDFROMONVESTMENTS","b100"); //取得投资收益所收到的现金a30}      
	          setCol2Value("M9819","b101"); //处置固定资产无形资产和其他长期资产所收回的现金净额a31}
	          
	          //b119+b59+c98
	          setCol2Value3Jia("M9821","b119","b59","c98"); //收到的其他与投资活动有关的现金a29+a32}        
	          
	          setCol2Value6Jia("M9917","a28","b100","b101","b119","b59","c98"); //投资活动现金流入小计}    9815+9817+9819+9821
	                 
	          //setCol2Value("M9825",""); //购建固定资产无形资产和其他长期资产所支付的现金a34}    
	          //"b67+b120
	          setCol2Value2Jia("CASHPAYMENTSFORINVESTMENTS","b67","b120"); //投资所支付的现金}     a35+a36  
	          
	          setCol2Value("M9829","a37"); //支付的其他与投资活动有关的现金a37}  
	          
	          setCol2Value3Jia("M9919","b67","b120","a37"); //投资活动现金流出小计}        9825+9827+9829
	          
	          setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //投资活动产生的现金流量净额}    9917-9919
	          
	          setCol2Value("CASHRECEIVEDFROMINVESTORS","b109"); //吸收投资所收到的现金a39}          
	          setCol2Value("CASHFROMBORROWINGS","a41"); //借款所收到的现金a41}       
	          setCol2Value2Jia("M9839","b71","b72"); //收到的其他与筹资活动有关的现金a40+a42}  
	          
	          setCol2Value4Jia("M9921","b109","a41","b71","b72"); //筹资活动现金流入小计} 9835+9837+9839
	          
	          setCol2Value("CASHREPAYMENTSFORDEBTS","a44"); //偿还债务所支付的现金a44}        
	          setCol2Value("M9845","a46"); //分配股利、利润或偿付利息所支付的现金a46}           
	          setCol2Value4Jia("M9847","a45","a47","a48","a49"); //支付的其他与筹资活动有关的现金a45+a47+a48+a49}  
	          
	          
	          setCol2Value6Jia("M9923","a44","a46","a45","a47","a48","a49"); //筹资活动现金流出小计}   9843+9845+9847
	          
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
	          setCol2Value10("M9851","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49" ); //筹集活动产生的现金流量净额}  9921-9923
	          
	          setCol2Value("M9853","a51"); //汇率变动对现金的影响a51}   
	          //value10 +a51  +setCol2ValueLDZJJE +setCol2ValueTZHDCSXJLLJE
	          setCol2ValueXJDJWJZJE("M9855","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51" ,"b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37" ); //现金及现金等价物净增加额1}   9813+9833+9851+9853

	          //setCol2Value("NETPROFIT",""); //净利润z85}              
	          //setCol2Value("PROVISIONFORASSETS",""); //计提的资产减值准备""}    
	          //setCol2Value("DEPRECIATIONOFFIXEDASSETS",""); //固定资产拆旧z87}           
	          //setCol2Value("M9863",""); //无形资产摊销z88}          
	          //setCol2Value("M9865",""); //长期待摊费用摊销z78}      
	          //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //待摊费用减少""}           
	          //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //预提费用增加""}           
	          //setCol2Value("M9871",""); //处置固定资产无形资产和其他长期资产的损失z89}         	
	          //setCol2Value("M9873",""); //固定资产报废损失z90}      
	          //setCol2Value("FINANCEEXPENSE",""); //财务费用z91}             
	          //setCol2Value("LOSSESARSINGFROMINVESTMENT",""); //投资损失}z92              
	          //setCol2Value("DEFERREDTAXCREDIT",""); //递延税款贷项z93}           
	          //setCol2Value("DECREASEININVENTORIES",""); //存货的减少z94}            
	          //setCol2Value("M9883",""); //经营性应收项目的减少z95}         
	          //setCol2Value("M9885",""); //经营性应付项目的增加z96}          
	          setCol2ValueLDZJJE("OTHERS1","b94","b95","a22","b112","b114","a49","a19","a26" ); //其他（净利润调节为经营活动现金流量科目下）z98}       	
	          //setCol2Value("M1813",""); //经营活动产生的现金流量净额1z99}    
	          //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //债务转为资本""}          
	          //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //一年内到期的可转换公司债券""}               		 
	          //setCol2Value("M9895",""); //融资租入固定资产""}      
	          //setCol2Value("OTHERS2",""); //其他（不涉及现金收支的投资和筹资活动科目下""}       	
	          
	          
	          
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" setCol2Value10
	          
	          setCol2ValueXJDQMYE("CASHATTHEENDOFPERIOD","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51"); //现金的期末余额        9851+9853
	          //setCol2Value("M9901",""); //现金的期初余额z102}      
	          
	          // setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //经营活动产生的现金流量净额 }     9823-9831  9813
	          //setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //投资活动产生的现金流量净额}    9917-9919
	          
	          setCol2ValueXJDJWQMYE("M9903", "b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37" ); //现金等价物的期末余额z103}   9813+9833
	          //setCol2Value("M9905","z104"); //现金等价物的期初余额z104}   
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" + "a51"'    +'"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"' ,+'"a51"')
	          setCol2ValueXJDJWJZJE("M1855", "b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51" ,"b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //现金及现金等价物净增加额2} 9851+9853+9813+9833
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }

	private void setCol2ValueXJDJWQMYE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17) {
			
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

				// setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //经营活动产生的现金流量净额 }     9823-9831  9813
		        //setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //投资活动产生的现金流量净额}    9917-9919
				
				//double col2Value=a1-(a2+a3+a4+a5+a6+a7+a8);
				//double col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9);
				
				double col2Value=a1-(a2+a3+a4+a5+a6+a7+a8) + a9+a10+a11+a12+a13+a14-(a15+a16+a17) ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}




	private void setCol2ValueXJDQMYE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11) {
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
						double col2Value=a1+a2+a3+a4-(a5+a6+a7+a8+a9+a10)+a11;
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}




	private void setCol2ValueXJDJWJZJE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28) {
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
				 //value10 +a51  +setCol2ValueLDZJJE +setCol2ValueTZHDCSXJLLJE
				// value10 col2Value=a1+a2+a3+a4-(a5+a6+a7+a8+a9+a10);
				//double a1-(a2+a3+a4+a5+a6+a7+a8);
				//double col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9);
				double col2Value=a1+a2+a3+a4-(a5+a6+a7+a8+a9+a10) +a11 +  a12-(a13+a14+a15+a16+a17+a18+a19 )  + +a20+a21+a22+a23+a24+a25 -(a26+a27+a28) ;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    
    //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
    private void setCol2Value10(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10) {
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
				
				double col2Value=a1+a2+a3+a4-(a5+a6+a7+a8+a9+a10);
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	private void setCol2Value6Jia( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6) {
    	try {
    			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double a6 = getCol2Value(rowSubject6);
				double col2Value=a1+a2+a3+a4+a5+a6;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    //"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"
	private void setCol2ValueTZHDCSXJLLJE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9) {
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
						double col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9);
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
    
	private void setCol2Value4Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) {
    	try {
    			if(currentRecord.getField(item) == null) return;
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
    
    private void setCol2ValueLDZJJE( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8) {
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
				
				double col2Value=a1-(a2+a3+a4+a5+a6+a7+a8);
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void setCol2Value7Jia(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
				double col2Value=a1-a2-a3+a4+(a5-a6)+a7;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setCol2Value3Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3) {
    	try {
    			if(currentRecord.getField(item) == null) return;
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


