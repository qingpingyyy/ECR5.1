/*
 * �ļ�����ReportProvider.java
 * ��Ȩ��Copyright by www.amarsoft.com
 * ������
 * �޸��ˣ�amarsoft
 * �޸�ʱ�䣺2018��9��7��
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�
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
    String sReportType="";//��������
    String s1="";
    ResultSet rs1 = null;
    String sReportDate = "";
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        sReportType =currentRecord.getField("SHEETTYPE").getString(); if(sReportType==null) sReportType= "" ;

        //2002������������
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0012' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
        conn1 = ARE.getDBConnection(database);
        
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
	     	  ARE.getLog().info("==============="+sReportNo);
	     	  //ps = connection.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");
	     	  s1=sReportDate.substring(0,4);//��ȡ���񱨱����
	     	  //030���걨
	          if("030".equals(sReportType)&&!"".equals(sReportDate)){
	          	if(sReportDate.compareTo(s1+"/06") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("20");//�ϰ���
	          	}else{
	          		currentRecord.getField("SHEETTYPE").setValue("30");//�°���
	          	}
	          }
	          //020����
	          if("020".equals(sReportType)&&!"".equals(sReportDate)){
	          	if(sReportDate.compareTo(s1+"/03") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("40");//��һ����
	          	}else if (sReportDate.compareTo(s1+"/06") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("50");//�ڶ�����
	          	}else if (sReportDate.compareTo(s1+"/09") <=0){
	          		currentRecord.getField("SHEETTYPE").setValue("60");//��������
	          	}else{
	          		currentRecord.getField("SHEETTYPE").setValue("70");//���ļ���
	          	}
	          }
	          //040�걨
	          if("040".equals(sReportType)&&!"".equals(sReportDate)){
	          		currentRecord.getField("SHEETTYPE").setValue("10");//�걨
	          	}
	     	  
	     	  //��ҵ2002���������������Ϣ
	          setCol2Value("MAINREVENUEFREVENUE","603"); // ��Ӫҵ������501} 9675       
	          //setCol2Value("M9677",""); // ��Ӫҵ��������ڲ�Ʒ��������""}   
	          // setCol2Value("M9679",""); // ��Ӫҵ��������ڲ�Ʒ��������""}   
	          //setCol2Value("DISCOUNTANDALLOWANCE",""); // �ۿ������""}  9681                  
	          setCol2Value("M9683","603"); // ��Ӫҵ�����뾻��9675-9681}     9683       
	          
	          setCol2Value("MAINOPERATINGCOST","440"); // ��Ӫҵ��ɱ�440}        9685          
	          //setCol2Value("SALESINCOMEOFEXPORTPRODUCTS",""); // ��Ӫҵ��ɱ����ڲ�Ʒ���۳ɱ�""}  
	          //setCol2Value("M9689",""); // ��Ӫҵ��˰�𼰸���}     9689     
	          //setCol2Value("OPERATIONEXPENSE",""); // ��Ӫ����}         9693            
	          //setCol2Value("OTHERSOPERATINGCOST",""); // ������ҵ��ɱ�""��}     9691        
	          //setCol2Value("DEFERREDINCOME",""); // ��������""}       9695              
	          //setCol2Value("INCOMEFROMSALESAGENCY",""); // ������������""}     9697            
	          //setCol2Value("OTHEROPERATINGINCOME",""); // ���������룩""}     9699            
	          setCol2Value2Jian("PRINCIPLEBUSINESSPROFIT","603","440"); // ��Ӫҵ������}  9683-9685-9689-9693-9691+9695+9697+9699       9701       
	          
	          setCol2Value("OTHERBUSINESSPROFIT","506"); // ����ҵ������506} 9703                
	          setCol2Value("SELLINGEXPENSES",""); // Ӫҵ����""}     9705                
	          setCol2Value("M9707","507"); // �������507}  9707                   
	          setCol2Value("FINANCIALEXPENSES","508"); // �������508}   9709                 
	          //setCol2Value("OTHERSEXPENSES",""); // ���������ã�}   9907          
	          setCol2ValueYELR("OPERATINGPROFITS","603","440","506","507","508"); // Ӫҵ����}     9701+9703-9705-9707-9709-9907          9711           
	          //603-440  +506   -507-508
	          
	          setCol2Value("INVESTMENTINCOME","466"); // Ͷ������510}    9713
	          setCol2Value("FUTURESINCOME","532"); // �ڻ�����532}             9715        
	          setCol2Value("ALLOWANCEINCOME","511"); // ��������511}     9717                
	          setCol2Value("M9719","477"); // �������벹��ǰ�������ҵ��������""}  9719
	          setCol2Value("NONOPERATINGINCOME","512"); // Ӫҵ������512}                      	
	          setCol2Value("M9723","488"); // Ӫҵ�����봦�ù̶��ʲ�������""}      9721
	          //setCol2Value("INCOMEFROMNONCURRENCYTRADE",""); // -Ӫҵ������ǻ����Խ�������""}       
	          //setCol2Value("M9727",""); // Ӫҵ��������������ʲ�����""}        
	          //setCol2Value("INCOMEFROMPENALTY",""); // Ӫҵ�����뷣�����""}              
	          setCol2Value("OTHERSINCOME","526"); // ����������514}                     	
	          //setCol2Value("M9731",""); // ��������ǰ��Ⱥ������ʽ����ֲ�����""}
	          setCol2Value("NONOPERATINGEXPENSES","513"); // Ӫҵ��֧��513}                     	
	          //setCol2Value("M9735",""); // Ӫҵ��֧�����ù̶��ʲ�����ʧ""}    
	          //setCol2Value("M9737",""); // Ӫҵ��֧��ծ��������ʧ}         
	          //setCol2Value("LOSSOFLAWSUITS",""); // Ӫҵ��֧������֧��}             
	          //setCol2Value("PAYMENTFORDONATION",""); // Ӫҵ��֧������֧��}             
	          setCol2Value("OTHERPAYMENTS","449"); // ����֧��}                       
	          //setCol2Value("BALANCEOFCONTENTSALARY",""); // ����֧����ת�ĺ������ʰ��ɽ���}  
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449
	          setCol2ValueLRZE("TOTALPROFIT","603","440","506","507","508","466","532","511","512","526","513","449"); // �����ܶ�}   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745              
	          
	          setCol2Value("INCOMETAX","450"); // ����˰516}                         
	          //setCol2Value("IMPARIMENTLOSS",""); // �����ɶ�����523}                   
	          //setCol2Value("UNREALIZEDINVESTMENTLOSSES",""); // δȷ�ϵ�Ͷ����ʧ""}               
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450
	          setCol2ValueJLR("NETPROFIT","603","440","506","507","508","466","532","511","512","526","513","449","450"); // ������} 9747-9749-9751+9753                        
	          
	          
	          setCol2Value("M9757","589"); // ���δ��������""}                 
	          setCol2Value("COMPENSATIONOFSURPLUSRESERVE","590"); // ӯ�๫������""}                   
	          setCol2Value("OTHERADJUSTMENTFACTORS","591"); // ������������""}                   
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591     
	          setCol2ValueKGFPLR("PROFITAVAILABLEFORDISTRIBUTION","603","440","506","507","508","466","532","511","512","526","513","449","450" ,"589","590","591" ); // �ɹ����������}     9755+9757+9759+9761            
	          
	          //setCol2Value("PROFITRESERVEDFORASINGLEITEM",""); // �������õ�����""}                 
	          setCol2Value("SUPPLEMENTARYCURRENTCAPITAL","593"); // ���������ʱ�""}                   
	          setCol2Value("M9769","594"); // ��ȡ����ӯ�๫��""}               
	          setCol2Value("M9771","595"); // ��ȡ���������""}                 
	          setCol2Value("M9773","596"); // ��ȡְ����������������""}         
	          setCol2Value("APPROPRIATIONOFRESERVEFUND","587"); // ��ȡ��������""}                   
	          setCol2Value("M9777","598"); // ��ȡ��ҵ��չ����""}               
	          setCol2Value("M9779","599"); // ����黹Ͷ��""}                   
	          setCol2Value("OTHERS4","441"); // �������ɹ�����������Ŀ�£�""}   
	          
	          //603-440  +506   -507-508   +466+ 532+511 +512+526      -513 -449       -450       +589 +590+591    -593 -594 -595 -596 -587  -598  -599 -441
	          setCol2ValueKGTZZFPLR("M9781","603","440","506","507","508","466","532","511","512","526","513","449","450","589","590","591","593","594","595","596","587","598","599","441"); // �ɹ�Ͷ���߷��������} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
	          
	          setCol2Value("PREFERREDSTOCKDIVIDENDSPAYABLE","585"); // Ӧ�����ȹɹ���""}                 
	          setCol2Value("M9785","586"); // ��ȡ����ӯ�๫��""}               
	          setCol2Value("PAYABLEDIVIDENDSOFCOMMONSTOCK","572"); // Ӧ����ͨ�ɹ���""}                 
	          setCol2Value("M9789","452"); // ת���ʱ�����ͨ�ɹ���""}           
	          setCol2Value("OTHERS5","453"); // �������ɹ�Ͷ���߷���������Ŀ��""}   
	        //603-440  +506   -507-508   +466+ 532+511 +512+526 -513 -449-450+589 +590+591-593 -594 -595 -596 -587  -598  -599 -441  -585 -586 -572-452-453            
	          setCol2ValueWFPLR("UNAPPROPRIATEDPROFIT","603","440","506","507","508","466","532","511","512","526","513","449","450","589","590","591","593","594","595","596","587","598","599","441","585","586","572","452","453"); // δ��������}     9781-9783-9785-9787-9789-9913          
	          //setCol2Value("LOSSCOMPENSATEDBEFORETHETAX",""); // δ��������Ӧ���Ժ����˰ǰ�����ֲ��Ŀ���""} 
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
     * �ر����ݿ���Դ */
    
    public void close() throws RecordSetException {
        if (ps != null) {
            try {
                ps.close();
                ps = null;
                logger.debug("�ر�preparedstatement�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�preparedstatement����", e);
            }
        }
        if (conn1 != null) {
            try {
            	conn1.close();
            	conn1 = null;
                logger.debug("�ر�connection�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�connection����", e);
            }
        }
        super.close();
    }

}


