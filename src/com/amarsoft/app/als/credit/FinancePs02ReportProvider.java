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

public class FinancePs02ReportProvider extends DefaultDataSourceProvider{

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

		        //2002������������
		        String sQueryReportNo ="select distinct rr.ReportNo as ReportNo from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0012' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' ";
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
		        	 
		        	 
			     	  //��ҵ2002���������������Ϣ
					  currentRecord.getField("MAINREVENUEFREVENUE").setValue(m.get("603").toString()); // ��Ӫҵ������501} 9675       
			          //currentRecord.getField("M9677").setValue(m.get("").toString()); // ��Ӫҵ��������ڲ�Ʒ��������""}   
			          // currentRecord.getField("M9679").setValue(m.get("").toString()); // ��Ӫҵ��������ڲ�Ʒ��������""}   
			          //currentRecord.getField("DISCOUNTANDALLOWANCE").setValue(m.get("").toString()); // �ۿ������""}  9681                  
			          currentRecord.getField("M9683").setValue(m.get("603").toString()); // ��Ӫҵ�����뾻��9675-9681}     9683       
			          
			          currentRecord.getField("MAINOPERATINGCOST").setValue(m.get("440").toString()); // ��Ӫҵ��ɱ�440}        9685          
			          //currentRecord.getField("SALESINCOMEOFEXPORTPRODUCTS").setValue(m.get("").toString()); // ��Ӫҵ��ɱ����ڲ�Ʒ���۳ɱ�""}  
			          //currentRecord.getField("M9689").setValue(m.get("").toString()); // ��Ӫҵ��˰�𼰸���}     9689     
			          //currentRecord.getField("OPERATIONEXPENSE").setValue(m.get("").toString()); // ��Ӫ����}         9693            
			          //currentRecord.getField("OTHERSOPERATINGCOST").setValue(m.get("").toString()); // ������ҵ��ɱ�""��}     9691        
			          //currentRecord.getField("DEFERREDINCOME").setValue(m.get("").toString()); // ��������""}       9695              
			          //currentRecord.getField("INCOMEFROMSALESAGENCY").setValue(m.get("").toString()); // ������������""}     9697            
			          //currentRecord.getField("OTHEROPERATINGINCOME").setValue(m.get("").toString()); // ���������룩""}     9699            

			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString()));
			          currentRecord.getField("PRINCIPLEBUSINESSPROFIT").setValue(b1.toString());// ��Ӫҵ������  9683-9685-9689-9693-9691+9695+9697+9699

			          currentRecord.getField("OTHERBUSINESSPROFIT").setValue(m.get("526").toString()); // ����ҵ������526              
			          //currentRecord.getField("SELLINGEXPENSES").setValue(m.get("").toString()); // Ӫҵ����""}     9705                
			          currentRecord.getField("M9707").setValue(m.get("507").toString()); // �������507}  9707                   
			          currentRecord.getField("FINANCIALEXPENSES").setValue(m.get("508").toString()); // �������508}   9709                 
			          //currentRecord.getField("OTHERSEXPENSES").setValue(m.get("").toString()); // ���������ã�}   9907          
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString()));
			          currentRecord.getField("OPERATINGPROFITS").setValue(b1.toString());//Ӫҵ����    9701+9703-9705-9707-9709-9907 
			          
			          currentRecord.getField("INVESTMENTINCOME").setValue(m.get("466").toString()); // Ͷ������510}    9713
			          currentRecord.getField("FUTURESINCOME").setValue(m.get("532").toString()); // �ڻ�����532}             9715        
			          currentRecord.getField("ALLOWANCEINCOME").setValue(m.get("511").toString()); // ��������511}     9717                
			          currentRecord.getField("M9719").setValue(m.get("447").toString()); // �������벹��ǰ�������ҵ��������""}  9719
			          currentRecord.getField("NONOPERATINGINCOME").setValue(m.get("512").toString()); // Ӫҵ������512}                      	
			          currentRecord.getField("M9723").setValue(m.get("448").toString()); // Ӫҵ�����봦�ù̶��ʲ�������""}      9721
			          //currentRecord.getField("INCOMEFROMNONCURRENCYTRADE").setValue(m.get("").toString()); // -Ӫҵ������ǻ����Խ�������""}       
			          //currentRecord.getField("M9727").setValue(m.get("").toString()); // Ӫҵ��������������ʲ�����""}        
			          //currentRecord.getField("INCOMEFROMPENALTY").setValue(m.get("").toString()); // Ӫҵ�����뷣�����""}              
			          currentRecord.getField("OTHERSINCOME").setValue(m.get("526").toString()); // ����������514}                     	
			          //currentRecord.getField("M9731").setValue(m.get("").toString()); // ��������ǰ��Ⱥ������ʽ����ֲ�����""}
			          currentRecord.getField("NONOPERATINGEXPENSES").setValue(m.get("513").toString()); // Ӫҵ��֧��513}                     	
			          //currentRecord.getField("M9735").setValue(m.get("").toString()); // Ӫҵ��֧�����ù̶��ʲ�����ʧ""}    
			          //currentRecord.getField("M9737").setValue(m.get("").toString()); // Ӫҵ��֧��ծ��������ʧ}         
			          //currentRecord.getField("LOSSOFLAWSUITS").setValue(m.get("").toString()); // Ӫҵ��֧������֧��}             
			          //currentRecord.getField("PAYMENTFORDONATION").setValue(m.get("").toString()); // Ӫҵ��֧������֧��}             
			          currentRecord.getField("OTHERPAYMENTS").setValue(m.get("449").toString()); // ����֧��}                       
			          //currentRecord.getField("BALANCEOFCONTENTSALARY").setValue(m.get("").toString()); // ����֧����ת�ĺ������ʰ��ɽ���}  
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString()));
			          currentRecord.getField("TOTALPROFIT").setValue(b1.toString());// �����ܶ�   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745
			          
			          currentRecord.getField("INCOMETAX").setValue(m.get("450").toString()); // ����˰                      
			          //currentRecord.getField("IMPARIMENTLOSS").setValue(m.get("").toString()); // �����ɶ�����523}                   
			          //currentRecord.getField("UNREALIZEDINVESTMENTLOSSES").setValue(m.get("").toString()); // δȷ�ϵ�Ͷ����ʧ""}               
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString()));
			          currentRecord.getField("NETPROFIT").setValue(b1.toString());//������
			          
			          
			          currentRecord.getField("M9757").setValue(m.get("589").toString()); // ���δ��������""}                 
			          currentRecord.getField("COMPENSATIONOFSURPLUSRESERVE").setValue(m.get("590").toString()); // ӯ�๫������""}                   
			          currentRecord.getField("OTHERADJUSTMENTFACTORS").setValue(m.get("591").toString()); // ������������""}                   
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString()));
			          currentRecord.getField("PROFITAVAILABLEFORDISTRIBUTION").setValue(b1.toString());// �ɹ����������}     9755+9757+9759+9761
			          
			          //currentRecord.getField("PROFITRESERVEDFORASINGLEITEM").setValue(m.get("").toString()); // �������õ�����""}                 
			          currentRecord.getField("SUPPLEMENTARYCURRENTCAPITAL").setValue(m.get("593").toString()); // ���������ʱ�""}                   
			          currentRecord.getField("M9769").setValue(m.get("594").toString()); // ��ȡ����ӯ�๫��""}               
			          currentRecord.getField("M9771").setValue(m.get("595").toString()); // ��ȡ���������""}                 
			          currentRecord.getField("M9773").setValue(m.get("596").toString()); // ��ȡְ����������������""}         
			          currentRecord.getField("APPROPRIATIONOFRESERVEFUND").setValue(m.get("597").toString()); // ��ȡ��������""}                   
			          currentRecord.getField("M9777").setValue(m.get("598").toString()); // ��ȡ��ҵ��չ����""}               
			          currentRecord.getField("M9779").setValue(m.get("599").toString()); // ����黹Ͷ��""}                   
			          currentRecord.getField("OTHERS4").setValue(m.get("441").toString()); // �������ɹ�����������Ŀ�£�""}   
			          
			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString())).subtract(new BigDecimal(m.get("593").toString())).subtract(new BigDecimal(m.get("594").toString())).subtract(new BigDecimal(m.get("595").toString())).subtract(new BigDecimal(m.get("596").toString())).subtract(new BigDecimal(m.get("597").toString())).subtract(new BigDecimal(m.get("598").toString())).subtract(new BigDecimal(m.get("599").toString())).subtract(new BigDecimal(m.get("441").toString()));
			          currentRecord.getField("M9781").setValue(b1.toString());// �ɹ�Ͷ���߷��������} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
			          
			          
			          currentRecord.getField("PREFERREDSTOCKDIVIDENDSPAYABLE").setValue(m.get("585").toString()); // Ӧ�����ȹɹ���""}                 
			          currentRecord.getField("M9785").setValue(m.get("586").toString()); // ��ȡ����ӯ�๫��""}               
			          currentRecord.getField("PAYABLEDIVIDENDSOFCOMMONSTOCK").setValue(m.get("572").toString()); // Ӧ����ͨ�ɹ���""}                 
			          currentRecord.getField("M9789").setValue(m.get("452").toString()); // ת���ʱ�����ͨ�ɹ���""}           
			          currentRecord.getField("OTHERS5").setValue(m.get("453").toString()); // �������ɹ�Ͷ���߷���������Ŀ��""}   

			          b1 = new BigDecimal(m.get("603").toString()).subtract(new BigDecimal(m.get("440").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("507").toString())).subtract(new BigDecimal(m.get("508").toString())).add(new BigDecimal(m.get("466").toString())).add(new BigDecimal(m.get("532").toString())).add(new BigDecimal(m.get("511").toString())).add(new BigDecimal(m.get("512").toString())).add(new BigDecimal(m.get("526").toString())).subtract(new BigDecimal(m.get("513").toString())).subtract(new BigDecimal(m.get("449").toString())).subtract(new BigDecimal(m.get("450").toString())).add(new BigDecimal(m.get("589").toString())).add(new BigDecimal(m.get("590").toString())).add(new BigDecimal(m.get("591").toString())).subtract(new BigDecimal(m.get("593").toString())).subtract(new BigDecimal(m.get("594").toString())).subtract(new BigDecimal(m.get("595").toString())).subtract(new BigDecimal(m.get("596").toString())).subtract(new BigDecimal(m.get("597").toString())).subtract(new BigDecimal(m.get("598").toString())).subtract(new BigDecimal(m.get("599").toString())).subtract(new BigDecimal(m.get("441").toString())).subtract(new BigDecimal(m.get("585").toString())).subtract(new BigDecimal(m.get("586").toString())).subtract(new BigDecimal(m.get("572").toString())).subtract(new BigDecimal(m.get("452").toString())).subtract(new BigDecimal(m.get("453").toString()));
			          currentRecord.getField("UNAPPROPRIATEDPROFIT").setValue(b1.toString());
			          
			          //currentRecord.getField("LOSSCOMPENSATEDBEFORETHETAX").setValue(m.get("").toString()); // δ��������Ӧ���Ժ����˰ǰ�����ֲ��Ŀ���""} 
				          
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
                logger.debug("�ر�connection�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�connection����", e);
            }
        }
        super.close();
    }

}


