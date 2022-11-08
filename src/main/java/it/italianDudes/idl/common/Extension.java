package it.italianDudes.idl.common;

import it.italianDudes.idl.common.exceptions.IO.InsufficientPrivilegesException;

import java.io.File;

@SuppressWarnings("unused")
public abstract class Extension {

    //Attributes
    private final File extensionDirectory;

    //Pseudo-Constructor
    public Extension(File extensionDirectory) throws InsufficientPrivilegesException {

        if(!DirectoryHandler.directoryExist(extensionDirectory)){
            if(!extensionDirectory.mkdirs())
                throw new InsufficientPrivilegesException("Can't create directory tree for extension: "+extensionDirectory.getAbsolutePath());
        }

        this.extensionDirectory = extensionDirectory;
    }

    //Methods
    public abstract State start() throws Exception;
    public File getExtensionDirectory(){
        return extensionDirectory;
    }

}
