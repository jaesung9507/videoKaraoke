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
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BookedPanel extends JPanel {
	private final int MAX_CURSOR = 8;
	
	private JLabel[] m_lblBooked = new JLabel[MAX_CURSOR];
	
	private int m_nCursor = 0;	// range : 0~MAX_CURSOR-1
	private int m_nPage = 0;	// range : 0~N
	
	public BookedPanel(Color colorBk) {
		setBackground(colorBk);
		setLayout(null);
		int nWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.8);
		int nHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		
		JLabel lblTitle = new JLabel("예약확인");
		Font font = new Font("Airal", Font.PLAIN, 50);
		lblTitle.setForeground(new Color(255, 255, 255, 255));
		lblTitle.setFont(font);
		lblTitle.setBounds(0, 0, nWidth/2, nHeight/(MAX_CURSOR+1));
		
		add(lblTitle);
		
		for(int i=0;i<MAX_CURSOR;i++) {
			m_lblBooked[i] = new JLabel();
			m_lblBooked[i].setOpaque(true);
			m_lblBooked[i].setFont(font);
			m_lblBooked[i].setForeground(new Color(255, 255, 255, 255));
			m_lblBooked[i].setBounds(0, nHeight/9*(i+1), nWidth, nHeight/(MAX_CURSOR+1));
			add(m_lblBooked[i]);
		}
		bookPrint();
	}
	
	public void bookPrint() {
		Playlist playlist = Playlist.getInstance();
		m_lblBooked[0].setText("예약곡이 없습니다.");
		for(int i=1;i<m_lblBooked.length;i++)
			m_lblBooked[i].setText("");
		
		for(int i=0;i<MAX_CURSOR;i++) {
			int nIndex = i + MAX_CURSOR*m_nPage;
			if(playlist.getBookedSongCount() <= nIndex)
				break;
			m_lblBooked[i].setText(playlist.getBookedSong(nIndex).toString());
		}
		cursorPrint();
	}
	
	public void keyReleased(int nKeyCode) {
		switch(nKeyCode) {
		case KeyEvent.VK_UP:
			m_nCursor--;
			cursorPrint();
			break;
		case KeyEvent.VK_DOWN:
			m_nCursor++;
			cursorPrint();
			break;
		case KeyEvent.VK_DELETE:
			Playlist.getInstance().deleteBookedSong(m_nCursor);
			bookPrint();
		default:
			return;
		}
	}
	
	private void cursorPrint() {
		if(m_nCursor < 0) {
			m_nPage--;
			if(m_nPage < 0) {
				m_nCursor = 0;
				m_nPage = 0;
			}
			else {
				m_nCursor = MAX_CURSOR-1;
				bookPrint();
			}
		}
		else if(m_nCursor >= MAX_CURSOR) {
			m_nPage++;
			Playlist playlist = Playlist.getInstance();
			if(m_nPage > (playlist.getBookedSongCount()-1) / MAX_CURSOR) {
				m_nCursor = MAX_CURSOR-1;
				m_nPage = (playlist.getBookedSongCount()-1) / MAX_CURSOR;
			}
			else {
				m_nCursor = 0;
				bookPrint();
			}
		}
		else {
			for(int i=0;i<MAX_CURSOR;i++) {
				if(i == m_nCursor)
					m_lblBooked[i].setBackground(new Color(255, 0, 0, 255));
				else if(i%2 == 0)
					m_lblBooked[i].setBackground(new Color(100, 100, 100,255));
				else
					m_lblBooked[i].setBackground(new Color(50, 50, 50, 255));
			}
		}
	}
}
