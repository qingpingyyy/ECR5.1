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

public class FinanceCf02ReportProvider extends DefaultDataSourceProvider{

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

        //2002���ֽ�������Ϣ
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
	     	  
	     	  
	     	  //2002���ֽ�������Ϣ
	          //setCol2Value("M9795",""); //������Ʒ���ṩ�����յ����ֽ�a01}   
	          //setCol2Value2("TAXREFUNDS","",""); //�յ���˰�ѷ���a17+a18}         
	          setCol2Value("M9799","b94"); //�յ��������뾭Ӫ��йص��ֽ�a16+a19} 
	          
	          setCol2Value("M9823","b94"); //��Ӫ��ֽ�����С�� 9795+9797+9799} 9823
	          
	          setCol2Value("CASHPAIDFORGOODSANDSERVICES","b95"); //������Ʒ����������֧�����ֽ�a02}   
	          setCol2Value("M9805","a22"); //֧����ְ���Լ�Ϊְ��֧�����ֽ�a22}  
	          setCol2Value3Jia("PAYMENTSOFALLTYPESOFTAXES","b112","b114","a49"); //֧���ĸ���˰��a23+a24+a25}         
	          setCol2Value2Jia("M9809","a19","a26"); //֧���������뾭Ӫ��йص��ֽ�a03+a26} 
	          
	          //
	          setCol2Value7Jia("M9831","b95","a22","b112","b114","a49","a19","a26"); //��Ӫ��ֽ�����С��} 9803+9805+9807+9809  
	          
	          setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //��Ӫ��������ֽ��������� }     9823-9831
	          
	          setCol2Value("M9815","a28"); //�ջ�Ͷ�����յ����ֽ�a28}   
	          setCol2Value("CASHRECEIVEDFROMONVESTMENTS","b100"); //ȡ��Ͷ���������յ����ֽ�a30}      
	          setCol2Value("M9819","b101"); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ𾻶�a31}
	          
	          //b119+b59+c98
	          setCol2Value3Jia("M9821","b119","b59","c98"); //�յ���������Ͷ�ʻ�йص��ֽ�a29+a32}        
	          
	          setCol2Value6Jia("M9917","a28","b100","b101","b119","b59","c98"); //Ͷ�ʻ�ֽ�����С��}    9815+9817+9819+9821
	                 
	          //setCol2Value("M9825",""); //�����̶��ʲ������ʲ������������ʲ���֧�����ֽ�a34}    
	          //"b67+b120
	          setCol2Value2Jia("CASHPAYMENTSFORINVESTMENTS","b67","b120"); //Ͷ����֧�����ֽ�}     a35+a36  
	          
	          setCol2Value("M9829","a37"); //֧����������Ͷ�ʻ�йص��ֽ�a37}  
	          
	          setCol2Value3Jia("M9919","b67","b120","a37"); //Ͷ�ʻ�ֽ�����С��}        9825+9827+9829
	          
	          setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //Ͷ�ʻ�������ֽ���������}    9917-9919
	          
	          setCol2Value("CASHRECEIVEDFROMINVESTORS","b109"); //����Ͷ�����յ����ֽ�a39}          
	          setCol2Value("CASHFROMBORROWINGS","a41"); //������յ����ֽ�a41}       
	          setCol2Value2Jia("M9839","b71","b72"); //�յ�����������ʻ�йص��ֽ�a40+a42}  
	          
	          setCol2Value4Jia("M9921","b109","a41","b71","b72"); //���ʻ�ֽ�����С��} 9835+9837+9839
	          
	          setCol2Value("CASHREPAYMENTSFORDEBTS","a44"); //����ծ����֧�����ֽ�a44}        
	          setCol2Value("M9845","a46"); //�������������򳥸���Ϣ��֧�����ֽ�a46}           
	          setCol2Value4Jia("M9847","a45","a47","a48","a49"); //֧������������ʻ�йص��ֽ�a45+a47+a48+a49}  
	          
	          
	          setCol2Value6Jia("M9923","a44","a46","a45","a47","a48","a49"); //���ʻ�ֽ�����С��}   9843+9845+9847
	          
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
	          setCol2Value10("M9851","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49" ); //�Ｏ��������ֽ���������}  9921-9923
	          
	          setCol2Value("M9853","a51"); //���ʱ䶯���ֽ��Ӱ��a51}   
	          //value10 +a51  +setCol2ValueLDZJJE +setCol2ValueTZHDCSXJLLJE
	          setCol2ValueXJDJWJZJE("M9855","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51" ,"b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37" ); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�1}   9813+9833+9851+9853

	          //setCol2Value("NETPROFIT",""); //������z85}              
	          //setCol2Value("PROVISIONFORASSETS",""); //������ʲ���ֵ׼��""}    
	          //setCol2Value("DEPRECIATIONOFFIXEDASSETS",""); //�̶��ʲ����z87}           
	          //setCol2Value("M9863",""); //�����ʲ�̯��z88}          
	          //setCol2Value("M9865",""); //���ڴ�̯����̯��z78}      
	          //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //��̯���ü���""}           
	          //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //Ԥ���������""}           
	          //setCol2Value("M9871",""); //���ù̶��ʲ������ʲ������������ʲ�����ʧz89}         	
	          //setCol2Value("M9873",""); //�̶��ʲ�������ʧz90}      
	          //setCol2Value("FINANCEEXPENSE",""); //�������z91}             
	          //setCol2Value("LOSSESARSINGFROMINVESTMENT",""); //Ͷ����ʧ}z92              
	          //setCol2Value("DEFERREDTAXCREDIT",""); //����˰�����z93}           
	          //setCol2Value("DECREASEININVENTORIES",""); //����ļ���z94}            
	          //setCol2Value("M9883",""); //��Ӫ��Ӧ����Ŀ�ļ���z95}         
	          //setCol2Value("M9885",""); //��Ӫ��Ӧ����Ŀ������z96}          
	          setCol2ValueLDZJJE("OTHERS1","b94","b95","a22","b112","b114","a49","a19","a26" ); //���������������Ϊ��Ӫ��ֽ�������Ŀ�£�z98}       	
	          //setCol2Value("M1813",""); //��Ӫ��������ֽ���������1z99}    
	          //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //ծ��תΪ�ʱ�""}          
	          //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //һ���ڵ��ڵĿ�ת����˾ծȯ""}               		 
	          //setCol2Value("M9895",""); //��������̶��ʲ�""}      
	          //setCol2Value("OTHERS2",""); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ��""}       	
	          
	          
	          
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" setCol2Value10
	          
	          setCol2ValueXJDQMYE("CASHATTHEENDOFPERIOD","b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51"); //�ֽ����ĩ���        9851+9853
	          //setCol2Value("M9901",""); //�ֽ���ڳ����z102}      
	          
	          // setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //��Ӫ��������ֽ��������� }     9823-9831  9813
	          //setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //Ͷ�ʻ�������ֽ���������}    9917-9919
	          
	          setCol2ValueXJDJWQMYE("M9903", "b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37" ); //�ֽ�ȼ������ĩ���z103}   9813+9833
	          //setCol2Value("M9905","z104"); //�ֽ�ȼ�����ڳ����z104}   
	          //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" + "a51"'    +'"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"' ,+'"a51"')
	          setCol2ValueXJDJWJZJE("M1855", "b109","a41","b71","b72"  ,"a44","a46","a45","a47","a48","a49"  ,"a51" ,"b94","b95","a22","b112","b114","a49","a19","a26" ,"a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�2} 9851+9853+9813+9833
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

				// setCol2ValueLDZJJE("M9813","b94","b95","a22","b112","b114","a49","a19","a26" ); //��Ӫ��������ֽ��������� }     9823-9831  9813
		        //setCol2ValueTZHDCSXJLLJE("M9833","a28","b100","b101","b119","b59","c98"   ,"b67","b120","a37"); //Ͷ�ʻ�������ֽ���������}    9917-9919
				
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
                logger.debug("�ر�conn1�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�conn1����", e);
            }
        }
        super.close();
    }

}


