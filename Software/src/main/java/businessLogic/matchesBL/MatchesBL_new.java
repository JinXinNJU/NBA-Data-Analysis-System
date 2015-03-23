package businessLogic.matchesBL;

import java.util.ArrayList;

import po.MatchPO_new;
import dataService.matchesDataService.MatchesDataService_new;
import vo.MatchVO;
import enums.Teams;
import exceptions.MatchNotFound;
import exceptions.TeamNotFound;
import factory.ObjectCreator;
import businessLogicService.matchesBLService.MatchesBLService;

public class MatchesBL_new implements MatchesBLService {
	
	MatchesDataService_new matchService;
	
	public MatchesBL_new(){
		matchService = new ObjectCreator().matchesDataService_new();
	}

	@Override
	public MatchVO getMatchVO(String season, String date, Teams team1,
			Teams team2) throws TeamNotFound, MatchNotFound {
		MatchPO_new po = matchService.getMatch(season, date, team1, team2);
		MatchVO vo = new MatchVO(po.getSeason(), po.getDate(), po.getHomeTeam(), po.getGuestTeam(),
				po.getScore(), po.getScore1(), po.getScore2(), po.getScore3(),
				po.getScore4(), po.getScoreExtra());
		return vo;
	}

	@Override
	public ArrayList<MatchVO> getMatchesVO(String season, String date,
			Teams team1, Teams team2) throws TeamNotFound, MatchNotFound {
		ArrayList<MatchVO> voList = new ArrayList<MatchVO>();
		ArrayList<MatchPO_new> poList = matchService.getMatches(season, date, team1, team2);
		for(MatchPO_new po: poList){
			MatchVO vo = new MatchVO(po.getSeason(), po.getDate(), po.getHomeTeam(), po.getGuestTeam(),
					po.getScore(), po.getScore1(), po.getScore2(), po.getScore3(),
					po.getScore4(), po.getScoreExtra());
			voList.add(vo);
		}
		
		return voList;
	}

}
