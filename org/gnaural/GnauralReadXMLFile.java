/*
 * Bret Logan 20110412
 * The philosophy of Gnaural XML is to be very simple:
 * every kind of gnauralfile element name is unique, so
 * whether it is passed as an attribute or a tag, it just
 * gets thrown at one big function as a name with a value,
 *  and BB sorts out the rest.
 *  This is a Sax XML parser, doing three-passes on the
 *  file. First is to count voices, so it can allot space
 *  for the voices, second is to count entries in each voice,
 *  so it can allot each voice's entries, and third is to
 *  actually read the values in the file itself to put
 *  directly in to the BB engine.
 */

package org.gnaural;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class GnauralReadXMLFile extends DefaultHandler {
  static final String GNAURAL_XML_VERSION = "1.20101006";
  BinauralBeatSoundEngine BB;
  int TotalVoiceCount;
  int TotalEntryCount;
  int CurVox;
  int CurEntry;
  String CurElement = "";
  boolean success;
  boolean FINAL_PASS;
  String voice_desc = "";
  File mFile;
  public GnauralReadXMLFile(String xmlFile, BinauralBeatSoundEngine theBB) {
    this.BB = null;
    this.success = false;
    SAXParserFactory factory;
    SAXParser parser = null;
    DefaultHandler handler;
    this.mFile = null;
    factory = SAXParserFactory.newInstance();
    try {
      parser = factory.newSAXParser();
    } catch (ParserConfigurationException | SAXException ignored) {
    }
    if (parser == null) {
      System.err.println("Parser is null!");
      System.exit(5);
    }
    handler = this;
    this.mFile = new File(xmlFile);
    if (!this.mFile.exists()) {
      System.out.println("File not found!");
      this.success = false;
      System.exit(10);
    }
    // 1st pass
    this.FINAL_PASS = false;
    this.TotalVoiceCount = 0;
    this.TotalEntryCount = 0;
    this.CurVox = 0;
    this.CurEntry = 0;
    try {
      parser.parse(this.mFile, handler);
    } catch (SAXException | java.io.IOException ignored) {
    }
    this.BB = theBB;
    this.BB.BB_InitVoices(this.TotalVoiceCount);
    // 2nd pass
    this.FINAL_PASS = false;
    this.BB.BB_TotalDuration = 0.0D;
    this.TotalVoiceCount = 0;
    this.TotalEntryCount = 0;
    this.CurVox = 0;
    this.CurEntry = 0;
    try {
      parser.parse(this.mFile, handler);
    } catch (SAXException | java.io.IOException ignored) { }
    System.out.println("Voices: " + this.TotalVoiceCount + " Entries: " + this.TotalEntryCount);
    // 3rd pass
    this.FINAL_PASS = true;
    this.BB.BB_TotalDuration = 0.0D;
    this.TotalVoiceCount = 0;
    this.TotalEntryCount = 0;
    this.CurVox = 0;
    this.CurEntry = 0;
    try {
      parser.parse(this.mFile, handler);
    } catch (SAXException | java.io.IOException ignored) { }
    this.BB.BB_CalibrateVoices();
    this.success = true;
    this.BB.BB_PauseFlag = false;
    // print voice types
    for (BB_VoiceData voice: BB.BB_Voice) {
      int voiceType = voice.type;
      String voiceLabel = (voiceType >= 0 && voiceType < BinauralBeatSoundEngine.BB_VoiceTypes.length) ?
              BinauralBeatSoundEngine.BB_VoiceTypes[voiceType] : "Unknown";
      System.out.println("  Voice Type: " + voiceType + " - " + voiceLabel);
    }
  }
  
  public void GnauralTextSorter(String name, String value) {
    if (null == this.BB || !this.FINAL_PASS) return;
    if ("duration".equals(name)) {
      ((this.BB.BB_Voice[this.CurVox]).Entry[this.CurEntry]).duration = Float.parseFloat(value);
      return;
    }
    if ("volume_left".equals(name)) {
      ((this.BB.BB_Voice[this.CurVox]).Entry[this.CurEntry]).volL_start = Float.parseFloat(value);
      return;
    }
    if ("volume_right".equals(name)) {
      ((this.BB.BB_Voice[this.CurVox]).Entry[this.CurEntry]).volR_start = Float.parseFloat(value);
      return;
    }
    if ("beatfreq".equals(name)) {
      ((this.BB.BB_Voice[this.CurVox]).Entry[this.CurEntry]).beatfreq_start_HALF = 0.5D * Float.parseFloat(value);
      return;
    }
    if ("basefreq".equals(name)) {
      ((this.BB.BB_Voice[this.CurVox]).Entry[this.CurEntry]).basefreq_start = Float.parseFloat(value);
      return;
    }
    if ("id".equals(name)) {
      (this.BB.BB_Voice[this.CurVox]).id = this.CurVox;
      return;
    }
    if ("type".equals(name)) {
      (this.BB.BB_Voice[this.CurVox]).type = Integer.parseInt(value);
      return;
    }
    if ("entrycount".equals(name)) return;
    if ("voicecount".equals(name)) return;
    if ("totalentrycount".equals(name)) return;
    if ("loops".equals(name)) {
      this.BB.BB_Loops = this.BB.BB_LoopCount = Integer.parseInt(value);
      return;
    }
    if ("overallvolume_left".equals(name)) {
      this.BB.BB_VolumeOverall_left = Float.parseFloat(value);
      if (this.BB.BB_VolumeOverall_right <= this.BB.BB_VolumeOverall_left) {
        this.BB.BB_OverallVolume = this.BB.BB_VolumeOverall_left;
        this.BB.BB_OverallBalance = 0.0D;
        if (this.BB.BB_VolumeOverall_left != 0.0D)
          this.BB.BB_OverallBalance = -(1.0D - this.BB.BB_VolumeOverall_right / this.BB.BB_VolumeOverall_left);
      }
      return;
    }
    if ("overallvolume_right".equals(name)) {
      this.BB.BB_VolumeOverall_right = Float.parseFloat(value);
      if (this.BB.BB_VolumeOverall_right >= this.BB.BB_VolumeOverall_left) {
        this.BB.BB_OverallVolume = this.BB.BB_VolumeOverall_right;
        this.BB.BB_OverallBalance = 0.0D;
        if (this.BB.BB_VolumeOverall_right != 0.0D)
          this.BB.BB_OverallBalance = 1.0D - this.BB.BB_VolumeOverall_left / this.BB.BB_VolumeOverall_right;
      }
      return;
    }
    if ("stereoswap".equals(name)) {
      this.BB.BB_StereoSwap = Integer.parseInt(value);
      return;
    }
    if ("voice_mute".equals(name)) {
      (this.BB.BB_Voice[this.CurVox]).mute = Integer.parseInt(value);
      return;
    }
    if ("voice_mono".equals(name)) {
      (this.BB.BB_Voice[this.CurVox]).mono = Integer.parseInt(value);
      return;
    }
    if ("voice_state".equals(name)) return;
    if ("voice_hide".equals(name)) {
      (this.BB.BB_Voice[this.CurVox]).hide = Integer.parseInt(value);
      return;
    }
    if ("gnauralfile_version".equals(name)) {
      if (GNAURAL_XML_VERSION.equals(value)) {
        System.out.println("Usable file version, " + value);
      } else {
        System.out.println("Unknown File Version, expected " + GNAURAL_XML_VERSION + ", got " + value);
      }
      return;
    }
    if ("title".equals(name)) {
      this.BB.mTitle = value;
      return;
    }
    if ("schedule_description".equals(name)) {
      this.BB.mDescription = value;
      return;
    }
    if ("author".equals(name)) {
      this.BB.mAuthor = value;
      return;
    }
    if ("description".equals(name)) {
      this.voice_desc = value;
      (this.BB.BB_Voice[this.CurVox]).Description = value;
    }
  }
  
  public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) {
    this.CurElement = qName;
    if ("entry".equalsIgnoreCase(qName)) {
      this.TotalEntryCount++;
      int count = attrs.getLength();
      if (null != this.BB) for (int i = 0; i < count; i++)
        GnauralTextSorter(attrs.getQName(i), attrs.getValue(i));
      return;
    }
    if ("voice".equalsIgnoreCase(qName)) {
      this.TotalVoiceCount++;
      this.CurEntry = 0;
    }
  }
  
  public void endElement(String namespaceURI, String localName, String qName) {
    if ("entry".equalsIgnoreCase(qName)) {
      this.CurEntry++;
      this.CurElement = "";
      return;
    }
    if ("voice".equalsIgnoreCase(qName)) {
      if (null != this.BB && !this.FINAL_PASS)
        this.BB.BB_SetupVoice(this.CurVox, 0, (this.BB.BB_Voice[this.CurVox]).mute, (this.BB.BB_Voice[this.CurVox]).mono, (this.BB.BB_Voice[this.CurVox]).hide, (this.BB.BB_Voice[this.CurVox]).Description, (this.BB.BB_Voice[this.CurVox]).PCM_samples, this.CurEntry);
      this.CurVox++;
      this.CurEntry = 0;
      this.CurElement = "";
      return;
    }
    this.CurElement = "";
  }
  
  public void characters(char[] ch, int start, int length) {
    if ("".equalsIgnoreCase(this.CurElement)) return;
    GnauralTextSorter(this.CurElement, new String(ch, start, length));
  }
  
  public void startDocument() {
  }
  
  public void endDocument() {
  }
}
