package gui.hot;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.PlayerVO;
import businessLogicService.playersBLService.PlayersBLService_new;
import enums.Position;
import enums.Teams;
import enums.Terminology;
import exceptions.PlayerNotFound;
import factory.ObjectCreator;
import gui.match.player.PlayerMatchStatsPanel;
import gui.player.PortraitPanel;
import gui.player.detail.PlayerDetailDialog;
import gui.player.detail.PlayerSeasonStatsPanel;
import gui.player.detail.PlayerStatsPanel;

public class PlayerCompareItemPanel extends JPanel {
	private static final long serialVersionUID = -8038316105498505686L;
	private static PlayersBLService_new playerbl = new ObjectCreator().playersBLService();
	private PlayerStatsPanel pnl_stats;

	private void construct(int rank,Terminology term,String name,Teams team,Position position) {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JLabel lbl_rank = new JLabel(String.valueOf(rank));
		lbl_rank.setFont(new Font(lbl_rank.getFont().getName(),Font.BOLD,20));
		lbl_rank.setAlignmentX(0.5f);
		add(lbl_rank);
		
		try {
			PlayerVO vo = playerbl.getPlayerInfo(name);
			add(new PortraitPanel(vo.getPortrait(),vo.getName(),0.4));
		} catch (PlayerNotFound e) {
			add(new PortraitPanel(name,0.4));
		}
		
		JLabel lbl_team = new JLabel(team.toString()+"——"+position.toString());
		lbl_team.setAlignmentX(0.5f);
		add(lbl_team);
		
		pnl_stats.setAlignmentX(0.5f);
		add(pnl_stats);
		
		JButton btn_rank = new JButton("查看球员详情");
		btn_rank.setAlignmentX(0.5f);
		add(btn_rank);
		btn_rank.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PlayerDetailDialog(name).setVisible(true);
			}
		});
		
		add(Box.createVerticalGlue());
	}
	public PlayerCompareItemPanel(int rank,String season,Date date,Terminology term,String name,Teams team,Position position) {
		pnl_stats = new PlayerMatchStatsPanel(playerbl, name, season,date,term);
		construct(rank,term,name,team,position);
	}
	public PlayerCompareItemPanel(int rank,String season,Terminology term,String name,Teams team,Position position){
		pnl_stats = new PlayerSeasonStatsPanel(playerbl, name,term);
		construct(rank,term,name,team,position);
	}

}