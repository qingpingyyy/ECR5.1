-----------------------
--07资产负债表ECR_INT_FINANCEBS07
--货币资金              	CurrencyFunds            
select rd.col1value(年初数),rd.col2value(期末数)
from REPORT_DATA RD,REPORT_MODEL RM ,REPORT_RECORD RR,ENT_INFO EI
where RD.RowNo = RM.RowNo
and RD.ReportNo = RR.ReportNo  
and RM.ModelNo = '0291'  --表示损益表
and RM.ModelNo = RR.ModelNo
and RR.ReportDate = '报表日期'
and RR.ObjectNo = '信贷客户号'
and rd.rowno = 'K列值'
and EI.CUSTOMERID = RR.OBJECTNO
and EI.FinanceBelong = '007'--表示07版财报
ORDER BY RD.DISPLAYORDER 


---------------------------
--07现金流量表ECR_INT_FINANCECF07
--销售商品和提供劳务收到的现金                      	M9199                      
select rd.col1value(年初数),rd.col2value(期末数)
from REPORT_DATA RD,REPORT_MODEL RM ,REPORT_RECORD RR,ENT_INFO EI
where RD.RowNo = RM.RowNo 
and RD.ReportNo = RR.ReportNo  
and RM.ModelNo = '0297'  --表示损益表
and RM.ModelNo = RR.ModelNo
and RR.ReportDate = '报表日期'
and RR.ObjectNo = '信贷客户号'
and rd.rowno = 'K列值'
and EI.CUSTOMERID = RR.OBJECTNO
and EI.FinanceBelong = '007'--表示07版财报
ORDER BY RD.DISPLAYORDER 

----------------------------------
--07利润及利润分配表ECR_INT_FINANCEPS07
--营业收入                      	RevenueOfSales         
select rd.col1value(年初数),rd.col2value(期末数)
from REPORT_DATA RD,REPORT_MODEL RM ,REPORT_RECORD RR,ENT_INFO EI
where RD.RowNo = RM.RowNo 
and RD.ReportNo = RR.ReportNo  
and RM.ModelNo = '0292'  --表示损益表
and RM.ModelNo = RR.ModelNo
and RR.ReportDate = '报表日期'
and RR.ObjectNo = '信贷客户号'
and rd.rowno = 'K列值'
and EI.CUSTOMERID = RR.OBJECTNO
and EI.FinanceBelong = '007'--表示07版财报
ORDER BY RD.DISPLAYORDER 

----------------------------------
--FinanceBs07ReportProvider.java

-- 构造 SQL 查询 report_record 和 report_data，根据客户号和年份（objectno 和 reportdate），
-- 查找资产负债表（modelno='0291'）的 reportno
select 
    distinct rr.ReportNo as ReportNo 
    from report_record rr ,report_data rd 
    where rr.reportno =rd.reportno  
    and rr.modelno ='0291' --资产负债表
    and rr.reportscope <>'03'  
    and rr.objectno =? 
    and rr.REPORTDATE  ='报表日期'

----
select rowSubject,col2Value from REPORT_DATA where reportNo = '"+sReportNo+"'
