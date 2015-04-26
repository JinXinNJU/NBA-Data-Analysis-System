package gui.team;

import enums.Teams;
import exceptions.TeamNotFound;
import gui.MainFrame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class TeamLabel extends JLabel {
	private static final long serialVersionUID = -4521190206450643161L;
	
	public TeamLabel(Teams team){
		super("<html><u>"+team.toString()+"</u></html>");
		
		setForeground(Color.BLUE);
		
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				try {
					MainFrame.showDialog(new TeamDialog(team));
				} catch (TeamNotFound e1) {
					e1.printStackTrace();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
	}
}
