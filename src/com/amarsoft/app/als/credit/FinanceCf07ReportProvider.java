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

public class FinanceCf07ReportProvider extends DefaultDataSourceProvider{

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
        
        //��ҵ2007���ֽ�������Ϣ
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0297' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"'";
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
	     	  
	     	//��ҵ2007���ֽ�������
	          currentRecord.getField("M9199").setValue(m.get("z100").toString()); //������Ʒ���ṩ�����յ����ֽ�                  
	          currentRecord.getField("TAXREFUNDS").setValue(m.get("z102").toString()); //�յ���˰�ѷ���                             	 
	          currentRecord.getField("M9201").setValue(m.get("z103").toString()); //�յ������뾭Ӫ��йص��ֽ�  
	          
	          b1 = new BigDecimal(m.get("z100").toString())
			  .add(new BigDecimal(m.get("z102").toString()))
			  .add(new BigDecimal(m.get("z103").toString()));
	          currentRecord.getField("M9202").setValue(b1.toString());//��Ӫ��ֽ�����С��     9199+9200+9201  
	          
	          currentRecord.getField("CASHPAIDFORGOODSANDSERVICES").setValue(m.get("b95").toString()); //������Ʒ����������֧�����ֽ�                  
	          currentRecord.getField("M9204").setValue(m.get("a22").toString()); //֧����ְ���Լ�Ϊְ��֧�����ֽ�                
	          currentRecord.getField("PAYMENTSOFALLTYPESOFTAXES").setValue(m.get("z104").toString()); //֧���ĸ���˰��                             	 
	          currentRecord.getField("M9206").setValue(m.get("z105").toString()); //֧�������뾭Ӫ��йص��ֽ�                  
	          
	          b1 = new BigDecimal(m.get("b95").toString()).add(new BigDecimal(m.get("a22").toString())).add(new BigDecimal(m.get("z104").toString())).add(new BigDecimal(m.get("z105").toString()));
	          currentRecord.getField("M9207").setValue(b1.toString());////��Ӫ��ֽ�����С��
	          
	          b1 = new BigDecimal(m.get("z100").toString()).add(new BigDecimal(m.get("z102").toString())).add(new BigDecimal(m.get("z103").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("z104").toString())).subtract(new BigDecimal(m.get("z105").toString()));
	          currentRecord.getField("M9208").setValue(b1.toString());//��Ӫ��������ֽ���������1      9202-9207
	          
	          currentRecord.getField("M9209").setValue(m.get("z106").toString()); //�ջ�Ͷ�����յ����ֽ�                          
	          currentRecord.getField("CASHRECEIVEDFROMONVESTMENTS").setValue(m.get("z107").toString()); //ȡ��Ͷ���������յ����ֽ�                      
	          currentRecord.getField("M9211").setValue(m.get("z108").toString()); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ�
	          currentRecord.getField("M9212").setValue(m.get("z111").toString()); //�����ӹ�˾������Ӫҵ��λ�յ����ֽ𾻶�        
	          currentRecord.getField("M9213").setValue(m.get("a32").toString()); //�յ�������Ͷ�ʻ�йص��ֽ�                  
	          
	          b1 = new BigDecimal(m.get("z106").toString())
			  .add(new BigDecimal(m.get("z107").toString()))
			  .add(new BigDecimal(m.get("z108").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("a32").toString()));
	          currentRecord.getField("M9214").setValue(b1.toString());//Ͷ�ʻ�ֽ�����С�� 9209+9210+9211+9212+9213  
	          
	          currentRecord.getField("M9215").setValue(m.get("z109").toString()); //�����̶��ʲ������ʲ������������ʲ���֧�����ֽ�
	          currentRecord.getField("CASHPAYMENTSFORINVESTMENTS").setValue(m.get("z110").toString()); //Ͷ����֧�����ֽ�                           	 
	          currentRecord.getField("M9217").setValue(m.get("z111").toString()); //ȡ���ӹ�˾������Ӫҵ��λ֧�����ֽ𾻶�        
	          currentRecord.getField("M9218").setValue(m.get("z112").toString()); //֧��������Ͷ�ʻ�йص��ֽ�                  
	          
	          b1 = new BigDecimal(m.get("z109").toString())
			  .add(new BigDecimal(m.get("z110").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("z112").toString()));
	          currentRecord.getField("SUBTOTALOFCASHOUTFLOWS").setValue(b1.toString());//Ͷ�ʻ�ֽ�����С�� 9209+9210+9211+9212+9213  
	          
	          b1 = new BigDecimal(m.get("z106").toString()).add(new BigDecimal(m.get("z107").toString())).add(new BigDecimal(m.get("z108").toString())).add(new BigDecimal(m.get("z111").toString())).add(new BigDecimal(m.get("a32").toString())).subtract(new BigDecimal(m.get("z109").toString())).subtract(new BigDecimal(m.get("z110").toString())).subtract(new BigDecimal(m.get("z111").toString())).subtract(new BigDecimal(m.get("z112").toString()));
	          currentRecord.getField("M9220").setValue(b1.toString());//Ͷ�ʻ�������ֽ���������  9214-9219
	          
	          currentRecord.getField("CASHRECEIVEDFROMINVESTORS").setValue(m.get("z113").toString()); //����Ͷ���յ����ֽ�                          	 
	          currentRecord.getField("CASHFROMBORROWINGS").setValue(m.get("z114").toString()); //ȡ�ý���յ����ֽ�                          	 
	          currentRecord.getField("M9223").setValue(m.get("a42").toString()); //�յ���������ʻ�йص��ֽ�                  
	          
	          b1 = new BigDecimal(m.get("z113").toString()).add(new BigDecimal(m.get("z114").toString())).add( new BigDecimal(m.get("a42").toString()));
	          currentRecord.getField("M9224").setValue(b1.toString());//���ʻ�ֽ�����С��        9221+9222+9223   
	          
	          currentRecord.getField("CASHREPAYMENTSFORDEBTS").setValue(m.get("z115").toString()); //����ծ����֧�����ֽ�                        	 
	          currentRecord.getField("M9226").setValue(m.get("z116").toString()); //�������������򳥸���Ϣ��֧�����ֽ�          
	          currentRecord.getField("M9227").setValue(m.get("z117").toString()); //֧����������ʻ�йص��ֽ�                  
	          
	          b1 = new BigDecimal(m.get("z115").toString()).add(new BigDecimal(m.get("z116").toString())).add( new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9228").setValue(b1.toString()); //���ʻ�ֽ�����С��     9225+9226+9227
	        
	          b1 = new BigDecimal(m.get("z113").toString()).add(new BigDecimal(m.get("z114").toString())).add(new BigDecimal(m.get("a42").toString())).subtract(new BigDecimal(m.get("z115").toString())).subtract(new BigDecimal(m.get("z116").toString())).subtract(new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9229").setValue(b1.toString());//�Ｏ��������ֽ���������       9224-9228
	          
	          //currentRecord.getField("M9230").setValue(m.get("").toString()); //���ʱ䶯���ֽ��ֽ�ȼ����Ӱ��              
	          //setCol2Value7H+setCol2Value9H1+setCol2Value6H1
	          //a1+a2+a3-(a4+a5+a6+a7) +a8+a9+a10+a11+a12-(a13+a14+a15+a16) +a17+a18+a19-(a20+a21+a22)  
	          
	          b1 = new BigDecimal(m.get("z100").toString())
			  .add(new BigDecimal(m.get("z102").toString()))
			  .add(new BigDecimal(m.get("z103").toString()))
			  .subtract(new BigDecimal(m.get("b95").toString()))
			  .subtract(new BigDecimal(m.get("a22").toString()))
			  .subtract(new BigDecimal(m.get("z104").toString()))
			  .subtract(new BigDecimal(m.get("z105").toString()))
			  .add(new BigDecimal(m.get("z106").toString()))
			  .add(new BigDecimal(m.get("z107").toString()))
			  .add(new BigDecimal(m.get("z108").toString()))
			  .add(new BigDecimal(m.get("z111").toString()))
			  .add(new BigDecimal(m.get("a32").toString()))
			  .subtract(new BigDecimal(m.get("z109").toString()))
			  .subtract(new BigDecimal(m.get("z110").toString()))
			  .subtract(new BigDecimal(m.get("z111").toString()))
			  .subtract(new BigDecimal(m.get("z112").toString()))
			  .add(new BigDecimal(m.get("z113").toString()))
			  .add(new BigDecimal(m.get("z114").toString()))
			  .add(new BigDecimal(m.get("a42").toString()))
			  .subtract(new BigDecimal(m.get("z115").toString()))
			  .subtract(new BigDecimal(m.get("z116").toString()))
			  .subtract(new BigDecimal(m.get("z117").toString()));
	          currentRecord.getField("M9231").setValue(b1.toString());//�ֽ��ֽ�ȼ��ﾻ���Ӷ�     9208+9220+9229
	          
	          currentRecord.getField("M9232").setValue(m.get("b82").toString()); //�ڳ��ֽ��ֽ�ȼ������  ""   
	          
	          //setCol2Value23H + b82
	          b1 = new BigDecimal(m.get("z100").toString()).add(new BigDecimal(m.get("z102").toString())).add(new BigDecimal(m.get("z103").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("z104").toString())).subtract(new BigDecimal(m.get("z105").toString())).add(new BigDecimal(m.get("z106").toString())).add(new BigDecimal(m.get("z107").toString())).add(new BigDecimal(m.get("z108").toString())).add(new BigDecimal(m.get("z111").toString())).add(new BigDecimal(m.get("a32").toString())).subtract(new BigDecimal(m.get("z109").toString())).subtract(new BigDecimal(m.get("z110").toString())).subtract(new BigDecimal(m.get("z111").toString())).subtract(new BigDecimal(m.get("z112").toString())).add(new BigDecimal(m.get("z113").toString())).add(new BigDecimal(m.get("z114").toString())).add(new BigDecimal(m.get("a42").toString())).subtract(new BigDecimal(m.get("z115").toString())).subtract(new BigDecimal(m.get("z116").toString())).subtract(new BigDecimal(m.get("z117").toString())).add(new BigDecimal(m.get("b82").toString()));
	          currentRecord.getField("M9233").setValue(b1.toString());//��ĩ�ֽ��ֽ�ȼ������   9231+9232 
	          
	          currentRecord.getField("NETPROFIT").setValue(m.get("517").toString()); //������                                  	
	          currentRecord.getField("PROVISIONFORASSETIMPAIRMENT").setValue(m.get("z600").toString()); //�ʲ���ֵ׼��                              	
	          currentRecord.getField("DEPRECIATIONOFFIXEDASSETS").setValue(m.get("z601").toString()); //�̶��ʲ��۾ɡ������ʲ��ۺġ������������ʲ��۾�
	          currentRecord.getField("M9237").setValue(m.get("b60").toString()); //�����ʲ�̯��                             	
	          currentRecord.getField("M9238").setValue(m.get("b61").toString()); //���ڴ�̯����̯��                           	 
	          currentRecord.getField("DECREASEOFDEFFEREDEXPENSES").setValue(m.get("z602").toString()); //��̯���ü���                             	
	          currentRecord.getField("ADDITIONOFACCUEDEXPENSE").setValue(m.get("z603").toString()); //Ԥ���������                             	
	          currentRecord.getField("M9241").setValue(m.get("z604").toString()); //���ù̶��ʲ������ʲ������������ʲ�����ʧ      
	          currentRecord.getField("M9242").setValue(m.get("z605").toString()); //�̶��ʲ�������ʧ                            
	          currentRecord.getField("M9243").setValue(m.get("z606").toString()); //���ʼ�ֵ�䶯��ʧ                            
	          currentRecord.getField("FINANCEEXPENSE").setValue(m.get("z607").toString()); //�������                                	
	          currentRecord.getField("LOSSESARSINGFROMINVESTMENT").setValue(m.get("z608").toString()); //Ͷ����ʧ                                	
	          currentRecord.getField("DEFERREDINCOMETAXASSETS").setValue(m.get("z609").toString()); //��������˰�ʲ�����                          
	          currentRecord.getField("M9247").setValue(m.get("z610").toString()); //��������˰��ծ����                          	 
	          currentRecord.getField("DECREASEININVENTORIES").setValue(m.get("z611").toString()); //����ļ���                               	
	          currentRecord.getField("M9250").setValue(m.get("z612").toString()); //��Ӫ��Ӧ����Ŀ�ļ���                         	
	          currentRecord.getField("M9251").setValue(m.get("z613").toString()); //��Ӫ��Ӧ����Ŀ������                        	 
	          currentRecord.getField("OTHERS").setValue(m.get("z614").toString()); //�����������Ϊ��Ӫ��ֽ�������Ŀ�£�����    
	          
	          b1 = new BigDecimal(m.get("c99").toString()).add(new BigDecimal(m.get("b75").toString()));
	          currentRecord.getField("M9233").setValue(b1.toString()); //��Ӫ��������ֽ���������2     
	          
	          currentRecord.getField("DEBTSTRANSFERTOCAPITAL").setValue(m.get("b76").toString()); //ծ��תΪ�ʱ�                              	
	          currentRecord.getField("ONEYEARDUECONVERTIBLEBONDS").setValue(m.get("b77").toString()); //һ���ڵ��ڵĿ�ת����˾ծȯ                    
	          currentRecord.getField("M9255").setValue(m.get("b78").toString()); //��������̶��ʲ�                             	
	          currentRecord.getField("NONCASHOTHERS").setValue(m.get("z614").toString()); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ�£�  
	          currentRecord.getField("CASHATTHEENDOFPERIOD").setValue(m.get("b79").toString()); //�ֽ����ĩ���                              
	          currentRecord.getField("M9258").setValue(m.get("z615").toString()); //�ֽ���ڳ����                               
	          currentRecord.getField("M9259").setValue(m.get("z616").toString()); //�ֽ�ȼ������ĩ���                         	
	          currentRecord.getField("M9260").setValue(m.get("z617").toString()); //�ֽ�ȼ�����ڳ����                         	
	          currentRecord.getField("M9261").setValue(m.get("z618").toString()); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�
        }
	} catch (SQLException e) {
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


