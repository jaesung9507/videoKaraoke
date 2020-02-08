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
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {	
	public final int PANEL_MAIN = 0;
	public final int PANEL_SEARCH = 1;
	public final int PANEL_BOOKED = 2;
	public final int PANEL_INFO = 3;
	public final int MAX_CURSOR = PANEL_INFO;
	
	private CardLayout m_card = new CardLayout();
	private SearchPanel m_pnlSearch;
	
	private int m_nCursor = PANEL_SEARCH;	// range : 1~MAX_CURSOR
	private int m_nSelectedPanel = PANEL_MAIN;
	
	public MenuPanel(JFrame f) {
		setLayout(m_card);
		JPanel pnlMain = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int nWidth = this.getSize().width/MAX_CURSOR;
				int nHeight = (int)(this.getSize().height*0.9);
				int nY = (int)(this.getSize().height*0.1/2);
				
				String strImgSrc[] = {"./res/search", "./res/booked", "./res/info"};
				strImgSrc[m_nCursor-1] += "_focus";
				
				for(int i=0;i<MAX_CURSOR;i++) {
					ImageIcon icon = new ImageIcon(strImgSrc[i] + ".jpg");
					g.drawImage(icon.getImage(), nWidth*i, nY, nWidth, nHeight, null);
				}
			}
		};
		Color color = new Color(50, 50, 50, 255);
		pnlMain.setBackground(color);
		
		m_pnlSearch = new SearchPanel(color, f);
		BookedPanel pnlBooked = new BookedPanel(color);
		InfoPanel pnlInfo = new InfoPanel(color);
		
		add(pnlMain, String.valueOf(PANEL_MAIN));
		add(m_pnlSearch, String.valueOf(PANEL_SEARCH));
		add(pnlBooked, String.valueOf(PANEL_BOOKED));
		add(pnlInfo, String.valueOf(PANEL_INFO));
	}
	
	public void init() {
		m_card.first(this);
		m_pnlSearch.init();
		m_nCursor = PANEL_SEARCH;
		m_nSelectedPanel = PANEL_MAIN;
		arrowKeyInput(0x00);
	}
	
	public int getSelectedPanel() {
		return m_nSelectedPanel;
	}
	
	public void selectInput() {
		switch(m_nCursor) {
		case PANEL_SEARCH:
		case PANEL_BOOKED:
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
				m_nCursor--;
				break;
			case KeyEvent.VK_RIGHT:
				m_nCursor++;
			default:
				break;
			}
			
			if(m_nCursor > MAX_CURSOR)
				m_nCursor = MAX_CURSOR;
			if(m_nCursor < 1)
				m_nCursor = 1;
			repaint();
		}
	}
	
	private void showPanel() {
		m_nSelectedPanel = m_nCursor;
		m_card.show(this, String.valueOf(m_nCursor));
		if(m_nCursor == PANEL_SEARCH)
			m_pnlSearch.giveFocus();
	}
}
