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
import com.amarsoft.are.util.StringFunction;

public class FinanceBs02ReportProvider extends DefaultDataSourceProvider{

    private Connection conn1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private Log logger = ARE.getLog();
    String database = "ecr";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate ="";
    BigDecimal b1 ;
    
    String sSql="";
    ResultSet rs = null;
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        
        //2002�ʲ���ծ��
        String sQueryReportNo = "select distinct rr.ReportNo as ReportNo  from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0011' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' " ;
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1,sCustomerID);
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
	     	  
			  currentRecord.getField("CURRENCYFUNDS").setValue(m.get("101").toString());//�����ʽ�101
	          currentRecord.getField("SHORTTERMINVESTMENTS").setValue(m.get("102").toString());//����Ͷ��
	          currentRecord.getField("NOTESRECEIVBLE").setValue(m.get("103").toString());//Ӧ��Ʊ�� 103
	          currentRecord.getField("DIVIDENDSRECEIVBLE").setValue(m.get("134").toString());//Ӧ�չ��� 134
	          currentRecord.getField("INTERESTRECEIVBLE").setValue(m.get("19c").toString());//Ӧ����Ϣ19c
	          currentRecord.getField("ACCOUNTSRECEIVBLE").setValue(m.get("104").toString());//Ӧ���˿�104
	          currentRecord.getField("OTHERRECEIVBLES").setValue(m.get("108").toString());//����Ӧ�տ� 108
	          currentRecord.getField("PREPYMENTS").setValue(m.get("19n").toString());//#Ԥ���˿�19n
	          currentRecord.getField("FUTUREGURNTEE").setValue(m.get("129").toString());//�ڻ���֤��""
	          currentRecord.getField("ALLOWNCERECEIVBLE").setValue(m.get("10806").toString());//Ӧ�ղ�����10806
	          currentRecord.getField("EXPORTDRWBCKRECEIVBLE").setValue(m.get("131").toString());//Ӧ�ճ�����˰""
	          currentRecord.getField("INVENTORIES").setValue(m.get("110").toString());//���
	          currentRecord.getField("RWMTERILS").setValue(m.get("11001").toString());//���ԭ����""
	          currentRecord.getField("FINISHEDPRODUCTS").setValue(m.get("11002").toString());//#�������Ʒ""
	          currentRecord.getField("DEFERREDEXPENSES").setValue(m.get("109").toString());//��̯����109
	          currentRecord.getField("UNSETTLEDGLONCURRENTSSETS").setValue(m.get("113").toString());//�����������ʲ�����ʧ 113
	          currentRecord.getField("M9533").setValue(m.get("114").toString());//һ���ڵ��ڵĳ���ծȨͶ�� 114
	          currentRecord.getField("OTHERCURRENTSSETS").setValue(m.get("115").toString());//���������ʲ�115
	          
	          b1 = new BigDecimal(m.get("101").toString()).add(new BigDecimal(m.get("102").toString())).add(new BigDecimal(m.get("103").toString())).add(new BigDecimal(m.get("134").toString())).add(new BigDecimal(m.get("19c").toString())).add(new BigDecimal(m.get("104").toString())).add(new BigDecimal(m.get("108").toString())).add(new BigDecimal(m.get("19n").toString())).add(new BigDecimal(m.get("129").toString())).add(new BigDecimal(m.get("10806").toString())).add(new BigDecimal(m.get("131").toString())).add(new BigDecimal(m.get("110").toString())).add(new BigDecimal(m.get("11001").toString())).add(new BigDecimal(m.get("11002").toString())).add(new BigDecimal(m.get("109").toString())).add(new BigDecimal(m.get("113").toString())).add(new BigDecimal(m.get("114").toString())).add(new BigDecimal(m.get("115").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//�����ʲ��ϼƣ�9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          
	          currentRecord.getField("LONGTERMINVESTMENT").setValue(m.get("116").toString());//����Ͷ��116
	          currentRecord.getField("LONGTERMEQUITYINVESTMENT").setValue(m.get("139").toString());//���ڹ�ȨͶ��""
	          currentRecord.getField("LONGTERMSECURITIESINVESTMENT").setValue(m.get("140").toString());//����ծȨͶ��""
	          currentRecord.getField("INCORPORTINGPRICEDIFFERENCE").setValue(m.get("135").toString());//�ϲ��۲�135
	          
	          b1 = new BigDecimal(m.get("116").toString()).add(new BigDecimal(m.get("135").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//����Ͷ�ʺϼ� 9539+9545
	          
	          currentRecord.getField("ORIGINLCOSTOFFIXEDSSET").setValue(m.get("117").toString());//#�̶��ʲ�ԭ�� 117
	          currentRecord.getField("FIXEDSSETCCUMULTEDDEPRECITION").setValue(m.get("118").toString());//�ۼ��۾�118
	          //setCol2Value2Jian("FIXEDSSETSNETVLUE").setValue(m.get("117").setValue(m.get("118").toString());//�̶��ʲ���ֵ 9549-9551}
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//����Ͷ�ʺϼ� 9539+9545
	          
	          currentRecord.getField("M9555").setValue(m.get("141").toString());//�̶��ʲ�ֵ��ֵ׼�� 141
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString()));
	          currentRecord.getField("TOTLCURRENTSSETS").setValue(b1.toString());//�̶��ʲ����� 9553-9555
	          
	          currentRecord.getField("FIXEDSSETSPENDINGFORDISPOSL").setValue(m.get("121").toString());//�̶��ʲ����� 121
	          currentRecord.getField("CONSTRUCTIONMTERILS").setValue(m.get("143").toString());//��������""
	         
	          
	          b1 = new BigDecimal(m.get("120").toString());
	          currentRecord.getField("CONSTRUCTIONINPROGRESS").setValue(b1.toString());////�ڽ�����  120
	          
	          currentRecord.getField("UNSETTLEDGLONFIXEDSSETS").setValue(m.get("122").toString());//������̶��ʲ�����ʧ   122
	          
	          b1 = new BigDecimal(m.get("117").toString()).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString())).add(new BigDecimal(m.get("121").toString())).add(new BigDecimal(m.get("143").toString())).add(new BigDecimal(m.get("120").toString())).add(new BigDecimal(m.get("122").toString()));
	          currentRecord.getField("TOTLFIXEDSSETS").setValue(b1.toString());//;//�̶��ʲ��ϼ� 9557+9559+9561+9563+9565
	          
	          currentRecord.getField("INTNGIBLESSETS").setValue(m.get("123").toString());//�����ʲ� 123
	          currentRecord.getField("LNDUSERIGHTS").setValue(m.get("12301").toString());//�����ʲ�����ʹ��Ȩ ""
	          currentRecord.getField("DEFERREDSSETS").setValue(m.get("181").toString());//�����ʲ�
	          currentRecord.getField("INCLUDINGFIXEDSSETSREPIR").setValue(m.get("12401").toString());//�����ʲ��̶��ʲ����� ""
	          currentRecord.getField("M9577").setValue(m.get("12402").toString());//#�����ʲ��̶��ʲ�����֧�� ""
	          currentRecord.getField("OTHERLONGTERMSSETS").setValue(m.get("133").toString());//���������ʲ�  125+181
	          currentRecord.getField("M9581").setValue(m.get("31405").toString());//���������ʲ���׼�������� ""
	          
	          b1 = new BigDecimal(m.get("123").toString()).add(new BigDecimal(m.get("181").toString())).add(new BigDecimal(m.get("133").toString()));
	          //���μ������ʲ��ϼ� 9569+9573+9579  
	          currentRecord.getField("M9583").setValue(b1.toString());
	          
	          currentRecord.getField("DEFERREDSSETSDEBITS").setValue(m.get("126").toString());//����˰����� 126
	          
	          b1 = new BigDecimal(m.get("101").toString()).add(new BigDecimal(m.get("102").toString())).add(new BigDecimal(m.get("103").toString())).add(new BigDecimal(m.get("134").toString())).add(new BigDecimal(m.get("19c").toString())).add(new BigDecimal(m.get("104").toString())).add(new BigDecimal(m.get("108").toString())).add(new BigDecimal(m.get("19n").toString())).add(new BigDecimal(m.get("129").toString())).add(new BigDecimal(m.get("10806").toString())).add(new BigDecimal(m.get("131").toString())).add(new BigDecimal(m.get("110").toString())).add(new BigDecimal(m.get("11001").toString())).add(new BigDecimal(m.get("11002").toString())).add(new BigDecimal(m.get("109").toString())).add(new BigDecimal(m.get("113").toString())).add(new BigDecimal(m.get("114").toString())).add(new BigDecimal(m.get("115").toString())).add(new BigDecimal(m.get("117").toString())).subtract(new BigDecimal(m.get("118").toString())).add(new BigDecimal(m.get("117").toString())).subtract(new BigDecimal(m.get("118").toString())).subtract(new BigDecimal(m.get("141").toString())).add(new BigDecimal(m.get("121").toString())).add(new BigDecimal(m.get("143").toString())).add(new BigDecimal(m.get("120").toString())).add(new BigDecimal(m.get("122").toString())).add(new BigDecimal(m.get("123").toString())).add(new BigDecimal(m.get("181").toString())).add(new BigDecimal(m.get("133").toString())).add(new BigDecimal(m.get("126").toString()));
	          currentRecord.getField("TOTLSSETS").setValue(b1.toString());//�ʲ��ܼ� 9537+9547+9567+9583+9585
	          
	          currentRecord.getField("SHORTTERMBORROWINGS").setValue(m.get("201").toString());//���ڽ�� 201
	          currentRecord.getField("NOTESPYBLE").setValue(m.get("202").toString());//Ӧ��Ʊ�� 202
	          currentRecord.getField("ACCOUNTSPYBLE").setValue(m.get("203").toString());//Ӧ���˿� 203
	          currentRecord.getField("RECEIPTSINDVNCE").setValue(m.get("204").toString());//Ԥ���˿� 204
	          currentRecord.getField("WGESORSLRIESPYBLES").setValue(m.get("218").toString());//Ӧ������ 218}
	          currentRecord.getField("EMPLOYEEBENEFITS").setValue(m.get("205").toString());//Ӧ�������� 205
	          
	          b1 = new BigDecimal(m.get("31404").toString()).add(new BigDecimal(m.get("274").toString()));
	          currentRecord.getField("INCOMEPYBLE").setValue(b1.toString());//Ӧ������ 314040+274
	          
	          
	          currentRecord.getField("TXESPYBLE").setValue(m.get("276").toString());//Ӧ��˰��
	          currentRecord.getField("OTHERPYBLETOGOVERNMENT").setValue(m.get("94704").toString());//����Ӧ����
	          currentRecord.getField("OTHERPYBLE").setValue(m.get("209").toString());//����Ӧ���� 209
	          
	          b1 = new BigDecimal(m.get("210").toString()).add(new BigDecimal(m.get("31403").toString()));
	          currentRecord.getField("PROVISIONFOREXPENSES").setValue(b1.toString());//Ԥ����� 210
	          //currentRecord.getField("PROVISIONS").setValue(m.get("").toString());//#Ԥ�Ƹ�ծ""
	          
	          currentRecord.getField("M9613").setValue(m.get("211").toString());//һ���ڵ��ڵĳ��ڸ�ծ 211
	          currentRecord.getField("OTHERCURRENTLIBILITIES").setValue(m.get("212").toString());//����������ծ 212}
	          
	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString()));
	          currentRecord.getField("TOTLCURRENTLIBILITIES").setValue(b1.toString());//������ծ�ϼ� 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
	          
	          currentRecord.getField("LONGTERMBORROWINGS").setValue(m.get("213").toString());//���ڽ�� 213 
	          currentRecord.getField("BONDSPYBLE").setValue(m.get("214").toString());//Ӧ��ծȯ 214
	          currentRecord.getField("LONGTERMPYBLES").setValue(m.get("215").toString());//����Ӧ���� 215
	          currentRecord.getField("GRNTSPYBLE").setValue(m.get("223").toString());//ר��Ӧ���� 220
	          currentRecord.getField("OTHERLONGTERMLIBILITIES").setValue(m.get("427").toString());//�������ڸ�ծ 216
	          currentRecord.getField("SPECILRESERVEFUND").setValue(m.get("21601").toString());//�������ڸ�ծ��׼�������� ""
	          
	          b1 = new BigDecimal(m.get("213").toString()).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString()));
	          currentRecord.getField("TOTLLONGTERMLIBILITIES").setValue(b1.toString());//���ڸ�ծ�ϼ� 9619+9621+9623+9625+9627
	          
	          currentRecord.getField("DEFERREDTXTIONCREDIT").setValue(m.get("217").toString());//����˰����� 217
	
	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString())).add(new BigDecimal(m.get("217").toString()));
	          currentRecord.getField("TOTLLIBILITIES").setValue(b1.toString());//��ծ�ϼ� 9617+9631+9633
	          
	          currentRecord.getField("MINORITYINTEREST").setValue(m.get("307").toString());//�����ɶ�Ȩ�� 307
	          currentRecord.getField("PIDINCPITL").setValue(m.get("301").toString());//ʵ���ʱ� 301
	          currentRecord.getField("NTIONLCPITL").setValue(m.get("30101").toString());//�����ʱ� ""
	          currentRecord.getField("COLLECTIVECPITL").setValue(m.get("30102").toString());//�����ʱ�""
	          currentRecord.getField("LEGLPERSONSCPITL").setValue(m.get("30103").toString());//�����ʱ�""
	          currentRecord.getField("STTEOWNEDLEGLPERSONSCPITL").setValue(m.get("3010301").toString());//�����ʱ����з����ʱ�""
	          currentRecord.getField("COLLECTIVELEGLPERSONSCPITL").setValue(m.get("3010302").toString());//�����ʱ����巨���ʱ�""
	          currentRecord.getField("PERSONLCPITL").setValue(m.get("30104").toString());//�����ʱ�""
	          currentRecord.getField("FOREIGNBUSINESSMENSCPITL").setValue(m.get("30105").toString());//#�����ʱ�""
	          currentRecord.getField("CPITLRRSERVE").setValue(m.get("302").toString());//�ʱ����� 302
	          currentRecord.getField("SURPLUSRESERVE").setValue(m.get("303").toString());//ӯ�๫�� 303
	          //currentRecord.getField("STTUTORYSURPLUSRESERVE").setValue(m.get("").toString());//ӯ�๫������ӯ�๫��""
	          //currentRecord.getField("PUBLICWELFREFUND").setValue(m.get("").toString());//ӯ�๫�������
	          //currentRecord.getField("SUPPLERMENTRYCURRENTCPITL").setValue(m.get("").toString());//#ӯ�๫�����������ʱ� ""
	          currentRecord.getField("UNFFIRMEDINVESTMENTLOSS").setValue(m.get("306").toString());//δȷ�ϵ�Ͷ����ʧ""
	          currentRecord.getField("UNPPROPRITEDPROFIT").setValue(m.get("305").toString());//δ�������� 305
	          
	          
	          b1 = new BigDecimal(m.get("308").toString()).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("M9669").setValue(b1.toString());//��ұ���������""
	          
	          b1 = new BigDecimal(m.get("301").toString()).add(new BigDecimal(m.get("302").toString())).add(new BigDecimal(m.get("303").toString())).add(new BigDecimal(m.get("306").toString()).add(new BigDecimal(m.get("305").toString()))).add(new BigDecimal(m.get("308").toString())).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("TOTLEQUITY").setValue(b1.toString());////������Ȩ��ϼ� 9639+9655+9657+9665+9667+9669

	          b1 = new BigDecimal(m.get("201").toString()).add(new BigDecimal(m.get("202").toString())).add(new BigDecimal(m.get("203").toString())).add(new BigDecimal(m.get("204").toString())).add(new BigDecimal(m.get("218").toString())).add(new BigDecimal(m.get("205").toString())).add(new BigDecimal(m.get("31404").toString())).add(new BigDecimal(m.get("274").toString())).add(new BigDecimal(m.get("276").toString())).add(new BigDecimal(m.get("94704").toString())).add(new BigDecimal(m.get("209").toString())).add(new BigDecimal(m.get("210").toString())).add(new BigDecimal(m.get("31403").toString())).add(new BigDecimal(m.get("222").toString())).add(new BigDecimal(m.get("524").toString())).add(new BigDecimal(m.get("211").toString())).add(new BigDecimal(m.get("212").toString())).add(new BigDecimal(m.get("213").toString())).add(new BigDecimal(m.get("214").toString())).add(new BigDecimal(m.get("215").toString())).add(new BigDecimal(m.get("223").toString())).add(new BigDecimal(m.get("427").toString())).add(new BigDecimal(m.get("217").toString())).add(new BigDecimal(m.get("307").toString())).add(new BigDecimal(m.get("301").toString())).add(new BigDecimal(m.get("302").toString())).add(new BigDecimal(m.get("303").toString())).add(new BigDecimal(m.get("306").toString()).add(new BigDecimal(m.get("305").toString()))).add(new BigDecimal(m.get("308").toString())).subtract(new BigDecimal(m.get("31401").toString()));
	          currentRecord.getField("TOTLEQUITYNDLIBILITIES").setValue(b1.toString());//��ծ��������Ȩ���ܼ�
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


