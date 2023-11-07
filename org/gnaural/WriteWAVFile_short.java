package org.gnaural;

import java.io.ByteArrayInputStream;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

class WriteWAVFile_short {
  BinauralBeatSoundEngine BB;
  int WT_FileByteCountTotal;
  long WT_FileByteCountCurrent = 0L;
  String WT_filename;
  boolean writeSuccess = false;
  
  WriteWAVFile_short(BinauralBeatSoundEngine bb, String filename) {
    this.BB = bb;
    this.WT_filename = filename;
    if (this.BB.BB_Loops < 1) {
      System.err.println("Can't write file in endless loop mode");
      System.exit(8);
    }
    this.WT_FileByteCountTotal = (int) (this.BB.BB_Loops * this.BB.BB_TotalDuration * 44100.0D) * 4;
    writeWAVFile();
  }
  
  private void writeWAVFile() {
    byte[] pcm_data = new byte[this.WT_FileByteCountTotal];
    this.BB.BB_MainLoop(pcm_data, this.WT_FileByteCountTotal);
    AudioFormat frmt = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
    System.out.println("Audio samples to write:" + (pcm_data.length / frmt.getFrameSize()));
    AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(pcm_data), frmt, (pcm_data.length / frmt.getFrameSize()));
    try {
      this.WT_FileByteCountCurrent = AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(this.WT_filename));
      this.writeSuccess = true;
    } catch (Exception e) {
      e.printStackTrace();
      this.writeSuccess = false;
    }
    System.out.println("WAVWriteFile_short Done, wrote " + this.WT_FileByteCountCurrent + " bytes");
  }
}

