package org.gnaural;

class BinauralBeatSoundEngine {
  // lost a lof of the usages of these after using the java decompiler
  static final String gnaural_version = "20220401";
  static final int BB_VOICETYPE_BINAURALBEAT = 0;
  static final int BB_VOICETYPE_PINKNOISE = 1;
  static final int BB_VOICETYPE_PCM = 2;
  static final int BB_VOICETYPE_ISOPULSE = 3;
  static final int BB_VOICETYPE_ISOPULSE_STAGGERED = 4;
  static final int BB_VOICETYPE_WATERDROPS = 5;
  static final int BB_VOICETYPE_RAIN = 6;
  static final int BB_VOICETYPE_ISOPULSE_ALTERNATING = 7;
  static final String[] BB_VoiceTypes = new String[]{"Binaural Beat", "Pink Noise", "Audio File", "Isochronic Pulse", "Isochronic Pulse - Staggered", "Waterdrop", "Rain", "Isochronic Pulse - Alternating"};
  static final int BB_EVENT_NUM_OF_ELEMENTS = 5;
  static final int BB_SELECTED = 1;
  static final int BB_UNSELECTED = 0;
  static final long BB_COMPLETED = 1L;
  static final long BB_NEWLOOP = 2L;
  static final long BB_NEWENTRY = 4L;
  static final int BB_UPDATEPERIOD_SAMPLES = 16;
  static final int BB_SIN_SCALER = 0x3FFF; // Max value for a short
  static final double BB_TWO_PI = Math.PI * 2;
  static final int BB_DROPLEN = 8192;
  static final int BB_RAINLEN = 44;
  static final int ML_MULT = 69069;
  static final float[] BB_DefaultBBSched = new float[]{9.0f, 0.72f, 0.72f, 0.0f, 262.35f, 45.0f, 0.73f, 0.73f, 12.0f, 262.1f, 60.0f, 0.73f, 0.73f, 8.0f, 260.83f, 60.0f, 0.73f, 0.73f, 6.0f, 259.14f, 120.0f, 0.73f, 0.73f, 5.0f, 257.45f, 180.0f, 0.73f, 0.73f, 4.3f, 254.07f, 180.0f, 0.74f, 0.74f, 4.0f, 249.0f, 6.0f, 0.74f, 0.74f, 3.9f, 243.94f, 6.0f, 0.74f, 0.74f, 7.0f, 243.77f, 360.0f, 0.74f, 0.74f, 3.9f, 243.6f, 6.0f, 0.75f, 0.75f, 4.2f, 233.47f, 6.0f, 0.75f, 0.75f, 7.0f, 233.3f, 180.0f, 0.75f, 0.75f, 3.9f, 233.13f, 180.0f, 0.76f, 0.76f, 4.0f, 228.06f, 6.0f, 0.77f, 0.77f, 3.9f, 222.99f, 6.0f, 0.77f, 0.77f, 7.0f, 222.82f, 340.0f, 0.77f, 0.77f, 3.9f, 222.66f, 6.0f, 0.78f, 0.78f, 4.2f, 213.08f, 6.0f, 0.78f, 0.78f, 7.0f, 212.91f, 180.0f, 0.78f, 0.78f, 4.0f, 212.75f, 180.0f, 0.78f, 0.78f, 4.2f, 207.68f, 6.0f, 0.79f, 0.79f, 3.8f, 202.61f, 6.0f, 0.79f, 0.79f, 7.0f, 202.44f, 400.0f, 0.79f, 0.79f, 3.9f, 202.27f, 6.0f, 0.8f, 0.8f, 4.2f, 191.01f, 6.0f, 0.8f, 0.8f, 7.0f, 190.84f, 180.0f, 0.8f, 0.8f, 4.2f, 190.67f, 180.0f, 0.8f, 0.8f, 3.9f, 185.61f, 6.0f, 0.81f, 0.81f, 4.0f, 180.54f, 6.0f, 0.81f, 0.81f, 7.0f, 180.37f, 300.0f, 0.81f, 0.81f, 4.0f, 180.2f, 6.0f, 0.82f, 0.82f, 3.8f, 171.76f, 6.0f, 0.82f, 0.82f, 7.0f, 171.59f, 180.0f, 0.82f, 0.82f, 3.9f, 171.42f, 180.0f, 0.82f, 0.82f, 4.1f, 166.35f, 6.0f, 0.83f, 0.83f, 3.9f, 161.28f, 6.0f, 0.83f, 0.83f, 7.0f, 161.11f, 360.0f, 0.83f, 0.83f, 3.9f, 160.94f, 6.0f, 0.84f, 0.84f, 4.1f, 150.81f, 6.0f, 0.84f, 0.84f, 7.0f, 150.64f, 180.0f, 0.84f, 0.84f, 3.9f, 150.47f, 180.0f, 0.84f, 0.84f, 3.6f, 145.41f, 6.0f, 0.85f, 0.85f, 4.0f, 140.34f, 6.0f, 0.85f, 0.85f, 7.0f, 140.17f, 64.0f, 0.85f, 0.85f, 4.3f, 140.0f};
  static int updateperiod = 1;
  BB_VoiceData[] BB_Voice;
  double BB_TotalDuration = 0.0;
  double BB_MaxBeatfrequency = 0.0;
  double BB_MaxBasefrequency = 0.0;
  long BB_CurrentSampleCount = 0L;
  long BB_CurrentSampleCountLooped = 0L;
  long BB_InfoFlag = 0L;
  int BB_VoiceCount = 0;
  int BB_LoopCount = 1;
  int BB_Loops;
  double BB_OverallVolume = 1.0;
  double BB_OverallBalance = 0.0;
  double BB_VolumeOverall_left = 1.0;
  double BB_VolumeOverall_right = 1.0;
  int BB_StereoSwap = 0;
  boolean BB_PauseFlag = true;
  boolean BB_InCriticalLoopFlag = false;
  int BB_Mono = 0; // set non-0 to mix stereo channels
  int BB_AUDIOSAMPLERATE = 44100;
  double BB_AUDIOSAMPLERATE_HALF = 0.5 * (double) this.BB_AUDIOSAMPLERATE;
  double BB_SAMPLE_FACTOR = Math.PI * 4 / (double) this.BB_AUDIOSAMPLERATE;
  short[] BB_DropMother = null;
  short[] BB_RainMother = null;
  float BB_WaterWindow = 126.0f;
  float BB_DropLowcut = 8.0f;
  float BB_RainLowcut = 0.15f;
  String mTitle = "Basic meditation session";
  String mDescription = "Default built-in schedule with descending base frequency, compensating volume, and intermittent wake-up spikes";
  String mAuthor = "Gnaural";
  int BB_OutputL = 0;
  int BB_OutputR = 0;
  long mcgn;
  long srgn;
  static double phi = (1 + Math.sqrt(5)) / 2;
  static double phe = 1/phi;
  
  // these have to be moved into BB_VoiceData if we have more than 1 voice
  private boolean leftRightChannelSwitch = true; // for alternating isochronic pulse
  
  BinauralBeatSoundEngine(int samplerate) {
    this.BB_AUDIOSAMPLERATE = samplerate;
    this.BB_AUDIOSAMPLERATE_HALF = 0.5 * (double) this.BB_AUDIOSAMPLERATE;
    this.BB_SAMPLE_FACTOR = BB_TWO_PI / (double) this.BB_AUDIOSAMPLERATE;
    this.BB_WaterWindow = this.BB_WaterWindow * 44100.0f / (float) this.BB_AUDIOSAMPLERATE;
    this.BB_DropLowcut = this.BB_DropLowcut * 44100.0f / (float) this.BB_AUDIOSAMPLERATE;
    this.BB_RainLowcut = this.BB_RainLowcut * 44100.0f / (float) this.BB_AUDIOSAMPLERATE;
    SeedRand(3676, 2676862);
    this.BB_Reset();
  }
  
  int BB_InitVoices(int NumberOfVoices) {
    int i = 0;
    if (null != this.BB_Voice) {
      while (this.BB_InCriticalLoopFlag) {
        ++i;
        try {
          Thread.sleep(100L);
        } catch (InterruptedException interruptedException) {
        }
      }
      this.BB_PauseFlag = true;
      this.BB_CleanupVoices();
    }
    this.BB_VoiceCount = 0;
    this.BB_Voice = new BB_VoiceData[NumberOfVoices];
    if (null == this.BB_Voice) {
      return 0;
    }
    for (i = 0; i < NumberOfVoices; ++i) {
      this.BB_Voice[i] = new BB_VoiceData();
      this.BB_Voice[i].Entry = null;
      this.BB_Voice[i].Drop = null;
      this.BB_Voice[i].id = 0;
      this.BB_Voice[i].mute = 0;
      this.BB_Voice[i].mono = 0;
      this.BB_Voice[i].hide = 0;
      this.BB_Voice[i].type = 0;
      this.BB_Voice[i].EntryCount = 0;
      this.BB_Voice[i].CurEntry = 0;
      this.BB_Voice[i].PCM_samples = null;
      this.BB_Voice[i].PCM_samples_currentcount = 0;
      this.BB_Voice[i].cur_beatfreq = 0.0;
      this.BB_Voice[i].cur_beatfreq_phaseenvelope = 0.0;
      this.BB_Voice[i].cur_beatfreq_phaseflag = false;
      this.BB_Voice[i].cur_beatfreq_phasesamplecount = 1;
      this.BB_Voice[i].cur_beatfreq_phasesamplecount_start = 1;
      this.BB_Voice[i].sinL = 0.0;
      this.BB_Voice[i].sinR = 0.0;
      this.BB_Voice[i].noiseL = 1;
      this.BB_Voice[i].noiseR = 1;
    }
    this.BB_VoiceCount = NumberOfVoices;
    return this.BB_VoiceCount;
  }
  
  void BB_SetupVoice(int i, int VoiceType, int mute, int mono, int hide, String description, byte[] PCM_samples, int NumberOfEvents) {
    this.BB_Voice[i].type = VoiceType;
    this.BB_Voice[i].mute = mute;
    this.BB_Voice[i].mono = mono;
    this.BB_Voice[i].mute = mute;
    this.BB_Voice[i].Description = description;
    this.BB_Voice[i].PCM_samples = PCM_samples;
    this.BB_Voice[i].EntryCount = 0;
    this.BB_Voice[i].CurEntry = 0;
    if (null == this.BB_Voice[i].Entry || this.BB_Voice[i].Entry.length != NumberOfEvents) {
      this.BB_Voice[i].Entry = new BB_EventData[NumberOfEvents];
      for (int j = 0; j < NumberOfEvents; ++j) {
        this.BB_Voice[i].Entry[j] = new BB_EventData();
      }
    }
    this.BB_Voice[i].TotalDuration = 0.0;
    this.BB_Voice[i].EntryCount = NumberOfEvents;
  }
  
  void BB_CalibrateVoices() {
    double totalduration = 0.0;
    double maxbeat_HALF = 5.0E-4;
    double maxbase = 0.001;
    for (BB_VoiceData v : this.BB_Voice) {
      v.TotalDuration = 0.0;
      int nextEntry = 0;
      for (BB_EventData e : v.Entry) {
        int prevEntry = nextEntry - 1;
        if (++nextEntry >= v.Entry.length) {
          nextEntry = 0;
        }
        v.TotalDuration += e.duration;
        e.AbsoluteEnd_samples = (long) (v.TotalDuration * (double) this.BB_AUDIOSAMPLERATE);
        e.beatfreq_end_HALF = v.Entry[nextEntry].beatfreq_start_HALF;
        e.beatfreq_spread_HALF = e.beatfreq_end_HALF - e.beatfreq_start_HALF;
        e.basefreq_end = v.Entry[nextEntry].basefreq_start;
        e.basefreq_spread = e.basefreq_end - e.basefreq_start;
        e.volL_end = v.Entry[nextEntry].volL_start;
        e.volL_spread = e.volL_end - e.volL_start;
        e.volR_end = v.Entry[nextEntry].volR_start;
        e.volR_spread = e.volR_end - e.volR_start;
        e.AbsoluteStart_samples = prevEntry < 0 ? 0L : v.Entry[prevEntry].AbsoluteEnd_samples;
        if (0 != v.hide) continue;
        if (maxbeat_HALF < e.beatfreq_start_HALF) {
          maxbeat_HALF = e.beatfreq_start_HALF;
        }
        if (!(maxbase < e.basefreq_start)) continue;
        maxbase = e.basefreq_start;
      }
      if (!(v.TotalDuration > totalduration)) continue;
      totalduration = v.TotalDuration;
    }
    this.BB_TotalDuration = totalduration;
    this.BB_MaxBeatfrequency = maxbeat_HALF * 2.0;
    this.BB_MaxBasefrequency = maxbase;
    for (BB_VoiceData v : this.BB_Voice) {
      if (!((float) v.TotalDuration < (float) this.BB_TotalDuration)) continue;
      v.Entry[v.Entry.length - 1].duration += this.BB_TotalDuration - v.TotalDuration;
    }
  }
  
  void BB_CleanupVoices() {
    for (int i = 0; i < this.BB_VoiceCount; ++i) {
      this.BB_Voice[i].Entry = null;
      this.BB_Voice[i].Drop = null;
    }
    this.BB_VoiceCount = 0;
    this.BB_Voice = null;
  }
  
  void BB_ResetAllVoices() {
    if (null != this.BB_Voice) {
      for (int i = 0; i < this.BB_VoiceCount; ++i) {
        this.BB_Voice[i].CurEntry = 0;
      }
    }
  }
  
  double volumeFactor(int currentSample, int fadeSampleCount, int totalSampleCount) {
    if (currentSample < fadeSampleCount) {
      // We are in the fade-in phase
      return (double)currentSample / fadeSampleCount;
    } else if (currentSample > totalSampleCount - fadeSampleCount) {
      // We are in the fade-out phase
      return (double)(totalSampleCount - currentSample) / fadeSampleCount;
    } else {
      // We are in the full volume phase
      return 1.0;
    }
  }
  
  double adsrVolume(int currentSample, int totalSamples, double attack, double decay, double sustain, double release) {
    double attackSamples = totalSamples * attack;
    double decaySamples = totalSamples * decay;
    double releaseSamples = totalSamples * release;
    double sustainSamples = totalSamples - (attackSamples + decaySamples + releaseSamples);
    
    if (currentSample < attackSamples) {
      // We are in the attack phase
      return (double)currentSample / attackSamples;
    } else if (currentSample < attackSamples + decaySamples) {
      // We are in the decay phase
      double decayProgress = (currentSample - attackSamples) / decaySamples;
      return 1.0 - ((1.0 - sustain) * decayProgress);
    } else if (currentSample < attackSamples + decaySamples + sustainSamples) {
      // We are in the sustain phase
      return sustain;
    } else {
      // We are in the release phase
      double releaseProgress = (currentSample - (attackSamples + decaySamples + sustainSamples)) / releaseSamples;
      return sustain * (1.0 - releaseProgress);
    }
  }
  
  void BB_MainLoop(byte[] pSoundBuffer, int bufferLen) {
    int pSoundBufferIndex = 0;
    long nbSample = bufferLen >> 2;
    int k = 0;
    int fadeSampleCount = 0;
    // Fill sound buffer; do everything in this loop for every sample in pSoundBuffer to be filled
    while ((long) k < nbSample) {
      --updateperiod;
      double sumR = 0.0;
      double sumL = 0.0;
      if (!BB_PauseFlag && null != BB_Voice) {
        for (int voice = 0; voice < BB_VoiceCount; ++voice) {
          double sampleR = 0.0;
          double sampleL = 0.0;
          BB_InCriticalLoopFlag = true;
          // Periodic stuff (the stuff NOT done every cycle)
          if (updateperiod == 0) {
            while (BB_Voice[voice].CurEntry >= BB_Voice[voice].EntryCount) {
              BB_ResetAllVoices();
            }
            // First figure out which Entry we're at for this voice.
            // See if totalsamples is LESS than CurEntry (very rare event)
            while (BB_CurrentSampleCount < BB_Voice[voice].Entry[BB_Voice[voice].CurEntry].AbsoluteStart_samples) {
              BB_InfoFlag |= BB_NEWENTRY;
              --BB_Voice[voice].CurEntry;
              if (BB_Voice[voice].CurEntry >= 0) continue;
              BB_Voice[voice].CurEntry = 0;
            }
            // Now see if totalsamples is Greater than CurEntry (common event)
            while (BB_CurrentSampleCount > BB_Voice[voice].Entry[BB_Voice[voice].CurEntry].AbsoluteEnd_samples) {
              BB_InfoFlag |= BB_NEWENTRY;
              ++BB_Voice[voice].CurEntry;
              if (BB_Voice[voice].CurEntry < BB_Voice[voice].EntryCount) continue;
              BB_CurrentSampleCountLooped += BB_CurrentSampleCount;
              BB_CurrentSampleCount = 0L;
              BB_ResetAllVoices();
              BB_InfoFlag |= BB_NEWLOOP;
              if (--BB_LoopCount != 0) break;
              BB_InfoFlag |= BB_COMPLETED; // done
              break;
            }
            // housecleaning is done, start signal processing
            if (0 == BB_Voice[voice].mute) {
              int entry = BB_Voice[voice].CurEntry;
              // come up with a factor describing exact point in the schedule by dividing exact point in period by total period time
              double factor = 0.0 != BB_Voice[voice].Entry[entry].duration ? (double) (BB_CurrentSampleCount - BB_Voice[voice].Entry[entry].AbsoluteStart_samples) / (BB_Voice[voice].Entry[entry].duration * (double) BB_AUDIOSAMPLERATE) : 0.0;
              if (0 == BB_Voice[voice].ManualVolumeControl) {
                BB_Voice[voice].CurVolL = BB_Voice[voice].Entry[entry].volL_spread * factor + BB_Voice[voice].Entry[entry].volL_start;
                BB_Voice[voice].CurVolR = BB_Voice[voice].Entry[entry].volR_spread * factor + BB_Voice[voice].Entry[entry].volR_start;
              }
              switch (BB_Voice[voice].type) {
                case BB_VOICETYPE_BINAURALBEAT -> {
                  // determine base frequency to be used for this slice
                  if (0 == BB_Voice[voice].ManualFreqBaseControl) {
                    BB_Voice[voice].cur_basefreq = BB_Voice[voice].Entry[entry].basefreq_spread * factor + BB_Voice[voice].Entry[entry].basefreq_start;
                  }
                  BB_Voice[voice].cur_beatfreqL_factor = BB_Voice[voice].cur_beatfreqR_factor = BB_Voice[voice].Entry[entry].beatfreq_spread_HALF * factor;
                  double old_beatfreq = BB_Voice[voice].cur_beatfreq;
                  if (0 == BB_Voice[voice].ManualFreqBeatControl) {
                    BB_Voice[voice].cur_beatfreq = (BB_Voice[voice].cur_beatfreqL_factor + BB_Voice[voice].Entry[entry].beatfreq_start_HALF) * 2.0;
                  }
                  if (0 == BB_Voice[voice].ManualFreqBeatControl) {
                    BB_Voice[voice].cur_beatfreqL_factor = (BB_Voice[voice].cur_basefreq + BB_Voice[voice].Entry[entry].beatfreq_start_HALF + BB_Voice[voice].cur_beatfreqL_factor) * BB_SAMPLE_FACTOR;
                    BB_Voice[voice].cur_beatfreqR_factor = (BB_Voice[voice].cur_basefreq - BB_Voice[voice].Entry[entry].beatfreq_start_HALF - BB_Voice[voice].cur_beatfreqR_factor) * BB_SAMPLE_FACTOR;
                  } else {
                    BB_Voice[voice].cur_beatfreqL_factor = (BB_Voice[voice].cur_basefreq + BB_Voice[voice].cur_beatfreq) * BB_SAMPLE_FACTOR;
                    BB_Voice[voice].cur_beatfreqR_factor = (BB_Voice[voice].cur_basefreq - BB_Voice[voice].cur_beatfreq) * BB_SAMPLE_FACTOR;
                  }
                  if (BB_Voice[voice].cur_beatfreq < 1.0E-4) {
                    BB_Voice[voice].cur_beatfreq = 1.0E-4;
                  }
                  if (old_beatfreq == BB_Voice[voice].cur_beatfreq) break;
                  double phasefactor = (double) BB_Voice[voice].cur_beatfreq_phasesamplecount / (double) BB_Voice[voice].cur_beatfreq_phasesamplecount_start;
                  BB_Voice[voice].cur_beatfreq_phasesamplecount_start = (int) (BB_AUDIOSAMPLERATE_HALF / BB_Voice[voice].cur_beatfreq);
                  BB_Voice[voice].cur_beatfreq_phasesamplecount = (int) ((double) BB_Voice[voice].cur_beatfreq_phasesamplecount_start * phasefactor);
                }
                case BB_VOICETYPE_PINKNOISE -> {}
                case BB_VOICETYPE_PCM -> {
                  if (null == BB_Voice[voice].PCM_samples) break;
                  BB_Voice[voice].PCM_samples_currentcount = (int) BB_CurrentSampleCount;
                  if (BB_Voice[voice].PCM_samples_currentcount < BB_Voice[voice].PCM_samples.length >> 2)
                    break;
                  BB_Voice[voice].PCM_samples_currentcount = (int) (BB_CurrentSampleCount % (long) (BB_Voice[voice].PCM_samples.length >> 2));
                }
                case BB_VOICETYPE_ISOPULSE, BB_VOICETYPE_ISOPULSE_STAGGERED, BB_VOICETYPE_ISOPULSE_ALTERNATING -> {
                  // get base frequency for this slice
                  BB_Voice[voice].cur_basefreq = BB_Voice[voice].Entry[entry].basefreq_spread * factor + BB_Voice[voice].Entry[entry].basefreq_start;
                  // Now get current beatfreq in Hz for this slice (HALF works because period alternates silence and tone for isochronic tones)
                  double old_beatfreq = BB_Voice[voice].cur_beatfreq;
                  BB_Voice[voice].cur_beatfreq = (BB_Voice[voice].Entry[entry].beatfreq_spread_HALF * factor + BB_Voice[voice].Entry[entry].beatfreq_start_HALF) * 2.0;
                  if (BB_Voice[voice].cur_beatfreq < 1.0E-4) {
                    BB_Voice[voice].cur_beatfreq = 1.0E-4;
                  }
                  // set both channels to the same base frequency (BB_SAMPLE_FACTOR == 2*PI/sample_rate)
                  BB_Voice[voice].cur_beatfreqR_factor = BB_Voice[voice].cur_beatfreqL_factor = BB_Voice[voice].cur_basefreq * BB_SAMPLE_FACTOR;
                  // if this is a new beatfreq, adjust phase accordingly
                  if (old_beatfreq == BB_Voice[voice].cur_beatfreq) break;
                  double phasefactor = (double) BB_Voice[voice].cur_beatfreq_phasesamplecount / (double) BB_Voice[voice].cur_beatfreq_phasesamplecount_start;
                  BB_Voice[voice].cur_beatfreq_phasesamplecount_start = (int) (BB_AUDIOSAMPLERATE_HALF / BB_Voice[voice].cur_beatfreq);
                  BB_Voice[voice].cur_beatfreq_phasesamplecount = (int) ((double) BB_Voice[voice].cur_beatfreq_phasesamplecount_start * phasefactor);
                  // fade in and fade out duration in samples, based on percentage of samples in tone (don't set to more than 0.5)
                  fadeSampleCount = (int)(0.3819660113 * BB_Voice[voice].cur_beatfreq_phasesamplecount_start);
                }
                case BB_VOICETYPE_WATERDROPS, BB_VOICETYPE_RAIN -> {
                  if (null == BB_Voice[voice].Drop) {
                    BB_WaterVoiceInit(voice);
                  }
                  BB_Voice[voice].cur_basefreq = BB_Voice[voice].Entry[entry].basefreq_spread * factor + BB_Voice[voice].Entry[entry].basefreq_start;
                  if (BB_AUDIOSAMPLERATE == 44100) break;
                  BB_Voice[voice].cur_basefreq = BB_Voice[voice].cur_basefreq * 44100.0 / (double) BB_AUDIOSAMPLERATE;
                }
              }
            }
          }
          // high priority calculations (done for every sample)
          if (0 != BB_Voice[voice].mute) continue;
          switch (BB_Voice[voice].type) {
            case BB_VOICETYPE_BINAURALBEAT -> {
              BB_Voice[voice].sinPosL += BB_Voice[voice].cur_beatfreqL_factor;
              if (BB_Voice[voice].sinPosL >= BB_TWO_PI) {
                BB_Voice[voice].sinPosL -= BB_TWO_PI;
              }
              BB_Voice[voice].sinPosR += BB_Voice[voice].cur_beatfreqR_factor;
              if (BB_Voice[voice].sinPosR >= BB_TWO_PI) {
                BB_Voice[voice].sinPosR -= BB_TWO_PI;
              }
              BB_Voice[voice].sinL = Math.sin(BB_Voice[voice].sinPosL);
              sampleL = BB_Voice[voice].sinL * BB_SIN_SCALER;
              BB_Voice[voice].sinR = Math.sin(BB_Voice[voice].sinPosR);
              sampleR = BB_Voice[voice].sinR * BB_SIN_SCALER;
              if (--BB_Voice[voice].cur_beatfreq_phasesamplecount < 1) { // completed one beat, flip the flag
                BB_Voice[voice].cur_beatfreq_phasesamplecount = BB_Voice[voice].cur_beatfreq_phasesamplecount_start;
                BB_Voice[voice].cur_beatfreq_phaseflag = !BB_Voice[voice].cur_beatfreq_phaseflag; // seems to be unnecessary here
                BB_Voice[voice].cur_beatfreq_phaseenvelope = 0.0;
              }
            }
            case BB_VOICETYPE_PINKNOISE -> {
              BB_Voice[voice].noiseL = BB_Voice[voice].noiseL * 31 + (rand() >> 15) >> 5;
              sampleL = BB_Voice[voice].noiseL;
              BB_Voice[voice].noiseR = BB_Voice[voice].noiseR * 31 + (rand() >> 15) >> 5;
              sampleR = BB_Voice[voice].noiseR;
            }
            case BB_VOICETYPE_PCM -> {
              if (null == BB_Voice[voice].PCM_samples) break;
              if (BB_Voice[voice].PCM_samples_currentcount >= BB_Voice[voice].PCM_samples.length >> 2) {
                BB_Voice[voice].PCM_samples_currentcount = (int) (BB_CurrentSampleCount % (long) (BB_Voice[voice].PCM_samples.length >> 2));
              }
              int bytecount = BB_Voice[voice].PCM_samples_currentcount << 2;
              sampleL = (short) (BB_Voice[voice].PCM_samples[bytecount] & 0xFF | BB_Voice[voice].PCM_samples[bytecount + 1] << 8);
              sampleR = (short) (BB_Voice[voice].PCM_samples[bytecount + 2] & 0xFF | BB_Voice[voice].PCM_samples[bytecount + 3] << 8);
              ++BB_Voice[voice].PCM_samples_currentcount;
            }
            case BB_VOICETYPE_ISOPULSE, BB_VOICETYPE_ISOPULSE_STAGGERED -> {
              boolean iso_stag = BB_Voice[voice].type == BB_VOICETYPE_ISOPULSE_STAGGERED;
  
              // advance to the next sample for each channel
              BB_Voice[voice].sinPosL += BB_Voice[voice].cur_beatfreqL_factor;
              if (BB_Voice[voice].sinPosL >= BB_TWO_PI) {
                BB_Voice[voice].sinPosL -= BB_TWO_PI;
              }
              BB_Voice[voice].sinPosR = BB_Voice[voice].sinPosL;
              // this cycle complete, now determine whether tone or silence and flip the phase flag
              if (--BB_Voice[voice].cur_beatfreq_phasesamplecount < 1) {
                BB_Voice[voice].cur_beatfreq_phasesamplecount = BB_Voice[voice].cur_beatfreq_phasesamplecount_start;
                BB_Voice[voice].cur_beatfreq_phaseflag = !BB_Voice[voice].cur_beatfreq_phaseflag;
                leftRightChannelSwitch = leftRightChannelSwitch ^ BB_Voice[voice].cur_beatfreq_phaseflag;
              }
              // calc sine for this sample
              BB_Voice[voice].sinR = BB_Voice[voice].sinL = Math.sin(BB_Voice[voice].sinPosL);
              // normal iso and iso-staggered
              if (BB_Voice[voice].cur_beatfreq_phaseflag) { // phaseflag is true = tone off
                sampleL = 0;
                sampleR = iso_stag ? BB_Voice[voice].sinL * BB_SIN_SCALER : sampleL;
              } else { // phaseflag is false = tone on
                sampleL = BB_Voice[voice].sinL * BB_SIN_SCALER;
                sampleR = iso_stag ? 0 : sampleL;
              }
              double factor = volumeFactor(BB_Voice[voice].cur_beatfreq_phasesamplecount, fadeSampleCount, BB_Voice[voice].cur_beatfreq_phasesamplecount_start);
              sampleL *= factor;
              sampleR *= factor;
            }
            case BB_VOICETYPE_ISOPULSE_ALTERNATING -> {
              // advance to the next sample for each channel
              BB_Voice[voice].sinPosL += BB_Voice[voice].cur_beatfreqL_factor;
              if (BB_Voice[voice].sinPosL >= BB_TWO_PI) {
                BB_Voice[voice].sinPosL -= BB_TWO_PI;
              }
              
              BB_Voice[voice].sinPosR = BB_Voice[voice].sinPosL;
              // this cycle complete, now determine whether tone or silence and flip the phase flag
              if (--BB_Voice[voice].cur_beatfreq_phasesamplecount < 1) {
                BB_Voice[voice].cur_beatfreq_phasesamplecount = BB_Voice[voice].cur_beatfreq_phasesamplecount_start;
                BB_Voice[voice].cur_beatfreq_phaseflag = !BB_Voice[voice].cur_beatfreq_phaseflag;
                leftRightChannelSwitch = leftRightChannelSwitch ^ BB_Voice[voice].cur_beatfreq_phaseflag;
              }
              // envelope
              int totalSamples = BB_Voice[voice].cur_beatfreq_phasesamplecount_start * 2;
              int currentSample;
              if (BB_Voice[voice].cur_beatfreq_phaseflag)
                currentSample = 2*BB_Voice[voice].cur_beatfreq_phasesamplecount_start-BB_Voice[voice].cur_beatfreq_phasesamplecount;
              else
                currentSample = BB_Voice[voice].cur_beatfreq_phasesamplecount_start-BB_Voice[voice].cur_beatfreq_phasesamplecount;
              
              double attack = 1-phe;
              double decay = phe;
              double sustain = 0;
              double release = 0;
              double envelope = adsrVolume(
                      currentSample,
                      totalSamples,
                      attack,
                      decay,
                      sustain,
                      release);
  
              if (BB_Voice[voice].cur_beatfreq_phaseflag == leftRightChannelSwitch)
                sampleR = envelope * Math.sin(BB_Voice[voice].sinPosR) * BB_SIN_SCALER;
              else
                sampleL = envelope * Math.sin(BB_Voice[voice].sinPosL) * BB_SIN_SCALER;
            }
  
            case BB_VOICETYPE_WATERDROPS -> {
              BB_Water(voice, BB_DropMother, BB_DROPLEN, BB_DropLowcut);
              sampleL = BB_Voice[voice].WaterL;
              sampleR = BB_Voice[voice].WaterR;
            }
            case BB_VOICETYPE_RAIN -> {
              BB_Water(voice, BB_RainMother, 44, BB_RainLowcut);
              sampleL = BB_Voice[voice].WaterL;
              sampleR = BB_Voice[voice].WaterR;
            }
          }
          if (0 == BB_Voice[voice].mono) { // stereo
            sumL += sampleL * BB_Voice[voice].CurVolL;
            sumR += sampleR * BB_Voice[voice].CurVolR;
          } else { // mono
            sampleL = (sampleL + sampleR) * 0.5;
            sumL += sampleL * BB_Voice[voice].CurVolL;
            sumR += sampleL * BB_Voice[voice].CurVolR;
          }
        }
      }
      BB_InCriticalLoopFlag = false;
      if (BB_Mono != 0) sumR = sumL = 0.5 * (sumL + sumR);
      
      if (BB_VolumeOverall_left != 1.0) sumL *= BB_VolumeOverall_left;
      if (BB_VolumeOverall_right != 1.0) sumR *= BB_VolumeOverall_right;
      
      if (0 == BB_StereoSwap) {
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumL & 0xFF);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumL >> 8);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumR & 0xFF);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumR >> 8);
      } else {
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumR & 0xFF);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumR >> 8);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumL & 0xFF);
        pSoundBuffer[pSoundBufferIndex++] = (byte) ((short) sumL >> 8);
      }
      if (updateperiod == 0) {
        updateperiod = 16;
        BB_CurrentSampleCount += 16L;
        if ((double) BB_OutputL < sumL) {
          BB_OutputL = (int) sumL;
        }
        if ((double) BB_OutputR < sumR) {
          BB_OutputR = (int) sumR;
        }
      }
      ++k;
    }
  }
  
  
  void SeedRand(int i1, int i2) {
    if (i2 == -1) {
      i2 = i1 + 1;
    }
    mcgn = (long) i1 == 0L ? 0L : (long) i1 | 1L;
    srgn = (long) i2 == 0L ? 0L : (long) i2 & 0x7FFL | 1L;
  }
  
  int rand() {
    long r0 = srgn >> 15;
    long r1 = srgn ^ r0;
    r0 = r1 << 17;
    srgn = r0 ^ r1;
    mcgn = 69069L * mcgn;
    r1 = mcgn ^ srgn;
    return (int) r1;
  }
  
  void BB_ProcessVolBal() {
    BB_VolumeOverall_right = BB_VolumeOverall_left = BB_OverallVolume;
    if (BB_OverallBalance > 0.0) {
      BB_VolumeOverall_left *= 1.0 - Math.abs(BB_OverallBalance);
    } else {
      BB_VolumeOverall_right *= 1.0 - Math.abs(BB_OverallBalance);
    }
  }
  
  void BB_SetBalance(double range) {
    BB_OverallBalance = range;
    BB_ProcessVolBal();
  }
  
  void BB_SetVolume(double range) {
    BB_OverallVolume = range;
    BB_ProcessVolBal();
  }
  
  void BB_Reset() {
    this.BB_CurrentSampleCountLooped = 0L;
    this.BB_CurrentSampleCount = 0L;
    this.BB_InfoFlag &= 0xFFFFFFFFFFFFFFFEL;
    this.BB_LoopCount = this.BB_Loops;
  }
  
  double BB_GetTopBeatfreq(int voice) {
    double top_beatfreq = 0.0;
    for (int e = 0; e < this.BB_Voice[voice].EntryCount; ++e) {
      if (!(top_beatfreq < Math.abs(this.BB_Voice[voice].Entry[e].beatfreq_start_HALF))) continue;
      top_beatfreq = Math.abs(this.BB_Voice[voice].Entry[e].beatfreq_start_HALF);
    }
    return top_beatfreq;
  }
  
  void BB_NullAllPCMData() {
    for (int i = 0; i < this.BB_VoiceCount; ++i) {
      this.BB_Voice[i].PCM_samples = null;
      this.BB_Voice[i].PCM_samples_currentcount = 0;
    }
  }
  
  void BB_ToneRain(short[] array, float pitch) {
    double p = 0.0;
    double q = 1.0 / (double) pitch;
    double r = 0.0;
    double s = 32767.0 / (double) array.length;
    for (int i = 0; i < array.length; ++i) {
      array[i] = (short) (r * Math.sin(p * BB_TWO_PI));
      p += q;
      r += s;
    }
  }
  
  void BB_WaterVoiceInit(int voice) {
    if (null == this.BB_RainMother || null == this.BB_DropMother) {
      this.BB_SetupWaterMothers();
    }
    if (null == this.BB_Voice[voice].Drop) {
      this.BB_Voice[voice].Dropcount = (int) (this.BB_Voice[voice].Entry[0].beatfreq_start_HALF * 2.0);
      if (1 > this.BB_Voice[voice].Dropcount) {
        this.BB_Voice[voice].Dropcount = 2;
      }
      if (100 < this.BB_Voice[voice].Dropcount) {
        this.BB_Voice[voice].Dropcount = 100;
      }
      this.BB_Voice[voice].Drop = new BB_Waterdrop[this.BB_Voice[voice].Dropcount];
      for (int j = 0; j < this.BB_Voice[voice].Dropcount; ++j) {
        this.BB_Voice[voice].Drop[j] = new BB_Waterdrop();
      }
      for (int i = 0; i < this.BB_Voice[voice].Dropcount; ++i) {
        this.BB_Voice[voice].Drop[i].count = -1.0f;
        this.BB_Voice[voice].Drop[i].decrement = 1.0f;
        this.BB_Voice[voice].Drop[i].stereoMix = 0.5f;
      }
    }
  }
  
  short[] BB_WaterInit(int arraylen, float pitch) {
    short[] array = new short[arraylen];
    double p = 0.0;
    double q = 1.0 / (double) pitch;
    double r = 0.0;
    double s = 32767.0 / (double) arraylen;
    for (int i = 0; i < arraylen; ++i) {
      array[i] = (short) (r * Math.sin(p * BB_TWO_PI));
      p += q;
      r += s;
    }
    return array;
  }
  
  void BB_Water(int voice, short[] mother_array, int arraylen, float Lowcut) {
    if (null == mother_array) {
      return;
    }
    if (null == this.BB_Voice[voice].Drop) {
      return;
    }
    int drop_index = this.BB_Voice[voice].Dropcount;
    int mixL = 0;
    int mixR = 0;
    while (0 < drop_index) {
      int mother_index = (int) (this.BB_Voice[voice].Drop[drop_index].count -= this.BB_Voice[voice].Drop[--drop_index].decrement);
      if (mother_array.length <= mother_index) {
        mother_index = 0;
        this.BB_Voice[voice].Drop[drop_index].count = 0;
      }
      if (-1 < mother_index) {
        if (0 != this.BB_Voice[voice].mono) {
          mixL += mother_array[mother_index];
          mixR += mother_array[mother_index];
          continue;
        }
        mixL += (int) ((float) mother_array[mother_index] * this.BB_Voice[voice].Drop[drop_index].stereoMix);
        mixR += (int) ((float) mother_array[mother_index] * (1.0f - this.BB_Voice[voice].Drop[drop_index].stereoMix));
        continue;
      }
      if (!(this.BB_Voice[voice].cur_basefreq > Math.random())) continue;
      this.BB_Voice[voice].Drop[drop_index].count = arraylen;
      this.BB_Voice[voice].Drop[drop_index].decrement = (float) (Math.random() * (double) this.BB_WaterWindow + (double) Lowcut);
      this.BB_Voice[voice].Drop[drop_index].stereoMix = (float) Math.random();
    }
    this.BB_Voice[voice].WaterL = this.BB_Voice[voice].noiseL = this.BB_Voice[voice].noiseL * 31 + mixL >> 5;
    this.BB_Voice[voice].WaterR = this.BB_Voice[voice].noiseR = this.BB_Voice[voice].noiseR * 31 + mixR >> 5;
  }
  
  void BB_SetupWaterMothers() {
    if (null == this.BB_DropMother) {
      this.BB_DropMother = this.BB_WaterInit(BB_DROPLEN, 600.0f);
    }
    if (null == this.BB_RainMother) {
      this.BB_RainMother = this.BB_WaterInit(44, 3.4f);
    }
  }
}