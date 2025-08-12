package com.amarsoft.app.pbc.ecr.data;

import java.io.File;

import com.amarsoft.are.ARE;
import com.amarsoft.are.log.Log;
import com.amarsoft.task.TaskConstants;
import com.amarsoft.task.units.dpx.FileDataSourceCheck;

/**
 * 
 * Description 文件格式校验
 * ClassName FileCheckUnit
 * @author lzhang19 
 * Date 2018/8/27
 */
public class FileCheckUnit extends FileDataSourceCheck {
	private String dataSources;//数据源
	protected Log logger = ARE.getLog();
	private String extraPath;

	public FileCheckUnit() {
		this.dataSources = null;
		this.extraPath = null;
	}
	
	@SuppressWarnings({ "finally", "unused" })
	@Override
	public int execute(){
		try{
			this.extraPath = getExtraPath();
			String extraDirectory = ARE.getProperty("ECR_HOME")+"/"+this.extraPath;
			if(extraDirectory == null){
				logger.error("文件路径不存在！");
				return TaskConstants.ES_FAILED;
			}
			//获取task配置文件中数据源
			this.dataSources = getTask().getCurrentUnit().getProperty("unit.dataSources");
			if(dataSources == null){
				dataSources ="";
			}
			int resultValue = getDataSources(extraDirectory,dataSources);
			if(resultValue != TaskConstants.ES_FAILED){
				setDataSources(dataSources);
			}
			return super.execute();
		}catch (Exception e) {	
			logger.error("文件检查失败！", e);
			return TaskConstants.ES_FAILED;
		}
	}
	

	/**
	 * 获取数据源
	 */
	private int getDataSources(String extraDirectory,String dataSources){
		//获取本地文件名
		File files = new File(extraDirectory);
		if(files.isDirectory() == false){
			logger.error("文件路径不存在！");
			return TaskConstants.ES_FAILED; 
		}
		String[] file = files.list();
		//遍历文件名，设置数据源
		for(int i=0;i<file.length;i++){
			dataSources= dataSources +"{datasource:ndb:ecr_data:select * from "+file[i].substring(0,file[i].length()-13)+":"+extraPath+"/"+file[i]+":\\|}";			
		}
		this.dataSources = dataSources;
		return TaskConstants.ES_RUNNING;
	}


	public String getExtraPath() {
		return extraPath;
	}


	public void setExtraPath(String extraPath) {
		this.extraPath = extraPath;
	}
	
	
}
