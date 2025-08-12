ECR_INT_FINANCEBS加载企业财务报表基础段信息
select CUSTOMERID,SHEETYEAR,max(EXTEND1) as EXTEND1 ,SHEETTYPE, SHEETTYPEDIVIDE ,ENTNAME,ENTCERTNUM,ENTCERTTYPE,AUDITFIRMNAME,MANAGERORGID,MANAGERUSERID,UPDATEDATE,OCCURDATE,INFRECTYPE
            from (   
            select distinct CF.CustomerID as CUSTOMERID, {#客户号} 			              
            substr(CF.ReportDate,1,4) as SHEETYEAR, {#报表年份}
            CF.ReportDate as EXTEND1,		          
            case when CF.ReportPeriod='040' then '10' when  CF.ReportPeriod='030'  and  substr(CF.ReportDate,6,2) in ('01','02','03','04','05','06') then '20' 
                    when CF.ReportPeriod='030' and substr(CF.ReportDate,6,2) in ('07','08','09','10','11','12') then '30' 
                    when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('01','02','03') then '40' 
                    when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('04','05','06') then '50' 
                    when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('07','08','09') then '60' 
                    when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('10','11','12') then '70' 
                end as SHEETTYPE, {#报表类型}
            case when cf.ReportScope='020' then '1' when cf.ReportScope ='010' then '2' end	 as SHEETTYPEDIVIDE, {#报表类型细分} 		      
            CI.CustomerName	as ENTNAME, {#企业名称}                
            case when length(trim(EI.LoancardNo))=16 then EI.LoancardNo 
                    when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then EI.Corpid
                    when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then EI.Corpid end as ENTCERTNUM, {#企业身份标识类型} 
            case when length(trim(EI.LoancardNo))=16 then '10' 
                    when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then '20'
                    when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then '30' end as ENTCERTTYPE, {#企业身份标识号码}          
            CF.AuditOffice as AUDITFIRMNAME, {#审计事务所名称}
            '' as AUDITORNAME, {#审计人名称}			        
            '' as AUDITTIME, {#审计时间}			        
            EI.INPUTORGID as MANAGERORGID,{#管户机构编号}
            EI.INPUTUSERID as MANAGERUSERID,{#管户用户编号}
            cf.OrgID as OPERORGID,{#操作机构编号}
            cf.UserID as OPERUSERID,{#操作用户编号}
            replace(cf.UpdateDate,'/','-') as UPDATEDATE,{#更新日期}   
            replace(cf.InputDate,'/','-') as OCCURDATE,{#数据日期}
            'U' as INFRECTYPE
            from Customer_FSRecord cf , Customer_info_temp ci ,ent_info ei
            where cf.customerid =ci.customerid 
            and ei.customerid =ci.customerid 
            and ei.FinanceBelong in ('001' ,'007','002')
            and length(EI.LoanCardNo) >= 16
            and cf.ReportScope in ('020','010')
            and cf.ReportPeriod in ('020','030','040')
    )
group by CUSTOMERID,SHEETYEAR ,SHEETTYPE, SHEETTYPEDIVIDE ,ENTNAME,ENTCERTNUM,ENTCERTTYPE,AUDITFIRMNAME,MANAGERORGID,MANAGERUSERID,UPDATEDATE,OCCURDATE,INFRECTYPE

-------------------------------
--ECR_INT_FINANCEPS07加载企业2007版利润及利润信息
select CUSTOMERID,SHEETYEAR,max(EXTEND1) as EXTEND1 ,SHEETTYPE, SHEETTYPEDIVIDE ,ENTNAME,ENTCERTNUM,ENTCERTTYPE,AUDITFIRMNAME,MANAGERORGID,MANAGERUSERID,UPDATEDATE,OCCURDATE,INFRECTYPE
            from ( 
                select distinct CF.CustomerID	as CUSTOMERID, {#客户号} 			              
                substr(CF.ReportDate,1,4) as SHEETYEAR, {#报表年份}
                CF.ReportDate as EXTEND1 ,{#信贷报表年月}	          
                case when CF.ReportPeriod='040' then '10' when  CF.ReportPeriod='030'  and  substr(CF.ReportDate,6,2) in ('01','02','03','04','05','06') then '20' 
                        when CF.ReportPeriod='030' and substr(CF.ReportDate,6,2) in ('07','08','09','10','11','12') then '30' 
                        when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('01','02','03') then '40' 
                        when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('04','05','06') then '50' 
                        when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('07','08','09') then '60' 
                        when CF.ReportPeriod='020' and substr(CF.ReportDate,6,2) in ('10','11','12') then '70' 
                    end as SHEETTYPE, {#报表类型   }
                case when cf.ReportScope='020' then '1' when cf.ReportScope ='010' then '2' end		as SHEETTYPEDIVIDE, {#报表类型细分} 		      
                CI.CustomerName	as ENTNAME, {#企业名称}                
                case when length(trim(EI.LoancardNo))=16 then EI.LoancardNo 
                    when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then EI.Corpid
                    when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then EI.Corpid end	as ENTCERTNUM, {#企业身份标识类型} 
                case when length(trim(EI.LoancardNo))=16 then '10' 
                        when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then '20'
                        when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then '30' end	 as ENTCERTTYPE, {#企业身份标识号码}          
                CF.AuditOffice	as AUDITFIRMNAME, {#审计事务所名称}		      
                '' as AUDITORNAME, {#审计人名称}			        
                '' as AUDITTIME, {#审计时间}
                EI.INPUTORGID as MANAGERORGID,{#管户机构编号}
                EI.INPUTUSERID as MANAGERUSERID,{#管户用户编号}
                cf.OrgID	as OPERORGID,{#操作机构编号}
                cf.UserID	as OPERUSERID,{#操作用户编号}
                replace(cf.UpdateDate,'/','-') as UPDATEDATE,{#更新日期}   
                replace(cf.InputDate,'/','-') as OCCURDATE,{#数据日期}
                'U' as INFRECTYPE
                from Customer_FSRecord cf , Customer_info ci ,ent_info ei
                where cf.customerid =ci.customerid 
                and ei.customerid =ci.customerid 
                and ei.FinanceBelong='007'
                and length(EI.LoanCardNo) >= 16
                and cf.ReportScope in ('020','010')
                and cf.ReportPeriod in ('020','030','040')
            )
group by CUSTOMERID,SHEETYEAR ,SHEETTYPE, SHEETTYPEDIVIDE ,ENTNAME,ENTCERTNUM,ENTCERTTYPE,AUDITFIRMNAME,MANAGERORGID,MANAGERUSERID,UPDATEDATE,OCCURDATE,INFRECTYPE

