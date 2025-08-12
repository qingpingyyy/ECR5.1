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

public class FinanceBsInReportProvider extends DefaultDataSourceProvider{

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

        //��ҵ��λ�ʲ���ծ��Ϣ
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0021' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	  
	     	 //��ҵ��λ�ʲ���ծ
	          setCol2Value("CURRENCYFUNDS",""); //�����ʽ�     ����
	          setCol2Value("SHORTTERMINVESTMENTS",""); //����Ͷ��    ??    
	          setCol2Value("M9408",""); //����Ӧ�������  ����
	          setCol2Value("NOTESRECEIVABLE","103"); //Ӧ��Ʊ�� 103       
	          setCol2Value("ACCOUNTSRECEIVABLE","104"); //Ӧ���˿�19f        
	          setCol2Value("PREPAYMENTS","z631"); //Ԥ���˿�     19n   
	          setCol2Value("OTHERRECEIVABLES","108"); //����Ӧ�տ�   19n   
	          setCol2Value("INVENTORIES",""); //���          	
	          setCol2Value("OTHERCURRENTASSETS",""); //���������ʲ�    ???
	          setCol2Value("TOTALCURRENTASSETS",""); //�����ʲ��ϼ�   ??? 
	          setCol2Value("LONGTERMINVESTMENT",""); //����Ͷ��        
	          setCol2Value("FIXEDASSETS","703"); //�̶��ʲ�        
	          setCol2Value("M9407",""); //�̶��ʲ�ԭ��    ??
	          setCol2Value("M9401",""); //�ۼ��۾�      ??  
	          setCol2Value("CONSTRUCTIONINPROCESS",""); //�ڽ�����      ??  
	          setCol2Value("INTANGIBLEASSETS","123"); //�����ʲ�      123+120+19g  
	          setCol2Value("M9402",""); //�����ʲ�ԭ��   ?? 
	          setCol2Value("ACCUMULATEDAMORTIZATION",""); //�ۼ�̯��       	
	          setCol2Value("M9403",""); //�������ʲ�����  ??
	          setCol2Value("TOTALNONCURRENTASSETS",""); //�������ʲ��ϼ�  ??
	          setCol2Value("TOTALASSETS",""); //�ʲ��ܼ�       	
	          setCol2Value("SHORTTERMBORROWINGS",""); //���ڽ��       ??	
	          setCol2Value("TAXPAYABLE",""); //Ӧ��˰��       ??	
	          setCol2Value("TREASURYPAYABLE",""); //Ӧ�ɹ����      ??
	          setCol2Value("M9404",""); //Ӧ�ɲ���ר����  ??
	          setCol2Value("EMPLOYEEBENEFITSPAYABLE",""); //Ӧ��ְ��н��    ??
	          
	          setCol2Value("NOTESPAYABLE","202"); //Ӧ��Ʊ��       	
	          setCol2Value("ACCOUNTSPAYABLE","203"); //Ӧ���˿�       	
	          setCol2Value("RECEIPTSINADVANCE","94750"); //Ԥ���˿�       	
	          setCol2Value("OTHERPAYABLES","209"); //����Ӧ����      
	          setCol2Value("OTHERCURRENTLIABILITIES",""); //����������ծ    ??
	          setCol2Value("TOTALCURRENTLIABILITIES",""); //������ծ�ϼ�    ??
	          setCol2Value("LONGTERMBORROWINGS",""); //���ڽ��       	??
	          setCol2Value("LONGTERMPAYABLES",""); //����Ӧ����      ??
	          setCol2Value("M9405",""); //��������ծ�ϼ�  ??
	          setCol2Value4Jia("TOTALLIABILITIES","202","203","94750","209"); //��ծ�ϼ�     9295+9296+9297+9298+9299+9300+9301+9302
	          
	          setCol2Value("ENTERPRISEFUND","319"); //��ҵ����       	
	          setCol2Value("NONCURRENTASSETSFUND",""); //�������ʲ�����  ??
	          setCol2Value("SPECIALPURPOSEFUNDS",""); //ר�û���       	??
	          setCol2Value("FINANCIALAIDCARRIEDOVER",""); //����������ת    
	          setCol2Value("FINANCIALAIDBALANCE",""); //������������    
	          setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //�ǲ���������ת  
	          setCol2Value("NONFINANCIALAIDBALANCE",""); //�ǲ�����������  
	          setCol2Value("UNDERTAKINGSBALANCE","322"); //��ҵ����        
	          setCol2Value("OPERATINGBALANCE","19x"); //��Ӫ����       19x+277	
	          setCol2Value2("TOTALNETASSETS","322","19x"); //���ʲ��ϼ�    9304+9306+9307+9308+9309+9310  
	          setCol2Value("M9406",""); //��ծ�;��ʲ��ܼ�
	          
	          
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }
    
    
	private void setCol2Value4Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) {
    	try {
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


