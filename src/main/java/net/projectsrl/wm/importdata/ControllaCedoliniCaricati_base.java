package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDBRow;
import net.projectsrl.wm.db.CaricaCedoliniDAO;

public abstract class ControllaCedoliniCaricati_base {

	private String _mese;
	private String _anno; 
	private String _azienda;
	
	public ControllaCedoliniCaricati_base(String _mese, String _anno, String _azienda) {
		super();
		this._mese = _mese;
		this._anno = _anno;
		this._azienda = _azienda;
	}


	public void check() throws AppCrash {

		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetElencoFile");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("AZIENDA", _azienda);
			params.put("ANNO", _anno);
			dataSet.setParam(params);
			//_mese=_mese.replace("TFR_A", "");

			dataSet.open();
			while (dataSet.hasMoreElements()) {
				
				PjDBRow row=(PjDBRow) dataSet.nextElement();
				CaricaCedoliniDAO caricaCedolini=new CaricaCedoliniDAO();
				caricaCedolini.setField(CaricaCedoliniDAO.AZIENDA,_azienda);
				caricaCedolini.setField(CaricaCedoliniDAO.ANNO,_anno);
				caricaCedolini.setField(CaricaCedoliniDAO.MESE,_mese);
				
				String dipendente = (String) row.getField(CaricaCedoliniDAO.DIPENDENTE);
				caricaCedolini.setField(CaricaCedoliniDAO.DIPENDENTE,dipendente);
				
				if (!caricaCedolini.retrieve()) {
					continue;
				}
				
				verifyData(row, caricaCedolini);
				
				caricaCedolini.update();
			}
			
			dataSet.close();
		} catch (Throwable t) {
			AppCrash ac = new AppCrash(t);
			throw ac;
		} finally {
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (AppCrash ac) {
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
				}
			}
		}

	}



	protected abstract void verifyData(PjDBRow row, CaricaCedoliniDAO caricaCedolini) throws AppCrash ;


	protected String getMese() {
		return _mese;
	}


	protected String getAnno() {
		return _anno;
	}


	protected String getAzienda() {
		return _azienda;
	}
}
