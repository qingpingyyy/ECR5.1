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

public class FinanceCf07ReportProvider extends DefaultDataSourceProvider{

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
        
        //企业2007版现金流量信息
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0297' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	  
	     	  //企业2007版现金流量信
	          setCol2Value("M9199","z100"); //销售商品和提供劳务收到的现金                  
	          setCol2Value("TAXREFUNDS","z102"); //收到的税费返还                             	 
	          setCol2Value("M9201","z103"); //收到其他与经营活动有关的现金  
	          setCol2Value3("M9202","z100","z102","z103"); //经营活动现金流入小计     9199+9200+9201                    	
	          setCol2Value("CASHPAIDFORGOODSANDSERVICES","b95"); //购买商品、接受劳务支付的现金                  
	          setCol2Value("M9204","a22"); //支付给职工以及为职工支付的现金                
	          setCol2Value("PAYMENTSOFALLTYPESOFTAXES","z104"); //支付的各项税费                             	 
	          setCol2Value("M9206","z105"); //支付其他与经营活动有关的现金                  
	          setCol2Value4Jia("M9207","b95","a22","z104","z105"); //经营活动现金流出小计
	          
	          //"601","602","603"-( "605","606","607","608")
	          setCol2Value7H("M9208","z100","z102","z103","b95","a22","z104","z105"); //经营活动产生的现金流量净额1      9202-9207             
	          
	          setCol2Value("M9209","z106"); //收回投资所收到的现金                          
	          setCol2Value("CASHRECEIVEDFROMONVESTMENTS","z107"); //取得投资收益所收到的现金                      
	          setCol2Value("M9211","z108"); //处置固定资产无形资产和其他长期资产所收回的现金
	          setCol2Value("M9212","z111"); //处置子公司及其他营业单位收到的现金净额        
	          setCol2Value("M9213","a32"); //收到其他与投资活动有关的现金                  
	          setCol2Value5Jia("M9214","z106","z107","z108","z111","a32"); //投资活动现金流入小计 9209+9210+9211+9212+9213      
	          
	          setCol2Value("M9215","z109"); //购建固定资产无形资产和其他长期资产所支付的现金
	          setCol2Value("CASHPAYMENTSFORINVESTMENTS","z110"); //投资所支付的现金                           	 
	          setCol2Value("M9217","z111"); //取得子公司及其他营业单位支付的现金净额        
	          setCol2Value("M9218","z112"); //支付其他与投资活动有关的现金                  
	          setCol2Value4Jia("SUBTOTALOFCASHOUTFLOWS","z109","z110","z111","z112"); //投资活动现金流出小计 9215+9216+9217+9218
	          
	          //("M9220","611","612","613","614","615" - ("617","618","619","620"))
	          setCol2Value9H1("M9220","z106","z107","z108","z111","a32","z109","z110","z111","z112"); //投资活动产生的现金流量净额  9214-9219
	          
	          setCol2Value("CASHRECEIVEDFROMINVESTORS","z113"); //吸收投资收到的现金                          	 
	          setCol2Value("CASHFROMBORROWINGS","z114"); //取得借款收到的现金                          	 
	          setCol2Value("M9223","a42"); //收到其他与筹资活动有关的现金                  
	          setCol2Value3("M9224","z113","z114","a42"); //筹资活动现金流入小计        9221+9222+9223   
	          
	          setCol2Value("CASHREPAYMENTSFORDEBTS","z115"); //偿还债务所支付的现金                        	 
	          setCol2Value("M9226","z116"); //分配股利、利润或偿付利息所支付的现金          
	          setCol2Value("M9227","z117"); //支付其他与筹资活动有关的现金                  
	          setCol2Value3("M9228","z115","z116","z117"); //筹资活动现金流出小计     9225+9226+9227
	          
	          //"622","623","624"-("626","627","628")
	          setCol2Value6H1("M9229","z113","z114","a42","z115","z116","z117" ); //筹集活动产生的现金流量净额       9224-9228
	          
	          //setCol2Value("M9230",""); //汇率变动对现金及现金等价物的影响              
	          //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
	          setCol2Value23H("M9231","z100","z102","z103","b95","a22","z104","z105","z106","z107","z108","z111","a32","z109","z110","z111","z112","z113","z114","a42","z115","z116","z117"  ); //现金及现金等价物净增加额     9208+9220+9229+9230
	          
	          setCol2Value("M9232","b82"); //期初现金及现金等价物余额  ""     
	          //setCol2Value23H + b82
	          setCol2Value24H("M9233","z100","z102","z103","b95","a22","z104","z105","z106","z107","z108","z111","a32","z109","z110","z111","z112","z113","z114","a42","z115","z116","z117" ,"b82" ); //期末现金及现金等价物余额   9231+9232                   
	          setCol2Value("NETPROFIT","517"); //净利润                                  	
	          setCol2Value("PROVISIONFORASSETIMPAIRMENT","z600"); //资产减值准备                              	
	          setCol2Value("DEPRECIATIONOFFIXEDASSETS","z601"); //固定资产折旧、油气资产折耗、生产性生物资产折旧
	          setCol2Value("M9237","b60"); //无形资产摊销                             	
	          setCol2Value("M9238","b61"); //长期待摊费用摊销                           	 
	          setCol2Value("DECREASEOFDEFFEREDEXPENSES","z602"); //待摊费用减少                             	
	          setCol2Value("ADDITIONOFACCUEDEXPENSE","z603"); //预提费用增加                             	
	          setCol2Value("M9241","z604"); //处置固定资产无形资产和其他长期资产的损失      
	          setCol2Value("M9242","z605"); //固定资产报废损失                            
	          setCol2Value("M9243","z606"); //公允价值变动损失                            
	          setCol2Value("FINANCEEXPENSE","z607"); //财务费用                                	
	          setCol2Value("LOSSESARSINGFROMINVESTMENT","z608"); //投资损失                                	
	          setCol2Value("DEFERREDINCOMETAXASSETS","z609"); //递延所得税资产减少                          
	          setCol2Value("M9247","z610"); //递延所得税负债增加                          	 
	          setCol2Value("DECREASEININVENTORIES","z611"); //存货的减少                               	
	          setCol2Value("M9250","z612"); //经营性应收项目的减少                         	
	          setCol2Value("M9251","z613"); //经营性应付项目的增加                        	 
	          setCol2Value("OTHERS","z614"); //（净利润调节为经营活动现金流量科目下）其他    
	          setCol2Value2("M9252","c99","b75"); //经营活动产生的现金流量净额2                   
	          setCol2Value("DEBTSTRANSFERTOCAPITAL","b76"); //债务转为资本                              	
	          setCol2Value("ONEYEARDUECONVERTIBLEBONDS","b77"); //一年内到期的可转换公司债券                    
	          setCol2Value("M9255","b78"); //融资租入固定资产                             	
	          setCol2Value("NONCASHOTHERS","z614"); //其他（不涉及现金收支的投资和筹资活动科目下）  
	          setCol2Value("CASHATTHEENDOFPERIOD","b79"); //现金的期末余额                              
	          setCol2Value("M9258","z615"); //现金的期初余额                               
	          setCol2Value("M9259","z616"); //现金等价物的期末余额                         	
	          setCol2Value("M9260","z617"); //现金等价物的期初余额                         	
	          setCol2Value("M9261","z618"); //现金及现金等价物净增加额
        }
        rs1.close();
        ps1.close();
        conn1.close();
        //企业2002版资产负债表信息
        //setCol2Value("MainRevenuefRevenue","501");
        //setCol2Value("MainOperatingCost","502");
        
    }
    
	private void setCol2Value24H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22, String rowSubject23){
    	
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
				 //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
				//double col2Value=a1+a2+a3-(a4+a5+a6+a7);
				//double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9);
				//double col2Value=a1+a2+a3-(a4+a5+a6);
				
				double col2Value= a1+a2+a3-(a4+a5+a6+a7) +a8+a9+a10+a11+a12-(a13+a14+a15+a16) +a17+a18+a19-(a20+a21+a22)+a23  ;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void setCol2Value23H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22){
    	
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
				 //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
				//double col2Value=a1+a2+a3-(a4+a5+a6+a7);
				//double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9);
				//double col2Value=a1+a2+a3-(a4+a5+a6);
				
				double col2Value= a1+a2+a3-(a4+a5+a6+a7) +a8+a9+a10+a11+a12-(a13+a14+a15+a16) +a17+a18+a19-(a20+a21+a22)  ;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    
    //"622","623","624"-("626","627","628")
  	private void setCol2Value6H1( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6) {
      	try {
      			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double a6 = getCol2Value(rowSubject6);
				double col2Value=a1+a2+a3-(a4+a5+a6);
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
    
    //("M9220","611","612","613","614","615" - ("617","618","619","620"))
    private void setCol2Value9H1(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
					double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9);
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
    
	//"601","602","603"-( "605","606","607","608"
    private void setCol2Value7H(String item, String rowSubject1, String rowSubject2,
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
				double col2Value=a1+a2+a3-(a4+a5+a6+a7);
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
    
	private void setCol2Value3(String item, String rowSubject1, String rowSubject2,String rowSubject3) {
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


