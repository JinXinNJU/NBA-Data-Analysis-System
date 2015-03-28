package businessLogic.teamsBL;

import java.util.ArrayList;

import po.TeamDefensiveFoulsStatsPO;
import po.TeamOffensiveStatsPO;
import po.TeamPO;
import po.TeamRatioGeneralStatsPO;
import data.imageData.ImageData;
import dataService.imageService.ImageService;
import dataService.teamsDataService.TeamsDataService_new;
import vo.TeamDefensiveFoulsVO;
import vo.TeamOffensiveStatsVO;
import vo.TeamRatioGeneralVO;
import vo.TeamVO;
import enums.Conference;
import enums.Division;
import enums.Teams;
import exceptions.TeamNotFound;
import factory.ObjectCreator;
import businessLogicService.teamsBLService.PlayersInTeamsService;
import businessLogicService.teamsBLService.TeamsBLService_new;

public class TeamsBL_new implements TeamsBLService_new, PlayersInTeamsService {
	TeamsDataService_new teamService;
	ImageService imageService;
	
	public TeamsBL_new(){
		teamService = new ObjectCreator().teamsDataService_new();
		imageService = new ImageData();
	}

	@Override
	public TeamVO getTeamInfo(Teams team) throws TeamNotFound {
		TeamPO po = teamService.getTeam(team);
		TeamVO vo = new TeamVO(po.name(), team.toAbbr(), po.location(), po.conference(),
				po.division(), po.homeCourt(), po.yearOfEstablishment());
		vo.setImage(imageService.getTeamImage(team));
		return vo;
	}

	@Override
	public ArrayList<TeamVO> getAllTeamsInfo() {
		ArrayList<TeamVO> voList = new ArrayList<TeamVO>();
		ArrayList<TeamPO> poList = teamService.getAllTeams();
		for(TeamPO po: poList){
			TeamVO vo = new TeamVO(po.name(), po.abbreviationOfName(), po.location(), po.conference(),
					po.division(), po.homeCourt(), po.yearOfEstablishment());
			vo.setImage(imageService.getTeamImage(po.name()));
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamVO> getTeamsInfo(Conference conference,
			Division division) throws TeamNotFound {
		ArrayList<TeamVO> voList = new ArrayList<TeamVO>();
		ArrayList<TeamPO> poList = teamService.getTeams(conference, division);
		for(TeamPO po: poList){
			TeamVO vo = new TeamVO(po.name(), po.abbreviationOfName(), po.location(), po.conference(),
					po.division(), po.homeCourt(), po.yearOfEstablishment());
			vo.setImage(imageService.getTeamImage(po.name()));
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamOffensiveStatsVO> getTeamOffensiveStatsTotal(
			Conference conference, Division division) throws TeamNotFound {
		ArrayList<Teams> teamNames = teamService.getTeamNames(conference, division);
		ArrayList<TeamOffensiveStatsVO> voList = new ArrayList<TeamOffensiveStatsVO>();
		for(Teams team: teamNames){
			ArrayList<TeamOffensiveStatsPO> poList = teamService.getOffensiveStats(team);
			TeamOffensiveStatsVO vo = this.sum_offensive(poList);
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamRatioGeneralVO> getTeamRatioGeneralStatsTotal(
			Conference conference, Division division) throws TeamNotFound {
		ArrayList<Teams> teamNames = teamService.getTeamNames(conference, division);
		ArrayList<TeamRatioGeneralVO> voList = new ArrayList<TeamRatioGeneralVO>();
		for(Teams team: teamNames){
			ArrayList<TeamRatioGeneralStatsPO> poList = teamService.getRatioGeneralStats(team);
			TeamRatioGeneralVO vo = this.sum_ratio(poList);
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamDefensiveFoulsVO> getTeamDefensiveFoulsStatsTotal(
			Conference conference, Division division) throws TeamNotFound {
		ArrayList<Teams> teamNames = teamService.getTeamNames(conference, division);
		ArrayList<TeamDefensiveFoulsVO> voList = new ArrayList<TeamDefensiveFoulsVO>();
		for(Teams team: teamNames){
			ArrayList<TeamDefensiveFoulsStatsPO> poList = teamService.getDefensiveFoulsStats(team);
			TeamDefensiveFoulsVO vo = this.sum_defensive(poList);
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamOffensiveStatsVO> getTeamOffensiveStatsAverage(
			Conference conference, Division division) throws TeamNotFound {
		ArrayList<TeamOffensiveStatsVO> voList = this.getTeamOffensiveStatsTotal(conference, division);
		for(TeamOffensiveStatsVO vo: voList){
			vo.average();
		}
		
		return voList;
	}

	@Override
	public ArrayList<TeamRatioGeneralVO> getTeamRatioGeneralStatsAverage(
			Conference conference, Division division) throws TeamNotFound {
		return this.getTeamRatioGeneralStatsTotal(conference, division);
	}

	@Override
	public ArrayList<TeamDefensiveFoulsVO> getTeamDefensiveFoulsStatsAverage(
			Conference conference, Division division) throws TeamNotFound {
		ArrayList<TeamDefensiveFoulsVO> voList = this.getTeamDefensiveFoulsStatsTotal(conference, division);
		for(TeamDefensiveFoulsVO vo: voList){
			vo.average();
		}
		
		return voList;
	}

	@Override
	public ArrayList<Teams> getTeams(Conference con, Division div)
			throws TeamNotFound {
		return teamService.getTeamNames(con, div);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private TeamOffensiveStatsVO sum_offensive(ArrayList<TeamOffensiveStatsPO> poList){
		Teams team = poList.get(0).getTeam();
		int games = poList.size();
		double points = 0;
		double fieldGoalsMade = 0;
		double fieldGoalsAttempted = 0;
		double freeThrowsMade = 0;
		double freeThrowsAttempted = 0;
		double threePointFieldGoalsMade = 0;
		double threePointFieldGoalsAttempted = 0;
		double assists = 0;
		for(TeamOffensiveStatsPO po: poList){
			points += po.getPoints();
			fieldGoalsMade += po.getFieldGoalsMade();
			fieldGoalsAttempted += po.getFieldGoalsAttempted();
			freeThrowsMade += po.getFreeThrowsMade();
			freeThrowsAttempted += po.getFreeThrowsAttempted();
			threePointFieldGoalsMade += po.getThreePointFieldGoalsMade();
			threePointFieldGoalsAttempted += po.getThreePointFieldGoalsAttempted();
			assists += po.getAssists();
		}
		
		TeamOffensiveStatsVO vo = new TeamOffensiveStatsVO(team, games, points, fieldGoalsMade, 
				fieldGoalsAttempted, freeThrowsMade, freeThrowsAttempted, threePointFieldGoalsMade, 
				threePointFieldGoalsAttempted, assists);
		return vo;
	}
	
	private TeamDefensiveFoulsVO sum_defensive(ArrayList<TeamDefensiveFoulsStatsPO> poList){
		Teams team = poList.get(0).getTeam();
		int games = poList.get(0).getGames();
		double offensiveRebounds = 0;
		double defensiveRebounds = 0;
		double rebounds = 0;
		double steals = 0;
		double blocks = 0;
		double turnovers = 0;
		double fouls = 0;
		for(TeamDefensiveFoulsStatsPO po: poList){
			offensiveRebounds += po.getOffensiveRebounds();
			defensiveRebounds += po.getDefensiveRebounds();
			rebounds += po.getRebounds();
			steals += po.getSteals();
			blocks += po.getBlocks();
			turnovers += po.getTurnovers();
			fouls += po.getFouls();
		}
		
		TeamDefensiveFoulsVO vo = new TeamDefensiveFoulsVO(team, games, offensiveRebounds, 
				defensiveRebounds, rebounds, steals, blocks, turnovers, fouls);
		return vo;
		
	}
	
	private TeamRatioGeneralVO sum_ratio(ArrayList<TeamRatioGeneralStatsPO> poList){
		Teams team = poList.get(0).getTeam();
		int games = poList.get(0).getGames();
		int fg_num = 0;
		int ft_num = 0;
		int tp_num = 0;
		double fieldGoalsPercentage = 0;
		double freeThrowsPercentage = 0;
		double threePointFieldGoalsPercentage = 0;
		double winningRating = 0;
		double offensiveRounds = 0;
		double offensiveEfficiency = 0;
		double defensiveEfficiency = 0;
		double offensiveReboundsEfficiency = 0;
		double defensiveReboundsEfficiency = 0;
		double stealsEfficiency = 0;
		double assistsEfficiency = 0;
		for(TeamRatioGeneralStatsPO po: poList){
			fieldGoalsPercentage += po.getFieldGoalsPercentage();
			freeThrowsPercentage += po.getFreeThrowsPercentage();
			threePointFieldGoalsPercentage += po.getThreePointFieldGoalsPercentage();
			winningRating += po.getWinningRating();
		    offensiveRounds += po.getOffensiveRounds();
			offensiveEfficiency += po.getOffensiveEfficiency();
			defensiveEfficiency += po.getDefensiveEfficiency();
			offensiveReboundsEfficiency += po.getOffensiveReboundsEfficiency();
			defensiveReboundsEfficiency += po.getDefensiveReboundsEfficiency();
			stealsEfficiency += po.getStealsEfficiency();
			assistsEfficiency += po.getAssistsEfficiency();
			if(po.getFieldGoalsAttempted() != 0){
				fg_num++;
			}
			if(po.getFreeThrowsAttempted() != 0){
				ft_num++;
			}
			if(po.getThreePointFieldGoalsAttempted() != 0){
				tp_num++;
			}
		}
		
		TeamRatioGeneralVO vo = new TeamRatioGeneralVO(team, games, fieldGoalsPercentage/fg_num, 
				freeThrowsPercentage/ft_num, threePointFieldGoalsPercentage/tp_num, 
				winningRating/games, offensiveRounds, offensiveEfficiency/games, 
				defensiveEfficiency/games, offensiveReboundsEfficiency/games, 
				defensiveReboundsEfficiency/games, stealsEfficiency/games, 
				assistsEfficiency/games);
		return vo;
	}

}
