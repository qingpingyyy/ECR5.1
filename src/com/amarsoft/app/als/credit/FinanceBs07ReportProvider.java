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

public class FinanceBs07ReportProvider extends DefaultDataSourceProvider{

    // �������ݿ����Ӻ�Ԥ�������
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
    
    // ����¼
    public void fillRecord() {
        try {
			//���ø����fillRecord����
			super.fillRecord();
			//��ȡ��ǰ��¼��CUSTOMERID�ֶ�ֵ�����Ϊ����ֵΪ���ַ���
			sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
	        //��ȡ��ǰ��¼��SHEETYEAR�ֶ�ֵ�����Ϊ����ֵΪ���ַ���
	        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
	        
	        //2007�ʲ���ծ����Ϣ��¼
	        //���� SQL ��ѯ report_record �� report_data�����ݿͻ��ź���ݣ�objectno �� reportdate����
			//�����ʲ���ծ��modelno='0291'���� reportno��
	        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo 
										from report_record rr ,report_data rd 
										where rr.reportno =rd.reportno  
										and rr.modelno ='0291' 
										and rr.reportscope <>'03' 
										and rr.objectno =? 
										and rr.REPORTDATE  ='"+sReportDate+"'";
	        //��ȡ���ݿ�����
	        conn1 = ARE.getDBConnection(database);
	        //��ѯREPORT_DATA����ȡrowSubject��col2Value�ֶ�ֵ
	        sSql = "select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"' ";
	        //Ԥ����SQL���
	        ps=conn1.prepareStatement(sSql);
	        ps1=conn1.prepareStatement(sQueryReportNo);
	        //����SQL����еĲ���
	        ps1.setString(1, sCustomerID);
	        //ִ�в�ѯ
	        rs1= this.ps1.executeQuery();
	        
	        while(rs1.next()){
	        	 //��ȡreportno�ֶ�ֵ�����Ϊ����ֵΪ���ַ���
	        	 sReportNo=rs1.getString("ReportNo"); if(sReportNo==null) sReportNo =""; 
	        	 //����һ��Map�����ڴ洢rowSubject��col2Value�ֶ�ֵ
	        	 Map m = new HashMap();
	        	 //���SQL����еĲ���
	        	 ps.clearParameters();
		     	 //ִ�в�ѯ
		     	 rs= this.ps.executeQuery();
		     	 while(rs.next()){
		     		  //���Map���Ѿ�����rowSubject�ֶ�ֵ��������ѭ��
		     		  if(!m.containsKey(rs.getString("rowSubject")))//������ظ���rowsubject������
		     		  {
		     			 //��rowSubject��col2Value�ֶ�ֵ����Map��
		     			 m.put(rs.getString("rowSubject"), rs.getDouble("col2Value"));  
		     		  }else{
		     			  //���Map���Ѿ�����rowSubject�ֶ�ֵ��������ѭ��
		     			  continue;
		     		  }
		     		  
		     	  }
		     	  //�رս������Ԥ�������
		     	  rs.close();
		     	  ps.close();
	        	 
		     	 //2007�ʲ���ծ����Ϣ��¼
		     	  //��Map�е�ֵ������ǰ��¼����Ӧ�ֶ�
		     	  currentRecord.getField("CURRENCYFUNDS").setValue(m.get("101").toString()); //�����ʽ�101
		          currentRecord.getField("M9101").setValue(m.get("147").toString()); //�����Խ����ʲ�   631    
		          currentRecord.getField("NOTESRECEIVABLE").setValue(m.get("103").toString()); //Ӧ��Ʊ��  103          	
		          currentRecord.getField("ACCOUNTSRECEIVABLE").setValue(m.get("104").toString()); //Ӧ���˿�  106          	
		          currentRecord.getField("PREPAYMENTS").setValue(m.get("107").toString()); //Ԥ���˿�   107         	
		          currentRecord.getField("INTERESTRECEIVABLE").setValue(m.get("19c").toString()); //Ӧ����Ϣ   19c         	
		          currentRecord.getField("DIVIDENDSRECEIVABLE").setValue(m.get("134").toString()); //Ӧ�չ��� 134           	
		          currentRecord.getField("OTHERRECEIVABLES").setValue(m.get("108").toString()); //����Ӧ�տ�  108         
		          currentRecord.getField("INVENTORIES").setValue(m.get("110").toString()); //���       110        	
		          currentRecord.getField("M9109").setValue(m.get("114").toString()); //һ���ڵ��ڵķ������ʲ� 632
		          currentRecord.getField("OTHERCURRENTASSETS").setValue(m.get("115").toString()); //���������ʲ�    115+109     
		          
		          //���������ʲ��ϼ�
		          b1 = new BigDecimal(m.get("101").toString())
				  .add(new BigDecimal(m.get("147").toString()))
				  .add(new BigDecimal(m.get("103").toString()))
				  .add(new BigDecimal(m.get("104").toString()))
				  .add(new BigDecimal(m.get("107").toString()))
				  .add(new BigDecimal(m.get("19c").toString()))
				  .add(new BigDecimal(m.get("134").toString()))
				  .add(new BigDecimal(m.get("108").toString()))
				  .add(new BigDecimal(m.get("110").toString()))
				  .add(new BigDecimal(m.get("114").toString()))
				  .add(new BigDecimal(m.get("115").toString()));
		          currentRecord.getField("TOTALCURRENTASSETS").setValue(b1.toString());//�����ʲ��ϼ� //9100+9101+9102+9103+9104+9105+9106+9107+9108+9109+9110
		          
		          //��Map�е�ֵ������ǰ��¼����Ӧ�ֶ�
		          currentRecord.getField("M9112").setValue(m.get("162").toString()); //�ɹ����۵Ľ����ʲ�   633
		          currentRecord.getField("M9113").setValue(m.get("163").toString()); //����������Ͷ��       634
		          currentRecord.getField("LONGTERMEQUITYINVESTMENT").setValue(m.get("116").toString()); //���ڹ�ȨͶ��   648      
		          currentRecord.getField("LONGTERMRECEIVABLES").setValue(m.get("164").toString()); //����Ӧ�տ�       635    
		          currentRecord.getField("INVESTMENTPROPERTIES").setValue(m.get("165").toString()); //Ͷ���Է��ز�   636      
		          currentRecord.getField("FIXEDASSETS").setValue(m.get("142").toString()); //�̶��ʲ�    119        	
		          currentRecord.getField("CONSTRUCTIONINPROGRESS").setValue(m.get("120").toString()); //�ڽ����� 120           	
		          currentRecord.getField("CONSTRUCTIONMATERIALS").setValue(m.get("143").toString()); //��������  127          	
		          currentRecord.getField("M9120").setValue(m.get("121").toString()); //�̶��ʲ�����  121       
		          currentRecord.getField("M9121").setValue(m.get("166").toString()); //�����������ʲ�  637     
		          currentRecord.getField("OILANDGASASSETS").setValue(m.get("167").toString()); //�����ʲ� 638          	
		          currentRecord.getField("INTANGIBLEASSETS").setValue(m.get("123").toString()); //�����ʲ�  129          	
		          currentRecord.getField("DEVELOPMENTDISBURSEMENTS").setValue(m.get("168").toString()); //����֧��130           	
		          currentRecord.getField("GOODWILL").setValue(m.get("169").toString()); //����       131       	 
		          currentRecord.getField("LONGTERMDEFERREDEXPENSES").setValue(m.get("181").toString()); //���ڴ�̯����       132  
		          currentRecord.getField("DEFERREDTAXASSETS").setValue(m.get("126").toString()); //��������˰�ʲ� 133     	
		          currentRecord.getField("OTHERNONCURRENTASSETS").setValue(m.get("170").toString()); //�����������ʲ�   639+125    
		          
		          //����������ʲ��ϼ�
		          b1 = new BigDecimal(m.get("162").toString())
				  .add(new BigDecimal(m.get("163").toString()))
				  .add(new BigDecimal(m.get("116").toString()))
				  .add(new BigDecimal(m.get("164").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("142").toString()))
				  .add(new BigDecimal(m.get("120").toString()))
				  .add(new BigDecimal(m.get("143").toString()))
				  .add(new BigDecimal(m.get("121").toString()))
				  .add(new BigDecimal(m.get("166").toString()))
				  .add(new BigDecimal(m.get("167").toString()))
				  .add(new BigDecimal(m.get("123").toString()))
				  .add(new BigDecimal(m.get("168").toString()))
				  .add(new BigDecimal(m.get("169").toString()))
				  .add(new BigDecimal(m.get("181").toString()))
				  .add(new BigDecimal(m.get("126").toString()))
				  .add(new BigDecimal(m.get("170").toString()));
		          currentRecord.getField("TOTALNONCURRENTASSETS").setValue(b1.toString());//�������ʲ��ϼ�     9112+9113+9114+9115+9116+9117+9118+9119+9120+9121+9122+9123+9124+9125+9126+9127+9128
		          
		          //�����ʲ��ܼ�
		          b1 = new BigDecimal(m.get("101").toString())
				  .add(new BigDecimal(m.get("147").toString()))
				  .add(new BigDecimal(m.get("103").toString()))
				  .add(new BigDecimal(m.get("104").toString()))
				  .add(new BigDecimal(m.get("107").toString()))
				  .add(new BigDecimal(m.get("19c").toString()))
				  .add(new BigDecimal(m.get("134").toString()))
				  .add(new BigDecimal(m.get("108").toString()))
				  .add(new BigDecimal(m.get("110").toString()))
				  .add(new BigDecimal(m.get("114").toString()))
				  .add(new BigDecimal(m.get("115").toString()))
				  .add(new BigDecimal(m.get("162").toString()))
				  .add(new BigDecimal(m.get("163").toString()))
				  .add(new BigDecimal(m.get("116").toString()))
				  .add(new BigDecimal(m.get("164").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("165").toString()))
				  .add(new BigDecimal(m.get("120").toString()))
				  .add(new BigDecimal(m.get("143").toString()))
				  .add(new BigDecimal(m.get("121").toString()))
				  .add(new BigDecimal(m.get("166").toString()))
				  .add(new BigDecimal(m.get("167").toString()))
				  .add(new BigDecimal(m.get("123").toString()))
				  .add(new BigDecimal(m.get("168").toString()))
				  .add(new BigDecimal(m.get("169").toString()))
				  .add(new BigDecimal(m.get("181").toString()))
				  .add(new BigDecimal(m.get("126").toString()))
				  .add(new BigDecimal(m.get("170").toString()));
				  //�����ʲ��ͷ������ʲ��ϼ�
		          currentRecord.getField("TOTALASSETS").setValue(b1.toString()); //�ʲ��ܼ�  9111+9129
		          
		          //��Map�е�ֵ������ǰ��¼����Ӧ�ֶ�
		          currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("201").toString()); //���ڽ��          201  	
		          currentRecord.getField("M9132").setValue(m.get("234").toString()); //�����Խ��ڸ�ծ  640     
		          currentRecord.getField("NOTESPAYABLE").setValue(m.get("202").toString()); //Ӧ��Ʊ��  202          	
		          currentRecord.getField("ACCOUNTSPAYABLE").setValue(m.get("203").toString()); //Ӧ���˿�  203           
		          currentRecord.getField("RECEIPTSINADVANCE").setValue(m.get("204").toString()); //Ԥ���˿� 204           	
		          currentRecord.getField("INTERESTPAYABLE").setValue(m.get("274").toString()); //Ӧ����Ϣ274            	
		          currentRecord.getField("EMPLOYEEBENEFITSPAYABLE").setValue(m.get("218").toString()); //Ӧ��ְ��н��  206       
		          currentRecord.getField("TAXSPAYABLE").setValue(m.get("207").toString()); //Ӧ��˰��     651       	
		          currentRecord.getField("DIVIDENDSPAYABLE").setValue(m.get("209").toString()); //Ӧ������    �������Ϊ���209���ڱ���г���        	
		          currentRecord.getField("OTHERPAYABLES").setValue(m.get("208").toString()); //����Ӧ����           
		          currentRecord.getField("M9141").setValue(m.get("211").toString()); //һ���ڵ��ڵķ�������ծ 643
		          currentRecord.getField("OTHERCURRENTLIABILITIES").setValue(m.get("212").toString()); //����������ծ   212    
		          
		          
		          //����������ծ�ϼ�
		          b1 = new BigDecimal(m.get("201").toString())
				  .add(new BigDecimal(m.get("234").toString()))
				  .add(new BigDecimal(m.get("202").toString()))
				  .add(new BigDecimal(m.get("203").toString()))
				  .add(new BigDecimal(m.get("204").toString()))
				  .add(new BigDecimal(m.get("274").toString()))
				  .add(new BigDecimal(m.get("218").toString()))
				  .add(new BigDecimal(m.get("207").toString()))
				  .add(new BigDecimal(m.get("208").toString()))
				  .add(new BigDecimal(m.get("209").toString()))
				  .add(new BigDecimal(m.get("211").toString()))
				  .add(new BigDecimal(m.get("212").toString()));
		          currentRecord.getField("TOTALCURRENTLIABILITIES").setValue(b1.toString());//������ծ�ϼ� 9131+9132+9133+9134+9135+9136+9137+9138+9139+9140+9141+9142
		          
		          //��Map�е�ֵ������ǰ��¼����Ӧ�ֶ�
		          currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("213").toString()); //���ڽ��213            	
		          currentRecord.getField("BONDSPAYABLES").setValue(m.get("214").toString()); //Ӧ��ծȯ    214        	
		          currentRecord.getField("LONGTERMPAYABLES").setValue(m.get("215").toString()); //����Ӧ����    215       
		          currentRecord.getField("GRANTSPAYABLE").setValue(m.get("223").toString()); //ר��Ӧ����     644      
		          currentRecord.getField("PROVISIONS").setValue(m.get("222").toString()); //Ԥ�Ƹ�ծ            	
		          currentRecord.getField("DEFERREDTAXLIABILITIES").setValue(m.get("217").toString()); //��������˰��652ծ       
		          currentRecord.getField("M9150").setValue(m.get("245").toString()); //������������ծ       
		          
		          //�����������ծ�ϼ�
		          b1 = new BigDecimal(m.get("213").toString())
				  .add(new BigDecimal(m.get("214").toString()))
				  .add(new BigDecimal(m.get("215").toString()))
				  .add(new BigDecimal(m.get("223").toString()))
				  .add(new BigDecimal(m.get("222").toString()))
				  .add(new BigDecimal(m.get("217").toString()))
				  .add(new BigDecimal(m.get("245").toString()));
		          currentRecord.getField("M9151").setValue(b1.toString());//��������ծ�ϼ�   9144+9145+9146+9147+9148+9149+9150
		          
		          //���㸺ծ�ϼ�
		          //��ծ�ϼ�            	9143+9151
				  //������ծ+��������ծ
		          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("234").toString())).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("207").toString())).add(new BigDecimal(m.get("208").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("217").toString())).add(new BigDecimal(m.get("245").toString()));
		          currentRecord.getField("TOTALLIABILITIES").setValue(b1.toString());//
		          
		          //��Map�е�ֵ������ǰ��¼����Ӧ�ֶ�
		          currentRecord.getField("M9153").setValue(m.get("301").toString()); //ʵ���ʱ�����ɱ���   
		          currentRecord.getField("CAPITALRRSERVE").setValue(m.get("302").toString()); //�ʱ�����   302          
		          currentRecord.getField("LESSTREASURYSTOCKS").setValue(m.get("309").toString()); //��������           
		          currentRecord.getField("SURPLUSRESERVE").setValue(m.get("303").toString()); //ӯ�๫��            	
		          currentRecord.getField("UNAPPROPRIATEDPROFIT").setValue(m.get("305").toString()); //δ��������        
		          
		          //����������Ȩ��ϼ�
		          b1 = new BigDecimal(m.get("301").toString())
				  .add(new BigDecimal(m.get("302").toString()))
				  .add(new BigDecimal(m.get("309").toString()))
				  .add(new BigDecimal(m.get("303").toString()))
				  .add(new BigDecimal(m.get("305").toString()));
		          currentRecord.getField("TOTALEQUITY").setValue(b1.toString());//������Ȩ��ϼ�       9153+9154-9155+9156+9157
		          
		          
		          //���㸺ծ��������Ȩ���ܼ�
		          b1 = new BigDecimal(m.get("201").toString())
				  .add(new BigDecimal(m.get("234").toString()))
				  .add(new BigDecimal(m.get("202").toString()))
				  .add(new BigDecimal(m.get("203").toString()))
				  .add(new BigDecimal(m.get("204").toString()))
				  .add(new BigDecimal(m.get("274").toString()))
				  .add(new BigDecimal(m.get("218").toString()))
				  .add(new BigDecimal(m.get("207").toString()))
				  .add(new BigDecimal(m.get("208").toString()))
				  .add(new BigDecimal(m.get("211").toString()))
				  .add(new BigDecimal(m.get("212").toString()))
				  .add(new BigDecimal(m.get("213").toString()))
				  .add(new BigDecimal(m.get("214").toString()))
				  .add(new BigDecimal(m.get("215").toString()))
				  .add(new BigDecimal(m.get("223").toString()))
				  .add(new BigDecimal(m.get("222").toString()))
				  .add(new BigDecimal(m.get("217").toString()))
				  .add(new BigDecimal(m.get("245").toString()))
				  .add(new BigDecimal(m.get("301").toString()))
				  .add(new BigDecimal(m.get("302").toString()))
				  .add(new BigDecimal(m.get("309").toString()))
				  .add(new BigDecimal(m.get("303").toString()))
				  .add(new BigDecimal(m.get("305").toString()));
		          currentRecord.getField("TOTALEQUITYANDLIABILITIES").setValue(b1.toString());
	     	   
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				//�رս������Ԥ�������
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn1 != null) conn1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
    }
    
    // �򿪼�¼��
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


