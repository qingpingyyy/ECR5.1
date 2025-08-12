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

public class FinanceBsInReportProvider extends DefaultDataSourceProvider{

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
    
    public void fillRecord(){
        try {
			super.fillRecord();
			 sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
		        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;

		        //��ҵ��λ�ʲ���ծ��Ϣ
		        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo 
										from report_record rr ,report_data rd 
										where rr.reportno =rd.reportno  
										and rr.modelno ='0021' 
										and rr.reportscope <>'03' 
										and rr.objectno =? 
										and rr.REPORTDATE  ='"+sReportDate+"'";
		        conn1 = ARE.getDBConnection(database);
		        sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
		    	ps=conn1.prepareStatement(sSql);
		        ps1=conn1.prepareStatement(sQueryReportNo);
		        ps1.setString(1, sCustomerID);
		        rs1= this.ps1.executeQuery();
		        while(rs1.next()){
		        	 sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo =""; 
		       	 	 Map m = new HashMap();
		       	 	 ps.clearParameters();
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
		       	 
			     	  
						//��ҵ��λ�ʲ���ծ
						//��ҵ��λ�ʲ���ծ
			          //currentRecord.getField("CURRENCYFUNDS").setValue(m.get("").toString()); //�����ʽ�     ����
			          //currentRecord.getField("SHORTTERMINVESTMENTS").setValue(m.get("").toString()); //����Ͷ��    ??    
			          //currentRecord.getField("M9408").setValue(m.get("").toString()); //����Ӧ�������  ����
			          currentRecord.getField("NOTESRECEIVABLE").setValue(m.get("103").toString()); //Ӧ��Ʊ�� 103       
			          currentRecord.getField("ACCOUNTSRECEIVABLE").setValue(m.get("104").toString()); //Ӧ���˿�19f        
			          currentRecord.getField("PREPAYMENTS").setValue(m.get("z631").toString()); //Ԥ���˿�     19n   
			          currentRecord.getField("OTHERRECEIVABLES").setValue(m.get("108").toString()); //����Ӧ�տ�   19n   
			          //currentRecord.getField("INVENTORIES").setValue(m.get("").toString()); //���          	
			          //currentRecord.getField("OTHERCURRENTASSETS").setValue(m.get("").toString()); //���������ʲ�    ???
			          //currentRecord.getField("TOTALCURRENTASSETS").setValue(m.get("").toString()); //�����ʲ��ϼ�   ??? 
			          //currentRecord.getField("LONGTERMINVESTMENT").setValue(m.get("").toString()); //����Ͷ��        
			          currentRecord.getField("FIXEDASSETS").setValue(m.get("703").toString()); //�̶��ʲ�        
			          //currentRecord.getField("M9407").setValue(m.get("").toString()); //�̶��ʲ�ԭ��    ??
			          //currentRecord.getField("M9401").setValue(m.get("").toString()); //�ۼ��۾�      ??  
			          //currentRecord.getField("CONSTRUCTIONINPROCESS").setValue(m.get("").toString()); //�ڽ�����      ??  
			          currentRecord.getField("INTANGIBLEASSETS").setValue(m.get("123").toString()); //�����ʲ�      123+120+19g  
			          //currentRecord.getField("M9402").setValue(m.get("").toString()); //�����ʲ�ԭ��   ?? 
			          //currentRecord.getField("ACCUMULATEDAMORTIZATION").setValue(m.get("").toString()); //�ۼ�̯��       	
			          //currentRecord.getField("M9403").setValue(m.get("").toString()); //�������ʲ�����  ??
			          //currentRecord.getField("TOTALNONCURRENTASSETS").setValue(m.get("").toString()); //�������ʲ��ϼ�  ??
			          //currentRecord.getField("TOTALASSETS").setValue(m.get("").toString()); //�ʲ��ܼ�       	
			          //currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("").toString()); //���ڽ��       ??	
			          //currentRecord.getField("TAXPAYABLE").setValue(m.get("").toString()); //Ӧ��˰��       ??	
			          //currentRecord.getField("TREASURYPAYABLE").setValue(m.get("").toString()); //Ӧ�ɹ����      ??
			          //currentRecord.getField("M9404").setValue(m.get("").toString()); //Ӧ�ɲ���ר����  ??
			          //currentRecord.getField("EMPLOYEEBENEFITSPAYABLE").setValue(m.get("").toString()); //Ӧ��ְ��н��    ??
			          
			          currentRecord.getField("NOTESPAYABLE").setValue(m.get("202").toString()); //Ӧ��Ʊ��       	
			          currentRecord.getField("ACCOUNTSPAYABLE").setValue(m.get("203").toString()); //Ӧ���˿�       	
			          currentRecord.getField("RECEIPTSINADVANCE").setValue(m.get("94705").toString()); //Ԥ���˿�       	
			          currentRecord.getField("OTHERPAYABLES").setValue(m.get("209").toString()); //����Ӧ����      
			          //currentRecord.getField("OTHERCURRENTLIABILITIES").setValue(m.get("").toString()); //����������ծ    ??
			          //currentRecord.getField("TOTALCURRENTLIABILITIES").setValue(m.get("").toString()); //������ծ�ϼ�    ??
			          //currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("").toString()); //���ڽ��       	??
			          //currentRecord.getField("LONGTERMPAYABLES").setValue(m.get("").toString()); //����Ӧ����      ??
			          //currentRecord.getField("M9405").setValue(m.get("").toString()); //��������ծ�ϼ�  ??
			          
			          b1 = new BigDecimal(m.get("202").toString())
					  .add(new BigDecimal(m.get("203").toString()))
					  .add(new BigDecimal(m.get("94705").toString()))
					  .add(new BigDecimal(m.get("209").toString()));
			          currentRecord.getField("TOTALLIABILITIES").setValue(b1.toString());//��ծ�ϼ�     9295+9296+9297+9298+9299+9300+9301+9302
			          
			          currentRecord.getField("ENTERPRISEFUND").setValue(m.get("319").toString()); //��ҵ����       	
			          //currentRecord.getField("NONCURRENTASSETSFUND").setValue(m.get("").toString()); //�������ʲ�����  ??
			          //currentRecord.getField("SPECIALPURPOSEFUNDS").setValue(m.get("").toString()); //ר�û���       	??
			          //currentRecord.getField("FINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //����������ת    
			          //currentRecord.getField("FINANCIALAIDBALANCE").setValue(m.get("").toString()); //������������    
			          //currentRecord.getField("NONFINANCIALAIDCARRIEDOVER").setValue(m.get("").toString()); //�ǲ���������ת  
			          //currentRecord.getField("NONFINANCIALAIDBALANCE").setValue(m.get("").toString()); //�ǲ�����������  
			          currentRecord.getField("UNDERTAKINGSBALANCE").setValue(m.get("322").toString()); //��ҵ����        
			          currentRecord.getField("OPERATINGBALANCE").setValue(m.get("19x").toString()); //��Ӫ����       19x+277	
			          b1 = new BigDecimal(m.get("322").toString())
					  .add(new BigDecimal(m.get("19x").toString()));
			          currentRecord.getField("TOTALNETASSETS").setValue(b1.toString());//���ʲ��ϼ�    9304+9306+9307+9308+9309+9310 
			          //currentRecord.getField("M9406").setValue(m.get("").toString()); //��ծ�;��ʲ��ܼ�
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn1 != null) conn1.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			} 
		}
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


