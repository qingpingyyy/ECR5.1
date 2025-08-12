package com.amarsoft.are.dpx.recordset;

import com.amarsoft.are.ARE;
import com.amarsoft.are.dpx.dataconvert.DataConvertor;
import com.amarsoft.are.dpx.dataconvert.DefaultConvertor;
import com.amarsoft.are.lang.StringX;
import com.amarsoft.are.sql.DataSourceURI;
import com.amarsoft.are.sql.TabularReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDataSourceProvider extends DataSourceProvider {
   protected HashMap convertors = null;
   protected HashMap c2fMap = null;
   private int[] c2fIndex = null;
   private int[] colTypes = null;
   private DataConvertor[] colConvertors = null;
   private String sourceEncoding = null;
   private boolean encodingConvert = false;

   public DefaultDataSourceProvider() {
      this.convertors = new HashMap();
      this.c2fMap = new HashMap();
   }

   public DefaultDataSourceProvider(DataSourceURI dataSource) {
      super(dataSource);
      this.convertors = new HashMap();
      this.c2fMap = new HashMap();
   }

   public void open(RecordSet recordSet) throws RecordSetException {
      super.open(recordSet);

      try {
         ResultSetMetaData md = this.sourceData.getMetaData();
         Record rt = recordSet.getRecordTemplet();
         int col = md.getColumnCount();
         this.c2fIndex = new int[col + 1];
         this.colTypes = new int[col + 1];
         this.colConvertors = new DataConvertor[col + 1];

         for(int i = 1; i <= col; ++i) {
            String colName = md.getColumnName(i);
            this.colTypes[i] = md.getColumnType(i);
            this.colConvertors[i] = this.getColumnConvertor(colName);
            String colMap = this.getColumnMapping(colName);
            this.c2fIndex[i] = rt.indexOf(colMap == null ? colName : colMap);
         }

      } catch (SQLException var8) {
         throw new RecordSetException("获取数据源的元数据出错", var8);
      }
   }

   protected void fillRecord() throws SQLException {
      for(int i = 1; i < this.c2fIndex.length; ++i) {
         if (this.c2fIndex[i] >= 0) {
            this.fillField(i, this.currentRecord.getField(this.c2fIndex[i]));
         }
      }

   }

   private void fillField(int col, Field f) throws SQLException {
      DataConvertor dc = this.colConvertors[col];
      if (dc == null) {
         dc = f;
      }

      String temp1 = null;
      String temp2 = null;
      switch(this.colTypes[col]) {
      case -6:
      case 4:
      case 5:
         ((DataConvertor)dc).setValue(this.sourceData.getInt(col));
         break;
      case -5:
         ((DataConvertor)dc).setValue(this.sourceData.getLong(col));
         break;
      case 1:
      case 12:
         temp1 = this.sourceData.getString(col);
         if (temp1 != null && this.encodingConvert) {
            try {
               temp2 = new String(temp1.getBytes(this.sourceEncoding));
            } catch (UnsupportedEncodingException var8) {
               ARE.getLog(this.getClass()).debug("源数据转码错误！", var8);
            }
         }

         ((DataConvertor)dc).setValue(temp2 == null ? temp1 : temp2);
         break;
      case 2:
      case 3:
      case 6:
      case 7:
      case 8:
         ((DataConvertor)dc).setValue(this.sourceData.getDouble(col));
         break;
      case 16:
         ((DataConvertor)dc).setValue(this.sourceData.getBoolean(col));
         break;
      case 91:
         ((DataConvertor)dc).setValue(this.sourceData.getDate(col));
         break;
      case 92:
         ((DataConvertor)dc).setValue(this.sourceData.getTime(col));
         break;
      case 93:
         ((DataConvertor)dc).setValue(this.sourceData.getTimestamp(col));
         break;
      default:
         temp1 = this.sourceData.getString(col);
         if (temp1 != null && this.encodingConvert) {
            try {
               temp2 = new String(temp1.getBytes(this.sourceEncoding));
            } catch (UnsupportedEncodingException var7) {
               ARE.getLog(this.getClass()).debug("源数据转码错误！", var7);
            }
         }

         ((DataConvertor)dc).setValue(temp2 == null ? temp1 : temp2);
      }

      if (this.sourceData.wasNull()) {
         f.setNull();
      } else {
         if (f != dc) {
            this.setConvertedValue(f, (DataConvertor)dc);
         }

      }
   }

   private void setConvertedValue(Field f, DataConvertor c) {
      switch(f.getType()) {
      case 0:
         f.setValue(c.getString());
         break;
      case 1:
         f.setValue(c.getInt());
         break;
      case 2:
         f.setValue(c.getLong());
         break;
      case 3:
      case 5:
      case 6:
      case 7:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      default:
         f.setValue(c.getString());
         break;
      case 4:
         f.setValue(c.getDouble());
         break;
      case 8:
         f.setValue(c.getBoolean());
         break;
      case 16:
         f.setValue(c.getDate());
      }

   }

   public void setColumnConvertor(String columnName, DataConvertor cov) {
      this.convertors.put(columnName.toUpperCase(), cov);
   }

   public DataConvertor getColumnConvertor(String columnName) {
      return (DataConvertor)this.convertors.get(columnName.toUpperCase());
   }

   public void setColumnMapping(String colName, String fieldName) {
      this.c2fMap.put(colName.toUpperCase(), fieldName);
   }

   public String getColumnMapping(String colName) {
      return (String)this.c2fMap.get(colName.toUpperCase());
   }

   public void setColumnMapping(String c2fmaps) {
      if (c2fmaps != null) {
         String map = c2fmaps.trim();
         if (map.startsWith("{")) {
            String[] ms = StringX.parseArray(map);

            for(int i = 0; i < ms.length; ++i) {
               int p = ms[i].indexOf(44);
               if (p != -1) {
                  String pn = ms[i].substring(0, p).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                  String pv = ms[i].substring(p + 1).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                  if (!pn.equals("") && !pv.equals("")) {
                     this.setColumnMapping(pn, pv);
                  }
               }
            }
         }

      }
   }

   public void setColumnConvertors(String converts) {
      if (converts != null) {
         String[] cvts = StringX.parseArray(converts);
         Pattern p = Pattern.compile("\\{\\s*trim-space\\s*:.*\\}$");

         for(int i = 0; i < cvts.length; ++i) {
            String cv = cvts[i];
            if (cv.length() >= 8) {
               int x = cv.indexOf(44);
               if (x > 1) {
                  String col = cv.substring(0, x).trim();
                  String con = cv.substring(x + 1);
                  DefaultConvertor c = this.createDefaultConvertor(con);
                  if (c != null) {
                     byte flag = 3;
                     Matcher m = p.matcher(col);
                     if (m.find()) {
                        String tf = m.group();
                        String tf_v = StringX.trimAll(tf.substring(tf.indexOf(58) + 1, tf.length() - 1));
                        col = col.substring(0, m.start());
                        if (tf_v.equalsIgnoreCase("start")) {
                           flag = 1;
                        } else if (tf_v.equalsIgnoreCase("end")) {
                           flag = 2;
                        } else if (tf_v.equalsIgnoreCase("none")) {
                           flag = 0;
                        }
                     }

                     c.setTrimSpace(flag);
                     this.setColumnConvertor(col, c);
                  }
               }
            }
         }

      }
   }

   public void close() throws RecordSetException {
      super.close();
      Iterator var1 = this.convertors.values().iterator();

      while(var1.hasNext()) {
         DataConvertor c = (DataConvertor)var1.next();
         c.close();
      }

   }

   protected DefaultConvertor createDefaultConvertor(String expr) {
      DefaultConvertor dfc = null;
      if (expr != null && !expr.matches("\\s*")) {
         String convert = expr.replaceAll("^\\s*", "");
         if (convert.startsWith("datasource")) {
            TabularReader t = null;

            try {
               t = new TabularReader(new DataSourceURI(convert));
               Properties p = t.getProperties(1, 2);
               dfc = new DefaultConvertor(p);
            } catch (URISyntaxException var14) {
               ARE.getLog(this.getClass()).debug("Initialize code table error,URI not avalibale", var14);
            } catch (SQLException var15) {
               ARE.getLog(this.getClass()).debug("Initialize code table error,database error.", var15);
            } finally {
               if (t != null) {
                  t.close();
                  t = null;
               }

            }
         } else if (convert.startsWith("{")) {
            Properties prop = new Properties();
            String dv = null;
            String[] props = StringX.parseArray(convert);

            for(int i = 0; i < props.length; ++i) {
               int p = props[i].indexOf(44);
               if (p != -1) {
                  String pn = props[i].substring(0, p).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                  String pv = props[i].substring(p + 1).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                  if (pn.equals("")) {
                     dv = pv;
                  } else {
                     prop.setProperty(pn, pv);
                  }
               }
            }

            if (prop.size() > 0) {
               if (dv == null) {
                  dfc = new DefaultConvertor(prop);
               } else {
                  dfc = new DefaultConvertor(prop, dv);
               }
            }
         }

         return dfc;
      } else {
         return null;
      }
   }

   public String getSourceEncoding() {
      return this.sourceEncoding;
   }

   public void setSourceEncoding(String encoding) {
      this.sourceEncoding = encoding;
      if (encoding == null) {
         this.encodingConvert = false;
      }

      String testchar = "设置数据源的字符编码格式，格式必须认识。中国wertijseren12309&%(|{}\t\n";

      try {
         String s = new String(testchar.getBytes(encoding));
         if (s.equals(testchar)) {
            this.encodingConvert = false;
         } else {
            this.encodingConvert = true;
         }
      } catch (UnsupportedEncodingException var4) {
         this.encodingConvert = false;
         ARE.getLog(this.getClass()).debug("数据源编码" + encoding + "无效！", var4);
      }

   }
}
