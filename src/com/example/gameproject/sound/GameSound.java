package com.example.gameproject.sound;

import android.media.SoundPool;

import com.example.gameproject.interfaces.Sound;

public class GameSound implements Sound {
	
	int soundId;
	SoundPool soundPool;
	
	public GameSound(SoundPool sp, int id) {
		this.soundPool = sp;
		this.soundId = id;
	}
	
	@Override
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		soundPool.unload(soundId);
	}

}
