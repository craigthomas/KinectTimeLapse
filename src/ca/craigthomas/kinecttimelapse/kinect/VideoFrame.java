/*
 * Copyright (C) 2014 Craig Thomas
 * This project uses an MIT style license - see LICENSE for details.
 */
package ca.craigthomas.kinecttimelapse.kinect;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import org.openkinect.freenect.FrameMode;

/**
 * Stores a single frame of data from a Kinect stream. 
 * 
 * @author thomas
 */
public class VideoFrame {

    private final ByteBuffer sByteBuffer;
    private final int sTimestamp;
    private final FrameMode sFrameMode;
    private BufferedImage mBufferedImage;

    /**
     * Simply store the contents of the video frame for future reference.
     * 
     * @param frameMode the image meta-data
     * @param byteBuffer the raw image bytes
     * @param timestamp a timestamp of when the image was taken
     */
    public VideoFrame(FrameMode frameMode, ByteBuffer byteBuffer, int timestamp) {
        sFrameMode = frameMode;
        sByteBuffer = byteBuffer;
        sTimestamp = timestamp;
    }
    
    /**
     * Returns a buffered image of the ByteBuffer, assumed to be an RGB
     * image.
     * 
     * @return the RGB image
     */
    private BufferedImage getRGBBufferedImage() {
        int width = sFrameMode.width;
        int height = sFrameMode.height;
        
        // Convert the ByteBuffer into a ByteArrayInputStream for easier access
        byte[] data = new byte[sByteBuffer.remaining()];
        sByteBuffer.get(data);
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        
        mBufferedImage = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_RGB);
        
        // Loop through the image data and write it into the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = stream.read();
                int g = stream.read();
                int b = stream.read();
                mBufferedImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return mBufferedImage;        
    }
    
    /**
     * Returns a buffered image of the ByteBuffer, assumed to be an 8-bit
     * greyscale image taken from the IR camera.
     * 
     * @return the IR image
     */
    private BufferedImage getIR8BITBufferedImage() {
        int width = sFrameMode.width;
        int height = sFrameMode.height;
        
        // Convert the ByteBuffer into a ByteArrayInputStream for easier access
        byte[] data = new byte[sByteBuffer.remaining()];
        sByteBuffer.get(data);
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        
        mBufferedImage = new BufferedImage(width, height, 
                BufferedImage.TYPE_BYTE_GRAY);
        
        // Loop through the image data and write it into the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int g = stream.read();
                mBufferedImage.setRGB(x, y, new Color(g, g, g).getRGB());
            }
        }
        return mBufferedImage;                
    }
    
    /**
     * Turn the data in the ByteBuffer into a BufferedImage. Store the results
     * of the BufferedImage so that we don't have to recalculate it again.
     * 
     * @return a BufferedImage of the ByteBuffer contents
     */
    public BufferedImage getBufferedImage() {
        // Return the already generated BufferedImage if one exists
        if (mBufferedImage != null) {
            return mBufferedImage;
        }
        
        switch (sFrameMode.getVideoFormat()) {
        case BAYER:
            break;
        case IR_10BIT:
            break;
        case IR_10BIT_PACKED:
            break;
        case IR_8BIT:
            mBufferedImage = getIR8BITBufferedImage();
            break;
        case RGB:
            mBufferedImage = getRGBBufferedImage();
            break;
        case YUV_RAW:
            break;
        case YUV_RGB:
            break;
        default:
            mBufferedImage = null;
            break;
        }
        
        return mBufferedImage;
    }

    /**
     * @return the contents of the raw byte buffer
     */
    public ByteBuffer getByteBuffer() {
        return sByteBuffer;
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
        return sTimestamp;
    }

    /**
     * @return the frame mode
     */
    public FrameMode getFrameMode() {
        return sFrameMode;
    }
}
