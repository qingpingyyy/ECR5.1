package com.amarsoft.app.pbc.ecr;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import com.amarsoft.app.pbc.ecr.common.Tools;
import com.amarsoft.are.ARE;
import com.amarsoft.are.AREConfigurationFinder;
import com.amarsoft.are.io.FileTool;
import com.amarsoft.are.util.CommandLineArgument;
import com.amarsoft.are.util.conf.ConfigurationSource;
import com.amarsoft.task.TaskRunner;
/**
 * describe��AmarECR ��Ϊ����������࣬�����������ڣ����Թ��������ļ�service�����з����������Ҳ���Խ��з���Ĺرա�
 * @author�� dnwang
 * modified��2018/08/22
 * purpose�� 
 */
public class AmarECR {
	
	public static final String TASK_FILE = "TaskFile";
	public static final String DEFAULT_ARE_CONFIG = "etc/ecr_are.xml";
	public static final String DEFAULT_LOGBACK_CONFIG = "etc/logback.xml";
	
	private static String bizDate = "";// ��ʾ����-����Ϊ�ϸ��µ���ĩ
	private static String appDate = "";// ��ʾ��������
	private static String fileDate = "";// ��ʾ�����ļ�����
	private static String startDate = "";// ��ʾ�ϸ��µĿ�ʼ����
	private static String endDate = "";// ���ɱ���ʱ����-��������ɵ�������ڣ�����endDate����Ϊ�ϸ��µ���ĩ
	private static String thisMonth = "";// ��ʾ����
	private static String lastMonth = "";// ��ʾ�ϸ���
	private static String nextMonth = "";// ��ʾ�¸���
	private static String businessOccurDate = "";// ��ʾҵ��������
	private static String currMonth = ""; // ���£��������ݺ˶�
	private static String currDate = "";// ϵͳ��ǰʱ��yyyyMMdd
	private static String lastStartDate = "";// ���³�
	private static String nextEndDate = "";// ���µ�

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// initialize are,first arg is are config,second is task type
		//����������� task ����Ҫ����
		CommandLineArgument arg = new CommandLineArgument(args);
		String are = arg.getArgument("are",DEFAULT_ARE_CONFIG);

		if(are==null ||"".equals(are)){
			ARE.getLog().error("����ִ�У�����Ҫָ��AREλ�ã���ȷ�������еĲ���...");
			return;
		}
		
		changeLogback(DEFAULT_LOGBACK_CONFIG);
		
		ARE.init(are);	
		
		bizDate = ARE.getProperty("bizDate");
		appDate = ARE.getProperty("appDate");

		businessOccurDate = setInitDate(bizDate);

		setAREProperties();
		
		currDate = Tools.getCurrentDay("0");
		ARE.setProperty("currDate", currDate.replaceAll("/","-"));
		ARE.setProperty("bizDate", businessOccurDate.replaceAll("/","-"));
		ARE.setProperty("appDate", appDate.replaceAll("/","-"));
		ARE.setProperty("fileDate", fileDate.replaceAll("/",""));
		ARE.setProperty("startDate", startDate.replaceAll("/","-"));
		ARE.setProperty("endDate", endDate.replaceAll("/","-"));
		ARE.setProperty("thisMonth", thisMonth.replaceAll("/","-"));
		ARE.setProperty("lastMonth", lastMonth.replaceAll("/","-"));
		ARE.setProperty("nextMonth", nextMonth.replaceAll("/","-"));
		ARE.setProperty("currMonth", currMonth.replaceAll("/","-"));
		ARE.setProperty("lastStartDate", lastStartDate.replaceAll("/","-"));
		ARE.setProperty("nextEndDate", nextEndDate.replaceAll("/","-"));
	    
		String taskName = ARE.getProperty(arg.getArgument("task","prepareAccount"));
		if (taskName == null) {
			ARE.getLog().info("û�ж������������ļ���");
			System.exit(-1);
		}
		
		
		boolean gui= arg.getArgument("gui",true);
		//��ʼ��������
		if(gui){
			ARE.getLog().info("����ǰ���ݵ�ҵ��������Ϊ:" + businessOccurDate);
			TaskRunner.run(taskName);
		}else{
			ARE.getLog().info("����ǰ���ݵ�ҵ��������Ϊ:" + businessOccurDate);
			TaskRunner.run(taskName);
		}	
		
	}
	private static String setInitDate(String bizDate) {
		String sDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (bizDate == null)
			bizDate = "AUTOSELECT";
		if (bizDate.equalsIgnoreCase("AUTOSELECT")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			if (cal.get(Calendar.HOUR_OF_DAY) < 22) // 22��֮ǰȡ���죬22��֮��ȡ����
				sDate = Tools.getLastDay("1");
			else
				sDate = Tools.getCurrentDay("1");
		} else { // ���������
			try {
				sDate = bizDate;
				sdf.parse(bizDate);
				endDate = Tools.getEndDateOfMonth(sDate);
			} catch (ParseException ex) {
				ARE.getLog().error(ex);
			}
		}
		return sDate;
	}

	public static void setAREProperties() {
		bizDate = businessOccurDate;
		fileDate = bizDate;
		thisMonth = businessOccurDate.substring(0, 7);
		startDate = Tools.getFristDateOfMonth(bizDate);
		endDate = Tools.getEndDateOfMonth(bizDate);
		lastMonth = Tools.diffMonth(businessOccurDate, -1).substring(0, 7);
		nextMonth = Tools.diffMonth(businessOccurDate, 1).substring(0, 7);
		currMonth = Tools.handleDate(businessOccurDate, "3");
		lastStartDate = lastMonth + "/01";
		nextEndDate = Tools.getEndDateOfMonth(nextMonth+ "/01");
	}
	
	public static void changeLogback(String logbackPath){
		ConfigurationSource  cfs_log =AREConfigurationFinder.findConfigurationSource(logbackPath);

		if (cfs_log != null) {
			ILoggerFactory  logFactory= LoggerFactory.getILoggerFactory();
			if (logFactory instanceof ch.qos.logback.classic.LoggerContext ) {
				try {
					ch.qos.logback.classic.LoggerContext  loggerContext = (ch.qos.logback.classic.LoggerContext) logFactory;
					loggerContext.reset();
					ch.qos.logback.classic.joran.JoranConfigurator joranConfigurator = new ch.qos.logback.classic.joran.JoranConfigurator();
					joranConfigurator.setContext(loggerContext);
					joranConfigurator.doConfigure(cfs_log.getInputStream());
				} catch (ch.qos.logback.core.joran.spi.JoranException e) {
					ARE.getLog().error(e);
				} catch (IOException e) {
					ARE.getLog().error(e);
				}
			}
		}
	}
}

