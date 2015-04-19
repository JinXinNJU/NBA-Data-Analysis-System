package sorter.teams.normalinfo;

import java.util.Comparator;

import vo.TeamNormalInfo;

public class SortByDefendRebound implements Comparator<TeamNormalInfo> {

	@Override
	public int compare(TeamNormalInfo o1, TeamNormalInfo o2) {
		if(o1.getDefendRebound()>o2.getDefendRebound()){
			return 1;
		}else if(o1.getDefendRebound()==o2.getDefendRebound()) {
			return 0;
		}else{
			return -1;
		}
	}

}
