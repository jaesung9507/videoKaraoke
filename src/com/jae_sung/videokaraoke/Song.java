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

public class Song {
	private int m_nNum;
	private String m_strName;
	private String m_strArtist;
	private String m_strFilePath;
	
	public int getNum() {
		return m_nNum;
	}
	public void setNum(int nNum) {
		this.m_nNum = nNum;
	}
	public String getName() {
		return m_strName;
	}
	public void setName(String strName) {
		this.m_strName = strName;
	}
	public String getArtist() {
		return m_strArtist;
	}
	public void setArtist(String strArtist) {
		this.m_strArtist = strArtist;
	}
	public String getFilePath() {
		return m_strFilePath;
	}
	public void setFilePath(String strFilePath) {
		this.m_strFilePath = strFilePath;
	}
	@Override
	public String toString() {
		return getNum() + " " + getName() + " - " + getArtist();
	}
}
