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
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel implements KeyListener {
	public final int MAX_CURSOR = 8;
	
	private JLabel m_lblCategory;
	private JLabel[] m_lblResult = new JLabel[MAX_CURSOR];
	private JTextField m_txtKeyword;
	private JFrame m_frmFocusMaster;
	
	private boolean m_bTitle = true;
	private int m_nCursor = 0;
	private ArrayList<Song> m_listResult;
	
	public SearchPanel(Color colorBk, JFrame f) {
		setBackground(colorBk);
		m_frmFocusMaster = f;
		setLayout(null);
		int nWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.8);
		int nHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		
		JLabel lblTitle = new JLabel("곡 검색");
		Font font = new Font("Airal", Font.PLAIN, 50);
		lblTitle.setForeground(new Color(255, 255, 255, 255));
		lblTitle.setFont(font);
		lblTitle.setBounds(0, 0, nWidth/2, nHeight/(MAX_CURSOR+1));
		
		m_lblCategory = new JLabel("제목별");
		m_lblCategory.setForeground(new Color(255, 255, 255, 255));
		m_lblCategory.setFont(font);
		m_lblCategory.setBounds(nWidth/2, 0, nWidth/8, nHeight/(MAX_CURSOR+1));
		
		m_txtKeyword = new JTextField();
		m_txtKeyword.setOpaque(true);
		m_txtKeyword.setBackground(new Color(255, 255, 255, 255));
		m_txtKeyword.setFont(font);
		m_txtKeyword.setBounds(nWidth/2+nWidth/8, 0, nWidth/8*3, nHeight/(MAX_CURSOR+1));
		m_txtKeyword.setFocusTraversalKeysEnabled(false);
		m_txtKeyword.addKeyListener(this);
		
		add(lblTitle);
		add(m_lblCategory);
		add(m_txtKeyword);
		
		for(int i=0;i<MAX_CURSOR;i++) {
			m_lblResult[i] = new JLabel();
			m_lblResult[i].setOpaque(true);
			m_lblResult[i].setFont(font);
			m_lblResult[i].setForeground(new Color(255, 255, 255, 255));
			m_lblResult[i].setBounds(0, nHeight/9*(i+1), nWidth, nHeight/(MAX_CURSOR+1));
			add(m_lblResult[i]);
		}
		
		resultPrint();
	}
	
	public void init() {
		m_txtKeyword.setText("");
		resultPrint();
	}
	
	public void giveFocus() {
		m_txtKeyword.requestFocus();
	}
	
	public void toggleCategory() {
		m_bTitle = !m_bTitle;
		if(m_bTitle)
			m_lblCategory.setText("제목별");
		else
			m_lblCategory.setText("가수별");
	}
	
	private void resultPrint() {
		m_lblResult[0].setText("검색결과 없습니다.");
		for(int i=1;i<m_lblResult.length;i++)
			m_lblResult[i].setText("");
		
		if(m_bTitle)
			m_listResult = Playlist.getInstance().searchTitle(m_txtKeyword.getText());
		else
			m_listResult = Playlist.getInstance().searchArtist(m_txtKeyword.getText());
		
		for(int i=0;i<m_listResult.size();i++) {
			if(i < MAX_CURSOR)
				m_lblResult[i].setText(m_listResult.get(i).toString());
		}
		cursorPrint();
	}
	
	private void cursorPrint() {
		if(m_nCursor < 0)
			m_nCursor = 0;
		if(m_nCursor >= MAX_CURSOR)
			m_nCursor = MAX_CURSOR-1;
		
		for(int i=0;i<MAX_CURSOR;i++) {
			if(i == m_nCursor)
				m_lblResult[i].setBackground(new Color(255, 0, 0, 255));
			else if(i%2 == 0)
				m_lblResult[i].setBackground(new Color(100, 100, 100,255));
			else
				m_lblResult[i].setBackground(new Color(50, 50, 50, 255));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_F1:
			m_frmFocusMaster.requestFocus();
			m_frmFocusMaster.getKeyListeners()[0].keyReleased(e);
			break;
		case KeyEvent.VK_UP:
			m_nCursor--;
			cursorPrint();
			break;
		case KeyEvent.VK_DOWN:
			m_nCursor++;
			cursorPrint();
			break;
		case KeyEvent.VK_TAB:
			toggleCategory();
		default:
			m_nCursor = 0;
			resultPrint();
			break;
		}
	}
}
