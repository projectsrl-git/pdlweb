package net.projectsrl.wm.importdata;

import net.project.errors.AppCrash;
import net.projectsrl.db.PjDBRow;
import net.projectsrl.wm.db.CaricaCedoliniDAO;

public class ControllaLinkNomeDipendenteDaTabellaAPDF extends ControllaCedoliniCaricati_base {

	public ControllaLinkNomeDipendenteDaTabellaAPDF(String _mese, String _anno, String _azienda) {
		super(_mese, _anno, _azienda);
	}

	@Override
	protected void verifyData(PjDBRow row, CaricaCedoliniDAO caricaCedolini) throws AppCrash{
		caricaCedolini.setField(CaricaCedoliniDAO.CHECK2, "S");
	}

}
