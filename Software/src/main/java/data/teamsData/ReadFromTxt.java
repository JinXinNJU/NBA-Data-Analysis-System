package data.teamsData;

import java.util.ArrayList;
import java.io.*;
import po.TeamPO;

public class ReadFromTxt implements ReadTeams {
	String path = "F:/学习/软工3/迭代一数据/teams/teams";

	@Override
	public ArrayList<TeamPO> readAllTeams() {
		ArrayList<TeamPO> teamList = null;
		
		File file = new File(path);
		if(file.exists()){
			try{
				teamList = new ArrayList<TeamPO>();
				BufferedReader br = new BufferedReader(new InputStreamReader(  
		                new FileInputStream(file), "utf-8")); 
				br.readLine();
				String line;
				while((line = br.readLine()) != null){
					if(line.charAt(1) == '═') break;
					String[] arr = line.split("│");
					for(int i=0; i<arr.length; i++){
						arr[i] = arr[i].trim();
					}
					arr[0] = arr[0].substring(1, arr[0].length());
					int lastIndex = arr.length-1;
					arr[lastIndex] = arr[lastIndex].substring(0, arr[lastIndex].length()-3);
					TeamPO team = new TeamPO(arr);
					teamList.add(team);
				}
				
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return teamList;
	}
	
	public static void main(String[] args){
		ReadFromTxt reader = new ReadFromTxt();
		ArrayList<TeamPO> teamList = reader.readAllTeams();
		int num = 0;
		for(int i=0; i<teamList.size(); i++){
			teamList.get(i).print();
			System.out.println();
			num++;
		}
		System.out.println(num);
	}

}
