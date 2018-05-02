
package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksCompleteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IregisterCompleteStarbucks;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.interactors.RegisterCompleteIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterCompleteIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterCompleteStarbuckss;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iregisterstarbucks;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.DATOSPERSONAREGISTROSTAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by asandovals on 26/04/2018.
 */

public class RegisterCompletePresenterStarbucks implements IregisterCompleteStarbuckss {
    private Context context;
    IregisterCompleteStarbucks registerCompleteStarbucksview;
    IregisterCompleteIteractorStarbucks iregisterCompleteIteractorStarbucks;





    public RegisterCompletePresenterStarbucks(Context context){
        this.context= context;
        iregisterCompleteIteractorStarbucks= new RegisterCompleteIteractorStarbucks(this);

    }

    public void setIView(View View) {
        this.registerCompleteStarbucksview = (IregisterCompleteStarbucks) View;
    }




    @Override
    public void onSucces(WebService ws, Object msgSuccess) {

        switch (ws) {
            case DATOSPERSONAREGISTROSTAR:
                registerCompleteStarbucksview.llenardatospersona();
                break;
            case OBTENER_COLONIAS_CP:

                registerCompleteStarbucksview.llenarcolonias(OBTENER_COLONIAS_CP,(List<ColoniasResponse>) msgSuccess);

                break;



        }

    }

    @Override
    public void onError(WebService ws, Object error) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void registerStarbucks(RegisterUserStarbucks registerUserStarbucks) {
        RegisterStarbucksCompleteRequest request = new RegisterStarbucksCompleteRequest();
        request.setId_PreRegistroMovil(registerUserStarbucks.getId_PreRegistroMovil());
        request.setUdid(registerUserStarbucks.getUdid());
        request.setNumeroTarjeta(registerUserStarbucks.getNumeroTarjeta());
        request.setCodigoVerificador(registerUserStarbucks.getCodigoVerificador());
        request.setNombre(registerUserStarbucks.getNombre());
        request.setGenero(registerUserStarbucks.getGenero());
        request.setPrimerApellido(registerUserStarbucks.getPrimerApellido());
        request.setSegundoApellido(registerUserStarbucks.getSegundoApellido());
        request.setFechaNacimiento(registerUserStarbucks.getFechaNacimiento());
        request.setEmail(registerUserStarbucks.getEmail());
        request.setContrasenia(registerUserStarbucks.getContrasenia());
        request.setTelefono(registerUserStarbucks.getTelefono());
        request.setCodigoPostal(registerUserStarbucks.getCodigoPostal());
        request.setColonia(registerUserStarbucks.getColonia());
        request.setCalleNumero(registerUserStarbucks.getCalleNumero());
        request.setCalleyNumero(registerUserStarbucks.getCalleyNumero());
        request.setBebidaFavorita("");
        request.setSuscripcion(registerUserStarbucks.isSuscripcion());
        iregisterCompleteIteractorStarbucks.registerStarBucks(request);

    }


    @Override
    public void getNeighborhoods(String zipCode) {

        registerCompleteStarbucksview.showLoader(App.getInstance().getString(R.string.obteniendo_colonias));
        iregisterCompleteIteractorStarbucks.getNeighborhoodByZipCode(zipCode);

    }

    @Override
    public void datosregisterStarbucks() {
        iregisterCompleteIteractorStarbucks.consultaInfoPersona();
    }
}
