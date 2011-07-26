/*
 *  Copyright (C) 2011 Evan Steinkerchner
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package MCP.mod_finiteliquids;

import java.io.*;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

/**
 * PropertyReader v2.0 by Roundaround<br><br>
 * 
 * PropertyReader v2.0 brings in a whole new system for writing to and reading from .property files.  Unlike previous versions, 2.0 is object based.  This means the user must first define a PropertyReader object based on a file path:<br><br>
 * <code>private static PropertyReader propReader = new PropertyReader("myExampleProp");</code><br><br>
 * This creates a new object based on the relative file path of "myExampleProp" which translates to an absolute file path of "/.minecraft/mods/myExampleProp.properties".<br><br>
 * Next, the user creates properties similarly to previous versions:<br><br>
 * <code>private float setting = propReader.prop(PropertyReader.ARGU_MIN, new float[] {0}, "setting", 6.25, null);</code><br><br>
 * Which will create a property <i>setting</i> with the default value of 6.25, that can be anything greater than or equal to 0.
 * 
 * @author Roundaround
 */
public class PropertyReader {
    
    /**
     * This PropertyReader object's file path.
     */
    private String filePath;
    //<editor-fold defaultstate="collapsed" desc="ARGU Values">
    /**
     * Not using arguments.
     */
    protected static final int ARGU_NULL = -1;
    /**
     * Submitting each value of an array as an argument.  In the case of Strings, the input is case-insensitive.
     */
    protected static final int ARGU_CASEINSENSITIVE = 0;
    /**
     * Submitting each value of an array as an argument.  In the case of Strings, the input is case-sensitive.
     */
    protected static final int ARGU_CASESENSITIVE = 1;
    /**
     * Submitting every possible numerical value between the lowest and highest values of an array as an argument.  Non-inclusive.
     */
    protected static final int ARGU_RANGE = 2;
    /**
     * Submitting every possible numerical value between the lowest and highest values of an array as an argument.  Inclusive on minimum, non-inclusive on maximum.
     */
    protected static final int ARGU_RANGEMIN = 3;
    /**
     * Submitting every possible numerical value between the lowest and highest values of an array as an argument.  Inclusive on maximum, non-inclusive on minimum.
     */
    protected static final int ARGU_RANGEMAX = 4;
    /**
     * Submitting every possible numerical value between the lowest and highest values of an array as an argument.  Inclusive on both minimum and maximum.
     */
    protected static final int ARGU_RANGEALL = 5;
    /**
     * Submitting every possible numerical value greater than the lowest value of an array as an argument.
     */
    protected static final int ARGU_MIN = 6;
    /**
     * Submitting every possible numerical value less than the greatest value of an array as an argument.
     */
    protected static final int ARGU_MAX = 7;
    //</editor-fold>
    
    /**
     * Constructs a new PropertyReader object from which to create and access various properties.
     * @param relative Tells whether the file path is relative (true) or absolute (false).
     * @param filePath The file path of the .properties file.
     */
    public PropertyReader(boolean relative, String filePath) {
        if(!filePath.endsWith(".properties")) filePath += ".properties";
        this.filePath = relative ? relativeFilePath(filePath) : filePath;
        checkFilePath(this.filePath);
    }
    
    /**
     * Constructs a new PropertyReader object from which to create and access various properties, where the file path is automatically set to relative.
     * @param filePath The file path of the .properties file.
     */
    public PropertyReader(String filePath) {
        this(true, filePath);
    }
    
    /**
     * Prints the supplied text through System.out.println, but with the prefix of "PropertyReader: ".
     * @param text The text to print.
     */
    private void systemPrint(String text) {
        System.out.println("PropertyReader: " + text);
    }

    /**
     * The path to the user's .minecraft folder, or a new Minecraft folder in the user's home directory if none can be found.
     * @return The Minecraft folder.
     */
    private String mcFolder() {
        String minefolder = "";
        try {
            File bin = new File(PropertyReader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            minefolder = new File(bin.getParent()).getParent();
        } catch (Exception e) {
            minefolder = System.getProperty("user.home") + "\\Minecraft";
            // TODO Logger
            e.printStackTrace();
        }
        return minefolder + "\\mods";
    }
    
    /**
     * Constructs a new URI String from appending the String input to the end of the mcFolder() String.
     * @param filePath The relative file path to convert.
     * @return The absolute version of the given relative file path.
     */
    private String relativeFilePath(String filePath) {
        filePath = filePath.replace("/", "\\\\");
        return (mcFolder() + "\\" + filePath).replace("\\\\\\\\", "\\\\");
    }

    /**
     * Checks if the given file path and/or file exists, and creates it if it doesn't.
     * @param filePath The file path to check.
     */
    private void checkFilePath(String filePath) {
        try {
            File file = new File(filePath);
            if(filePath.endsWith("\\")) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Throwable t) {
            // TODO Logger
            t.printStackTrace();
        }
    }

    /**
     * Retrieves the runtime value of the property under the given key.
     * @param key The property key.
     * @return The value of the property, or null if none exists.
     */
    private String readProp(String key) {
        try {
            File file = new File(filePath);
            BufferedReader dis = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = dis.readLine()) != null) {
                if (line.startsWith(key) && line.contains("=")) {
                    return line.split("=")[1].trim();
                }
            }
        } catch (Exception e) { 
            // TODO Logger
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the property with the given parameters to the end of the .properties file.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     */
    private void writeProp(String key, String def, String comment) {
        try {
            systemPrint("Could not find property '" + key + ".'  Creating it with the default of '" + def + "'.");
            BufferedWriter prop = new BufferedWriter(new FileWriter(filePath, true));
            prop.newLine();
            prop.write(key + " = " + def);
            if(comment != null && !comment.isEmpty()) {
                prop.newLine();
                prop.write(comment);
            }
            prop.close();
        } catch (IOException e) {
            // TODO Logger
        }
    }

    /**
     * Checks if the property exists, and writes it if it doesn't, then retrieves the value of the property.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The property value to use.
     */
    private String getProp(String key, String def, String comment) {
        if (readProp(key) == null) {
            writeProp(key, def, comment);
            return def;
        }
        return readProp(key);
    }
    
    /**
     * Compares the properties against their arguments, based on the argument type.
     * @param propValue The property's current value.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The property if it checks out, or default if it doesn't.
     */
    private double checkArgsNumeric(double propValue, int arguType, double[] argu, String key, double def, String comment) {
        try {
            double minValue = 0.0F, maxValue = 0.0F;
            for(double val : argu) {
                minValue = Math.min(minValue, val);
                maxValue = Math.max(maxValue, val);
            }
            checkArguType(true, arguType);
            if(arguType == ARGU_NULL) {
                return propValue;
            } else if(arguType >= ARGU_RANGE && arguType <= ARGU_RANGEALL) {
                boolean min = false, max = false;
                if((arguType == ARGU_RANGE || arguType == ARGU_RANGEMAX) && propValue > minValue) {
                    min = true;
                }
                if((arguType == ARGU_RANGE || arguType == ARGU_RANGEMIN) && propValue < maxValue) {
                    max = true;
                }
                if((arguType == ARGU_RANGEALL || arguType == ARGU_RANGEMAX) && propValue <= maxValue) {
                    max = true;
                }
                if((arguType == ARGU_RANGEALL || arguType == ARGU_RANGEMIN) && propValue >= minValue) {
                    min = true;
                }
                if(min && max) {
                    return propValue;
                }
            } else if(arguType == ARGU_MIN) {
                if(propValue >= minValue) return propValue;
            } else if(arguType == ARGU_MAX) {
                if(propValue <= maxValue) return propValue;
            }
        } catch(InvalidArgumentTypeException e) {
            // TODO Logger
            e.printStackTrace();
        }
        return def;
    }

    /**
     * Reads a property and converts it to a String.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The String object of the property.
     */
    public String prop(int arguType, String[] argu, String key, String def, String comment) {
        try {
            checkArguType(false, arguType);
            if(arguType == ARGU_NULL) {
                return getProp(key, def, comment);
            } else if(arguType == ARGU_CASESENSITIVE) {
                for(String arg : argu) {
                    if(getProp(key, def, comment).equals(arg)) {
                        return getProp(key, def, comment);
                    }
                }
            } else if(arguType == ARGU_CASEINSENSITIVE) {
                for(String arg : argu) {
                    if(getProp(key, def, comment).equalsIgnoreCase(arg)) {
                        return getProp(key, def, comment);
                    }
                }
            }
        } catch(InvalidArgumentTypeException e) {
            // TODO Logger
            e.printStackTrace();
        }
        return def;
    }

    /**
     * Reads an array of properties and converts them to an array of Strings.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The String objects of the properties.
     */
    public String[] prop(int arguType, String[] argu, String[] key, String[] def, String[] comment) {
        String[] props = new String[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a boolean.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The boolean datatype value of the property.
     */
    public boolean prop(String key, boolean def, String comment) {
        String prop = getProp(key, String.valueOf(def), comment);
        String[] accepted = new String[] {"true","1","yes","on"};
        for(String s : accepted) {
            if(prop.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads an array of properties and converts them to an array of booleans.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The boolean datatype values of the properties.
     */
    public boolean[] prop(String[] key, boolean[] def, String[] comment) {
        boolean[] props = new boolean[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a float.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The float datatype value of the property.
     */
    public float prop(int arguType, float[] argu, String key, float def, String comment) {
        double[] argu1 = new double[argu.length];
        for(int i=0;i<argu.length;i++) {
            argu1[i] = (double)argu[i];
        }
        return (float)checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu1, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of floats.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The float datatype values of the properties.
     */
    public float[] prop(int arguType, float[] argu, String[] key, float[] def, String[] comment) {
        float[] props = new float[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a double.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The double datatype value of the property.
     */
    public double prop(int arguType, double[] argu, String key, double def, String comment) {
        return checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of doubles.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The double datatype values of the properties.
     */
    public double[] prop(int arguType, double[] argu, String[] key, double[] def, String[] comment) {
        double[] props = new double[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a byte.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The byte datatype value of the property.
     */
    public byte prop(int arguType, byte[] argu, String key, byte def, String comment) {
        double[] argu1 = new double[argu.length];
        for(int i=0;i<argu.length;i++) {
            argu1[i] = (double)argu[i];
        }
        return (byte)checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu1, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of bytes.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The byte datatype values of the properties.
     */
    public byte[] prop(int arguType, byte[] argu, String[] key, byte[] def, String[] comment) {
        byte[] props = new byte[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a short.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The short datatype value of the property.
     */
    public short prop(int arguType, short[] argu, String key, short def, String comment) {
        double[] argu1 = new double[argu.length];
        for(int i=0;i<argu.length;i++) {
            argu1[i] = (double)argu[i];
        }
        return (short)checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu1, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of shorts.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The short datatype values of the properties.
     */
    public short[] prop(int arguType, short[] argu, String[] key, short[] def, String[] comment) {
        short[] props = new short[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to an int.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The int datatype value of the property.
     */
    public int prop(int arguType, int[] argu, String key, int def, String comment) {
        double[] argu1 = new double[argu.length];
        for(int i=0;i<argu.length;i++) {
            argu1[i] = (double)argu[i];
        }
        return (int)checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu1, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of ints.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The int datatype values of the properties.
     */
    public int[] prop(int arguType, int[] argu, String[] key, int[] def, String[] comment) {
        int[] props = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to a long.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property key.
     * @param def The default property value.
     * @param comment Extra information about the property.  Can be null.
     * @return The long datatype value of the property.
     */
    public long prop(int arguType, long[] argu, String key, long def, String comment) {
        double[] argu1 = new double[argu.length];
        for(int i=0;i<argu.length;i++) {
            argu1[i] = (double)argu[i];
        }
        return (long)checkArgsNumeric(Double.valueOf(getProp(key, String.valueOf(def), comment)), arguType, argu1, key, def, comment);
    }

    /**
     * Reads an array of properties and converts them to an array of longs.
     * @param arguType The argument type used.
     * @param argu The array of arguments.
     * @param key The property keys.
     * @param def The default property values.
     * @param comment Extra information about the properties.  Can be null.
     * @return The long datatype value of the properties.
     */
    public long[] prop(int arguType, long[] argu, String[] key, long[] def, String[] comment) {
        long[] props = new long[key.length];
        for (int i = 0; i < key.length; i++) {
            props[i] = prop(arguType, argu, key[i], def[i], comment[i]);
        }
        return props;
    }

    /**
     * Reads a property and converts it to an int for use as a BLOCK id.  Includes built-in arguments.
     * @param name The block's name.
     * @param def The default id.
     * @param comment Extra information about the block.  Can be null.
     * @return The id of the block.
     */
    public int blockID(String name, int def, String comment) {
        if(Block.blocksList[Integer.valueOf(getProp(name, String.valueOf(def), comment))] != null) {
            return Integer.valueOf(getProp(name, String.valueOf(def), comment));
        }
        return def;
    }

    /**
     * Reads an array of properties and converts them to ints for use as BLOCK ids.  Includes built-in arguments.
     * @param name The blocks' names.
     * @param def The default ids.
     * @param comment Extra information about the blocks.  Can be null.
     * @return The ids of the blocks.
     */
    public int[] blockID(String[] name, int[] def, String[] comment) {
        int[] ids = new int[name.length];
        for (int i = 0; i < name.length; i++) {
            ids[i] = blockID(name[i], def[i], comment[i]);
        }
        return ids;
    }

    /**
     * Reads a property and converts it to an int for use as an ITEM id.  Includes built-in arguments.
     * @param name The item's name.
     * @param def The default id.
     * @param comment Extra information about the item.  Can be null.
     * @return The id of the item.
     */
    public int itemID(String name, int def, String comment) {
        if(Item.itemsList[Integer.valueOf(getProp(name, String.valueOf(def), comment))] != null) {
            return Integer.valueOf(getProp(name, String.valueOf(def), comment));
        }
        return def;
    }

    /**
     * Reads an array of properties and converts them to ints for use as ITEM ids.  Includes built-in arguments.
     * @param name The items' names.
     * @param def The default ids.
     * @param comment Extra information about the items.  Can be null.
     * @return The ids of the items.
     */
    public int[] itemID(String[] name, int[] def, String[] comment) {
        int[] ids = new int[name.length];
        for (int i = 0; i < name.length; i++) {
            ids[i] = itemID(name[i], def[i], comment[i]);
        }
        return ids;
    }
    
    /**
     * Checks the used argument type against the current type of primitive datatype being analyzed, and throws an InvalidArgumentTypeException in the case that an invalid argument type int is used.
     * @param isNumeric Determines the type of datatype.
     * @param arguType The argument type used.
     * @throws InvalidArgumentTypeException Thrown when an argument type int provided is not of the valid choices for the property.
     */
    private void checkArguType(boolean isNumeric, int arguType) throws InvalidArgumentTypeException {
        if(arguType<-1 || arguType>7) arguType = 8;
        if(!isNumeric && (arguType != ARGU_NULL && arguType != ARGU_CASEINSENSITIVE && arguType != ARGU_CASESENSITIVE)) {
            throw new InvalidArgumentTypeException(arguType);
        }
        if(isNumeric && (arguType == ARGU_CASEINSENSITIVE || arguType == ARGU_CASESENSITIVE)) {
            throw new InvalidArgumentTypeException(arguType);
        }
    }
    
    /**
     * The custom exception thrown by the checkArguType method.
     * @param arguType The argument type that is used and invalid.
     */
    private class InvalidArgumentTypeException extends Exception {
        private InvalidArgumentTypeException(int arguType) {
            super("Cannot use the " + new String[] {
                "CASEINSENSITIVE","CASESENSITIVE","RANGE","RANGEMIN","RANGEMAX","RANGEALL","MIN","MAX","(Unknown)"
                }[arguType] + "argument type here.");
        }
    }
}
