
package net.projectsrl.pdlweb.registri;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.pdl.core.PdlWebUtils;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.wm.utils.Utils;

public class FunctionRegistroGiornalieroLavori extends FunctionProjectWebApp_base {

    public FunctionRegistroGiornalieroLavori(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        String anno=Utils.getAnnoOggi();
        
        templateData.put("ANNO", anno);
        templateData.put("DATA_AVANZAMENTO", Utils.getStringDataOggiRibaltata());
        
        if((templateData.get("DATA_FINE_TURNO")==null) && templateData.get("ORA_FINE_TURNO")==null){
        	templateData.put("DATA_FINE_TURNO", Utils.getStringDataOggiRibaltata());
        	
        	String oraFineTurno="";
        	
        	Calendar cal = Calendar.getInstance(); //Create Calendar-Object
        	cal.setTime(new Date());               //Set the Calendar to now
        	int hour = cal.get(Calendar.HOUR_OF_DAY); //Get the hour from the calendar
        	if((hour < 6 || hour>22) && oraFineTurno.equals(""))           
        	{
        	     oraFineTurno="06:00";
        	}
        	
        	if(hour < 14 && oraFineTurno.equals(""))           
        	{
        	     oraFineTurno="14:00";
        	}
        	
        	if(hour < 18 && oraFineTurno.equals(""))           
        	{
        	     oraFineTurno="18:00";
        	}
        	
        	if(hour <22 && oraFineTurno.equals(""))           
        	{
        	     oraFineTurno="22:00";
        	}
        	
        	
        	templateData.put("ORA_FINE_TURNO", oraFineTurno);
        }
        
        
        if (templateData.get("CODICE_TURNO")==null){
        	Calendar cal = Calendar.getInstance(); //Create Calendar-Object
        	cal.setTime(new Date());               //Set the Calendar to now
        	int hour = cal.get(Calendar.HOUR_OF_DAY); //Get the hour from the calendar
        	if((hour > 6 && hour<22))           
        	{
        		templateData.put("CODICE_TURNO", "001");
        	}
        	
        	if((hour > 14 && hour<22))         
        	{
        		templateData.put("CODICE_TURNO", "002");
        	}
        	
        	if((hour > 6 && hour<18))               
        	{
        		templateData.put("CODICE_TURNO", "004");
        	}
        	
        	
        	if((hour > 18 || hour<6))               
        	{
        		templateData.put("CODICE_TURNO", "005");
        	}
        	
        	if((hour > 22 || hour<6))          
        	{
        		templateData.put("CODICE_TURNO", "003");
        	}
        	
        	
        	
        	
        }
        
        PdlWebUtils.updatePdlStatus();

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
}
