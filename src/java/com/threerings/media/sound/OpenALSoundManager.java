package com.threerings.media.sound;


import static com.threerings.media.Log.log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.lwjgl.util.WaveData;

import com.google.common.collect.Maps;

import com.samskivert.util.RunQueue;

import com.threerings.media.sound.SoundManager.Frob;
import com.threerings.openal.Clip;
import com.threerings.openal.ClipBuffer;
import com.threerings.openal.ClipProvider;
import com.threerings.openal.Sound;
import com.threerings.openal.SoundGroup;
import com.threerings.openal.Stream;

/**
 * Plays sounds via OpenAL.
 */
public class OpenALSoundManager extends AbstractSoundManager
    implements ClipProvider
{

    public OpenALSoundManager (SoundLoader loader)
    {
        _loader = loader;
        _alSoundManager = new MediaALSoundManager();
        _group = _alSoundManager.createGroup(this, SOURCE_COUNT);
    }

    public Clip loadClip (String path)
        throws IOException
    {
        int pkgEnd = path.lastIndexOf("/") + 1;
        byte[] data = _loader.load(path.substring(0, pkgEnd), path.substring(pkgEnd))[0];
        return new Clip(WaveData.create(new ByteArrayInputStream(data)));
    }

    @Override
    public RunQueue getSoundQueue ()
    {
        return RunQueue.AWT;
    }

    @Override
    public void lock (final String pkgPath, String... keys)
    {
        for (final String key : keys) {
            final String path = pkgPath + key;
            if (_locked.containsKey(path)) {
                continue;
            }
            _alSoundManager.loadClip(this, path, new ClipBuffer.Observer() {
                public void clipFailed (ClipBuffer buffer) {
                    log.warning("Unable to load sound", "path", path);
                }

                public void clipLoaded (ClipBuffer buffer) {
                    _locked.put(path, buffer);
                }});
        }
    }

    @Override
    public void unlock (String pkgPath, String... keys)
    {
        for (String key : keys) {
            _locked.remove(pkgPath + key);
        }
    }

    @Override
    protected Frob loop (String pkgPath, String key, float pan)
    {
        final Sound sound = _group.getSound(pkgPath + key);
        sound.loop(true);
        return new Frob(){
            public float getPan () {
                return 0;
            }

            public float getVolume () {
                return 0;
            }

            public void setPan (float pan) {}

            public void setVolume (float vol) { }

            public void stop () {
                sound.stop();
            }};
    }

    @Override
    public void play (String pkgPath, String key, float pan)
    {
        Sound sound = _group.getSound(pkgPath + key);
        sound.play(true);
    }

    @Override
    public void shutdown ()
    {
        _group.dispose();
        _locked.clear();
        for (Stream stream : _alSoundManager.getStreams()) {
            stream.dispose();
        }
    }

    protected class MediaALSoundManager extends com.threerings.openal.SoundManager {
        protected MediaALSoundManager () {
            super(getSoundQueue());
        }

        @Override
        protected ClipBuffer getClip (ClipProvider provider, String path) {
            if (_locked.containsKey(path)) {
                return _locked.get(path);
            }
            return super.getClip(provider, path, null);
        }
    }

    protected Map<String, ClipBuffer> _locked = Maps.newHashMap();

    protected final SoundLoader _loader;

    protected final SoundGroup _group;

    protected final com.threerings.openal.SoundManager _alSoundManager;

    /** Number of sounds that can be played simultaneously. */
    protected final int SOURCE_COUNT = 10;
}