/*
 * Copyright (C) 2014 Craig Thomas
 * This project uses an MIT style license - see LICENSE for details.
 */
package ca.craigthomas.kinecttimelapse.commandline;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.openkinect.freenect.VideoFormat;

import ca.craigthomas.kinecttimelapse.kinect.Monitor;
import ca.craigthomas.kinecttimelapse.kinect.VideoFrame;

/**
 * 
 *  
 * @author thomas
 */
public class Runner {

    // The name of the generated class
    private static final String PROGRAM_NAME = "KinectTimeLapse-0.1.jar";
    // The logger for the class
    private final static Logger LOGGER = Logger.getLogger(Runner.class.getName());
    // Used to generate filenames for each picture taken
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    // Used to convert the current time into the filename string
    private DateFormat dateFormat;

    @Option(name="-i", usage="takes images using the IR camera")
    private boolean useIRCamera = false;
    
    @Option(name="-n", usage="the number of pictures to take (default 1, 0 = continuous)")
    private int numPictures = 1;
    
    @Option(name="-d", usage="delay in seconds between pictures (default 0)")
    private int delay = 0;
    
    @Option(name="-p", usage="location to store generated images")
    private String path = "./";
    
    public Runner() {
         dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);        
    }
    
    /**
     * Save the snapshot to the directory. The filename will be the current
     * date and time stamp, plus the extension of 'jpg'.
     * 
     * @param snapshot the snapshot data to save
     * @param directory the directory to save to
     */
    private void saveSnapshot(BufferedImage snapshot, File directory) {
        String filename = dateFormat.format(new Date()) + ".jpg";
        File file = new File(directory, filename);
        try {
            ImageIO.write(snapshot, "jpg", file);
        } catch (IOException e) {
            LOGGER.severe("IO exception: " + e.getMessage());
        }        
    }
    
    public void mainLoop() {
        VideoFormat videoFormat = useIRCamera == true ? VideoFormat.IR_8BIT : VideoFormat.RGB;
        Monitor mFreenectMonitor = new Monitor();

        File directory = new File(path);
        if (!directory.isDirectory()) {
            LOGGER.log(Level.SEVERE, "Error: path [" + path + "] is not a directory");
            return;
        }
        
        LOGGER.log(Level.INFO, "Taking " + numPictures + " picture(s)");
        
        for (int counter = 0; counter < numPictures; counter++) {
            LOGGER.log(Level.INFO, "Taking snapshot (" + (counter+1) + " of " + numPictures + ")");
            VideoFrame videoFrame = mFreenectMonitor.takeSnapshot(videoFormat);
            saveSnapshot(videoFrame.getBufferedImage(), directory);
            sleep(delay);
        }
        
        LOGGER.log(Level.INFO, "Execution complete");
    }
    
    /**
     * Pause execution for the specified number of seconds.
     * 
     * @param seconds the number of seconds to sleep for
     */
    private void sleep(int seconds) {
        LOGGER.log(Level.INFO, "Sleeping for " + seconds + " second(s)");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOGGER.warning("Sleep interruped");
        }        
    }

    public static void main(String [] argv) throws CmdLineException {
        Runner runner = new Runner();
        CmdLineParser parser = new CmdLineParser(runner);
        try {
            parser.parseArgument(argv);
            runner.mainLoop();
        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            System.err.println("java -jar " + PROGRAM_NAME + " [options...] arguments...");
            parser.printUsage(System.err);
            System.exit(-1);
        }
    }        
}
