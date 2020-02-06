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
	private static TopPanel m_instance = null;
	private String m_strKaraokeName;
	private String m_strInputNum = "";
	private String m_strSongMsg = "";
	private String m_strTempMsg = "";
	private int m_nTimerCnt = 0;
	
	private TopPanel() {
		SettingMgr setting = SettingMgr.getInstance();
		setBackground(setting.getTopColor());
		m_strKaraokeName = setting.getKaraokeName();
	}
	
	public static TopPanel getInstance() {
		if(m_instance == null)
			m_instance = new TopPanel();
		return m_instance;
	}
	
	public void inputNumber(char c) {
		switch(c) {
		case KeyEvent.VK_BACK_SPACE:
			if(m_strInputNum.length() > 0)
				m_strInputNum = m_strInputNum.substring(0, m_strInputNum.length()-1);
			break;
		case KeyEvent.VK_CLEAR:
			m_strInputNum = "";
			break;
		default:
			m_strInputNum += c;
			m_strTempMsg = "";
			break;
		}
		repaint();
	}
	
	public int getInputNumber() {
		int nResult = -1;
		if(m_strInputNum.length() > 0)
			nResult = Integer.parseInt(m_strInputNum);
		
		return nResult;
	}
	
	public void setSongMsg(String str) {
		m_strSongMsg = str;
		repaint();
	}
	
	public String getSongMsg() {
		return m_strSongMsg;
	}
	
	public void setTempMsg(String str) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				m_nTimerCnt--;
				if(m_nTimerCnt == 0) {
					m_strTempMsg = "";
					repaint();
				}
			}
		};
		timer.schedule(task, 3000);
		m_nTimerCnt++;
		
		m_strTempMsg = str;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Font font = new Font("Airal", Font.PLAIN, 50);
		g.setFont(font);
		g.setColor(new Color(255, 255, 255, 150));
		String strMsg = m_strKaraokeName;
		if(!m_strSongMsg.equals(""))
			strMsg = m_strSongMsg;
		if(!m_strInputNum.equals(""))
			strMsg = m_strKaraokeName + " - " + m_strInputNum;
		if(!m_strTempMsg.equals(""))
			strMsg = m_strTempMsg;
		g.drawString(strMsg, 50, (screenSize.height / 15) / 2 + (screenSize.height / 15) / 4);
	}
}
