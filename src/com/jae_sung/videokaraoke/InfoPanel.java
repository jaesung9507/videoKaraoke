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

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {
	public InfoPanel(Color colorBk) {
		setBackground(colorBk);
		JLabel infoLabel = new JLabel(getInfoText());
		infoLabel.setForeground(new Color(255, 255, 255, 255));
		add(infoLabel);
	}
	
	private String getInfoText() {
		String strResult = "<html><b>Contact</b><br>" +
			"http://www.jae-sung.com<br>" +
			"jaesung.lee9507@gmail.com<br><br>" +
			"<b>Contributors</b><br>" +
			"namjunwoo223 (Hangul Initial Search)<br><br>" +
			"<b>Source Code</b><br>" +
			"https://github.com/jaesung9507/videoKaraoke<br><br>" +
			"VideoKaraoke is free software: you can redistribute it and/or modify<br>" + 
			"it under the terms of the GNU General Public License as published by<br>" + 
			"the Free Software Foundation, either version 3 of the License, or<br>" + 
			"(at your option) any later version.<br>" + 
			"<br>" + 
			"VideoKaraoke is distributed in the hope that it will be useful,<br>" + 
			"but WITHOUT ANY WARRANTY; without even the implied warranty of<br>" + 
			"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br>" + 
			"GNU General Public License for more details.<br>" + 
			"<br>" + 
			"You should have received a copy of the GNU General Public License<br>" + 
			"along with VideoKaraoke.  If not, see <http://www.gnu.org/licenses/>.<br>" + 
			"<br>" + 
			"Copyright (c) 2020, LEE Jae-Sung.<br>" + 
			"<br>" + 
			"VideoKaraoke makes use of VLCJ (https://github.com/caprica/vlcj/tree/vlcj-3.x/).<br>" + 
			"VLCJ is licensed under GPLv3.</html>";
		return strResult;
	}
}
