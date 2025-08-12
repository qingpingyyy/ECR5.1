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
 * describe：AmarECR 作为服务的启动类，整个程序的入口，可以构建配置文件service，进行服务的启动，也可以进行服务的关闭。
 * @author： dnwang
 * modified：2018/08/22
 * purpose： 
 */
public class AmarECR {
	
	public static final String TASK_FILE = "TaskFile";
	public static final String DEFAULT_ARE_CONFIG = "etc/ecr_are.xml";
	public static final String DEFAULT_LOGBACK_CONFIG = "etc/logback.xml";
	
	private static String bizDate = "";// 表示昨天-设置为上个月的月末
	private static String appDate = "";// 表示上线日期
	private static String fileDate = "";// 表示上游文件日期
	private static String startDate = "";// 表示上个月的开始日期
	private static String endDate = "";// 生成报表时有用-报表的生成的最后日期：对于endDate设置为上个月的月末
	private static String thisMonth = "";// 表示当月
	private static String lastMonth = "";// 表示上个月
	private static String nextMonth = "";// 表示下个月
	private static String businessOccurDate = "";// 表示业务发生日期
	private static String currMonth = ""; // 当月，用于数据核对
	private static String currDate = "";// 系统当前时间yyyyMMdd
	private static String lastStartDate = "";// 上月初
	private static String nextEndDate = "";// 下月底

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// initialize are,first arg is are config,second is task type
		//参数必须带上 task 否者要报错
		CommandLineArgument arg = new CommandLineArgument(args);
		String are = arg.getArgument("are",DEFAULT_ARE_CONFIG);

		if(are==null ||"".equals(are)){
			ARE.getLog().error("程序执行，必须要指定ARE位置，请确定命令中的参数...");
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
			ARE.getLog().info("没有定义任务配置文件！");
			System.exit(-1);
		}
		
		
		boolean gui= arg.getArgument("gui",true);
		//开始调度任务
		if(gui){
			ARE.getLog().info("处理当前数据的业务发生日期为:" + businessOccurDate);
			TaskRunner.run(taskName);
		}else{
			ARE.getLog().info("处理当前数据的业务发生日期为:" + businessOccurDate);
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
			if (cal.get(Calendar.HOUR_OF_DAY) < 22) // 22点之前取昨天，22点之后取当天
				sDate = Tools.getLastDay("1");
			else
				sDate = Tools.getCurrentDay("1");
		} else { // 具体的日期
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

