package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.INavigationView;

import java.util.List;

public interface IGetOperators extends INavigationView {

    void succedGetOperador(List<Operadores> operadores);

    void failGetOperador(String mensaje);
}
