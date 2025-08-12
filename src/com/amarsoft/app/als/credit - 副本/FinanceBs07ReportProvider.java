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

public class FinanceBs07ReportProvider extends DefaultDataSourceProvider{

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
        
        //2007资产负债表信息记录
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0291' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	 //2007资产负债表信息记录
	          setCol2Value("CURRENCYFUNDS","101"); //货币资金101
	          setCol2Value("M9101","147"); //交易性金融资产   631    
	          setCol2Value("NOTESRECEIVABLE","103"); //应收票据  103          	
	          setCol2Value("ACCOUNTSRECEIVABLE","104"); //应收账款  106          	
	          setCol2Value("PREPAYMENTS","107"); //预付账款   107         	
	          setCol2Value("INTERESTRECEIVABLE","19c"); //应收利息   19c         	
	          setCol2Value("DIVIDENDSRECEIVABLE","134"); //应收股利 134           	
	          setCol2Value("OTHERRECEIVABLES","108"); //其他应收款  108         
	          setCol2Value("INVENTORIES","110"); //存货       110        	
	          setCol2Value("M9109","114"); //一年内到期的非流动资产 632
	          setCol2Value("OTHERCURRENTASSETS","115"); //其他流动资产    115+109     
	          setCol2ValueLDZCHJ("TOTALCURRENTASSETS","101","147","103","104","107","19c","134","108","110","114","115"); //流动资产合计 //9100+9101+9102+9103+9104+9105+9106+9107+9108+9109+9110
	          
	          
	          setCol2Value("M9112","162"); //可供出售的金融资产   633
	          setCol2Value("M9113","163"); //持有至到期投资       634
	          setCol2Value("LONGTERMEQUITYINVESTMENT","116"); //长期股权投资   648      
	          setCol2Value("LONGTERMRECEIVABLES","164"); //长期应收款       635    
	          setCol2Value("INVESTMENTPROPERTIES","165"); //投资性房地产   636      
	          setCol2Value("FIXEDASSETS","142"); //固定资产    119        	
	          setCol2Value("CONSTRUCTIONINPROGRESS","120"); //在建工程 120           	
	          setCol2Value("CONSTRUCTIONMATERIALS","143"); //工程物资  127          	
	          setCol2Value("M9120","121"); //固定资产清理  121       
	          setCol2Value("M9121","166"); //生产性生物资产  637     
	          setCol2Value("OILANDGASASSETS","167"); //油气资产 638          	
	          setCol2Value("INTANGIBLEASSETS","123"); //无形资产  129          	
	          setCol2Value("DEVELOPMENTDISBURSEMENTS","168"); //开发支出130           	
	          setCol2Value("GOODWILL","169"); //商誉       131       	 
	          setCol2Value("LONGTERMDEFERREDEXPENSES","181"); //长期待摊费用       132  
	          setCol2Value("DEFERREDTAXASSETS","126"); //递延所得税资产 133     	
	          setCol2Value("OTHERNONCURRENTASSETS","170"); //其他非流动资产   639+125    
	          setCol2ValueFLDZCHJ("TOTALNONCURRENTASSETS","162","163","116","164","165","142","120","143","121","166","167","123","168","169","181","126","170"); //非流动资产合计     9112+9113+9114+9115+9116+9117+9118+9119+9120+9121+9122+9123+9124+9125+9126+9127+9128  
	         
	          setCol2ValueZCZJ("TOTALASSETS","101","147","103","104","107","19c","134","108","110","114","115","162","163","116","164","165","142","120","143","121","166","167","123","168","169","181","126","170"); //资产总计  9111+9129          	
	          
	          setCol2Value("SHORTTERMBORROWINGS","201"); //短期借款          201  	
	          setCol2Value("M9132","234"); //交易性金融负债  640     
	          setCol2Value("NOTESPAYABLE","202"); //应付票据  202          	
	          setCol2Value("ACCOUNTSPAYABLE","203"); //应付账款  203           
	          setCol2Value("RECEIPTSINADVANCE","204"); //预收账款 204           	
	          setCol2Value("INTERESTPAYABLE","274"); //应付利息274            	
	          setCol2Value("EMPLOYEEBENEFITSPAYABLE","218"); //应付职工薪酬  206       
	          setCol2Value("TAXSPAYABLE","207"); //应交税费     651       	
	          setCol2Value("DIVIDENDSPAYABLE","641"); //应付股利  641          	
	          setCol2Value("OTHERPAYABLES","208"); //其他应付款 209+210          
	          setCol2Value("M9141","211"); //一年内到期的非流动负债 643
	          setCol2Value("OTHERCURRENTLIABILITIES","212"); //其他流动负债   212    
	          setCol2ValueLDFZHJ("TOTALCURRENTLIABILITIES","201","234","202","203","204","274","218","207","641","208","221","212"); //流动负债合计 9131+9132+9133+9134+9135+9136+9137+9138+9139+9140+9141+9142        
	          
	          setCol2Value("LONGTERMBORROWINGS","213"); //长期借款213            	
	          setCol2Value("BONDSPAYABLES","214"); //应付债券    214        	
	          setCol2Value("LONGTERMPAYABLES","215"); //长期应付款    215       
	          setCol2Value("GRANTSPAYABLE","223"); //专项应付款     644      
	          setCol2Value("PROVISIONS","222"); //预计负债            	
	          setCol2Value("DEFERREDTAXLIABILITIES","217"); //递延所得税负652债       
	          setCol2Value("M9150","245"); //其他非流动负债       
	          setCol2Value7Jia("M9151","213","214","215","223","222","217","245"); //非流动负债合计   9144+9145+9146+9147+9148+9149+9150
	          
	          setCol2ValueFZHJ("TOTALLIABILITIES","201","234","202","203","204","274","218","207","641","208","221","212","213","214","215","223","222","217","245"); //负债合计            	9143+9151
	          
	          setCol2Value("M9153","301"); //实收资本（或股本）   
	          setCol2Value("CAPITALRRSERVE","302"); //资本公积   302          
	          setCol2Value("LESSTREASURYSTOCKS","309"); //减：库存股           
	          setCol2Value("SURPLUSRESERVE","303"); //盈余公积            	
	          setCol2Value("UNAPPROPRIATEDPROFIT","305"); //未分配利润           
	          setCol2ValueSYZQYHJ("TOTALEQUITY","301","302","309","303","305"); //所有者权益合计       9153+9154-9155+9156+9157
	          //setCol2ValueFZHJ+setCol2ValueSYZQYHJ
	          setCol2ValueFZHSYZQY("TOTALEQUITYANDLIABILITIES","201","234","202","203","204","274","218","207","641","208","221","212","213","214","215","223","222","217","245","301","302","309","303","305" ); //负债和所有者权益合计 9152+9158
	          
	          
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }
    




	private void setCol2ValueLDFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
				double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}


	private void setCol2ValueZCZJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
	   
					double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19+a20+a21 + a22+a23+a24+a25 +a26+a27+a28;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	private void setCol2ValueFLDZCHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
	   
					double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	private void setCol2ValueLDZCHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
				double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		
	}


	//"201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645" +,"301","302","-646","303","305","307"
    private void setCol2ValueFZHSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
					//	          //setCol2ValueFZHJ+setCol2ValueSYZQYHJ
					//double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19;
					//double col2Value=a1+a2-a3+a4+a5;
					double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19+a20+a21 - a22-a23+a24 ;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
    

  	private void setCol2ValueSYZQYHJ( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5) {
      	try {
      			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double a4 = getCol2Value(rowSubject4);
				double a5 = getCol2Value(rowSubject5);
				double col2Value=a1+a2-a3+a4+a5;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
    
    private void setCol2ValueFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18, String rowSubject19) {
    	
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
	   
					double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19;
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
					double col2Value=a1+a2+a3+a4+a5+a6+a7;
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


