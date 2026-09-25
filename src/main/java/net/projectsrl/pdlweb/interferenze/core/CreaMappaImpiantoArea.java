
package net.projectsrl.pdlweb.interferenze.core;

import com.itextpdf.text.pdf.PdfContentByte;

import net.project.errors.AppCrash;
import net.projectsrl.pdlweb.anagrafiche.db.AreaLavoroDAO;

public class CreaMappaImpiantoArea extends CreaMappaImpianto_base {

    private AreaLavoroDAO _areaLavoro;

    public CreaMappaImpiantoArea(Integer idImpianto, AreaLavoroDAO areaLavoro) {

        super(idImpianto);

        _areaLavoro = areaLavoro;

    }

    @Override
    protected void riempiMappa(PdfContentByte cb,String turno) throws AppCrash {

        int rectX;
        int rectY;
        int rectW;
        int rectH;
        int xTextPosition;
        int yTextPosition;
        int lineSpacing;
        String colore="red";

        try {
            if (_areaLavoro.getAttribute(AreaLavoroDAO.RECT_X) != null
                    && _areaLavoro.getAttribute(AreaLavoroDAO.RECT_X) != null
                    && _areaLavoro.getAttribute(AreaLavoroDAO.RECT_W) != null
                    && _areaLavoro.getAttribute(AreaLavoroDAO.RECT_H) != null) {
                rectX = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.RECT_X);

                rectY = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.RECT_Y);

                rectW = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.RECT_W);

                rectH = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.RECT_H);
                coloraArea(cb, rectX, rectY, rectW, rectH, colore);
            }

            if (_areaLavoro.getAttribute(AreaLavoroDAO.TESTO_X) != null
                    && _areaLavoro.getAttribute(AreaLavoroDAO.TESTO_Y) != null
                    && _areaLavoro.getAttribute(AreaLavoroDAO.A_CAPO) != null) {
                xTextPosition = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.TESTO_X);
                yTextPosition = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.TESTO_Y);
                lineSpacing = (Integer) _areaLavoro.getAttribute(AreaLavoroDAO.A_CAPO);

                int yPosition = 0;
                String text = "NR.PDL 1 - IMPRESA 1";
                writePdfText(cb, yPosition, xTextPosition, yTextPosition, lineSpacing, text);

                yPosition += 1;
                text = "NR.PDL 2 - IMPRESA 2";
                writePdfText(cb, yPosition, xTextPosition, yTextPosition, lineSpacing, text);

                yPosition += 1;
                text = "NR.PDL...";
                writePdfText(cb, yPosition, xTextPosition, yTextPosition, lineSpacing, text);
            }

        } catch (AppCrash ac) {
            // nothing to do

        }

    }

}
