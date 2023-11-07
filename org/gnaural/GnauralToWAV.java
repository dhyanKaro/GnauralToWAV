package org.gnaural;

import java.io.File;

public class GnauralToWAV {
  private final BinauralBeatSoundEngine BB;
  private WriteWAVFile WriteWF = null;
  private static final int SAMPLE_RATE = 44100;
  public GnauralToWAV() {
    this.BB = new BinauralBeatSoundEngine(SAMPLE_RATE);
    this.BB.BB_PauseFlag = false;
  }
  
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Error: Supply one input schedule file");
      System.err.println("Usage: java " + System.getProperty("sun.java.command").split("\\s+")[0] + " <input-file-path>");
      System.exit(7);
    }
    String inputXmlFileName = args[0];
    System.out.println("Input XML File: " + inputXmlFileName);
    String outputWavFileName = inputXmlFileName.replaceFirst("\\.[^.]+$", "") + ".wav";
    System.out.println("Output WAV File: " + outputWavFileName);
    
    
    GnauralToWAV gnauralToWAV = new GnauralToWAV();
    System.out.println("Reading input file...");
    gnauralToWAV.openScheduleFile(inputXmlFileName);
    
    System.out.println("Total Duration: " + gnauralToWAV.BB.BB_TotalDuration + " seconds");
    System.out.println("Writing output file...");
  
    int exitCode = gnauralToWAV.writeWAVFile(outputWavFileName);
    System.exit(exitCode);
  }
  
  public void stopBB() {
    if (null != this.WriteWF) this.WriteWF.quit = true;
    this.BB.BB_Reset();
  }
  
  File setExtension(File file, String ext) {
    if (file.getName().endsWith(ext)) {
      return file;
    }
    System.out.println("Changing filename to " + file + ext);
    return new File(file + ext);
  }
  
  private int writeWAVFile(String filePath) {
    if (this.BB.BB_Loops < 1) {
      System.err.println("Not writing file because schedule is in endless loop mode.");
      return 3;
    }
    if (null != this.WriteWF) {
      System.err.println("Not writing file because one is already being written.");
      return 6;
    }
    File file = new File(filePath);
    file = setExtension(file, ".wav");
    if (file.exists())
      System.out.println("Output file already exists, overwriting...");
    stopBB();
    this.BB.BB_Reset();
    System.out.println("Writing audio file to " + file);
    this.WriteWF = new WriteWAVFile(this.BB, file.getAbsolutePath());
    boolean writeSuccess = this.WriteWF.writeSuccess;
    if (writeSuccess) {
      System.out.println("Audio file written successfully.");
      return 0;
    } else {
      System.err.println("Error writing audio file.");
      return 1;
    }
  }
  
  private void openScheduleFile(String filePath) {
    File file = new File(filePath);
    // Check if the input file exists
    if (!file.exists()) {
      System.err.println("Input file does not exist: " + filePath);
      System.exit(2);
    }
    stopBB();
    new GnauralReadXMLFile(file.getAbsolutePath(), this.BB);
  }
}
