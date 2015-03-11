package po;

import enums.Conference;
import enums.Division;
import enums.Teams;

public class TeamPO {
	Teams name;                            //队名
	String abbreviationOfName;             //队名缩写
	String location;                       //所在地
	Conference conference;                 //赛区
	Division division;                     //分区
	String homeCourt;                      //主场
	String yearOfEstablishment;            //建立时间（年）
	
	public TeamPO(Teams nm, String abbr, String loc, Conference con, Division div, 
			String hc, String year){
		name = nm;
		abbreviationOfName = abbr;
		location = loc;
		conference = con;
		division = div;
		homeCourt = hc;
		yearOfEstablishment = year;
	}
	
	public TeamPO(String[] arr){
		//此方法仅用于读文件
		name = Teams.toEnum(arr[0]);
		abbreviationOfName = arr[1];
		location = arr[2];
		conference = Conference.toEnum(arr[3]);
		division = Division.toEnum(arr[4]);
		homeCourt = arr[5];
		yearOfEstablishment = arr[6];
	}
	
	public void print(){
		System.out.println(name.toString()+'\n'+
				           abbreviationOfName+'\n'+
				           location+'\n'+
				           conference+'\n'+
				           division+'\n'+
				           homeCourt+'\n'+
				           yearOfEstablishment);
	}
}
