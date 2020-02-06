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
import java.io.FileInputStream;
import java.util.Properties;

public class SettingMgr {
	private static SettingMgr m_instance = null;
	
	private int m_nTopR = 0;
	private int m_nTopG = 0;
	private int m_nTopB = 0;
	String m_strKaraokeName = "노래방";
	String m_strIntroVideo = "./res/intro.mp4";
	
	private SettingMgr() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("./setting.ini"));
			
			m_nTopR = Integer.parseInt(p.getProperty("TopBK_R"));
			m_nTopG = Integer.parseInt(p.getProperty("TopBK_G"));
			m_nTopB = Integer.parseInt(p.getProperty("TopBK_B"));
			m_strKaraokeName = new String(p.getProperty("KaraokeName").getBytes("ISO-8859-1"), "UTF-8");
			m_strIntroVideo = p.getProperty("introVideo");
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public static SettingMgr getInstance() {
		if(m_instance == null)
			m_instance = new SettingMgr();
		return m_instance;
	}
	
	public Color getTopColor() {
		return new Color(m_nTopR, m_nTopG, m_nTopB, 255);
	}
	
	public String getKaraokeName() {
		return m_strKaraokeName;
	}
	
	public String getIntroVideoPath() {
		return m_strIntroVideo;
	}
}
