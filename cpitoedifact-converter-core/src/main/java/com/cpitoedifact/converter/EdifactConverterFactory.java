package com.cpitoedifact.converter;


/**
 * Factory class for creating EdifactConverter instances.
 * Provides a centralized way to create and configure converter instances.
 */
public class EdifactConverterFactory {
    
    
    private static volatile EdifactConverter defaultConverter;
    
    /**
     * Creates a new instance of EdifactConverter.
     * 
     * @return A new EdifactConverter instance
     */
    public static EdifactConverter createConverter() {
        return new CPIEdifactConverter();
    }
    
    /**
     * Gets the default singleton converter instance.
     * 
     * @return The default EdifactConverter instance
     */
    public static EdifactConverter getDefaultConverter() {
        if (defaultConverter == null) {
            synchronized (EdifactConverterFactory.class) {
                if (defaultConverter == null) {
                    defaultConverter = createConverter();
                    System.out.println("Created default EdifactConverter instance");
                }
            }
        }
        return defaultConverter;
    }
    
    /**
     * Resets the default converter instance.
     * Useful for testing or when configuration changes.
     */
    public static void resetDefaultConverter() {
        synchronized (EdifactConverterFactory.class) {
            defaultConverter = null;
            System.out.println("Reset default EdifactConverter instance");
        }
    }
    
    /**
     * Creates a converter with specific configuration.
     * 
     * @param configPath Path to the configuration file (for future use)
     * @return A configured EdifactConverter instance
     */
    public static EdifactConverter createConverter(String configPath) {
        // For now, return the default converter
        // In future versions, this could load specific configurations
        System.out.println("Creating converter with config: " + configPath);
        return createConverter();
    }
    
    /**
     * Creates a converter with auto-calculation settings.
     * 
     * @param autoCalculateSegments Whether to auto-calculate UNT and UNZ segments
     * @return A configured EdifactConverter instance
     */
    public static EdifactConverter createConverter(boolean autoCalculateSegments) {
        System.out.println("Creating converter with auto-calculate segments: " + autoCalculateSegments);
        return new CPIEdifactConverter();
    }
}
