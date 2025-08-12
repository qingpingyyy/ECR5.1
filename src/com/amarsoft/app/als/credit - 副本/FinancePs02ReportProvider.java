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

public class FinancePs02ReportProvider extends DefaultDataSourceProvider{

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

        //2002利润及利润分配表
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0012' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
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
	     	  
	     	  //企业2002版利润及利润分配信息
	          setCol2Value("MAINREVENUEFREVENUE","603"); // 主营业务收入501} 9675       
	          //setCol2Value("M9677",""); // 主营业务收入出口产品销售收入""}   
	          // setCol2Value("M9679",""); // 主营业务收入进口产品销售收入""}   
	          //setCol2Value("DISCOUNTANDALLOWANCE",""); // 折扣与拆让""}  9681                  
	          setCol2Value("M9683","603"); // 主营业务收入净额9675-9681}     9683       
	          
	          setCol2Value("MAINOPERATINGCOST","440"); // 主营业务成本440}        9685          
	          //setCol2Value("SALESINCOMEOFEXPORTPRODUCTS",""); // 主营业务成本出口产品销售成本""}  
	          //setCol2Value("M9689",""); // 主营业务税金及附加}     9689     
	          //setCol2Value("OPERATIONEXPENSE",""); // 经营费用}         9693            
	          //setCol2Value("OTHERSOPERATINGCOST",""); // 其他（业务成本""）}     9691        
	          //setCol2Value("DEFERREDINCOME",""); // 递延收益""}       9695              
	          //setCol2Value("INCOMEFROMSALESAGENCY",""); // 代购代销收入""}     9697            
	          //setCol2Value("OTHEROPERATINGINCOME",""); // 其他（收入）""}     9699            
	          setCol2Value2Jian("PRINCIPLEBUSINESSPROFIT","603","440"); // 主营业务利润}  9683-9685-9689-9693-9691+9695+9697+9699       9701       
	          
	          setCol2Value("OTHERBUSINESSPROFIT","506"); // 其他业务利润506} 9703                
	          setCol2Value("SELLINGEXPENSES",""); // 营业费用""}     9705                
	          setCol2Value("M9707","507"); // 管理费用507}  9707                   
	          setCol2Value("FINANCIALEXPENSES","508"); // 财务费用508}   9709                 
	          //setCol2Value("OTHERSEXPENSES",""); // 其他（费用）}   9907          
	          setCol2ValueYELR("OPERATINGPROFITS","603","440","506","507","508"); // 营业利润}     9701+9703-9705-9707-9709-9907          9711           
	          //603-440  +506   -507-508
	          
	          setCol2Value("INVESTMENTINCOME","466"); // 投资收益510}    9713
	          setCol2Value("FUTURESINCOME","532"); // 期货收益532}             9715        
	          setCol2Value("ALLOWANCEINCOME","511"); // 补贴收入511}     9717                
	          setCol2Value("M9719","477"); // 补贴收入补贴前亏损的企业补贴收入""}  9719
	          setCol2Value("NONOPERATINGINCOME","512"); // 营业外收入512}                      	
	          setCol2Value("M9723","488"); // 营业外收入处置固定资产净收益""}      9721
	          //setCol2Value("INCOMEFROMNONCURRENCYTRADE",""); // -营业外收入非货币性交易收益""}       
	          //setCol2Value("M9727",""); // 营业外收入出售无形资产收益""}        
	          //setCol2Value("INCOMEFROMPENALTY",""); // 营业外收入罚款净收入""}              
	          setCol2Value("OTHERSINCOME","526"); // 其他（利润）514}                     	
	          //setCol2Value("M9731",""); // 其他用以前年度含量工资节余弥补利润""}
	          setCol2Value("NONOPERATINGEXPENSES","513"); // 营业外支出513}                     	
	          //setCol2Value("M9735",""); // 营业外支出处置固定资产净损失""}    
	          //setCol2Value("M9737",""); // 营业外支出债务重组损失}         
	          //setCol2Value("LOSSOFLAWSUITS",""); // 营业外支出罚款支出}             
	          //setCol2Value("PAYMENTFORDONATION",""); // 营业外支出捐赠支出}             
	          setCol2Value("OTHERPAYMENTS","449"); // 其他支出}                       
	          //setCol2Value("BALANCEOFCONTENTSALARY",""); // 其他支出结转的含量工资包干节余}  
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449
	          setCol2ValueLRZE("TOTALPROFIT","603","440","506","507","508","466","532","511","512","526","513","449"); // 利润总额}   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745              
	          
	          setCol2Value("INCOMETAX","450"); // 所得税516}                         
	          //setCol2Value("IMPARIMENTLOSS",""); // 少数股东损益523}                   
	          //setCol2Value("UNREALIZEDINVESTMENTLOSSES",""); // 未确认的投资损失""}               
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450
	          setCol2ValueJLR("NETPROFIT","603","440","506","507","508","466","532","511","512","526","513","449","450"); // 净利润} 9747-9749-9751+9753                        
	          
	          
	          setCol2Value("M9757","589"); // 年初未分配利润""}                 
	          setCol2Value("COMPENSATIONOFSURPLUSRESERVE","590"); // 盈余公积补亏""}                   
	          setCol2Value("OTHERADJUSTMENTFACTORS","591"); // 其他调整因素""}                   
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591     
	          setCol2ValueKGFPLR("PROFITAVAILABLEFORDISTRIBUTION","603","440","506","507","508","466","532","511","512","526","513","449","450" ,"589","590","591" ); // 可供分配的利润}     9755+9757+9759+9761            
	          
	          //setCol2Value("PROFITRESERVEDFORASINGLEITEM",""); // 单项留用的利润""}                 
	          setCol2Value("SUPPLEMENTARYCURRENTCAPITAL","593"); // 补充流动资本""}                   
	          setCol2Value("M9769","594"); // 提取法定盈余公积""}               
	          setCol2Value("M9771","595"); // 提取法定公益金""}                 
	          setCol2Value("M9773","596"); // 提取职工奖励及福利基金""}         
	          setCol2Value("APPROPRIATIONOFRESERVEFUND","587"); // 提取储备基金""}                   
	          setCol2Value("M9777","598"); // 提取企业发展基金""}               
	          setCol2Value("M9779","599"); // 利润归还投资""}                   
	          setCol2Value("OTHERS4","441"); // 其他（可供分配的利润科目下）""}   
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591    -593 -594 -595 -596 -587  -598  -599 -441
	          setCol2ValueKGTZZFPLR("M9781","603","440","506","507","508","466","532","511","512","526","513","449","450","589","590","591","593","594","595","596","587","598","599","441"); // 可供投资者分配的利润} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
	          
	          setCol2Value("PREFERREDSTOCKDIVIDENDSPAYABLE","585"); // 应付优先股股利""}                 
	          setCol2Value("M9785","586"); // 提取任意盈余公积""}               
	          setCol2Value("PAYABLEDIVIDENDSOFCOMMONSTOCK","572"); // 应付普通股股利""}                 
	          setCol2Value("M9789","452"); // 转作资本的普通股股利""}           
	          setCol2Value("OTHERS5","453"); // 其他（可供投资者分配的利润科目下""}   
	        //603-440  +506   -507-508   +466+ 532+511 +512+526 -513 -449-450+589 +590+591-593 -594 -595 -596 -587  -598  -599 -441  -585 -586 -572-452-453            
	          setCol2ValueWFPLR("UNAPPROPRIATEDPROFIT","603","440","506","507","508","466","532","511","512","526","513","449","450","589","590","591","593","594","595","596","587","598","599","441","585","586","572","452","453"); // 未分配利润}     9781-9783-9785-9787-9789-9913          
	          //setCol2Value("LOSSCOMPENSATEDBEFORETHETAX",""); // 未分配利润应由以后年度税前利润弥补的亏损""} 
        }
        rs1.close();
        ps1.close();
        conn1.close();        
    }
    
    
	private void setCol2ValueWFPLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
				//603-440  +506   -507-508   +466+ 532+511 +512+526 -513 -449-450+589 +590+591-593 -594 -595 -596 -587  -598  -599 -441  -585 -586 -572-452-453
				double col2Value=a1-a2+a3-a4-a5+a6+a7+a8+a9+a10 -a11-a12-a13   +a14+a15+a16 - a17-a18  -a19-a20-a21-a22-a23-a24   -a25-a26  -a27-a28-a29;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}



	private void setCol2ValueKGTZZFPLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24) {
		
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
				//603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591    -593 -594 -595 -596 -587  -598  -599 -441
				double col2Value=a1-a2+a3-a4-a5+a6+a7+a8+a9+a10 -a11-a12-a13   +a14+a15+a16 - a17-a18  -a19-a20-a21-a22-a23-a24  ;
				currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	private void setCol2ValueKGFPLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14, String rowSubject15, String rowSubject16) {
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
				
				//603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591     
				double col2Value=a1-a2+a3-a4-a5+a6+a7+a8+a9+a10-a11-a12-a13  +a14+a15+a16;
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
    
	
    private void setCol2ValueJLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13) {
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
						//603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450
						double col2Value=a1-a2+a3-a4-a5+a6+a7+a8+a9+a10-a11-a12-a13;
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		
	}
    
    private void setCol2ValueLRZE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12) {
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
						//603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449
						double col2Value=a1-a2+a3-a4-a5+a6+a7+a8+a9+a10-a11-a12;
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
    
    //603-440  +506   -507-508
    private void setCol2ValueYELR( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5) {
    	try {
    			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);

				double col2Value=a1-a2+a3-a4-a5;
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
                logger.debug("关闭connection成功");
            } catch (SQLException e) {
                logger.debug("关闭connection出错", e);
            }
        }
        super.close();
    }

}


