package net.projectsrl.wm.importdata;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;


public class FunctionFileUploadAnagrafiche extends FunctionFileUpload_base {


    public FunctionFileUploadAnagrafiche() {

        super();
    }
    
    public FunctionFileUploadAnagrafiche(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }
    
    protected void loadData(String sessionUser, String absolutePathFileName , SsbServletRequest req) throws AppCrash {

        LoadFileAnagrafiche load=new LoadFileAnagrafiche();
        String fileName=absolutePathFileName.substring(absolutePathFileName.lastIndexOf("\\")+1);
        req.getSession(false).setAttribute( "FILE_NAME_IMPORT_ANAGRAFICHE", fileName);
        load.loadData(sessionUser, absolutePathFileName,req);
        
   }


}
