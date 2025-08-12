--CUSTOMER_INFO_TEMP
select * from CUSTOMER_INFO ci where  not exists (
    select 1 from 
    db2inst1.business_contract bc 
    where businesstype like '3%' and ci.customerid =bc.customerid)
这段 SQL 查询的作用是从 CUSTOMER_INFO 表（客户信息表）中筛选出那些没有与 business_contract（业务合同表）中 businesstype 以 '3' 开头的合同相关联的客户。具体来说：
/*
主查询 select * from CUSTOMER_INFO ci 会选出所有客户信息。
where not exists (...) 子句用于排除那些在子查询中有匹配记录的客户。
子查询会在 business_contract 表中查找 businesstype 字段以 '3' 开头，并且 customerid 与 CUSTOMER_INFO 表中的 customerid 相同的记录。
如果某个客户在 business_contract 表中没有 businesstype 以 '3' 开头的合同，则该客户会被主查询选中。
*/

--------------------------------------------------------------------------------------------------------------------------------
--ECR_INT_ENTINFO加载企业基本信息(信贷)
select distinct EI.CustomerID	as CUSTOMERID,{#客户编号}
        EI.EnterpriseName as ENTNAME,{#企业名称}
        EI.LoancardNo as LOANCARDNO,{#中征码}
        case when length(EI.corpid)=18 then EI.corpid end as USCCID,{#统一社会信用代码}
        case when length(EI.corpid)=18 then substr(EI.corpid,9,8)||substr(EI.corpid,17,1) else replace(EI.corpid,'-','') end  as CORPID,{#组织机构代码}  		  
        substr(EI.TaxNo,1,30) as NATIONALTAXNO,{#纳税人识别号(国税)}
        substr(EI.TaxNo1,1,30) as LOCALTAXNO,{#纳税人识别号(地税)}	 
        '2'	as CUSTOMERTYPE,{#客户资料类型}
        '1' as ETPSTS,{#存续状态 以企业'营业执照'有效期取值，期限内-正常；期限外-其他}  	  
        EI.ORGNATURE as ORGTYPE,{#组织机构类型}	
        EI.CountryCode as NATIONALITY,{#国别代码}
        case when EI.RegisterAdd is null or EI.RegisterAdd ='' then '暂缺' else EI.RegisterAdd end  as REGADD,{#登记地址}
        EI.RegionCode as ADMDIVOFREG ,{#登记地行政区划代码}  	 
        replace(EI.SetupDate ,'/','-') as ESTABLISHDATE,{#成立日期}  	  
        Replace(EI.LicenseDate,'/','-')	as BIZENDDATE,{#营业许可证到期日}
        EI.MostBusiness as BIZRANGE,{#业务范围}
        EI.IndustryType as ECOINDUSCATE,{#行业分类代码}	
        EI.OrgType as ECOTYPE,{#经济类型代码}	
        case when length( EI.SCOPE) =0 then 'X' else EI.SCOPE end as ENTSCALE,{#企业规模}--不能为空	
        EI.RCCurrency as REGCAPCURRENCY,{#注册资本币种}	
        EI.RegisterCapital as REGCAP,{#注册资本}	
        '110106' as CONADDDISTRICTCODE,{#联系地址行政区划代码EI.RegionCode} 		
        EI.OfficeAdd as CONADD,{#联系地址}	
        substr(EI.OFFICETEL,1,25) as CONPHONE,{#联系电话}	
        EI.FINTELSPE as FINCONPHONE,{#财务部门联系电话} 	 
        EI.INPUTORGID as MANAGERORGID,{#管户机构编号} 	 
        EI.INPUTUSERID as MANAGERUSERID,{#管户用户编号} 	 
        Replace(EI.Updatedate,'/','-')	as UPDATEDATE,{#更新日期}   
        '{$ARE.bizDate}' as OCCURDATE{#数据日期}
    from ENT_INFO EI ,customer_info ci left join CISFMBBIF on replace(CI.FCustomerID,'$','#') = CISFMBBIF.CusNo 
where ei.customerid =ci.customerid 
and exists (select 1 from Business_Contract_temp BC where BC.CustomerID=EI.CustomerID )
and EI.LoanCardNo is not null and length(EI.LoanCardNo)>=16 
and EI.InputOrgID is not null and EI.InputOrgID not in ('')

--连接条件解释
1. 隐式内连接（Implicit Inner Join）
    连接字段：ei.customerid = ci.customerid
    作用：关联企业信息表(ENT_INFO)和客户信息表(customer_info)，只返回两个表中customerId匹配的记录
2. 左外连接（Left Outer Join）
    连接字段：replace(CI.FCustomerID,'$','#') = CISFMBBIF.CusNo
    特殊处理：使用replace函数将customer_info表中的FCustomerID字段的'$'字符替换为'#'后再与CISFMBBIF表的CusNo字段匹配
    作用：关联客户信息表和核心系统客户基本信息表，即使CISFMBBIF中没有匹配记录也会保留customer_info的数据
3. Exists子查询
    作用：判断临时业务合同表Business_Contract_temp表中是否有匹配的记录，如果有则返回该记录，否则返回NULL
4.贷款卡号有效性检查
    条件：EI.LoanCardNo is not null and length(EI.LoanCardNo)>=16
    作用：确保贷款卡号不为空且长度大于等于16位，符合规范的贷款卡号格式
5. 管户机构编号有效性检查
    条件：EI.InputOrgID is not null and EI.InputOrgID not in ('')
    作用：确保管户机构编号不为空且不为空字符串，符合规范的管户机构编号格式
---------------------------------------------------------------------------------------------------------
--加载企业基本信息(核心)ECR_INT_ENTINFO
select distinct trim(replace(CISFMBBIF.CusNo,'#','$')) as CUSTOMERID,
    trim(CISFMBBIF.CUSNAM)  as ENTNAME,{#企业名称} 	
    trim(CIFM51.C51LOADNO)	as LOANCARDNO,{#中征码} 		
    '' as USCCID,{#统一社会信用代码-核心无}  		 
    replace(CISFMSCBI.ORGNUM,'-','') as CORPID,{#组织机构代码}  		  
    trim(CISFMSCBI.CTYTAX) as NATIONALTAXNO,{#纳税人识别号(国税)}		 
    trim(CISFMSCBI.REGTAX) as LOCALTAXNO,{#纳税人识别号(地税)}		 
    '1'	as CUSTOMERTYPE,{#客户资料类型}
    'X' as ETPSTS,{#存续状态}
    '99' as ORGTYPE,{#组织机构类型---------业务确认后续核心增加字段 ，存量数据用99}	
    case when trim(CISFMBBIF.CTYCOD) = '' then 'CHN' else trim(CISFMBBIF.CTYCOD) end as NATIONALITY,{#国别代码}
    case when trim(CISFMSCBI.JKREADR) is null or trim(CISFMSCBI.JKREADR) ='' then '暂缺' else trim(CISFMSCBI.JKREADR) end  as REGADD,{#登记地址}
    '110106'	as ADMDIVOFREG,{#登记地行政区划代码case when trim(CIFM51.C51REGARE) = '' then null else trim(CIFM51.C51REGARE) end}  	 
    case when trim(CIFM51.C51REGDAT) = '' or trim(CIFM51.C51REGDAT) = '0001-01-01' then null else trim(CIFM51.C51REGDAT) end as ESTABLISHDATE,{#成立日期}  	  
    case when trim(CISFMSCBI.LNUMDAT2) = '' or trim(CISFMSCBI.LNUMDAT2) = '0001-01-01' then null else trim(CISFMSCBI.LNUMDAT2) end 	as BIZENDDATE,{#营业许可证到期日}
    trim(CIFM51.C51BISCPE) as BIZRANGE,{#业务范围} 
    trim(CIFM51.C51INDSUB) as ECOINDUSCATE,{#行业分类代码}	
    '900' as ECOTYPE,{#经济类型代码CISFMSCBI.ORGTYP}	
    case when length(trim(CIFM51.C51SCOPE)) =0 or CIFM51.C51SCOPE is null  then 'X' else trim(CIFM51.C51SCOPE) end as ENTSCALE,{#企业规模------------------------不能为空}	
    case when trim(CIFM51.C51CPCRNC) is null or trim(CIFM51.C51CPCRNC) = '' then 'CNY' else trim(CIFM51.C51CPCRNC) end 	as REGCAPCURRENCY,{#注册资本币种}	
    case when round(CISFMSCBI.ERLAMT) = 0 then null when round(CISFMSCBI.ERLAMT)>=100000000 then 99999999 else round(CISFMSCBI.ERLAMT) end as REGCAP,{#注册资本}	
    '' as CONADDDISTRICTCODE,{#联系地址行政区划代码}
    {#(select trim(CISFMSFFI.FAREADR) from CISFMSFFI where  CISFMBBIF.CusNo = CISFMSFFI.CusNo)  as CONADD,联系地址}	
    trim(CISFMSCBI.JKRTELL) as CONPHONE,{#联系电话}	
    {#(select trim(CISFMSFFI.FACTEL) from CISFMSFFI where  CISFMBBIF.CusNo = CISFMSFFI.CusNo)  as FINCONPHONE,财务部门联系电话} 	 
    CISFMBBIF.CRTDPT as MANAGERORGID,{#管户机构编号} 	 
    CISFMBBIF.CRTTLR as MANAGERUSERID,{#管户用户编号} 	 
    '{$ARE.bizDate}' as UPDATEDATE,{#更新日期}   
    '{$ARE.bizDate}' as OCCURDATE{#数据日期}
from CISFMSCBI CISFMSCBI,BISFMNOD,CISFMBBIF left outer join CIFM51 CIFM51 on CISFMBBIF.CusNo = CIFM51.C51MFCUSI
where CISFMBBIF.CusNo = CISFMSCBI.CusNo
and CISFMBBIF.ACTUALCRTDPT=BISFMNOD.ORG 
and (length(trim(CIFM51.C51LOADNO))=16 or length(CISFMSCBI.ORGNUM)=10 and CISFMSCBI.ORGNUM like '%-%')
and not exists (select 1 from customer_info CI where CI.FCustomerID = replace(CISFMBBIF.CusNo,'#','$')){#只要核心客户号不存在customer_info表中}  
and Substr(BISFMNOD.RMK1,9,1) in ('1','2','4') {#BISFMNOD 第９位：银行性质  1: 信用社  2: 农商行  3: 村镇银行 4: 合行 }

--解释
关联条件
1.表关联方式
    from CISFMSCBI CISFMSCBI, BISFMNOD, CISFMBBIF left outer join CIFM51 CIFM51 on CISFMBBIF.CusNo = CIFM51.C51MFCUSI
    这是多表连接，CISFMBBIF 与 CIFM51 通过客户号（CusNo = C51MFCUSI）做左外连接，保证即使 CIFM51 没有匹配也能保留 CISFMBBIF 的数据。
    其他表为内连接，默认通过 where 条件进行关联。
2.主关联条件
    where CISFMBBIF.CusNo = CISFMSCBI.CusNo
    核心客户基本信息表（CISFMBBIF）与企业基本信息表（CISFMSCBI）通过客户号关联。
    and CISFMBBIF.ACTUALCRTDPT = BISFMNOD.ORG
    CISFMBBIF 的实际管户机构编号与 BISFMNOD 的机构编号关联。
查询条件
1.企业标识有效性
    and (length(trim(CIFM51.C51LOADNO))=16 or length(CISFMSCBI.ORGNUM)=10 and CISFMSCBI.ORGNUM like '%-%')
    要么 CIFM51 的贷款卡号长度为16，要么 CISFMSCBI 的组织机构代码长度为10且包含“-”，保证企业标识有效。
2.客户去重
    and not exists (select 1 from customer_info CI where CI.FCustomerID = replace(CISFMBBIF.CusNo,'#','$'))
    只选取那些核心客户号（CusNo）在 customer_info 表中不存在的客户，避免重复导入。
3.银行性质筛选
    and Substr(BISFMNOD.RMK1,9,1) in ('1','2','4')
    只选取银行性质为“信用社”、“农商行”、“合行”的数据（通过 BISFMNOD.RMK1 第9位判断）
通过客户号、机构编号等字段将多个表关联，确保数据完整性。
通过有效性、去重、银行类型等条件筛选出需要的数据，保证导入的数据符合业务要求且不重复。
-------------------------------------------------------------------------------------------------------------------------------
--加载企业相关还款责任人信息ECR_INT_RLTREPYMTINFO
select distinct BC.Serialno as BCTRCTCODE, {#账户标识码}   		          
    case when length(getLoanCardNo(GC.GuarantorID)) &gt;='16'  then '1' else '2' end  as INFOIDTYPE, {#身份类别 2自然人}  			          
    trim(GC.GuarantorName) as ARLPNAME, {#责任人名称}      
    case when length(getLoanCardNo(GC.GuarantorID)) &gt;='16' then '10' else ci.CertType end  as ARLPCERTTYPE, {#责任人身份标识类型}
    case when length(getLoanCardNo(GC.GuarantorID)) &gt;= '16' then getLoanCardNo(GC.GuarantorID) else ci.CertID end  as ARLPCERTNUM, {#责任人身份标识号码}
    '2' as ARLPTYPE, {#还款责任人类型}
    GC.GuarantyValue as ARLPAMT, {#还款责任金额}
    case when gc.GuarantyType ='045' then '1' else '0' end  as WARTYSIGN, {#联保标志} 			            
    GC.SerialNo as MAXGUARMCC, {#保证合同编号} 		          
    case when (BC.FinishDate is null or BC.FinishDate = '') then (case when GC.ContractStatus = '030' then '2' else '1' end) else '2' end as EffStatus, {#有效标志} 	    	        
    bc.manageorgid as MANAGERORGID, {#管户机构编号} 		      
    bc.manageuserid as MANAGERUSERID, {#管户用户编号} 		    
    replace(bc.updatedate,'/','-') as UPDATEDATE, {#更新日期}  	 
    '{$ARE.bizDate}' as OCCURDATE {#数据日期}
    from GUARANTY_CONTRACT_temp GC,CONTRACT_RELATIVE_temp CR,BUSINESS_CONTRACT_temp BC,ENT_INFO EI,BUSINESS_TYPE BT,CUSTOMER_INFO_temp CI
    where  BC.SerialNo = CR.SerialNo
    and CR.ObjectNo = GC.SerialNo
    and BT.TypeNO = BC.BusinessType
    and EI.CustomerID = BC.CustomerID
    AND CI.CUSTOMERID=GC.GuarantorID
    and CR.ObjectType = 'GUARANTY_CONTRACT'
    and GC.GuarantyType in('010','020','030','040','045')  {#担保方式：010=保证}
    and BT.Attribute16 in ('11','13','21','12') {#11贷款业务 13保理 21票据贴现 按照D2报送 12贸易融资  }
    and GC.ContractStatus in ('020','030','040')
    and (CI.CertType in ('0','1','2','3','4','5','6','7','8','9','X') or length(getLoanCardNo(GC.GuarantorID)) >=16)
关联条件（连接条件）
表连接方式
from GUARANTY_CONTRACT_temp GC, CONTRACT_RELATIVE_temp CR, BUSINESS_CONTRACT_temp BC, ENT_INFO EI, BUSINESS_TYPE BT, CUSTOMER_INFO_temp CI
这是多表的内连接，所有表通过 where 子句中的字段进行关联。

主连接字段
BC.SerialNo = CR.SerialNo
业务合同表与合同关联表通过合同编号（SerialNo）关联。
CR.ObjectNo = GC.SerialNo
合同关联表与担保合同表通过合同编号关联。
BT.TypeNO = BC.BusinessType
业务类型表与业务合同表通过业务类型编号关联。
EI.CustomerID = BC.CustomerID
企业信息表与业务合同表通过客户号关联。
CI.CUSTOMERID = GC.GuarantorID
客户信息表与担保合同表通过担保人客户号关联。

查询条件
合同类型筛选
CR.ObjectType = 'GUARANTY_CONTRACT'
只选取合同关联类型为“担保合同”的数据。

担保方式筛选
GC.GuarantyType in('010','020','030','040','045')
只选取担保方式为保证、抵押、质押等类型的数据。

业务类型筛选
BT.Attribute16 in ('11','13','21','12')
只选取贷款业务、保理、票据贴现、贸易融资等业务类型。

合同状态筛选
GC.ContractStatus in ('020','030','040')
只选取合同状态为有效、已生效、已完成等状态的数据。

责任人有效性筛选
(CI.CertType in ('0','1','2','3','4','5','6','7','8','9','X') or length(getLoanCardNo(GC.GuarantorID)) >=16)
责任人证件类型必须在指定范围内，或者贷款卡号长度大于等于16，保证责任人身份有效。

总结：
通过合同编号、客户号等字段将担保合同、合同关联、业务合同、企业信息、业务类型、客户信息等表进行多层级关联。
通过担保方式、业务类型、合同状态、责任人有效性等条件筛选出符合征信报送要求的还款责任人信息数据。

------------------------------------------------------------------------------------------------------------
ECR_INT_GUARACCTINFO加载企业表外业务信息
select distinct 
    case when BD.BUSINESSTYPE = '2012' then substr(BD.serialno,14,17) else BD.SerialNo end as BILLCODE, {#票据流水号}
    bd.relativeserialno2 as BCTRCTCODE, {#合同号} 
    bd.CustomerID as CUSTOMERID, {#客户号}
    replace(bd.putoutdate,'/','-')		as OPENDATE, {#开户日期}          	  
    replace(BD.ActualMaturity ,'/','-')	as DUEDATE, {#到期日期}          	  
    BD.BusinessCurrency	as CY, {#币种}             	    
    BD.BusinessSum as GUARAMT, {#担保金额}          	  
    bd.Balance	as ACCTBAL, {#余额}             	    
    '{$ARE.bizDate}' as BALCHGDAT, {#余额变化日期    ------核心取}
    case when length(ltrim(rtrim(BD.ClassifyResult)))=0 then '9' else coalesce(BD.ClassifyResult,'9') end 	as FIVECATE, {#五级分类}          	
    replace(BD.CLASSIFYDATE,'/','-') as FIVECATEADJDATE, {#五级分类认定日期}  	
    case when BD.Finishdate is not null and BD.Finishdate &lt;&gt; '' then '3' 
    when (coalesce(BD.OverDueBalance,0) + coalesce(BD.DullBalance,0) + coalesce(BD.BadBalance,0)+coalesce(BD.ReplaceOverdueBalance,0)+coalesce(BD.ReplaceDullBalance,0)+coalesce(BD.ReplaceBadBalance,0)+coalesce(CancelBalance,0)+coalesce(BD.Interestbalance1,0) + coalesce(BD.Interestbalance2,0)+coalesce(FineBalance1,0)+coalesce(FineBalance2,0)+coalesce(ReplaceInterestBalance,0)+coalesce(CancelInterest,0)) &gt; 0 then '2'
    else '1' end as ACCTSTATUS, {#账户状态}          	
    replace(BD.FinishDate,'/','-') as CLOSEDATE, {#账户关闭日期--------核心  借据 }     	
    bd.CustomerName	as NAME, {#借款人名称}   
    case when length(trim(EI.LoancardNo))=16 then EI.LoancardNo 
            when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then EI.Corpid
            when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then EI.Corpid end as IDNUM, {#借款人身份标识号码}    
    case when length(trim(EI.LoancardNo))=16 then '10' 
            when length(trim(EI.Corpid))=18 and EI.Corpid not like '%-%' then '20'
            when length(trim(EI.Corpid))=10 and EI.Corpid like '%-%' then '30' end	 as IDTYPE, {#借款人身份标识类型}
    '0'	as COMPADVFLAG, {#代偿（垫款）标志      ------核心取 需要确认----}  	
    bd.businesssum*(1-bc.BAILRATIO)	as RIEX, {#风险敞口        业务确认：按照保证金金额无法覆盖的本金额  ？？ 有无保证金}          	
    bd.manageorgid	as MANAGERORGID, {#管户机构编号} 		      
    bd.manageuserid	as MANAGERUSERID, {#管户用户编号} 		    
    replace(bd.updatedate,'/','-') 	as UPDATEDATE, {#更新日期}  			        
    '{$ARE.bizDate}'	as OCCURDATE  {#数据日期}  	
from business_duebill_temp bd ,business_type bt ,ent_info ei ,business_contract_temp bc
where bt.typeno =bd.businesstype 
and bd.relativeserialno2=bc.serialno 
and ei.customerid = bd.customerid 
{#5信用证 6保函 7承兑汇票and substr(BT.Attribute15,1,1) in ('5','6','7')}
and (substr(bt.attribute15,1,1)='5'or bt.attribute16='7'or bt.attribute16='6') {#5信用证7保函 6承兑汇票}

----注释
关联表及条件
business_duebill_temp bd：借据临时表，主表。
business_type bt：业务类型表，通过 bt.typeno = bd.businesstype 关联。
ent_info ei：企业信息表，通过 ei.customerid = bd.customerid 关联。
business_contract_temp bc：合同临时表，通过 bd.relativeserialno2 = bc.serialno 关联。
查询条件
bt.typeno = bd.businesstype：借据的业务类型与业务类型表关联。
bd.relativeserialno2 = bc.serialno：借据与合同关联。
ei.customerid = bd.customerid：借据与企业信息关联。
(substr(bt.attribute15,1,1)='5' or bt.attribute16='7' or bt.attribute16='6')：只筛选业务类型为5（信用证）、7（保函）、6（承兑汇票）的票据业务。