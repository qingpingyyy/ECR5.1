--导入借贷账户信息18:49:30-18:49:35----------跑这个
select EAI.BAcctCode as AcctCode{#账户标识码},
    EAI.BCtrctCode as BCtrctCode{#合同号},
    EAI.CustomerID as CustomerID{#客户号},
    EAC.ContractCode as ContractCode{#授信协议标识码},
    EAC.AcctType as AcctType{#账户类型},
    EAC.Flag as Flag{#分次放款标志},
    EAI.Name as Name{#借款人名称},
    EAI.IDType as IDType{#借款人身份标识类型},
    EAI.IDNum as IDNum{#借款人身份标识号码},
    EAC.BusiLines as BusiLines{#借贷业务大类},
    EAC.BusiDtlLines as BusiDtlLines{#借贷业务种类细分},
    EAI.RepayMode as RepayMode{#还款方式},
    EAI.RepayFreqcy as RepayFreqcy{#还款频率},
    EAI.NxtAgrrRpyDate as NxtAgrrRpyDate{#下一次约定还款日期},
    EAI.LoanTimeLimCat as LoanTimeLimCat{#借款期限分类},
    EAI.OpenDate as OpenDate{#开户日期},
    EAI.DueDate as DueDate{#到期日期},
    EAI.Cy as Cy{#币种},
    EAC.CtrCtamt as AcctCredLine{#信用额度},
    EAI.LoanAmt as LoanAmt{#借款金额},
    EAI.AcctBal as AcctBal{#余额},
    EAI.BalChgDat as BalChgDat{#余额变化日期},
    EAI.NormalBal as NormalBal{#正常本金余额},
    EAI.OverdBal as OverdBal{#逾期本金余额},
    EAI.Interest as Interest{#利息},
    EAI.OverdDy as OverdDy{#当前逾期天数},
    EAI.FiveCate as FiveCate{#五级分类},
    EAI.FiveCateAdjDate as FiveCateAdjDate{#五级分类认定日期},
    EAI.AcctStatus as AcctStatus{#账户状态},
    EAI.CloseDate as CloseDate{#账户关闭日期},
    EAC.GuarMode as GuarMode{#担保方式},
    EAC.OthRepyGuarWay as OthRepyGuarWay{#其他还款保证方式},
    EAC.ActInvest as ActInvest{#贷款实际投向},
    EAC.LoaFrm as LoaFrm{#贷款发放形式},
    EAI.AssetTrandFlag as AssetTrandFlag{#资产转让标志},
    EAC.FundSou as FundSou{#业务经营类型},
    EAC.ApplyBusiDist as ApplyBusiDist{#业务申请地行政区划代码},
    EAI.ManagerorgID as ManagerorgID{#管户机构编号},
    EAI.ManagerUserID as ManagerUserID{#管户用户编号},
    EAI.UpdateDate as UpdateDate{#更新日期}
from ECR_INT_ACCTINFO EAI,ECR_INT_ACCTCTRCT EAC
where EAI.BCtrctCode = EAC.BCtrctCode
    and (EAC.AcctType = 'R4' or EAC.AcctType = 'D2' or EAC.AcctType = 'C1' or (EAC.AcctType = 'D1' and (EAC.Flag = '0' or EAC.Flag = '1')))
    and (EAI.DueDate &gt; EAI.OpenDate or EAC.AcctType ='C1') {#到期日期大于开立日期}
    and EAI.LoanAmt &gt; 0{#发放金额大于0}
    and (EAI.AcctBal + EAI.Interest &gt; 0 {#未结清数据}
    or (EAI.AcctBal + EAI.Interest = 0 and EAI.CloseDate &gt;= '{$ARE.appDate}'
        and EAI.CloseDate &gt;= '{$ARE.lastMonth}-01' and EAI.CloseDate &lt;= '{$ARE.bizDate}')){#近阶段结清数据}
--更新列
AcctType,Flag,Name,IDType,IDNum,BusiLines,BusiDtlLines,RepayMode,RepayFreqcy,NxtAgrrRpyDate,
LoanTimeLimCat,OpenDate,DueDate,Cy,AcctCredLine,LoanAmt,AcctBal,BalChgDat,NormalBal,OverdBal,
Interest,OverdDy,FiveCate,FiveCateAdjDate,AcctStatus,CloseDate,GuarMode,OthRepyGuarWay,ActInvest,
LoaFrm,AssetTrandFlag,FundSou,ApplyBusiDist,RptDateCode,RecordFlag,ManagerorgID,ManagerUserID,
UpdateDate,OccurDate
--对比列
AcctType,Name,IDType,IDNum,NxtAgrrRpyDate,AcctBal,BalChgDat,NormalBal,OverdBal,Interest,OverdDy,
FiveCate,FiveCateAdjDate,AcctStatus,CloseDate,DueDate

--导入担保账户信息
select EGI.BillCode as AcctCode{#账户标识码},
    EGI.BCtrctCode as BCtrctCode{#BCtrctCode},
    EGI.CustomerID as CustomerID{#客户号},
    EAC.ContractCode as ContractCode{#授信协议标识码},
    EAC.AcctType as AcctType{#账户类型},
    EAC.Flag as Flag{#分次放款标志},
    EGI.Name as Name{#借款人名称},
    EGI.IDType as IDType{#借款人身份标识类型},
    EGI.IDNum as IDNum{#借款人身份标识号码},
    EAC.BusiLines as BusiLines{#借贷业务大类},
    EAC.BusiDtlLines as BusiDtlLines{#借贷业务种类细分},
    EGI.OpenDate as OpenDate{#开户日期},
    EGI.DueDate as DueDate{#到期日期},
    EGI.Cy as Cy{#币种},
    EGI.GuarAmt as LoanAmt{#借款金额},
    EGI.AcctBal as AcctBal{#余额},
    EGI.BalChgDat as BalChgDat{#余额变化日期},
    EGI.FiveCate as FiveCate{#五级分类},
    EGI.FiveCateAdjDate as FiveCateAdjDate{#五级分类认定日期},
    EGI.AcctStatus as AcctStatus{#账户状态},
    EGI.CloseDate as CloseDate{#账户关闭日期},
    EAC.GuarMode as GuarMode{#担保方式},
    EAC.OthRepyGuarWay as OthRepyGuarWay{#其他还款保证方式},
    EAC.SecDep as SecDep{#保证金百分比},
    EGI.CompAdvFlag as CompAdvFlag{#代偿（垫款）标志},
    EGI.RiEx as RiEx{#风险敞口},
    EAC.CycFlag as CycFlag{#可循环标志},
    EGI.ManagerorgID as ManagerorgID{#管户机构编号},
    EGI.ManagerUserID as ManagerUserID{#管户用户编号},
    EGI.UpdateDate as UpdateDate{#更新日期}
from ECR_INT_GUARACCTINFO EGI,ECR_INT_ACCTCTRCT EAC
where  EGI.BCtrctCode = EAC.BCtrctCode
    and EGI.DueDate &gt; EGI.OpenDate {#到期日期大于开立日期}
    and EGI.GuarAmt &gt; 0{#发放金额大于0}
    and (EGI.AcctBal &gt; 0
    or (EGI.AcctBal = 0 and EGI.CloseDate &gt;= '{$ARE.appDate}'
        and EGI.CloseDate &gt;= '{$ARE.lastMonth}-01' and EGI.CloseDate &lt;= '{$ARE.bizDate}')){#未结清和近阶段结清数据}
--更新列
AcctType,Name,IDType,IDNum,BusiLines,BusiDtlLines,OpenDate,Cy,LoanAmt,AcctBal,BalChgDat,FiveCate,
FiveCateAdjDate,AcctStatus,CloseDate,GuarMode,OthRepyGuarWay,SecDep,CompAdvFlag,RiEx,CycFlag,
RptDateCode,RecordFlag,ManagerorgID,ManagerUserID,UpdateDate,OccurDate
--对比列
AcctType,Name,IDType,IDNum,OpenDate,LoanAmt,AcctBal,BalChgDat,FiveCate,FiveCateAdjDate,
AcctStatus,CloseDate,SecDep,CompAdvFlag,RiEx

--导入企业循环贷账户
select EAC.BCtrctCode as AcctCode{#账户标识码},
    EAC.BCtrctCode as BCtrctCode{#合同号},
    EAC.CustomerID as CustomerID{#客户号},
    EAC.ContractCode as ContractCode{#授信协议标识码},
    EAC.AcctType as AcctType{#账户类型},
    EAC.Flag as Flag{#分次放款标志},
    EAC.Name as Name{#借款人名称},
    EAC.IDType as IDType{#借款人身份标识类型},
    EAC.IDNum as IDNum{#借款人身份标识号码},
    EAC.BusiLines as BusiLines{#借贷业务大类},
    EAC.BusiDtlLines as BusiDtlLines{#借贷业务种类细分},
    '90' as RepayMode{#还款方式},--汇总报送，不区分还款方式
    '10' as RepayFreqcy{#还款频率},--月
    AA.NxtAgrrRpyDate as NxtAgrrRpyDate{#下一次约定还款日期},
    AA.LoanTimeLimCat as LoanTimeLimCat{#借款期限分类},
    AA.OpenDate as OpenDate{#开户日期},
    case when EAC.AcctType ='R1' then EAC.DueDate else AA.DueDate end as DueDate{#到期日期},
    EAC.Cy as Cy{#币种},
    case when EAC.AcctType ='R1' then EAC.CtrCtamt else null end as AcctCredLine{#信用额度},
    case when EAC.AcctType ='R1' then null else AA.LoanAmt end as LoanAmt{#借款金额},
    AA.AcctBal as AcctBal{#余额},
    AA.BalChgDat as BalChgDat{#余额变化日期},
    AA.NormalBal as NormalBal{#正常本金余额},
    AA.OverdBal as OverdBal{#逾期本金余额},
    AA.Interest as Interest{#利息},
    AA.OverdDy as OverdDy{#当前逾期天数},
    EAC.FiveCate as FiveCate{#五级分类},
    EAC.FiveCateAdjDate as FiveCateAdjDate{#五级分类认定日期},
    AA.AcctStatus as AcctStatus{#账户状态},
    EAC.ConCloseDate as CloseDate{#账户关闭日期},
    EAC.GuarMode as GuarMode{#担保方式},
    EAC.OthRepyGuarWay as OthRepyGuarWay{#其他还款保证方式},
    EAC.ActInvest as ActInvest{#贷款实际投向},
    EAC.LoaFrm as LoaFrm{#贷款发放形式},
    EAC.FundSou as FundSou{#业务经营类型},
    AA.AssetTrandFlag as AssetTrandFlag{#资产转让标志},
    EAC.ApplyBusiDist as ApplyBusiDist{#业务申请地行政区划代码},
    EAC.ManagerOrgID as ManagerOrgID{#管户机构编号},
    EAC.ManagerUserID as ManagerUserID{#管户用户编号},
    EAC.UpdateDate as UpdateDate{#更新日期}
from (select EAC.BCtrctCode as AcctCode{#账户标识码},
    max(EAI.NxtAgrrRpyDate) as NxtAgrrRpyDate{#下一次约定还款日期},
    max(EAI.LoanTimeLimCat) as LoanTimeLimCat{#借款期限分类},
    min(EAI.OpenDate) as OpenDate{#开户日期},
    max(EAI.DueDate) as DueDate{#到期日期},
    sum(EAI.LoanAmt) as LoanAmt{#借款金额},
    sum(EAI.AcctBal) as AcctBal{#余额},
    max(EAI.BalChgDat) as BalChgDat{#余额变化日期},
    sum(EAI.NormalBal) as NormalBal{#正常本金余额},
    sum(EAI.OverdBal) as OverdBal{#逾期本金余额},
    sum(EAI.Interest) as Interest{#利息},
    max(EAI.OverdDy) as OverdDy{#当前逾期天数},
    max(EAI.AcctStatus) as AcctStatus{#账户状态},
    min(EAI.AssetTrandFlag) as AssetTrandFlag{#资产转让标志}
from ECR_INT_ACCTINFO EAI,ECR_INT_ACCTCTRCT EAC
where EAI.BCtrctCode = EAC.BCtrctCode 
    and ((EAC.AcctType ='R1' and '{$ARE.endDate}'='{$ARE.bizDate}'){#月底更新} or (EAC.AcctType ='D1' and EAC.Flag ='2')){#统一报送的循环贷和D1分次放款账户}
    and (EAC.ConStatus = '1' {#未结清数据}
    or (EAC.ConCloseDate &gt;= '{$ARE.appDate}' and EAC.ConCloseDate &gt;= '{$ARE.lastMonth}-01' and EAC.ConCloseDate &lt;= '{$ARE.bizDate}')){#近阶段结清数据}
    and EAI.DueDate &gt; EAI.OpenDate {#到期日期大于开立日期}
    and EAI.LoanAmt &gt; 0{#发放金额大于0}
    and EAI.RepayFreqcy is not null{#还款频率不为空}
group by EAC.BCtrctCode) AA,ECR_INT_ACCTCTRCT EAC
where AA.AcctCode = EAC.BCtrctCode

--更新列
AcctType,Flag,Name,IDType,IDNum,BusiDtlLines,RepayMode,RepayFreqcy,PymtPrd,NxtAgrrRpyDate,
LoanTimeLimCat,OpenDate,DueDate,Cy,AcctCredLine,LoanAmt,AcctBal,BalChgDat,NormalBal,OverdBal,
Interest,OverdDy,FiveCate,FiveCateAdjDate,AcctStatus,CloseDate,GuarMode,OthRepyGuarWay,ActInvest,
LoaFrm,AssetTrandFlag,FundSou,ApplyBusiDist,RptDateCode,RecordFlag,ManagerOrgID,ManagerUserID,
UpdateDate,OccurDate
--对比列				 	
AcctType,Flag,Name,IDType,IDNum,BusiDtlLines,RepayMode,RepayFreqcy,PymtPrd,NxtAgrrRpyDate,
LoanTimeLimCat,OpenDate,DueDate,Cy,AcctCredLine,LoanAmt,AcctBal,BalChgDat,NormalBal,OverdBal,
Interest,OverdDy,FiveCate,FiveCateAdjDate,AcctStatus,CloseDate,GuarMode,OthRepyGuarWay,ActInvest,
LoaFrm,AssetTrandFlag,FundSou,ApplyBusiDist



-- 创建临时表存储第一类账户数据（借贷账户信息）
CREATE TEMPORARY TABLE TEMP_ACCOUNT_TYPE1 AS
SELECT 
    EAI.BAcctCode AS ACCTCODE,
    EAI.BCtrctCode AS BCTRCTCODE,
    EAI.CustomerID AS CUSTOMERID,
    EAC.ContractCode AS CONTRACTCODE,
    EAC.AcctType AS ACCTTYPE,
    EAC.Flag AS FLAG,
    EAI.Name AS NAME,
    EAI.IDType AS IDTYPE,
    EAI.IDNum AS IDNUM,
    EAC.BusiLines AS BUSILINES,
    EAC.BusiDtlLines AS BUSIDTLLINES,
    EAI.RepayMode AS REPAYMODE,
    EAI.RepayFreqcy AS REPAYFREQCY,
    NULL AS PYMTPRD,
    EAI.LoanTimeLimCat AS LOANTIMELIMCAT,
    EAI.OpenDate AS OPENDATE,
    EAI.DueDate AS DUEDATE,
    EAI.Cy AS CY,
    EAC.CtrCtamt AS ACCTCREDLINE,
    EAI.LoanAmt AS LOANAMT,
    EAI.AcctBal AS ACCTBAL,
    EAI.BalChgDat AS BALCHGDAT,
    EAI.NormalBal AS NORMALBAL,
    EAI.OverdBal AS OVERDBAL,
    EAI.Interest AS INTEREST,
    EAI.OverdDy AS OVERDDY,
    EAI.FiveCate AS FIVECATE,
    EAI.FiveCateAdjDate AS FIVECATEADJDATE,
    EAI.NxtAgrrRpyDate AS NXTAGRRRPYDATE,
    EAI.AcctStatus AS ACCTSTATUS,
    EAI.CloseDate AS CLOSEDATE,
    EAC.GuarMode AS GUARMODE,
    EAC.OthRepyGuarWay AS OTHREPYGUARWAY,
    NULL AS SECDEP,
    EAC.ActInvest AS ACTINVEST,
    EAC.LoaFrm AS LOAFRM,
    EAI.AssetTrandFlag AS COMPADVFLAG,
    NULL AS RIEX,
    EAI.AssetTrandFlag AS ASSETTRANDFLAG,
    EAC.FundSou AS FUNDSOU,
    NULL AS CYCFLAG,
    EAC.ApplyBusiDist AS APPLYBUSIDIST,
    'A' AS RECORDFLAG,
    NULL AS RPTDATECODE,
    EAI.ManagerorgID AS MANAGERORGID,
    EAI.ManagerUserID AS MANAGERUSERID,
    NULL AS OPERORGID,
    NULL AS OPERUSERID,
    EAI.UpdateDate AS UPDATEDATE,
    '${bizDate}' AS OCCURDATE,
    NULL AS EXTEND1,
    NULL AS EXTEND2,
    NULL AS EXTEND3
FROM ECR_INT_ACCTINFO EAI
JOIN ECR_INT_ACCTCTRCT EAC ON EAI.BCtrctCode = EAC.BCtrctCode
WHERE (EAC.AcctType = 'R4' OR EAC.AcctType = 'D2' OR EAC.AcctType = 'C1' 
       OR (EAC.AcctType = 'D1' AND (EAC.Flag = '0' OR EAC.Flag = '1')))
  AND (EAI.DueDate > EAI.OpenDate OR EAC.AcctType = 'C1')
  AND EAI.LoanAmt > 0
  AND (EAI.AcctBal + EAI.Interest > 0
       OR (EAI.AcctBal + EAI.Interest = 0 
           AND EAI.CloseDate >= '${appDate}' 
           AND EAI.CloseDate >= '${lastMonth}-01' 
           AND EAI.CloseDate <= '${bizDate}'))
;

-- 创建临时表存储第二类账户数据（担保账户信息）
CREATE TEMPORARY TABLE TEMP_ACCOUNT_TYPE2 AS
SELECT 
    EGI.BillCode AS ACCTCODE,
    EGI.BCtrctCode AS BCTRCTCODE,
    EGI.CustomerID AS CUSTOMERID,
    EAC.ContractCode AS CONTRACTCODE,
    EAC.AcctType AS ACCTTYPE,
    EAC.Flag AS FLAG,
    EGI.Name AS NAME,
    EGI.IDType AS IDTYPE,
    EGI.IDNum AS IDNUM,
    EAC.BusiLines AS BUSILINES,
    EAC.BusiDtlLines AS BUSIDTLLINES,
    NULL AS REPAYMODE,
    NULL AS REPAYFREQCY,
    NULL AS PYMTPRD,
    NULL AS LOANTIMELIMCAT,
    EGI.OpenDate AS OPENDATE,
    EGI.DueDate AS DUEDATE,
    EGI.Cy AS CY,
    NULL AS ACCTCREDLINE,
    EGI.GuarAmt AS LOANAMT,
    EGI.AcctBal AS ACCTBAL,
    EGI.BalChgDat AS BALCHGDAT,
    NULL AS NORMALBAL,
    NULL AS OVERDBAL,
    NULL AS INTEREST,
    NULL AS OVERDDY,
    EGI.FiveCate AS FIVECATE,
    EGI.FiveCateAdjDate AS FIVECATEADJDATE,
    NULL AS NXTAGRRRPYDATE,
    EGI.AcctStatus AS ACCTSTATUS,
    EGI.CloseDate AS CLOSEDATE,
    EAC.GuarMode AS GUARMODE,
    EAC.OthRepyGuarWay AS OTHREPYGUARWAY,
    EAC.SecDep AS SECDEP,
    NULL AS ACTINVEST,
    NULL AS LOAFRM,
    EGI.CompAdvFlag AS COMPADVFLAG,
    EGI.RiEx AS RIEX,
    NULL AS ASSETTRANDFLAG,
    NULL AS FUNDSOU,
    EAC.CycFlag AS CYCFLAG,
    EAC.ApplyBusiDist AS APPLYBUSIDIST,
    'A' AS RECORDFLAG,
    NULL AS RPTDATECODE,
    EGI.ManagerorgID AS MANAGERORGID,
    EGI.ManagerUserID AS MANAGERUSERID,
    NULL AS OPERORGID,
    NULL AS OPERUSERID,
    EGI.UpdateDate AS UPDATEDATE,
    '${bizDate}' AS OCCURDATE,
    NULL AS EXTEND1,
    NULL AS EXTEND2,
    NULL AS EXTEND3
FROM ECR_INT_GUARACCTINFO EGI
JOIN ECR_INT_ACCTCTRCT EAC ON EGI.BCtrctCode = EAC.BCtrctCode
WHERE EGI.DueDate > EGI.OpenDate
  AND EGI.GuarAmt > 0
  AND (EGI.AcctBal > 0
       OR (EGI.AcctBal = 0 
           AND EGI.CloseDate >= '${appDate}' 
           AND EGI.CloseDate >= '${lastMonth}-01' 
           AND EGI.CloseDate <= '${bizDate}'))
;

-- 创建临时表存储第三类账户数据（循环贷账户信息）
CREATE TEMPORARY TABLE TEMP_ACCOUNT_TYPE3 AS
SELECT 
    EAC.BCtrctCode AS ACCTCODE,
    EAC.BCtrctCode AS BCTRCTCODE,
    EAC.CustomerID AS CUSTOMERID,
    EAC.ContractCode AS CONTRACTCODE,
    EAC.AcctType AS ACCTTYPE,
    EAC.Flag AS FLAG,
    EAC.Name AS NAME,
    EAC.IDType AS IDTYPE,
    EAC.IDNum AS IDNUM,
    EAC.BusiLines AS BUSILINES,
    EAC.BusiDtlLines AS BUSIDTLLINES,
    '90' AS REPAYMODE,
    '10' AS REPAYFREQCY,
    NULL AS PYMTPRD,
    AA.LoanTimeLimCat AS LOANTIMELIMCAT,
    AA.OpenDate AS OPENDATE,
    CASE 
        WHEN EAC.AcctType = 'R1' THEN EAC.DueDate 
        ELSE AA.DueDate 
    END AS DUEDATE,
    EAC.Cy AS CY,
    CASE 
        WHEN EAC.AcctType = 'R1' THEN EAC.CtrCtamt 
        ELSE NULL 
    END AS ACCTCREDLINE,
    CASE 
        WHEN EAC.AcctType = 'R1' THEN NULL 
        ELSE AA.LoanAmt 
    END AS LOANAMT,
    AA.AcctBal AS ACCTBAL,
    AA.BalChgDat AS BALCHGDAT,
    AA.NormalBal AS NORMALBAL,
    AA.OverdBal AS OVERDBAL,
    AA.Interest AS INTEREST,
    AA.OverdDy AS OVERDDY,
    EAC.FiveCate AS FIVECATE,
    EAC.FiveCateAdjDate AS FIVECATEADJDATE,
    AA.NxtAgrrRpyDate AS NXTAGRRRPYDATE,
    AA.AcctStatus AS ACCTSTATUS,
    EAC.ConCloseDate AS CLOSEDATE,
    EAC.GuarMode AS GUARMODE,
    EAC.OthRepyGuarWay AS OTHREPYGUARWAY,
    NULL AS SECDEP,
    EAC.ActInvest AS ACTINVEST,
    EAC.LoaFrm AS LOAFRM,
    AA.AssetTrandFlag AS COMPADVFLAG,
    NULL AS RIEX,
    AA.AssetTrandFlag AS ASSETTRANDFLAG,
    EAC.FundSou AS FUNDSOU,
    NULL AS CYCFLAG,
    EAC.ApplyBusiDist AS APPLYBUSIDIST,
    'A' AS RECORDFLAG,
    NULL AS RPTDATECODE,
    EAC.ManagerOrgID AS MANAGERORGID,
    EAC.ManagerUserID AS MANAGERUSERID,
    NULL AS OPERORGID,
    NULL AS OPERUSERID,
    EAC.UpdateDate AS UPDATEDATE,
    '${bizDate}' AS OCCURDATE,
    NULL AS EXTEND1,
    NULL AS EXTEND2,
    NULL AS EXTEND3
FROM (
    SELECT 
        EAC.BCtrctCode AS AcctCode,
        MAX(EAI.NxtAgrrRpyDate) AS NxtAgrrRpyDate,
        MAX(EAI.LoanTimeLimCat) AS LoanTimeLimCat,
        MIN(EAI.OpenDate) AS OpenDate,
        MAX(EAI.DueDate) AS DueDate,
        SUM(EAI.LoanAmt) AS LoanAmt,
        SUM(EAI.AcctBal) AS AcctBal,
        MAX(EAI.BalChgDat) AS BalChgDat,
        SUM(EAI.NormalBal) AS NormalBal,
        SUM(EAI.OverdBal) AS OverdBal,
        SUM(EAI.Interest) AS Interest,
        MAX(EAI.OverdDy) AS OverdDy,
        MAX(EAI.AcctStatus) AS AcctStatus,
        MIN(EAI.AssetTrandFlag) AS AssetTrandFlag
    FROM ECR_INT_ACCTINFO EAI
    JOIN ECR_INT_ACCTCTRCT EAC ON EAI.BCtrctCode = EAC.BCtrctCode
    WHERE ((EAC.AcctType = 'R1' AND '${endDate}' = '${bizDate}')
           OR (EAC.AcctType = 'D1' AND EAC.Flag = '2'))
      AND (EAC.ConStatus = '1'
           OR (EAC.ConCloseDate >= '${appDate}' 
               AND EAC.ConCloseDate >= '${lastMonth}-01' 
               AND EAC.ConCloseDate <= '${bizDate}'))
      AND EAI.DueDate > EAI.OpenDate
      AND EAI.LoanAmt > 0
      AND EAI.RepayFreqcy IS NOT NULL
    GROUP BY EAC.BCtrctCode
) AA
JOIN ECR_INT_ACCTCTRCT EAC ON AA.AcctCode = EAC.BCtrctCode
;

-- 创建ECR_ACCOUNTINFO表，用于存储合并后的账户信息
CREATE TABLE IF NOT EXISTS ECR_ACCOUNTINFO (
    ACCTCODE STRING COMMENT '账户标识码',
    BCTRCTCODE STRING COMMENT '合同号',
    CUSTOMERID STRING COMMENT '客户号',
    CONTRACTCODE STRING COMMENT '授信协议标识码',
    ACCTTYPE STRING COMMENT '账户类型',
    FLAG STRING COMMENT '分次放款标志',
    NAME STRING COMMENT '借款人姓名',
    IDTYPE STRING COMMENT '借款人身份标识类型',
    IDNUM STRING COMMENT '借款人身份标识号码',
    BUSILINES STRING COMMENT '借贷业务大类',
    BUSIDTLLINES STRING COMMENT '借贷业务种类细分',
    REPAYMODE STRING COMMENT '还款方式',
    REPAYFREQCY STRING COMMENT '还款频率',
    PYMTPRD STRING COMMENT '剩余还款期数',
    LOANTIMELIMCAT STRING COMMENT '贷款期限分类',
    OPENDATE STRING COMMENT '开户日期',
    DUEDATE STRING COMMENT '到期日期',
    CY STRING COMMENT '币种',
    ACCTCREDLINE DECIMAL(20,2) COMMENT '授信额度',
    LOANAMT DECIMAL(20,2) COMMENT '借款金额',
    ACCTBAL DECIMAL(20,2) COMMENT '余额',
    BALCHGDAT STRING COMMENT '余额变化日期',
    NORMALBAL DECIMAL(20,2) COMMENT '正常类贷款余额',
    OVERDBAL DECIMAL(20,2) COMMENT '逾期类贷款余额',
    INTEREST DECIMAL(20,2) COMMENT '利息',
    OVERDDY INT COMMENT '当前逾期天数',
    FIVECATE STRING COMMENT '五级分类',
    FIVECATEADJDATE STRING COMMENT '五级分类认定日期',
    NXTAGRRRPYDATE STRING COMMENT '下一次约定还款日期',
    ACCTSTATUS STRING COMMENT '账户状态',
    CLOSEDATE STRING COMMENT '账户关闭日期',
    GUARMODE STRING COMMENT '担保方式',
    OTHREPYGUARWAY STRING COMMENT '其他还款保证方式',
    SECDEP DECIMAL(10,4) COMMENT '保证金百分比',
    ACTINVEST STRING COMMENT '贷款实际投向',
    LOAFRM STRING COMMENT '贷款发放形式',
    COMPADVFLAG STRING COMMENT '代偿（垫款）标志',
    RIEX DECIMAL(20,2) COMMENT '风险敞口',
    ASSETTRANDFLAG STRING COMMENT '资产转让标志',
    FUNDSOU STRING COMMENT '业务经营用途',
    CYCFLAG STRING COMMENT '可循环标志',
    APPLYBUSIDIST STRING COMMENT '业务申请时距',
    RECORDFLAG STRING COMMENT '记录标志',
    RPTDATECODE STRING COMMENT '报送时点说明代码',
    MANAGERORGID STRING COMMENT '管户机构编号',
    MANAGERUSERID STRING COMMENT '管户用户编号',
    OPERORGID STRING COMMENT '操作机构编号',
    OPERUSERID STRING COMMENT '操作用户编号',
    UPDATEDATE STRING COMMENT '更新日期',
    OCCURDATE STRING COMMENT '数据日期',
    EXTEND1 STRING COMMENT '扩展字段1',
    EXTEND2 STRING COMMENT '扩展字段2',
    EXTEND3 STRING COMMENT '扩展字段3'
)
STORED AS PARQUET;

-- 使用FULL OUTER JOIN合并所有数据到ECR_ACCOUNTINFO表中
INSERT OVERWRITE TABLE ECR_ACCOUNTINFO
SELECT 
    COALESCE(t1.ACCTCODE, t2.ACCTCODE, t3.ACCTCODE) AS ACCTCODE,
    COALESCE(t1.BCTRCTCODE, t2.BCTRCTCODE, t3.BCTRCTCODE) AS BCTRCTCODE,
    COALESCE(t1.CUSTOMERID, t2.CUSTOMERID, t3.CUSTOMERID) AS CUSTOMERID,
    COALESCE(t1.CONTRACTCODE, t2.CONTRACTCODE, t3.CONTRACTCODE) AS CONTRACTCODE,
    COALESCE(t1.ACCTTYPE, t2.ACCTTYPE, t3.ACCTTYPE) AS ACCTTYPE,
    COALESCE(t1.FLAG, t2.FLAG, t3.FLAG) AS FLAG,
    COALESCE(t1.NAME, t2.NAME, t3.NAME) AS NAME,
    COALESCE(t1.IDTYPE, t2.IDTYPE, t3.IDTYPE) AS IDTYPE,
    COALESCE(t1.IDNUM, t2.IDNUM, t3.IDNUM) AS IDNUM,
    COALESCE(t1.BUSILINES, t2.BUSILINES, t3.BUSILINES) AS BUSILINES,
    COALESCE(t1.BUSIDTLLINES, t2.BUSIDTLLINES, t3.BUSIDTLLINES) AS BUSIDTLLINES,
    COALESCE(t1.REPAYMODE, t2.REPAYMODE, t3.REPAYMODE) AS REPAYMODE,
    COALESCE(t1.REPAYFREQCY, t2.REPAYFREQCY, t3.REPAYFREQCY) AS REPAYFREQCY,
    COALESCE(t1.PYMTPRD, t2.PYMTPRD, t3.PYMTPRD) AS PYMTPRD,
    COALESCE(t1.LOANTIMELIMCAT, t2.LOANTIMELIMCAT, t3.LOANTIMELIMCAT) AS LOANTIMELIMCAT,
    COALESCE(t1.OPENDATE, t2.OPENDATE, t3.OPENDATE) AS OPENDATE,
    COALESCE(t1.DUEDATE, t2.DUEDATE, t3.DUEDATE) AS DUEDATE,
    COALESCE(t1.CY, t2.CY, t3.CY) AS CY,
    COALESCE(t1.ACCTCREDLINE, t2.ACCTCREDLINE, t3.ACCTCREDLINE) AS ACCTCREDLINE,
    COALESCE(t1.LOANAMT, t2.LOANAMT, t3.LOANAMT) AS LOANAMT,
    COALESCE(t1.ACCTBAL, t2.ACCTBAL, t3.ACCTBAL) AS ACCTBAL,
    COALESCE(t1.BALCHGDAT, t2.BALCHGDAT, t3.BALCHGDAT) AS BALCHGDAT,
    COALESCE(t1.NORMALBAL, t2.NORMALBAL, t3.NORMALBAL) AS NORMALBAL,
    COALESCE(t1.OVERDBAL, t2.OVERDBAL, t3.OVERDBAL) AS OVERDBAL,
    COALESCE(t1.INTEREST, t2.INTEREST, t3.INTEREST) AS INTEREST,
    COALESCE(t1.OVERDDY, t2.OVERDDY, t3.OVERDDY) AS OVERDDY,
    COALESCE(t1.FIVECATE, t2.FIVECATE, t3.FIVECATE) AS FIVECATE,
    COALESCE(t1.FIVECATEADJDATE, t2.FIVECATEADJDATE, t3.FIVECATEADJDATE) AS FIVECATEADJDATE,
    COALESCE(t1.NXTAGRRRPYDATE, t2.NXTAGRRRPYDATE, t3.NXTAGRRRPYDATE) AS NXTAGRRRPYDATE,
    COALESCE(t1.ACCTSTATUS, t2.ACCTSTATUS, t3.ACCTSTATUS) AS ACCTSTATUS,
    COALESCE(t1.CLOSEDATE, t2.CLOSEDATE, t3.CLOSEDATE) AS CLOSEDATE,
    COALESCE(t1.GUARMODE, t2.GUARMODE, t3.GUARMODE) AS GUARMODE,
    COALESCE(t1.OTHREPYGUARWAY, t2.OTHREPYGUARWAY, t3.OTHREPYGUARWAY) AS OTHREPYGUARWAY,
    COALESCE(t1.SECDEP, t2.SECDEP, t3.SECDEP) AS SECDEP,
    COALESCE(t1.ACTINVEST, t2.ACTINVEST, t3.ACTINVEST) AS ACTINVEST,
    COALESCE(t1.LOAFRM, t2.LOAFRM, t3.LOAFRM) AS LOAFRM,
    COALESCE(t1.COMPADVFLAG, t2.COMPADVFLAG, t3.COMPADVFLAG) AS COMPADVFLAG,
    COALESCE(t1.RIEX, t2.RIEX, t3.RIEX) AS RIEX,
    COALESCE(t1.ASSETTRANDFLAG, t2.ASSETTRANDFLAG, t3.ASSETTRANDFLAG) AS ASSETTRANDFLAG,
    COALESCE(t1.FUNDSOU, t2.FUNDSOU, t3.FUNDSOU) AS FUNDSOU,
    COALESCE(t1.CYCFLAG, t2.CYCFLAG, t3.CYCFLAG) AS CYCFLAG,
    COALESCE(t1.APPLYBUSIDIST, t2.APPLYBUSIDIST, t3.APPLYBUSIDIST) AS APPLYBUSIDIST,
    COALESCE(t1.RECORDFLAG, t2.RECORDFLAG, t3.RECORDFLAG) AS RECORDFLAG,
    COALESCE(t1.RPTDATECODE, t2.RPTDATECODE, t3.RPTDATECODE) AS RPTDATECODE,
    COALESCE(t1.MANAGERORGID, t2.MANAGERORGID, t3.MANAGERORGID) AS MANAGERORGID,
    COALESCE(t1.MANAGERUSERID, t2.MANAGERUSERID, t3.MANAGERUSERID) AS MANAGERUSERID,
    COALESCE(t1.OPERORGID, t2.OPERORGID, t3.OPERORGID) AS OPERORGID,
    COALESCE(t1.OPERUSERID, t2.OPERUSERID, t3.OPERUSERID) AS OPERUSERID,
    COALESCE(t1.UPDATEDATE, t2.UPDATEDATE, t3.UPDATEDATE) AS UPDATEDATE,
    COALESCE(t1.OCCURDATE, t2.OCCURDATE, t3.OCCURDATE) AS OCCURDATE,
    COALESCE(t1.EXTEND1, t2.EXTEND1, t3.EXTEND1) AS EXTEND1,
    COALESCE(t1.EXTEND2, t2.EXTEND2, t3.EXTEND2) AS EXTEND2,
    COALESCE(t1.EXTEND3, t2.EXTEND3, t3.EXTEND3) AS EXTEND3
FROM TEMP_ACCOUNT_TYPE1 t1
FULL OUTER JOIN TEMP_ACCOUNT_TYPE2 t2 ON t1.ACCTCODE = t2.ACCTCODE
FULL OUTER JOIN TEMP_ACCOUNT_TYPE3 t3 ON t1.ACCTCODE = t3.ACCTCODE OR t2.ACCTCODE = t3.ACCTCODE;



