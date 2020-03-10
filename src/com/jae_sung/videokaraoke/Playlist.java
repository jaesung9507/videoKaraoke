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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Playlist {
	private static Playlist m_instance = null;
	private ArrayList<Song> m_listSong = new ArrayList<>();
	private Queue<Song> m_queueBooked = new LinkedList<Song>();
	
	private Playlist(String strFilePath) {
		readFile(strFilePath);
	}
	
	public static Playlist getInstance() {
		if(m_instance == null)
			m_instance = new Playlist("./playlist.txt");
		return m_instance;
	}
	
	@Override
	public String toString() {
		return "수록곡 : " + m_listSong.size() + "곡";
	}
	
	public String toArtistString(String strArtist) {
		String strResult = "문법 오류\nExample : -artist 가수명";
		if(strArtist != null) {
			int nCount = 0;
			for(int i=0; i<m_listSong.size();i++) {
				if(m_listSong.get(i).getArtist().equals(strArtist))
					nCount++;
			}
			
			strResult = "가수명 : " + strArtist + "\n수록곡 : " + nCount;
		}
		
		return strResult;
	}
	
	public void bookSong(int nNum) {
		Song song = getSong(nNum);
    	bookSong(song);
    }
	
	public void bookSong(Song song) {
    	if(song != null) {
    		m_queueBooked.offer(song);
    		TopPanel.getInstance().setTempMsg(song.getNum() + " | 예약되었습니다.");
    	}
    }
	
	public boolean bookFirstSong(int nNum) {
		Song song = getSong(nNum);
    	return bookFirstSong(song, true);
	}
	
	public boolean bookFirstSong(int nNum, boolean bMsg) {
		Song song = getSong(nNum);
    	return bookFirstSong(song, bMsg);
	}
	
	public boolean bookFirstSong(Song song, boolean bMsg) {
		boolean bResult = false;
    	if(song != null) {
    		((LinkedList<Song>)m_queueBooked).add(0, song);
    		bResult = true;
    		if(bMsg)
    			TopPanel.getInstance().setTempMsg(song.getNum() + " | 우선예약되었습니다.");
    	}
    	return bResult;
    }
	
	public void deleteBookedSong(int nIndex) {
		if(m_queueBooked.size() > nIndex)
			((LinkedList<Song>)m_queueBooked).remove(nIndex);
	}
	
	public Song getBookedSong(int nIndex) {
		return ((LinkedList<Song>)m_queueBooked).get(nIndex);
	}
	
	public Song pollBookedSong() {
		return m_queueBooked.poll();
	}
	
	public int getBookedSongCount() {
    	return m_queueBooked.size();
    }
	
	public void exportHTML() {
		String strResult = "<html><head><title>Playlist</title></head><body><table border=\"1\">";
		strResult += "<tr><th style=\"background-color: #ffff00;\">번호</th><th>제목</th><th>가수</th></tr>";
		for(int i=0; i<m_listSong.size();i++) {
			Song song = m_listSong.get(i);
			strResult += "<tr><td style=\"background-color: #ffff00;\"><b>" + song.getNum() + "</b></td>";
			strResult += "<td>" + song.getName() + "</td>";
			strResult += "<td>" + song.getArtist() + "</td></tr>";
		}
		strResult += "</table></body></html>";
		File file = new File("./Playlist.html");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(strResult);
			fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Song getSong(int nNum) {
		Song song = null;
		if(nNum != -1) {
			for(int i=0; i<m_listSong.size();i++) {
				if(m_listSong.get(i).getNum() == nNum) {
					song = m_listSong.get(i);
					break;
				}
			}
		}
		return song;
	}
	
	public ArrayList<Song> searchTitle(String strKeyword) {
		ArrayList<Song> listResult = new ArrayList<>();
		for(int i=0; i < m_listSong.size(); i++){
			String choName = VideoKaraoke.splitHangulToConsonant((m_listSong.get(i).getName().toUpperCase()));
			if(m_listSong.get(i).getName().replaceAll(" ", "").contains(strKeyword) || choName.replaceAll(" ", "").contains(strKeyword))
				listResult.add(m_listSong.get(i));
		}
		return listResult;
	}
	
	public ArrayList<Song> searchArtist(String strKeyword) {
		ArrayList<Song> listResult = new ArrayList<>();
		for(int i=0; i < m_listSong.size(); i++){
			String choArtist = VideoKaraoke.splitHangulToConsonant((m_listSong.get(i).getArtist().toUpperCase()));
			if(m_listSong.get(i).getArtist().replaceAll(" ", "").contains(strKeyword) || choArtist.replaceAll(" ", "").contains(strKeyword))
				listResult.add(m_listSong.get(i));
		}
		return listResult;
	}
	
	private void readFile(String strFilePath) {
		try{
            File file = new File(strFilePath);
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String strLine = "";
            Song temp = null;
            int nCnt = 0;
            while((strLine = bufReader.readLine()) != null){
            	if(strLine.isEmpty() || strLine.charAt(0) == '#')
            		continue;
            	
            	if(temp == null) {
            		int nNum = Integer.valueOf(strLine);
            		temp = new Song();
            		temp.setNum(nNum);
            		nCnt = 0;
            	}
            	else {
            		switch(nCnt) {
            		case 0:
            			temp.setName(strLine);
            			break;
            		case 1:
            			temp.setArtist(strLine);
            			break;
            		case 2:
            		default:
            			temp.setFilePath(strLine);
            			m_listSong.add(temp);
            			temp = null;
            			break;
            		}
            		nCnt++;
            	}
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
	}
}
