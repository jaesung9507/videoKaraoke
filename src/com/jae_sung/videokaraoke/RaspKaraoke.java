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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;

class Controller implements KeyListener {
	Playlist playlist;
	TopPanel topPanel;
	VlcPanel vlc;
	
	public Controller() {
		int nTopR = 0, nTopG = 0, nTopB = 0;
		String strKaraokeName = "노래방", strIntroVideo = "./intro.mp4";
		try {
			Properties p = new Properties();
			System.out.println();
			p.load(new FileInputStream("./setting.ini"));
			
			nTopR = Integer.parseInt(p.getProperty("TopBK_R"));
			nTopG = Integer.parseInt(p.getProperty("TopBK_G"));
			nTopB = Integer.parseInt(p.getProperty("TopBK_B"));
			strKaraokeName = new String(p.getProperty("KaraokeName").getBytes("ISO-8859-1"), "UTF-8");
			strIntroVideo = p.getProperty("introVideo");
		}
		catch(Exception e) { e.printStackTrace(); }
		
		playlist = new Playlist("./playlist.txt");
		JFrame f = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		topPanel = new TopPanel(nTopR, nTopG, nTopB, strKaraokeName);
	    topPanel.setBounds(0, 0, screenSize.width, screenSize.height / 15);
	    f.add(topPanel);
		vlc = new VlcPanel(topPanel, strIntroVideo);
		f.add(vlc);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.addKeyListener(this);
		vlc.startVLC();
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
		int nKeyCode = e.getKeyCode();
		switch(nKeyCode) {
		// 시작
		case KeyEvent.VK_ENTER:
			if(vlc.addFirstSong(playlist.searchSong(topPanel.getInputNumber())))
				vlc.playSong();
			else if(topPanel.getInputNumber() != -1)
				topPanel.setTempMsg("안내 | 없는 곡입니다.");
			else if(vlc.getSongCount() > 0)
				vlc.playSong();
			topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			break;
		// 취소
		case KeyEvent.VK_ESCAPE:
			vlc.cancelSong();
			break;
		// 일시정지
		case KeyEvent.VK_SPACE:
			vlc.pauseSong();
			break;
		// 예약
		case KeyEvent.VK_CONTROL:
			if(vlc.addSong(playlist.searchSong(topPanel.getInputNumber())))
				topPanel.setTempMsg(topPanel.getInputNumber() + " | 예약되었습니다.");
			topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			break;
		// 우선예약
		case KeyEvent.VK_SHIFT:
			if(vlc.addFirstSong(playlist.searchSong(topPanel.getInputNumber())))
				topPanel.setTempMsg(topPanel.getInputNumber() + " | 우선예약되었습니다.");
			topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			break;
		case KeyEvent.VK_0:
		case KeyEvent.VK_1:
		case KeyEvent.VK_2:
		case KeyEvent.VK_3:
		case KeyEvent.VK_4:
		case KeyEvent.VK_5:
		case KeyEvent.VK_6:
		case KeyEvent.VK_7:
		case KeyEvent.VK_8:
		case KeyEvent.VK_9:
		case KeyEvent.VK_BACK_SPACE:
			topPanel.inputNumber((char)nKeyCode);
			break;
		default:
			System.out.println(nKeyCode);
			break;
		}
	}
}

public class RaspKaraoke {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length > 0) {
			if(args[0].equals("-ver"))
				System.out.println("VideoKaraoke version 0.0.1");
			else
				System.out.println("Unrecognized option: " + args[0]);
			return;
		}
		else {
			new Controller();
		}
	}
}
