/*
 * This file is part of VideoKaraoke.
 *
 * VideoKaraoke is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VideoKaraoke is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VideoKaraoke.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2020, LEE Jae-Sung.
 */

package com.jae_sung.videokaraoke;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {	
	public final int PANEL_MAIN = 0;
	public final int PANEL_SEARCH = 1;
	public final int PANEL_INFO = 2;
	public final int MAX_FOCUS = PANEL_INFO;
	
	CardLayout card = new CardLayout();
	JLabel searchBtn, infoBtn;
	
	private int m_nFocus = PANEL_SEARCH;	// range : 1~MAX_FOCUS
	private int m_nSelectedPanel = PANEL_MAIN;
	
	public MenuPanel(int nTopR, int nTopG, int nTopB) {
		setLayout(card);
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(nTopR, nTopG, nTopB, 255));
		searchBtn = new JLabel();
		infoBtn = new JLabel();
		arrowKeyInput(0x00);
		mainPanel.add(searchBtn);
		mainPanel.add(infoBtn);
		
		SearchPanel searchPanel = new SearchPanel(nTopR, nTopG, nTopB);
		InfoPanel infoPanel = new InfoPanel(nTopR, nTopG, nTopB);
		
		add(mainPanel, String.valueOf(PANEL_MAIN));
		add(searchPanel, String.valueOf(PANEL_SEARCH));
		add(infoPanel, String.valueOf(PANEL_INFO));
	}
	
	public void init() {
		card.first(this);
		m_nFocus = PANEL_SEARCH;
		m_nSelectedPanel = PANEL_MAIN;
		arrowKeyInput(0x00);
	}
	
	public void selectInput() {
		switch(m_nFocus) {
		case PANEL_SEARCH:
		case PANEL_INFO:
			showPanel();
			break;
		default:
			break;
		}
	}
	
	public void arrowKeyInput(int nKeyCode) {
		if(m_nSelectedPanel == PANEL_MAIN) {
			switch(nKeyCode) {
			case KeyEvent.VK_LEFT:
				m_nFocus--;
				break;
			case KeyEvent.VK_RIGHT:
				m_nFocus++;
			default:
				break;
			}
			
			if(m_nFocus > MAX_FOCUS)
				m_nFocus = MAX_FOCUS;
			if(m_nFocus < 1)
				m_nFocus = 1;
			
			switch(m_nFocus) {
			case PANEL_SEARCH:
				searchBtn.setIcon(new ImageIcon("./res/search_focus.jpg"));
				infoBtn.setIcon(new ImageIcon("./res/info.jpg"));
				break;
			case PANEL_INFO:
				searchBtn.setIcon(new ImageIcon("./res/search.jpg"));
				infoBtn.setIcon(new ImageIcon("./res/info_focus.jpg"));
				break;
			default:
				break;
			}
		}
	}
	
	private void showPanel() {
		m_nSelectedPanel = m_nFocus;
		card.show(this, String.valueOf(m_nFocus));
	}
}
