package sorter.teams.normalinfo;

import java.util.Comparator;

import vo.TeamNormalInfo;

public class SortByAssist implements Comparator<TeamNormalInfo> {

	@Override
	public int compare(TeamNormalInfo o1, TeamNormalInfo o2) {
		if(o1.getAssist()>o2.getAssist()){
			return 1;
		}else if(o1.getAssist()==o2.getAssist()) {
			return 0;
		}else{
			return -1;
		}
	}

}
