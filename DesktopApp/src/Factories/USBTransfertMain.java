package Factories;
import javax.swing.*;
import java.lang.Number;
import java.math.BigInteger;

import screens.AbstractEmptyScreen;
/**
 * Created by Philip on 04/05/2016.
 */
public class USBTransfertMain {

    public static void jMTPeMethode(String name)
    {
        //System.loadLibrary("jmtp");
        jmtp.PortableDeviceFolderObject targetFolder = null;
        jmtp.PortableDeviceManager manager = new jmtp.PortableDeviceManager();
        jmtp.PortableDevice device = null;
        boolean done = false;
        while(!done) {
            try {
                device = manager.getDevices()[0];
                done = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "The tablet is not connected properly \n " +
                                "connect it and only then press OK"
                        , null, JOptionPane.ERROR_MESSAGE);
                done = true;
            }
        }
        // Connect to USB tablet
        device.open();
        System.out.println(device.getModel());

        System.out.println("---------------");

        // Iterate over deviceObjects
        for (jmtp.PortableDeviceObject object : device.getRootObjects())
        {
            // If the object is a storage object
            if (object instanceof jmtp.PortableDeviceStorageObject)
            {
                jmtp.PortableDeviceStorageObject storage = (jmtp.PortableDeviceStorageObject) object;

                for (jmtp.PortableDeviceObject o2 : storage.getChildObjects())
                {
                    if(o2.getOriginalFileName().equalsIgnoreCase("AAXml")) {
                        targetFolder = (jmtp.PortableDeviceFolderObject) o2;
                    }
                    System.out.println(o2.getOriginalFileName());
                }

                copyFileFromComputerToDeviceFolder(targetFolder,name);
                jmtp.PortableDeviceObject[] folderFiles = targetFolder.getChildObjects();
                for (jmtp.PortableDeviceObject pDO : folderFiles) {
                    //copyFileFromDeviceToComputerFolder(pDO, device);
                }

            }
        }

        manager.getDevices()[0].close();
    }

//    private static void copyFileFromDeviceToComputerFolder(jmtp.PortableDeviceObject pDO, jmtp.PortableDevice device)
//    {
//        jmtp.PortableDeviceToHostImpl32 copy = new jmtp.PortableDeviceToHostImpl32();
//        try {
//            copy.copyFromPortableDeviceToHost(pDO.getID(), "C:\\TransferTest", device);
//        } catch (COMException ex) {
//            ex.printStackTrace();
//        }
//    }

    private static void copyFileFromComputerToDeviceFolder(jmtp.PortableDeviceFolderObject targetFolder,String name)
    {
        BigInteger bigInteger1 = new BigInteger("123456789");
        java.io.File file = new java.io.File("./xmlDir/"+name+".xml");
        try {
            targetFolder.addAudioObject(file, "jj", "jj", bigInteger1);
        } catch (Exception e) {
            System.out.println("Exception e = " + e);
        }
    }
}
