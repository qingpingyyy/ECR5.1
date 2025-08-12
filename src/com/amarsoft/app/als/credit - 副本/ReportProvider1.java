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

public class ReportProvider1 extends DefaultDataSourceProvider{

    private Connection connection = null;
    private Connection conn1 = null;
    private Connection conn2 = null;
    private Connection conn3 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private PreparedStatement ps2 = null;
    private PreparedStatement ps3 = null;
    private Log logger = ARE.getLog();
    String database = "loan";
    String sReportNo="";
    
    
    public void fillRecord() throws SQLException {
        super.fillRecord();
        //��ҵ2002���ʲ���ծ����Ϣ
        setCol2Value("MainRevenuefRevenue","501");
        setCol2Value("MainOperatingCost","502");
        setCol2Value("CURRENCYFUNDS","101");//�����ʽ�101
        setCol2Value2("SHORTTERMINVESTMENTS","102","195");//����Ͷ�� 102-195
        /*setCol2Value("NOTESRECEIVBLE","103");//Ӧ��Ʊ�� 103
        setCol2Value("DIVIDENDSRECEIVBLE","134");//Ӧ�չ��� 134
        setCol2Value("INTERESTRECEIVBLE","19c");//Ӧ����Ϣ19c
        setPlusCol2Value("ACCOUNTSRECEIVBLE","10405","10406","10407","105");//Ӧ���˿� 10405+10406+10407-105
        setCol2Value3("OTHERRECEIVBLES","10803","10804","10805");//����Ӧ�տ� 10803+10804+10805
        setCol2Value("PREPYMENTS","107");//#Ԥ���˿�107
        //setCol2Value("FUTUREGURNTEE","");//�ڻ���֤��""
        setCol2Value("ALLOWNCERECEIVBLE","10806");//Ӧ�ղ�����10806
        //setCol2Value("EXPORTDRWBCKRECEIVBLE","");//Ӧ�ճ�����˰""
        setCol2Value2J("INVENTORIES","110","111");//���110-111
        //setCol2Value("RWMTERILS","");//���ԭ����""
       // setCol2Value("FINISHEDPRODUCTS","");//#�������Ʒ""
        setCol2Value("DEFERREDEXPENSES","109");//��̯����109
        setCol2Value("UNSETTLEDGLONCURRENTSSETS","113");//�����������ʲ�����ʧ 113
        setCol2Value("M9533","114");//һ���ڵ��ڵĳ���ծȨͶ�� 114
        setCol2Value("OTHERCURRENTSSETS","115");//���������ʲ�115
        setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115");//�����ʲ��ϼ� 9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
        setCol2Value4("LONGTERMINVESTMENT","116","197","196","198");//����Ͷ��116+197-196-198
        //setCol2Value("LONGTERMEQUITYINVESTMENT","");//���ڹ�ȨͶ��""
        //setCol2Value("LONGTERMSECURITIESINVESTMENT","");//����ծȨͶ��""
        setCol2Value("INCORPORTINGPRICEDIFFERENCE","135");//�ϲ��۲�135
        setCol2Value5("TOTLLONGTERMINVESTMENT","116","197","196","198","135");//����Ͷ�ʺϼ� 9539+9545
        setCol2Value("ORIGINLCOSTOFFIXEDSSET","117");//#�̶��ʲ�ԭ�� 117
        setCol2Value("FIXEDSSETCCUMULTEDDEPRECITION","118");//�ۼ��۾�118
        setCol2Value2J("FIXEDSSETSNETVLUE","117","118");//�̶��ʲ���ֵ 9549-9551}
        setCol2Value("M9555","128");//�̶��ʲ�ֵ��ֵ׼�� 128
        setCol2Value3J("NETVLUEOFFIXEDSSETS","117","118","128");//�̶��ʲ����� 9553-9555
        setCol2Value("FIXEDSSETSPENDINGFORDISPOSL","121");//�̶��ʲ����� 121
        //setCol2Value("CONSTRUCTIONMTERILS","");//��������""
        setCol2Value2J("CONSTRUCTIONINPROGRESS","120","19a");//�ڽ�����  120-19a
        setCol2Value("UNSETTLEDGLONFIXEDSSETS","122");//������̶��ʲ�����ʧ   122
        //117-118-128+121+(120-19a)+122 
        setCol2Value7("TOTLFIXEDSSETS","117","118","128"  ,"121" ,"120","19a" ,"122");//�̶��ʲ��ϼ� 9557+9559+9561+9563+9565
        setCol2Value2J("INTNGIBLESSETS","123","199");//�����ʲ� 123-199
        //setCol2Value("LNDUSERIGHTS","");//�����ʲ�����ʹ��Ȩ ""
        setCol2Value("DEFERREDSSETS","124");//�����ʲ�  124
        //setCol2Value("INCLUDINGFIXEDSSETSREPIR","");//�����ʲ��̶��ʲ����� ""
        //setCol2Value("M9577","");//#�����ʲ��̶��ʲ�����֧�� ""
        setCol2Value2("OTHERLONGTERMSSETS","125","181");//���������ʲ�  125+181
        //setCol2Value("M9581","");//���������ʲ���׼�������� ""
        //123-199+124+125+181
        setCol2Value5H("M9583","123","199" ,"124","125","181" );//���μ������ʲ��ϼ� 9569+9573+9579  
        setCol2Value("DEFERREDSSETSDEBITS","126");//����˰����� 126
      //�ʲ��ܼ� 9537+9547+9567+9583+9585
        setCol2ValueZCZJ("TOTLSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115"     ,"116","197","196","198","135"    ,"117","118","128"  ,"121" ,"120","19a" ,"122" ,"123","199" ,"124","125","181" ,"126" );
      
        setCol2Value("SHORTTERMBORROWINGS","201");//���ڽ�� 201
        setCol2Value("NOTESPYBLE","202");//Ӧ��Ʊ�� 202
        setCol2Value("ACCOUNTSPYBLE","203");//Ӧ���˿� 203
        setCol2Value("RECEIPTSINDVNCE","204");//Ԥ���˿� 204
        setCol2Value("WGESORSLRIESPYBLES","218");//Ӧ������ 218}
        setCol2Value("EMPLOYEEBENEFITS","205");//Ӧ�������� 205
        setCol2Value3("INCOMEPYBLE","219","230","274");//Ӧ������ 219+230+274
        setCol2Value("TXESPYBLE","207");//Ӧ��˰�� 207
        setCol2Value("OTHERPYBLETOGOVERNMENT","208");//����Ӧ���� 208
        setCol2Value("OTHERPYBLE","209");//����Ӧ���� 209
        setCol2Value("PROVISIONFOREXPENSES","210");//Ԥ����� 210
        //setCol2Value("PROVISIONS","");//#Ԥ�Ƹ�ծ""
        setCol2Value("M9613","211");//һ���ڵ��ڵĳ��ڸ�ծ 211
        setCol2Value("OTHERCURRENTLIBILITIES","212");//����������ծ 212}
        
        setCol2Value14("TOTLCURRENTLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212");//������ծ�ϼ� 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
        
        setCol2Value("LONGTERMBORROWINGS","213");//���ڽ�� 213 
        setCol2Value("BONDSPYBLE","214");//Ӧ��ծȯ 214
        setCol2Value("LONGTERMPYBLES","215");//����Ӧ���� 215
        setCol2Value("GRNTSPYBLE","220");//ר��Ӧ���� 220
        setCol2Value("OTHERLONGTERMLIBILITIES","216");//�������ڸ�ծ 216
        //setCol2Value("SPECILRESERVEFUND","");//�������ڸ�ծ��׼�������� ""
        setCol2Value5Jia("TOTLLONGTERMLIBILITIES","213","214","215","220","216");//���ڸ�ծ�ϼ� 9619+9621+9623+9625+9627
        setCol2Value("DEFERREDTXTIONCREDIT","217");//����˰����� 217
        setCol2ValueFZHJ("TOTLLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212","213","214","215","220","216","217");//��ծ�ϼ� 9617+9631+9633

        setCol2Value("MINORITYINTEREST","307");//�����ɶ�Ȩ�� 307
        setCol2Value("PIDINCPITL","301");//ʵ���ʱ� 301
        //setCol2Value("NTIONLCPITL","");//�����ʱ� ""
        //setCol2Value("COLLECTIVECPITL","");//�����ʱ�""
        //setCol2Value("LEGLPERSONSCPITL","");//�����ʱ�""
        //setCol2Value("STTEOWNEDLEGLPERSONSCPITL","");//�����ʱ����з����ʱ�""
        //setCol2Value("COLLECTIVELEGLPERSONSCPITL","");//�����ʱ����巨���ʱ�""
        //setCol2Value("PERSONLCPITL","");//�����ʱ�""
        //setCol2Value("FOREIGNBUSINESSMENSCPITL","");//#�����ʱ�""
        setCol2Value("CPITLRRSERVE","302");//�ʱ����� 302
        setCol2Value("SURPLUSRESERVE","303");//ӯ�๫�� 303
       // setCol2Value("STTUTORYSURPLUSRESERVE","");//ӯ�๫������ӯ�๫��""
        setCol2Value("PUBLICWELFREFUND","304");//ӯ�๫�������304
        //setCol2Value("SUPPLERMENTRYCURRENTCPITL","");//#ӯ�๫�����������ʱ� ""
       // setCol2Value("UNFFIRMEDINVESTMENTLOSS","");//δȷ�ϵ�Ͷ����ʧ""
        setCol2Value("UNPPROPRITEDPROFIT","");//δ�������� 305
      //  setCol2Value("M9669","");//��ұ���������""
        setCol2Value4H("TOTLEQUITY","301","302","303","305");//������Ȩ��ϼ� 9639+9655+9657+9665+9667+9669
        setCol2ValueFZHSYZQY("TOTLEQUITYNDLIBILITIES"   ,"213","214","215","220","216"       ,"301","302","303","305"             ,"307");//��ծ��������Ȩ���ܼ� 9635+9671+9637
                  
        */
        /*//��ҵ2002���������������Ϣ
        setCol2Value("MAINREVENUEFREVENUE","501"); // ��Ӫҵ������501} 9675       
        //setCol2Value("M9677",""); // ��Ӫҵ��������ڲ�Ʒ��������""}   
       // setCol2Value("M9679",""); // ��Ӫҵ��������ڲ�Ʒ��������""}   
        setCol2Value("DISCOUNTANDALLOWANCE",""); // �ۿ������""}  9681                  
        setCol2Value("M9683","501"); // ��Ӫҵ�����뾻��9675-9681}     9683       
        setCol2Value("MAINOPERATINGCOST","502"); // ��Ӫҵ��ɱ�502}        9685          
        //setCol2Value("SALESINCOMEOFEXPORTPRODUCTS",""); // ��Ӫҵ��ɱ����ڲ�Ʒ���۳ɱ�""}  
        setCol2Value("M9689","504"); // ��Ӫҵ��˰�𼰸���504}     9689     
        setCol2Value("OPERATIONEXPENSE","503"); // ��Ӫ����503}         9693            
        //setCol2Value("OTHERSOPERATINGCOST",""); // ������ҵ��ɱ�""��}     9691        
        setCol2Value("DEFERREDINCOME",""); // ��������""}       9695              
        setCol2Value("INCOMEFROMSALESAGENCY",""); // ������������""}     9697            
        setCol2Value("OTHEROPERATINGINCOME",""); // ���������룩""}     9699            
        setCol2Value4J("PRINCIPLEBUSINESSPROFIT","501","502","504","503"); // ��Ӫҵ������}  9683-9685-9689-9693-9691+9695+9697+9699       9701       
        setCol2Value("OTHERBUSINESSPROFIT","506"); // ����ҵ������506} 9703                
        setCol2Value("SELLINGEXPENSES",""); // Ӫҵ����""}     9705                
        setCol2Value("M9707","507"); // �������507}  9707                   
        setCol2Value("FINANCIALEXPENSES","508"); // �������508}   9709                 
        //setCol2Value("OTHERSEXPENSES",""); // ���������ã�}   9907          
        setCol2ValueYELR("OPERATINGPROFITS","501","502","504","503","+506","507","508"); // Ӫҵ����}     9701+9703-9705-9707-9709-9907          9711           
        setCol2Value("INVESTMENTINCOME","510"); // Ͷ������510}    9713                 
        setCol2Value("FUTURESINCOME","532"); // �ڻ�����532}             9715        
        setCol2Value("ALLOWANCEINCOME","511"); // ��������511}     9717                
        //setCol2Value("M9719",""); // �������벹��ǰ�������ҵ��������""}  9719
        setCol2Value("NONOPERATINGINCOME","512"); // Ӫҵ������512}                      	
        //setCol2Value("M9723",""); // Ӫҵ�����봦�ù̶��ʲ�������""}      9721
        //setCol2Value("INCOMEFROMNONCURRENCYTRADE",""); // -Ӫҵ������ǻ����Խ�������""}       
        //setCol2Value("M9727",""); // Ӫҵ��������������ʲ�����""}        
        //setCol2Value("INCOMEFROMPENALTY",""); // Ӫҵ�����뷣�����""}              
        setCol2Value("OTHERSINCOME","514"); // ����������514}                     	
        //setCol2Value("M9731",""); // ��������ǰ��Ⱥ������ʽ����ֲ�����""}
        setCol2Value("NONOPERATINGEXPENSES","513"); // Ӫҵ��֧��513}                     	
        //setCol2Value("M9735",""); // Ӫҵ��֧�����ù̶��ʲ�����ʧ""}    
        //setCol2Value("M9737",""); // Ӫҵ��֧��ծ��������ʧ}         
        //setCol2Value("LOSSOFLAWSUITS",""); // Ӫҵ��֧������֧��}             
        //setCol2Value("PAYMENTFORDONATION",""); // Ӫҵ��֧������֧��}             
        //setCol2Value("OTHERPAYMENTS",""); // ����֧��}                       
        //setCol2Value("BALANCEOFCONTENTSALARY",""); // ����֧����ת�ĺ������ʰ��ɽ���}  
        setCol2ValueLRZE("TOTALPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"); // �����ܶ�}   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745              
        setCol2Value("INCOMETAX","516"); // ����˰516}                         
        setCol2Value("IMPARIMENTLOSS","523"); // �����ɶ�����523}                   
        //setCol2Value("UNREALIZEDINVESTMENTLOSSES",""); // δȷ�ϵ�Ͷ����ʧ""}               
        setCol2ValueJLR("NETPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // ������} 9747-9749-9751+9753                        
        //setCol2Value("M9757",""); // ���δ��������""}                 
        //setCol2Value("COMPENSATIONOFSURPLUSRESERVE",""); // ӯ�๫������""}                   
        //setCol2Value("OTHERADJUSTMENTFACTORS",""); // ������������""}                   
        setCol2ValueJLR("PROFITAVAILABLEFORDISTRIBUTION","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // �ɹ����������}     9755+9757+9759+9761            
        //setCol2Value("PROFITRESERVEDFORASINGLEITEM",""); // �������õ�����""}                 
        //setCol2Value("SUPPLEMENTARYCURRENTCAPITAL",""); // ���������ʱ�""}                   
        //setCol2Value("M9769",""); // ��ȡ����ӯ�๫��""}               
        //setCol2Value("M9771",""); // ��ȡ���������""}                 
        //setCol2Value("M9773",""); // ��ȡְ����������������""}         
        //setCol2Value("APPROPRIATIONOFRESERVEFUND",""); // ��ȡ��������""}                   
        //setCol2Value("M9777",""); // ��ȡ��ҵ��չ����""}               
        //setCol2Value("M9779",""); // ����黹Ͷ��""}                   
        //setCol2Value("OTHERS4",""); // �������ɹ�����������Ŀ�£�""}    
        setCol2ValueJLR("M9781","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // �ɹ�Ͷ���߷��������} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
        //setCol2Value("PREFERREDSTOCKDIVIDENDSPAYABLE",""); // Ӧ�����ȹɹ���""}                 
       // setCol2Value("M9785",""); // ��ȡ����ӯ�๫��""}               
        //setCol2Value("PAYABLEDIVIDENDSOFCOMMONSTOCK",""); // Ӧ����ͨ�ɹ���""}                 
        //setCol2Value("M9789",""); // ת���ʱ�����ͨ�ɹ���""}           
        //setCol2Value("OTHERS5",""); // �������ɹ�Ͷ���߷���������Ŀ��""}   
        setCol2ValueJLR("UNAPPROPRIATEDPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // δ��������}     9781-9783-9785-9787-9789-9913          
        //setCol2Value("LOSSCOMPENSATEDBEFORETHETAX",""); // δ��������Ӧ���Ժ����˰ǰ�����ֲ��Ŀ���""} 
        
        
        
        //��ҵ2002���ֽ�������Ϣ
        setCol2Value("M9795","a01"); //������Ʒ���ṩ�����յ����ֽ�a01}   
        setCol2Value2("TAXREFUNDS","a17","a18"); //�յ���˰�ѷ���a17+a18}         
        setCol2Value2("M9799","a16","a19"); //�յ��������뾭Ӫ��йص��ֽ�a16+a19} 
        
        setCol2Value5Jia("M9823","a01","a17","a18","a16","a19"); //��Ӫ��ֽ�����С�� 9795+9797+9799} 9823
        
        setCol2Value("CASHPAIDFORGOODSANDSERVICES","a02"); //������Ʒ����������֧�����ֽ�a02}   
        setCol2Value("M9805","a22"); //֧����ְ���Լ�Ϊְ��֧�����ֽ�a22}  
        setCol2Value3("PAYMENTSOFALLTYPESOFTAXES","a23","a24","a25"); //֧���ĸ���˰��a23+a24+a25}         
        setCol2Value2("M9809","a03","a26"); //֧���������뾭Ӫ��йص��ֽ�a03+a26} 
        setCol2Value7("M9831","a02","a22","a23","a24","a25","a03","a26"); //��Ӫ��ֽ�����С��} 9803+9805+9807+9809  
        
        setCol2ValueLDZJJE("M9813","a01","a17","a18","a16","a19"  ,"a02","a22","a23","a24","a25","a03","a26"); //��Ӫ��������ֽ��������� }     9823-9831
        
        setCol2Value("M9815","a28"); //�ջ�Ͷ�����յ����ֽ�a28}   
        setCol2Value("CASHRECEIVEDFROMONVESTMENTS","a30"); //ȡ��Ͷ���������յ����ֽ�a30}      
        setCol2Value("M9819","a31"); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ𾻶�a31}
        setCol2Value2("M9821","a29","a32"); //�յ���������Ͷ�ʻ�йص��ֽ�a29+a32}                	 
        setCol2Value5Jia("M9917","a28","a30","a31","a29","a32"); //Ͷ�ʻ�ֽ�����С��}    9815+9817+9819+9821
               
        setCol2Value("M9825","a34"); //�����̶��ʲ������ʲ������������ʲ���֧�����ֽ�a34}    
        setCol2Value2("CASHPAYMENTSFORINVESTMENTS","a35","a36"); //Ͷ����֧�����ֽ�}     a35+a36  
        setCol2Value("M9829","a37"); //֧����������Ͷ�ʻ�йص��ֽ�a37}  
        setCol2Value4Jia("M9919","a34","a35","a36","a37"); //Ͷ�ʻ�ֽ�����С��}        9825+9827+9829
        
        setCol2Value9("M9833","a28","a30","a31","a29","a32","a34","a35","a36","a37"); //Ͷ�ʻ�������ֽ���������}    9917-9919
        
        setCol2Value("CASHRECEIVEDFROMINVESTORS","a39"); //����Ͷ�����յ����ֽ�a39}          
        setCol2Value("CASHFROMBORROWINGS","a41"); //������յ����ֽ�a41}       
        setCol2Value2("M9839","a40","a42"); //�յ�����������ʻ�йص��ֽ�a40+a42}  
        setCol2Value4Jia("M9921","a39","a41","a40","a42"); //���ʻ�ֽ�����С��} 9835+9837+9839
        
        setCol2Value("CASHREPAYMENTSFORDEBTS","a44"); //����ծ����֧�����ֽ�a44}        
        setCol2Value("M9845","a46"); //�������������򳥸���Ϣ��֧�����ֽ�a46}           
        setCol2Value4Jia("M9847","a45","a47","a48","a49"); //֧������������ʻ�йص��ֽ�a45+a47+a48+a49}  
        setCol2Value6Jia("M9923","a44","a46","a45","a47","a48","a49"); //���ʻ�ֽ�����С��}   9843+9845+9847
        //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
        setCol2Value10("M9851","a39","a41","a40","a42" , "a44","a46","a45","a47","a48","a49"); //�Ｏ��������ֽ���������}  9921-9923
        
        setCol2Value("M9853","a51"); //���ʱ䶯���ֽ��Ӱ��a51}   
        ////�ֽ��ֽ�ȼ��ﾻ���Ӷ�,"a01","+a17","a18","+a16","a19"  - ,"a02","a22","a23","a24","a25","a03","a26"        +'"a28","+a30","+a31","+a29","+a32"   - ,"+a34","+a35","+a36","+a37"'    + '"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"'     +"a51"
        setCol2ValueXJDJWJZJE("M9855","a01","a17","a18","a16","a19"    ,"a02","a22","a23","a24","a25","a03","a26"     ,"a28","a30","a31","a29","a32"     ,"a34","a35","a36","a37"  ,"a39","a41","a40","a42" ,  "a44","a46","a45","a47","a48","a49","a51"); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�1}   9813+9833+9851+9853

        setCol2Value("NETPROFIT","z85"); //������z85}              
        //setCol2Value("PROVISIONFORASSETS",""); //������ʲ���ֵ׼��""}    
        setCol2Value("DEPRECIATIONOFFIXEDASSETS","z87"); //�̶��ʲ����z87}           
        setCol2Value("M9863","z88"); //�����ʲ�̯��z88}          
        setCol2Value("M9865","z78"); //���ڴ�̯����̯��z78}      
        //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //��̯���ü���""}           
        //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //Ԥ���������""}           
        setCol2Value("M9871","z89"); //���ù̶��ʲ������ʲ������������ʲ�����ʧz89}         	
        setCol2Value("M9873","z90"); //�̶��ʲ�������ʧz90}      
        setCol2Value("FINANCEEXPENSE","z91"); //�������z91}             
        setCol2Value("LOSSESARSINGFROMINVESTMENT","z92"); //Ͷ����ʧ}z92              
        setCol2Value("DEFERREDTAXCREDIT","z93"); //����˰�����z93}           
        setCol2Value("DECREASEININVENTORIES","z94"); //����ļ���z94}            
        setCol2Value("M9883","z95"); //��Ӫ��Ӧ����Ŀ�ļ���z95}         
        setCol2Value("M9885","z96"); //��Ӫ��Ӧ����Ŀ������z96}          
        setCol2Value("OTHERS1","z98"); //���������������Ϊ��Ӫ��ֽ�������Ŀ�£�z98}       	
        setCol2Value("M1813","z99"); //��Ӫ��������ֽ���������1z99}    
        //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //ծ��תΪ�ʱ�""}          
        //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //һ���ڵ��ڵĿ�ת����˾ծȯ""}               		 
        //setCol2Value("M9895",""); //��������̶��ʲ�""}      
        //setCol2Value("OTHERS2",""); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ��""}       	
        setCol2Value("CASHATTHEENDOFPERIOD","z101"); //�ֽ����ĩ���z101}        
        setCol2Value("M9901","z102"); //�ֽ���ڳ����z102}         
        setCol2Value("M9903","z103"); //�ֽ�ȼ������ĩ���z103}   
        setCol2Value("M9905","z104"); //�ֽ�ȼ�����ڳ����z104}   
        //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" + "a51"'    +'"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"' ,+'"a51"')
        setCol2ValueXJDJWJZJE2("M1855","a39","a41","a40","a42" ,  "a44","a46","a45","a47","a48","a49" , "a51" ,"a01","a17","a18","a16","a19"   ,"a02","a22","a23","a24","a25","a03","a26","a51" ); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�2} 9851+9853+9813+9833
        
        
        //2007���ʲ���ծ
        setCol2Value("CURRENCYFUNDS","101"); //�����ʽ�101
        setCol2Value("M9101","631"); //�����Խ����ʲ�   631    
        setCol2Value("NOTESRECEIVABLE","103"); //Ӧ��Ʊ��  103          	
        setCol2Value("ACCOUNTSRECEIVABLE","106"); //Ӧ���˿�  106          	
        setCol2Value("PREPAYMENTS","107"); //Ԥ���˿�   107         	
        setCol2Value("INTERESTRECEIVABLE","19c"); //Ӧ����Ϣ   19c         	
        setCol2Value("DIVIDENDSRECEIVABLE","134"); //Ӧ�չ��� 134           	
        setCol2Value("OTHERRECEIVABLES","108"); //����Ӧ�տ�  108         
        setCol2Value("INVENTORIES","110"); //���       110        	
        setCol2Value("M9109","632"); //һ���ڵ��ڵķ������ʲ� 632
        setCol2Value2("OTHERCURRENTASSETS","115","109"); //���������ʲ�    115+109     
        setCol2Value12Jia("TOTALCURRENTASSETS","101","631","103","106","107","19c","134","108","110","632","115","109"); //�����ʲ��ϼ� //9100+9101+9102+9103+9104+9105+9106+9107+9108+9109+9110
        
        setCol2Value("M9112","633"); //�ɹ����۵Ľ����ʲ�   633
        setCol2Value("M9113","634"); //����������Ͷ��       634
        setCol2Value("LONGTERMEQUITYINVESTMENT","648"); //���ڹ�ȨͶ��   648      
        setCol2Value("LONGTERMRECEIVABLES","635"); //����Ӧ�տ�       635    
        setCol2Value("INVESTMENTPROPERTIES","636"); //Ͷ���Է��ز�   636      
        setCol2Value("FIXEDASSETS","119"); //�̶��ʲ�    119        	
        setCol2Value("CONSTRUCTIONINPROGRESS","120"); //�ڽ����� 120           	
        setCol2Value("CONSTRUCTIONMATERIALS","127"); //��������  127          	
        setCol2Value("M9120","121"); //�̶��ʲ�����  121       
        setCol2Value("M9121","637"); //�����������ʲ�  637     
        setCol2Value("OILANDGASASSETS","638"); //�����ʲ� 638          	
        setCol2Value("INTANGIBLEASSETS","129"); //�����ʲ�  129          	
        setCol2Value("DEVELOPMENTDISBURSEMENTS","130"); //����֧��130           	
        setCol2Value("GOODWILL","131"); //����       131       	 
        setCol2Value("LONGTERMDEFERREDEXPENSES","132"); //���ڴ�̯����       132  
        setCol2Value("DEFERREDTAXASSETS","133"); //��������˰�ʲ� 133     	
        setCol2Value2("OTHERNONCURRENTASSETS","639","125"); //�����������ʲ�   639+125    
        setCol2Value18Jia("TOTALNONCURRENTASSETS","633","634","648","635","636","119","120","127","121","637","638","129","130","131","132","133","639","125"); //�������ʲ��ϼ�     9112+9113+9114+9115+9116+9117+9118+9119+9120+9121+9122+9123+9124+9125+9126+9127+9128  
       
        setCol2Value30Jia("TOTALASSETS","101","631","103","106","107","19c","134","108","110","632","115","109","633","634","648","635","636","119","120","127","121","637","638","129","130","131","132","133","639","125"); //�ʲ��ܼ�  9111+9129          	
        
        setCol2Value("SHORTTERMBORROWINGS","201"); //���ڽ��          201  	
        setCol2Value("M9132","640"); //�����Խ��ڸ�ծ  640     
        setCol2Value("NOTESPAYABLE","202"); //Ӧ��Ʊ��  202          	
        setCol2Value("ACCOUNTSPAYABLE","203"); //Ӧ���˿�  203           
        setCol2Value("RECEIPTSINADVANCE","204"); //Ԥ���˿� 204           	
        setCol2Value("INTERESTPAYABLE","274"); //Ӧ����Ϣ274            	
        setCol2Value("EMPLOYEEBENEFITSPAYABLE","206"); //Ӧ��ְ��н��  206       
        setCol2Value("TAXSPAYABLE","651"); //Ӧ��˰��     651       	
        setCol2Value("DIVIDENDSPAYABLE","641"); //Ӧ������  641          	
        setCol2Value2("OTHERPAYABLES","209","210"); //����Ӧ���� 209+210          
        setCol2Value("M9141","643"); //һ���ڵ��ڵķ�������ծ 643
        setCol2Value("OTHERCURRENTLIABILITIES","212"); //����������ծ   212    
        setCol2Value13Jia("TOTALCURRENTLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212"); //������ծ�ϼ� 9131+9132+9133+9134+9135+9136+9137+9138+9139+9140+9141+9142        
        
        setCol2Value("LONGTERMBORROWINGS","213"); //���ڽ��213            	
        setCol2Value("BONDSPAYABLES","214"); //Ӧ��ծȯ    214        	
        setCol2Value("LONGTERMPAYABLES","215"); //����Ӧ����    215       
        setCol2Value("GRANTSPAYABLE","644"); //ר��Ӧ����     644      
        setCol2Value("PROVISIONS","647"); //Ԥ�Ƹ�ծ            	
        setCol2Value("DEFERREDTAXLIABILITIES","652"); //��������˰��652ծ       
        setCol2Value("M9150","645"); //������������ծ       
        setCol2Value7Jia("M9151","213","214","215","644","647","652","645"); //��������ծ�ϼ�   9144+9145+9146+9147+9148+9149+9150
        
        setCol2Value20Jia("TOTALLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645"); //��ծ�ϼ�            	9143+9151
        
        setCol2Value("M9153","301"); //ʵ���ʱ�����ɱ���   
        setCol2Value("CAPITALRRSERVE","302"); //�ʱ�����   302          
        setCol2Value("LESSTREASURYSTOCKS","646"); //��������           
        setCol2Value("SURPLUSRESERVE","303"); //ӯ�๫��            	
        setCol2Value2("UNAPPROPRIATEDPROFIT","305","307"); //δ��������           
        setCol2Value6H("TOTALEQUITY","301","302","646","303","305","307"); //������Ȩ��ϼ�       9153+9154-9155+9156+9157
        setCol2Value26H("TOTALEQUITYANDLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645"  ,"301","302","646","303","305","307"   ); //��ծ��������Ȩ��ϼ� 9152+9158
        
        //��ҵ2007������������
        setCol2Value("REVENUEOFSALES","501"); //Ӫҵ����                     	
        setCol2Value("COSTOFSALES","502"); //Ӫҵ�ɱ�                    	
        setCol2Value("BUSINESSANDOTHERTAXES","504"); //Ӫҵ˰�𼰸���                
        setCol2Value("SELLINGEXPENSES","503"); //���۷���                      
        setCol2Value("M9174","507"); //�������                     	
        setCol2Value("FINANCIALEXPENSE","508"); //�������508                     	
        setCol2Value("IMPAIRMENTLOSSOFASSETS","653"); //�ʲ���ֵ��ʧ                  
        setCol2Value("M9177","654"); //���ʼ�ֵ�䶯������            
        setCol2Value("INVESTMENTINCOME","510"); //Ͷ�ʾ�����                    
        setCol2Value("M9179",""); //����Ӫ��ҵ�ͺ�Ӫ��ҵ��Ͷ������
        setCol2Value9H("OPERATINGPROFITS","501","502","504","503","507","508","653","654","510"); //Ӫҵ����      9170-9171-9172-9173-9174-9175-9176+9177+9178
        
        setCol2Value("NONOPERATINGINCOME","512"); //Ӫҵ������                    
        setCol2Value("NONOPERATINGEXPENSES","513"); //Ӫҵ��֧��                    
        setCol2Value("NONCURRENTASSETS","524"); //�������ʲ���ʧ                
        setCol2Value11H("PROFITBEFORETAX","501","502","504","503","507","508","653","654","510","512","513"); //�����ܶ�    9180+91819182
        
        setCol2Value("INCOMETAXEXPENSE","516"); //����˰����516                    
        setCol2Value12H("NETPROFIT","501","502","504","503","507","508","653","654","510","512","513" ,"516"); //������               9184-9185         	
        setCol2Value("BASICEARNINGSPERSHARE","657"); //����ÿ������                  
        setCol2Value("DILUTEDEARNINGSPERSHARE","553"); //ϡ��ÿ������   
        
        //��ҵ2007���ֽ�������
        setCol2Value("M9199","601"); //������Ʒ���ṩ�����յ����ֽ�                  
        setCol2Value("TAXREFUNDS","602"); //�յ���˰�ѷ���                             	 
        setCol2Value("M9201","603"); //�յ������뾭Ӫ��йص��ֽ�  
        setCol2Value3("M9202","601","602","603"); //��Ӫ��ֽ�����С��     9199+9200+9201                    	

        setCol2Value("CASHPAIDFORGOODSANDSERVICES","605"); //������Ʒ����������֧�����ֽ�                  
        setCol2Value("M9204","606"); //֧����ְ���Լ�Ϊְ��֧�����ֽ�                
        setCol2Value("PAYMENTSOFALLTYPESOFTAXES","607"); //֧���ĸ���˰��                             	 
        setCol2Value("M9206","608"); //֧�������뾭Ӫ��йص��ֽ�                  
        setCol2Value4Jia("M9207","605","606","607","608"); //��Ӫ��ֽ�����С��
        //"601","602","603"-( "605","606","607","608")
        setCol2Value7H("M9208","601","602","603", "605","606","607","608"); //��Ӫ��������ֽ���������1      9202-9207             
        
        setCol2Value("M9209","611"); //�ջ�Ͷ�����յ����ֽ�                          
        setCol2Value("CASHRECEIVEDFROMONVESTMENTS","612"); //ȡ��Ͷ���������յ����ֽ�                      
        setCol2Value("M9211","613"); //���ù̶��ʲ������ʲ������������ʲ����ջص��ֽ�
        setCol2Value("M9212","614"); //�����ӹ�˾������Ӫҵ��λ�յ����ֽ𾻶�        
        setCol2Value("M9213","615"); //�յ�������Ͷ�ʻ�йص��ֽ�                  
        setCol2Value5Jia("M9214","611","612","613","614","615"); //Ͷ�ʻ�ֽ�����С�� 9209+9210+9211+9212+9213      
        
        setCol2Value("M9215","617"); //�����̶��ʲ������ʲ������������ʲ���֧�����ֽ�
        setCol2Value("CASHPAYMENTSFORINVESTMENTS","618"); //Ͷ����֧�����ֽ�                           	 
        setCol2Value("M9217","618"); //ȡ���ӹ�˾������Ӫҵ��λ֧�����ֽ𾻶�        
        setCol2Value("M9218","620"); //֧��������Ͷ�ʻ�йص��ֽ�                  
        setCol2Value4Jia("SUBTOTALOFCASHOUTFLOWS","617","618","619","620"); //Ͷ�ʻ�ֽ�����С�� 9215+9216+9217+9218
        
        //("M9220","611","612","613","614","615" - ("617","618","619","620"))
        setCol2Value9H1("M9220","611","612","613","614","615" ,"617","618","619","620"); //Ͷ�ʻ�������ֽ���������  9214-9219
        
        setCol2Value("CASHRECEIVEDFROMINVESTORS","622"); //����Ͷ���յ����ֽ�                          	 
        setCol2Value("CASHFROMBORROWINGS","623"); //ȡ�ý���յ����ֽ�                          	 
        setCol2Value("M9223","624"); //�յ���������ʻ�йص��ֽ�                  
        setCol2Value3("M9224","622","623","624"); //���ʻ�ֽ�����С��        9221+9222+9223   
        
        setCol2Value("CASHREPAYMENTSFORDEBTS","626"); //����ծ����֧�����ֽ�                        	 
        setCol2Value("M9226","627"); //�������������򳥸���Ϣ��֧�����ֽ�          
        setCol2Value("M9227","628"); //֧����������ʻ�йص��ֽ�                  
        setCol2Value3("M9228","626","627","628"); //���ʻ�ֽ�����С��     9225+9226+9227
        
        //"622","623","624"-("626","627","628")
        setCol2Value6H1("M9229","622","623","624","626","627","628" ); //�Ｏ��������ֽ���������       9224-9228
        
        setCol2Value("M9230","630"); //���ʱ䶯���ֽ��ֽ�ȼ����Ӱ��              
        //["601","602","603"-( "605","606","607","608")] +["611","612","613","614","615" - ("617","618","619","620")]  + ["622","623","624"-("626","627","628"] +"630"
        setCol2Value23H("M9231", "601","602","603", "605","606","607","608","611","612","613","614","615","617","618","619","620","622","623","624","626","627","628" ,"630"); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�     9208+9220+9229+9230
        
        //setCol2Value("M9232",""); //�ڳ��ֽ��ֽ�ȼ������  ""     
        
        setCol2Value23H("M9233", "601","602","603", "605","606","607","608","611","612","613","614","615","617","618","619","620","622","623","624","626","627","628" ,"630" ); //��ĩ�ֽ��ֽ�ȼ������   9231+9232                   
        //setCol2Value("NETPROFIT",""); //������                                  	
        //setCol2Value("PROVISIONFORASSETIMPAIRMENT",""); //�ʲ���ֵ׼��                              	
        //setCol2Value("DEPRECIATIONOFFIXEDASSETS",""); //�̶��ʲ��۾ɡ������ʲ��ۺġ������������ʲ��۾�
        //setCol2Value("M9237",""); //�����ʲ�̯��                             	
        //setCol2Value("M9238",""); //���ڴ�̯����̯��                           	 
        //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //��̯���ü���                             	
        //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //Ԥ���������                             	
        //setCol2Value("M9241",""); //���ù̶��ʲ������ʲ������������ʲ�����ʧ      
        //setCol2Value("M9242",""); //�̶��ʲ�������ʧ                            
        //setCol2Value("M9243",""); //���ʼ�ֵ�䶯��ʧ                            
        //setCol2Value("FINANCEEXPENSE",""); //�������                                	
        //setCol2Value("LOSSESARSINGFROMINVESTMENT",""); //Ͷ����ʧ                                	
        //setCol2Value("DEFERREDINCOMETAXASSETS",""); //��������˰�ʲ�����                          
        //setCol2Value("M9247",""); //��������˰��ծ����                          	 
        //setCol2Value("DECREASEININVENTORIES",""); //����ļ���                               	
        //setCol2Value("M9250",""); //��Ӫ��Ӧ����Ŀ�ļ���                         	
        //setCol2Value("M9251",""); //��Ӫ��Ӧ����Ŀ������                        	 
        //setCol2Value("OTHERS",""); //�����������Ϊ��Ӫ��ֽ�������Ŀ�£�����    
        //setCol2Value("M9252",""); //��Ӫ��������ֽ���������2                   
        //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //ծ��תΪ�ʱ�                              	
        //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //һ���ڵ��ڵĿ�ת����˾ծȯ                    
        //setCol2Value("M9255",""); //��������̶��ʲ�                             	
        //setCol2Value("NONCASHOTHERS",""); //���������漰�ֽ���֧��Ͷ�ʺͳ��ʻ��Ŀ�£�  
        //setCol2Value("CASHATTHEENDOFPERIOD",""); //�ֽ����ĩ���                              
        //setCol2Value("M9258",""); //�ֽ���ڳ����                               
        //setCol2Value("M9259",""); //�ֽ�ȼ������ĩ���                         	
        //setCol2Value("M9260",""); //�ֽ�ȼ�����ڳ����                         	
        //setCol2Value("M9261",""); //�ֽ��ֽ�ȼ��ﾻ���Ӷ�
        
        
        //��ҵ��λ�ʲ���ծ
        setCol2Value("CURRENCYFUNDS",""); //�����ʽ�     ����
        setCol2Value("SHORTTERMINVESTMENTS",""); //����Ͷ��    ??    
        setCol2Value("M9408",""); //����Ӧ�������  ����
        setCol2Value("NOTESRECEIVABLE","103"); //Ӧ��Ʊ�� 103       
        setCol2Value("ACCOUNTSRECEIVABLE","19f"); //Ӧ���˿�19f        
        setCol2Value("PREPAYMENTS","19n"); //Ԥ���˿�     19n   
        setCol2Value("OTHERRECEIVABLES","19n"); //����Ӧ�տ�   19n   
        setCol2Value("INVENTORIES",""); //���          	
        setCol2Value("OTHERCURRENTASSETS",""); //���������ʲ�    ???
        setCol2Value("TOTALCURRENTASSETS",""); //�����ʲ��ϼ�   ??? 
        setCol2Value("LONGTERMINVESTMENT",""); //����Ͷ��        
        setCol2Value("FIXEDASSETS","119"); //�̶��ʲ�        
        setCol2Value("M9407",""); //�̶��ʲ�ԭ��    ??
        setCol2Value("M9401",""); //�ۼ��۾�      ??  
        setCol2Value("CONSTRUCTIONINPROCESS",""); //�ڽ�����      ??  
        setCol2Value3("INTANGIBLEASSETS","123","120","19g"); //�����ʲ�      123+120+19g  
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
        setCol2Value("RECEIPTSINADVANCE","204"); //Ԥ���˿�       	
        setCol2Value("OTHERPAYABLES","209"); //����Ӧ����      
        setCol2Value("OTHERCURRENTLIABILITIES",""); //����������ծ    ??
        setCol2Value("TOTALCURRENTLIABILITIES",""); //������ծ�ϼ�    ??
        setCol2Value("LONGTERMBORROWINGS",""); //���ڽ��       	??
        setCol2Value("LONGTERMPAYABLES",""); //����Ӧ����      ??
        setCol2Value("M9405",""); //��������ծ�ϼ�  ??
        setCol2Value4Jia("TOTALLIABILITIES","202","203","204","209"); //��ծ�ϼ�     9295+9296+9297+9298+9299+9300+9301+9302
        
        setCol2Value("ENTERPRISEFUND","319"); //��ҵ����       	
        setCol2Value("NONCURRENTASSETSFUND",""); //�������ʲ�����  ??
        setCol2Value("SPECIALPURPOSEFUNDS",""); //ר�û���       	??
        setCol2Value("FINANCIALAIDCARRIEDOVER",""); //����������ת    
        setCol2Value("FINANCIALAIDBALANCE",""); //������������    
        setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //�ǲ���������ת  
        setCol2Value("NONFINANCIALAIDBALANCE",""); //�ǲ�����������  
        setCol2Value("UNDERTAKINGSBALANCE","322"); //��ҵ����        
        setCol2Value2("OPERATINGBALANCE","19x","277"); //��Ӫ����       19x+277	
        setCol2Value3("TOTALNETASSETS","322","19x","277"); //���ʲ��ϼ�    9304+9306+9307+9308+9309+9310  
        setCol2Value("M9406",""); //��ծ�;��ʲ��ܼ�
        
        //��ҵ��λ����֧��
        setCol2Value("M9501",""); //���ڲ���������ת����    û��   ������
        setCol2Value("FINANCIALSUBSIDYREVENUE","559"); //������������       559      
        setCol2Value("M9345","5b2"); //��ҵ֧������������֧����              ��������֧��
        setCol2Value("M9502",""); //������ҵ��ת����      9336-9350 ��ҵ����    
        setCol2Value("UNDERTAKINGSCLASSREVENUE",""); //��ҵ������   ����        
        setCol2Value("UNDERTAKINGSREVENUE","562"); //��ҵ����                
        setCol2Value("SUPERIORSUBSIDYREVENUE","560"); //�ϼ���������    560         
        setCol2Value("M9503","565"); //������λ�Ͻ�����          	
        setCol2Value("OTHERREVENUE","571"); //��������               
        setCol2Value("DONATIONINCOME",""); //�����������Ŀ�£��������� ������
        setCol2Value("UNDERTAKINGSCLASSEXPENDITURE",""); //��ҵ��֧��              	 
        setCol2Value("M9505",""); //��ҵ֧�����ǲ�������֧����      ����������
        setCol2Value("PAYMENTTOTHEHIGHERAUTHORITY","556"); //�Ͻ��ϼ�֧��             	 
        setCol2Value("M9508","557"); //�Ը�����λ����֧��        	
        setCol2Value("OTHEREXPENDITURE",""); //����֧��        ��������       	 
        setCol2Value("CURRENTOPERATINGBALANCE",""); //���ھ�Ӫ����            ������	 
        setCol2Value("OPERATINGREVENUE","564"); //��Ӫ����               
        setCol2Value("OPERATINGEXPENDITURE","552"); //��Ӫ֧��               	 
        setCol2Value("M9506","z81"); //�ֲ���ǰ��ȿ����ľ�Ӫ����} ��ǰ��Ⱦ�Ӫ����
        setCol2Value("M9507",""); //����ǲ���������ת����     ����
        setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //�ǲ���������ת            ����	
        setCol2Value("NONFINANCIALAIDBALANCETHISYEAR",""); //����ǲ�����������        	
        setCol2Value("ENTERPRISEINCOMETAXPAYABLE","z82"); //Ӧ����ҵ����˰         
        setCol2Value("SPECIALFUNDSTOEXTRACT","z85"); //��ȡר�û���             	 
        setCol2Value("PUBLICFUNDTURNEDINTO","578"); //ת����ҵ����
*/        
    }
    
    private void setCol2Value23H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22, String rowSubject23) throws SQLException{
    	
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
    //  //["601","602","603"-( "605","606","607","608")] +["611","612","613","614","615" - ("617","618","619","620")]  + ["622","623","624"-("626","627","628"] +"630"
    	double col2Value= a1+a2+a3 -(a4+a5+a6+a7) +(a8+a9+a10+a11+a12) -( a13 +a14+a15+a16) +a17+a18+a19- (a20+a21 + a22)+a23  ;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//"601","602","603"-( "605","606","607","608"
    private void setCol2Value7H(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	double col2Value=a1+a2+a3-(a4+a5+a6+a7);
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//"501","-502","-504","-503","-507","-508","-653","654","510","512","-513" ,"-516"
    private void setCol2Value12H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10, String rowSubject11, String rowSubject12) throws SQLException {
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
    	double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10-a11-a12;
    	currentRecord.getField(item).setValue(col2Value);
		
	}

	//"501","-502","-504","-503","-507","-508","-653","654","510","512","-513"
    private void setCol2Value11H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10, String rowSubject11) throws SQLException {
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
    	double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9+a10-a11;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//"501","-502","-504","-503","-507","-508","-653","654","510"
    private void setCol2Value9H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	double a8 = getCol2Value(rowSubject8);
    	double a9 = getCol2Value(rowSubject9);
    	double col2Value=a1-a2-a3-a4-a5-a6-a7+a8+a9;
    	currentRecord.getField(item).setValue(col2Value);
	}
  //("M9220","611","612","613","614","615" - ("617","618","619","620"))
    private void setCol2Value9H1(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	double a8 = getCol2Value(rowSubject8);
    	double a9 = getCol2Value(rowSubject9);
    	double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9);
    	currentRecord.getField(item).setValue(col2Value);
	}
    
	//"201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645" +,"301","302","-646","303","305","307"
    private void setCol2Value26H(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26) throws SQLException {
    	
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
    
    	double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19+a20+a21 + a22-a23+a24+a25 +a26  ;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//,"301","302","-646","303","305","307"
	private void setCol2Value6H( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double col2Value=a1+a2-a3+a4+a5+a6;
    	currentRecord.getField(item).setValue(col2Value);
	}
	//"622","623","624"-("626","627","628")
	private void setCol2Value6H1( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double col2Value=a1+a2+a3-(a4+a5+a6);
    	currentRecord.getField(item).setValue(col2Value);
	}
	private void setCol2Value20Jia(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20) throws SQLException {
    	
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
    
    	double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19+a20;
    	currentRecord.getField(item).setValue(col2Value);
		
		
	}

	private void setCol2Value7Jia(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	double col2Value=a1+a2+a3+a4+a5+a6+a7;
    	currentRecord.getField(item).setValue(col2Value);
		
		
	}

	private void setCol2Value13Jia(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13) throws SQLException {
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
    	//"501","-502","-504","-503","+506","-507","-508","+510","+532","+511","+512","+514","-513"  ,"-516","-523"
    	double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13 ;
    	currentRecord.getField(item).setValue(col2Value);
		
	}

	private void setCol2Value30Jia(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29, String rowSubject30) throws SQLException {
    	
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
    
    	double col2Value= a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12 + a13 +a14+a15+a16+a17+a18+a19+a20+a21 + a22+a23+a24+a25 +a26+a27+a28+a29+a30;
    	currentRecord.getField(item).setValue(col2Value);
		
		
	}

	private void setCol2Value18Jia(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18) throws SQLException {
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
		    	double col2Value= a1+a2+a3+a4 +a5+a6+a7+a8+a9+a10+a11 + a12+a13 +a14+a15+a16+ a17+a18 ;
		    	currentRecord.getField(item).setValue(col2Value);
		
	}

	private void setCol2Value12Jia(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12) throws SQLException {
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
    	
    	double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12;
    	currentRecord.getField(item).setValue(col2Value);
		
	}

	private void setCol2ValueXJDJWJZJE2(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23 ,String rowSubject24) throws SQLException {
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
		    	 //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" + "a51"'    +'"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"' ,+'"a51"')
		    	double col2Value=(a1+a2+a3+a4) - (a5+a6+a7+a8+a9+a10+a11)+(a12+a13 +a14+a15+a16)     - ( a17+a18+a19+a20+a21   +  a22+a23) +a24;
		    	currentRecord.getField(item).setValue(col2Value);
		}

	////�ֽ��ֽ�ȼ��ﾻ���Ӷ�,"a01","+a17","+a18","+a16","a19"  - ,"a02","a22","a23","a24","a25","a03","a26"        +'"a28","+a30","+a31","+a29","+a32"   - ,"+a34","+a35","+a36","+a37"'    + '"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"'     +"a51"
    private void setCol2ValueXJDJWJZJE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29, String rowSubject30,
			String rowSubject31, String rowSubject32) throws SQLException {
    	
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
    	double a31 = getCol2Value(rowSubject31);
    	double a32 = getCol2Value(rowSubject32);
    
    	//"a01","+a17","+a18","+a16","a19"  - ,"a02","a22","a23","a24","a25","a03","a26"        +'"a28","+a30","+a31","+a29","+a32"   - ,"+a34","+a35","+a36","+a37"'    + '"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"'     +"a51"
    	double col2Value=(a1+a2+a3+a4+a5) - (a6+a7+a8+a9+a10+a11+a12)+(a13 +a14+a15+a16+a17)     - (a18+a19+a20+a21)  + (a22+a23+a24+a25) -(a26+a27+a28+a29+a30+a31)+a32;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
    private void setCol2Value10(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10) throws SQLException {
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
    	
    	double col2Value=a1+a2+a3+a4-(a5+a6+a7+a8+a9+a10);
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2Value6Jia( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double col2Value=a1+a2+a3+a4+a5+a6;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//,"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"
    private void setCol2ValueLDZJJE( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12) throws SQLException {
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
    	
    	double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9+a10+a11+a12);
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//"501","-502","-504","-503","+506","-507","-508","+510","+532","+511","+512","+514","-513"  ,"-516","-523"
    private void setCol2ValueKGFPLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14, String rowSubject15) throws SQLException {
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
    	//"501","-502","-504","-503","+506","-507","-508","+510","+532","+511","+512","+514","-513"  ,"-516","-523"
    	double col2Value=a1-a2-a3-a4+a5-a6-a7+a8+a9+a10+a11+a12-a13   -a14-a15;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2ValueJLR(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14, String rowSubject15) throws SQLException {
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
		    	//"501","-502","-504","-503","+506","-507","-508","+510","+532","+511","+512","+514","-513"  ,"-516","-523"
		    	double col2Value=a1-a2-a3-a4+a5-a6-a7+a8+a9+a10+a11+a12-a13   -a14-a15;
		    	currentRecord.getField(item).setValue(col2Value);
		    	
		
	}
	//,"501","-502","-504","-503","+506","-507","-508","+510","+532","+511","+512","+514","-513"
    private void setCol2ValueLRZE(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13) throws SQLException {
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
		    	double col2Value=a1-a2-a3-a4+a5-a6-a7+a8+a9+a10+a11+a12-a13;
		    	currentRecord.getField(item).setValue(col2Value);
				
		
	}
	//,"501","-502","-504","-503","+506","-507","-508"
    private void setCol2ValueYELR( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7 ) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	double col2Value=a1-a2-a3-a4+a5-a6-a7;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2Value4J(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1-a2-a3-a4;
    	currentRecord.getField(item).setValue(col2Value);
	}
	
	private void setCol2Value4Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1+a2+a3+a4;
    	currentRecord.getField(item).setValue(col2Value);
	}
	
	private void setCol2Value9(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9) throws SQLException {
		    	double a1 = getCol2Value(rowSubject1);
		    	double a2 = getCol2Value(rowSubject2);
		    	double a3 = getCol2Value(rowSubject3);
		    	double a4 = getCol2Value(rowSubject4);
		    	double a5 = getCol2Value(rowSubject5);
		    	double a6 = getCol2Value(rowSubject6);
		    	double a7 = getCol2Value(rowSubject7);
		    	double a8 = getCol2Value(rowSubject8);
		    	double a9 = getCol2Value(rowSubject9);
		    	double col2Value=a1+a2+a3+a4+a5-(a6+a7+a8+a9);
		    	currentRecord.getField(item).setValue(col2Value);
		
	}
	
	private void setCol2ValueFZHSYZQY(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10) throws SQLException {
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
		    	double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10;
		    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2Value4H(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1+a2-a3-a3;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2ValueFZHJ(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21) throws SQLException {
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
		
	}
	
	private void setCol2Value14(String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,String rowSubject15) throws SQLException {
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
    	double col2Value=a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2ValueZCZJ( String item, String rowSubject1, String rowSubject2,	String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,String rowSubject19, String rowSubject20, String rowSubject21, String rowSubject22,
			String rowSubject23, String rowSubject24, String rowSubject25, String rowSubject26,	String rowSubject27, String rowSubject28, String rowSubject29, String rowSubject30,
			String rowSubject31, String rowSubject32, String rowSubject33, String rowSubject34,String rowSubject35, String rowSubject36, String rowSubject37, String rowSubject38,
			String rowSubject39) throws SQLException {
    	
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
    	double a31 = getCol2Value(rowSubject31);
    	double a32 = getCol2Value(rowSubject32);
    	double a33 = getCol2Value(rowSubject33);
    	double a34 = getCol2Value(rowSubject34);
    	double a35 = getCol2Value(rowSubject35);
    	double a36 = getCol2Value(rowSubject36);
    	double a37 = getCol2Value(rowSubject37);
    	double a38 = getCol2Value(rowSubject38);
    	double a39 = getCol2Value(rowSubject39);
    
    	//a1+(a2-a3)+a4+a5+a6+(a7+a8+a9-a10)+(a11+a12+a13) +a14+a15+(a16-a17)+a18+a19+a20+a21
    	//double col2Value=a1-a2+a3+a4+a5;
    	//double col2Value=a1-a2-a3+a4+(a5-a6)+a7;
    	//double col2Value=a1-a2+a3+a4+a5;
    	//126
    	double col2Value=a1+(a2-a3)+a4+a5+a6+(a7+a8+a9-a10)+(a11+a12+a13) +a14+a15+(a16-a17)+a18+a19+a20+a21  + (a22-a23+a24+a25+a26) +(a27-a28-a29+a30+(a31-a32)+a33)+(a34-a35+a36+a37+a38) +a39;
    	currentRecord.getField(item).setValue(col2Value);
	}
	//123-199+124+125+181
    private void setCol2Value5H(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4, String rowSubject5) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double col2Value=a1-a2+a3+a4+a5;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2Value7(String item, String rowSubject1, String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double a6 = getCol2Value(rowSubject6);
    	double a7 = getCol2Value(rowSubject7);
    	//117-118-128+121+(120-19a)+122 
    	double col2Value=a1-a2-a3+a4+(a5-a6)+a7;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	private void setCol2Value3J(String item, String rowSubject1, String rowSubject2, String rowSubject3) throws SQLException {
       	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double col2Value=a1-a2-a3;
    	currentRecord.getField(item).setValue(col2Value);
		
		
	}
	private void setCol2Value5(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4, String rowSubject5) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double col2Value=a1+a2-a3-a4+a5;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	
	private void setCol2Value5Jia(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4, String rowSubject5) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double a5 = getCol2Value(rowSubject5);
    	double col2Value=a1+a2+a3+a4+a5;
    	currentRecord.getField(item).setValue(col2Value);
		
	}
	//116+197-196-198
    private void setCol2Value4(String item, String rowSubject1, String rowSubject2,String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1+a2-a3-a3;
    	currentRecord.getField(item).setValue(col2Value);
	}

	private void setCol2ValueLDZCHJ(String item, String rowSubject1,String rowSubject2,
			String rowSubject3, String rowSubject4, String rowSubject5, String rowSubject6,
			String rowSubject7, String rowSubject8, String rowSubject9, String rowSubject10,
			String rowSubject11, String rowSubject12, String rowSubject13, String rowSubject14,
			String rowSubject15, String rowSubject16, String rowSubject17, String rowSubject18,
			String rowSubject19, String rowSubject20, String rowSubject21) throws SQLException {
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

    	double col2Value=a1+(a2-a3)+a4+a5+a6+(a7+a8+a9-a10)+(a11+a12+a13) +a14+a15+(a16-a17)+a18+a19+a20+a21;
    	currentRecord.getField(item).setValue(col2Value);
	}

	private void setCol2Value2J(String item, String rowSubject1, String rowSubject2) throws SQLException {
       	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double col2Value=a1-a2;
    	currentRecord.getField(item).setValue(col2Value);
		
	}

	private void setCol2Value3(String item, String rowSubject1, String rowSubject2,
			String rowSubject3) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double col2Value=a1+a2+a3;
    	currentRecord.getField(item).setValue(col2Value);
		
	}


	private void setCol2Value2(String item, String rowSubject1,String rowSubject2, String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1+a2+a3-a4;
    	currentRecord.getField(item).setValue(col2Value);
    }

    
    private void setPlusCol2Value(String item, String rowSubject1,String rowSubject2, String rowSubject3, String rowSubject4) throws SQLException {
    	double a1 = getCol2Value(rowSubject1);
    	double a2 = getCol2Value(rowSubject2);
    	double a3 = getCol2Value(rowSubject3);
    	double a4 = getCol2Value(rowSubject4);
    	double col2Value=a1+a2+a3-a4;
    	currentRecord.getField(item).setValue(col2Value);
    }

	private double getCol2Value(String rowSubject1) throws SQLException {
		
        ps.setString(1, sReportNo);
        ps.setString(2, rowSubject1);
        ResultSet rs = ps.executeQuery();
        double col2Value = 0.0;
        if (rs.next()) {
            col2Value =  col2Value = rs.getDouble(1);;
        }
        rs.close();
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
        ps.setString(1, sReportNo);//currentRecord.getField("RecordNo").getString()
        ps.setString(2, rowSubject);
        ResultSet rs = ps.executeQuery();
        String col2Value = null;
        if (rs.next()) {
            col2Value = rs.getString(1);
        }
        rs.close();
        currentRecord.getField(item).setValue(col2Value);
    }

    public void open(RecordSet recordSet) throws RecordSetException {
    	
        super.open(recordSet);
        try {
	           connection = ARE.getDBConnection(database);
	           conn1 = ARE.getDBConnection(database);
	           
	           String sQueryReportNo ="select ReportNo from report_record where objectno =? ";
	           
	           ps1=conn1.prepareStatement(sQueryReportNo);
	           ps1.setString(1, currentRecord.getField("CUSTOMERID").getString());
	           ResultSet rs = this.ps1.executeQuery();
	           while(rs.next()){
	        	   sReportNo=rs.getString("ReportNo");
	        	   ps = connection.prepareStatement("select col2Value from REPORT_DATA where reportNo = ? and rowSubject = ?");
	           }
	           
	           
	           
	          
	           logger.debug("�����ӳɹ�");
        } catch (SQLException e) {
            logger.debug("�����ӳ���", e);
            throw new RecordSetException(e);
        }
    }
    
    /**
     * �ر����ݿ���Դ
     */
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
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.debug("�ر�connection�ɹ�");
            } catch (SQLException e) {
                logger.debug("�ر�connection����", e);
            }
        }
        super.close();
    }

}


