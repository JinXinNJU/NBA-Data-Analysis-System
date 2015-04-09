package businessLogic.playersBL;

import helper.TypeTransform;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;

import po.PlayerAdvancedStatsPO;
import po.PlayerBasicStatsPO;
import po.PlayerHotStatsPO;
import po.PlayerPO;
import po.PlayerProgressPO;
import dataService.imageService.ImageService;
import dataService.playersDataService.PlayersDataService_new;
import vo.PlayerAdvancedStatsVO;
import vo.PlayerBasicStatsVO;
import vo.PlayerHotStatsVO;
import vo.PlayerPortraitVO;
import vo.PlayerProgressVO;
import vo.PlayerVO;
import enums.Conference;
import enums.Division;
import enums.Position;
import enums.Teams;
import enums.Terminology;
import exceptions.PlayerNotFound;
import exceptions.TeamNotFound;
import factory.ObjectCreator;
import businessLogicService.playersBLService.PlayersBLService_new;
import businessLogicService.teamsBLService.PlayersInTeamsService;

public class PlayersBL_new implements PlayersBLService_new{
	PlayersDataService_new playerService;
	PlayersInTeamsService teamService;
	ImageService imageService;
	
	public PlayersBL_new(){
		playerService = new ObjectCreator().playersDataService_new();
		teamService = new ObjectCreator().playersInTeamsService();
		imageService = new ObjectCreator().imageService();
	}

	private ArrayList<String> getPlayers(Conference con, Division div, Position pos)
			throws PlayerNotFound {
		ArrayList<String> players = new ArrayList<String>();
		ArrayList<Teams> teams;
		try {
			//先获取该赛区的球队
			teams = teamService.getTeams(con, div);
		} catch (TeamNotFound e) {
			//该地区没有球队
			throw new PlayerNotFound("该地区没有球队，也没有球员");
		}
		for(Teams team: teams){
			//根据球队获取该地区球员
			ArrayList<String> names = playerService.getPlayers(team, pos);
			players.addAll(names);
		}
		
		return players;
	}
	
	@Override
	public ArrayList<PlayerPortraitVO> getPlayersPortrait(Conference con,
			Division div, Position pos) throws PlayerNotFound {
		ArrayList<PlayerPortraitVO> voList = new ArrayList<PlayerPortraitVO>();
		
		//获取球员名单
		ArrayList<String> names = this.getPlayers(con, div, pos);
		for(String name: names){
			PlayerPortraitVO vo = new PlayerPortraitVO(name, imageService.getPlayerPortrait(name));
			voList.add(vo);
		}
		
		return voList;
	}
	
	@Override
	public ArrayList<PlayerPortraitVO> getPlayerPortrait(String name)
			throws PlayerNotFound {
		ArrayList<PlayerPortraitVO> list = new ArrayList<PlayerPortraitVO>();
		PlayerPortraitVO vo = new PlayerPortraitVO(name, imageService.getPlayerPortrait(name));
		list.add(vo);
		return list;
	}

	@Override
	public PlayerVO getPlayerInfo(String name) throws PlayerNotFound {
		PlayerPO po = playerService.getPlayerInfo(name);
		PlayerVO vo = new PlayerVO(po.name(), po.number(), po.position(), po.height_Foot(), 
				po.height_Inch(), po.weight_Pounds(), po.birthday(), po.age(), po.exp(), po.school());
		vo.addImage(imageService.getPlayerPortrait(name), imageService.getPlayerAction(name));
		
		return vo;
	}

	@Override
	public ArrayList<PlayerVO> getAllPlayersInfo() {
		//这个方法最好别用，读图片太慢
		ArrayList<PlayerVO> voList = new ArrayList<PlayerVO>();
		ArrayList<PlayerPO> poList = playerService.getAllPlayersInfo();
		for(PlayerPO po: poList){
			PlayerVO vo = new PlayerVO(po.name(), po.number(), po.position(), po.height_Foot(), 
					po.height_Inch(), po.weight_Pounds(), po.birthday(), po.age(), po.exp(), po.school());
			vo.addImage(imageService.getPlayerPortrait(po.name()), imageService.getPlayerAction(po.name()));
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<PlayerBasicStatsVO> getBasicPlayersStatsTotal(
			Conference con, Division div, Position pos) throws PlayerNotFound {
		ArrayList<PlayerBasicStatsVO> voList = new ArrayList<PlayerBasicStatsVO>();
		ArrayList<String> players = this.getPlayers(con, div, pos);
		for(String name: players){
			try{
				ArrayList<PlayerBasicStatsPO> stats = playerService.getBasicStats(name);
				PlayerBasicStatsVO vo = this.sum_basic(stats);
				voList.add(vo);
			}catch(PlayerNotFound e){
				//没找到比赛数据的不处理
			}
		}
		
		return voList;
	}

	@Override
	public ArrayList<PlayerBasicStatsVO> getBasicPlayersStatsAverage(
			Conference con, Division div, Position pos) throws PlayerNotFound {
		ArrayList<PlayerBasicStatsVO> voList = this.getBasicPlayersStatsTotal(con, div, pos);
		for(PlayerBasicStatsVO vo: voList){
			vo.average();
		}
		return voList;
	}
	
	@Override
	public PlayerBasicStatsVO getBasicPlayerStatsTotal(String name)
			throws PlayerNotFound {
		ArrayList<PlayerBasicStatsPO> stats = playerService.getBasicStats(name);
		PlayerBasicStatsVO vo = this.sum_basic(stats);
		return vo;
	}
	
	@Override
	public PlayerBasicStatsVO getBasicPlayerStatsAverage(String name)
			throws PlayerNotFound {
		ArrayList<PlayerBasicStatsPO> stats = playerService.getBasicStats(name);
		PlayerBasicStatsVO vo = this.sum_basic(stats);
		vo.average();
		return vo;
	}

	@Override
	public ArrayList<PlayerAdvancedStatsVO> getAdvancedPlayersStatsTotal(
			Conference con, Division div, Position pos) throws PlayerNotFound {
		ArrayList<PlayerAdvancedStatsVO> voList = new ArrayList<PlayerAdvancedStatsVO>();
		ArrayList<String> players = this.getPlayers(con, div, pos);
		for(String name: players){
			try{
				ArrayList<PlayerAdvancedStatsPO> stats = playerService.getAdvancedStats(name);
				PlayerAdvancedStatsVO vo = this.average_advanced(stats);
				voList.add(vo);
			}catch(PlayerNotFound e){
				//没找到比赛数据的不处理
			}
		}
		
		return voList;
	}

	@Override
	public ArrayList<PlayerAdvancedStatsVO> getAdvancedPlayersStatsAverage(
			Conference con, Division div, Position pos) throws PlayerNotFound {
		//高级数据没有平均和总和的区别
		return this.getAdvancedPlayersStatsTotal(con, div, pos);
	}
	
	@Override
	public PlayerAdvancedStatsVO getAdvancedPlayerStats(String name)
			throws PlayerNotFound {
		ArrayList<PlayerAdvancedStatsPO> stats = playerService.getAdvancedStats(name);
		PlayerAdvancedStatsVO vo = this.average_advanced(stats);
		return vo;
	}
	
	
	
	
	
	
	
	private PlayerBasicStatsVO sum_basic(ArrayList<PlayerBasicStatsPO> poList) {
		String name = poList.get(0).getName();
		Teams team = poList.get(0).getTeam();
		double games = 0;
		double gamesStarting = 0;
		double minutes = 0;
		double rebounds = 0;
		double assists = 0;
		double fieldGoalPercentage = 0;
		double threePointFieldGoalPercentage = 0;
		double freeThrowPercentage = 0;
		double offensiveRebounds = 0;
		double defensiveRebounds = 0;
		double steals = 0;
		double blocks = 0;
		double turnovers = 0;
		double personalFouls = 0;
		double points = 0;
		double doubleDoubles = 0;
		int fg_num = 0;       //两分数
		int tp_num = 0;       //三分数
		int ft_num = 0;       //罚球数
		for(PlayerBasicStatsPO po: poList){
			games++;
			gamesStarting = po.isGamesStarting() ? gamesStarting + 1 : gamesStarting;
			minutes += po.getMinutes();
			rebounds += po.getRebounds();
			assists += po.getAssists();
			if(po.getFieldGoalsAttempted() > 0.001){
				fieldGoalPercentage += po.getFieldGoalsMade()/po.getFieldGoalsAttempted();
				fg_num++;
			}
			if(po.getThreePointFieldGoalsAttempted() > 0.001){
				threePointFieldGoalPercentage += po.getThreePointFieldGoalsMade()/po.getThreePointFieldGoalsAttempted();
				tp_num++;
			}
			if(po.getFreeThrowsAttempted() > 0.001){
				freeThrowPercentage += po.getFreeThrowsMade()/po.getFreeThrowsAttempted();
				ft_num++;
			}
			offensiveRebounds += po.getOffensiveRebounds();
			defensiveRebounds += po.getDefensiveRebounds();
			steals += po.getSteals();
			blocks += po.getBlocks();
			turnovers += po.getTurnovers();
			personalFouls += po.getPersonalFouls();
			points += po.getPoints();
			if(po.isDoubleDouble()){
				doubleDoubles++;
			}
		}
		
		if(fg_num != 0){
			fieldGoalPercentage /= fg_num;
		}
		if(tp_num != 0){
			threePointFieldGoalPercentage /= tp_num;
		}
		if(ft_num != 0){
			freeThrowPercentage /= ft_num;
		}
		
		PlayerBasicStatsVO vo = new PlayerBasicStatsVO(name, team, games, gamesStarting, 
				TypeTransform.minutes_to_str(minutes), rebounds, assists, fieldGoalPercentage,
				threePointFieldGoalPercentage, freeThrowPercentage, offensiveRebounds, 
				defensiveRebounds, steals, blocks, turnovers, personalFouls, points, doubleDoubles);
		
		return vo;
	}
	
	private PlayerAdvancedStatsVO average_advanced(ArrayList<PlayerAdvancedStatsPO> poList) {
		String name = poList.get(0).getName();
		Teams team = poList.get(0).getTeam();
		double efficiency = 0;
		int n_pe = 0;
		double GmSc = 0;
		int n_gs = 0;
		double trueScore = 0;
		int n_ts = 0;
		double fieldGoal = 0;
		int n_fg = 0;
		double rebounds = 0;
		int n_rp = 0;
		double offensiveRebounds = 0;
		int n_orp = 0;
		double defensiveRebounds = 0;
		int n_drp = 0;
		double assists = 0;
		int n_ap = 0;
		double steals = 0;
		int n_sp = 0;
		double blocks = 0;
		int n_bp = 0;
		double turnovers = 0;
		int n_tp = 0;
		double usage = 0;
		int n_up = 0;
		for(PlayerAdvancedStatsPO po: poList){
			if(po.getPlayerEfficiency() != null){
				efficiency += po.getPlayerEfficiency();
				n_pe++;
			}
			if(po.getGmsc() != null){
				GmSc += po.getGmsc();
				n_gs++;
			}
			if(po.getTrueScorePercent() != null){
				trueScore += po.getTrueScorePercent();
				n_ts++;
			}
			if(po.getFieldGoalsPercent() != null){
				fieldGoal += po.getFieldGoalsPercent();
				n_fg++;
			}
			if(po.getReboundsPercent() != null){
				rebounds += po.getReboundsPercent();
				n_rp++;
			}
			if(po.getOffensiveReboundsPercent() != null){
				offensiveRebounds += po.getOffensiveReboundsPercent();
				n_orp++;
			}
			if(po.getDefensiveReboundsPercent() != null){
				defensiveRebounds += po.getDefensiveReboundsPercent();
				n_drp++;
			}
			if(po.getAssistsPercent() != null){
				assists += po.getAssistsPercent();
				n_ap++;
			}
			if(po.getStealsPercent() != null){
				steals += po.getStealsPercent();
				n_sp++;
			}
			if(po.getBlockPercent() != null){
				blocks += po.getBlockPercent();
				n_bp++;
			}
			if(po.getTurnoversPercent() != null){
				turnovers += po.getTurnoversPercent();
				n_tp++;
			}
			if(po.getUsagePercent() != null){
				usage += po.getUsagePercent();
				n_up++;
			}
		}
		PlayerAdvancedStatsVO vo = new PlayerAdvancedStatsVO(name, team, efficiency/n_pe, 
				GmSc/n_gs, trueScore/n_ts, fieldGoal/n_fg, rebounds/n_rp, 
				offensiveRebounds/n_orp, defensiveRebounds/n_drp, assists/n_ap,
				steals/n_sp, blocks/n_bp, turnovers/n_tp, usage/n_up);
		
		return vo;
	}
	
	
	
	
	//迭代二

	@Override
	public ArrayList<Date> getAvailableDays(String season, String player)
			throws PlayerNotFound {
		ArrayList<Date> result = new ArrayList<Date>();
		ArrayList<String> dates = playerService.getAvailableDays(season, player);
		for(String d: dates){
			result.add(TypeTransform.str_to_date(d));
		}
		
		return result;
	}
	
	@Override
	public ArrayList<PlayerProgressVO> getPlayerProgress(Terminology term, int num) {
		ArrayList<PlayerProgressVO> voList = new ArrayList<PlayerProgressVO>();
		ArrayList<PlayerProgressPO> poList = playerService.getPlayerProgress(term, num);
		for(PlayerProgressPO po: poList){
			PlayerProgressVO vo = new PlayerProgressVO(po.getName(), po.getTeam(), 
					po.getPosition(), po.getStats());
			voList.add(vo);
		}
		
		ArrayList<PlayerProgressVO> result = this.sort_progress(voList, num);
		return result;
	}

	@Override
	public PlayerAdvancedStatsVO getAdvancedStats(String season,
			Date date, String player) throws PlayerNotFound {
		PlayerAdvancedStatsPO po = playerService.getAdvancedStats(season,
				TypeTransform.date_to_str(date), player);
		return new PlayerAdvancedStatsVO(po.getName(), po.getTeam(), po.getPlayerEfficiency(), 
				po.getGmsc(), po.getTrueScorePercent(), po.getFieldGoalsPercent(), 
				po.getRebounds(), po.getOffensiveReboundsPercent(), po.getDefensiveReboundsPercent(), 
				po.getAssistsPercent(), po.getStealsPercent(), po.getBlockPercent(), 
				po.getTurnoversPercent(), po.getUsagePercent());
	}

	@Override
	public PlayerBasicStatsVO getBasicStats(String season,
			Date date, String player) throws PlayerNotFound {
		PlayerBasicStatsPO po= playerService.getBasicStats(season, 
				TypeTransform.date_to_str(date), player);
		double fgp = 0;
		double tpp = 0;
		double ftp = 0;
		if(po.getFieldGoalsAttempted() != 0){
			fgp = po.getFieldGoalsMade()/po.getFieldGoalsAttempted();
		}
		if(po.getThreePointFieldGoalsAttempted() != 0){
			tpp = po.getThreePointFieldGoalsMade()/po.getThreePointFieldGoalsAttempted();
		}
		if(po.getFreeThrowsAttempted() != 0){
			ftp = po.getFreeThrowsMade()/po.getFreeThrowsAttempted();
		}
		return new PlayerBasicStatsVO(po.getName(), po.getTeam(), 0, po.isGamesStarting()==true?1:0, 
				TypeTransform.minutes_to_str(po.getMinutes()), po.getRebounds(), po.getAssists(), fgp, tpp, ftp, 
				po.getOffensiveRebounds(), po.getDefensiveRebounds(), 
				po.getSteals(), po.getBlocks(), po.getTurnovers(), po.getPersonalFouls(), 
				po.getPoints(), po.isDoubleDouble()==true?1:0);		
	}

	@Override
	public ArrayList<PlayerHotStatsVO> getHotPlayersByDay(String season,
			Date date, Terminology term, int num) {
		ArrayList<PlayerHotStatsPO> poList = playerService.getPlayerHotStats(season, 
				TypeTransform.date_to_str(date), term);
		ArrayList<PlayerHotStatsVO> voList = new ArrayList<PlayerHotStatsVO>();
		for(PlayerHotStatsPO po: poList){
			double stats = 0;
			int n = po.getStats().size();
			for(Double s: po.getStats()){
				stats += s;
			}
			PlayerHotStatsVO vo = new PlayerHotStatsVO(po.getName(), po.getTeam(), 
					po.getPosition(), stats/n);
			voList.add(vo);
		}
		ArrayList<PlayerHotStatsVO> result = this.sort_hotplayer(voList, num);
		return result;
	}

	@Override
	public ArrayList<PlayerHotStatsVO> getHotPlayersBySeason(
			String season, Terminology term, int num) {
		ArrayList<PlayerHotStatsPO> poList = playerService.getPlayerHotStats(season, term);
		ArrayList<PlayerHotStatsVO> voList = new ArrayList<PlayerHotStatsVO>();
		for(PlayerHotStatsPO po: poList){
			double stats = 0;
			int n = po.getStats().size();
			for(Double s: po.getStats()){
				stats += s;
			}
			PlayerHotStatsVO vo = new PlayerHotStatsVO(po.getName(), po.getTeam(), 
					po.getPosition(), stats/n);
			voList.add(vo);
		}
		ArrayList<PlayerHotStatsVO> result = this.sort_hotplayer(voList, num);
		return result;
	}
	
	
	private ArrayList<PlayerHotStatsVO> sort_hotplayer(ArrayList<PlayerHotStatsVO> list, int num){
		ArrayList<PlayerHotStatsVO> result = new ArrayList<PlayerHotStatsVO>();
		Collections.sort(list, new ComparePlayerHotStats());
		for(int i=list.size()-1; i>=list.size()-num; i--){
			PlayerHotStatsVO vo = list.get(i);
			result.add(vo);
		}
		
		return result;
	}
	
	class ComparePlayerHotStats implements Comparator<PlayerHotStatsVO>{

		@Override
		public int compare(PlayerHotStatsVO o1, PlayerHotStatsVO o2) {
			if(o1.getStats() > o2.getStats()){
				return 1;
			}else if(o1.getStats() < o2.getStats()){
				return -1;
			}else{
				return 0;
			}
		}
		
	}
	
	private ArrayList<PlayerProgressVO> sort_progress(ArrayList<PlayerProgressVO> list, int num){
		Collections.sort(list, new ComparePlayerProgress());
		ArrayList<PlayerProgressVO> voList = new ArrayList<PlayerProgressVO>();
		for(int i=list.size()-1; i>=list.size()-num; i--){
			voList.add(list.get(i));
		}
		
		return voList;
	}
	
	class ComparePlayerProgress implements Comparator<PlayerProgressVO>{

		@Override
		public int compare(PlayerProgressVO o1, PlayerProgressVO o2) {
			if(o1.getImprovement() > o2.getImprovement()){
				return 1;
			}else if(o1.getImprovement() < o2.getImprovement()){
				return -1;
			}else{
				return 0;
			}
		}
		
	}

}
