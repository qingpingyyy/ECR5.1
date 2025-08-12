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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
    String database = "ecr";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate = "";
    BigDecimal b1 ;
    String sSql="";
    ResultSet rs = null;
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;

        //��ҵ��λ����֧����Ϣ
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0022' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
        //ARE.getLog().info("----------sCustomerID ->"+sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
        	sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo =""; 
	     	  
       	 Map m = new HashMap();
	     	 sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
	     	 ps=conn1.prepareStatement(sSql);
	     	 rs= this.ps.executeQuery();
	     	 while(rs.next()){
	     		  if(!m.containsKey(rs.getString("rowSubject")))//������ظ���rowsubject������
	     		  {
	     			 m.put(rs.getString("rowSubject"), rs.getDouble("col2Value"));  
	     		  }else{
	     			  continue;
	     		  }
	     		  
	     	  }
	     	  rs.close();
	     	  ps.close();
       	 
	     	//��ҵ��λ����֧��
	          //currentRecord.getField("M9501").setValue(m.get("").toString()); //���ڲ���������ת����    û��   ������
	          currentRecord.getField("FINANCIALSUBSIDYREVENUE").setValue(m.get("559").toString()); //������������       559
	          currentRecord.getField("M9345").setValue(m.get("z679").toString()); //��ҵ֧������������֧����              ��������֧��
	          //currentRecord.getField("M9502").setValue(m.get("").toString()); //������ҵ��ת����      9336-9350 ��ҵ����    
	          //currentRecord.getField("UNDERTAKINGSCLASSREVENUE").setValue(m.get("").toString()); //��ҵ������   ����        
	          //currentRecord.getField("UNDERTAKINGSREVENUE").setValue(m.get("").toString()); //��ҵ����                
	          currentRecord.getField("SUPERIORSUBSIDYREVENUE").setValue(m.get("560").toString()); //�ϼ���������    560         
	          currentRecord.getField("M9503").setValue(m.get("565").toString()); //������λ�Ͻ�����          	
	          currentRecord.getField("OTHERREVENUE").setValue(m.get("526").toString()); //��������               
	          //currentRecord.getField("DONATIONINCOME").setValue(m.get("").toString()); //�����������Ŀ�£��������� ������
	          //currentRecord.getField("UNDERTAKINGSCLASSEXPENDITURE").setValue(m.get("").toString()); //��ҵ��֧��              	 
	          //currentRecord.getField("M9505").setValue(m.get("").toString()); //��ҵ֧�����ǲ�������֧����      ����������
	          currentRecord.getField("PAYMENTTOTHEHIGHERAUTHORITY").setValue(m.get("556").toString()); //�Ͻ��ϼ�֧��             	 
	          currentRecord.getField("M9508").setValue(m.get("557").toString()); //�Ը�����λ����֧��        	
	          //currentRecord.getField("OTHEREXPENDITURE").setValue(m.get("").toString()); //����֧��        ��������       	 
	          //currentRecord.getField("CURRENTOPERATINGBALANCE").setValue(m.get("").toString()); //���ھ�Ӫ����            ������	 
	          currentRecord.getField("OPERATINGREVENUE").setValue(m.get("564").toString()); //��Ӫ����               
	          currentRecord.getField("OPERATINGEXPENDITURE").setValue(m.get("552").toString()); //��Ӫ֧��               	 
	          //currentRecord.getField("M9506").setValue(m.get("").toString()); //�ֲ���ǰ��ȿ����ľ�Ӫ����} ��ǰ��Ⱦ�Ӫ����
	          //currentRecord.getField("M9507").setValue(m.get("").toString()); //����ǲ���������ת����     ����
	          //currentRecord.getField("NONFINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //�ǲ���������ת            ����	
	          //currentRecord.getField("NONFINANCIALAIDBALANCETHISYEAR").setValue(m.get("").toString()); //����ǲ�����������        	
	          //currentRecord.getField("ENTERPRISEINCOMETAXPAYABLE").setValue(m.get("").toString()); //Ӧ����ҵ����˰         
	          //currentRecord.getField("SPECIALFUNDSTOEXTRACT").setValue(m.get("").toString()); //��ȡר�û���             	 
	          //currentRecord.getField("PUBLICFUNDTURNEDINTO").setValue(m.get("").toString()); //ת����ҵ����
	          
     	   
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


