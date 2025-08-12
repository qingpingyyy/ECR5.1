package com.amarsoft.app.pbc.ecr.dataimport;

import com.amarsoft.app.pbc.ecr.common.Tools;
import com.amarsoft.are.ARE;
import com.amarsoft.are.dpx.recordset.Field;
import com.amarsoft.are.dpx.recordset.Record;
import com.amarsoft.are.dpx.recordset.RecordSetException;
import com.amarsoft.are.dpx.recordset.UpdateDBHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportAccountHandler extends UpdateDBHandler implements Cloneable {
   private String occurDate = ARE.getProperty("bizDate");

   private void setField(Record var1, String var2, Object var3) {
      Field var4 = var1.getField(var2);
      if (var4 != null) {
         var4.setValue(var3);
      }

   }

   protected int checkRecord(Record var1) throws RecordSetException {
      this.setField(var1, "OccurDate", this.occurDate);
      String var2 = var1.getField("DueDate").getString();
      String var3 = var1.getField("AcctType").getString();
      String var4 = var1.getField("AcctStatus").getString();
      if ("R1".equals(var3)) {
         int var5 = Tools.betweenMonths(this.occurDate, var2);
         this.setField(var1, "PymtPrd", var5);
      }

      if (super.checkRecord(var1) == -1) {
         if (var4.equals("21")) {
            this.setField(var1, "RptDateCode", "20");
         } else {
            this.setField(var1, "RptDateCode", "10");
         }

         this.setField(var1, "RecordFlag", "A");
         return -1;
      } else if (this.match(var1, this.dbRecord)) {
         return 0;
      } else {
         String var6 = var1.getField("AcctCode").getString();
         Field var7 = var1.getField("AcctBal");
         Field var8 = this.dbRecord.getField("AcctBal");
         Field var9 = var1.getField("FiveCate");
         Field var10 = this.dbRecord.getField("FiveCate");
         Field var12;
         Field var13;
         if (!"G1".equals(var3) && !"G2".equals(var3) && !"G3".equals(var3)) {
            String var15 = var1.getField("Flag").getString();
            var12 = var1.getField("Interest");
            var13 = this.dbRecord.getField("Interest");
            if ("21".equals(var4)) {
               this.setField(var1, "RptDateCode", "20");
            } else if ("D1".equals(var3) && "2".equals(var15) && this.checkBD(var6)) {
               this.setField(var1, "RptDateCode", "31");
            } else if (("D1".equals(var3) || "D2".equals(var3) || "R4".equals(var3)) && (var7.compareTo(var8) < 0 || var12.compareTo(var13) < 0) && this.checkPayDate(var6)) {
               this.setField(var1, "RptDateCode", "32");
            } else if (("D1".equals(var3) || "D2".equals(var3) || "R4".equals(var3) || "C1".equals(var3)) && (var7.compareTo(var8) < 0 || var12.compareTo(var13) < 0) && !"31".equals(var4) && !"32".equals(var4)) {
               this.setField(var1, "RptDateCode", "33");
            } else if (("D1".equals(var3) || "D2".equals(var3) || "R4".equals(var3) || "C1".equals(var3)) && var10.compareTo(var9) != 0) {
               this.setField(var1, "RptDateCode", "41");
            } else if (("D1".equals(var3) || "R4".equals(var3)) && this.checkSpeciBusiness(var6)) {
               this.setField(var1, "RptDateCode", "42");
            } else {
               this.setField(var1, "RptDateCode", "49");
            }
         } else {
            Field var11 = var1.getField("RiEx");
            var12 = this.dbRecord.getField("RiEx");
            var13 = var1.getField("CompAdvFlag");
            Field var14 = this.dbRecord.getField("CompAdvFlag");
            if ("2".equals(var4)) {
               this.setField(var1, "RptDateCode", "20");
            } else if (var8.compareTo(var7) == 0 && var12.compareTo(var11) == 0 && var14.compareTo(var13) == 0) {
               if (var10.compareTo(var9) != 0) {
                  this.setField(var1, "RptDateCode", "40");
               } else {
                  this.setField(var1, "RptDateCode", "50");
               }
            } else {
               this.setField(var1, "RptDateCode", "30");
            }
         }

         this.setField(var1, "RecordFlag", "U");
         return 1;
      }
   }

   private boolean checkBD(String var1) throws RecordSetException {
      boolean var2 = false;
      String var3 = "select 1 from ECR_INT_ACCTINFO where BCtrctCode=? and OpenDate=?";
      PreparedStatement var4 = null;

      try {
         var4 = this.connection.prepareStatement(var3);
         var4.setString(1, var1);
         var4.setString(2, this.occurDate);
         ResultSet var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = true;
            this.logger.info("非首次放款 ");
         }

         var5.close();
      } catch (SQLException var13) {
         this.logger.error("校验是否非首次放款出错！", var13);
         throw new RecordSetException("校验是否非首次放款异常！");
      } finally {
         if (var4 != null) {
            try {
               var4.close();
            } catch (SQLException var12) {
               this.logger.error(var12);
            }
         }

      }

      return var2;
   }

   private boolean checkPayDate(String var1) throws RecordSetException {
      boolean var2 = false;
      String var3 = "select 1 from ECR_PAYPLAN where acctCode=? and payDate=?";
      PreparedStatement var4 = null;

      try {
         var4 = this.connection.prepareStatement(var3);
         var4.setString(1, var1);
         var4.setString(2, this.occurDate);
         ResultSet var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = true;
            this.logger.info("当前日期为约定还款日期 ");
         }

         var5.close();
      } catch (SQLException var13) {
         this.logger.error("校验还款计划的应还款时间和当前时间是否一致出错！", var13);
         throw new RecordSetException("校验还款计划的应还款时间和当前时间是否一致异常！");
      } finally {
         if (var4 != null) {
            try {
               var4.close();
            } catch (SQLException var12) {
               this.logger.error(var12);
            }
         }

      }

      return var2;
   }

   private boolean checkSpeciBusiness(String var1) throws RecordSetException {
      boolean var2 = false;
      String var3 = "select 1 from ECR_INT_SPECIBUSINESS where acctCode=? and TranDate=? and ChanTranType=?";
      PreparedStatement var4 = null;

      try {
         var4 = this.connection.prepareStatement(var3);
         var4.setString(1, var1);
         var4.setString(2, this.occurDate);
         var4.setString(3, "11");
         ResultSet var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = true;
            this.logger.info("发生展期");
         }

         var5.close();
      } catch (SQLException var13) {
         this.logger.error("校验当期是否为展期出错！", var13);
         throw new RecordSetException("校验当期是否为展期异常！");
      } finally {
         if (var4 != null) {
            try {
               var4.close();
            } catch (SQLException var12) {
               this.logger.error(var12);
            }
         }

      }

      return var2;
   }

   protected boolean match(Record var1, Record var2) {
      Field var3 = var2.getField("RecordFlag");
      return "X".equals(var3.getString()) ? true : super.match(var1, var2);
   }

   public ImportAccountHandler newHandler() throws RecordSetException {
      try {
         return (ImportAccountHandler)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RecordSetException(var2);
      }
   }
}

