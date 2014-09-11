package com.example.gameproject.graphics;

import android.graphics.Bitmap;

import com.example.gameproject.interfaces.Graphics.PixmapFormat;
import com.example.gameproject.interfaces.Pixmap;

public class GamePixmap implements Pixmap {
	Bitmap bitmap;
	PixmapFormat pixmapFormat;

	public GamePixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.pixmapFormat = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return pixmapFormat;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}

}
