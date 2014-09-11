package com.example.gameproject.sound;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.gameproject.interfaces.Audio;
import com.example.gameproject.interfaces.Music;
import com.example.gameproject.interfaces.Sound;

public class GameAudio implements Audio {

	AssetManager assets;
	SoundPool soundPool;
	
	public GameAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Music newMusic(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
			return new GameMusic(assetDescriptor);
		}
		catch(IOException e) {
			throw new RuntimeException("Couldn't load music '" + fileName + "'");
		}
	}

	@Override
	public Sound newSound(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new GameSound(soundPool, soundId);
		}
		catch(IOException e) {
			throw new RuntimeException("Couldn't load sound '" + fileName + "'");
		}
	}

}
