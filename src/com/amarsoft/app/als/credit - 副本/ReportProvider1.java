/*
 * 文件名：ReportProvider.java
 * 版权：Copyright by www.amarsoft.com
 * 描述：
 * 修改人：amarsoft
 * 修改时间：2018年9月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
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
        //企业2002版资产负债表信息
        setCol2Value("MainRevenuefRevenue","501");
        setCol2Value("MainOperatingCost","502");
        setCol2Value("CURRENCYFUNDS","101");//货币资金101
        setCol2Value2("SHORTTERMINVESTMENTS","102","195");//短期投资 102-195
        /*setCol2Value("NOTESRECEIVBLE","103");//应收票据 103
        setCol2Value("DIVIDENDSRECEIVBLE","134");//应收股利 134
        setCol2Value("INTERESTRECEIVBLE","19c");//应收利息19c
        setPlusCol2Value("ACCOUNTSRECEIVBLE","10405","10406","10407","105");//应收账款 10405+10406+10407-105
        setCol2Value3("OTHERRECEIVBLES","10803","10804","10805");//其他应收款 10803+10804+10805
        setCol2Value("PREPYMENTS","107");//#预付账款107
        //setCol2Value("FUTUREGURNTEE","");//期货保证金""
        setCol2Value("ALLOWNCERECEIVBLE","10806");//应收补贴款10806
        //setCol2Value("EXPORTDRWBCKRECEIVBLE","");//应收出口退税""
        setCol2Value2J("INVENTORIES","110","111");//存货110-111
        //setCol2Value("RWMTERILS","");//存货原材料""
       // setCol2Value("FINISHEDPRODUCTS","");//#存货产成品""
        setCol2Value("DEFERREDEXPENSES","109");//待摊费用109
        setCol2Value("UNSETTLEDGLONCURRENTSSETS","113");//待处理流动资产净损失 113
        setCol2Value("M9533","114");//一年内到期的长期债权投资 114
        setCol2Value("OTHERCURRENTSSETS","115");//其他流动资产115
        setCol2ValueLDZCHJ("TOTLCURRENTSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115");//流动资产合计 9501+9503+9505+9507+9509+9511+9513+9515+9517+9519+9521+9523+9529+9531+9533+9535
        setCol2Value4("LONGTERMINVESTMENT","116","197","196","198");//长期投资116+197-196-198
        //setCol2Value("LONGTERMEQUITYINVESTMENT","");//长期股权投资""
        //setCol2Value("LONGTERMSECURITIESINVESTMENT","");//长期债权投资""
        setCol2Value("INCORPORTINGPRICEDIFFERENCE","135");//合并价差135
        setCol2Value5("TOTLLONGTERMINVESTMENT","116","197","196","198","135");//长期投资合计 9539+9545
        setCol2Value("ORIGINLCOSTOFFIXEDSSET","117");//#固定资产原价 117
        setCol2Value("FIXEDSSETCCUMULTEDDEPRECITION","118");//累计折旧118
        setCol2Value2J("FIXEDSSETSNETVLUE","117","118");//固定资产净值 9549-9551}
        setCol2Value("M9555","128");//固定资产值减值准备 128
        setCol2Value3J("NETVLUEOFFIXEDSSETS","117","118","128");//固定资产净额 9553-9555
        setCol2Value("FIXEDSSETSPENDINGFORDISPOSL","121");//固定资产清理 121
        //setCol2Value("CONSTRUCTIONMTERILS","");//工程物资""
        setCol2Value2J("CONSTRUCTIONINPROGRESS","120","19a");//在建工程  120-19a
        setCol2Value("UNSETTLEDGLONFIXEDSSETS","122");//待处理固定资产净损失   122
        //117-118-128+121+(120-19a)+122 
        setCol2Value7("TOTLFIXEDSSETS","117","118","128"  ,"121" ,"120","19a" ,"122");//固定资产合计 9557+9559+9561+9563+9565
        setCol2Value2J("INTNGIBLESSETS","123","199");//无形资产 123-199
        //setCol2Value("LNDUSERIGHTS","");//无形资产土地使用权 ""
        setCol2Value("DEFERREDSSETS","124");//递延资产  124
        //setCol2Value("INCLUDINGFIXEDSSETSREPIR","");//递延资产固定资产修理 ""
        //setCol2Value("M9577","");//#递延资产固定资产改良支出 ""
        setCol2Value2("OTHERLONGTERMSSETS","125","181");//其他长期资产  125+181
        //setCol2Value("M9581","");//其他长期资产特准储备物资 ""
        //123-199+124+125+181
        setCol2Value5H("M9583","123","199" ,"124","125","181" );//无形及其他资产合计 9569+9573+9579  
        setCol2Value("DEFERREDSSETSDEBITS","126");//递延税款借项 126
      //资产总计 9537+9547+9567+9583+9585
        setCol2ValueZCZJ("TOTLSSETS","101","102","195","103","134","19c","10405","10406","10407","105","10803","10804","10805","107","10806","110","111","109","113","114","115"     ,"116","197","196","198","135"    ,"117","118","128"  ,"121" ,"120","19a" ,"122" ,"123","199" ,"124","125","181" ,"126" );
      
        setCol2Value("SHORTTERMBORROWINGS","201");//短期借款 201
        setCol2Value("NOTESPYBLE","202");//应付票据 202
        setCol2Value("ACCOUNTSPYBLE","203");//应付账款 203
        setCol2Value("RECEIPTSINDVNCE","204");//预收账款 204
        setCol2Value("WGESORSLRIESPYBLES","218");//应付工资 218}
        setCol2Value("EMPLOYEEBENEFITS","205");//应付福利费 205
        setCol2Value3("INCOMEPYBLE","219","230","274");//应付利润 219+230+274
        setCol2Value("TXESPYBLE","207");//应交税金 207
        setCol2Value("OTHERPYBLETOGOVERNMENT","208");//其他应交款 208
        setCol2Value("OTHERPYBLE","209");//其他应付款 209
        setCol2Value("PROVISIONFOREXPENSES","210");//预提费用 210
        //setCol2Value("PROVISIONS","");//#预计负债""
        setCol2Value("M9613","211");//一年内到期的长期负债 211
        setCol2Value("OTHERCURRENTLIBILITIES","212");//其他流动负债 212}
        
        setCol2Value14("TOTLCURRENTLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212");//流动负债合计 9589+9591+9593+9595+9597+9599+9601+9603+9605+9607+9609+9611+9613+9615
        
        setCol2Value("LONGTERMBORROWINGS","213");//长期借款 213 
        setCol2Value("BONDSPYBLE","214");//应付债券 214
        setCol2Value("LONGTERMPYBLES","215");//长期应付款 215
        setCol2Value("GRNTSPYBLE","220");//专项应付款 220
        setCol2Value("OTHERLONGTERMLIBILITIES","216");//其他长期负债 216
        //setCol2Value("SPECILRESERVEFUND","");//其他长期负债特准储备基金 ""
        setCol2Value5Jia("TOTLLONGTERMLIBILITIES","213","214","215","220","216");//长期负债合计 9619+9621+9623+9625+9627
        setCol2Value("DEFERREDTXTIONCREDIT","217");//递延税款贷项 217
        setCol2ValueFZHJ("TOTLLIBILITIES","201","202","203","204","218","205","219","230","274","207","208","209","210","211","212","213","214","215","220","216","217");//负债合计 9617+9631+9633

        setCol2Value("MINORITYINTEREST","307");//少数股东权益 307
        setCol2Value("PIDINCPITL","301");//实收资本 301
        //setCol2Value("NTIONLCPITL","");//国家资本 ""
        //setCol2Value("COLLECTIVECPITL","");//集体资本""
        //setCol2Value("LEGLPERSONSCPITL","");//法人资本""
        //setCol2Value("STTEOWNEDLEGLPERSONSCPITL","");//法人资本国有法人资本""
        //setCol2Value("COLLECTIVELEGLPERSONSCPITL","");//法人资本集体法人资本""
        //setCol2Value("PERSONLCPITL","");//个人资本""
        //setCol2Value("FOREIGNBUSINESSMENSCPITL","");//#外商资本""
        setCol2Value("CPITLRRSERVE","302");//资本公积 302
        setCol2Value("SURPLUSRESERVE","303");//盈余公积 303
       // setCol2Value("STTUTORYSURPLUSRESERVE","");//盈余公积法定盈余公积""
        setCol2Value("PUBLICWELFREFUND","304");//盈余公积公益金304
        //setCol2Value("SUPPLERMENTRYCURRENTCPITL","");//#盈余公积补充流动资本 ""
       // setCol2Value("UNFFIRMEDINVESTMENTLOSS","");//未确认的投资损失""
        setCol2Value("UNPPROPRITEDPROFIT","");//未分配利润 305
      //  setCol2Value("M9669","");//外币报表折算差额""
        setCol2Value4H("TOTLEQUITY","301","302","303","305");//所有者权益合计 9639+9655+9657+9665+9667+9669
        setCol2ValueFZHSYZQY("TOTLEQUITYNDLIBILITIES"   ,"213","214","215","220","216"       ,"301","302","303","305"             ,"307");//负债和所有者权益总计 9635+9671+9637
                  
        */
        /*//企业2002版利润及利润分配信息
        setCol2Value("MAINREVENUEFREVENUE","501"); // 主营业务收入501} 9675       
        //setCol2Value("M9677",""); // 主营业务收入出口产品销售收入""}   
       // setCol2Value("M9679",""); // 主营业务收入进口产品销售收入""}   
        setCol2Value("DISCOUNTANDALLOWANCE",""); // 折扣与拆让""}  9681                  
        setCol2Value("M9683","501"); // 主营业务收入净额9675-9681}     9683       
        setCol2Value("MAINOPERATINGCOST","502"); // 主营业务成本502}        9685          
        //setCol2Value("SALESINCOMEOFEXPORTPRODUCTS",""); // 主营业务成本出口产品销售成本""}  
        setCol2Value("M9689","504"); // 主营业务税金及附加504}     9689     
        setCol2Value("OPERATIONEXPENSE","503"); // 经营费用503}         9693            
        //setCol2Value("OTHERSOPERATINGCOST",""); // 其他（业务成本""）}     9691        
        setCol2Value("DEFERREDINCOME",""); // 递延收益""}       9695              
        setCol2Value("INCOMEFROMSALESAGENCY",""); // 代购代销收入""}     9697            
        setCol2Value("OTHEROPERATINGINCOME",""); // 其他（收入）""}     9699            
        setCol2Value4J("PRINCIPLEBUSINESSPROFIT","501","502","504","503"); // 主营业务利润}  9683-9685-9689-9693-9691+9695+9697+9699       9701       
        setCol2Value("OTHERBUSINESSPROFIT","506"); // 其他业务利润506} 9703                
        setCol2Value("SELLINGEXPENSES",""); // 营业费用""}     9705                
        setCol2Value("M9707","507"); // 管理费用507}  9707                   
        setCol2Value("FINANCIALEXPENSES","508"); // 财务费用508}   9709                 
        //setCol2Value("OTHERSEXPENSES",""); // 其他（费用）}   9907          
        setCol2ValueYELR("OPERATINGPROFITS","501","502","504","503","+506","507","508"); // 营业利润}     9701+9703-9705-9707-9709-9907          9711           
        setCol2Value("INVESTMENTINCOME","510"); // 投资收益510}    9713                 
        setCol2Value("FUTURESINCOME","532"); // 期货收益532}             9715        
        setCol2Value("ALLOWANCEINCOME","511"); // 补贴收入511}     9717                
        //setCol2Value("M9719",""); // 补贴收入补贴前亏损的企业补贴收入""}  9719
        setCol2Value("NONOPERATINGINCOME","512"); // 营业外收入512}                      	
        //setCol2Value("M9723",""); // 营业外收入处置固定资产净收益""}      9721
        //setCol2Value("INCOMEFROMNONCURRENCYTRADE",""); // -营业外收入非货币性交易收益""}       
        //setCol2Value("M9727",""); // 营业外收入出售无形资产收益""}        
        //setCol2Value("INCOMEFROMPENALTY",""); // 营业外收入罚款净收入""}              
        setCol2Value("OTHERSINCOME","514"); // 其他（利润）514}                     	
        //setCol2Value("M9731",""); // 其他用以前年度含量工资节余弥补利润""}
        setCol2Value("NONOPERATINGEXPENSES","513"); // 营业外支出513}                     	
        //setCol2Value("M9735",""); // 营业外支出处置固定资产净损失""}    
        //setCol2Value("M9737",""); // 营业外支出债务重组损失}         
        //setCol2Value("LOSSOFLAWSUITS",""); // 营业外支出罚款支出}             
        //setCol2Value("PAYMENTFORDONATION",""); // 营业外支出捐赠支出}             
        //setCol2Value("OTHERPAYMENTS",""); // 其他支出}                       
        //setCol2Value("BALANCEOFCONTENTSALARY",""); // 其他支出结转的含量工资包干节余}  
        setCol2ValueLRZE("TOTALPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"); // 利润总额}   9747      9711+9713+9715+9717+9721+9909-9733-9743+9745              
        setCol2Value("INCOMETAX","516"); // 所得税516}                         
        setCol2Value("IMPARIMENTLOSS","523"); // 少数股东损益523}                   
        //setCol2Value("UNREALIZEDINVESTMENTLOSSES",""); // 未确认的投资损失""}               
        setCol2ValueJLR("NETPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // 净利润} 9747-9749-9751+9753                        
        //setCol2Value("M9757",""); // 年初未分配利润""}                 
        //setCol2Value("COMPENSATIONOFSURPLUSRESERVE",""); // 盈余公积补亏""}                   
        //setCol2Value("OTHERADJUSTMENTFACTORS",""); // 其他调整因素""}                   
        setCol2ValueJLR("PROFITAVAILABLEFORDISTRIBUTION","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // 可供分配的利润}     9755+9757+9759+9761            
        //setCol2Value("PROFITRESERVEDFORASINGLEITEM",""); // 单项留用的利润""}                 
        //setCol2Value("SUPPLEMENTARYCURRENTCAPITAL",""); // 补充流动资本""}                   
        //setCol2Value("M9769",""); // 提取法定盈余公积""}               
        //setCol2Value("M9771",""); // 提取法定公益金""}                 
        //setCol2Value("M9773",""); // 提取职工奖励及福利基金""}         
        //setCol2Value("APPROPRIATIONOFRESERVEFUND",""); // 提取储备基金""}                   
        //setCol2Value("M9777",""); // 提取企业发展基金""}               
        //setCol2Value("M9779",""); // 利润归还投资""}                   
        //setCol2Value("OTHERS4",""); // 其他（可供分配的利润科目下）""}    
        setCol2ValueJLR("M9781","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // 可供投资者分配的利润} 9763-9765-9767-9769-9771-9773-9775-9777-9779-9911          
        //setCol2Value("PREFERREDSTOCKDIVIDENDSPAYABLE",""); // 应付优先股股利""}                 
       // setCol2Value("M9785",""); // 提取任意盈余公积""}               
        //setCol2Value("PAYABLEDIVIDENDSOFCOMMONSTOCK",""); // 应付普通股股利""}                 
        //setCol2Value("M9789",""); // 转作资本的普通股股利""}           
        //setCol2Value("OTHERS5",""); // 其他（可供投资者分配的利润科目下""}   
        setCol2ValueJLR("UNAPPROPRIATEDPROFIT","501","502","504","503","506","507","508","510","532","511","512","514","513"  ,"516","523"); // 未分配利润}     9781-9783-9785-9787-9789-9913          
        //setCol2Value("LOSSCOMPENSATEDBEFORETHETAX",""); // 未分配利润应由以后年度税前利润弥补的亏损""} 
        
        
        
        //企业2002版现金流量信息
        setCol2Value("M9795","a01"); //销售商品和提供劳务收到的现金a01}   
        setCol2Value2("TAXREFUNDS","a17","a18"); //收到的税费返还a17+a18}         
        setCol2Value2("M9799","a16","a19"); //收到的其他与经营活动有关的现金a16+a19} 
        
        setCol2Value5Jia("M9823","a01","a17","a18","a16","a19"); //经营活动现金流入小计 9795+9797+9799} 9823
        
        setCol2Value("CASHPAIDFORGOODSANDSERVICES","a02"); //购买商品、接受劳务支付的现金a02}   
        setCol2Value("M9805","a22"); //支付给职工以及为职工支付的现金a22}  
        setCol2Value3("PAYMENTSOFALLTYPESOFTAXES","a23","a24","a25"); //支付的各项税费a23+a24+a25}         
        setCol2Value2("M9809","a03","a26"); //支付的其他与经营活动有关的现金a03+a26} 
        setCol2Value7("M9831","a02","a22","a23","a24","a25","a03","a26"); //经营活动现金流出小计} 9803+9805+9807+9809  
        
        setCol2ValueLDZJJE("M9813","a01","a17","a18","a16","a19"  ,"a02","a22","a23","a24","a25","a03","a26"); //经营活动产生的现金流量净额 }     9823-9831
        
        setCol2Value("M9815","a28"); //收回投资所收到的现金a28}   
        setCol2Value("CASHRECEIVEDFROMONVESTMENTS","a30"); //取得投资收益所收到的现金a30}      
        setCol2Value("M9819","a31"); //处置固定资产无形资产和其他长期资产所收回的现金净额a31}
        setCol2Value2("M9821","a29","a32"); //收到的其他与投资活动有关的现金a29+a32}                	 
        setCol2Value5Jia("M9917","a28","a30","a31","a29","a32"); //投资活动现金流入小计}    9815+9817+9819+9821
               
        setCol2Value("M9825","a34"); //购建固定资产无形资产和其他长期资产所支付的现金a34}    
        setCol2Value2("CASHPAYMENTSFORINVESTMENTS","a35","a36"); //投资所支付的现金}     a35+a36  
        setCol2Value("M9829","a37"); //支付的其他与投资活动有关的现金a37}  
        setCol2Value4Jia("M9919","a34","a35","a36","a37"); //投资活动现金流出小计}        9825+9827+9829
        
        setCol2Value9("M9833","a28","a30","a31","a29","a32","a34","a35","a36","a37"); //投资活动产生的现金流量净额}    9917-9919
        
        setCol2Value("CASHRECEIVEDFROMINVESTORS","a39"); //吸收投资所收到的现金a39}          
        setCol2Value("CASHFROMBORROWINGS","a41"); //借款所收到的现金a41}       
        setCol2Value2("M9839","a40","a42"); //收到的其他与筹资活动有关的现金a40+a42}  
        setCol2Value4Jia("M9921","a39","a41","a40","a42"); //筹资活动现金流入小计} 9835+9837+9839
        
        setCol2Value("CASHREPAYMENTSFORDEBTS","a44"); //偿还债务所支付的现金a44}        
        setCol2Value("M9845","a46"); //分配股利、利润或偿付利息所支付的现金a46}           
        setCol2Value4Jia("M9847","a45","a47","a48","a49"); //支付的其他与筹资活动有关的现金a45+a47+a48+a49}  
        setCol2Value6Jia("M9923","a44","a46","a45","a47","a48","a49"); //筹资活动现金流出小计}   9843+9845+9847
        //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"
        setCol2Value10("M9851","a39","a41","a40","a42" , "a44","a46","a45","a47","a48","a49"); //筹集活动产生的现金流量净额}  9921-9923
        
        setCol2Value("M9853","a51"); //汇率变动对现金的影响a51}   
        ////现金及现金等价物净增加额,"a01","+a17","a18","+a16","a19"  - ,"a02","a22","a23","a24","a25","a03","a26"        +'"a28","+a30","+a31","+a29","+a32"   - ,"+a34","+a35","+a36","+a37"'    + '"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"'     +"a51"
        setCol2ValueXJDJWJZJE("M9855","a01","a17","a18","a16","a19"    ,"a02","a22","a23","a24","a25","a03","a26"     ,"a28","a30","a31","a29","a32"     ,"a34","a35","a36","a37"  ,"a39","a41","a40","a42" ,  "a44","a46","a45","a47","a48","a49","a51"); //现金及现金等价物净增加额1}   9813+9833+9851+9853

        setCol2Value("NETPROFIT","z85"); //净利润z85}              
        //setCol2Value("PROVISIONFORASSETS",""); //计提的资产减值准备""}    
        setCol2Value("DEPRECIATIONOFFIXEDASSETS","z87"); //固定资产拆旧z87}           
        setCol2Value("M9863","z88"); //无形资产摊销z88}          
        setCol2Value("M9865","z78"); //长期待摊费用摊销z78}      
        //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //待摊费用减少""}           
        //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //预提费用增加""}           
        setCol2Value("M9871","z89"); //处置固定资产无形资产和其他长期资产的损失z89}         	
        setCol2Value("M9873","z90"); //固定资产报废损失z90}      
        setCol2Value("FINANCEEXPENSE","z91"); //财务费用z91}             
        setCol2Value("LOSSESARSINGFROMINVESTMENT","z92"); //投资损失}z92              
        setCol2Value("DEFERREDTAXCREDIT","z93"); //递延税款贷项z93}           
        setCol2Value("DECREASEININVENTORIES","z94"); //存货的减少z94}            
        setCol2Value("M9883","z95"); //经营性应收项目的减少z95}         
        setCol2Value("M9885","z96"); //经营性应付项目的增加z96}          
        setCol2Value("OTHERS1","z98"); //其他（净利润调节为经营活动现金流量科目下）z98}       	
        setCol2Value("M1813","z99"); //经营活动产生的现金流量净额1z99}    
        //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //债务转为资本""}          
        //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //一年内到期的可转换公司债券""}               		 
        //setCol2Value("M9895",""); //融资租入固定资产""}      
        //setCol2Value("OTHERS2",""); //其他（不涉及现金收支的投资和筹资活动科目下""}       	
        setCol2Value("CASHATTHEENDOFPERIOD","z101"); //现金的期末余额z101}        
        setCol2Value("M9901","z102"); //现金的期初余额z102}         
        setCol2Value("M9903","z103"); //现金等价物的期末余额z103}   
        setCol2Value("M9905","z104"); //现金等价物的期初余额z104}   
        //"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49" + "a51"'    +'"a01","+a17","+a18","+a16","a19" - ,"a02","a22","a23","a24","a25","a03","a26"' ,+'"a51"')
        setCol2ValueXJDJWJZJE2("M1855","a39","a41","a40","a42" ,  "a44","a46","a45","a47","a48","a49" , "a51" ,"a01","a17","a18","a16","a19"   ,"a02","a22","a23","a24","a25","a03","a26","a51" ); //现金及现金等价物净增加额2} 9851+9853+9813+9833
        
        
        //2007版资产负债
        setCol2Value("CURRENCYFUNDS","101"); //货币资金101
        setCol2Value("M9101","631"); //交易性金融资产   631    
        setCol2Value("NOTESRECEIVABLE","103"); //应收票据  103          	
        setCol2Value("ACCOUNTSRECEIVABLE","106"); //应收账款  106          	
        setCol2Value("PREPAYMENTS","107"); //预付账款   107         	
        setCol2Value("INTERESTRECEIVABLE","19c"); //应收利息   19c         	
        setCol2Value("DIVIDENDSRECEIVABLE","134"); //应收股利 134           	
        setCol2Value("OTHERRECEIVABLES","108"); //其他应收款  108         
        setCol2Value("INVENTORIES","110"); //存货       110        	
        setCol2Value("M9109","632"); //一年内到期的非流动资产 632
        setCol2Value2("OTHERCURRENTASSETS","115","109"); //其他流动资产    115+109     
        setCol2Value12Jia("TOTALCURRENTASSETS","101","631","103","106","107","19c","134","108","110","632","115","109"); //流动资产合计 //9100+9101+9102+9103+9104+9105+9106+9107+9108+9109+9110
        
        setCol2Value("M9112","633"); //可供出售的金融资产   633
        setCol2Value("M9113","634"); //持有至到期投资       634
        setCol2Value("LONGTERMEQUITYINVESTMENT","648"); //长期股权投资   648      
        setCol2Value("LONGTERMRECEIVABLES","635"); //长期应收款       635    
        setCol2Value("INVESTMENTPROPERTIES","636"); //投资性房地产   636      
        setCol2Value("FIXEDASSETS","119"); //固定资产    119        	
        setCol2Value("CONSTRUCTIONINPROGRESS","120"); //在建工程 120           	
        setCol2Value("CONSTRUCTIONMATERIALS","127"); //工程物资  127          	
        setCol2Value("M9120","121"); //固定资产清理  121       
        setCol2Value("M9121","637"); //生产性生物资产  637     
        setCol2Value("OILANDGASASSETS","638"); //油气资产 638          	
        setCol2Value("INTANGIBLEASSETS","129"); //无形资产  129          	
        setCol2Value("DEVELOPMENTDISBURSEMENTS","130"); //开发支出130           	
        setCol2Value("GOODWILL","131"); //商誉       131       	 
        setCol2Value("LONGTERMDEFERREDEXPENSES","132"); //长期待摊费用       132  
        setCol2Value("DEFERREDTAXASSETS","133"); //递延所得税资产 133     	
        setCol2Value2("OTHERNONCURRENTASSETS","639","125"); //其他非流动资产   639+125    
        setCol2Value18Jia("TOTALNONCURRENTASSETS","633","634","648","635","636","119","120","127","121","637","638","129","130","131","132","133","639","125"); //非流动资产合计     9112+9113+9114+9115+9116+9117+9118+9119+9120+9121+9122+9123+9124+9125+9126+9127+9128  
       
        setCol2Value30Jia("TOTALASSETS","101","631","103","106","107","19c","134","108","110","632","115","109","633","634","648","635","636","119","120","127","121","637","638","129","130","131","132","133","639","125"); //资产总计  9111+9129          	
        
        setCol2Value("SHORTTERMBORROWINGS","201"); //短期借款          201  	
        setCol2Value("M9132","640"); //交易性金融负债  640     
        setCol2Value("NOTESPAYABLE","202"); //应付票据  202          	
        setCol2Value("ACCOUNTSPAYABLE","203"); //应付账款  203           
        setCol2Value("RECEIPTSINADVANCE","204"); //预收账款 204           	
        setCol2Value("INTERESTPAYABLE","274"); //应付利息274            	
        setCol2Value("EMPLOYEEBENEFITSPAYABLE","206"); //应付职工薪酬  206       
        setCol2Value("TAXSPAYABLE","651"); //应交税费     651       	
        setCol2Value("DIVIDENDSPAYABLE","641"); //应付股利  641          	
        setCol2Value2("OTHERPAYABLES","209","210"); //其他应付款 209+210          
        setCol2Value("M9141","643"); //一年内到期的非流动负债 643
        setCol2Value("OTHERCURRENTLIABILITIES","212"); //其他流动负债   212    
        setCol2Value13Jia("TOTALCURRENTLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212"); //流动负债合计 9131+9132+9133+9134+9135+9136+9137+9138+9139+9140+9141+9142        
        
        setCol2Value("LONGTERMBORROWINGS","213"); //长期借款213            	
        setCol2Value("BONDSPAYABLES","214"); //应付债券    214        	
        setCol2Value("LONGTERMPAYABLES","215"); //长期应付款    215       
        setCol2Value("GRANTSPAYABLE","644"); //专项应付款     644      
        setCol2Value("PROVISIONS","647"); //预计负债            	
        setCol2Value("DEFERREDTAXLIABILITIES","652"); //递延所得税负652债       
        setCol2Value("M9150","645"); //其他非流动负债       
        setCol2Value7Jia("M9151","213","214","215","644","647","652","645"); //非流动负债合计   9144+9145+9146+9147+9148+9149+9150
        
        setCol2Value20Jia("TOTALLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645"); //负债合计            	9143+9151
        
        setCol2Value("M9153","301"); //实收资本（或股本）   
        setCol2Value("CAPITALRRSERVE","302"); //资本公积   302          
        setCol2Value("LESSTREASURYSTOCKS","646"); //减：库存股           
        setCol2Value("SURPLUSRESERVE","303"); //盈余公积            	
        setCol2Value2("UNAPPROPRIATEDPROFIT","305","307"); //未分配利润           
        setCol2Value6H("TOTALEQUITY","301","302","646","303","305","307"); //所有者权益合计       9153+9154-9155+9156+9157
        setCol2Value26H("TOTALEQUITYANDLIABILITIES","201","640","202","203","204","274","206","651","641","209","210","643","212","213","214","215","644","647","652","645"  ,"301","302","646","303","305","307"   ); //负债和所有者权益合计 9152+9158
        
        //企业2007版利润及利润信
        setCol2Value("REVENUEOFSALES","501"); //营业收入                     	
        setCol2Value("COSTOFSALES","502"); //营业成本                    	
        setCol2Value("BUSINESSANDOTHERTAXES","504"); //营业税金及附加                
        setCol2Value("SELLINGEXPENSES","503"); //销售费用                      
        setCol2Value("M9174","507"); //管理费用                     	
        setCol2Value("FINANCIALEXPENSE","508"); //财务费用508                     	
        setCol2Value("IMPAIRMENTLOSSOFASSETS","653"); //资产减值损失                  
        setCol2Value("M9177","654"); //公允价值变动净收益            
        setCol2Value("INVESTMENTINCOME","510"); //投资净收益                    
        setCol2Value("M9179",""); //对联营企业和合营企业的投资收益
        setCol2Value9H("OPERATINGPROFITS","501","502","504","503","507","508","653","654","510"); //营业利润      9170-9171-9172-9173-9174-9175-9176+9177+9178
        
        setCol2Value("NONOPERATINGINCOME","512"); //营业外收入                    
        setCol2Value("NONOPERATINGEXPENSES","513"); //营业外支出                    
        setCol2Value("NONCURRENTASSETS","524"); //非流动资产损失                
        setCol2Value11H("PROFITBEFORETAX","501","502","504","503","507","508","653","654","510","512","513"); //利润总额    9180+91819182
        
        setCol2Value("INCOMETAXEXPENSE","516"); //所得税费用516                    
        setCol2Value12H("NETPROFIT","501","502","504","503","507","508","653","654","510","512","513" ,"516"); //净利润               9184-9185         	
        setCol2Value("BASICEARNINGSPERSHARE","657"); //基本每股收益                  
        setCol2Value("DILUTEDEARNINGSPERSHARE","553"); //稀释每股收益   
        
        //企业2007版现金流量信
        setCol2Value("M9199","601"); //销售商品和提供劳务收到的现金                  
        setCol2Value("TAXREFUNDS","602"); //收到的税费返还                             	 
        setCol2Value("M9201","603"); //收到其他与经营活动有关的现金  
        setCol2Value3("M9202","601","602","603"); //经营活动现金流入小计     9199+9200+9201                    	

        setCol2Value("CASHPAIDFORGOODSANDSERVICES","605"); //购买商品、接受劳务支付的现金                  
        setCol2Value("M9204","606"); //支付给职工以及为职工支付的现金                
        setCol2Value("PAYMENTSOFALLTYPESOFTAXES","607"); //支付的各项税费                             	 
        setCol2Value("M9206","608"); //支付其他与经营活动有关的现金                  
        setCol2Value4Jia("M9207","605","606","607","608"); //经营活动现金流出小计
        //"601","602","603"-( "605","606","607","608")
        setCol2Value7H("M9208","601","602","603", "605","606","607","608"); //经营活动产生的现金流量净额1      9202-9207             
        
        setCol2Value("M9209","611"); //收回投资所收到的现金                          
        setCol2Value("CASHRECEIVEDFROMONVESTMENTS","612"); //取得投资收益所收到的现金                      
        setCol2Value("M9211","613"); //处置固定资产无形资产和其他长期资产所收回的现金
        setCol2Value("M9212","614"); //处置子公司及其他营业单位收到的现金净额        
        setCol2Value("M9213","615"); //收到其他与投资活动有关的现金                  
        setCol2Value5Jia("M9214","611","612","613","614","615"); //投资活动现金流入小计 9209+9210+9211+9212+9213      
        
        setCol2Value("M9215","617"); //购建固定资产无形资产和其他长期资产所支付的现金
        setCol2Value("CASHPAYMENTSFORINVESTMENTS","618"); //投资所支付的现金                           	 
        setCol2Value("M9217","618"); //取得子公司及其他营业单位支付的现金净额        
        setCol2Value("M9218","620"); //支付其他与投资活动有关的现金                  
        setCol2Value4Jia("SUBTOTALOFCASHOUTFLOWS","617","618","619","620"); //投资活动现金流出小计 9215+9216+9217+9218
        
        //("M9220","611","612","613","614","615" - ("617","618","619","620"))
        setCol2Value9H1("M9220","611","612","613","614","615" ,"617","618","619","620"); //投资活动产生的现金流量净额  9214-9219
        
        setCol2Value("CASHRECEIVEDFROMINVESTORS","622"); //吸收投资收到的现金                          	 
        setCol2Value("CASHFROMBORROWINGS","623"); //取得借款收到的现金                          	 
        setCol2Value("M9223","624"); //收到其他与筹资活动有关的现金                  
        setCol2Value3("M9224","622","623","624"); //筹资活动现金流入小计        9221+9222+9223   
        
        setCol2Value("CASHREPAYMENTSFORDEBTS","626"); //偿还债务所支付的现金                        	 
        setCol2Value("M9226","627"); //分配股利、利润或偿付利息所支付的现金          
        setCol2Value("M9227","628"); //支付其他与筹资活动有关的现金                  
        setCol2Value3("M9228","626","627","628"); //筹资活动现金流出小计     9225+9226+9227
        
        //"622","623","624"-("626","627","628")
        setCol2Value6H1("M9229","622","623","624","626","627","628" ); //筹集活动产生的现金流量净额       9224-9228
        
        setCol2Value("M9230","630"); //汇率变动对现金及现金等价物的影响              
        //["601","602","603"-( "605","606","607","608")] +["611","612","613","614","615" - ("617","618","619","620")]  + ["622","623","624"-("626","627","628"] +"630"
        setCol2Value23H("M9231", "601","602","603", "605","606","607","608","611","612","613","614","615","617","618","619","620","622","623","624","626","627","628" ,"630"); //现金及现金等价物净增加额     9208+9220+9229+9230
        
        //setCol2Value("M9232",""); //期初现金及现金等价物余额  ""     
        
        setCol2Value23H("M9233", "601","602","603", "605","606","607","608","611","612","613","614","615","617","618","619","620","622","623","624","626","627","628" ,"630" ); //期末现金及现金等价物余额   9231+9232                   
        //setCol2Value("NETPROFIT",""); //净利润                                  	
        //setCol2Value("PROVISIONFORASSETIMPAIRMENT",""); //资产减值准备                              	
        //setCol2Value("DEPRECIATIONOFFIXEDASSETS",""); //固定资产折旧、油气资产折耗、生产性生物资产折旧
        //setCol2Value("M9237",""); //无形资产摊销                             	
        //setCol2Value("M9238",""); //长期待摊费用摊销                           	 
        //setCol2Value("DECREASEOFDEFFEREDEXPENSES",""); //待摊费用减少                             	
        //setCol2Value("ADDITIONOFACCUEDEXPENSE",""); //预提费用增加                             	
        //setCol2Value("M9241",""); //处置固定资产无形资产和其他长期资产的损失      
        //setCol2Value("M9242",""); //固定资产报废损失                            
        //setCol2Value("M9243",""); //公允价值变动损失                            
        //setCol2Value("FINANCEEXPENSE",""); //财务费用                                	
        //setCol2Value("LOSSESARSINGFROMINVESTMENT",""); //投资损失                                	
        //setCol2Value("DEFERREDINCOMETAXASSETS",""); //递延所得税资产减少                          
        //setCol2Value("M9247",""); //递延所得税负债增加                          	 
        //setCol2Value("DECREASEININVENTORIES",""); //存货的减少                               	
        //setCol2Value("M9250",""); //经营性应收项目的减少                         	
        //setCol2Value("M9251",""); //经营性应付项目的增加                        	 
        //setCol2Value("OTHERS",""); //（净利润调节为经营活动现金流量科目下）其他    
        //setCol2Value("M9252",""); //经营活动产生的现金流量净额2                   
        //setCol2Value("DEBTSTRANSFERTOCAPITAL",""); //债务转为资本                              	
        //setCol2Value("ONEYEARDUECONVERTIBLEBONDS",""); //一年内到期的可转换公司债券                    
        //setCol2Value("M9255",""); //融资租入固定资产                             	
        //setCol2Value("NONCASHOTHERS",""); //其他（不涉及现金收支的投资和筹资活动科目下）  
        //setCol2Value("CASHATTHEENDOFPERIOD",""); //现金的期末余额                              
        //setCol2Value("M9258",""); //现金的期初余额                               
        //setCol2Value("M9259",""); //现金等价物的期末余额                         	
        //setCol2Value("M9260",""); //现金等价物的期初余额                         	
        //setCol2Value("M9261",""); //现金及现金等价物净增加额
        
        
        //事业单位资产负债
        setCol2Value("CURRENCYFUNDS",""); //货币资金     ？？
        setCol2Value("SHORTTERMINVESTMENTS",""); //短期投资    ??    
        setCol2Value("M9408",""); //财政应返还额度  ？？
        setCol2Value("NOTESRECEIVABLE","103"); //应收票据 103       
        setCol2Value("ACCOUNTSRECEIVABLE","19f"); //应收账款19f        
        setCol2Value("PREPAYMENTS","19n"); //预付账款     19n   
        setCol2Value("OTHERRECEIVABLES","19n"); //其他应收款   19n   
        setCol2Value("INVENTORIES",""); //存货          	
        setCol2Value("OTHERCURRENTASSETS",""); //其他流动资产    ???
        setCol2Value("TOTALCURRENTASSETS",""); //流动资产合计   ??? 
        setCol2Value("LONGTERMINVESTMENT",""); //长期投资        
        setCol2Value("FIXEDASSETS","119"); //固定资产        
        setCol2Value("M9407",""); //固定资产原价    ??
        setCol2Value("M9401",""); //累计折旧      ??  
        setCol2Value("CONSTRUCTIONINPROCESS",""); //在建工程      ??  
        setCol2Value3("INTANGIBLEASSETS","123","120","19g"); //无形资产      123+120+19g  
        setCol2Value("M9402",""); //无形资产原价   ?? 
        setCol2Value("ACCUMULATEDAMORTIZATION",""); //累计摊销       	
        setCol2Value("M9403",""); //待处置资产损溢  ??
        setCol2Value("TOTALNONCURRENTASSETS",""); //非流动资产合计  ??
        setCol2Value("TOTALASSETS",""); //资产总计       	
        setCol2Value("SHORTTERMBORROWINGS",""); //短期借款       ??	
        setCol2Value("TAXPAYABLE",""); //应缴税费       ??	
        setCol2Value("TREASURYPAYABLE",""); //应缴国库款      ??
        setCol2Value("M9404",""); //应缴财政专户款  ??
        setCol2Value("EMPLOYEEBENEFITSPAYABLE",""); //应付职工薪酬    ??
        
        setCol2Value("NOTESPAYABLE","202"); //应付票据       	
        setCol2Value("ACCOUNTSPAYABLE","203"); //应付账款       	
        setCol2Value("RECEIPTSINADVANCE","204"); //预收账款       	
        setCol2Value("OTHERPAYABLES","209"); //其他应付款      
        setCol2Value("OTHERCURRENTLIABILITIES",""); //其他流动负债    ??
        setCol2Value("TOTALCURRENTLIABILITIES",""); //流动负债合计    ??
        setCol2Value("LONGTERMBORROWINGS",""); //长期借款       	??
        setCol2Value("LONGTERMPAYABLES",""); //长期应付款      ??
        setCol2Value("M9405",""); //非流动负债合计  ??
        setCol2Value4Jia("TOTALLIABILITIES","202","203","204","209"); //负债合计     9295+9296+9297+9298+9299+9300+9301+9302
        
        setCol2Value("ENTERPRISEFUND","319"); //事业基金       	
        setCol2Value("NONCURRENTASSETSFUND",""); //非流动资产基金  ??
        setCol2Value("SPECIALPURPOSEFUNDS",""); //专用基金       	??
        setCol2Value("FINANCIALAIDCARRIEDOVER",""); //财政补助结转    
        setCol2Value("FINANCIALAIDBALANCE",""); //财政补助结余    
        setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //非财政补助结转  
        setCol2Value("NONFINANCIALAIDBALANCE",""); //非财政补助结余  
        setCol2Value("UNDERTAKINGSBALANCE","322"); //事业结余        
        setCol2Value2("OPERATINGBALANCE","19x","277"); //经营结余       19x+277	
        setCol2Value3("TOTALNETASSETS","322","19x","277"); //净资产合计    9304+9306+9307+9308+9309+9310  
        setCol2Value("M9406",""); //负债和净资产总计
        
        //事业单位收入支出
        setCol2Value("M9501",""); //本期财政补助结转结余    没有   ？？？
        setCol2Value("FINANCIALSUBSIDYREVENUE","559"); //财政补助收入       559      
        setCol2Value("M9345","5b2"); //事业支出（财政补助支出）              财政补助支出
        setCol2Value("M9502",""); //本期事业结转结余      9336-9350 事业结余    
        setCol2Value("UNDERTAKINGSCLASSREVENUE",""); //事业类收入   ？？        
        setCol2Value("UNDERTAKINGSREVENUE","562"); //事业收入                
        setCol2Value("SUPERIORSUBSIDYREVENUE","560"); //上级补助收入    560         
        setCol2Value("M9503","565"); //附属单位上缴收入          	
        setCol2Value("OTHERREVENUE","571"); //其他收入               
        setCol2Value("DONATIONINCOME",""); //（其他收入科目下）捐赠收入 ？？？
        setCol2Value("UNDERTAKINGSCLASSEXPENDITURE",""); //事业类支出              	 
        setCol2Value("M9505",""); //事业支出（非财政补助支出）      ？？？？？
        setCol2Value("PAYMENTTOTHEHIGHERAUTHORITY","556"); //上缴上级支出             	 
        setCol2Value("M9508","557"); //对附属单位补助支出        	
        setCol2Value("OTHEREXPENDITURE",""); //其他支出        ？？？？       	 
        setCol2Value("CURRENTOPERATINGBALANCE",""); //本期经营结余            ？？？	 
        setCol2Value("OPERATINGREVENUE","564"); //经营收入               
        setCol2Value("OPERATINGEXPENDITURE","552"); //经营支出               	 
        setCol2Value("M9506","z81"); //弥补以前年度亏损后的经营结余} 以前年度经营亏损
        setCol2Value("M9507",""); //本年非财政补助结转结余     ？？
        setCol2Value("NONFINANCIALAIDCARRIEDOVER",""); //非财政补助结转            ？？	
        setCol2Value("NONFINANCIALAIDBALANCETHISYEAR",""); //本年非财政补助结余        	
        setCol2Value("ENTERPRISEINCOMETAXPAYABLE","z82"); //应缴企业所得税         
        setCol2Value("SPECIALFUNDSTOEXTRACT","z85"); //提取专用基金             	 
        setCol2Value("PUBLICFUNDTURNEDINTO","578"); //转入事业基金
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

	////现金及现金等价物净增加额,"a01","+a17","+a18","+a16","a19"  - ,"a02","a22","a23","a24","a25","a03","a26"        +'"a28","+a30","+a31","+a29","+a32"   - ,"+a34","+a35","+a36","+a37"'    + '"a39","+a41","+a40","+a42" -  "a44","+a46","+a45","+a47","+a48","+a49"'     +"a51"
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
	           
	           
	           
	          
	           logger.debug("打开连接成功");
        } catch (SQLException e) {
            logger.debug("打开连接出错", e);
            throw new RecordSetException(e);
        }
    }
    
    /**
     * 关闭数据库资源
     */
    public void close() throws RecordSetException {
        if (ps != null) {
            try {
                ps.close();
                ps = null;
                logger.debug("关闭preparedstatement成功");
            } catch (SQLException e) {
                logger.debug("关闭preparedstatement出错", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.debug("关闭connection成功");
            } catch (SQLException e) {
                logger.debug("关闭connection出错", e);
            }
        }
        super.close();
    }

}


