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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;

class Controller implements KeyListener, FocusListener {
	private MenuPanel menuPanel;
	private VlcPanel vlc;
	
	public Controller() {
		Playlist playlist = Playlist.getInstance();
		System.out.println(playlist);
		JFrame f = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		TopPanel topPanel = TopPanel.getInstance();
	    topPanel.setBounds(0, 0, screenSize.width, screenSize.height / 15);
	    menuPanel = new MenuPanel(f);
	    menuPanel.setBounds(screenSize.width / 10, screenSize.height/10, (int)(screenSize.width * 0.8), (int)(screenSize.height * 0.8));
	    menuPanel.setVisible(false);
	    f.add(topPanel);
	    f.add(menuPanel);
		vlc = new VlcPanel();
		f.add(vlc);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.addKeyListener(this);
		f.addFocusListener(this);
		vlc.startVLC();
	}
	
	private void playSound(String FilePath) {
		try {
			File soundFile = new File(FilePath);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
			AudioFormat format = audioIn.getFormat();             
			
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
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
		int nKeyCode = e.getKeyCode();
		Playlist playlist = Playlist.getInstance();
		TopPanel topPanel = TopPanel.getInstance();
		switch(nKeyCode) {
		// 시작
		case KeyEvent.VK_ENTER:
			if(menuPanel.isVisible() == false ||
					menuPanel.getSelectedPanel() == menuPanel.PANEL_SEARCH) {
				if(playlist.bookFirstSong(topPanel.getInputNumber(), false))
					vlc.playSong();
				else if(topPanel.getInputNumber() != -1)
					topPanel.setTempMsg("안내 | 없는 곡입니다.");
				else if(playlist.getBookedSongCount() > 0)
					vlc.playSong();
				topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			}
			break;
		// 취소
		case KeyEvent.VK_ESCAPE:
			if(menuPanel.isVisible()) {
				menuPanel.init();
				menuPanel.setVisible(false);
			}
			else {
				vlc.cancelSong();
			}
			break;
		// 일시정지
		case KeyEvent.VK_SPACE:
			vlc.pauseSong();
			break;
		// 방향키 이동
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			if(menuPanel.isVisible())
				menuPanel.keyReleased(nKeyCode);
			break;
		// 메뉴
		case KeyEvent.VK_F1:
			if(menuPanel.isVisible())
				menuPanel.init();
			menuPanel.setVisible(!menuPanel.isVisible());
			break;
		// 반주 작게
		case KeyEvent.VK_F2:
			vlc.volumeDown();
			break;
		// 반주 크게
		case KeyEvent.VK_F3:
			vlc.volumeUp();
			break;
		// 템포 느리게
		case KeyEvent.VK_F4:
			vlc.tempoDown();
			break;
		// 템포 빠르게
		case KeyEvent.VK_F5:
			vlc.tempoUp();
			break;
		// 박수
		case KeyEvent.VK_F6:
			playSound("./res/clap.wav");
			break;
		// 선택
		case KeyEvent.VK_F7:
			if(menuPanel.isVisible())
				menuPanel.selectInput();
			break;
		// 예약
		case KeyEvent.VK_F8:
			playlist.bookSong(topPanel.getInputNumber());
			topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			menuPanel.keyReleased(nKeyCode);
			break;
		// 우선예약
		case KeyEvent.VK_F9:
			playlist.bookFirstSong(topPanel.getInputNumber());
			topPanel.inputNumber((char)KeyEvent.VK_CLEAR);
			menuPanel.keyReleased(nKeyCode);
			break;
		// 예약취소
		case KeyEvent.VK_DELETE:
			if(menuPanel.getSelectedPanel() == menuPanel.PANEL_BOOKED)
				menuPanel.keyReleased(nKeyCode);
			break;
		// 종료
		case KeyEvent.VK_F12:
			System.exit(0);
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
		case KeyEvent.VK_S:
			if(e.isControlDown()) {
				playlist.exportHTML();
				break;
			}
		default:
			System.out.println(nKeyCode);
			break;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(menuPanel.isVisible()) {
			menuPanel.init();
			menuPanel.setVisible(false);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(menuPanel.getSelectedPanel() != menuPanel.PANEL_SEARCH)
			((JFrame)e.getSource()).requestFocus();
	}
}

public class VideoKaraoke {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length > 0) {
			if(args[0].equals("-ver"))
				System.out.println("VideoKaraoke version 0.1.1");
			else if(args[0].equals("-playlist"))
				System.out.println(Playlist.getInstance());
			else if(args[0].equals("-artist"))
				if(args.length > 1)
					System.out.println(Playlist.getInstance().toArtistString(args[1]));
				else
					System.out.println(Playlist.getInstance().toArtistString(null));
			else
				System.out.println("Unrecognized option: " + args[0]);
			return;
		}
		else {
			new Controller();
		}
	}
	
	public static String splitHangulToConsonant(String strText) {
    	int HANGUL_BASE = 0xAC00;
    	int HANGUL_END = 0xD7AF;
    	int JAMO_BASE = 0x1100;
    	int[] JAMO_ARRAY= {
    		0x3131,0x3132,0x3134,0x3137,0x3138,0x3139,0x3141,
    		0x3142,0x3143,0x3145,0x3146,0x3147,0x3148,0x3149,
    		0x314a,0x314b,0x314c,0x314d,0x314e
    	};
    	
    	StringBuilder sb = new StringBuilder();
    	for(char cChar : strText.toCharArray()) {
    		if(cChar >= HANGUL_BASE && cChar <= HANGUL_END){
    			int nCode = ((cChar - HANGUL_BASE) / 28 / 21) + JAMO_BASE;
    			char cJamo = (char)JAMO_ARRAY[nCode-JAMO_BASE];
    			sb.append(cJamo);
    		}
    		else {
    			sb.append(cChar);
    		}
    	}
    	return sb.toString();
	}
}
