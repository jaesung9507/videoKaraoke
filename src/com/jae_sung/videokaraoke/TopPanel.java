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
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	private String strKaraokeName;
	private String strInputNum = "";
	private String strSongMsg = "";
	private String strTempMsg = "";
	
	public TopPanel(int nTopR, int nTopG, int nTopB, String strKaraokeName) {
		setBackground(new Color(nTopR, nTopG, nTopB, 255));
		this.strKaraokeName = strKaraokeName;
	}
	
	public void inputNumber(char c) {
		switch(c) {
		case KeyEvent.VK_BACK_SPACE:
			if(strInputNum.length() > 0)
				strInputNum = strInputNum.substring(0, strInputNum.length()-1);
			break;
		case KeyEvent.VK_CLEAR:
			strInputNum = "";
			break;
		default:
			strInputNum += c;
			strTempMsg = "";
			break;
		}
		repaint();
	}
	
	public int getInputNumber() {
		int nResult = -1;
		if(strInputNum.length() > 0)
			nResult = Integer.parseInt(strInputNum);
		
		return nResult;
	}
	
	public void setSongMsg(String str) {
		strSongMsg = str;
		repaint();
	}
	
	public String getSongMsg() {
		return strSongMsg;
	}
	
	public void setTempMsg(String str) {
		strTempMsg = str;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				strTempMsg = "";
				repaint();
			}
		};
		timer.schedule(task, 3000);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Font font = new Font("Airal", Font.PLAIN, 50);
		g.setFont(font);
		g.setColor(new Color(255, 255, 255, 150));
		String strMsg = strKaraokeName;
		if(!strSongMsg.equals(""))
			strMsg = strSongMsg;
		if(!strInputNum.equals(""))
			strMsg = strKaraokeName + " - " + strInputNum;
		if(!strTempMsg.equals(""))
			strMsg = strTempMsg;
		g.drawString(strMsg, 50, (screenSize.height / 15) / 2 + (screenSize.height / 15) / 4);
	}
}
