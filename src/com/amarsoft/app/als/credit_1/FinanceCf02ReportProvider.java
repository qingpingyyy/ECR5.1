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

public class FinanceCf02ReportProvider extends DefaultDataSourceProvider{

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

        //2002���ֽ�������Ϣ
        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0018' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1, sCustomerID);
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
	     	  
	     	  
	          currentRecord.getField("M9799").setValue(m.containsKey("b94")?m.get("b94"):"0.00"); //�յ��������뾭Ӫ��йص��ֽ� 
	          currentRecord.getField("M9823").setValue(m.containsKey("b94")?m.get("b94").toString():"0.00"); //��Ӫ��ֽ�����С�� 9795+9797+9799} 9823
	          
	          
	          currentRecord.getField("CASHPAIDFORGOODSANDSERVICES").setValue(m.containsKey("b95")?m.get("b95"):"0.00"); //������Ʒ����������֧�����ֽ�a02}   
	          currentRecord.getField("M9805").setValue(m.get("a22").toString()); //֧����ְ���Լ�Ϊְ��֧�����ֽ�a22}  
	         
	          b1 = new BigDecimal(m.get("b112").toString()).add(new BigDecimal(m.get("b114").toString()).add(new BigDecimal(m.get("a49").toString())));
	          currentRecord.getField("PAYMENTSOFALLTYPESOFTAXES").setValue(b1.toString());//֧���ĸ���˰��a23+a24+a25}   
	          
	          b1 = new BigDecimal(m.get("a19").toString()).add(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9809").setValue(b1.toString()); //֧���������뾭Ӫ��йص��ֽ�a03+a26} 
	          
	          //double col2Value=a1-a2-a3+a4+(a5-a6)+a7; b95-a22- b112+ b114+ a49- a19+ a26
	          b1 = new BigDecimal(m.get("b95").toString()).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).add(new BigDecimal(m.get("b114").toString())).add(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).add(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9831").setValue(b1.toString());
	          //double col2Value=a1-(a2+a3+a4+a5+a6+a7+a8);
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9813").setValue(b1.toString());//��Ӫ��������ֽ��������� 
	          
	          currentRecord.getField("M9815").setValue(m.get("a28")); //�ջ�Ͷ�����յ����ֽ�a28
	          currentRecord.getField("CASHRECEIVEDFROMONVESTMENTS").setValue(m.get("b100")); //ȡ��Ͷ���������յ����ֽ�a30}      
	          currentRecord.getField("M9819").setValue(m.get("b101")); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ𾻶�a31}
	          //b119+b59+c98
	          //setCol2Value3Jia("M9821").setValue(m.get("b119").setValue(m.get("b59").setValue(m.get("c98")); //�յ���������Ͷ�ʻ�йص��ֽ�a29+a32} 
	          b1 = new BigDecimal(m.get("b119").toString()).add( new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString()));
	          currentRecord.getField("M9821").setValue(b1.toString());
	          
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString()));
	          currentRecord.getField("M9917").setValue(b1.toString());//Ͷ�ʻ�ֽ�����С��
	          
	          b1 = new BigDecimal(m.get("b67").toString()).add(new BigDecimal(m.get("b120").toString()));
	          currentRecord.getField("CASHPAYMENTSFORINVESTMENTS").setValue(b1.toString()); //Ͷ����֧�����ֽ�}     a35+a36 
	          
	          currentRecord.getField("M9829").setValue(m.get("a37")); //֧����������Ͷ�ʻ�йص��ֽ�a37}  
	          
	          b1 = new BigDecimal(m.get("b67").toString()).add(new BigDecimal(m.get("b120").toString())).add(new BigDecimal(m.get("a37").toString()));//Ͷ�ʻ�ֽ�����С��
	          currentRecord.getField("M9919").setValue(b1.toString()); 
	          
	          //double col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9);9917
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9833").setValue(b1.toString()); //Ͷ�ʻ�������ֽ���������
	          
	          currentRecord.getField("CASHRECEIVEDFROMINVESTORS").setValue(m.get("b109")); //����Ͷ�����յ����ֽ�a39}          
	          currentRecord.getField("CASHFROMBORROWINGS").setValue(m.get("a41")); //������յ����ֽ�a41}       
	          
        	  b1 = new BigDecimal(m.get("b71").toString()).add(new BigDecimal(m.get("b72").toString()));
	          currentRecord.getField("M9839").setValue(b1.toString()); //�յ�����������ʻ�йص��ֽ�
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString()));
	          currentRecord.getField("M9921").setValue(b1.toString()); ////���ʻ�ֽ�����С��
	          
	          currentRecord.getField("CASHREPAYMENTSFORDEBTS").setValue(m.get("a44")); //����ծ����֧�����ֽ�a44}        
	          currentRecord.getField("M9845").setValue(m.get("b112")); //�������������򳥸���Ϣ��֧�����ֽ�a46}           
	          
	          b1 = new BigDecimal(m.get("b76").toString()).add(new BigDecimal(m.get("b77").toString())).add(new BigDecimal(m.get("b78").toString())).add(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9847").setValue(b1.toString()); //֧������������ʻ�йص��ֽ�
	        
	          b1 = new BigDecimal(m.get("a44").toString()).add(new BigDecimal(m.get("b112").toString())).add(new BigDecimal(m.get("b76").toString())).add(new BigDecimal(m.get("b77").toString())).add(new BigDecimal(m.get("b78").toString())).add(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9923").setValue(b1.toString()); //���ʻ�ֽ�����С��
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString()));
	          currentRecord.getField("M9851").setValue(b1.toString()); //�Ｏ��������ֽ���������
	          
	          currentRecord.getField("M9853").setValue(m.get("a51")); //���ʱ䶯���ֽ��Ӱ��a51
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString())).add(new BigDecimal(m.get("b94").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9855").setValue(b1.toString()); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�1}   9813+9833+9851+9853
	          
	          //currentRecord.getField("NETPROFIT").setValue(m.get("")); //������z85}              
	          //currentRecord.getField("PROVISIONFORASSETS").setValue(m.get("")); //������ʲ���ֵ׼��""}    
	          //currentRecord.getField("DEPRECIATIONOFFIXEDASSETS").setValue(m.get("")); //�̶��ʲ����z87}           
	          //currentRecord.getField("M9863").setValue(m.get("")); //�����ʲ�̯��z88}          
	          //currentRecord.getField("M9865").setValue(m.get("")); //���ڴ�̯����̯��z78}      
	          //currentRecord.getField("DECREASEOFDEFFEREDEXPENSES").setValue(m.get("")); //��̯���ü���""}           
	          //currentRecord.getField("ADDITIONOFACCUEDEXPENSE").setValue(m.get("")); //Ԥ���������""}           
	          //currentRecord.getField("M9871").setValue(m.get("")); //���ù̶��ʲ������ʲ������������ʲ�����ʧz89}         	
	          //currentRecord.getField("M9873").setValue(m.get("")); //�̶��ʲ�������ʧz90}      
	          //currentRecord.getField("FINANCEEXPENSE").setValue(m.get("")); //�������z91}             
	          //currentRecord.getField("LOSSESARSINGFROMINVESTMENT").setValue(m.get("")); //Ͷ����ʧ}z92              
	          //currentRecord.getField("DEFERREDTAXCREDIT").setValue(m.get("")); //����˰�����z93}           
	          //currentRecord.getField("DECREASEININVENTORIES").setValue(m.get("")); //����ļ���z94}            
	          //currentRecord.getField("M9883").setValue(m.get("")); //��Ӫ��Ӧ����Ŀ�ļ���z95}         
	          //currentRecord.getField("M9885").setValue(m.get("")); //��Ӫ��Ӧ����Ŀ������z96} 
	          
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("OTHERS1").setValue(b1.toString());//
	          
	          //currentRecord.getField("M1813").setValue(m.get("")); //��Ӫ��������ֽ���������1z99}    
	          //currentRecord.getField("DEBTSTRANSFERTOCAPITAL").setValue(m.get("")); //ծ��תΪ�ʱ�""}          
	          //currentRecord.getField("ONEYEARDUECONVERTIBLEBONDS").setValue(m.get("")); //һ���ڵ��ڵĿ�ת����˾ծȯ""}               		 
	          //currentRecord.getField("M9895").setValue(m.get("")); //��������̶��ʲ�""}      
	          //currentRecord.getField("OTHERS2").setValue(m.get("")); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ��""}       
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString()));
	          currentRecord.getField("CASHATTHEENDOFPERIOD").setValue(b1.toString());//�ֽ����ĩ��� 
	          
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString()));
	          currentRecord.getField("M9813").setValue(b1.toString());///��Ӫ��������ֽ��������� 
	          
	          //col2Value=a1+a2+a3+a4+a5+a6-(a7+a8+a9)
	          b1 = new BigDecimal(m.get("a28").toString()).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9833").setValue(b1.toString()); //Ͷ�ʻ�������ֽ���������
	          
	          
	          //a1-(a2+a3+a4+a5+a6+a7+a8) + a9+a10+a11+a12+a13+a14-(a15+a16+a17)
	          b1 = new BigDecimal(m.get("b94").toString()).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));
	          currentRecord.getField("M9903").setValue(b1.toString()); //�ֽ�ȼ������ĩ���
	         
	          //currentRecord.getField("M9905").setValue(m.get("")); //�ֽ�ȼ�����ڳ����z104}   
	          
	          
	          b1 = new BigDecimal(m.get("b109").toString()).add(new BigDecimal(m.get("a41").toString())).add(new BigDecimal(m.get("b71").toString())).add(new BigDecimal(m.get("b72").toString())).subtract(new BigDecimal(m.get("a44").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b76").toString())).subtract(new BigDecimal(m.get("b77").toString())).subtract(new BigDecimal(m.get("b78").toString())).subtract(new BigDecimal(m.get("b126").toString())).add(new BigDecimal(m.get("a51").toString())).add(new BigDecimal(m.get("b94").toString())).subtract(new BigDecimal(m.get("b95").toString())).subtract(new BigDecimal(m.get("a22").toString())).subtract(new BigDecimal(m.get("b112").toString())).subtract(new BigDecimal(m.get("b114").toString())).subtract(new BigDecimal(m.get("a49").toString())).subtract(new BigDecimal(m.get("a19").toString())).subtract(new BigDecimal(m.get("a26").toString())).add(new BigDecimal(m.get("a28").toString())).add(new BigDecimal(m.get("b100").toString())).add(new BigDecimal(m.get("b101").toString())).add(new BigDecimal(m.get("b119").toString())).add(new BigDecimal(m.get("b59").toString())).add(new BigDecimal(m.get("c98").toString())).subtract(new BigDecimal(m.get("b67").toString())).subtract(new BigDecimal(m.get("b120").toString())).subtract(new BigDecimal(m.get("a37").toString()));	        		  
	          currentRecord.getField("M1855").setValue(b1.toString()); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�2
	         
	          m.clear();
        
        }
        rs1.close();
        ps1.close();
        conn1.close();
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


