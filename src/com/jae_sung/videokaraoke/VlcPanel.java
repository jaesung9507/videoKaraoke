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
import java.util.ArrayList;
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
    private EmbeddedMediaPlayerComponent component;
    private EmbeddedMediaPlayer player;
    private ArrayList<Song> listSong = new ArrayList<>();
    private String strIntroPath;
    private Song playSong = null;
    private TopPanel topPanel;
    private int nMsgType = 0;
    private int nTempo = 0;
    
    public VlcPanel(TopPanel topPanel, String strIntroPath) {
    	this.topPanel = topPanel;
    	this.strIntroPath = strIntroPath;
        new NativeDiscovery().discover();
        component = new EmbeddedMediaPlayerComponent();
        player = component.getMediaPlayer();        

        setLayout(new BorderLayout());
        setVisible(true);
        add(component, BorderLayout.CENTER);
        player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
            	mediaPlayer.setRepeat(true);
            }
        });
    }
    
    public void startVLC() {
    	player.playMedia(strIntroPath);
    	Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String strMsg = "";
				if(playSong != null)
					strMsg = "현재곡 ♬ | " + playSong;
				else if(nMsgType == 0)
					nMsgType++;

				switch(nMsgType) {
				case 1:
					if(listSong.size() > 0)
						strMsg = "다음곡 ♬ | " + listSong.get(0);
					break;
				case 2:
					if(listSong.size() > 0) {
						strMsg = "예약곡 ♬ | ";
						for(int i=0;i<listSong.size();i++)
							strMsg += listSong.get(i).getNum() + " ";
					}
					break;
				default:
					break;
				}
				
				topPanel.setSongMsg(strMsg);
				nMsgType++;
				if(nMsgType > 2)
					nMsgType = 0;
			}
		};
		timer.schedule(task, 0, 3000);
    }
    
    public void tempoUp() {
    	if(playSong != null) {
    		if(++nTempo > 6)
    			nTempo = 6;
    		float fTempo = 1.0f + nTempo * 0.1f;
    		player.setRate(fTempo);
    		printTempo();
    	}
    }
    
    public void tempoDown() {
    	if(playSong != null) {
    		if(--nTempo < -6)
    			nTempo = -6;
    		float fTempo = 1.0f + nTempo * 0.1f;
    		player.setRate(fTempo);
    		printTempo();
    	}
    }
    
    private void printTempo() {
    	String strMsg = "템포 | ";
    	switch(nTempo) {
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
    	topPanel.setTempMsg(strMsg);
    }
    
    public int getSongCount() {
    	return listSong.size();
    }
    
    public boolean addSong(Song song) {
		boolean bResult = false;
    	if(song != null) {
    		listSong.add(song);
    		bResult = true;
    	}
    	return bResult;
    }
    
    public boolean addFirstSong(Song song) {
    	boolean bResult = false;
    	if(song != null) {
    		listSong.add(0, song);
    		bResult = true;
    	}
    	return bResult;
    }
    
    public void playSong() {
    	if(listSong.size() > 0) {
    		playSong = listSong.get(0);
    		listSong.remove(0);
    	}
    	else {
    		playSong = null;
    	}
    	endVideo();
    }
    
    public void pauseSong() {
    	if(playSong != null) {
    		if(player.canPause())
    			player.pause();
    		else
    			player.play();
    	}
    }
    
    public void cancelSong() {
    	if(playSong != null)
    		player.stop();
    }
    
    private void runVideo() {
    	if(playSong != null) {
    		player.playMedia(playSong.getFilePath());
    		topPanel.setSongMsg("현재곡 ♬ | " + playSong);
    	}
    	else {
    		player.playMedia(strIntroPath);
    		topPanel.setSongMsg("");
    	}
    }
    
    private void endVideo() {
    	player.release();
        component.release();
        remove(component);
        component = new EmbeddedMediaPlayerComponent();
        player = component.getMediaPlayer();
        player.setPlaySubItems(true);
        add(component, BorderLayout.CENTER);
        revalidate();
        player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
        	private void run(MediaPlayer mediaPlayer) {
        		if(playSong.getFilePath().substring(0,4).equals("http")) {
            		playSong.setFilePath("play" + playSong.getFilePath().substring(4));
            	}
            	else if(playSong != null) {
            		if(playSong.getFilePath().substring(0, 4).equals("play"))
                		playSong.setFilePath("http" + playSong.getFilePath().substring(4));
            		playSong();
            	}
            	else {
            		mediaPlayer.setRepeat(true);
            	}
        	}
            @Override
            public void finished(MediaPlayer mediaPlayer) { run(mediaPlayer); }
            @Override
            public void stopped(MediaPlayer mediaPlayer) { run(mediaPlayer); }
        });
        runVideo();
    }
}
