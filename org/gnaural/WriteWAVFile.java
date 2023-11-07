package org.gnaural;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class WriteWAVFile {
  boolean quit = false;
  private final BinauralBeatSoundEngine BB;
  private FileOutputStream fos = null;
  private long WT_FileByteCountTotal = 0L;
  private long WT_FileByteCountCurrent = 0L;
  private final String WT_filename;
  boolean writeSuccess = false;
  
  WriteWAVFile(BinauralBeatSoundEngine theBB, String filename) {
    BB = theBB;
    WT_filename = filename;
    if (BB.BB_Loops < 1) {
      System.err.println("Can't write file if in infinite loop mode (loops=0)");
      System.exit(4);
    }
    if (BB.BB_Loops * BB.BB_TotalDuration < 180.0D) {
      System.out.println("Doing precision short write");
      WriteWAVFile_short shortWriter = new WriteWAVFile_short(BB, WT_filename);
      writeSuccess = shortWriter.writeSuccess;
    }
    else {
      write();
    }
  }
  
  private void write() {
    WT_FileByteCountTotal = ((int) (BB.BB_Loops * BB.BB_TotalDuration * 44100.0D) * 4);
    if (!AT_WriteWAVHeader(WT_filename)) System.exit(9);
    WT_FileByteCountCurrent = 0L;
    byte[] audioBuffer = new byte[4096];
    try {
      while (!quit && WT_FileByteCountCurrent <= WT_FileByteCountTotal) {
        BB.BB_MainLoop(audioBuffer, audioBuffer.length);
        if ((BB.BB_InfoFlag & BinauralBeatSoundEngine.BB_COMPLETED) != 0L) {
          quit = true;
        } else {
          fos.write(audioBuffer, 0, audioBuffer.length);
          WT_FileByteCountCurrent += audioBuffer.length;
          printProgress();
        }
      }
      writeSuccess = true;
    } catch (IOException e) {
      System.err.println("An error occurred while writing to the file.");
      e.printStackTrace();
      writeSuccess = false;
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
        System.err.println("An error occurred while closing the file.");
        e.printStackTrace();
      }
    }
    System.out.println("Done, wrote " + (36L + WT_FileByteCountCurrent) + " bytes");
  }
  
  private int progressCounter = 0;
  private void printProgress() {
    progressCounter++;
    if (progressCounter % 2000 == 0) { // adjust this number as needed
      int percentage = (int) ((WT_FileByteCountCurrent * 100L) / WT_FileByteCountTotal);
      System.out.println("Writing WAV: " + percentage + "%");
      System.out.flush();
    }
  }
  private boolean AT_WriteWAVHeader(String filename) {
    byte[] WAVheader_part1 = {82, 73, 70, 70};
    byte[] WAVheader_part2 = {87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, 2, 0, 68, -84, 0, 0, 16, -79, 2, 0, 4, 0, 16, 0, 100, 97, 116, 97};
    byte[] unsignedintholder = {0, 0, 0, 0};
    System.out.println("Total to write: " + (36L + WT_FileByteCountTotal) + " bytes");
    try {
      fos = new FileOutputStream(filename);
    } catch (FileNotFoundException e) {
      System.exit(11);
    }
    try {
      fos.write(WAVheader_part1);
      unsignedintholder[3] = (byte) (int) (WT_FileByteCountTotal + 36L >> 24L & 0xFFL);
      unsignedintholder[2] = (byte) (int) (WT_FileByteCountTotal + 36L >> 16L & 0xFFL);
      unsignedintholder[1] = (byte) (int) (WT_FileByteCountTotal + 36L >> 8L & 0xFFL);
      unsignedintholder[0] = (byte) (int) (WT_FileByteCountTotal + 36L & 0xFFL);
      fos.write(unsignedintholder, 0, 4);
      fos.write(WAVheader_part2);
      unsignedintholder[3] = (byte) (int) (WT_FileByteCountTotal >> 24L & 0xFFL);
      unsignedintholder[2] = (byte) (int) (WT_FileByteCountTotal >> 16L & 0xFFL);
      unsignedintholder[1] = (byte) (int) (WT_FileByteCountTotal >> 8L & 0xFFL);
      unsignedintholder[0] = (byte) (int) (WT_FileByteCountTotal & 0xFFL);
      fos.write(unsignedintholder, 0, 4);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
}

