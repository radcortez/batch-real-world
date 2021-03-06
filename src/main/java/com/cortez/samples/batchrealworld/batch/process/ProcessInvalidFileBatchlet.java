package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.Batchlet;
import javax.inject.Named;
import java.io.File;

import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;
import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.moveFile;

/**
 * @author Roberto Cortez
 */
@Named
public class ProcessInvalidFileBatchlet extends AbstractFileProcess implements Batchlet {
    @Override
    public String process() throws Exception {
        File fileToProcess = getFileToProcess(FolderType.FI_TMP);
        File fileToMove = getFile(getFolder(FolderType.FO) + "/" + fileToProcess.getName() + ".INVALID");

        getLogger(this.getClass().getName()).log(INFO,
                                                 "Moving invalid file from " + fileToProcess + " to " + fileToMove);
        moveFile(fileToProcess, fileToMove);

        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {}
}
