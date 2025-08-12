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

public class FinanceCfInReportProvider extends DefaultDataSourceProvider{

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

        //��ҵ��λ����֧����Ϣ
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0022' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        ARE.getLog().info("----------sCustomerID ->"+sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
	     	  ARE.getLog().info("==============="+sReportNo);
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
	     	  //��ҵ��λ����֧����Ϣ
	     	//��ҵ��λ����֧��
	          setCol2Value("M9501",""); //���ڲ���������ת����    û��   ������
	          setCol2Value("FINANCIALSUBSIDYREVENUE","559"); //������������       559
	          setCol2Value("M9345","z679"); //��ҵ֧������������֧����              ��������֧��
	          setCol2Value("M9502",""); //������ҵ��ת����      9336-9350 ��ҵ����    
	          setCol2Value("UNDERTAKINGSCLASSREVENUE",""); //��ҵ������   ����        
	          setCol2Value("UNDERTAKINGSREVENUE",""); //��ҵ����                
	          setCol2Value("SUPERIORSUBSIDYREVENUE","560"); //�ϼ���������    560         
	          setCol2Value("M9503","565"); //������λ�Ͻ�����          	
	          setCol2Value("OTHERREVENUE","526"); //��������               
	          setCol2Value("DONATIONINCOME",""); //�����������Ŀ�£��������� ������
	          setCol2Value("UNDERTAKINGSCLASSEXPENDITURE",""); //��ҵ��֧��              	 
	          setCol2Value("M9505",""); //��ҵ֧�����ǲ�������֧����      ����������
	          setCol2Value("PAYMENTTOTHEHIGHERAUTHORITY","556"); //�Ͻ��ϼ�֧��             	 
	          setCol2Value("M9508","557"); //�Ը�����λ����֧��        	
	          setCol2Value("OTHEREXPENDITURE",""); //����֧��        ��������       	 
	          setCol2Value("CURRENTOPERATINGBALANCE",""); //���ھ�Ӫ����            ������	 
	          setCol2Value("OPERATINGREVENUE","564"); //��Ӫ����               
	          setCol2Value("OPERATINGEXPENDITURE","552"); //��Ӫ֧��               	 
	          setCol2Value("M9506",""); //�ֲ���ǰ��ȿ����ľ�Ӫ����} ��ǰ��Ⱦ�Ӫ����
	          setCol2Value("M9507",""); //����ǲ���������ת����     ����
	          setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //�ǲ���������ת            ����	
	          setCol2Value("NONFINANCIALAIDBALANCETHISYEAR",""); //����ǲ�����������        	
	          setCol2Value("ENTERPRISEINCOMETAXPAYABLE",""); //Ӧ����ҵ����˰         
	          setCol2Value("SPECIALFUNDSTOEXTRACT",""); //��ȡר�û���             	 
	          setCol2Value("PUBLICFUNDTURNEDINTO",""); //ת����ҵ����
	          
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
        //��ҵ2002���ʲ���ծ����Ϣ
        //setCol2Value("MainRevenuefRevenue","501");
        //setCol2Value("MainOperatingCost","502");
        
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
                logger.debug("�ر�conn1�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�conn1����", e);
            }
        }
        super.close();
    }

}


