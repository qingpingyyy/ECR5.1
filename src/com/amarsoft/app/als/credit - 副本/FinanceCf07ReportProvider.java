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

public class FinanceCf07ReportProvider extends DefaultDataSourceProvider{

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
        
        //��ҵ2007���ֽ�������Ϣ
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
	     	  
	     	  //��ҵ2007���ֽ�������
	          setCol2Value("M9199","z100"); //������Ʒ���ṩ�����յ����ֽ�                  
	          setCol2Value("TAXREFUNDS","z102"); //�յ���˰�ѷ���                             	 
	          setCol2Value("M9201","z103"); //�յ������뾭Ӫ��йص��ֽ�  
	          setCol2Value3("M9202","z100","z102","z103"); //��Ӫ��ֽ�����С��     9199+9200+9201                    	
	          setCol2Value("CASHPAIDFORGOODSANDSERVICES","b95"); //������Ʒ����������֧�����ֽ�                  
	          setCol2Value("M9204","a22"); //֧����ְ���Լ�Ϊְ��֧�����ֽ�                
	          setCol2Value("PAYMENTSOFALLTYPESOFTAXES","z104"); //֧���ĸ���˰��                             	 
	          setCol2Value("M9206","z105"); //֧�������뾭Ӫ��йص��ֽ�                  
	          setCol2Value4Jia("M9207","b95","a22","z104","z105"); //��Ӫ��ֽ�����С��
	          
	          //"601","602","603"-( "605","606","607","608")
	          setCol2Value7H("M9208","z100","z102","z103","b95","a22","z104","z105"); //��Ӫ��������ֽ���������1      9202-9207             
	          
	          setCol2Value("M9209","z106"); //�ջ�Ͷ�����յ����ֽ�                          
	          setCol2Value("CASHRECEIVEDFROMONVESTMENTS","z107"); //ȡ��Ͷ���������յ����ֽ�                      
	          setCol2Value("M9211","z108"); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ�
	          setCol2Value("M9212","z111"); //�����ӹ�˾������Ӫҵ��λ�յ����ֽ𾻶�        
	          setCol2Value("M9213","a32"); //�յ�������Ͷ�ʻ�йص��ֽ�                  
	          setCol2Value5Jia("M9214","z106","z107","z108","z111","a32"); //Ͷ�ʻ�ֽ�����С�� 9209+9210+9211+9212+9213      
	          
	          setCol2Value("M9215","z109"); //�����̶��ʲ������ʲ������������ʲ���֧�����ֽ�
	          setCol2Value("CASHPAYMENTSFORINVESTMENTS","z110"); //Ͷ����֧�����ֽ�                           	 
	          setCol2Value("M9217","z111"); //ȡ���ӹ�˾������Ӫҵ��λ֧�����ֽ𾻶�        
	          setCol2Value("M9218","z112"); //֧��������Ͷ�ʻ�йص��ֽ�                  
	          setCol2Value4Jia("SUBTOTALOFCASHOUTFLOWS","z109","z110","z111","z112"); //Ͷ�ʻ�ֽ�����С�� 9215+9216+9217+9218
	          
	          //("M9220","611","612","613","614","615" - ("617","618","619","620"))
	          setCol2Value9H1("M9220","z106","z107","z108","z111","a32","z109","z110","z111","z112"); //Ͷ�ʻ�������ֽ���������  9214-9219
	          
	          setCol2Value("CASHRECEIVEDFROMINVESTORS","z113"); //����Ͷ���յ����ֽ�                          	 
	          setCol2Value("CASHFROMBORROWINGS","z114"); //ȡ�ý���յ����ֽ�                          	 
	          setCol2Value("M9223","a42"); //�յ���������ʻ�йص��ֽ�                  
	          setCol2Value3("M9224","z113","z114","a42"); //���ʻ�ֽ�����С��        9221+9222+9223   
	          
	          setCol2Value("CASHREPAYMENTSFORDEBTS","z115"); //����ծ����֧�����ֽ�                        	 
	          setCol2Value("M9226","z116"); //�������������򳥸���Ϣ��֧�����ֽ�          
	          setCol2Value("M9227","z117"); //֧����������ʻ�йص��ֽ�                  
	          setCol2Value3("M9228","z115","z116","z117"); //���ʻ�ֽ�����С��     9225+9226+9227
	          
	          //"622","623","624"-("626","627","628")
	          setCol2Value6H1("M9229","z113","z114","a42","z115","z116","z117" ); //�Ｏ��������ֽ���������       9224-9228
	          
	          //setCol2Value("M9230",""); //���ʱ䶯���ֽ��ֽ�ȼ����Ӱ��              
	          //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
	          setCol2Value23H("M9231","z100","z102","z103","b95","a22","z104","z105","z106","z107","z108","z111","a32","z109","z110","z111","z112","z113","z114","a42","z115","z116","z117"  ); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�     9208+9220+9229+9230
	          
	          setCol2Value("M9232","b82"); //�ڳ��ֽ��ֽ�ȼ������  ""     
	          //setCol2Value23H + b82
	          setCol2Value24H("M9233","z100","z102","z103","b95","a22","z104","z105","z106","z107","z108","z111","a32","z109","z110","z111","z112","z113","z114","a42","z115","z116","z117" ,"b82" ); //��ĩ�ֽ��ֽ�ȼ������   9231+9232                   
	          setCol2Value("NETPROFIT","517"); //������                                  	
	          setCol2Value("PROVISIONFORASSETIMPAIRMENT","z600"); //�ʲ���ֵ׼��                              	
	          setCol2Value("DEPRECIATIONOFFIXEDASSETS","z601"); //�̶��ʲ��۾ɡ������ʲ��ۺġ������������ʲ��۾�
	          setCol2Value("M9237","b60"); //�����ʲ�̯��                             	
	          setCol2Value("M9238","b61"); //���ڴ�̯����̯��                           	 
	          setCol2Value("DECREASEOFDEFFEREDEXPENSES","z602"); //��̯���ü���                             	
	          setCol2Value("ADDITIONOFACCUEDEXPENSE","z603"); //Ԥ���������                             	
	          setCol2Value("M9241","z604"); //���ù̶��ʲ������ʲ������������ʲ�����ʧ      
	          setCol2Value("M9242","z605"); //�̶��ʲ�������ʧ                            
	          setCol2Value("M9243","z606"); //���ʼ�ֵ�䶯��ʧ                            
	          setCol2Value("FINANCEEXPENSE","z607"); //�������                                	
	          setCol2Value("LOSSESARSINGFROMINVESTMENT","z608"); //Ͷ����ʧ                                	
	          setCol2Value("DEFERREDINCOMETAXASSETS","z609"); //��������˰�ʲ�����                          
	          setCol2Value("M9247","z610"); //��������˰��ծ����                          	 
	          setCol2Value("DECREASEININVENTORIES","z611"); //����ļ���                               	
	          setCol2Value("M9250","z612"); //��Ӫ��Ӧ����Ŀ�ļ���                         	
	          setCol2Value("M9251","z613"); //��Ӫ��Ӧ����Ŀ������                        	 
	          setCol2Value("OTHERS","z614"); //�����������Ϊ��Ӫ��ֽ�������Ŀ�£�����    
	          setCol2Value2("M9252","c99","b75"); //��Ӫ��������ֽ���������2                   
	          setCol2Value("DEBTSTRANSFERTOCAPITAL","b76"); //ծ��תΪ�ʱ�                              	
	          setCol2Value("ONEYEARDUECONVERTIBLEBONDS","b77"); //һ���ڵ��ڵĿ�ת����˾ծȯ                    
	          setCol2Value("M9255","b78"); //��������̶��ʲ�                             	
	          setCol2Value("NONCASHOTHERS","z614"); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ�£�  
	          setCol2Value("CASHATTHEENDOFPERIOD","b79"); //�ֽ����ĩ���                              
	          setCol2Value("M9258","z615"); //�ֽ���ڳ����                               
	          setCol2Value("M9259","z616"); //�ֽ�ȼ������ĩ���                         	
	          setCol2Value("M9260","z617"); //�ֽ�ȼ�����ڳ����                         	
	          setCol2Value("M9261","z618"); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�
        }
        rs1.close();
        ps1.close();
        conn1.close();
        //��ҵ2002���ʲ���ծ����Ϣ
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


