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

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	public final int FOCUS_SEARCH = 0;
	public final int FOCUS_INFO = 1;
	public final int MAX_FOCUS = 2;
	JPanel mainPanel = new JPanel();
	JLabel searchLabel, infoLabel;
	private int m_nFocus = FOCUS_SEARCH;
	
	public MenuPanel(int nTopR, int nTopG, int nTopB) {
		setBackground(new Color(nTopR, nTopG, nTopB, 255));
		mainPanel.setBackground(new Color(nTopR, nTopG, nTopB, 255));
		searchLabel = new JLabel();
		infoLabel = new JLabel();
		searchLabel.setIcon(new ImageIcon("./res/search_focus.jpg"));
		infoLabel.setIcon(new ImageIcon("./res/info.jpg"));
		mainPanel.add(searchLabel);
		mainPanel.add(infoLabel);
		add(mainPanel);
	}
	
	public void arrowKeyInput(int nKeyCode) {
		switch(nKeyCode) {
		case KeyEvent.VK_LEFT:
			m_nFocus--;
			break;
		case KeyEvent.VK_RIGHT:
			m_nFocus++;
		default:
			break;
		}
		
		if(m_nFocus >= MAX_FOCUS)
			m_nFocus = MAX_FOCUS-1;
		if(m_nFocus < 0)
			m_nFocus = 0;
		
		switch(m_nFocus) {
		case FOCUS_SEARCH:
			searchLabel.setIcon(new ImageIcon("./res/search_focus.jpg"));
			infoLabel.setIcon(new ImageIcon("./res/info.jpg"));
			break;
		case FOCUS_INFO:
			searchLabel.setIcon(new ImageIcon("./res/search.jpg"));
			infoLabel.setIcon(new ImageIcon("./res/info_focus.jpg"));
			break;
		default:
			break;
		}
	}
}
