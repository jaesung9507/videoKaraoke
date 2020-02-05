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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel implements KeyListener {
	JLabel categoryLabel;
	JTextField keywordTxt;
	JFrame focusMaster;
	
	boolean m_bTitle = true;
	
	public SearchPanel(int nTopR, int nTopG, int nTopB, JFrame f) {
		setBackground(new Color(nTopR, nTopG, nTopB, 255));
		focusMaster = f;
		setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int nWidth = (int)(screenSize.width * 0.8);
		int nHeight = (int)(screenSize.height * 0.8);
		
		JLabel titleLabel = new JLabel("곡 검색");
		Font font = new Font("Airal", Font.PLAIN, 50);
		titleLabel.setForeground(new Color(255, 255, 255, 255));
		titleLabel.setFont(font);
		titleLabel.setBounds(0, 0, nWidth/2, nHeight/9);
		
		categoryLabel = new JLabel("제목별");
		categoryLabel.setForeground(new Color(255, 255, 255, 255));
		categoryLabel.setFont(font);
		categoryLabel.setBounds(nWidth/2, 0, nWidth/8, nHeight/9);
		
		keywordTxt = new JTextField();
		keywordTxt.setOpaque(true);
		keywordTxt.setBackground(new Color(255, 255, 255, 255));
		keywordTxt.setFont(font);
		keywordTxt.setBounds(nWidth/2+nWidth/8, 0, nWidth/8*3, nHeight/9);
		keywordTxt.addKeyListener(this);
		
		add(titleLabel);
		add(categoryLabel);
		add(keywordTxt);
		
		JLabel[] resultLabel = new JLabel[8];
		for(int i=0;i<8;i++) {
			resultLabel[i] = new JLabel();
			resultLabel[i].setOpaque(true);
			if(i%2 == 0)
				resultLabel[i].setBackground(new Color(nTopR+50, nTopG+50, nTopB+50,255));
			else
				resultLabel[i].setBackground(new Color(nTopR, nTopG, nTopB, 255));
			resultLabel[i].setBounds(0, nHeight/9*(i+1), nWidth, nHeight/9);
			add(resultLabel[i]);
		}
	}
	
	public void giveFocus() {
		keywordTxt.requestFocus();
	}
	
	public void toggleCategory() {
		m_bTitle = !m_bTitle;
		if(m_bTitle)
			categoryLabel.setText("제목별");
		else
			categoryLabel.setText("가수별");
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
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_F1:
			focusMaster.requestFocus();
			focusMaster.getKeyListeners()[0].keyReleased(e);
			break;
		case KeyEvent.VK_F8:
			toggleCategory();
			break;
		default:
			break;
		}
	}
}
