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
import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@SuppressWarnings("serial")
public class VlcPanel extends JPanel {
    private EmbeddedMediaPlayerComponent m_component;
    private EmbeddedMediaPlayer m_player;
    private Song m_playSong = null;
    private int m_nTempo = 0;
    
    public VlcPanel() {
    	new NativeDiscovery().discover();
    	m_component = new EmbeddedMediaPlayerComponent();
    	m_player = m_component.getMediaPlayer();

        setLayout(new BorderLayout());
        setVisible(true);
        add(m_component, BorderLayout.CENTER);
        m_player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
            	mediaPlayer.setRepeat(true);
            }
        });
    }
    
    public void startVLC() {
    	m_player.playMedia(SettingMgr.getInstance().getIntroVideoPath());
    	Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int m_nMsgType = 0;
			@Override
			public void run() {
				String strMsg = "";
				Playlist playlist = Playlist.getInstance();
				if(m_playSong != null)
					strMsg = "현재곡 ♬ | " + m_playSong;
				else if(m_nMsgType == 0)
					m_nMsgType++;

				switch(m_nMsgType) {
				case 1:
					if(playlist.getBookedSongCount() > 0)
						strMsg = "다음곡 ♬ | " + playlist.getBookedSong(0);
					break;
				case 2:
					if(playlist.getBookedSongCount() > 0) {
						strMsg = "예약곡 ♬ | ";
						for(int i=0;i<playlist.getBookedSongCount();i++)
							strMsg += playlist.getBookedSong(i).getNum() + " ";
					}
					break;
				default:
					break;
				}
				
				TopPanel.getInstance().setSongMsg(strMsg);
				m_nMsgType++;
				if(m_nMsgType > 2)
					m_nMsgType = 0;
			}
		};
		timer.schedule(task, 0, 3000);
    }
    
    public void volumeUp() {
    	int nVolume = m_player.getVolume() + 1;
    	if(nVolume > 100)
    		nVolume = 100;
    	m_player.setVolume(nVolume);
    	printVolume();
    }
    
    public void volumeDown() {
    	int nVolume = m_player.getVolume() - 1;
    	if(nVolume < 0)
    		nVolume = 0;
    	m_player.setVolume(nVolume);
    	printVolume();
    }
    
    private void printVolume() {
    	int nVolume = m_player.getVolume();
    	String strMsg = "반주음량 | "+ nVolume +" -[";
    	int nProgress = nVolume / 5;
    	for(int i=0;i<nProgress;i++)
    		strMsg += "=";
    	for(int i=20;i>nProgress;i--)
    		strMsg += "-";
    	strMsg += "]+";
    	TopPanel.getInstance().setTempMsg(strMsg);
    }
    
    public void tempoUp() {
    	if(m_playSong != null) {
    		if(++m_nTempo > 6)
    			m_nTempo = 6;
    		float fTempo = 1.0f + m_nTempo * 0.1f;
    		m_player.setRate(fTempo);
    		printTempo();
    	}
    }
    
    public void tempoDown() {
    	if(m_playSong != null) {
    		if(--m_nTempo < -6)
    			m_nTempo = -6;
    		float fTempo = 1.0f + m_nTempo * 0.1f;
    		m_player.setRate(fTempo);
    		printTempo();
    	}
    }
    
    private void printTempo() {
    	String strMsg = "템포 | ";
    	switch(m_nTempo) {
    	case -6: strMsg += "- ◀◁◁◁◁◁□▷▷▷▷▷▷ +"; break;
    	case -5: strMsg += "- ◁◀◁◁◁◁□▷▷▷▷▷▷ +"; break;
    	case -4: strMsg += "- ◁◁◀◁◁◁□▷▷▷▷▷▷ +"; break;
    	case -3: strMsg += "- ◁◁◁◀◁◁□▷▷▷▷▷▷ +"; break;
    	case -2: strMsg += "- ◁◁◁◁◀◁□▷▷▷▷▷▷ +"; break;
    	case -1: strMsg += "- ◁◁◁◁◁◀□▷▷▷▷▷▷ +"; break;
    	case 0: strMsg += "- ◁◁◁◁◁◁■▷▷▷▷▷▷ +"; break;
    	case 1: strMsg += "- ◁◁◁◁◁◁□▶▷▷▷▷▷ +"; break;
    	case 2: strMsg += "- ◁◁◁◁◁◁□▷▶▷▷▷▷ +"; break;
    	case 3: strMsg += "- ◁◁◁◁◁◁□▷▷▶▷▷▷ +"; break;
    	case 4: strMsg += "- ◁◁◁◁◁◁□▷▷▷▶▷▷ +"; break;
    	case 5: strMsg += "- ◁◁◁◁◁◁□▷▷▷▷▶▷ +"; break;
    	case 6: strMsg += "- ◁◁◁◁◁◁□▷▷▷▷▷▶ +"; break;
    	default: return;
    	}
    	TopPanel.getInstance().setTempMsg(strMsg);
    }
    
    public void playSong() {
    	Playlist playlist = Playlist.getInstance();
    	m_playSong = playlist.pollBookedSong();
    	endVideo();
    }
    
    public void pauseSong() {
    	if(m_playSong != null) {
    		if(m_player.canPause())
    			m_player.pause();
    		else
    			m_player.play();
    	}
    }
    
    public void cancelSong() {
    	if(m_playSong != null) {
    		m_playSong = null;
    		endVideo();
    	}
    }
    
    private void runVideo() {
    	if(m_playSong != null) {
    		if(m_player.playMedia(m_playSong.getFilePath()))
    			TopPanel.getInstance().setSongMsg("현재곡 ♬ | " + m_playSong);
    		else
    			TopPanel.getInstance().setTempMsg(m_playSong.getNum() + " | 오류로 시작할 수 없습니다.");
    	}
    	else {
    		m_player.playMedia(SettingMgr.getInstance().getIntroVideoPath());
    		TopPanel.getInstance().setSongMsg("");
    	}
    }
    
    private void endVideo() {
    	m_player.release();
    	m_component.release();
        remove(m_component);
        m_component = new EmbeddedMediaPlayerComponent();
        m_player = m_component.getMediaPlayer();
        m_player.setPlaySubItems(true);
        add(m_component, BorderLayout.CENTER);
        revalidate();
        m_player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
        	int nCall = 0;
            @Override
            public void finished(MediaPlayer mediaPlayer) {
            	if(m_playSong == null) {
        			mediaPlayer.setRepeat(true);
        		}
            	else {
            		if(m_playSong.getFilePath().substring(0,4).equals("http")) {
            			nCall++;
            			if(nCall <= 1)
                			return;
                	}
            		playSong();
            	}
            }
            @Override
            public void error(MediaPlayer mediaPlayer) {
            	TopPanel.getInstance().setTempMsg(m_playSong.getNum() + " | 설정된 경로 또는 URL을 확인하세요.");
            	cancelSong();
            }
        });
        runVideo();
    }
}
