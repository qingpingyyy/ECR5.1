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
