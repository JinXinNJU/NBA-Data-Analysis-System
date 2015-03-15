package gui.player;

import exceptions.PlayerNotFound;
import gui.SelfAdjustPanel;
import gui.util.ReturnButton;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;








import vo.PlayerVO;
import businessLogic.playersBL.PlayersBL;

public class PlayerPanel extends SelfAdjustPanel {

	private static final long serialVersionUID = 9090035509234357424L;

	public PlayerPanel() {
		GridBagLayout gbl_pnl_menu = new GridBagLayout();
		gbl_pnl_menu.columnWidths = new int[]{pWidth/10, pWidth/(10/8), pWidth/10};
		gbl_pnl_menu.rowHeights = new int[]{pHeight/10, pHeight/5, pHeight/10, pHeight/2, pHeight/10};
		gbl_pnl_menu.columnWeights = new double[]{1,1,1};
		gbl_pnl_menu.rowWeights = new double[]{1,0,1,0,1};
		setLayout(gbl_pnl_menu);
		
		/*
		try {
			ArrayList<PlayerVO> list = new PlayersBL().getAllPlayersInfo();
		} catch (PlayerNotFound e) {
			//TODO
		}
		
		JScrollPane pane_list = new JScrollPane();
		pane_list.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JTable tbl_list = new JTable(new PlayerTableModel_Simple(null));// TODO
		tbl_list.setFillsViewportHeight(true);
		tbl_list.setRowSorter(new TableRowSorter<PlayerTableModel_Simple>());
		tbl_list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_list.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && me.getButton() == MouseEvent.BUTTON1){
					
				}
			}
		});
		pane_list.add(tbl_list);
		GridBagConstraints gbc_pane_list = new GridBagConstraints();
		gbc_pane_list.gridx = 1;
		gbc_pane_list.gridy = 3;
		gbc_pane_list.fill = GridBagConstraints.BOTH;
		add(pane_list, gbc_pane_list);
		*/
		
		
		PortraitPanel[] plist = new PortraitPanel[10];
		Arrays.fill(plist, new PortraitPanel());
		JList<PortraitPanel> list = new JList<PortraitPanel>(plist);
		list.setCellRenderer(new ListCellRenderer<PortraitPanel>(){
			@Override
			public Component getListCellRendererComponent(
					JList<? extends PortraitPanel> list, PortraitPanel value,
					int index, boolean isSelected, boolean cellHasFocus) {
				return value;
			}
		});
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		JScrollPane pane_list = new JScrollPane(list);
		GridBagConstraints gbc_pane_list = new GridBagConstraints();
		gbc_pane_list.gridx = 1;
		gbc_pane_list.gridy = 3;
		gbc_pane_list.fill = GridBagConstraints.BOTH;
		add(pane_list, gbc_pane_list);
		
		SearchPlayerPanel pnl_search = new SearchPlayerPanel(null);//TODO
		GridBagConstraints gbc_pnl_search = new GridBagConstraints();
		gbc_pnl_search.gridx = 1;
		gbc_pnl_search.gridy = 1;
		gbc_pnl_search.fill = GridBagConstraints.HORIZONTAL;
		add(pnl_search, gbc_pnl_search);
		
		ReturnButton btn_return = new ReturnButton();
		GridBagConstraints gbc_btn_return = new GridBagConstraints();
		gbc_btn_return.gridx = 1;
		gbc_btn_return.gridy = 4;
		gbc_btn_return.anchor = GridBagConstraints.SOUTHWEST;
		add(btn_return,gbc_btn_return);
	}

}
