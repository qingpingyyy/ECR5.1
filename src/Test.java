import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		BigDecimal b ;
		BigDecimal b1 ;
		b =new BigDecimal("0.01");
		System.out.println(b);
		b =new BigDecimal("0.012");
		System.out.println(b);
		b1 =new BigDecimal("0.0122");
		System.out.println(b.add(b1));
		String sReportType ="030";
		String sReportDate="2020/09";
		String sReportType1 ="020";
		String s1=sReportDate.substring(0,4);
		System.out.println("====>"+s1);
		
		
		
		
		
		 if("030".equals(sReportType)){
	        	if(sReportDate.compareTo(s1+"/06") <=0){
	        		System.out.println("�ϰ���");
	        	}else{
	        		System.out.println("�°���");
	        	}
	        }
	        //ReportPeriod	020	����
	        if("020".equals(sReportType1)){
	        	if(sReportDate.compareTo(s1+"/03") <=0){
	        		System.out.println("��һ����");//��һ����
	        	}else if (sReportDate.compareTo(s1+"/06") <=0){
	        		System.out.println("�ڶ�����");//��һ����
	        	}else if (sReportDate.compareTo(s1+"/09") <=0){
	        		System.out.println("��������");//��һ����
	        	}else{
	        		System.out.println("���ļ���");//��һ����
	        	}
	        }

	}

}
