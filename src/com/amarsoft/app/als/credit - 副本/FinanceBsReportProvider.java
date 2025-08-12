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

public class FinanceBsReportProvider extends DefaultDataSourceProvider{

    private Connection conn1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private Log logger = ARE.getLog();
    String database = "loan";
    String sReportNo="";
    String sCustomerID="";
    ResultSet rs1 = null;
    String sReportDate ="";
    String sReportType="";//��������
    String s1="";
    public void fillRecord() throws SQLException {
        super.fillRecord();
        
        sCustomerID=currentRecord.getField("CUSTOMERID").getString(); if(sCustomerID==null) sCustomerID= "" ;
        sReportDate =currentRecord.getField("SHEETYEAR").getString(); if(sReportDate==null) sReportDate= "" ;
        sReportType =currentRecord.getField("SHEETTYPE").getString(); if(sReportType==null) sReportType= "" ;
        
        String sQueryReportNo = "select distinct rr.ReportNo as ReportNo  from report_record rr ,report_data rd where rr.reportno =rd.reportno  and rr.modelno ='0011' and rr.reportscope <>'03' and rr.objectno =? and rr.REPORTDATE  ='"+sReportDate+"' " ;
        conn1 = ARE.getDBConnection(database);
        ps1=conn1.prepareStatement(sQueryReportNo);
        ps1.setString(1,sCustomerID);
        rs1= this.ps1.executeQuery();
        while(rs1.next()){
	     	  sReportNo=rs1.getString("ReportNo");
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
	          
        	  //currentRecord.getField("SHEETYEAR").setValue(s1);//�������
        	  //ps = conn1.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");	     	  
        	  setCol2Value("CURRENCYFUNDS","101");//�����ʽ�101
	          setCol2Value("SHORTTERMINVESTMENTS","102");//����Ͷ��
	          setCol2Value("NOTESRECEIVBLE","103");//Ӧ��Ʊ�� 103
	          setCol2Value("DIVIDENDSRECEIVBLE","134");//Ӧ�չ��� 134
	          setCol2Value("INTERESTRECEIVBLE","19c");//Ӧ����Ϣ19c
	          setCol2Value("ACCOUNTSRECEIVBLE","104");//Ӧ���˿�104
	          setCol2Value("OTHERRECEIVBLES","108");//����Ӧ�տ� 108
	          setCol2Value("PREPYMENTS","19n");//#Ԥ���˿�19n
	          setCol2Value("FUTUREGURNTEE","129");//�ڻ���֤��""
	          setCol2Value("ALLOWNCERECEIVBLE","10806");//Ӧ�ղ�����10806
	          setCol2Value("EXPORTDRWBCKRECEIVBLE","131");//Ӧ�ճ�����˰""
	          setCol2Value("INVENTORIES","110");//���
	          setCol2Value("RWMTERILS","11001");//���ԭ����""
	          setCol2Value("FINISHEDPRODUCTS","11002");//#�������Ʒ""
	          setCol2Value("DEFERREDEXPENSES","109");//��̯����109
	          setCol2Value("UNSETTLEDGLONCURRENTSSETS","113");//�����������ʲ�����ʧ 113
	          setCol2Value("M9533","114");//һ���ڵ��ڵĳ���ծȨͶ�� 114
	          setCol2Value("OTHERCURRENTSSETS","115");//���������ʲ�115
	          
	          //�����ʲ��ϼƣ�9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","103","134","19c","104","108","19n","129","10806","131","110","109","113","114","115");
	          //setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115");//�����ʲ��ϼ� 9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
	          
	          setCol2Value("LONGTERMINVESTMENT","116");//����Ͷ��116
	          setCol2Value("LONGTERMEQUITYINVESTMENT","139");//���ڹ�ȨͶ��""
	          setCol2Value("LONGTERMSECURITIESINVESTMENT","140");//����ծȨͶ��""
	          setCol2Value("INCORPORTINGPRICEDIFFERENCE","135");//�ϲ��۲�135
	          setCol2Value2Jia("TOTLLONGTERMINVESTMENT","116","135");//����Ͷ�ʺϼ� 9539+9545
	          
	          setCol2Value("ORIGINLCOSTOFFIXEDSSET","117");//#�̶��ʲ�ԭ�� 117
	          setCol2Value("FIXEDSSETCCUMULTEDDEPRECITION","118");//�ۼ��۾�118
	          setCol2Value2Jian("FIXEDSSETSNETVLUE","117","118");//�̶��ʲ���ֵ 9549-9551}
	          
	          
	          setCol2Value("M9555","141");//�̶��ʲ�ֵ��ֵ׼�� 141
	          setCol2Value3Jian("NETVLUEOFFIXEDSSETS","117","118","141");//�̶��ʲ����� 9553-9555
	          
	          setCol2Value("FIXEDSSETSPENDINGFORDISPOSL","121");//�̶��ʲ����� 121
	          setCol2Value("CONSTRUCTIONMTERILS","143");//��������""
	          setCol2Value2Jian("CONSTRUCTIONINPROGRESS","120","19a");//�ڽ�����  120-19a
	          setCol2Value("UNSETTLEDGLONFIXEDSSETS","122");//������̶��ʲ�����ʧ   122
	          //117-118-128+121+(120-19a)+122 
	          setCol2Value8Jia("TOTLFIXEDSSETS","117","118","141"  ,"121" ,"143","120","19a" ,"122");//�̶��ʲ��ϼ� 9557+9559+9561+9563+9565
	          // a1-a2-a3+a4+a5+(a6-a7)+a8
	          
	          setCol2Value("INTNGIBLESSETS","123");//�����ʲ� 123
	          setCol2Value("LNDUSERIGHTS","12301");//�����ʲ�����ʹ��Ȩ ""
	          setCol2Value("DEFERREDSSETS","181");//�����ʲ�
	          setCol2Value("INCLUDINGFIXEDSSETSREPIR","12401");//�����ʲ��̶��ʲ����� ""
	          setCol2Value("M9577","12402");//#�����ʲ��̶��ʲ�����֧�� ""
	          setCol2Value("OTHERLONGTERMSSETS","133");//���������ʲ�  125+181
	          setCol2Value("M9581","31405");//���������ʲ���׼�������� ""
	          //123-199+124+125+181
	          setCol2Value3Jia("M9583","123","181" ,"133" );//���μ������ʲ��ϼ� 9569+9573+9579  
	          
	          setCol2Value("DEFERREDSSETSDEBITS","126");//����˰����� 126
	          //�ʲ��ܼ� 9537+9547+9567+9583+9585
	          setCol2ValueZCZJ("TOTLSSETS","101","102","103","134","19c","104","108","19n","129","10806","131","110","109","113","114","115"       ,"116","135"  ,"117","118","141"  ,"121" ,"143","120","19a" ,"122"    ,"123","181" ,"133"  ,"126"   );
	          
	          setCol2Value("SHORTTERMBORROWINGS","201");//���ڽ�� 201
	          setCol2Value("NOTESPYBLE","202");//Ӧ��Ʊ�� 202
	          setCol2Value("ACCOUNTSPYBLE","203");//Ӧ���˿� 203
	          setCol2Value("RECEIPTSINDVNCE","204");//Ԥ���˿� 204
	          setCol2Value("WGESORSLRIESPYBLES","218");//Ӧ������ 218}
	          setCol2Value("EMPLOYEEBENEFITS","205");//Ӧ�������� 205
	          setCol2Value3Jia("INCOMEPYBLE","219","230","274");//Ӧ������ 219+230+274
	          setCol2Value("TXESPYBLE","207");//Ӧ��˰�� 207
	          setCol2Value("OTHERPYBLETOGOVERNMENT","208");//����Ӧ���� 208
	          setCol2Value("OTHERPYBLE","209");//����Ӧ���� 209
	          setCol2Value("PROVISIONFOREXPENSES","210");//Ԥ����� 210
	          //setCol2Value("PROVISIONS","");//#Ԥ�Ƹ�ծ""
	          setCol2Value("M9613","211");//һ���ڵ��ڵĳ��ڸ�ծ 211
	          setCol2Value("OTHERCURRENTLIBILITIES","212");//����������ծ 212}
	          
	          setCol2ValueLDFZHJ("TOTLCURRENTLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212");//������ծ�ϼ� 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
	          
	          setCol2Value("LONGTERMBORROWINGS","213");//���ڽ�� 213 
	          setCol2Value("BONDSPYBLE","214");//Ӧ��ծȯ 214
	          setCol2Value("LONGTERMPYBLES","215");//����Ӧ���� 215
	          setCol2Value("GRNTSPYBLE","223");//ר��Ӧ���� 220
	          setCol2Value("OTHERLONGTERMLIBILITIES","427");//�������ڸ�ծ 216
	          setCol2Value("SPECILRESERVEFUND","21601");//�������ڸ�ծ��׼�������� ""
	          setCol2Value5Jia("TOTLLONGTERMLIBILITIES","213","214","215","223","427");//���ڸ�ծ�ϼ� 9619+9621+9623+9625+9627
	          setCol2Value("DEFERREDTXTIONCREDIT","217");//����˰����� 217
	          
	          setCol2ValueFZHJ("TOTLLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212"    ,"213","214","215","223","427"  ,"217" );//��ծ�ϼ� 9617+9631+9633
	
	          setCol2Value("MINORITYINTEREST","307");//�����ɶ�Ȩ�� 307
	          setCol2Value("PIDINCPITL","301");//ʵ���ʱ� 301
	          setCol2Value("NTIONLCPITL","30101");//�����ʱ� ""
	          setCol2Value("COLLECTIVECPITL","30102");//�����ʱ�""
	          setCol2Value("LEGLPERSONSCPITL","301003");//�����ʱ�""
	          setCol2Value("STTEOWNEDLEGLPERSONSCPITL","3010301");//�����ʱ����з����ʱ�""
	          setCol2Value("COLLECTIVELEGLPERSONSCPITL","3010302");//�����ʱ����巨���ʱ�""
	          setCol2Value("PERSONLCPITL","30104");//�����ʱ�""
	          setCol2Value("FOREIGNBUSINESSMENSCPITL","30105");//#�����ʱ�""
	          setCol2Value("CPITLRRSERVE","302");//�ʱ����� 302
	          setCol2Value("SURPLUSRESERVE","303");//ӯ�๫�� 303
	          //setCol2Value("STTUTORYSURPLUSRESERVE","");//ӯ�๫������ӯ�๫��""
	          //setCol2Value("PUBLICWELFREFUND","");//ӯ�๫�������
	          //setCol2Value("SUPPLERMENTRYCURRENTCPITL","");//#ӯ�๫�����������ʱ� ""
	          setCol2Value("UNFFIRMEDINVESTMENTLOSS","306");//δȷ�ϵ�Ͷ����ʧ""
	          setCol2Value("UNPPROPRITEDPROFIT","305");//δ�������� 305
	          setCol2Value2Jian("M9669","308","31401");//��ұ���������""   308-31401
	          setCol2ValueSYZQY("TOTLEQUITY","301",   "302","303","306" ,"305" ,"308","31401");//������Ȩ��ϼ� 9639+9655+9657+9665+9667+9669
	          
	          setCol2ValueFZHSYZQY("TOTLEQUITYNDLIBILITIES" ,"201","202","203","204","218","205","219","230","274","207","208","209","210","211","212"    ,"213","214","215","223","427"  ,"217"    ,"307" ,"301",   "302","303","306" ,"305" ,"308","31401"  );//��ծ��������Ȩ���ܼ� 9635+9671+9637
     	   
        }
        rs1.close();
        ps1.close();
        conn1.close();
    }
    
    
	private void setCol2ValueFZHSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29) {
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
				double a24 = getCol2Value(rowSubject24);
				double a25 = getCol2Value(rowSubject25);
				double a26 = getCol2Value(rowSubject26);
				double a27 = getCol2Value(rowSubject27);
				double a28 = getCol2Value(rowSubject28);
				double a29 = getCol2Value(rowSubject29);
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16 +a17+a18   +a19+a20+a21+a22+a23+a24+a25+a26  +a27+(a28-a29)  ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		ARE.getLog().info("��ծ��������Ȩ���ܼƳ���");
		e.printStackTrace();
	}
	
		
	}





	private void setCol2ValueSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
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
				double col2Value=a1+a2+a3+a4+a5+(a6-a7 ) ;
				currentRecord.getField(item).setValue(col2Value);
	} catch (SQLException e) {
		ARE.getLog().info("������Ȩ��ϼƳ���");
		e.printStackTrace();
	}
		
	}





	private void setCol2ValueLDFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15) {
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
					double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15 ;
					currentRecord.getField(item).setValue(col2Value);
			} catch (SQLException e) {
				ARE.getLog().info("������ծ�ϼƳ���");
				e.printStackTrace();
			}
			
	}





	private void setCol2ValueZCZJ( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29, String rowSubject30) {
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
				double a24 = getCol2Value(rowSubject24);
				double a25 = getCol2Value(rowSubject25);
				double a26 = getCol2Value(rowSubject26);
				double a27 = getCol2Value(rowSubject27);
				double a28 = getCol2Value(rowSubject28);
				double a29 = getCol2Value(rowSubject29);
				double a30 = getCol2Value(rowSubject30);
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16 +a17+a18 +a19+a20+a21  +a22    +a23+a24+a25+a26  +a27+a28+a29 +a30  ;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			ARE.getLog().info("�ʲ��ܼƳ���");
			e.printStackTrace();
		}
	}

	private void setCol2ValueLDZCHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16) {
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
				double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16;
			currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			ARE.getLog().info("�����ʲ��ϼƳ���");
			e.printStackTrace();
		}
		
	}

    
    
    private void setCol2ValueFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21) {
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
						double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16+a17+a18+a19+a20+a21;
						currentRecord.getField(item).setValue(col2Value);
				} catch (SQLException e) {
					ARE.getLog().info("��ծ�ϼƺϼƳ���");
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
    
	private void setCol2Value8Jia(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7,String rowSubject8) {
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
				double col2Value=a1-a2-a3+a4+a5+(a6-a7)+a8;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	private void setCol2Value3Jian(String item, String rowSubject1, String rowSubject2, String rowSubject3) {
       	try {
       		if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double a3 = getCol2Value(rowSubject3);
				double col2Value=a1-a2-a3;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    
	private void setCol2Value2Jian(String item, String rowSubject1, String rowSubject2) {
       	try {
       			if(currentRecord.getField(item) == null) return;
				double a1 = getCol2Value(rowSubject1);
				double a2 = getCol2Value(rowSubject2);
				double col2Value=a1-a2;
				currentRecord.getField(item).setValue(col2Value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	private void setCol2Value3Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3) {
    	try {
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

	
	private void setCol2Value2Jia(String item, String rowSubject1, String rowSubject2) throws SQLException {
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


