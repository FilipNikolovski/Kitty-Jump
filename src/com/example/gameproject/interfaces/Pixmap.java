package com.example.gameproject.interfaces;

import com.example.gameproject.interfaces.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();
}