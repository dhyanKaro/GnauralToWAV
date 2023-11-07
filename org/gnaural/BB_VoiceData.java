package org.gnaural;

class BB_VoiceData {
  int id;
  
  int type;
  
  int mute;
  
  int mono;
  
  int hide;
  
  double TotalDuration;
  
  int EntryCount;
  
  int CurEntry;
  
  BB_EventData[] Entry;
  
  double CurVolL;
  
  double CurVolR;
  
  double cur_basefreq;
  
  double cur_beatfreq;
  
  double cur_beatfreqL_factor;
  
  double cur_beatfreqR_factor;
  
  int cur_beatfreq_phasesamplecount;
  
  int cur_beatfreq_phasesamplecount_start;
  
  boolean cur_beatfreq_phaseflag;
  
  double cur_beatfreq_phaseenvelope;
  
  double sinPosL;
  
  double sinPosR;
  
  double sinL;
  
  double sinR;
  
  int noiseL;
  
  int noiseR;
  
  int Dropcount;
  
  int WaterL;
  
  int WaterR;
  
  byte[] PCM_samples;
  
  int PCM_samples_currentcount;
  
  BB_Waterdrop[] Drop;
  
  int ManualFreqBeatControl;
  
  int ManualFreqBaseControl;
  
  int ManualVolumeControl;
  
  String Description;
}
